package imt.archi.bfb.core.common.validators;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Constraint(validatedBy = DateValidator.Validator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidator {
    String message() default "Format de date invalide. (dd/MM/yyyy)";
    boolean required() default false;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<DateValidator, String> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

        private boolean isRequired;

        @Override
        public void initialize(DateValidator constraintAnnotation) {
            this.isRequired = constraintAnnotation.required();
        }

        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            if(s == null) return !this.isRequired;
            try {
                LocalDate.parse(s.trim(), formatter);
                return true;
            }catch (DateTimeParseException e) {
                return false;
            }
        }
    }
}
