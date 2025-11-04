package imt.archi.bfb.infra.db.contracts;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.contracts.repositories.ContractRepository;
import imt.archi.bfb.infra.db.contracts.repositories.mapper.ContractDbMapper;

@Service
@AllArgsConstructor
public class ContractsDbService {

    private final ContractRepository contractRepository;
    private final ContractDbMapper contractDbMapper;

    public Collection<Contract> getAll(){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method");    
    }

    public Optional<Contract> get(final UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method");    
    }

    public Contract save(final Contract id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method");    
    }

    public void delete(final UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method");    
    }

}
