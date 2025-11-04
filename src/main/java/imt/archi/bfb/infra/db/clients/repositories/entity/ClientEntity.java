package imt.archi.bfb.infra.db.clients.repositories.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection= "clients")

public class ClientEntity {
    private String id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String driverLicenseNumber;
    private String address;
}
