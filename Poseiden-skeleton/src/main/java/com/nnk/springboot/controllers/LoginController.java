package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Contrôleur pour la gestion de l'authentification et des accès
 * Gère les opérations liées à la connexion et aux erreurs d'autorisation
 */
@Controller
@RequestMapping("app")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;


    /**
     * Affiche la page de connexion
     *
     * @return La vue de la page de connexion
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Affiche la liste de tous les utilisateurs
     * Accessible uniquement aux utilisateurs authentifiés
     *
     * @return La vue contenant la liste des utilisateurs
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Affiche la page d'erreur 403 (Accès refusé)
     *
     * @param principal L'utilisateur connecté
     * @return La vue de la page d'erreur 403
     */
    @GetMapping("error")
    public ModelAndView error(Principal principal) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("username", principal.getName());
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
