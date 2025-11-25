package imt.archi.bfb.core.clients;

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
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientServiceValidator - validations métier client")
class ClientServiceValidatorTest {

    @Mock
    private ClientBddService clientBddService;

    @InjectMocks
    private ClientServiceValidator serviceValidator;

    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .name("John")
                .surname("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .driverLicenseNumber("010203040506")
                .address("1 Main Street")
                .build();
    }

    @Nested
    @DisplayName("create - création client")
    class Create {

        @Test
        @DisplayName("doit créer un client lorsque les validations passent")
        void shouldCreateClientWhenValidatorsPass() {
            // Given
            when(clientBddService.getAll()).thenReturn(Collections.emptySet());
            when(clientBddService.save(client)).thenReturn(client);

            // When
            Client result = serviceValidator.create(client);

            // Then
            assertSame(client, result);
            verify(clientBddService).save(client);
        }

        @Test
        @DisplayName("doit lever une ConflictException lorsqu'un client en conflit existe")
        void shouldThrowConflictExceptionWhenConflictingClientExists() {
            // Given
            Client conflicting = client.toBuilder()
                    .id(UUID.randomUUID())
                    .build();
            when(clientBddService.getAll()).thenReturn(Set.of(conflicting));

            // When
            Executable invocation = () -> serviceValidator.create(client);

            // Then
            assertThrows(ConflictException.class, invocation);
            verify(clientBddService, never()).save(any(Client.class));
        }
    }

    @Nested
    @DisplayName("update - mise à jour client")
    class Update {

        @Test
        @DisplayName("doit mettre à jour le client lorsque les validations passent")
        void shouldUpdateClientWhenValidatorsPass() {
            // Given
            when(clientBddService.getAll()).thenReturn(Collections.emptySet());
            when(clientBddService.save(client)).thenReturn(client);

            // When
            assertDoesNotThrow(() -> serviceValidator.update(client));

            // Then
            verify(clientBddService).save(client);
        }

        @Test
        @DisplayName("doit lever une ConflictException lorsqu'un conflit apparaît")
        void shouldThrowConflictExceptionWhenClientConflicts() {
            // Given
            Client conflicting = client.toBuilder()
                    .id(UUID.randomUUID())
                    .build();
            when(clientBddService.getAll()).thenReturn(Set.of(conflicting));

            // When
            Executable invocation = () -> serviceValidator.update(client);

            // Then
            assertThrows(ConflictException.class, invocation);
            verify(clientBddService, never()).save(any(Client.class));
        }
    }
}
