package imt.archi.bfb.infra.db.contracts.repositories.mapper;

import java.util.UUID;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.common.model.mappers.AbstractDbMapper;
import imt.archi.bfb.infra.db.contracts.repositories.entities.ContractEntity;

@Service
public class ContractDbMapper extends AbstractDbMapper<Contract, ContractEntity>{

    // TODO : DOCS Singleton Mapper (gerer par Springboot)
    // private VehicleBddMapper compteMapper;
    // private ClientBddMapper compteMapper;

    @Override
    public Contract from(final ContractEntity input) {
        return Contract.builder()
            .id(UUID.fromString(input.getId()))
            .idClient(input.getIdClient())
            .vehicleRegistration(input.getVehicleRegistration())
            .startDate(input.getStartDate())
            .endDate(input.getEndDate())
            .state(input.getState())
            .build();
    }

    @Override
    public ContractEntity to(final Contract object) {
        return ContractEntity.builder()
            .id(object.getId().toString())
            .idClient(object.getIdClient())
            .vehicleRegistration(object.getVehicleRegistration())
            .startDate(object.getStartDate())
            .endDate(object.getEndDate())
            .state(object.getState())
            .build();
    }
    
}
