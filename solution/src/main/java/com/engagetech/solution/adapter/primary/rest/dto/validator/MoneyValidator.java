package com.engagetech.solution.adapter.primary.rest.dto.validator;

import java.util.Arrays;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates expense amount.
 * The amount is only accepted in the provided format and / or with supported currencies.
 */
public class MoneyValidator implements ConstraintValidator<Money, String> {


  public static final String REGEX = "^(\\d+(?:\\.\\d{1,2})?)(\\s([A-Z]{3}))?$";

  public static final Pattern PATTERN = Pattern.compile(REGEX);

  private Money constraintAnnotation;

  @Override
  public void initialize(Money constraintAnnotation) {
    this.constraintAnnotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    Matcher matcher = PATTERN.matcher(value);

    if (matcher.matches()) {
      String currency = matcher.group(3);

      if (currency == null) {
        return true;
      }

      boolean isSupported = Arrays.asList(constraintAnnotation.supportedCurrencies()).contains(currency);

      if (!isSupported) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
          String.format("Currency '%s' is not supported", currency))
          .addConstraintViolation();

        return false;
      }

      try {
        Currency.getInstance(currency);
      } catch (IllegalArgumentException e) {
        return false;
      }

      return true;
    }

    return false;
  }
}
