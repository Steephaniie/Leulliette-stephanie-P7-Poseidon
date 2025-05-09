package com.nnk.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Classe de test pour démontrer l'encodage de mot de passe avec BCrypt.
 * Cette classe teste le fonctionnement de l'encodeur BCrypt pour la sécurité des mots de passe.
 *
 * @author Khang Nguyen
 * @since 09/03/2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncodeTest {
    /**
     * Teste l'encodage d'un mot de passe avec BCrypt.
     * Encode le mot de passe "123456" et affiche le résultat.
     */
    @Test
    public void testPassword() {
        // Création d'une instance de l'encodeur BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // Encodage du mot de passe test
        String pw = encoder.encode("123456");
        // Affichage du mot de passe encodé
        System.out.println("[ "+ pw + " ]");
    }
}
