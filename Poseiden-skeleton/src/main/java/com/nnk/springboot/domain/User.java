package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant un utilisateur dans l'application.
 * Gère les informations d'authentification et les droits d'accès des utilisateurs.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Identifiant unique pour l'authentification de l'utilisateur
     */
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    /**
     * Mot de passe sécurisé de l'utilisateur
     * Doit respecter les règles de complexité définies
     */
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!?%^&+=]).{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial (@#$!?%^&+=)"
    )
    private String password;

    /**
     * Nom complet de l'utilisateur
     */
    @NotBlank(message = "Le nom complet est obligatoire")
    private String fullname;

    /**
     * Rôle déterminant les permissions de l'utilisateur
     */
    @NotBlank(message = "Le rôle est obligatoire")
    private String role;


    /**
     * Crée un nouvel utilisateur avec les informations essentielles
     *
     * @param username Identifiant unique de l'utilisateur
     * @param password Mot de passe sécurisé
     * @param fullname Nom complet de l'utilisateur
     * @param role     Rôle de l'utilisateur
     */
    public User(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
}
