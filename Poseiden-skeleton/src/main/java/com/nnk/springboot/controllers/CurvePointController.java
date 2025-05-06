package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
 * Contrôleur pour la gestion des points de courbe
 * Gère les opérations CRUD pour les points de courbe
 */
@Controller
@RequiredArgsConstructor
public class CurvePointController {

    public final Logger logger = LoggerFactory.getLogger(CurvePointController.class);

    private final CurvePointService curveService;


    /**
     * Affiche la liste de tous les points de courbe
     *
     * @param model     Le modèle pour la vue
     * @param principal L'utilisateur connecté
     * @return La vue de la liste des points de courbe
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("curvePoints", curveService.getAll());
        return "curvePoint/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouveau point de courbe
     *
     * @param model Le modèle pour la vue
     * @return La vue du formulaire d'ajout
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    /**
     * Valide et enregistre un nouveau point de courbe
     *
     * @param curvePoint Le point de courbe à valider
     * @param result     Le résultat de la validation
     * @param model      Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        logger.info("Request to add CurvePoint: {}", curvePoint);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "curvePoint/add";
        }

        curveService.save(curvePoint);
        logger.info("New curve point added : {}", curvePoint);
        return "redirect:/curvePoint/list";
    }


    /**
     * Affiche le formulaire de modification d'un point de courbe
     *
     * @param id    L'identifiant du point de courbe à modifier
     * @param model Le modèle pour la vue
     * @return La vue du formulaire de modification
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curveService.getById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }


    /**
     * Met à jour un point de courbe existant
     *
     * @param id         L'identifiant du point de courbe à modifier
     * @param curvePoint Le point de courbe avec les nouvelles données
     * @param result     Le résultat de la validation
     * @param model      Le modèle pour la vue
     * @return Redirection vers la liste ou retour au formulaire si erreurs
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                              BindingResult result, Model model) {
        logger.info("Request to update curve point : {}", curvePoint);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "curvePoint/update";
        }

        curveService.update(id, curvePoint);
        logger.info("Curve point updated : {}", curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Supprime un point de courbe
     *
     * @param id    L'identifiant du point de courbe à supprimer
     * @param model Le modèle pour la vue
     * @return Redirection vers la liste des points de courbe
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete curve point : {}", id);
        curveService.delete(id);
        logger.info("Curve point deleted : {}", id);
        return "redirect:/curvePoint/list";
    }
}
