package imt.archi.bfb.core.vehicles.validators;

import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.interfaces.rest.common.exception.ConflictException;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Objects;

@AllArgsConstructor
public class VehicleAlreadyExistValidatorStep extends AbstractValidatorStep<Vehicle> {
    private VehiclesDbService service;

    @Override
    public void check(Vehicle vehicle) {
        if(Objects.requireNonNullElse(service.getAll(), Collections.<Vehicle>emptySet()).stream().anyMatch(alreadySaved -> isSameThing(vehicle, alreadySaved))) {
            throw new ConflictException(String.format("Vehicle with registration %s already exists", vehicle.getRegistration()));
        }
    }

    private boolean isSameThing(final Vehicle input, final Vehicle alreadySaved) {
        return alreadySaved.getRegistration().equals(input.getRegistration());
    }
}
