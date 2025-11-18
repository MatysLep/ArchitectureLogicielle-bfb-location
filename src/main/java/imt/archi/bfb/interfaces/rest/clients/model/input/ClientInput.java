package imt.archi.bfb.interfaces.rest.clients.model.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientInput extends AbstractInput {

    @Schema(description = "Nom du client", example = "Doe")
    private String name;

    @Schema(description = "Prénom du client", example = "John")
    private String surname;

    @Schema(description = "Date de naissance", type = "date")
    private LocalDate birthDate;

    @Schema(description = "Adresse du client", example = "1 Rue de Paris, 75000 Paris")
    private String address;

    @Schema(description = "Numéro de permis", example = "010203040506")
    private String driverLicenseNumber;

    public static Client convert(ClientInput input) {
        return Client.builder()
                .id(UUID.randomUUID())
                .name(input.getName())
                .surname(input.getSurname())
                .birthDate(input.getBirthDate())
                .address(input.getAddress())
                .driverLicenseNumber(input.getDriverLicenseNumber())
                .build();
    }
}
