package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("BrokenVehicleValidatorStep - validation impossible de louer un véhicule cassé")
public class BrokenVehicleValidatorStepTest {

    @Mock
    private VehiclesDbService vehiclesDbService;

    private BrokenVehicleValidatorStep validator;

    private Contract contract;

    @BeforeEach
    void setUp() {
        validator = new BrokenVehicleValidatorStep(vehiclesDbService);

        contract = Contract.builder()
                .vehicleRegistration("AA-111-BB")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .state(null)
                .idClient(null)
                .build();
    }

    @Test
    void shouldPassWhenVehicleNotBroken() {
        Vehicle vehicle = Vehicle.builder()
                .registration("AA-111-BB")
                .state(VehicleState.AVAILABLE)
                .build();
        when(vehiclesDbService.get("AA-111-BB")).thenReturn(Optional.of(vehicle));

        validator.validate(contract).throwIfValid();
    }

    @Test
    void shouldThrowBadRequestWhenVehicleBroken() {
        Vehicle vehicle = Vehicle.builder()
                .registration("AA-111-BB")
                .state(VehicleState.BROKEN)
                .build();
        when(vehiclesDbService.get("AA-111-BB")).thenReturn(Optional.of(vehicle));

        assertThrows(BadRequestException.class, () -> validator.validate(contract).throwIfValid());
    }
}
