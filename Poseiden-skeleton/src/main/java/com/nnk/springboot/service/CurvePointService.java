package com.nnk.springboot.service;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des points de courbe (CurvePoint).
 * Fournit les opérations CRUD et la logique métier associée.
 */
@Service
@RequiredArgsConstructor
public class CurvePointService  implements CrudService<CurvePoint> {

    public final Logger logger = LoggerFactory.getLogger(CurvePointService.class);

    private final CurvePointRepository curvePointRepository;

    /**
     * Récupère tous les points de courbe existants.
     *
     * @return une liste contenant tous les points de courbe
     */
    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }


    /**
     * Sauvegarde un nouveau point de courbe.
     *
     * @param curvePoint le point de courbe à sauvegarder
     * @return le point de courbe sauvegardé avec son ID généré
     */
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }


    /**
     * Récupère un point de courbe par son ID.
     *
     * @param id l'identifiant du point de courbe recherché
     * @return le point de courbe correspondant à l'ID
     * @throws EntityNotFoundException si aucun point de courbe n'est trouvé avec cet ID
     */
    public CurvePoint getById(int id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Point de courbe non trouvé avec l'ID : {}", id);
                    return new EntityNotFoundException("Point de courbe spécifié non trouvé");
                });
    }

    /**
     * Met à jour un point de courbe existant.
     *
     * @param id         l'identifiant du point de courbe à mettre à jour
     * @param curvePoint le point de courbe avec les nouvelles valeurs
     * @return le point de courbe mis à jour
     * @throws EntityNotFoundException si aucun point de courbe n'est trouvé avec cet ID
     */
    public CurvePoint update(int id, CurvePoint curvePoint) {
        // Récupère le point de courbe existant
        CurvePoint curvePointToUpdate = getById(id);

        // Met à jour les champs avec les nouvelles valeurs
        curvePointToUpdate.setCurveId(curvePoint.getCurveId());
        curvePointToUpdate.setTerm(curvePoint.getTerm());
        curvePointToUpdate.setValue(curvePoint.getValue());

        return curvePointRepository.save(curvePointToUpdate);
    }

    /**
     * Supprime un point de courbe par son ID.
     *
     * @param id l'identifiant du point de courbe à supprimer
     * @throws EntityNotFoundException si aucun point de courbe n'est trouvé avec cet ID
     */
    public void delete(int id) {
        // Vérifie l'existence du point de courbe avant la suppression
        if (!curvePointRepository.existsById(id)) {
            logger.warn("Point de courbe avec l'ID {} non trouvé pour la suppression", id);
            throw new EntityNotFoundException("Point de courbe spécifié non trouvé");
        }

        curvePointRepository.deleteById(id);
    }
}
