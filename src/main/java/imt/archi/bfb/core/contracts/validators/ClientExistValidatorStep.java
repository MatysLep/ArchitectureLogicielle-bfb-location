package imt.archi.bfb.core.contracts.validators;

import java.util.Collections;
import java.util.Objects;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientExistValidatorStep extends AbstractValidatorStep<Contract> {

    protected ClientBddService service;

    @Override
    public void check(final Contract toValidate) {
        boolean clientExists = Objects
                .requireNonNullElse(service.getAll(), Collections.<Client>emptySet())
                .stream()
                .anyMatch(client -> client.getId().equals(toValidate.getIdClient()));

        if (!clientExists) {
            throw new NotFoundException(String.format("Aucun client avec l'id %s n'existe en base", toValidate.getIdClient()));
        }
    }
    
}
