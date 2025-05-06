package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des points de courbe
 * Fournit les opérations CRUD standard via l'extension de JpaRepository
 *
 * @see CurvePoint
 * @see JpaRepository
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
