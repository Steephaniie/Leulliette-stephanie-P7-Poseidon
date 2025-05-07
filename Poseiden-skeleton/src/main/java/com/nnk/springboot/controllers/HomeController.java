package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur principal pour la gestion des pages d'accueil
 * Gère les redirections vers les pages d'accueil publiques et administrateur
 */
@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Affiche la page d'accueil publique
     *
     * @param model Le modèle pour la vue
     * @return La vue de la page d'accueil
     */
    @RequestMapping("/")
    public String home(Model model) {
        logger.info("Accès à la page d'accueil publique");
        return "home";
    }

    /**
     * Redirige vers la page d'accueil administrateur
     * Redirige vers la liste des enchères
     *
     * @param model Le modèle pour la vue
     * @return Redirection vers la liste des enchères
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        logger.info("Redirection vers la page d'accueil administrateur");
        return "redirect:/bidList/list";
    }

}
