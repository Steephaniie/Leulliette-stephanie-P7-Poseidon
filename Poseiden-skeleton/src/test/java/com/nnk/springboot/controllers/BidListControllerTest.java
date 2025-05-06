package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
 * Classe de test pour le contrôleur BidListController.
 * Teste toutes les opérations CRUD sur les BidList.
 */
@AutoConfigureMockMvc
@SpringBootTest
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;


    /**
     * Teste l'affichage de la liste des BidList.
     * Vérifie que la page est accessible et que la liste est bien transmise au modèle.
     */
    @Test
    @DisplayName("GET /bidList/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnBidListView() throws Exception {

        when(bidListService.getAll()).thenReturn(List.of(mock(BidList.class)));

        mockMvc.perform(get("/bidList/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(bidListService).getAll();
    }

    /**
     * Teste l'affichage du formulaire d'ajout d'une BidList.
     * Vérifie que la page est accessible et retourne la vue appropriée.
     */
    @Test
    @DisplayName("GET /bidList/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addBidForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/bidList/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    /**
     * Teste la validation d'une nouvelle BidList valide.
     * Vérifie la redirection vers la liste après la sauvegarde.
     */
    @Test
    @DisplayName("POST /bidList/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToBidList_whenValid() throws Exception {

        when(bidListService.save(any(BidList.class))).thenReturn(mock(BidList.class));

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("bidQuantity", "10.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).save(any(BidList.class));
    }

    /**
     * Teste la validation d'une nouvelle BidList invalide.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /bidList/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(bidListService, never()).save(any(BidList.class));
    }

    /**
     * Teste l'affichage du formulaire de mise à jour d'une BidList.
     * Vérifie que la page est accessible et que la BidList est bien transmise au modèle.
     */
    @Test
    @DisplayName("GET /bidList/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(bidListService.getById(anyInt())).thenReturn(mock(BidList.class));

        mockMvc.perform(get("/bidList/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));

        verify(bidListService).getById(anyInt());
    }

    /**
     * Teste la mise à jour d'une BidList avec des données valides.
     * Vérifie la redirection vers la liste après la mise à jour.
     */
    @Test
    @DisplayName("POST /bidList/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateBid_shouldRedirectToBidList_whenValid() throws Exception {
        when(bidListService.update(anyInt(), any(BidList.class))).thenReturn(mock(BidList.class));

        mockMvc.perform(post("/bidList/update/" + 1)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("bidQuantity", "20.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).update(anyInt(), any(BidList.class));
    }

    /**
     * Teste la mise à jour d'une BidList avec des données invalides.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /bidList/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateBid_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/bidList/update/" + 1)
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

        verify(bidListService, never()).update(anyInt(), any(BidList.class));
    }

    /**
     * Teste la suppression d'une BidList.
     * Vérifie la redirection vers la liste après la suppression.
     */
    @Test
    @DisplayName("GET /bidList/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteBid_shouldRedirectToBidList() throws Exception {
        doNothing().when(bidListService).delete(anyInt());

        mockMvc.perform(get("/bidList/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).delete(anyInt());
    }
}