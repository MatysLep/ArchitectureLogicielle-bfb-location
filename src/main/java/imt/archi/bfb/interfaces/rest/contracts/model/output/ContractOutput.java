package imt.archi.bfb.interfaces.rest.contracts.model.output;

import java.io.Serial;
import java.time.LocalDate;

import lombok.*;

import imt.archi.bfb.core.common.model.ContractState;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.interfaces.rest.common.model.output.AbstractOutput;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ContractOutput extends AbstractOutput {

    public static ContractOutput from(final Contract contract){
        return ContractOutput.builder()
                .id(contract.getId().toString())
                .idClient(contract.getIdClient())
                .idVehicle(contract.getIdVehicle())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .state(contract.getState())
                .build();
    }

    @Serial
    private static final long serialVersionUID = 1L;
    private final String id;
    private final String idClient;
    private final String idVehicle;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final ContractState state;

}
