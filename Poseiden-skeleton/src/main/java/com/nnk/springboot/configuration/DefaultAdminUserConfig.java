package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration pour la création automatique d'un utilisateur administrateur par défaut
 * lors du démarrage de l'application.
 */
@Configuration
public class DefaultAdminUserConfig {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAdminUserConfig.class);

    /**
     * Initialise un utilisateur administrateur par défaut si celui-ci n'existe pas déjà.
     *
     * @param passwordEncoder L'encodeur de mot de passe pour sécuriser le mot de passe
     * @param userService     Le service de gestion des utilisateurs
     * @return Un CommandLineRunner qui crée l'utilisateur admin au démarrage
     */
    @Bean
    CommandLineRunner initDefaultAdmin(PasswordEncoder passwordEncoder, UserService userService) {
        return args -> {
            /* Vérifier si l'utilisateur par défaut existe */
            String defaultUsername = "Stef";
            if (userService.findByUsername(defaultUsername).isEmpty()) {
                /* Créer l'utilisateur admin par défaut */
                User defaultAdmin = new User();
                defaultAdmin.setFullname(defaultUsername);
                defaultAdmin.setUsername(defaultUsername);
                defaultAdmin.setPassword(passwordEncoder.encode("P@ssw0rd"));
                defaultAdmin.setRole("ADMIN");

                /* Sauvegarder l'utilisateur */
                userService.save(defaultAdmin);
                logger.debug("Utilisateur admin par défaut créé : " + defaultUsername);
            } else {
                logger.debug("L'utilisateur admin par défaut existe déjà.");
            }
        };
    }
}