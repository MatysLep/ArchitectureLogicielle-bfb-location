package imt.archi.bfb.core.common.validators;

import imt.archi.bfb.core.common.model.ValidatorResult;
import imt.archi.bfb.interfaces.rest.common.exception.AbstractRestException;

import java.util.Objects;

public abstract class AbstractValidatorStep<T> {
    private AbstractValidatorStep<T> nextStep;

    public abstract void check(final T toValidate);

    public ValidatorResult validate(final T toValidate) {
        Objects.requireNonNull(toValidate, "toValidate null");
        try {
            check(toValidate);
        }catch (final AbstractRestException e) {
            return ValidatorResult.invalid(e);
        }

        if(Objects.nonNull(nextStep)) {
            return nextStep.validate(toValidate);
        }
        return ValidatorResult.valid();
    }

    public AbstractValidatorStep<T> linkWith(final AbstractValidatorStep<T> nextStep) {
        if(Objects.isNull(this.nextStep)) {
            this.nextStep = nextStep;
        }else  {
            this.nextStep.linkWith(nextStep);
        }
        return this;
    }
}
