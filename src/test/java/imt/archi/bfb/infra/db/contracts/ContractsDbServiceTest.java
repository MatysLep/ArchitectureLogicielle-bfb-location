package imt.archi.bfb.infra.db.contracts;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.contracts.repositories.ContractRepository;
import imt.archi.bfb.infra.db.contracts.repositories.entities.ContractEntity;
import imt.archi.bfb.infra.db.contracts.repositories.mapper.ContractDbMapper;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContractsDbService - interactions avec la base")
class ContractsDbServiceTest {

    @Mock
    private ContractRepository repository;

    @Mock
    private ContractDbMapper mapper;

    @InjectMocks
    private ContractsDbService contractsDbService;

    private Contract contract;
    private ContractEntity entity;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        contract = Contract.builder()
                .id(id)
                .idClient(UUID.randomUUID())
                .vehicleRegistration("C-12345")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .build();

        entity = ContractEntity.builder()
                .id(id.toString())
                .idClient(contract.getIdClient().toString())
                .vehicleRegistration(contract.getVehicleRegistration())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .build();
    }

    @Nested
    @DisplayName("getAll - récupération complète")
    class GetAll {

        @Test
        void shouldReturnMappedContractsWhenRepositoryReturnsEntities() {
            when(repository.findAll()).thenReturn(List.of(entity));
            when(mapper.from(entity)).thenReturn(contract);

            Collection<Contract> result = contractsDbService.getAll();
            assertEquals(Set.of(contract), new HashSet<>(result));
            verify(mapper).from(entity);
        }

        @Test
        void shouldReturnEmptySetWhenRepositoryReturnsNull() {
            when(repository.findAll()).thenReturn(null);
            Collection<Contract> result = contractsDbService.getAll();
            assertTrue(result.isEmpty());
            verify(mapper, never()).from(any());
        }
    }

    @Nested
    @DisplayName("get - récupération unitaire")
    class Get {

        @Test
        void shouldReturnMappedContractWhenRepositoryFindsId() {
            when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
            when(mapper.from(entity)).thenReturn(contract);

            Optional<Contract> result = contractsDbService.get(UUID.fromString(entity.getId()));
            assertTrue(result.isPresent());
            assertSame(contract, result.orElseThrow());
            verify(mapper).from(entity);
        }

        @Test
        void shouldReturnEmptyOptionalWhenIdIsNull() {
            Optional<Contract> result = contractsDbService.get(null);
            assertTrue(result.isEmpty());
            verify(repository, never()).findById(any());
        }

        @Test
        void shouldReturnEmptyOptionalWhenRepositoryDoesNotFindId() {
            when(repository.findById(entity.getId())).thenReturn(Optional.empty());
            Optional<Contract> result = contractsDbService.get(UUID.fromString(entity.getId()));
            assertTrue(result.isEmpty());
            verify(mapper, never()).from(any());
        }
    }

    @Nested
    @DisplayName("save - sauvegarde contrat")
    class Save {

        @Test
        void shouldPersistContractAndReturnMappedResult() {
            when(mapper.to(contract)).thenReturn(entity);
            when(repository.save(entity)).thenReturn(entity);
            when(mapper.from(entity)).thenReturn(contract);

            Contract result = contractsDbService.save(contract);
            assertSame(contract, result);
            verify(repository).save(entity);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenContractIsNull() {
            Executable invocation = () -> contractsDbService.save(null);
            assertThrows(NullPointerException.class, invocation);
            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("delete - suppression contrat")
    class Delete {

        @Test
        void shouldDeleteContractWhenIdProvided() {
            UUID id = UUID.fromString(entity.getId());
            contractsDbService.delete(id);
            verify(repository).deleteById(entity.getId());
        }

        @Test
        void shouldNotDeleteWhenIdIsNull() {
            contractsDbService.delete(null);
            verify(repository, never()).deleteById(any());
        }
    }
}
