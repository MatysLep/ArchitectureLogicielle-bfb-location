package imt.archi.bfb.core.contracts.model;

import java.time.LocalDate;
import java.util.UUID;

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
    //@Pattern voir format date ?
    private LocalDate dateDebut;

    @NotNull
    private LocalDate dateFin;

    @NotNull
    private String etat; // TODO gerer Enum : (en attente, en cours, terminé, en retard et annulé) voir common state enum (differencier de l'etat des voitures VehicleState != ContractState)


}
