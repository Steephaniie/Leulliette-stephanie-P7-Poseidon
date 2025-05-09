package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur pour la gestion des utilisateurs
 * Gère les opérations CRUD (Création, Lecture, Mise à jour, Suppression) des utilisateurs
 * et la sécurité des mots de passe
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Affiche la liste de tous les utilisateurs enregistrés
     *
     * @param model Le modèle pour la vue
     * @return La vue de la liste des utilisateurs
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouvel utilisateur
     *
     * @param model Le modèle pour la vue
     * @return La vue du formulaire d'ajout
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    /**
     * Valide et enregistre un nouvel utilisateur
     *
     * @param user   L'utilisateur à valider et enregistrer
     * @param result Le résultat de la validation
     * @param model  Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
    
        logger.info("Request to add User: {}", user);
    
        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "user/add";
        }
    
        // Chiffrement du mot de passe avec BCrypt avant l'enregistrement
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);

        logger.info("New user added : {}", user);
        return "redirect:/user/list";
    }

    /**
     * Affiche le formulaire de modification d'un utilisateur
     *
     * @param id    L'identifiant de l'utilisateur à modifier
     * @param model Le modèle pour la vue
     * @return La vue du formulaire de modification
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getById(id);
        // Réinitialisation du mot de passe pour des raisons de sécurité
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {

        logger.info("Request to update User: {}", user);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);

        logger.info("User updated : {}", id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.delete(id);
        return "redirect:/user/list";
    }
}
