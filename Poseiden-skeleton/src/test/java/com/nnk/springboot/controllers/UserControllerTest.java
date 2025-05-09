package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
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
 * Classe de test pour le contrôleur UserController.
 * Teste toutes les opérations CRUD sur les Users.
 */
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    /**
     * Teste l'affichage de la liste des Users.
     * Vérifie que la page est accessible et que la liste est bien transmise au modèle.
     */
    @Test
    @DisplayName("GET /user/list - success")
    @WithMockUser(username = "User", roles = "ADMIN")
    public void home_shouldReturnUserListView() throws Exception {
        when(userService.getAll()).thenReturn(List.of(mock(User.class)));

        mockMvc.perform(get("/user/list")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));

        verify(userService).getAll();
    }

    /**
     * Teste l'affichage du formulaire d'ajout d'un User.
     * Vérifie que la page est accessible et retourne la vue appropriée.
     */
    @Test
    @DisplayName("GET /user/add - success")
    @WithMockUser(username = "User", roles = "ADMIN")
    public void addUserForm_shouldReturnAddView() throws Exception {
        mockMvc.perform(get("/user/add")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    /**
     * Teste la validation d'un nouveau User valide.
     * Vérifie la redirection vers la liste après la sauvegarde.
     */
    @Test
    @DisplayName("POST /user/validate - success")
    @WithMockUser(username = "User", roles = "ADMIN")
    public void validate_shouldRedirectToUserList_whenValid() throws Exception {
        when(userService.save(any(User.class))).thenReturn(mock(User.class));

        mockMvc.perform(post("/user/validate")
                        .param("username", "Username")
                        .param("password", "Abc123@!")
                        .param("fullname", "Full Name")
                        .param("role", "Role")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).save(any(User.class));
    }

//    @Test
//    @DisplayName("POST /user/validate - failure")
//    @WithMockUser(username = "User", roles = "ADMIN")
//    public void validate_shouldReturnAddView_whenInvalid() throws Exception {
//        mockMvc.perform(post("/user/validate")
//                        .param("username", "")
//                        .param("password", "")
//                        .param("fullname", "")
//                        .param("role", "")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/add"));
//
//        verify(userService, never()).save(any(User.class));
//    }

    /**
     * Teste l'affichage du formulaire de mise à jour d'un User.
     * Vérifie que la page est accessible et que le User est bien transmis au modèle.
     */
    @Test
    @DisplayName("GET /user/update/{id} - success")
    @WithMockUser(username = "User", roles = "ADMIN")
    public void showUpdateForm_shouldReturnUpdateView() throws Exception {
        when(userService.getById(anyInt())).thenReturn(mock(User.class));

        mockMvc.perform(get("/user/update/" + 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));

        verify(userService).getById(anyInt());
    }

    /**
     * Teste la mise à jour d'un User avec des données valides.
     * Vérifie la redirection vers la liste après la mise à jour.
     */
    @Test
    @DisplayName("POST /user/update/{id} - success")
    @WithMockUser(username = "User", roles = "ADMIN")
    public void updateUser_shouldRedirectToUserList_whenValid() throws Exception {
        when(userService.save(any(User.class))).thenReturn(mock(User.class));

        mockMvc.perform(post("/user/update/" + 1)
                        .param("username", "Updated Username")
                        .param("password", "Abc123@!")
                        .param("fullname", "Updated Full Name")
                        .param("role", "Updated Role")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).save(any(User.class));
    }

//    @Test
//    @DisplayName("POST /user/update/{id} - failure")
//    @WithMockUser(username = "User", roles = "ADMIN")
//    public void updateUser_shouldReturnUpdateView_whenInvalid() throws Exception {
//        mockMvc.perform(post("/user/update/" + 1)
//                        .param("username", "")
//                        .param("password", "")
//                        .param("fullname", "")
//                        .param("role", "")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user/update"));
//
//        verify(userService, never()).save(any(User.class));
//    }

    /**
     * Teste la suppression d'un User.
     * Vérifie la redirection vers la liste après la suppression.
     */
    @Test
    @DisplayName("GET /user/delete/{id} - success")
    @WithMockUser(username = "User", roles = "ADMIN")
    public void deleteUser_shouldRedirectToUserList() throws Exception {
        doNothing().when(userService).delete(anyInt());

        mockMvc.perform(get("/user/delete/" + 1)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).delete(anyInt());
    }
}