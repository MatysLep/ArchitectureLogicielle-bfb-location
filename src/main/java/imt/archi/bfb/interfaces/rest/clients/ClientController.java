package imt.archi.bfb.interfaces.rest.clients;

import imt.archi.bfb.core.clients.ClientService;
import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.interfaces.rest.clients.model.input.ClientInput;
import imt.archi.bfb.interfaces.rest.clients.model.input.ClientUpdateInput;
import imt.archi.bfb.interfaces.rest.clients.model.output.ClientOutput;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {
    ClientService service;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ClientOutput> getAll() {
        return Objects.requireNonNullElse(this.service.getAll(), Collections.<Client>emptySet()).stream()
                .map(ClientOutput::from)
                .collect(Collectors.toSet());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientOutput create(@RequestBody final ClientInput client) {
        return ClientOutput.from(
                this.service.create(
                        ClientInput.convert(client)
                )
        );
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientOutput getOne(@PathVariable("idClient") final String id) {
        return this.service.getOne(UUID.fromString(id))
                .map(ClientOutput::from)
                .orElseThrow(() -> new NotFoundException(String.format("Le client d'identifiant %s n'a pas été trouvé.", id)));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{idClient}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("idClient") final String id, @RequestBody final ClientUpdateInput client) {
        this.service.update(
                this.service.getOne(UUID.fromString(id))
                        .map(alreadySaved -> ClientUpdateInput.from(client, alreadySaved))
                        .orElseThrow(() -> new NotFoundException(String.format("Le client d'identifiant %s n'a pas été trouvé.", id)))
        );
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{idClient}")
    public void delete(@PathVariable("idClient") final String id) {
        this.service.delete(UUID.fromString(id));
    }


}
