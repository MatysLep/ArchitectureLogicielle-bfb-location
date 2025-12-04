package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("VehicleIdExistValidatorStep - validation impossible de lire les contrats d'un véhicule qui n'existe pas")
public class VehicleIdExistValidatorStepTest {

    @Mock
    private VehiclesDbService vehiclesDbService;

    private VehicleIdExistValidatorStep validator;

    @BeforeEach
    void setUp() {
        validator = new VehicleIdExistValidatorStep(vehiclesDbService);
    }

    @Test
    void shouldPassWhenVehicleExists() {
        Vehicle vehicle = Vehicle.builder().registration("AA-111-BB").build();
        when(vehiclesDbService.getAll()).thenReturn(Set.of(vehicle));

        validator.validate("AA-111-BB").throwIfValid();
    }

    @Test
    void shouldThrowNotFoundWhenVehicleDoesNotExist() {
        when(vehiclesDbService.getAll()).thenReturn(Collections.emptySet());

        assertThrows(NotFoundException.class, () -> validator.validate("AA-111-BB").throwIfValid());
    }
}
