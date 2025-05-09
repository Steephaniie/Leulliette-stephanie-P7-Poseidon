package com.nnk.springboot.service;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Service personnalisé pour charger les détails des utilisateurs lors de l'authentification.
 * Cette classe implémente l'interface UserDetailsService de Spring Security pour permettre
 * l'authentification des utilisateurs à partir de la base de données.
 *
 * <p>Le service utilise un {@link UserRepository} pour accéder aux données des utilisateurs
 * et convertit les entités {@link User} en objets {@link UserDetails} compatibles avec Spring Security.</p>
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    /**
     * Logger pour tracer les opérations de chargement des utilisateurs
     */
    private final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    /**
     * Repository pour accéder aux données des utilisateurs
     */
    private final UserRepository userRepository;


    /**
     * Charge les détails d'un utilisateur pour l'authentification en se basant sur son nom d'utilisateur.
     * Cette méthode recherche l'utilisateur dans le référentiel {@link UserRepository} et,
     * s'il est trouvé, convertit l'entité {@link User} en un utilisateur de sécurité
     * compatible avec Spring Security via {@link UserDetails}.
     *
     * <p>Si aucun utilisateur correspondant n'est trouvé, une exception {@link UsernameNotFoundException}
     * est levée, et un avertissement est enregistré dans les logs.</p>
     *
     * @param username le nom d'utilisateur de l'utilisateur à authentifier
     * @return une instance de {@link UserDetails} contenant les informations de l'utilisateur pour l'authentification
     * @throws UsernameNotFoundException si aucun utilisateur correspondant n'est trouvé dans le référentiel
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(this::createSecurityUser)
                .orElseThrow(() -> {
                    logger.warn("User not found with username : {}", username);
                    return new UsernameNotFoundException("User not found with username : " + username);
                });
    }


    /**
     * Crée et retourne un utilisateur de sécurité Spring {@link org.springframework.security.core.userdetails.User} basé sur un utilisateur de l'application
     * représenté par un {@link User}. Cette méthode extrait le rôle de l'utilisateur, le convertit en
     * autorité de sécurité, puis crée une instance de {@link org.springframework.security.core.userdetails.User} pour les contrôles d'authentification et d'autorisation.
     *
     * <p>Le rôle de l'utilisateur est préfixé avec "ROLE_" pour se conformer aux conventions de Spring Security.
     * Cette instance de {@link org.springframework.security.core.userdetails.User} est ensuite utilisée par le framework pour gérer les sessions et les contrôles d'accès.</p>
     *
     * @param user l'entité {@link User} représentant un utilisateur dans l'application
     * @return une instance de {@link org.springframework.security.core.userdetails.User} avec le nom d'utilisateur, le mot de passe et les autorités de sécurité
     */
    private org.springframework.security.core.userdetails.User createSecurityUser(User user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of(authority));
    }

}
