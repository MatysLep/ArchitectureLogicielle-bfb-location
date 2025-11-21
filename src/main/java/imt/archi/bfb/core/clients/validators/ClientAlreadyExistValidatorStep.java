package imt.archi.bfb.core.clients.validators;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import imt.archi.bfb.interfaces.rest.common.exception.ConflictException;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Objects;

@AllArgsConstructor
public class ClientAlreadyExistValidatorStep extends AbstractValidatorStep<Client> {
    protected ClientBddService service;

    @Override
    public void check(final Client toValidate) {
        if(Objects.requireNonNullElse(this.service.getAll(), Collections.<Client>emptySet()).stream().anyMatch(alreadySaved -> this.isSameThing(toValidate, alreadySaved))) {
            throw new ConflictException(String.format("Un client ayant ses infos existe déjà : name : %s, surname : %s, birthdate : %s", toValidate.getName(), toValidate.getSurname(), toValidate.getBirthDate()));
        }
    }

    private boolean isSameThing(final Client input, final Client alreadySaved){
        boolean sameName = alreadySaved.getName().equalsIgnoreCase(input.getName());
        boolean sameSurname = alreadySaved.getSurname().equalsIgnoreCase(input.getSurname());
        boolean sameBirthDate = alreadySaved.getBirthDate().equals(input.getBirthDate());
        boolean differentId = !alreadySaved.getId().equals(input.getId());

        return sameName && sameSurname && sameBirthDate && differentId;
    }
}

