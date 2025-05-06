package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
 * Contrôleur pour la gestion des enchères (BidList)
 * Gère les opérations CRUD pour les enchères
 */
@Controller
@RequiredArgsConstructor
public class BidListController {

    public final Logger logger = LoggerFactory.getLogger(BidListController.class);

    private final BidListService bidListService;


    /**
     * Affiche la liste de toutes les enchères
     *
     * @param model     Le modèle pour la vue
     * @param principal L'utilisateur connecté
     * @return La vue de la liste des enchères
     */
    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal) {

        model.addAttribute("username", principal.getName());
        model.addAttribute("bidLists", bidListService.getAll());
        return "bidList/list";
    }

    /**
     * Affiche le formulaire d'ajout d'une nouvelle enchère
     *
     * @param model Le modèle pour la vue
     * @return La vue du formulaire d'ajout
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    /**
     * Valide et enregistre une nouvelle enchère
     *
     * @param bidList L'enchère à valider
     * @param result  Le résultat de la validation
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList,
            BindingResult result) {

        logger.info("Request to add new Bid : {}", bidList);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "bidList/add";
        }

        bidListService.save(bidList);
        logger.info("Bid added : {}", bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Affiche le formulaire de modification d'une enchère
     *
     * @param id    L'identifiant de l'enchère à modifier
     * @param model Le modèle pour la vue
     * @return La vue du formulaire de modification
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    /**
     * Met à jour une enchère existante
     *
     * @param id      L'identifiant de l'enchère à modifier
     * @param bidList L'enchère avec les nouvelles données
     * @param result  Le résultat de la validation
     * @param model   Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {

        logger.info("Request to update Bid : {}", bidList);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }

        bidListService.update(id, bidList);
        logger.info("Bid updated : {}", id);

        return "redirect:/bidList/list";
    }

    /**
     * Supprime une enchère
     *
     * @param id L'identifiant de l'enchère à supprimer
     * @return Redirection vers la liste des enchères
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        logger.info("Request to delete Bid : {}", id);
        bidListService.delete(id);
        logger.info("Bid deleted : {}", id);
        return "redirect:/bidList/list";
    }
}
