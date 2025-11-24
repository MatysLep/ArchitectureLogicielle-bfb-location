package imt.archi.bfb.interfaces.rest.contracts.model.input;

import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.common.validators.DateValidator;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID du client")
    private String idClient;

    @Schema(description = "ID du véhicule")
    private String idVehicle;

    @Schema(description = "Date de début", example = "01/01/2000")
    @DateValidator
    private String startDate;

    @Schema(description = "Date de fin", example = "01/01/2000")
    @DateValidator
    private String endDate;

    @Schema(description = "Status du contrat", implementation = ContractState.class)
    private ContractState state;

    public static Contract convert(ContractInput contract) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return Contract.builder()
            .id(UUID.randomUUID())
            .idClient(contract.getIdClient())
            .idVehicle(contract.getIdVehicle())
            .startDate(LocalDate.parse(contract.getStartDate(), formatter))
            .endDate(LocalDate.parse(contract.getEndDate(), formatter))
            .state(contract.getState())
            .build();
    }


    
}
