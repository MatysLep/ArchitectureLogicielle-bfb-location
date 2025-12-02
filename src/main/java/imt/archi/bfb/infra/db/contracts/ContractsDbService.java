package imt.archi.bfb.infra.db.contracts;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.contracts.repositories.ContractRepository;
import imt.archi.bfb.infra.db.contracts.repositories.entities.ContractEntity;
import imt.archi.bfb.infra.db.contracts.repositories.mapper.ContractDbMapper;

@Service
@AllArgsConstructor
public class ContractsDbService {

    private final ContractRepository repository;
    private final ContractDbMapper mapper;

    public Collection<Contract> getAll() {
        return Objects.requireNonNullElse(repository.findAll(), Collections.<ContractEntity>emptyList())
                .stream()
                .map(mapper::from)
                .collect(Collectors.toSet());
    }

    public Optional<Contract> get(final UUID identifier){
        return Optional.ofNullable(identifier)
                .map(UUID::toString)
                .flatMap(repository::findById)
                .map(mapper::from);
    }

    public Collection<Contract> getAllByVehicle(final String idVehicle) {
        return Optional.ofNullable(idVehicle)
                .map(repository::findByVehicleRegistration)
                .map(contracts -> contracts.stream().map(mapper::from).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    public Collection<Contract> getAllByClient(final String idClient) {
        return Optional.ofNullable(idClient)
                .map(repository::findByIdClient)
                .map(contracts -> contracts.stream().map(mapper::from).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    public Contract save(final Contract Contract){
        Objects.requireNonNull(Contract, "Impossible de sauvegarder un contrat nul");
        return mapper.from(
                repository.save(
                        mapper.to(Contract)
                )
        );
    }

    public void delete(final UUID identifier){
        Optional.ofNullable(identifier)
                .map(UUID::toString)
                .ifPresent(repository::deleteById);
    }

}
