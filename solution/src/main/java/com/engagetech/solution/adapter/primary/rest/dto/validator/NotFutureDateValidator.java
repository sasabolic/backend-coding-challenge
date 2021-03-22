package com.engagetech.solution.adapter.primary.rest.dto.validator;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates expense related date.
 * This date should not be in the future, only past and present date is valid.
 */
public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, String> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    @Override
    public void initialize(NotFutureDate constraintAnnotation) {
        String pattern = constraintAnnotation.pattern();
        if (!pattern.isEmpty()) {
            this.formatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern(pattern))
                    .optionalStart()
                    .appendOffsetId()
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT)
                    .withChronology(IsoChronology.INSTANCE);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            return LocalDate.parse(value, formatter).isBefore(LocalDate.now().plusDays(1));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
