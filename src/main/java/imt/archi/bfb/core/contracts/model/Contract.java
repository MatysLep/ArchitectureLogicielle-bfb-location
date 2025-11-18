package imt.archi.bfb.core.contracts.model;

import java.time.LocalDate;
import java.util.UUID;

import imt.archi.bfb.core.common.model.ContractState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Contract {
    
    @Builder.Default
    @NotNull(message = "L'identifiant ne peut pas être nul")
    private final UUID id = UUID.randomUUID();

    @NotNull
    @Schema(description = "ID du client")
    private String idClient;

    @NotNull
    @Schema(description = "ID du véhicule")
    private String idVehicle;

    @NotNull
    //@Pattern voir format date ?
    @Schema(description = "Date de début", type = "date")
    private LocalDate startDate;

    @NotNull
    @Schema(description = "Date de fin", type = "date")
    private LocalDate endDate;

    @NotNull
    @Schema(description = "Status du contrat", implementation = ContractState.class)
    private ContractState state;


}
