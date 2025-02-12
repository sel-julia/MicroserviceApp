package org.song.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.song.validation.ValidYear;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;


public class ValidYearValidator implements ConstraintValidator<ValidYear, Object> {
    private Long minValue;
    private Long maxValue;

    private String yearFormat;

    @Override
    public void initialize(ValidYear constraintAnnotation) {
        minValue = Long.parseLong(constraintAnnotation.minValue());
        maxValue = Long.parseLong(constraintAnnotation.maxValue());
        yearFormat = constraintAnnotation.yearFormat();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        if (ObjectUtils.isEmpty(value)) {
            return true;
        }

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(yearFormat, Locale.US);
        try {
            sdf.parse(value.toString());
        } catch (DateTimeParseException e) {
            return false;
        }


        Long year = Long.parseLong(value.toString());

        return year >= minValue && year <= maxValue;
    }
}
