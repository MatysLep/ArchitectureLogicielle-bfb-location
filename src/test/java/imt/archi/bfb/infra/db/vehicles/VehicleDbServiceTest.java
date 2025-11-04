package imt.archi.bfb.infra.db.vehicles;

import imt.archi.bfb.core.common.model.StateEnum;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.repositories.VehicleRepository;
import imt.archi.bfb.infra.db.vehicles.repositories.entities.VehicleEntity;
import imt.archi.bfb.infra.db.vehicles.repositories.mappers.VehicleDbMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.function.Executable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VehicleDbService")
class VehicleDbServiceTest {
    @Mock
    private VehicleRepository vehicleRepo;

    @Mock
    private VehicleDbMapper vehicleDbMapper;

    @InjectMocks
    private VehiclesDbService vehiclesDbService;

    private Vehicle vehicle;
    private VehicleEntity vehicleEntity;

    String generateRegistration() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String nums = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(7);
        sb.append(chars.charAt(random.nextInt(chars.length())));
        sb.append(chars.charAt(random.nextInt(chars.length())));
        sb.append(nums.charAt(random.nextInt(nums.length())));
        sb.append(nums.charAt(random.nextInt(nums.length())));
        sb.append(nums.charAt(random.nextInt(nums.length())));
        sb.append(chars.charAt(random.nextInt(chars.length())));
        sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }

    @BeforeEach
    void init() {
        String registration = generateRegistration();
        vehicle = Vehicle.builder()
                .registration(registration)
                .brand("BRAND")
                .model("MODEL")
                .motorization("MOTOR")
                .color("COLOR")
                .acquisitionDate(new Date())
                .state(StateEnum.AVAILABLE)
                .build();

        vehicleEntity = VehicleEntity.builder()
                .registration(vehicle.getRegistration())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .motorization(vehicle.getMotorization())
                .color(vehicle.getColor())
                .acquisitionDate(vehicle.getAcquisitionDate())
                .state(vehicle.getState())
                .build();
    }

    @Nested
    @DisplayName("exist - vérification d'existence")
    class Exist {

        @Test
        @DisplayName("doit retourner vrai lorsque le repository trouve la plaque d'immatriculation")
        void shouldReturnTrueWhenRepositoryFindsIdentifier() {
            // Given
            when(vehicleRepo.existsById(vehicleEntity.getRegistration())).thenReturn(true);

            // When
            boolean exists = vehiclesDbService.exist(vehicleEntity.getRegistration());

            // Then
            assertTrue(exists);
            verify(vehicleRepo).existsById(vehicleEntity.getRegistration());
        }

        @Test
        @DisplayName("doit retourner faux lorsque la plaque d'immatriculation est nul")
        void shouldReturnFalseWhenIdentifierIsNull() {
            // Given
            // When
            boolean exists = vehiclesDbService.exist(null);

            // Then
            assertFalse(exists);
            verify(vehicleRepo, never()).existsById(any());
        }
    }

    @Nested
    @DisplayName("getAll - récupération complète")
    class GetAll {

        @Test
        @DisplayName("doit retourner les véhicules mappés lorsque le repository renvoie des entités")
        void shouldReturnMappedClientsWhenRepositoryReturnsEntities() {
            // Given
            when(vehicleRepo.findAll()).thenReturn(List.of(vehicleEntity));
            when(vehicleDbMapper.from(vehicleEntity)).thenReturn(vehicle);

            // When
            Collection<Vehicle> result = vehiclesDbService.getAll();

            // Then
            assertEquals(Set.of(vehicle), new HashSet<>(result));
            verify(vehicleDbMapper).from(vehicleEntity);
        }

        @Test
        @DisplayName("doit retourner un ensemble vide lorsque le repository renvoie null")
        void shouldReturnEmptySetWhenRepositoryReturnsNull() {
            // Given
            when(vehicleRepo.findAll()).thenReturn(null);

            // When
            Collection<Vehicle> result = vehiclesDbService.getAll();

            // Then
            assertTrue(result.isEmpty());
            verify(vehicleDbMapper, never()).from(any());
        }
    }

    @Nested
    @DisplayName("get - récupération unitaire")
    class Get {

        @Test
        @DisplayName("doit retourner le véhicule mappé lorsque le repository trouve la plaque")
        void shouldReturnMappedClientWhenRepositoryFindsIdentifier() {
            // Given
            when(vehicleRepo.findById(vehicleEntity.getRegistration())).thenReturn(Optional.of(vehicleEntity));
            when(vehicleDbMapper.from(vehicleEntity)).thenReturn(vehicle);

            // When
            Optional<Vehicle> result = vehiclesDbService.get(vehicleEntity.getRegistration());

            // Then
            assertTrue(result.isPresent());
            assertSame(vehicle, result.orElseThrow());
            verify(vehicleDbMapper).from(vehicleEntity);
        }

        @Test
        @DisplayName("doit retourner un Optional vide lorsque l'identifiant est nul")
        void shouldReturnEmptyOptionalWhenIdentifierIsNull() {
            // Given
            // When
            Optional<Vehicle> result = vehiclesDbService.get(null);

            // Then
            assertTrue(result.isEmpty());
            verify(vehicleRepo, never()).findById(any());
        }

        @Test
        @DisplayName("doit retourner un Optional vide lorsque le repository ne trouve pas la plaque")
        void shouldReturnEmptyOptionalWhenRepositoryDoesNotFindIdentifier() {
            // Given
            when(vehicleRepo.findById(vehicleEntity.getRegistration())).thenReturn(Optional.empty());

            // When
            Optional<Vehicle> result = vehiclesDbService.get(vehicleEntity.getRegistration());

            // Then
            assertTrue(result.isEmpty());
            verify(vehicleDbMapper, never()).from(any());
        }
    }

    @Nested
    @DisplayName("save - sauvegarde véhicule")
    class Save {

        @Test
        @DisplayName("doit persister le client et retourner le résultat mappé")
        void shouldPersistClientAndReturnMappedResult() {
            // Given
            when(vehicleDbMapper.to(vehicle)).thenReturn(vehicleEntity);
            when(vehicleRepo.save(vehicleEntity)).thenReturn(vehicleEntity);
            when(vehicleDbMapper.from(vehicleEntity)).thenReturn(vehicle);

            // When
            Vehicle result = vehiclesDbService.save(vehicle);

            // Then
            assertSame(vehicle, result);
            verify(vehicleRepo).save(vehicleEntity);
        }

        @Test
        @DisplayName("doit lever NullPointerException lorsque le véhicule est nul")
        void shouldThrowNullPointerExceptionWhenClientIsNull() {
            // Given
            // When
            Executable invocation = () -> vehiclesDbService.save(null);

            // Then
            assertThrows(NullPointerException.class, invocation);
            verify(vehicleRepo, never()).save(any());
        }
    }

    @Nested
    @DisplayName("delete - suppression véhicule")
    class Delete {

        @Test
        @DisplayName("doit supprimer le véhicule lorsque la plaque est fournie")
        void shouldDeleteClientWhenIdentifierProvided() {
            // Given
            String registration = vehicleEntity.getRegistration();

            // When
            vehiclesDbService.delete(registration);

            // Then
            verify(vehicleRepo).deleteById(vehicleEntity.getRegistration());
        }

        @Test
        @DisplayName("ne doit pas supprimer lorsque la plaque est nulle")
        void shouldNotDeleteWhenIdentifierIsNull() {
            // Given
            // When
            vehiclesDbService.delete(null);

            // Then
            verify(vehicleRepo, never()).deleteById(any());
        }
    }
}
