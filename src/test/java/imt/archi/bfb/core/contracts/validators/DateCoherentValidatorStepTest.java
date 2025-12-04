package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.core.common.model.ContractState;
import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("DateCoherentValidatorStep - validation des dates de contrat")
class DateCoherentValidatorStepTest {

    private DateCoherentValidatorStep validator;

    @BeforeEach
    void setUp() {
        validator = new DateCoherentValidatorStep();
    }

    @Test
    @DisplayName("doit passer lorsque la date de début est avant la date de fin")
    void shouldPassWhenStartDateBeforeEndDate() {
        Contract contract = Contract.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .vehicleRegistration("AA-111-BB")
                .idClient(UUID.randomUUID())
                .state(ContractState.PENDING)
                .build();

        // Ne doit pas lever d'exception
        validator.check(contract);
    }

    @Test
    @DisplayName("doit lever BadRequestException lorsque la date de début est après la date de fin")
    void shouldThrowBadRequestWhenStartDateAfterEndDate() {
        Contract invalidContract = Contract.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now())
                .vehicleRegistration("AA-111-BB")
                .idClient(UUID.randomUUID())
                .state(ContractState.PENDING)
                .build();

        assertThrows(BadRequestException.class, () -> validator.check(invalidContract));
    }

    @Test
    @DisplayName("doit lever BadRequestException lorsque la date de début est égale à la date de fin")
    void shouldThrowBadRequestWhenStartDateEqualsEndDate() {
        LocalDate sameDate = LocalDate.now();
        Contract invalidContract = Contract.builder()
                .startDate(sameDate.plusYears(1))
                .endDate(sameDate)
                .vehicleRegistration("AA-111-BB")
                .idClient(UUID.randomUUID())
                .state(ContractState.PENDING)
                .build();

        assertThrows(BadRequestException.class, () -> validator.check(invalidContract));
    }
}
