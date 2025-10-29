package imt.archi.bfb.core.common.validators;

import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ConstraintValidatorStep<T> extends AbstractValidatorStep<T> {

    @Override
    public void check(final T toValidate) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            final Set<ConstraintViolation<T>> violations = factory.getValidator().validate(toValidate);
            if(!violations.isEmpty()) {
                throw new BadRequestException(String.format("Validation failed: %s", violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "))));
            }
        }
    }
}
