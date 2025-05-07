package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entité représentant une règle de trading.
 * Cette classe stocke les informations relatives aux règles
 * utilisées dans le système de trading.
 */
@Entity
@Table(name = "rulename")
@Data
@NoArgsConstructor
public class RuleName {

    /**
     * Identifiant unique de la règle
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Nom de la règle
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * Description détaillée de la règle
     */
    @NotBlank(message = "Description is mandatory")
    private String description;

    /**
     * Configuration JSON de la règle
     */
    @NotBlank(message = "Json is mandatory")
    private String json;

    /**
     * Template utilisé pour la règle
     */
    @NotBlank(message = "Template is mandatory")
    private String template;

    /**
     * Requête SQL complète associée à la règle
     */
    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;

    /**
     * Partie SQL spécifique de la règle
     */
    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;


    /**
     * Constructeur avec paramètres pour créer une nouvelle règle
     *
     * @param name        Le nom de la règle
     * @param description La description de la règle
     * @param json        La configuration JSON
     * @param template    Le template utilisé
     * @param sqlStr      La requête SQL complète
     * @param sqlPart     La partie SQL spécifique
     */
    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}