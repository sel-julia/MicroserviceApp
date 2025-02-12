package org.song.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.song.validation.impl.ValidDurationFormatValidation;
import org.song.validation.impl.ValidYearValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidDurationFormatValidation.class)
public @interface ValidDurationFormat {

    String timeFormat();

    String message() default "Duration must be in the format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
