package com.nnk.springboot.service;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des règles (RuleName)
 * Gère les opérations CRUD pour les règles
 */
@Service
@RequiredArgsConstructor
public class RuleNameService {

    public final Logger logger = LoggerFactory.getLogger(RuleNameService.class);

    private final RuleNameRepository ruleNameRepository;

    /**
     * Récupère toutes les règles
     *
     * @return Liste de toutes les règles
     */
    public List<RuleName> getAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * Enregistre une nouvelle règle
     *
     * @param ruleName La règle à sauvegarder
     * @return La règle sauvegardée
     */
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Récupère une règle par son identifiant
     *
     * @param id L'identifiant de la règle
     * @return La règle trouvée
     * @throws EntityNotFoundException si la règle n'existe pas
     */
    public RuleName getById(int id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified rule name not found : {}", id);
                    return new EntityNotFoundException("Specified rule name not found");
                });
    }

    /**
     * Met à jour une règle existante
     *
     * @param id       L'identifiant de la règle à modifier
     * @param ruleName La règle avec les nouvelles données
     * @return La règle mise à jour
     * @throws EntityNotFoundException si la règle n'existe pas
     */
    public RuleName update(int id, RuleName ruleName) {
        // Recherche de la règle existante
        RuleName ruleNameToUpdate = getById(id);
        ruleNameToUpdate.setName(ruleName.getName());
        ruleNameToUpdate.setDescription(ruleName.getDescription());
        ruleNameToUpdate.setJson(ruleName.getJson());
        ruleNameToUpdate.setTemplate(ruleName.getTemplate());
        ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
        ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());

        return ruleNameRepository.save(ruleNameToUpdate);
    }

    /**
     * Supprime une règle
     *
     * @param id L'identifiant de la règle à supprimer
     * @throws EntityNotFoundException si la règle n'existe pas
     */
    public void delete(int id) {
        // Vérification de l'existence de la règle
        if (!ruleNameRepository.existsById(id)) {
            logger.warn("Rule name with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified rule name not found");
        }

        ruleNameRepository.deleteById(id);
    }
}
