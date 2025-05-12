package com.nnk.springboot.configuration;

import com.nnk.springboot.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configure et initialise le bean {@link SecurityFilterChain} pour gérer la sécurité HTTP dans l'application.
     * Cette méthode définit les règles d'autorisation pour différents endpoints, la configuration de la page
     * de connexion et de déconnexion, ainsi que le traitement des exceptions.
     *
     * <p>La configuration spécifie des autorisations pour les ressources statiques et pour certains rôles.
     * Elle définit également une page de connexion personnalisée, une redirection après succès, et une
     * URL de déconnexion personnalisée qui invalide la session et supprime les cookies.</p>
     *
     * @param http l'instance de {@link HttpSecurity} pour la configuration de la sécurité HTTP
     * @return une instance de {@link SecurityFilterChain} configurée pour gérer la sécurité HTTP
     * @throws Exception si une erreur de configuration survient lors de la création de la chaîne de filtres
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/css/**").permitAll() // Accès public
                        .requestMatchers("/", "/app/login").permitAll() // Accès public
                        .requestMatchers("/user/**").hasRole("ADMIN") // accès restreint aux administrateurs
                        .anyRequest().authenticated() // Toutes les autres requêtes doivent être authentifiées
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/app/error") // Redirection vers la page 403 pour accès refusé
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .defaultSuccessUrl("/bidList/list", true) // Page de connexion personnalisée avec redirection forcée
                        .permitAll()
                )
                // Associe le service d'utilisateurs personnalisé
                .userDetailsService(customUserDetailsService)
                .logout(logout -> logout
                        .logoutUrl("/app-logout") // URL de déconnexion personnalisée
                        .logoutSuccessUrl("/app/login") // Redirection après déconnexion
                        .permitAll()
                );        ;
        return http.build();
    }


    /**
     * Crée un encodeur de mots de passe basé sur BCrypt.
     * Cet encodeur est utilisé pour hacher et vérifier les mots de passe utilisateur de manière sécurisée.
     *
     * @return Un encodeur BCrypt pour hacher les mots de passe.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Création du bean BCryptPasswordEncoder pour le hachage des mots de passe.");
        return new BCryptPasswordEncoder(); // Pour hacher les mots de passe
    }
}
