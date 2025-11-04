package imt.archi.bfb.interfaces.rest.clients.model.input;

import imt.archi.bfb.core.clients.model.Client;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode()
public class ClientInput {
    private String name;
    private String surname;
    private String birthDate;
    private String address;
    private String driverLicenseNumber;

    public static Client convert(ClientInput input) {
        return Client.builder()
                .id(UUID.randomUUID())
                .name(input.getName())
                .surname(input.getSurname())
                .birthDate(LocalDate.parse(input.getBirthDate()))
                .address(input.getAddress())
                .driverLicenseNumber(input.getDriverLicenseNumber())
                .build();
    }
}
