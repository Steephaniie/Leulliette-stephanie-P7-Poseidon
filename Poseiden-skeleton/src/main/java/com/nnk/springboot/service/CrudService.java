package com.nnk.springboot.service;

import java.util.List;

public interface CrudService<T> {

    /**
     * Récupère tous les éléments.
     *
     * @return Liste de tous les éléments.
     */
    List<T> getAll();

    /**
     * Enregistre un nouvel élément.
     *
     * @param entity L'entité à sauvegarder.
     * @return L'entité sauvegardée.
     */
    T save(T entity);

    /**
     * Récupère un élément par son identifiant.
     *
     * @param id Identifiant de l'élément recherché.
     * @return L'entité trouvée.
     */
    T getById(int id);

    /**
     * Met à jour un élément existant.
     *
     * @param id     Identifiant de l'élément à mettre à jour.
     * @param entity L'entité avec les nouvelles données.
     * @return L'entité mise à jour.
     */
    T update(int id, T entity);

    /**
     * Supprime un élément.
     *
     * @param id Identifiant de l'élément à supprimer.
     */
    void delete(int id);
}