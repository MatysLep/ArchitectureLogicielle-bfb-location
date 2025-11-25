package imt.archi.bfb.core.contracts;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.common.validators.ConstraintValidatorStep;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.core.contracts.validators.AlreadyRentedValidatorStep;
import imt.archi.bfb.core.contracts.validators.BrokenVehicleValidatorStep;
import imt.archi.bfb.core.contracts.validators.ClientExistValidatorStep;
import imt.archi.bfb.core.contracts.validators.VehicleExistValidatorStep;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import imt.archi.bfb.infra.db.contracts.ContractsDbService;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;

@Service
public class ContractsServiceValidator extends ContractsService {

    protected VehiclesDbService vehiclesDbService;
    protected ClientBddService clientBddService;

    public ContractsServiceValidator(final ContractsDbService contractsDbService, final VehiclesDbService vehiclesDbService, final ClientBddService clientBddService) {
        super(contractsDbService);
        this.vehiclesDbService = vehiclesDbService;
        this.clientBddService = clientBddService;
    }

    public Contract create(Contract newContract) {
        new ConstraintValidatorStep<Contract>()
            .linkWith(new ClientExistValidatorStep(clientBddService))
            .linkWith(new VehicleExistValidatorStep(vehiclesDbService))
            .linkWith(new BrokenVehicleValidatorStep(vehiclesDbService))
            .linkWith(new AlreadyRentedValidatorStep(contractsDbService))
            .validate(newContract)
            .throwIfValid();
        return super.create(newContract);
    }

    public void update(final Contract contract) {
        new ConstraintValidatorStep<Contract>()
            .linkWith(new ClientExistValidatorStep(clientBddService))
            .linkWith(new VehicleExistValidatorStep(vehiclesDbService))
            .linkWith(new BrokenVehicleValidatorStep(vehiclesDbService))
            .linkWith(new AlreadyRentedValidatorStep(contractsDbService))
            .validate(contract)
            .throwIfValid();
        super.update(contract);
    }
    
}
