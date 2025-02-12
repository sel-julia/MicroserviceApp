package org.song.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.song.validation.ValidDurationFormat;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ValidDurationFormatValidation implements ConstraintValidator<ValidDurationFormat, Object> {

    private String timeFormat;
    @Override
    public void initialize(ValidDurationFormat constraintAnnotation) {
        timeFormat = constraintAnnotation.timeFormat();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        if (ObjectUtils.isEmpty(value)) {
            return true;
        }

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(timeFormat, Locale.US);
        try {
            sdf.parse(value.toString());
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
