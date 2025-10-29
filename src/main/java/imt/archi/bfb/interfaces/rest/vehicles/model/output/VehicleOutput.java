package imt.archi.bfb.interfaces.rest.vehicles.model.output;

import imt.archi.bfb.core.commons.model.StateEnum;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.common.model.output.AbstractOutput;
import lombok.*;

import java.io.Serial;
import java.util.Date;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class VehicleOutput extends AbstractOutput {
    @Serial
    private static final long serialVersionUID = -5881478654611574936L;
    private final String registration;
    private final String brand;
    private final String model;
    private final String motorization;
    private final String color;
    private final Date acquisitionDate;
    private final StateEnum state;


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
