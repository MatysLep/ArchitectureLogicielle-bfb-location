package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrokenVehicleValidatorStep extends AbstractValidatorStep<Contract> {

    protected VehiclesDbService service;

    @Override
    public void check(final Contract toValidate) {
        if (isBroken(service.get(toValidate.getVehicleRegistration()).get())) {
            throw new BadRequestException(String.format("Le vehicule de registration %s est en panne.", toValidate.getVehicleRegistration()));
        }
    }

    private boolean isBroken(final Vehicle vehicle) {
        return vehicle.getState() == VehicleState.BROKEN;
    }
    
}
