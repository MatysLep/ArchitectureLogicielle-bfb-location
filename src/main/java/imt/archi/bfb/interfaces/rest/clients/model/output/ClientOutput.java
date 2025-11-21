package imt.archi.bfb.interfaces.rest.clients.model.output;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.interfaces.rest.common.model.output.AbstractOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ClientOutput extends AbstractOutput {
    public static ClientOutput from(final Client client){
        return ClientOutput.builder()
                .id(client.getId().toString())
                .name(client.getName())
                .surname(client.getSurname())
                .birthDate(client.getBirthDate().toString())
                .address(client.getAddress())
                .driverLicenseNumber(client.getDriverLicenseNumber())
                .build();
    }

    @Serial
    private static final long serialVersionUID = 1L;
    private String id;

    @Schema(description = "Nom du client", example = "Doe")
    private String name;

    @Schema(description = "Prénom du client", example = "John")
    private String surname;

    @Schema(description = "Date de naissance", type = "date")
    private String birthDate;

    @Schema(description = "Adresse du client", example = "1 Rue de Paris, 75000 Paris")
    private String address;

    @Schema(description = "Numéro de permis", example = "010203040506")
    private String driverLicenseNumber;
}
