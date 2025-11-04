package imt.archi.bfb.infra.db.clients;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.infra.db.clients.repositories.entity.ClientEntity;
import imt.archi.bfb.infra.db.clients.repositories.ClientRepository;
import imt.archi.bfb.infra.db.clients.repositories.mappers.ClientBddMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientBddService {
    private ClientRepository repository;
    private ClientBddMapper mapper;


    public boolean exist(final UUID id){
        return Optional.ofNullable(id)
                .map(UUID::toString)
                .map(this.repository::existsById)
                .orElse(false);
    }

    public Collection<Client> getAll() {
        return Objects.requireNonNullElse(this.repository.findAll(), Collections.<ClientEntity>emptyList())
                .stream()
                .map(this.mapper::from)
                .collect(Collectors.toSet());
    }

    public Optional<Client> get(final UUID id){
        return Optional.ofNullable(id)
                .map(UUID::toString)
                .flatMap(this.repository::findById)
                .map(this.mapper::from);
    }

    public Client save(final Client client){
        Objects.requireNonNull(client, "Impossible de sauvegarder un client nul");
        return this.mapper.from(
                this.repository.save(
                        this.mapper.to(client)
                )
        );
    }

    public void delete(final UUID id){
        Optional.ofNullable(id)
                .map(UUID::toString)
                .ifPresent(this.repository::deleteById);
    }
}