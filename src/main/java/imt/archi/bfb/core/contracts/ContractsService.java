package imt.archi.bfb.core.contracts;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.core.common.model.ContractState;
import imt.archi.bfb.infra.db.contracts.ContractsDbService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractsService {
    
    protected ContractsDbService contractsDbService;

    public Collection<Contract> getAll(){
        return Objects.requireNonNullElse(contractsDbService.getAll(), Collections.emptySet());
    }

    public Optional<Contract> get(final UUID id) {
        return contractsDbService.get(id);
    }

    public Contract create(final Contract newContract) {
        return contractsDbService.save(newContract);
    }

    public void update(final Contract updatedContract) {
        contractsDbService.save(updatedContract);
    }

    public void cancelContracts(final String idVehicle) {
        contractsDbService.getAllByVehicle(idVehicle).stream()
                .filter(contract -> ContractState.PENDING.equals(contract.getState()))
                .map(contract -> contract.toBuilder().state(ContractState.CANCELLED).build())
                .forEach(this::update);
    }

    public void updateOverdueAndConflictingContracts() {
        final LocalDate today = LocalDate.now();
        final Collection<Contract> allContracts = getAll();

        // Passer en LATE les contrats actifs dont la date de fin est passée
        List<Contract> lateContracts = allContracts.stream()
                // On suppose un état ACTIVE ou ON_GOING
                .filter(c -> ContractState.IN_PROGRESS.equals(c.getState()))
                .filter(c -> c.getEndDate().isBefore(today))
                .map(c -> c.toBuilder().state(ContractState.DELAYED).build())
                .toList();

        lateContracts.forEach(this::update);

        // CANCELLED les contrats futurs qui ne peuvent pas démarrer
        List<String> vehiclesIdsInLate = allContracts.stream()
                .filter(c -> ContractState.DELAYED.equals(c.getState()) || lateContracts.contains(c))
                .map(Contract::getVehicleRegistration)
                .distinct()
                .toList();

        allContracts.stream()
                .filter(c -> ContractState.PENDING.equals(c.getState())) // Contrat en attente
                .filter(c -> vehiclesIdsInLate.contains(c.getVehicleRegistration())) // Sur un véhicule en retard
                .filter(c -> !c.getStartDate().isAfter(today)) // Qui aurait dû commencer aujourd'hui ou avant
                .map(c -> c.toBuilder().state(ContractState.CANCELLED).build())
                .forEach(this::update);
    }

    public void delete(final UUID id) {
        contractsDbService.delete(id);
    }

}
