package imt.archi.bfb.interfaces.rest.clients.model.output;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.interfaces.rest.common.model.output.AbstractOutput;
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
                .driverLicenseNumber(client.getDriverLicenseNumber())
                .build();
    }

    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String surname;
    private String birthDate;
    private String address;
    private String driverLicenseNumber;
}
