package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des enchères (BidList)
 * Fournit les opérations CRUD de base pour l'entité BidList via Spring Data JPA
 * Utilise Integer comme type d'identifiant
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
