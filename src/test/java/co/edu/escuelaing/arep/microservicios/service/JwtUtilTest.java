package co.edu.escuelaing.arep.microservicios.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String validToken;
    private String expiredToken;
    private final String username = "testUser";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        // Generar un token válido
        validToken = jwtUtil.generateToken(username);

        // Generar un token expirado
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // Emitido hace 1 hora
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60)) // Expirado hace 1 minuto
                .signWith(key)
                .compact();
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        assertNotNull(validToken);
        assertFalse(validToken.isEmpty());
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        assertTrue(jwtUtil.validateToken(validToken));
    }

    @Test
    void validateToken_ShouldReturnFalseForInvalidToken() {
        assertFalse(jwtUtil.validateToken("invalidToken"));
    }

    @Test
    void validateToken_ShouldReturnFalseForExpiredToken() {
        assertFalse(jwtUtil.validateToken(expiredToken));
    }

    @Test
    void getUsernameFromToken_ShouldReturnCorrectUsername() {
        String extractedUsername = jwtUtil.getUsernameFromToken(validToken);
        assertEquals(username, extractedUsername);
    }

    @Test
    void getUsernameFromToken_ShouldThrowExceptionForInvalidToken() {
        assertThrows(Exception.class, () -> jwtUtil.getUsernameFromToken("invalidToken"));
    }
}
