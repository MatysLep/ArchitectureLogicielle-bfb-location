package imt.archi.bfb.core.vehicles.validators;

import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
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

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("VehicleAlreadyExistValidatorStep - validations existence véhicule")
class VehicleAlreadyExistValidatorStepTest {
    @Mock
    private VehiclesDbService vehiclesDbService;

    @InjectMocks
    private VehicleAlreadyExistValidatorStep validator;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = Vehicle.builder()
                .registration("AABBBCC")
                .brand("BRAND")
                .model("MODEL")
                .motorization("MOTOR")
                .color("COLOR")
                .acquisitionDate(new Date())
                .state(VehicleState.AVAILABLE)
                .build();
    }

    @Nested
    @DisplayName("check - validation de la présence d'un véhicule identique")
    class Check {

        @Test
        @DisplayName("doit lever une ConflictException lorsqu'un véhicule identique existe déjà")
        void shouldThrowConflictExceptionWhenClientAlreadyExists() {
            // Given
            Vehicle existingClient = vehicle.toBuilder().build();
            when(vehiclesDbService.getAll()).thenReturn(Set.of(existingClient));

            // When
            Executable checkCall = () -> validator.check(vehicle);

            // Then
            assertThrows(ConflictException.class, checkCall);
        }

        @Test
        @DisplayName("ne doit pas lever d'exception lorsqu'aucun véhicule correspondant n'est trouvé")
        void shouldNotThrowWhenClientDoesNotExist() {
            // Given
            Vehicle differentVehicle =Vehicle.builder()
                    .registration("BBCCCDD")
                    .brand("BRAND")
                    .model("MODEL")
                    .motorization("MOTOR")
                    .color("COLOR")
                    .acquisitionDate(new Date())
                    .state(VehicleState.AVAILABLE)
                    .build();
            when(vehiclesDbService.getAll()).thenReturn(Set.of(differentVehicle));

            // When
            Executable checkCall = () -> validator.check(vehicle);

            // Then
            assertDoesNotThrow(checkCall);
        }

        @Test
        @DisplayName("ne doit pas lever d'exception lorsque le service retourne null")
        void shouldNotThrowWhenServiceReturnsNull() {
            // Given
            when(vehiclesDbService.getAll()).thenReturn(null);

            // When
            Executable checkCall = () -> validator.check(vehicle);

            // Then
            assertDoesNotThrow(checkCall);
        }
    }
}
