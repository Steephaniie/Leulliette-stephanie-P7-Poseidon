package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


/**
 * Contrôleur pour la gestion des trades (transactions commerciales)
 * Gère les opérations CRUD sur les trades
 */
@Controller
@RequiredArgsConstructor
public class TradeController {

    /**
     * Logger pour la traçabilité des opérations
     */
    public final Logger logger = LoggerFactory.getLogger(TradeController.class);

    /**
     * Service de gestion des trades
     */
    private final TradeService tradeService;

    /**
     * Affiche la liste de tous les trades
     *
     * @param model     Le modèle pour la vue
     * @param principal L'utilisateur connecté
     * @return La vue de la liste des trades
     */
    @RequestMapping("/trade/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouveau trade
     *
     * @param model     Le modèle pour la vue
     * @param principal L'utilisateur connecté
     * @return La vue du formulaire d'ajout
     */
    @GetMapping("/trade/add")
    public String addUser(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    /**
     * Valide et enregistre un nouveau trade
     *
     * @param trade  Le trade à valider et enregistrer
     * @param result Le résultat de la validation
     * @param model  Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.info("Request to add Trade: {}", trade);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "trade/add";
        }

        Trade savedTrade = tradeService.save(trade);
        logger.info("New trade added : {}", savedTrade);
        return "redirect:/trade/list";
    }

    /**
     * Affiche le formulaire de modification d'un trade
     *
     * @param id    L'identifiant du trade à modifier
     * @param model Le modèle pour la vue
     * @return La vue du formulaire de modification
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Met à jour un trade existant
     *
     * @param id     L'identifiant du trade à modifier
     * @param trade  Le trade avec les nouvelles données
     * @param result Le résultat de la validation
     * @param model  Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        logger.info("Request to update TradeEntity: {}", trade);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "trade/update";
        }

        tradeService.update(id, trade);
        logger.info("TradeEntity updated : {}", trade);
        return "redirect:/trade/list";
    }

    /**
     * Supprime un trade existant
     *
     * @param id    L'identifiant du trade à supprimer
     * @param model Le modèle pour la vue
     * @return Redirection vers la liste des trades
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete TradeEntity: {}", id);
        tradeService.delete(id);
        logger.info("TradeEntity deleted : {}", id);
        return "redirect:/trade/list";
    }
}
