package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
 * Classe de test pour le contrôleur RuleNameController.
 * Teste toutes les opérations CRUD sur les RuleName.
 */
@AutoConfigureMockMvc
@SpringBootTest
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    /**
     * Teste l'affichage de la liste des RuleName.
     * Vérifie que la page est accessible et que la liste est bien transmise au modèle.
     */
    @Test
    @DisplayName("GET /ruleName/list - success")
    @WithMockUser(username = "User", roles = "USER")
    public void home_shouldReturnRuleNameListView() throws Exception {
        when(ruleNameService.getAll()).thenReturn(List.of(mock(RuleName.class)));

        mockMvc.perform(get("/ruleName/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"));

        verify(ruleNameService).getAll();
    }

    /**
     * Teste l'affichage du formulaire d'ajout d'un RuleName.
     * Vérifie que la page est accessible et retourne la vue appropriée.
     */
    @Test
    @DisplayName("GET /ruleName/add - success")
    @WithMockUser(username = "User", roles = "USER")
    public void addRuleForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/ruleName/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    /**
     * Teste la validation d'un nouveau RuleName valide.
     * Vérifie la redirection vers la liste après la sauvegarde.
     */
    @Test
    @DisplayName("POST /ruleName/validate - success")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldRedirectToRuleNameList_whenValid() throws Exception {
        when(ruleNameService.save(any(RuleName.class))).thenReturn(mock(RuleName.class));

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Rule Name")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SQL String")
                        .param("sqlPart", "SQL Part")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).save(any(RuleName.class));
    }

    /**
     * Teste la validation d'un nouveau RuleName invalide.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /ruleName/validate - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void validate_shouldReturnAddView_whenInvalid() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        verify(ruleNameService, never()).save(any(RuleName.class));
    }

    /**
     * Teste l'affichage du formulaire de mise à jour d'un RuleName.
     * Vérifie que la page est accessible et que le RuleName est bien transmis au modèle.
     */
    @Test
    @DisplayName("GET /ruleName/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(ruleNameService.getById(anyInt())).thenReturn(mock(RuleName.class));

        mockMvc.perform(get("/ruleName/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));

        verify(ruleNameService).getById(anyInt());
    }

    /**
     * Teste la mise à jour d'un RuleName avec des données valides.
     * Vérifie la redirection vers la liste après la mise à jour.
     */
    @Test
    @DisplayName("POST /ruleName/update/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRuleName_shouldRedirectToRuleNameList_whenValid() throws Exception {
        when(ruleNameService.update(anyInt(), any(RuleName.class))).thenReturn(mock(RuleName.class));

        mockMvc.perform(post("/ruleName/update/" + 1)
                        .param("name", "Updated Rule Name")
                        .param("description", "Updated Description")
                        .param("json", "Updated Json")
                        .param("template", "Updated Template")
                        .param("sqlStr", "Updated SQL String")
                        .param("sqlPart", "Updated SQL Part")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).update(anyInt(), any(RuleName.class));
    }

    /**
     * Teste la mise à jour d'un RuleName avec des données invalides.
     * Vérifie que l'utilisateur reste sur le formulaire en cas d'erreur.
     */
    @Test
    @DisplayName("POST /ruleName/update/{id} - failure")
    @WithMockUser(username = "User", roles = "USER")
    public void updateRuleName_shouldReturnUpdateView_whenInvalid() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + 1)
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));

        verify(ruleNameService, never()).update(anyInt(), any(RuleName.class));
    }

    /**
     * Teste la suppression d'un RuleName.
     * Vérifie la redirection vers la liste après la suppression.
     */
    @Test
    @DisplayName("GET /ruleName/delete/{id} - success")
    @WithMockUser(username = "User", roles = "USER")
    public void deleteRuleName_shouldRedirectToRuleNameList() throws Exception {
        doNothing().when(ruleNameService).delete(anyInt());

        mockMvc.perform(get("/ruleName/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).delete(anyInt());
    }
}