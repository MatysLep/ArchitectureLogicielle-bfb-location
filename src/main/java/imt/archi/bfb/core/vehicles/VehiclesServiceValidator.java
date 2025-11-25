package imt.archi.bfb.core.vehicles;

import imt.archi.bfb.core.common.validators.ConstraintValidatorStep;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.core.vehicles.validators.VehicleAlreadyExistValidatorStep;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.infra.event.vehicles.MouvementVehicleEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VehiclesServiceValidator extends VehiclesService{
    public VehiclesServiceValidator(VehiclesDbService vehiclesDbService,final MouvementVehicleEventPublisher mouvementPublisher) {
        super(vehiclesDbService,mouvementPublisher);
    }

    public Vehicle create(final Vehicle vehicle) {
        new ConstraintValidatorStep<Vehicle>()
                .linkWith(new VehicleAlreadyExistValidatorStep(vehiclesDbService))
                .validate(vehicle)
                .throwIfValid();

        return super.create(vehicle);
    }

    public void update(final Vehicle vehicle) {
        new ConstraintValidatorStep<Vehicle>()
            .validate(vehicle)
            .throwIfValid();
        super.update(vehicle);
    }
}
