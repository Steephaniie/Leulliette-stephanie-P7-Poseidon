package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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
 * Contrôleur pour la gestion des évaluations (Rating)
 * Gère les opérations CRUD pour les évaluations
 */
@Controller
@RequiredArgsConstructor
public class RatingController {

    private final Logger logger = LoggerFactory.getLogger(RatingController.class);

    private final RatingService ratingService;


    /**
     * Affiche la liste de toutes les évaluations
     *
     * @param model     Le modèle pour la vue
     * @param principal L'utilisateur connecté
     * @return La vue de la liste des évaluations
     */
    @RequestMapping("/rating/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("ratings", ratingService.getAll());
        return "rating/list";
    }

    /**
     * Affiche le formulaire d'ajout d'une nouvelle évaluation
     *
     * @param model Le modèle pour la vue
     * @return La vue du formulaire d'ajout
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    /**
     * Valide et enregistre une nouvelle évaluation
     *
     * @param rating L'évaluation à valider
     * @param result Le résultat de la validation
     * @param model  Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        logger.info("Request to add Rating: {}", rating);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "rating/add";
        }

        ratingService.save(rating);
        logger.info("New rating added : {}", rating);
        return "redirect:/rating/list";
    }

    /**
     * Affiche le formulaire de modification d'une évaluation
     *
     * @param id    L'identifiant de l'évaluation à modifier
     * @param model Le modèle pour la vue
     * @return La vue du formulaire de modification
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Met à jour une évaluation existante
     *
     * @param id     L'identifiant de l'évaluation à modifier
     * @param rating L'évaluation avec les nouvelles données
     * @param result Le résultat de la validation
     * @param model  Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {

        logger.info("Request to update Rating: {}", rating);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "rating/update";
        }

        ratingService.update(id, rating);
        logger.info("Rating updated : {}", id);
        return "redirect:/rating/list";
    }

    /**
     * Supprime une évaluation
     *
     * @param id    L'identifiant de l'évaluation à supprimer
     * @param model Le modèle pour la vue
     * @return Redirection vers la liste des évaluations
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete Rating with id: {}", id);
        ratingService.delete(id);
        logger.info("Rating deleted : {}", id);
        return "redirect:/rating/list";
    }
}
