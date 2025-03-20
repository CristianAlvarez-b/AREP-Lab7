package co.edu.escuelaing.arep.microservicios.controller;

import co.edu.escuelaing.arep.microservicios.model.User;
import co.edu.escuelaing.arep.microservicios.service.JwtUtil;
import co.edu.escuelaing.arep.microservicios.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        // Simular comportamiento del servicio
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // Llamar al método del controlador
        List<User> response = userController.getAllUsers();

        // Verificar la respuesta
        assertNotNull(response);
        assertEquals(0, response.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void createUser_ShouldReturnUserWithToken() {
        // Simular usuario
        User mockUser = new User();
        mockUser.setUsername("testUser");

        // Simular comportamiento del servicio y JWT
        when(userService.createUser(any(User.class))).thenReturn(mockUser);
        when(jwtUtil.generateToken(mockUser.getUsername())).thenReturn("mockToken");

        // Llamar al método del controlador
        ResponseEntity<?> response = userController.createUser(mockUser);

        // Verificar la respuesta
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Extraer el cuerpo de la respuesta y verificar valores
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("user"));
        assertTrue(responseBody.containsKey("token"));
        assertEquals("testUser", ((User) responseBody.get("user")).getUsername());
        assertEquals("mockToken", responseBody.get("token"));

        // Verificar que los métodos fueron llamados
        verify(userService, times(1)).createUser(any(User.class));
        verify(jwtUtil, times(1)).generateToken("testUser");
    }
}
