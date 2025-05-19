package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
 * Classe de tests unitaires pour BidListService.
 * Cette classe teste toutes les fonctionnalités du service de gestion des enchères.
 */
@ExtendWith(MockitoExtension.class)
class BidListServiceTest {

    // Repository mock pour simuler les opérations de base de données
    @Mock
    private BidListRepository bidListRepository;

    // Service à tester avec injection des mocks
    @InjectMocks
    private BidListService bidListService;


    /**
     * Teste la récupération de toutes les enchères.
     * Vérifie que la méthode getAll appelle correctement le repository.
     */
    @Test
    void getAll_returnsAllBids() {
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(mock(BidList.class), mock(BidList.class)));

        bidListService.getAll();

        verify(bidListRepository).findAll();
    }


    /**
     * Teste la sauvegarde d'une enchère.
     * Vérifie que la méthode save appelle correctement le repository.
     */
    @Test
    void save_savesAndReturnsBid() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(mock(BidList.class));

        bidListService.save(mock(BidList.class));

        verify(bidListRepository).save(any(BidList.class));
    }


    /**
     * Teste la récupération d'une enchère par son ID quand elle existe.
     * Vérifie que la méthode getById retourne l'enchère correctement.
     */
    @Test
    void getById_existingId_returnsBid() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(mock(BidList.class)));

        bidListService.getById(anyInt());

        verify(bidListRepository).findById(anyInt());
    }


    /**
     * Teste la récupération d'une enchère avec un ID inexistant.
     * Vérifie que la méthode getById lance une exception EntityNotFoundException.
     */
    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bidListService.getById(anyInt()));
        verify(bidListRepository).findById(anyInt());
    }


    /**
     * Teste la mise à jour d'une enchère existante.
     * Vérifie que la méthode update modifie correctement l'enchère.
     */
    @Test
    void update_existingId_updatesAndReturnsBid() {
        BidList existingBid = new BidList();
        existingBid.setAccount("oldAccount");
        BidList newBid = new BidList();
        newBid.setAccount("newAccount");

        when(bidListRepository.findById(1)).thenReturn(Optional.of(existingBid));
        when(bidListRepository.save(existingBid)).thenReturn(existingBid);

        bidListService.update(1, newBid);

        verify(bidListRepository).findById(1);
        verify(bidListRepository).save(existingBid);
    }


    /**
     * Teste la suppression d'une enchère existante.
     * Vérifie que la méthode delete supprime correctement l'enchère.
     */
    @Test
    void delete_existingId_deletesBid() {
        when(bidListRepository.existsById(anyInt())).thenReturn(true);

        bidListService.delete(anyInt());

        verify(bidListRepository).existsById(anyInt());
        verify(bidListRepository).deleteById(anyInt());
    }


    /**
     * Teste la suppression d'une enchère avec un ID inexistant.
     * Vérifie que la méthode delete lance une exception EntityNotFoundException.
     */
    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(bidListRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bidListService.delete(anyInt()));
        verify(bidListRepository).existsById(anyInt());
    }
}