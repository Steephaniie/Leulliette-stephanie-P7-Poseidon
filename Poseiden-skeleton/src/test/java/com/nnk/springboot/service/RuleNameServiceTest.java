package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
 * Classe de tests pour RuleNameService.
 * Cette classe teste les différentes fonctionnalités du service de gestion des règles.
 */
@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;


    /**
     * Teste la récupération de toutes les règles.
     * Vérifie que la méthode getAll retourne correctement la liste des règles.
     */
    @Test
    void getAll_returnsAllRuleNames() {
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(mock(RuleName.class), mock(RuleName.class)));

        ruleNameService.getAll();

        verify(ruleNameRepository).findAll();
    }


    /**
     * Teste la sauvegarde d'une nouvelle règle.
     * Vérifie que la méthode save enregistre correctement une règle.
     */
    @Test
    void save_savesAndReturnsRuleName() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(mock(RuleName.class));

        ruleNameService.save(mock(RuleName.class));

        verify(ruleNameRepository).save(any(RuleName.class));
    }


    /**
     * Teste la récupération d'une règle par son ID existant.
     * Vérifie que la méthode getById retourne la règle correspondante.
     */
    @Test
    void getById_existingId_returnsRuleName() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(mock(RuleName.class)));

        ruleNameService.getById(anyInt());

        verify(ruleNameRepository).findById(anyInt());
    }


    /**
     * Teste la récupération d'une règle avec un ID inexistant.
     * Vérifie que la méthode getById lance une exception EntityNotFoundException.
     */
    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ruleNameService.getById(anyInt()));
        verify(ruleNameRepository).findById(anyInt());
    }


    /**
     * Teste la mise à jour d'une règle existante.
     * Vérifie que la méthode update modifie correctement la règle.
     */
    @Test
    void update_existingId_updatesAndReturnsRuleName() {
        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("oldName");
        RuleName newRuleName = new RuleName();
        newRuleName.setName("newName");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(existingRuleName));
        when(ruleNameRepository.save(existingRuleName)).thenReturn(existingRuleName);

        ruleNameService.update(1, newRuleName);

        verify(ruleNameRepository).findById(1);
        verify(ruleNameRepository).save(existingRuleName);
    }


    /**
     * Teste la suppression d'une règle existante.
     * Vérifie que la méthode delete supprime correctement la règle.
     */
    @Test
    void delete_existingId_deletesRuleName() {
        when(ruleNameRepository.existsById(anyInt())).thenReturn(true);

        ruleNameService.delete(anyInt());

        verify(ruleNameRepository).existsById(anyInt());
        verify(ruleNameRepository).deleteById(anyInt());
    }


    /**
     * Teste la suppression d'une règle avec un ID inexistant.
     * Vérifie que la méthode delete lance une exception EntityNotFoundException.
     */
    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(ruleNameRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> ruleNameService.delete(anyInt()));
        verify(ruleNameRepository).existsById(anyInt());
    }
}