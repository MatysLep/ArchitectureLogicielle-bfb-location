package imt.archi.bfb.interfaces.rest.vehicles.model.input;

import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class VehicleInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;
    private String registration;
    private String brand;
    private String model;
    private String motorization;
    private String color;
    private Date acquisitionDate;
    private String state;

    public static Vehicle convert(final VehicleInput input) {
        return Vehicle.builder()
                .registration(input.getRegistration())
                .brand(input.getBrand())
                .model(input.getModel())
                .motorization(input.getMotorization())
                .color(input.getColor())
                .acquisitionDate(input.getAcquisitionDate())
                .state(VehicleState.fromOrDefault(input.getState()))
                .build();
    }
}
