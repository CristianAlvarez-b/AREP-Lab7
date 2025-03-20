package co.edu.escuelaing.arep.microservicios.controller;

import co.edu.escuelaing.arep.microservicios.model.Post;
import co.edu.escuelaing.arep.microservicios.model.User;
import co.edu.escuelaing.arep.microservicios.service.JwtUtil;
import co.edu.escuelaing.arep.microservicios.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() {
        // Simular datos de prueba
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        Post post1 = new Post();
        post1.setId(1L);
        post1.setContent("Primer post");
        post1.setUser(user1);
        post1.setTimestamp(new Date());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setContent("Segundo post");
        post2.setUser(user2);
        post2.setTimestamp(new Date());

        List<Post> mockPosts = List.of(post1, post2);

        when(postService.getAllPosts()).thenReturn(mockPosts);

        // Llamar al método de prueba
        List<Post> result = postController.getAllPosts();

        // Verificar resultados
        assertEquals(2, result.size());
        assertEquals("Primer post", result.get(0).getContent());
        assertEquals("Segundo post", result.get(1).getContent());
    }

    @Test
    void createPost_ShouldReturnPostAndToken_WhenValidRequest() {
        // Simular datos de entrada
        String content = "Nuevo post";
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");
        user.setEmail("testUser@example.com");

        Post mockPost = new Post();
        mockPost.setId(10L);
        mockPost.setContent(content);
        mockPost.setUser(user);
        mockPost.setTimestamp(new Date());

        // Simular comportamiento de servicios
        when(postService.createPost(content, userId)).thenReturn(mockPost);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn("mocked-token");

        // Crear request body simulado
        Map<String, Object> requestBody = Map.of("content", content, "userId", userId);

        // Llamar al método de prueba
        ResponseEntity<?> response = postController.createPost(requestBody);

        // Validar respuesta
        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(mockPost, responseBody.get("post"));
        assertEquals("mocked-token", responseBody.get("token"));
    }

    @Test
    void createPost_ShouldReturnBadRequest_WhenMissingFields() {
        // Crear request body sin userId
        Map<String, Object> requestBody = Map.of("content", "Falta userId");

        // Llamar al método de prueba
        ResponseEntity<?> response = postController.createPost(requestBody);

        // Validar que se retorna BadRequest con mensaje de error
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((Map<String, Object>) response.getBody()).containsKey("error"));
    }
}
