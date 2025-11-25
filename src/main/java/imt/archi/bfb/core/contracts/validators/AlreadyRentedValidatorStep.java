package imt.archi.bfb.core.contracts.validators;

import java.util.Collections;
import java.util.Objects;

import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.infra.db.contracts.ContractsDbService;
import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlreadyRentedValidatorStep extends AbstractValidatorStep<Contract> {

    protected ContractsDbService service;

    @Override
    public void check(Contract toValidate) {
        Contract conflictingContract = Objects
            .requireNonNullElse(service.getAll(), Collections.<Contract>emptySet())
            .stream()
            .filter(c -> checkDate(c, toValidate))
            .findFirst()
            .orElse(null);

        if (conflictingContract != null) {
            throw new BadRequestException(String.format(
                "Les dates du nouveau contrat empiètent sur le contrat existant d'id %s.",
                conflictingContract.getId()
            ));
        }
    }

    private boolean checkDate(final Contract existing, final Contract toValidate) {
        return existing.getVehicleRegistration().equals(toValidate.getVehicleRegistration())
                && toValidate.getEndDate().isAfter(existing.getStartDate())
                && toValidate.getStartDate().isBefore(existing.getEndDate());
    }

}
