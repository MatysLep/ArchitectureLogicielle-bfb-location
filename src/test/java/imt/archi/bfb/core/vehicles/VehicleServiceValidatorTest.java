package imt.archi.bfb.core.vehicles;

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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VehicleServiceValidator - validation métier véhicule")
class VehicleServiceValidatorTest {
    @Mock
    private VehiclesDbService vehiclesDbService;

    @InjectMocks
    private VehiclesServiceValidator vehiclesServiceValidator;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = Vehicle.builder()
                .registration("AA-345-CC")
                .brand("BRAND")
                .model("MODEL")
                .motorization("MOTOR")
                .color("COLOR")
                .acquisitionDate(LocalDate.now())
                .state(VehicleState.AVAILABLE)
                .build();
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("doit créer un véhicule lorsque les validations passent")
        void shouldCreateClientWhenValidatorsPass() {
            // Given
            when(vehiclesDbService.getAll()).thenReturn(Collections.emptySet());
            when(vehiclesDbService.save(vehicle)).thenReturn(vehicle);

            // When
            Vehicle result = vehiclesServiceValidator.create(vehicle);

            // Then
            assertSame(vehicle, result);
            verify(vehiclesDbService).save(vehicle);
        }

        @Test
        @DisplayName("doit lever une ConflictException lorsqu'un client en conflit existe")
        void shouldThrowConflictExceptionWhenConflictingClientExists() {
            // Given
            Vehicle conflicting = vehicle.toBuilder().build();
            when(vehiclesDbService.getAll()).thenReturn(Set.of(conflicting));

            // When
            Executable invocation = () -> vehiclesServiceValidator.create(vehicle);

            // Then
            assertThrows(ConflictException.class, invocation);
            verify(vehiclesDbService, never()).save(any(Vehicle.class));
        }
    }

    @Nested
    @DisplayName("update - mise à jour véhicule")
    class Update {

        @Test
        @DisplayName("doit mettre à jour le véhicule lorsque les validations passent")
        void shouldUpdateClientWhenValidatorsPass() {
            // Given
            when(vehiclesDbService.save(vehicle)).thenReturn(vehicle);

            // When
            assertDoesNotThrow(() -> vehiclesServiceValidator.update(vehicle));

            // Then
            verify(vehiclesDbService).save(vehicle);
        }
    }
}
