package com.nnk.springboot.service;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des utilisateurs
 * Fournit les opérations CRUD et autres fonctionnalités liées aux utilisateurs
 */
@Service
@RequiredArgsConstructor
public class UserService  implements CrudService<User>{

    public final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    /**
     * Récupère tous les utilisateurs
     *
     * @return Liste de tous les utilisateurs
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Sauvegarde un nouvel utilisateur
     *
     * @param user L'utilisateur à sauvegarder
     * @return L'utilisateur sauvegardé avec son ID généré
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Récupère un utilisateur par son ID
     *
     * @param id L'ID de l'utilisateur à rechercher
     * @return L'utilisateur trouvé
     * @throws EntityNotFoundException si l'utilisateur n'est pas trouvé
     */
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Utilisateur non trouvé avec l'id : {}", id);
                    return new EntityNotFoundException("Utilisateur spécifié non trouvé");
                });
    }

    /**
     * Met à jour les informations d'un utilisateur existant
     *
     * @param id L'ID de l'utilisateur à mettre à jour
     * @param user Les nouvelles données de l'utilisateur
     * @return L'utilisateur mis à jour
     */
    public User update(int id, User user) {
        // Récupération de l'utilisateur existant
        User userToUpdate = getById(id);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setFullname(user.getFullname());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setPassword(user.getPassword());
    
        // Sauvegarde des modifications
        return userRepository.save(userToUpdate);
    }

    public void delete(int id) {

        if (!userRepository.existsById(id)) {
            logger.warn("UserEntity with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified user not found");
        }

        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String defaultUsername) {
        return userRepository.findByUsername(defaultUsername);
    }
}
