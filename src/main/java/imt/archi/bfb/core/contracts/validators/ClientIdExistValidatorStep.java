package imt.archi.bfb.core.contracts.validators;

import java.util.Collections;
import java.util.Objects;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientIdExistValidatorStep extends AbstractValidatorStep<String> {

    protected ClientBddService service;

    @Override
    public void check(final String idClient) {
        boolean clientExists = Objects
                .requireNonNullElse(service.getAll(), Collections.<Client>emptySet())
                .stream()
                .anyMatch(client -> client.getId().toString().equals(idClient));

        if (!clientExists) {
            throw new NotFoundException(String.format("Aucun client avec l'id %s n'existe en base", idClient));
        }
    }
    
}
