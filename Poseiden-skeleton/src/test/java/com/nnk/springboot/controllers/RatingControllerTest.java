package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Classe de test pour le contrôleur RatingController.
 * Teste toutes les opérations CRUD sur les Rating.
 */
@AutoConfigureMockMvc
@SpringBootTest
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;


    /**
     * Teste l'affichage de la liste des Rating.
     * Vérifie que la page est accessible et que la liste est bien transmise au modèle.
     */
    @Test
    @DisplayName("GET /rating/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnRatingListView() throws Exception {

        when(ratingService.getAll()).thenReturn(List.of(mock(Rating.class)));

        mockMvc.perform(get("/rating/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

        verify(ratingService).getAll();
    }


    /**
     * Teste l'affichage du formulaire d'ajout d'un Rating.
     * Vérifie que la page est accessible et retourne la vue appropriée.
     */
    @Test
    @DisplayName("GET /rating/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addRatingForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/rating/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }


    /**
     * Teste la validation d'un nouveau Rating valide.
     * Vérifie la redirection vers la liste après la sauvegarde.
     */
    @Test
    @DisplayName("POST /rating/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToRatingList_whenValid() throws Exception {

        when(ratingService.save(any(Rating.class))).thenReturn(mock(Rating.class));

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Moodys Rating")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch Rating")
                        .param("orderNumber", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).save(any(Rating.class));
    }


    /**
     * Teste la validation d'un nouveau Rating invalide.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /rating/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(ratingService, never()).save(any(Rating.class));
    }


    /**
     * Teste l'affichage du formulaire de mise à jour d'un Rating.
     * Vérifie que la page est accessible et que le Rating est bien transmis au modèle.
     */
    @Test
    @DisplayName("GET /rating/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(ratingService.getById(anyInt())).thenReturn(mock(Rating.class));

        mockMvc.perform(get("/rating/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));

        verify(ratingService).getById(anyInt());
    }


    /**
     * Teste la mise à jour d'un Rating avec des données valides.
     * Vérifie la redirection vers la liste après la mise à jour.
     */
    @Test
    @DisplayName("POST /rating/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRating_shouldRedirectToRatingList_whenValid() throws Exception {

        when(ratingService.update(anyInt(), any(Rating.class))).thenReturn(mock(Rating.class));

        mockMvc.perform(post("/rating/update/" + 1)
                        .param("moodysRating", "Updated Moodys Rating")
                        .param("sandPRating", "Updated Sand PRating")
                        .param("fitchRating", "Updated Fitch Rating")
                        .param("orderNumber", "20")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).update(anyInt(), any(Rating.class));
    }


    /**
     * Teste la mise à jour d'un Rating avec des données invalides.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /rating/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRating_shouldReturnUpdateView_whenInvalid() throws Exception {

        mockMvc.perform(post("/rating/update/" + 1)
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

        verify(ratingService, never()).update(anyInt(), any(Rating.class));
    }


    /**
     * Teste la suppression d'un Rating.
     * Vérifie la redirection vers la liste après la suppression.
     */
    @Test
    @DisplayName("GET /rating/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteRating_shouldRedirectToRatingList() throws Exception {

        doNothing().when(ratingService).delete(anyInt());

        mockMvc.perform(get("/rating/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).delete(anyInt());
    }
}