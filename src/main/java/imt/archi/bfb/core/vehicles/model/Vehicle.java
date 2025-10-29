package imt.archi.bfb.core.vehicles.model;

import imt.archi.bfb.core.commons.model.StateVehicle;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@ToString
public class Vehicle {

    /**
     * Identifiant unique du véhicule
     */
    public String registration;

    /**
     * Marque de la voiture
     */
    public String brand;

    /**
     * Modèle de la voiture
     */
    public String model;

    /**
     * Motorisation de la voiture
     */
    public String motorization;

    /**
     * Couleur de la voiture
     */
    public String color;

    /**
     * Date d'acquisition
     */
    public Date acquisitionDate;

    /**
     * État de la voiture
     */
    public StateVehicle state;
}
