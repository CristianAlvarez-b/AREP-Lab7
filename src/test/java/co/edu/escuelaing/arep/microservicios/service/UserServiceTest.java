package co.edu.escuelaing.arep.microservicios.service;

import co.edu.escuelaing.arep.microservicios.model.User;
import co.edu.escuelaing.arep.microservicios.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Simular datos de prueba
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // Llamar al método de prueba
        List<User> result = userService.getAllUsers();

        // Validaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createUser_ShouldReturnSavedUser() {
        // Simular datos de prueba
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Llamar al método de prueba
        User result = userService.createUser(user);

        // Validaciones
        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }
}
