package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.core.common.model.ContractState;
import imt.archi.bfb.infra.db.contracts.ContractsDbService;
import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AlreadyRentedValidatorStep - validation impossible de louer un véhicule déjà loué")
public class AlreadyRentedValidatorStepTest {

    @Mock
    private ContractsDbService contractsDbService;

    private AlreadyRentedValidatorStep validator;
    private Contract contract;

    @BeforeEach
    void setUp() {
        validator = new AlreadyRentedValidatorStep(contractsDbService);

        contract = Contract.builder()
                .vehicleRegistration("AA-111-BB")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .state(ContractState.PENDING)
                .idClient(null)
                .build();
    }

    @Test
    void shouldPassWhenNoConflict() {
        when(contractsDbService.getAll()).thenReturn(Collections.emptySet());
        validator.validate(contract).throwIfValid();
    }

    @Test
    void shouldThrowBadRequestWhenConflict() {
        Contract existing = Contract.builder()
                .vehicleRegistration("AA-111-BB")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .build();
        when(contractsDbService.getAll()).thenReturn(Set.of(existing));

        assertThrows(BadRequestException.class, () -> validator.validate(contract).throwIfValid());
    }
}
