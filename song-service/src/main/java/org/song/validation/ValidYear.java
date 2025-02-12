package org.song.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.song.validation.impl.ValidYearValidator;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidYearValidator.class)
public @interface ValidYear {

    String yearFormat();
    String minValue();
    String maxValue();

    String message() default "Sorry, year has a wrong format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
