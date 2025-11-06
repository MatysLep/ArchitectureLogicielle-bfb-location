package imt.archi.bfb.core.contracts.model;

import java.time.LocalDate;
import java.util.UUID;

import imt.archi.bfb.core.common.model.ContractState;
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
    private String idClient;

    @NotNull
    private String idVehicle;

    @NotNull
    //@Pattern voir format date ?
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private ContractState state;


}
