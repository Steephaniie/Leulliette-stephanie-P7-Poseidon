package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Spring Boot pour Poseidon Capital Solutions.
 * Cette classe initialise et démarre l'application Spring Boot.
 * Elle utilise l'annotation @SpringBootApplication qui combine :
 * - @Configuration
 * - @EnableAutoConfiguration
 * - @ComponentScan
 */
@SpringBootApplication
public class Application {

    /**
     * Point d'entrée principal de l'application.
     * Cette méthode démarre l'application Spring Boot avec la configuration spécifiée.
     *
     * @param args Arguments de ligne de commande passés à l'application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
