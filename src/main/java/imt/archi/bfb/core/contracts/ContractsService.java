package imt.archi.bfb.core.contracts;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.contracts.ContractsDbService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractsService {
    
    protected ContractsDbService contractsDbService;

    public Collection<Contract> getAll(){
        return Objects.requireNonNullElse(contractsDbService.getAll(), Collections.emptySet());
    }

    public Optional<Contract> get(final UUID id) {
        return contractsDbService.get(id);
    }

    public Contract create(final Contract newContract) {
        return contractsDbService.save(newContract);
    }

    public void update(final Contract updatedContract) {
        contractsDbService.save(updatedContract);
    }

    public void delete(final UUID id) {
        contractsDbService.delete(id);
    }

}
