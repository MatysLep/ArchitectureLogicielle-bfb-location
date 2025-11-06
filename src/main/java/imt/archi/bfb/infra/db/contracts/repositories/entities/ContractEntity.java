package imt.archi.bfb.infra.db.contracts.repositories.entities;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import imt.archi.bfb.core.common.model.ContractState;

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
    private String idVehicle;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractState state;

}
