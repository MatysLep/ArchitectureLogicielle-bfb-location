# 🚗 BFB Location
> **Une plateforme de location de véhicules conçue pour la robustesse, l'extensibilité et l'éco-conception logicielle.**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-4.4-47A248?style=for-the-badge&logo=mongodb&logoColor=white)](https://www.mongodb.com/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

---

## 🎯 Contexte & Objectifs
Ce projet a été réalisé dans le cadre du module **Architecture Logicielle & Éco-conception** à **IMT Nord Europe**.  
L'ambition était de dépasser le simple CRUD pour concevoir une architecture backend capable de supporter des règles métier complexes (gestion de flotte, conflits de planning) tout en respectant des principes forts :
*   **Qualité Logicielle** : Code lisible, architecturé en couches et testable.
*   **Éco-conception** : Choix techniques sobres et algorithmes optimisés pour limiter la consommation de ressources.

## 🏗 Aperçu Technique
Ce projet est structuré selon une **Architecture en Couches (Layered Architecture)** claire et modulaire. Cette organisation favorise la séparation des préoccupations (Separation of Concerns), rendant le code plus lisible et facile à maintenir.

*   **Layer Core (Domaine & Métier)** : Le cœur de l'application. Il contient toute la logique métier, les services et les règles de validation (Validators). C'est ici que réside la valeur ajoutée fonctionnelle.
*   **Layer Infrastructure (Persistance)** : Gère l'accès aux données. Cette couche est responsable de la communication avec le moteur de base de données (MongoDB) et abstrait la complexité du stockage.
*   **Layer Interfaces (Présentation)** : Point d'entrée de l'application via des API REST. Elle expose les fonctionnalités au monde extérieur via des DTOs et Controllers.

## ✨ Fonctionnalités Clés
Voici les mécaniques techniques les plus avancées du projet :

*   **Pipeline de Validation Extensible (Chain of Responsibility)** : Implémentation du pattern *Chain of Responsibility* pour valider les contrats étape par étape (ex: `ClientExist` -> `VehicleExist` -> `DateCoherent` -> `AlreadyRented`). Cela permet d'ajouter dynamiquement de nouvelles règles sans modifier le code existant.
*   **Gestion Automatisée des Conflits** : Système intelligent de détection des retards de restitution (`Delay`) et d'annulation en cascade (`Cancellation`) des contrats futurs impactés.
*   **Architecture "Eco-Logicielle"** : Conception optimisée pour réduire l'empreinte numérique via des traitements batch ciblés et une modélisation efficace des données.

## 🛠 Stack Technique

| Catégorie | Technologies |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot 3.5.7, Lombok |
| **Architecture** | Hexagonal (Ports & Adapters), DDD (Domain Driven Design) |
| **Base de Données** | MongoDB (NoSQL) |
| **Validation** | Jakarta Validation, Hibernate Validator |
| **DevOps** | Docker, Docker Compose, Maven |
| **API Doc** | SpringDoc OpenAPI (Swagger UI) |

## 🚀 Installation & Usage

Démarrez l'application complète (Back + Base de données) en une seule commande grâce à Docker.

```bash
# 1. Cloner le projet
git clone https://github.com/StartYourImpossible/ArchitectureLogicielle-bfb-location.git
cd ArchitectureLogicielle-bfb-location

# 2. Lancer l'environnement (App Java + MongoDB)
docker-compose up -d --build

# 3. Accéder à l'API et à la documentation
# Swagger UI : http://localhost:8080/swagger-ui.html
```

## 🧠 Challenge & Apprentissage

### Le Défi : L'effet Domino des Retards 📉
Dans un système de location, si un client rend un véhicule en retard, cela impacte le contrat suivant prévu pour ce même véhicule. Gérer ces conflits manuellement est impossible à l'échelle.

**La Solution Technique :**
J'ai conçu un algorithme de réconciliation automatique (`updateOverdueAndConflictingContracts`) qui s'exécute périodiquement :
1.  **Détection** : Identifie les contrats "en cours" dont la date de fin est dépassée -> passage en état `DELAYED`.
2.  **Propagation** : Recherche tous les contrats futurs (`PENDING`) liés à ces véhicules retardataires.
3.  **Résolution** : Annule (`CANCELLED`) automatiquement les contrats futurs qui ne peuvent plus être honorés aujourd'hui, notifiant ainsi le système pour une intervention proactive.

Cette logique encapsulée dans le `Domain Service` garantit que l'état du parc reste cohérent sans intervention humaine brute.