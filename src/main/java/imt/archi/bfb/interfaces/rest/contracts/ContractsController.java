package imt.archi.bfb.interfaces.rest.contracts;

import java.util.Collection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imt.archi.bfb.core.contracts.ContractsServiceValidator;
import imt.archi.bfb.interfaces.rest.contracts.model.output.ContractOutput;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/contracts")
public class ContractsController {

    private final ContractsServiceValidator service;

    public Collection<ContractOutput> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method");    
    }

}
