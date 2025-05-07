package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Classe de test pour le contrôleur de connexion.
 * Teste les fonctionnalités d'authentification des utilisateurs.
 */
@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {

    /**
     * Mock du contexte MVC pour simuler les requêtes HTTP.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Repository mock pour simuler les opérations utilisateur.
     */
    @MockBean
    private UserRepository repository;

    /**
     * Utilisateur de test.
     */
    private User user;


    /**
     * Configuration initiale avant chaque test.
     * Crée un utilisateur de test avec des identifiants valides.
     */
    @BeforeEach
    public void setUp() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user = new User("Joe", encoder.encode("Abc123@!"), "Joe Doe", "USER");
        when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    }


    /**
     * Teste la connexion avec des identifiants valides.
     * Vérifie la redirection et l'authentification réussie.
     */
    @Test
    void loginWithValidCredentials_ShouldReturn302AndRedirect() throws Exception {

        mockMvc.perform(formLogin("/app/login")
                        .user(user.getUsername())
                        .password("Abc123@!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(authenticated());
    }


    /**
     * Teste la connexion avec des identifiants invalides.
     * Vérifie que l'utilisateur n'est pas authentifié et est redirigé vers la page de connexion.
     */
    @Test
    void loginWithInvalidCredentials_ShouldReturnUnauthenticated() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                        .user("invalidUser")
                        .password("invalidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login?error"))
                .andExpect(unauthenticated());
    }
}
