package imt.archi.bfb.core.clients.validators;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import imt.archi.bfb.interfaces.rest.common.exception.ConflictException;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Objects;

@AllArgsConstructor
public class NumberDriverLicenceAlreadyExistValidatorStep extends AbstractValidatorStep<Client>{

    protected ClientBddService service;

    @Override
    public void check(final Client toValidate) {
        if(Objects.requireNonNullElse(this.service.getAll(), Collections.<Client>emptySet()).stream().anyMatch(alreadySaved -> this.isSameDriverLicenseNumber(toValidate, alreadySaved))) {
            throw new ConflictException(String.format("Un client ayant ce numéro de permis de conduire existe déjà : %s.", toValidate.getDriverLicenseNumber()));
        }
    }

    private boolean isSameDriverLicenseNumber(final Client input, final Client alreadySaved){
        boolean sameLicenseNumber = alreadySaved.getDriverLicenseNumber().equalsIgnoreCase(input.getDriverLicenseNumber());
        boolean differentUser = !alreadySaved.getId().equals(input.getId());
        return sameLicenseNumber && differentUser;
    }
}
