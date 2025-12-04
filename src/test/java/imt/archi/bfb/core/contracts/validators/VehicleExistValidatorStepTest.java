package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;
import imt.archi.bfb.core.contracts.model.Contract;
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
@DisplayName("VehicleExistValidatorStep - validation impossible de louer un véhicule qui n'existe pas")
public class VehicleExistValidatorStepTest {

    @Mock
    private VehiclesDbService vehiclesDbService;

    private VehicleExistValidatorStep validator;
    private Contract contract;

    @BeforeEach
    void setUp() {
        validator = new VehicleExistValidatorStep(vehiclesDbService);
        contract = Contract.builder()
                .idClient(null)
                .vehicleRegistration("AA-111-BB")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .state(null)
                .build();
    }

    @Test
    void shouldPassWhenVehicleExists() {
        Vehicle vehicle = Vehicle.builder().registration("AA-111-BB").build();
        when(vehiclesDbService.getAll()).thenReturn(Set.of(vehicle));

        validator.validate(contract).throwIfValid();
    }

    @Test
    void shouldThrowNotFoundWhenVehicleDoesNotExist() {
        when(vehiclesDbService.getAll()).thenReturn(Collections.emptySet());

        assertThrows(NotFoundException.class, () -> validator.validate(contract).throwIfValid());
    }
}
