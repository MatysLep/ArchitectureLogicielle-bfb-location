package imt.archi.bfb.interfaces.rest.clients.model.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.core.common.validators.DateValidator;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import imt.archi.bfb.interfaces.rest.common.model.input.UpdatableProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ClientUpdateInput extends AbstractInput {
    public static Client from(final ClientUpdateInput input, final Client alreadySaved) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return alreadySaved.toBuilder()
                .name(input.getName().defaultIfNotOverwrite(alreadySaved.getName()))
                .surname(input.getSurname().defaultIfNotOverwrite(alreadySaved.getSurname()))
                .birthDate(
                        LocalDate.parse(
                            input.getBirthDate().defaultIfNotOverwrite(alreadySaved.getBirthDate().format(formatter))
                        , formatter)
                )
                .driverLicenseNumber(input.getDriverLicenseNumber().defaultIfNotOverwrite(alreadySaved.getDriverLicenseNumber()))
                .address(input.getAddress().defaultIfNotOverwrite(alreadySaved.getAddress()))
                .build();
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Nom du client", example = "Doe", type = "string")
    private UpdatableProperty<String> name = UpdatableProperty.empty();

    @Schema(description = "Prénom du client", example = "John", type = "string")
    private UpdatableProperty<String> surname = UpdatableProperty.empty();

    @Schema(description = "Date de naissance", example = "01/01/2000")
    private UpdatableProperty<String> birthDate = UpdatableProperty.empty();

    @Schema(description = "Adresse du client", example = "1 Rue de Paris, 75000 Paris", type = "string")
    private UpdatableProperty<String> address = UpdatableProperty.empty();

    @Schema(description = "Numéro de permis", example = "010203040506", type = "string")
    private UpdatableProperty<String> driverLicenseNumber = UpdatableProperty.empty();

    public void setName(final String name) {
        this.name = UpdatableProperty.makesChanges(name);
    }
    public void setSurname(final String surname) {
        this.surname = UpdatableProperty.makesChanges(surname);
    }
    public void setBirthDate(final String birthDate) {this.birthDate = UpdatableProperty.makesChanges(birthDate);}
    public void setAddress(final String address) {this.address = UpdatableProperty.makesChanges(address);}
    public void setDriverLicenseNumber(final String driverLicenseNumber) {this.driverLicenseNumber = UpdatableProperty.makesChanges(driverLicenseNumber);}

    @JsonIgnore
    @DateValidator(required = true)
    public String getBirthDateValidation() {
        if (this.birthDate == null) return null;
        return this.birthDate.getValue();
    }
}