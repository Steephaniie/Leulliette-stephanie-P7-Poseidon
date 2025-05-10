package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service pour la gestion des enchères (BidList)
 * Fournit les opérations CRUD pour les enchères
 */
@Service
@RequiredArgsConstructor
public class BidListService {

    public final Logger logger = LoggerFactory.getLogger(BidListService.class);

    private final BidListRepository bidListRepository;

    /**
     * Récupère toutes les enchères existantes
     *
     * @return Liste de toutes les enchères
     */
    public List<BidList> getAll() {
        return bidListRepository.findAll();
    }

    /**
     * Enregistre une nouvelle enchère
     *
     * @param bid L'enchère à sauvegarder
     * @return L'enchère sauvegardée
     */
    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    /**
     * Récupère une enchère par son identifiant
     *
     * @param id Identifiant de l'enchère recherchée
     * @return L'enchère trouvée
     * @throws EntityNotFoundException si l'enchère n'existe pas
     */
    public BidList getById(int id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified bid not found : {}", id);
                    return new EntityNotFoundException("Specified bid not found");
                });
    }

    /**
     * Met à jour une enchère existante
     *
     * @param id  Identifiant de l'enchère à mettre à jour
     * @param bid Nouvelles données de l'enchère
     * @return L'enchère mise à jour
     * @throws EntityNotFoundException si l'enchère n'existe pas
     */
    public BidList update(int id, BidList bid) {
        // Récupère l'enchère existante
        BidList bidToUpdate = getById(id);

        // Met à jour les champs modifiables
        bidToUpdate.setAccount(bid.getAccount());
        bidToUpdate.setType(bid.getType());
        bidToUpdate.setBidQuantity(bid.getBidQuantity());

        return bidListRepository.save(bidToUpdate);
    }

    /**
     * Supprime une enchère
     *
     * @param id Identifiant de l'enchère à supprimer
     * @throws EntityNotFoundException si l'enchère n'existe pas
     */
    public void delete(int id) {
        // Vérifie l'existence de l'enchère avant la suppression
        if (!bidListRepository.existsById(id)) {
            logger.warn("Bid with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified bid not found");
        }
        bidListRepository.deleteById(id);
    }
}
