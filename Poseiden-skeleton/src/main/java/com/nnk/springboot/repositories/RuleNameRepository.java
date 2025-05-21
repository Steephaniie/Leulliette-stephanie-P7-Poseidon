package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des règles métier dans l'application.
 * Fournit les opérations CRUD standard pour l'entité RuleName via l'extension de JpaRepository.
 *
 * @see RuleName
 * @see JpaRepository
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
