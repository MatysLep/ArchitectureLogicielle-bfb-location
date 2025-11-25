package imt.archi.bfb.core.vehicles.model;

import imt.archi.bfb.core.common.model.VehicleState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@ToString
public class Vehicle {
    /**
     * Pattern de validation pour les plaques : lettres et chiffres uniquement
     */
    private static final String REGISTRATION_PATTERN = "^[A-Z]{2}-[0-9]{3}-[A-Z]{2}$";

    /**
     * Identifiant unique du véhicule
     */
    @NotNull(message = "La plaque d'immatriculation ne peut pas être nulle.")
    @Pattern(regexp = REGISTRATION_PATTERN, message = "La plaque n'est pas valide, il faut respecter ce format : AA-123-BB.")
    @Schema(description = "Plaque du véhicule", example = "AA-123-BB")
    private String registration;

    /**
     * Marque de la voiture
     */
    @NotNull(message = "La marque ne peut pas être nulle.")
    @Schema(description = "Marque du véhicule", example = "Toyota")
    private String brand;

    /**
     * Modèle de la voiture
     */
    @NotNull(message = "Le model ne peut pas être nul.")
    @Schema(description = "Modèle du véhicule", example = "Clio")
    private String model;

    /**
     * Motorisation de la voiture
     */
    @NotNull(message = "La motorisation ne peut pas être nulle.")
    @Schema(description = "Motorisation du véhicule", example = "Diesel")
    private String motorization;

    /**
     * Couleur de la voiture
     */
    @NotNull(message = "La couleur ne peut pas être nulle.")
    @Schema(description = "Couleur du véhicule", example = "Bleu")
    private String color;

    /**
     * Date d'acquisition
     */
    @NotNull(message = "La date d'acquisition ne peut pas être nulle.")
    @Schema(description = "Date d'acquisition", example = "01/01/1990")
    private LocalDate acquisitionDate;

    /**
     * État de la voiture
     */
    @NotNull(message = "L'état du véhicule ne peut pas être nul.")
    @Schema(description = "État du véhicule", implementation = VehicleState.class)
    private VehicleState state;
}
