package imt.archi.bfb.core.contracts;

import org.springframework.stereotype.Service;

import imt.archi.bfb.infra.db.contracts.ContractsDbService;

@Service
public class ContractsServiceValidator extends ContractsService {

    public ContractsServiceValidator(final ContractsDbService service) {
        super(service);
    }

    // TODO Validator
    
}
