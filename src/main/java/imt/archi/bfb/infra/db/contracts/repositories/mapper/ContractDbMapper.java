package imt.archi.bfb.infra.db.contracts.repositories.mapper;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.common.model.mappers.AbstractDbMapper;
import imt.archi.bfb.infra.db.contracts.repositories.entities.ContractEntity;

@Service
public class ContractDbMapper extends AbstractDbMapper<Contract, ContractEntity>{

    @Override
    public Contract from(ContractEntity from) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'from'");
    }

    @Override
    public ContractEntity to(Contract to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'to'");
    }
    
}
