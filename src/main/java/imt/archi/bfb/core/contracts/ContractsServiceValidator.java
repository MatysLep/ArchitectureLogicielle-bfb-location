package imt.archi.bfb.core.contracts;

import java.util.Collection;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.common.validators.ConstraintValidatorStep;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.core.contracts.validators.AlreadyRentedValidatorStep;
import imt.archi.bfb.core.contracts.validators.BrokenVehicleValidatorStep;
import imt.archi.bfb.core.contracts.validators.ClientExistValidatorStep;
import imt.archi.bfb.core.contracts.validators.ClientIdExistValidatorStep;
import imt.archi.bfb.core.contracts.validators.VehicleExistValidatorStep;
import imt.archi.bfb.core.contracts.validators.VehicleIdExistValidatorStep;
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

    // TODO validator date coherente start date > end date

    @Override
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

    @Override
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

    @Override
    public Collection<Contract> getAllByVehicle(String idVehicle){
        new ConstraintValidatorStep<String>()
            .linkWith(new VehicleIdExistValidatorStep(vehiclesDbService))
            .validate(idVehicle)
            .throwIfValid();
        return super.getAllByVehicle(idVehicle);
    }

    @Override
    public Collection<Contract> getAllByClient(String idClient){
        new ConstraintValidatorStep<String>()
            .linkWith(new ClientIdExistValidatorStep(clientBddService))
            .validate(idClient)
            .throwIfValid();
        return super.getAllByClient(idClient);
    }


    
}
