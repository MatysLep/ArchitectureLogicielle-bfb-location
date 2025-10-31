package imt.archi.bfb.core.clients;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientBddService clientBddService;

    public Collection<Client> getAll() {
        return Objects.requireNonNullElse(this.clientBddService.getAll(), Collections.emptySet());
    }

    public Optional<Client> getOne(final UUID identifier) {
        return this.clientBddService.get(identifier);
    }

    public Client create(final Client newClient) {
        return this.clientBddService.save(newClient);
    }

    public void update(final Client updatedClient) {
        this.clientBddService.save(updatedClient);
    }

    public void delete(final UUID identifier) {
        this.clientBddService.delete(identifier);
    }
}
