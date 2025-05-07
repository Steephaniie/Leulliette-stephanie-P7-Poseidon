package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des notations (Rating)
 * Fournit les opérations CRUD et les méthodes de requête personnalisées pour l'entité Rating
 */
@Repository // Indique que cette interface est un repository Spring Data JPA
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
