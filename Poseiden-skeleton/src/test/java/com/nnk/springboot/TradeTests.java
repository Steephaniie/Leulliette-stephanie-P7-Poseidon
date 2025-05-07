package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Classe de test pour la gestion des opérations CRUD sur l'entité Trade.
 * Utilise Spring Runner et SpringBootTest pour l'intégration avec Spring.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeTests {

	/**
	 * Injection du repository Trade pour accéder aux opérations de base de données.
	 */
	@Autowired
	private TradeRepository tradeRepository;

	/**
	 * Teste les opérations CRUD pour l'entité Trade.
	 * Vérifie la création, la lecture, la mise à jour et la suppression d'un Trade.
	 */
	@Test
	public void tradeTest() {
		// Création d'un nouveau Trade
		Trade trade = new Trade("Trade Account", "Type");
		trade.setAccount("Trade Account");
		trade.setType("Type");
		// Sauvegarde dans la base de données
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getId());
		Assert.assertTrue(trade.getAccount().equals("Trade Account"));

		// Mise à jour du Trade
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Recherche de tous les Trades
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Suppression du Trade
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}
}
