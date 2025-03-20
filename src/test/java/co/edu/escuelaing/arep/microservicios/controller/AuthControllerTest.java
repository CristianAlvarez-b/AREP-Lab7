package co.edu.escuelaing.arep.microservicios.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private OAuth2AuthorizedClientService authorizedClientService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private OAuth2AuthenticationToken authenticationToken;

    @Mock
    private OAuth2AuthorizedClient authorizedClient;

    @Mock
    private OAuth2AccessToken accessToken;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getToken_ShouldReturnToken_WhenAuthenticated() {
        // Simular contexto de autenticación
        when(securityContext.getAuthentication()).thenReturn(authenticationToken);
        when(authenticationToken.getAuthorizedClientRegistrationId()).thenReturn("client-id");
        when(authenticationToken.getName()).thenReturn("user");

        // Simular cliente autorizado con un token de acceso
        when(authorizedClientService.loadAuthorizedClient("client-id", "user"))
                .thenReturn(authorizedClient);
        when(authorizedClient.getAccessToken()).thenReturn(accessToken);
        when(accessToken.getTokenValue()).thenReturn("mocked-token");

        // Llamar al método de prueba
        ResponseEntity<Map<String, String>> response = authController.getToken();

        // Validar que el token se obtiene correctamente
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("mocked-token", response.getBody().get("token"));
    }

    @Test
    void getToken_ShouldReturnUnauthorized_WhenNotAuthenticated() {
        // Simular contexto sin autenticación válida
        when(securityContext.getAuthentication()).thenReturn(null);

        // Llamar al método de prueba
        ResponseEntity<Map<String, String>> response = authController.getToken();

        // Validar que la respuesta sea UNAUTHORIZED
        assertEquals(401, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getToken_ShouldReturnUnauthorized_WhenNoTokenAvailable() {
        // Simular contexto de autenticación sin token de acceso
        when(securityContext.getAuthentication()).thenReturn(authenticationToken);
        when(authenticationToken.getAuthorizedClientRegistrationId()).thenReturn("client-id");
        when(authenticationToken.getName()).thenReturn("user");

        // Simular cliente autorizado sin token
        when(authorizedClientService.loadAuthorizedClient("client-id", "user"))
                .thenReturn(authorizedClient);
        when(authorizedClient.getAccessToken()).thenReturn(null);

        // Llamar al método de prueba
        ResponseEntity<Map<String, String>> response = authController.getToken();

        // Validar que la respuesta sea UNAUTHORIZED
        assertEquals(401, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}

