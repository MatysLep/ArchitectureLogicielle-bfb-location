package imt.archi.bfb.interfaces.rest.contracts.model.output;

import java.io.Serial;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID du client")
    private final String idClient;

    @Schema(description = "ID du véhicule")
    private final String idVehicle;

    @Schema(description = "Date de début", type = "date")
    private final LocalDate startDate;

    @Schema(description = "Date de fin", type = "date")
    private final LocalDate endDate;

    @Schema(description = "Status du contrat", implementation = ContractState.class)
    private final ContractState state;

}
