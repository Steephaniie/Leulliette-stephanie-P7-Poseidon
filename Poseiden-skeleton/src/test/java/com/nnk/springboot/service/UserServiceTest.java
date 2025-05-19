package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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
 * Classe de tests unitaires pour le UserService.
 * Cette classe teste toutes les fonctionnalités CRUD du service utilisateur.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    /**
     * Teste la récupération de tous les utilisateurs.
     * Vérifie que la méthode getAll appelle correctement le repository.
     */
    @Test
    void getAll_returnsAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(mock(User.class), mock(User.class)));

        userService.getAll();

        verify(userRepository).findAll();
    }

    /**
     * Teste la sauvegarde d'un nouvel utilisateur.
     * Vérifie que la méthode save appelle correctement le repository.
     */
    @Test
    void save_savesAndReturnsUser() {
        when(userRepository.save(any(User.class))).thenReturn(mock(User.class));

        userService.save(mock(User.class));

        verify(userRepository).save(any(User.class));
    }

    /**
     * Teste la récupération d'un utilisateur par son ID existant.
     * Vérifie que la méthode getById retourne l'utilisateur correctement.
     */
    @Test
    void getById_existingId_returnsUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mock(User.class)));

        userService.getById(anyInt());

        verify(userRepository).findById(anyInt());
    }

    /**
     * Teste la tentative de récupération d'un utilisateur avec un ID inexistant.
     * Vérifie que la méthode getById lance une exception EntityNotFoundException.
     */
    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getById(anyInt()));
        verify(userRepository).findById(anyInt());
    }

    /**
     * Teste la mise à jour d'un utilisateur existant.
     * Vérifie que la méthode update modifie correctement l'utilisateur.
     */
    @Test
    void update_existingId_updatesAndReturnsUser() {
        User existingUser = new User();
        existingUser.setUsername("oldUsername");
        User newUser = new User();
        newUser.setUsername("newUsername");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        userService.update(1, newUser);

        verify(userRepository).findById(1);
        verify(userRepository).save(existingUser);
    }


    /**
     * Teste la suppression d'un utilisateur existant.
     * Vérifie que la méthode delete supprime correctement l'utilisateur.
     */
    @Test
    void delete_existingId_deletesUser() {
        when(userRepository.existsById(anyInt())).thenReturn(true);

        userService.delete(anyInt());

        verify(userRepository).existsById(anyInt());
        verify(userRepository).deleteById(anyInt());
    }

    /**
     * Teste la tentative de suppression d'un utilisateur avec un ID inexistant.
     * Vérifie que la méthode delete lance une exception EntityNotFoundException.
     */
    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(userRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.delete(anyInt()));
        verify(userRepository).existsById(anyInt());
    }
}