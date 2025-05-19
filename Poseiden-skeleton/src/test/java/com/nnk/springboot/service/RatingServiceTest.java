package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
 * Classe de tests unitaires pour RatingService.
 * Cette classe vérifie le bon fonctionnement des opérations CRUD du service Rating.
 */
@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    /**
     * Mock du repository de Rating utilisé pour simuler les opérations de base de données.
     */
    @Mock
    private RatingRepository ratingRepository;

    /**
     * Instance du service Rating à tester, avec injection des mocks.
     */
    @InjectMocks
    private RatingService ratingService;


    /**
     * Vérifie que la méthode getAll retourne tous les ratings existants.
     */
    @Test
    void getAll_returnsAllRatings() {
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(mock(Rating.class), mock(Rating.class)));

        ratingService.getAll();

        verify(ratingRepository).findAll();
    }

    /**
     * Vérifie que la méthode save enregistre et retourne correctement un rating.
     */
    @Test
    void save_savesAndReturnsRating() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(mock(Rating.class));

        ratingService.save(mock(Rating.class));

        verify(ratingRepository).save(any(Rating.class));
    }

    /**
     * Vérifie que la méthode getById retourne le rating correspondant à l'ID fourni.
     */
    @Test
    void getById_existingId_returnsRating() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(mock(Rating.class)));

        ratingService.getById(anyInt());

        verify(ratingRepository).findById(anyInt());
    }

    /**
     * Vérifie que la méthode getById lance une exception quand l'ID n'existe pas.
     */
    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ratingService.getById(anyInt()));
        verify(ratingRepository).findById(anyInt());
    }

    /**
     * Vérifie que la méthode update met à jour correctement un rating existant.
     */
    @Test
    void update_existingId_updatesAndReturnsRating() {
        Rating existingRating = new Rating();
        existingRating.setMoodysRating("oldMoodys");
        Rating newRating = new Rating();
        newRating.setMoodysRating("newMoodys");

        when(ratingRepository.findById(1)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(existingRating)).thenReturn(existingRating);

        ratingService.update(1, newRating);

        verify(ratingRepository).findById(1);
        verify(ratingRepository).save(existingRating);
    }


    /**
     * Vérifie que la méthode delete supprime correctement un rating existant.
     */
    @Test
    void delete_existingId_deletesRating() {
        when(ratingRepository.existsById(anyInt())).thenReturn(true);

        ratingService.delete(anyInt());

        verify(ratingRepository).existsById(anyInt());
        verify(ratingRepository).deleteById(anyInt());
    }

    /**
     * Vérifie que la méthode delete lance une exception quand l'ID n'existe pas.
     */
    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(ratingRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> ratingService.delete(anyInt()));
        verify(ratingRepository).existsById(anyInt());
    }
}