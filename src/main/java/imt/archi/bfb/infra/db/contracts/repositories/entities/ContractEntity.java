package imt.archi.bfb.infra.db.contracts.repositories.entities;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contracts")
public class ContractEntity {

    @Id
    private String id;
    private String idClient;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String etat; // TODO gerer Enum : (en attente, en cours, terminé, en retard et annulé) voir common state enum (differencier de l'etat des voitures VehicleState != ContractState)
}
