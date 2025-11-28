package imt.archi.bfb.interfaces.rest.vehicles.model.input;

import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.common.validators.DateValidator;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class VehicleInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "Plaque du véhicule", example = "AA-123-BB")
    private String registration;

    @Schema(description = "Marque du véhicule", example = "Toyota")
    private String brand;

    @Schema(description = "Modèle du véhicule", example = "Clio")
    private String model;

    @Schema(description = "Motorisation du véhicule", example = "Diesel")
    private String motorization;

    @Schema(description = "Couleur du véhicule", example = "Bleu")
    private String color;

    @Schema(description = "Date d'acquisition", example = "01/01/1990")
    @DateValidator(required = true, message = "La date est obligatoire et doit être au format JJ/MM/AAAA")
    private String acquisitionDate;

    @Schema(description = "État du véhicule", implementation = VehicleState.class)
    private VehicleState state;

    public static Vehicle convert(final VehicleInput input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return Vehicle.builder()
                .registration(input.getRegistration())
                .brand(input.getBrand())
                .model(input.getModel())
                .motorization(input.getMotorization())
                .color(input.getColor())
                .acquisitionDate(LocalDate.parse(input.getAcquisitionDate(), formatter))
                .state(input.getState())
                .build();
    }
}
