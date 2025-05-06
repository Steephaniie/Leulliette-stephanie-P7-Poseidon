package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant une liste d'enchères dans le système.
 * Cette classe stocke les informations relatives aux enchères, incluant les détails du compte,
 * le type d'enchère, les quantités et autres informations pertinentes.
 */
@Entity
@Table(name = "bidlist")
@Data
@NoArgsConstructor
public class BidList {

    /**
     * Identifiant unique de l'enchère
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Compte associé à l'enchère
     * Doit être non vide et contenir entre 1 et 50 caractères
     */
    @NotEmpty(message = "Account is mandatory")
    @Size(min = 1, max = 50, message = "Account must be between 1 and 50 characters")
    private String account;

    /**
     * Type d'enchère
     * Doit être non vide et contenir entre 1 et 50 caractères
     */
    @NotEmpty(message = "Type is mandatory")
    @Size(min = 1, max = 50, message = "Type must be between 1 and 50 characters")
    private String type;

    /**
     * Quantité de l'enchère
     * Doit être non nulle et supérieure ou égale à zéro
     */
    @NotNull(message = "Bid quantity is mandatory") // Assure que la quantité n'est pas nulle
    @Min(value = 0, message = "Bid quantity must be greater than or equal to zero")
    private Double bidQuantity;


    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private LocalDateTime bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private LocalDateTime creationDate;
    private String revisionName;
    private LocalDateTime revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;


    /**
     * Constructeur avec les champs obligatoires
     *
     * @param account     Le compte associé à l'enchère
     * @param type        Le type d'enchère
     * @param bidQuantity La quantité de l'enchère
     */
    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}