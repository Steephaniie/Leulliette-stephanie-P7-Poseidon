package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Classe de test pour vérifier les opérations CRUD sur l'entité CurvePoint.
 * Utilise Spring Runner pour l'exécution des tests avec le contexte Spring.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {

	/**
	 * Repository pour gérer les opérations de persistance des CurvePoint.
	 * Injecté automatiquement par Spring.
	 */
	@Autowired
	private CurvePointRepository curvePointRepository;

	/**
	 * Teste l'ensemble des opérations CRUD sur l'entité CurvePoint.
	 * Vérifie la création, la lecture, la mise à jour et la suppression.
	 */
	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Teste la sauvegarde d'un nouveau CurvePoint
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertNotNull(curvePoint.getId());
		Assert.assertTrue(curvePoint.getCurveId() == 10);

		// Teste la mise à jour d'un CurvePoint existant
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertTrue(curvePoint.getCurveId() == 20);

		// Teste la recherche de tous les CurvePoint
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Teste la suppression d'un CurvePoint
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assert.assertFalse(curvePointList.isPresent());
	}

}
