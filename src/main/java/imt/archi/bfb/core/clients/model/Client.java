package imt.archi.bfb.core.clients.model;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode

@ToString
public class Client {

    @Builder.Default
    @NotNull(message = "L'ID ne peut pas être nul.")
    private final UUID id = UUID.randomUUID();

    @NotNull(message = "Le prénom ne peut pas être nul.")
    private String name;

    @NotNull(message = "Le nom ne peut pas être nul.")
    private String surname;

    @NotNull(message = "La date de naissance ne peut pas être nul.")
    private LocalDate birthDate;

    @NotNull(message = "Le numéro de permis ne peut pas être nul.")
    private String driverLicenseNumber;

    @NotNull(message = "L'adresse ne peut pas être nul.")
    private String address;


}
