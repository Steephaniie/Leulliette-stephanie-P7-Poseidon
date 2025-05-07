package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
/**
 * Classe représentant une notation financière.
 * Stocke les évaluations de différentes agences de notation (Moody's, S&P, Fitch)
 * et un numéro d'ordre associé.
 */
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * Identifiant unique de la notation
     */
    private Integer id;

    /**
     * Notation attribuée par l'agence Moody's
     * Limitée à 125 caractères
     */
    @Size(max = 125, message = "Moody's rating must be at most 125 characters.")
    private String moodysRating;

    /**
     * Notation attribuée par l'agence Standard & Poor's
     * Limitée à 125 caractères
     */
    @Size(max = 125, message = "S&P rating must be at most 125 characters.")
    private String sandPRating;

    /**
     * Notation attribuée par l'agence Fitch
     * Limitée à 125 caractères
     */
    @Size(max = 125, message = "Fitch rating must be at most 125 characters.")
    private String fitchRating;

    /**
     * Numéro d'ordre de la notation
     * Doit être compris entre 1 et 255
     */
    @NotNull(message = "Order number is a mandatory.")
    @Min(value = 1, message = "Order number must be greater than or equal to 1.")
    @Max(value = 255, message = "Order number must be less than or equal to 255.")
    private Integer orderNumber;


    /**
     * Constructeur avec paramètres pour créer une nouvelle notation
     *
     * @param moodysRating Notation de Moody's
     * @param sandPRating  Notation de Standard & Poor's
     * @param fitchRating  Notation de Fitch
     * @param orderNumber  Numéro d'ordre
     */
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}