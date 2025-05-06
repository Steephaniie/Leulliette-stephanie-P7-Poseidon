package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant un point sur une courbe financière.
 * Cette classe stocke les informations relatives à un point spécifique sur une courbe,
 * y compris son identifiant, sa valeur et sa date de création.
 */
@Entity
@Table(name = "curvepoint")
@Data
@NoArgsConstructor
public class CurvePoint {

    /**
     * Identifiant unique du point de courbe
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Identifiant de la courbe associée
     * Doit être supérieur ou égal à 1
     */
    @NotNull(message = "Curve ID is a mandatory")
    @Min(value = 1, message = "Curve ID must be greater than or equal to 1")
    private Integer curveId;

    /**
     * Date de référence du point de courbe
     */
    private LocalDateTime asOfDate;

    /**
     * Terme (durée) associé au point de courbe
     * Doit être une valeur positive
     */
    @NotNull(message = "Term is a mandatory")
    @Positive(message = "Term must be greater than 0")
    private Double term;

    /**
     * Valeur associée au point de courbe
     */
    @NotNull(message = "Value is a mandatory")
    private Double value;

    /**
     * Date de création de l'enregistrement
     */
    private LocalDateTime creationDate;


    /**
     * Constructeur avec paramètres pour créer un point de courbe
     *
     * @param curveId Identifiant de la courbe
     * @param term    Terme (durée) du point
     * @param value   Valeur du point
     */
    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}