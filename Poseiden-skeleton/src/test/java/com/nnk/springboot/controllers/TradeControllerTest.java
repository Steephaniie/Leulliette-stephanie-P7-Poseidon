package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
 * Classe de test pour le contrôleur TradeController.
 * Teste toutes les opérations CRUD sur les Trades.
 */
@AutoConfigureMockMvc
@SpringBootTest
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    /**
     * Teste l'affichage de la liste des Trades.
     * Vérifie que la page est accessible et que la liste est bien transmise au modèle.
     */
    @Test
    @DisplayName("GET /trade/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnTradeListView() throws Exception {
        when(tradeService.getAll()).thenReturn(List.of(mock(Trade.class)));

        mockMvc.perform(get("/trade/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

        verify(tradeService).getAll();
    }

    /**
     * Teste l'affichage du formulaire d'ajout d'un Trade.
     * Vérifie que la page est accessible et retourne la vue appropriée.
     */
    @Test
    @DisplayName("GET /trade/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addTradeForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/trade/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    /**
     * Teste la validation d'un nouveau Trade valide.
     * Vérifie la redirection vers la liste après la sauvegarde.
     */
    @Test
    @DisplayName("POST /trade/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToTradeList_whenValid() throws Exception {
        when(tradeService.save(any(Trade.class))).thenReturn(mock(Trade.class));

        mockMvc.perform(post("/trade/validate")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).save(any(Trade.class));
    }

    /**
     * Teste la validation d'un nouveau Trade invalide.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /trade/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verify(tradeService, never()).save(any(Trade.class));
    }

    /**
     * Teste l'affichage du formulaire de mise à jour d'un Trade.
     * Vérifie que la page est accessible et que le Trade est bien transmis au modèle.
     */
    @Test
    @DisplayName("GET /trade/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(tradeService.getById(anyInt())).thenReturn(mock(Trade.class));

        mockMvc.perform(get("/trade/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));

        verify(tradeService).getById(anyInt());
    }

    /**
     * Teste la mise à jour d'un Trade avec des données valides.
     * Vérifie la redirection vers la liste après la mise à jour.
     */
    @Test
    @DisplayName("POST /trade/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateTrade_shouldRedirectToTradeList_whenValid() throws Exception {
        when(tradeService.update(anyInt(), any(Trade.class))).thenReturn(mock(Trade.class));

        mockMvc.perform(post("/trade/update/" + 1)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("buyQuantity", "20.0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).update(anyInt(), any(Trade.class));
    }

    /**
     * Teste la mise à jour d'un Trade avec des données invalides.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /trade/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateTrade_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/trade/update/" + 1)
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));

        verify(tradeService, never()).update(anyInt(), any(Trade.class));
    }

    /**
     * Teste la suppression d'un Trade.
     * Vérifie la redirection vers la liste après la suppression.
     */
    @Test
    @DisplayName("GET /trade/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteTrade_shouldRedirectToTradeList() throws Exception {
        doNothing().when(tradeService).delete(anyInt());

        mockMvc.perform(get("/trade/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).delete(anyInt());
    }
}