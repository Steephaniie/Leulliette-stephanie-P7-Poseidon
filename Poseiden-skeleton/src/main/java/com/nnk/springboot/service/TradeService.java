package com.nnk.springboot.service;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsable de la gestion des opérations commerciales (trades)
 * Fournit les méthodes CRUD pour manipuler les entités Trade
 */
@Service
@RequiredArgsConstructor
public class TradeService  implements CrudService<Trade>{

    public final Logger logger = LoggerFactory.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;

    /**
     * Récupère la liste de toutes les transactions
     *
     * @return Liste de toutes les transactions existantes
     */
    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }

    /**
     * Enregistre une nouvelle transaction
     *
     * @param trade La transaction à sauvegarder
     * @return La transaction sauvegardée avec son ID généré
     */
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * Récupère une transaction par son identifiant
     *
     * @param id L'identifiant de la transaction recherchée
     * @return La transaction trouvée
     * @throws EntityNotFoundException si la transaction n'existe pas
     */
    public Trade getById(int id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified trade not found : {}", id);
                    return new EntityNotFoundException("Specified trade not found");
                });
    }

    /**
     * Met à jour une transaction existante
     *
     * @param id    L'identifiant de la transaction à mettre à jour
     * @param trade La transaction contenant les nouvelles données
     * @return La transaction mise à jour
     * @throws EntityNotFoundException si la transaction n'existe pas
     */
    public Trade update(int id, Trade trade) {
        // Récupère la transaction existante
        Trade tradeToUpdate = getById(id);

        // Met à jour les champs modifiables
        tradeToUpdate.setAccount(trade.getAccount());
        tradeToUpdate.setType(trade.getType());
        tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());

        // Sauvegarde les modifications
        return tradeRepository.save(tradeToUpdate);
    }

    /**
     * Supprime une transaction
     *
     * @param id L'identifiant de la transaction à supprimer
     * @throws EntityNotFoundException si la transaction n'existe pas
     */
    public void delete(int id) {
        // Vérifie l'existence de la transaction avant la suppression
        if (!tradeRepository.existsById(id)) {
            logger.warn("TradeEntity with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified trade not found");
        }

        tradeRepository.deleteById(id);
    }
}
