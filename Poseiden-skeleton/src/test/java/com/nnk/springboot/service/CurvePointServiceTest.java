package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


/**
 * Tests unitaires pour le service CurvePoint.
 * Cette classe teste toutes les opérations CRUD du service CurvePoint
 * en utilisant des mocks pour simuler le comportement du repository.
 */
@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;


    /**
     * Teste la récupération de tous les points de courbe.
     * Vérifie que la méthode getAll appelle correctement le repository
     * et retourne la liste complète des points.
     */
    @Test
    void getAll_returnsAllCurvePoints() {
        // Simule le retour d'une liste de deux points de courbe
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(mock(CurvePoint.class), mock(CurvePoint.class)));

        curvePointService.getAll();

        // Vérifie que la méthode findAll du repository a été appelée
        verify(curvePointRepository).findAll();
    }

    /**
     * Teste la sauvegarde d'un point de courbe.
     * Vérifie que la méthode save enregistre correctement le point
     * dans le repository.
     */
    @Test
    void save_savesAndReturnsCurvePoint() {
        // Configure le mock pour simuler la sauvegarde
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(mock(CurvePoint.class));

        curvePointService.save(mock(CurvePoint.class));

        // Vérifie que la méthode save du repository a été appelée avec un point de courbe
        verify(curvePointRepository).save(any(CurvePoint.class));
    }

    /**
     * Teste la récupération d'un point de courbe par son ID.
     * Vérifie que la méthode retourne correctement le point
     * lorsque l'ID existe.
     */
    @Test
    void getById_existingId_returnsCurvePoint() {
        // Simule la présence d'un point de courbe dans le repository
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(mock(CurvePoint.class)));

        curvePointService.getById(anyInt());

        // Vérifie que la recherche par ID a été effectuée
        verify(curvePointRepository).findById(anyInt());
    }

    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> curvePointService.getById(anyInt()));
        verify(curvePointRepository).findById(anyInt());
    }

    @Test
    void update_existingId_updatesAndReturnsCurvePoint() {
        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setCurveId(1);
        CurvePoint newCurvePoint = new CurvePoint();
        newCurvePoint.setCurveId(2);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(existingCurvePoint));
        when(curvePointRepository.save(existingCurvePoint)).thenReturn(existingCurvePoint);

        curvePointService.update(1, newCurvePoint);

        verify(curvePointRepository).findById(1);
        verify(curvePointRepository).save(existingCurvePoint);
    }


    @Test
    void delete_existingId_deletesCurvePoint() {
        when(curvePointRepository.existsById(anyInt())).thenReturn(true);

        curvePointService.delete(anyInt());

        verify(curvePointRepository).existsById(anyInt());
        verify(curvePointRepository).deleteById(anyInt());
    }

    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(curvePointRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> curvePointService.delete(anyInt()));
        verify(curvePointRepository).existsById(anyInt());
    }
}