package com.nnk.springboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Classe de test pour la vérification du chiffrement des mots de passe.
 * Cette classe teste l'encodage BCrypt pour s'assurer que les mots de passe
 * sont correctement chiffrés.
 */
@SpringBootTest
public class PasswordEncodeTest {
    /**
     * Teste le chiffrement d'un mot de passe avec BCrypt.
     * Vérifie que le mot de passe chiffré n'est pas vide et
     * affiche le résultat du chiffrement.
     */
    @Test
    public void testPassword() {
        // Création d'un nouvel encodeur BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // Chiffrement du mot de passe "123456"
        String pw = encoder.encode("123456");
        // Affichage du mot de passe chiffré
        System.out.println("[ " + pw + " ]");
        // Vérification que le résultat n'est pas vide
        assertThat(pw).isNotEmpty();
    }
}