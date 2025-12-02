package imt.archi.bfb.core.contracts.validators;

import java.util.Collections;
import java.util.Objects;

import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VehicleIdExistValidatorStep extends AbstractValidatorStep<String> {

    protected VehiclesDbService service;

    @Override
    public void check(final String vehicleRegistration) {
        boolean clientExists = Objects
                .requireNonNullElse(service.getAll(), Collections.<Vehicle>emptySet())
                .stream()
                .anyMatch(vehicle -> vehicle.getRegistration().equals(vehicleRegistration));

        if (!clientExists) {
            throw new NotFoundException(String.format("Aucun vehicule avec l'id %s n'existe en base", vehicleRegistration));
        }
    }
    
}
