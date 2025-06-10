package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration pour la création automatique d'un utilisateur administrateur par défaut
 * lors du démarrage de l'application.
 */
@Configuration
@RequiredArgsConstructor
public class DefaultAdminUserConfig {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAdminUserConfig.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Initialise un utilisateur administrateur par défaut si aucun utilisateur avec
     * le nom d'utilisateur spécifié n'existe déjà dans le système. Cette méthode est
     * exécutée automatiquement après l'initialisation des dépendances de la classe
     * grâce à l'annotation {@code @PostConstruct}.
     *
     */

    @PostConstruct
    public void initDefaultAdmin() {
        /* Vérifier si l'utilisateur par défaut existe */
        String stef = "Stef";
        if (userService.findByUsername(stef).isEmpty()) {
            /* Créer l'utilisateur admin par défaut */
            User defaultAdmin = new User();
            defaultAdmin.setFullname(stef);
            defaultAdmin.setUsername(stef);
            defaultAdmin.setPassword(passwordEncoder.encode("P@ssw0rd"));
            defaultAdmin.setRole("ADMIN");
            /* Sauvegarder l'utilisateur */
            userService.save(defaultAdmin);
            logger.debug("Utilisateur admin par défaut créé : " + stef);
        } else {
            logger.debug("L'utilisateur admin par défaut existe déjà.");

        }
    }
}