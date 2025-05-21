package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des opérations de persistance des trades.
 * Fournit les méthodes CRUD de base pour l'entité Trade.
 */
@Repository
/**
 * Cette annotation indique que cette interface est un repository Spring
 * et sera utilisée pour la persistance des données.
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
