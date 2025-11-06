package imt.archi.bfb.interfaces.rest.contracts.model.input;

import java.io.Serial;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

import imt.archi.bfb.core.common.model.ContractState;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ContractInput extends AbstractInput {

    @Serial
    private static final long serialVersionUID = 1L;
    private String idClient;
    private String idVehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractState state;

    public static Contract convert(ContractInput contract) {
        return Contract.builder()
            .id(UUID.randomUUID())
            .idClient(contract.getIdClient())
            .idVehicle(contract.getIdVehicle())
            .startDate(contract.getStartDate())
            .endDate(contract.getEndDate())
            .state(contract.getState())
            .build();
    }


    
}
