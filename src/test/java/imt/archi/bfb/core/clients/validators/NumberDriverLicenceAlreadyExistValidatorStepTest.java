package imt.archi.bfb.core.clients.validators;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import imt.archi.bfb.interfaces.rest.common.exception.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("NumberDriverLicenceAlreadyExistValidatorStep - validations numéro de permis")
class NumberDriverLicenceAlreadyExistValidatorStepTest {

    @Mock
    private ClientBddService service;

    @InjectMocks
    private NumberDriverLicenceAlreadyExistValidatorStep validator;

    private Client inputClient;

    @BeforeEach
    void setUp() {
        inputClient = Client.builder()
                .name("John")
                .surname("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .driverLicenseNumber("DL-12345")
                .address("1 Main Street")
                .build();
    }

    @Nested
    @DisplayName("check - validation de l'unicité du numéro de permis")
    class Check {

        @Test
        @DisplayName("doit lever une ConflictException lorsque le numéro de permis existe déjà")
        void shouldThrowConflictExceptionWhenDriverLicenseNumberAlreadyExists() {
            // Given
            Client existingClient = inputClient.toBuilder()
                    .driverLicenseNumber("dl-12345")
                    .build();
            when(service.getAll()).thenReturn(Set.of(existingClient));

            // When
            Executable checkCall = () -> validator.check(inputClient);

            // Then
            assertThrows(ConflictException.class, checkCall);
        }

        @Test
        @DisplayName("ne doit pas lever d'exception lorsque les numéros de permis diffèrent")
        void shouldNotThrowWhenDriverLicenseNumbersDiffer() {
            // Given
            Client differentClient = inputClient.toBuilder()
                    .driverLicenseNumber("DL-67890")
                    .build();
            when(service.getAll()).thenReturn(Set.of(differentClient));

            // When
            Executable checkCall = () -> validator.check(inputClient);

            // Then
            assertDoesNotThrow(checkCall);
        }

        @Test
        @DisplayName("ne doit pas lever d'exception lorsque le service retourne null")
        void shouldNotThrowWhenServiceReturnsNull() {
            // Given
            when(service.getAll()).thenReturn(null);

            // When
            Executable checkCall = () -> validator.check(inputClient);

            // Then
            assertDoesNotThrow(checkCall);
        }
    }
}
