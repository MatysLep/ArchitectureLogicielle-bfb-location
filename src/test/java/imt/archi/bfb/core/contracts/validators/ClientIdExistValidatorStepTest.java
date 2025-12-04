package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientIdExistValidatorStep - validation impossible de lire les contrats d'un client qui n'existe pas")
public class ClientIdExistValidatorStepTest {

    @Mock
    private ClientBddService clientBddService;

    private ClientIdExistValidatorStep validator;

    private UUID clientId;

    @BeforeEach
    void setUp() {
        validator = new ClientIdExistValidatorStep(clientBddService);
        clientId = UUID.randomUUID();
    }

    @Test
    void shouldPassWhenClientExists() {
        Client client = Client.builder().id(clientId).build();
        when(clientBddService.getAll()).thenReturn(Set.of(client));

        validator.validate(clientId.toString()).throwIfValid();
    }

    @Test
    void shouldThrowNotFoundWhenClientDoesNotExist() {
        when(clientBddService.getAll()).thenReturn(Collections.emptySet());

        assertThrows(NotFoundException.class, () -> validator.validate(clientId.toString()).throwIfValid());
    }
}
