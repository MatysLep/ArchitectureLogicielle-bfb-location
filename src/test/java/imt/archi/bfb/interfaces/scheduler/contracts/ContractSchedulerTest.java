package imt.archi.bfb.interfaces.scheduler.contracts;

import imt.archi.bfb.core.contracts.ContractsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContractSchedulerTest {

    @Mock
    private ContractsService contractsService;

    @InjectMocks
    private ContractScheduler contractScheduler;

    @Test
    void testProcessContractStatuses() {
        // Act
        contractScheduler.processContractStatuses();

        // Assert
        // Vérifie que la méthode du service est bien appelée une fois
        verify(contractsService, times(1)).updateOverdueAndConflictingContracts();
    }
}
