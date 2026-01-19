package imt.archi.bfb.core.clients.model;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Client {
    private static final String LICENCE_PATTERN = "^[0-9]{12}$";

    @Builder.Default
    @NotNull(message = "L'ID ne peut pas être nul.")
    private final UUID id = UUID.randomUUID();

    @NotNull(message = "Le prénom ne peut pas être nul.")
    @Schema(description = "Nom du client", example = "Doe")
    private String name;

    @NotNull(message = "Le nom ne peut pas être nul.")
    @Schema(description = "Prénom du client", example = "John")
    private String surname;

    @NotNull(message = "La date de naissance ne peut pas être nulle.")
    @Schema(description = "Date de naissance", example = "01/01/1990")
    private LocalDate birthDate;

    @NotNull(message = "Le numéro de permis ne peut pas être nul.")
    @Pattern(regexp = LICENCE_PATTERN, message = "Le numéro de permis n'est pas valide, il doit être composé de 12 chiffres.")
    @Schema(description = "Numéro de permis", example = "010203040506")
    private String driverLicenseNumber;

    @NotNull(message = "L'adresse ne peut pas être nulle.")
    @Schema(description = "Adresse du client", example = "1 Rue de Paris, 75000 Paris")
    private String address;


}
