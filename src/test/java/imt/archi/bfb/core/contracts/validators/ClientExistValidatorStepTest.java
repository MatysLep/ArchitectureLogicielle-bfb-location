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

import imt.archi.bfb.core.contracts.model.Contract;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientExistValidatorStep - validation impossible de créer un contract avec un client qui n'existe pas")
public class ClientExistValidatorStepTest {

    @Mock
    private ClientBddService clientBddService;

    private ClientExistValidatorStep validator;

    private Contract contract;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        validator = new ClientExistValidatorStep(clientBddService);
        clientId = UUID.randomUUID();

        contract = Contract.builder()
                .idClient(clientId)
                .vehicleRegistration("AA-111-BB")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .state(null)
                .build();
    }

    @Test
    void shouldPassWhenClientExists() {
        Client client = Client.builder().id(clientId).build();
        when(clientBddService.getAll()).thenReturn(Set.of(client));

        validator.validate(contract).throwIfValid(); // ne doit pas lever d'exception
    }

    @Test
    void shouldThrowNotFoundWhenClientDoesNotExist() {
        when(clientBddService.getAll()).thenReturn(Collections.emptySet());

        assertThrows(NotFoundException.class, () -> validator.validate(contract).throwIfValid());
    }
}
