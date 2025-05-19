package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Classe de tests pour la fonctionnalité RuleName.
 * Teste les opérations CRUD sur l'entité RuleName.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	/**
	 * Teste les opérations CRUD (Create, Read, Update, Delete) pour l'entité RuleName.
	 * Vérifie la création, la mise à jour, la lecture et la suppression d'une règle.
	 */
	@Test
	public void ruleTest() {
		// Création d'une nouvelle règle avec des valeurs de test
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Sauvegarde de la règle dans la base de données
		rule = ruleNameRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Mise à jour du nom de la règle
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Recherche de toutes les règles
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Suppression de la règle et vérification
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
