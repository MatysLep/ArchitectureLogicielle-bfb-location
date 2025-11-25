package imt.archi.bfb.interfaces.scheduler.contracts;

import imt.archi.bfb.core.contracts.ContractsService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContractScheduler {

    private final ContractsService contractsService;

    @Scheduled(cron = "0 0 * * * *")
    public void processContractStatuses() {
        contractsService.updateOverdueAndConflictingContracts();
    }
}