package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
 * Contrôleur pour la gestion des règles (RuleName)
 * Gère les opérations CRUD pour les règles
 */
@Controller
@RequiredArgsConstructor
public class RuleNameController {

    public final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    private final RuleNameService ruleNameService;

    /**
     * Affiche la liste de toutes les règles
     *
     * @param model     Le modèle pour la vue
     * @param principal L'utilisateur connecté
     * @return La vue de la liste des règles
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("ruleNames", ruleNameService.getAll());
        return "ruleName/list";
    }

    /**
     * Affiche le formulaire d'ajout d'une nouvelle règle
     *
     * @param model Le modèle pour la vue
     * @return La vue du formulaire d'ajout
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    /**
     * Valide et enregistre une nouvelle règle
     *
     * @param ruleName La règle à valider
     * @param result   Le résultat de la validation
     * @param model    Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

        logger.info("Request to add RuleName : {}", ruleName);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "ruleName/add";
        }

        ruleNameService.save(ruleName);
        logger.info("New ruleName added : {}", ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Affiche le formulaire de modification d'une règle
     *
     * @param id    L'identifiant de la règle à modifier
     * @param model Le modèle pour la vue
     * @return La vue du formulaire de modification
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * Met à jour une règle existante
     *
     * @param id       L'identifiant de la règle à modifier
     * @param ruleName La règle avec les nouvelles données
     * @param result   Le résultat de la validation
     * @param model    Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {

        logger.info("Request to update RuleName : {}", ruleName);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "ruleName/update";
        }

        ruleNameService.update(id, ruleName);
        logger.info("RuleName updated : {}", id);
        return "redirect:/ruleName/list";
    }

    /**
     * Supprime une règle
     *
     * @param id    L'identifiant de la règle à supprimer
     * @param model Le modèle pour la vue
     * @return Redirection vers la liste des règles
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete ruleName : {}", id);
        ruleNameService.delete(id);
        logger.info("RuleName deleted : {}", id);
        return "redirect:/ruleName/list";
    }
}
