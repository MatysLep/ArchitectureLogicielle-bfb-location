package imt.archi.bfb.core.common.model;

import imt.archi.bfb.interfaces.rest.common.exception.AbstractRestException;
import imt.archi.bfb.interfaces.rest.common.exception.BadRequestException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ValidatorResult {

    private final boolean isValid;
    private final AbstractRestException exceptionToThrow;

    public static ValidatorResult valid() {return ValidatorResult.builder().isValid(true).build();}

    public static ValidatorResult invalid(final String message) {return ValidatorResult.builder().isValid(false).exceptionToThrow(new BadRequestException(message)).build();}

    public static ValidatorResult invalid(final AbstractRestException exception) {return ValidatorResult.builder().isValid(false).exceptionToThrow(exception).build();}

    public void throwIfValid() {
        if (!isValid) {
            throw exceptionToThrow;
        }
    }
}
