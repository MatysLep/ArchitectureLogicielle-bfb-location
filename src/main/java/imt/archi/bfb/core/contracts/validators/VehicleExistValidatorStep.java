package imt.archi.bfb.core.contracts.validators;

import java.util.Collections;
import java.util.Objects;

import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VehicleExistValidatorStep extends AbstractValidatorStep<Contract> {

    protected VehiclesDbService service;

    @Override
    public void check(final Contract toValidate) {
        boolean clientExists = Objects
                .requireNonNullElse(service.getAll(), Collections.<Vehicle>emptySet())
                .stream()
                .anyMatch(vehicle -> vehicle.getRegistration().equals(toValidate.getVehicleRegistration()));

        if (!clientExists) {
            throw new NotFoundException(String.format("Aucun vehicule avec l'id %s n'existe en base", toValidate.getIdClient()));
        }
    }
    
}
