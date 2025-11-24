package imt.archi.bfb.interfaces.rest.vehicles.model.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.common.model.output.AbstractOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class VehicleOutput extends AbstractOutput {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Plaque du véhicule", example = "AA-123-BB")
    private final String registration;

    @Schema(description = "Marque du véhicule", example = "Toyota")
    private final String brand;

    @Schema(description = "Modèle du véhicule", example = "Clio")
    private final String model;

    @Schema(description = "Motorisation du véhicule", example = "Diesel")
    private final String motorization;

    @Schema(description = "Couleur du véhicule", example = "Bleu")
    private final String color;

    @Schema(description = "Date d'acquisition'", example = "01/01/2000", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private final LocalDate acquisitionDate;

    @Schema(description = "État du véhicule", implementation = VehicleState.class)
    private final VehicleState state;


    public static VehicleOutput from(final Vehicle vehicle) {
        return VehicleOutput.builder()
                .registration(vehicle.getRegistration())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .motorization(vehicle.getMotorization())
                .color(vehicle.getColor())
                .acquisitionDate(vehicle.getAcquisitionDate())
                .state(vehicle.getState())
                .build();
    }
}
