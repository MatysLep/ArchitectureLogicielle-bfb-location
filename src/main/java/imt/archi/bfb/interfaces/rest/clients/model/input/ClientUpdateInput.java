package imt.archi.bfb.interfaces.rest.clients.model.input;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import imt.archi.bfb.interfaces.rest.common.model.input.UpdatableProperty;
import lombok.*;

import java.io.Serial;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ClientUpdateInput extends AbstractInput {
    public static Client from(final ClientUpdateInput input, final Client alreadySaved) {
        return alreadySaved.toBuilder()
                .name(input.getName().defaultIfNotOverwrite(alreadySaved.getName()))
                .surname(input.getSurname().defaultIfNotOverwrite(alreadySaved.getSurname()))
                .address(input.getAddress().defaultIfNotOverwrite(alreadySaved.getAddress()))
                .build();
    }

    @Serial
    private static final long serialVersionUID = 1L;
    private UpdatableProperty<String> name = UpdatableProperty.empty();
    private UpdatableProperty<String> surname = UpdatableProperty.empty();
    private UpdatableProperty<LocalDate> birthDate = UpdatableProperty.empty();
    private UpdatableProperty<String> address = UpdatableProperty.empty();
    private UpdatableProperty<String> driverLicenseNumber = UpdatableProperty.empty();

    public void setName(final String name) {
        this.name = UpdatableProperty.makesChanges(name);
    }
    public void setSurname(final String surname) {
        this.surname = UpdatableProperty.makesChanges(surname);
    }
    public void setBirthDate(final LocalDate birthDate) {this.birthDate = UpdatableProperty.makesChanges(birthDate);}
    public void setAddress(final String address) {this.address = UpdatableProperty.makesChanges(address);}
    public void setDriverLicenseNumber(final String driverLicenseNumber) {this.driverLicenseNumber = UpdatableProperty.makesChanges(driverLicenseNumber);}
}