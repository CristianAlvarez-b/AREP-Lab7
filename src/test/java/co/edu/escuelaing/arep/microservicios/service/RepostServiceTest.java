package co.edu.escuelaing.arep.microservicios.service;

import co.edu.escuelaing.arep.microservicios.model.Repost;
import co.edu.escuelaing.arep.microservicios.repository.RepostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepostServiceTest {

    @Mock
    private RepostRepository repostRepository;

    @InjectMocks
    private RepostService repostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReposts_ShouldReturnListOfReposts() {
        // Simular datos de prueba
        List<Repost> reposts = Arrays.asList(new Repost(), new Repost());
        when(repostRepository.findAll()).thenReturn(reposts);

        // Llamar al método de prueba
        List<Repost> result = repostService.getAllReposts();

        // Validaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repostRepository, times(1)).findAll();
    }

    @Test
    void createRepost_ShouldReturnSavedRepost() {
        // Simular datos de prueba
        Repost repost = new Repost();
        when(repostRepository.save(any(Repost.class))).thenReturn(repost);

        // Llamar al método de prueba
        Repost result = repostService.createRepost(repost);

        // Validaciones
        assertNotNull(result);
        verify(repostRepository, times(1)).save(repost);
    }
}
