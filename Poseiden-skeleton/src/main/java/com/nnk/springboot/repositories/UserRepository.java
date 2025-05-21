package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour la gestion des utilisateurs
 * Fournit les opérations CRUD standard via l'extension de JpaRepository
 * ainsi que des fonctionnalités de recherche avancées via JpaSpecificationExecutor
 *
 * @see User
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Recherche un utilisateur par son nom d'utilisateur
     *
     * @param username le nom d'utilisateur à rechercher
     * @return Optional contenant l'utilisateur s'il existe, vide sinon
     */
    Optional<User> findByUsername(String username);
}
