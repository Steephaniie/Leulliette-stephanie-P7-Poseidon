package com.nnk.springboot.service;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des notations (Rating).
 * Fournit les opérations CRUD et la logique métier associée.
 */
@Service
@RequiredArgsConstructor
public class RatingService {

    /**
     * Logger pour tracer les opérations du service
     */
    public final Logger logger = LoggerFactory.getLogger(RatingService.class);

    /**
     * Repository pour accéder aux données des notations
     */
    private final RatingRepository ratingRepository;


    /**
     * Récupère toutes les notations existantes.
     *
     * @return une liste contenant toutes les notations
     */
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    /**
     * Sauvegarde une nouvelle notation.
     *
     * @param rating la notation à sauvegarder
     * @return la notation sauvegardée avec son ID généré
     */
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Récupère une notation par son ID.
     *
     * @param id l'identifiant de la notation recherchée
     * @return la notation correspondante à l'ID
     * @throws EntityNotFoundException si aucune notation n'est trouvée avec cet ID
     */
    public Rating getById(int id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified rating not found : {}", id);
                    return new EntityNotFoundException("Specified rating not found");
                });
    }


    /**
     * Met à jour une notation existante.
     *
     * @param id     l'identifiant de la notation à mettre à jour
     * @param rating la notation avec les nouvelles valeurs
     * @return la notation mise à jour
     * @throws EntityNotFoundException si aucune notation n'est trouvée avec cet ID
     */
    public Rating update(int id, Rating rating) {

        Rating ratingToUpdate = getById(id);
        ratingToUpdate.setMoodysRating(rating.getMoodysRating());
        ratingToUpdate.setSandPRating(rating.getSandPRating());
        ratingToUpdate.setFitchRating(rating.getFitchRating());
        ratingToUpdate.setOrderNumber(rating.getOrderNumber());

        if (!rating.equals(ratingToUpdate)) return ratingToUpdate;

        return ratingRepository.save(ratingToUpdate);
    }


    /**
     * Supprime une notation par son ID.
     *
     * @param id l'identifiant de la notation à supprimer
     * @throws EntityNotFoundException si aucune notation n'est trouvée avec cet ID
     */
    public void delete(int id) {

        if (!ratingRepository.existsById(id)) {
            logger.warn("RatingEntity with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified rating not found");
        }

        ratingRepository.deleteById(id);
    }
}
