# Application Spring Boot - Poseidon Capital Solutions

## Présentation du projet

Cette application est développée dans le cadre d’un projet réalisé chez **Poseidon Capital Solutions**, une société d’agrégation financière.  
L’objectif principal est de compléter le système back-end existant en :

- **ajoutant des fonctionnalités CRUD** (Create, Read, Update, Delete) pour 6 entités financières,
- **mettant en place un système d’authentification et d’autorisation sécurisé** avec Spring Security,
- **validant les données utilisateurs**, notamment les champs numériques et les mots de passe,
- **ajoutant des tests unitaires** pour garantir la robustesse de l’application.

L'interface utilisateur (front-end) est déjà fournie, ce projet se concentre donc exclusivement sur la partie **API REST** sécurisée avec **Spring Boot, Spring Data JPA et Spring Security**.

---

## Technologies utilisées

- Spring Boot 3.3.4
- Java 17
- Thymeleaf
- Bootstrap v4.3.1

---

## Configuration du projet avec IntelliJ IDEA

1. Créer un projet à partir de Spring Initializr : Fichier > Nouveau > Projet > Spring Initializr
2. Ajouter les dépendances nécessaires dans le fichier `pom.xml`.
3. Créer les dossiers suivants :
   - **src/main/java** (code source principal)
   - **src/main/resources/templates** (vues Thymeleaf)
   - **src/main/resources/static** (ressources statiques : CSS, JS, images)
4. Créer une base de données nommée `demo` (ou celle définie dans `application.properties`).

---

## Implémentation d'une fonctionnalité

1. Créer la classe de domaine et la placer dans le package :  
   `com.nnk.springboot.domain`
2. Créer la classe repository (interface `JpaRepository`) dans le package :  
   `com.nnk.springboot.repositories`
3. Créer la classe controller (logique métier + endpoints) dans :  
   `com.nnk.springboot.controllers`

---

## Sécurité de l'application

1. Créer une classe de service pour la gestion des utilisateurs et l’authentification, dans :  
   `com.nnk.springboot.services`
2. Ajouter une classe de configuration Spring Security dans :  
   `com.nnk.springboot.config`
3. Utiliser une **authentification basée sur la session** (session-based).
4. Les mots de passe doivent être **hachés** avec un algorithme sécurisé (`BCryptPasswordEncoder`).
5. Ajouter la **JavaDoc** sur toutes les classes et méthodes liées à l'authentification.

---

## Entités gérées (POJO)

Le projet gère les entités suivantes avec des méthodes CRUD complètes :

- `Bid` (offres financières)
- `CurvePoint` (points de courbe)
- `Rating` (notations financières)
- `Rule` (règles métiers)
- `Trade` (transactions financières)
- `User` (utilisateurs de l'application)

---

## Bonnes pratiques appliquées

- Respect de la convention `lowerCamelCase`
- Séparation claire des responsabilités (MVC)
- Validation des données en entrée (notations `@Valid`, regex, contraintes `@Size`, `@NotNull`, etc.)
- Tests unitaires sur les services CRUD
- Contrôle des accès selon les rôles (`ADMIN`, `USER`)

---

## Lancement de l'application

- Vérifier que la base de données est active.
- Lancer l’application depuis la classe `SpringBootApp` ou avec Maven :
  ```bash
  mvn spring-boot:run

## Accès à l'application 
- Page d’accueil : http://localhost:8080
- Connexion: http://localhost:8080/login
- Interface sécurisée accessible après authentification