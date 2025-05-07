package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Classe de test pour vérifier le bon fonctionnement des opérations CRUD
 * sur les entités Rating via le repository.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	/**
	 * Teste les opérations CRUD sur l'entité Rating :
	 * - Création d'un nouveau rating
	 * - Mise à jour du rating
	 * - Recherche des ratings
	 * - Suppression d'un rating
	 */
	@Test
	public void ratingTest() {
		// Création d'un nouveau rating pour le test
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Sauvegarde du rating et vérification de la création
		rating = ratingRepository.save(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertTrue(rating.getOrderNumber() == 10);

		// Mise à jour du numéro de commande et vérification
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		Assert.assertTrue(rating.getOrderNumber() == 20);

		// Recherche de tous les ratings et vérification qu'il en existe au moins un
		List<Rating> listResult = ratingRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Suppression du rating et vérification qu'il n'existe plus
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}
}
