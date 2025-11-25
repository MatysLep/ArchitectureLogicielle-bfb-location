package imt.archi.bfb.infra.db.vehicles.repositories.entities;

import imt.archi.bfb.core.common.model.VehicleState;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Entité mongodb représentant un client
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicles")
public class VehicleEntity {

    /**
     * Identifiant unique du véhicule
     */
    @Id
    private String registration;

    /**
     * Marque de la voiture
     */
    private String brand;

    /**
     * Modèle de la voiture
     */
    private String model;

    /**
     * Motorisation de la voiture
     */
    private String motorization;

    /**
     * Couleur de la voiture
     */
    private String color;

    /**
     * Date d'acquisition
     */
    private LocalDate acquisitionDate;

    /**
     * État de la voiture
     */
    private VehicleState state;
}
