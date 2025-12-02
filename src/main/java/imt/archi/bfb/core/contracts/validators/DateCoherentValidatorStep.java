package imt.archi.bfb.core.contracts.validators;

import imt.archi.bfb.core.common.validators.AbstractValidatorStep;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;
import lombok.AllArgsConstructor;

@AllArgsConstructor 
public class DateCoherentValidatorStep extends AbstractValidatorStep<Contract> {

    @Override
    public void check(Contract toValidate) {
        if (toValidate.getEndDate().isBefore(toValidate.getStartDate())) {
            throw new BadRequestException("La date de début du contrat doit être antérieur à celle de fin.");
        }
    }
    
}
