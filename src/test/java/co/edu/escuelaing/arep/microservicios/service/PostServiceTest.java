package co.edu.escuelaing.arep.microservicios.service;

import co.edu.escuelaing.arep.microservicios.model.Post;
import co.edu.escuelaing.arep.microservicios.model.User;
import co.edu.escuelaing.arep.microservicios.repository.PostRepository;
import co.edu.escuelaing.arep.microservicios.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() {
        // Simular datos de prueba
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postRepository.findAll()).thenReturn(posts);

        // Llamar al método de prueba
        List<Post> result = postService.getAllPosts();

        // Validaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void createPost_ShouldReturnSavedPost_WhenUserExists() {
        // Simular datos de prueba
        String content = "Este es un post de prueba";
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Post post = new Post();
        post.setContent(content);
        post.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Llamar al método de prueba
        Post result = postService.createPost(content, userId);

        // Validaciones
        assertNotNull(result);
        assertEquals(content, result.getContent());
        assertEquals(user, result.getUser());
        verify(userRepository, times(1)).findById(userId);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void createPost_ShouldThrowException_WhenContentExceedsLimit() {
        String longContent = "a".repeat(141); // Excede el límite de 140 caracteres
        Long userId = 1L;

        // Validar que se lanza una excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.createPost(longContent, userId);
        });

        assertEquals("El contenido excede los 140 caracteres.", exception.getMessage());
        verify(userRepository, never()).findById(anyLong());
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void createPost_ShouldThrowException_WhenUserNotFound() {
        String content = "Un post válido";
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Validar que se lanza una excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.createPost(content, userId);
        });

        assertEquals("Usuario no encontrado.", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(postRepository, never()).save(any(Post.class));
    }
}
