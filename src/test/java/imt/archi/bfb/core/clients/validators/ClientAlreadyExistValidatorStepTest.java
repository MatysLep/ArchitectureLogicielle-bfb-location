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
@DisplayName("ClientAlreadyExistValidatorStep - validations existence client")
class ClientAlreadyExistValidatorStepTest {

    @Mock
    private ClientBddService service;

    @InjectMocks
    private ClientAlreadyExistValidatorStep validator;

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
    @DisplayName("check - validation de la présence d'un client identique")
    class Check {

        @Test
        @DisplayName("doit lever une ConflictException lorsqu'un client identique existe déjà")
        void shouldThrowConflictExceptionWhenClientAlreadyExists() {
            // Given
            Client existingClient = inputClient.toBuilder().build();
            when(service.getAll()).thenReturn(Set.of(existingClient));

            // When
            Executable checkCall = () -> validator.check(inputClient);

            // Then
            assertThrows(ConflictException.class, checkCall);
        }

        @Test
        @DisplayName("ne doit pas lever d'exception lorsqu'aucun client correspondant n'est trouvé")
        void shouldNotThrowWhenClientDoesNotExist() {
            // Given
            Client differentClient = Client.builder()
                    .name("Jane")
                    .surname("Smith")
                    .birthDate(LocalDate.of(1985, 5, 15))
                    .driverLicenseNumber("DL-67890")
                    .address("2 Main Street")
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
