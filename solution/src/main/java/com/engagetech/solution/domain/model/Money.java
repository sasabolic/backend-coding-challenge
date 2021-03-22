package com.engagetech.solution.domain.model;

import static org.springframework.util.Assert.notNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lombok.EqualsAndHashCode;

/**
 * Represents money which is composed of {@link #value} and {@link #currency}.
 */
@EqualsAndHashCode
public final class Money {

  private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

  private static final Currency DEFAULT_CURRENCY = Currency.getInstance("GBP");

  private final Currency currency;

  private final BigDecimal value;

  private Money(BigDecimal value, Currency currency) {
    notNull(value, "Value cannot be null.");
    notNull(currency, "Currency cannot be null.");

    this.value = value.setScale(currency.getDefaultFractionDigits(), DEFAULT_ROUNDING);
    this.currency = currency;
  }

  public static Money of(BigDecimal value, Currency currency) {
    return new Money(value, currency);
  }

  public static Money of(BigDecimal value, String currency) {
    return of(value, Currency.getInstance(currency));
  }

  public static Money of(BigDecimal value) {
    return of(value, DEFAULT_CURRENCY);
  }

  public BigDecimal getValue() {
    return value;
  }

  public Currency getCurrency() {
    return currency;
  }

  public Money multiply(BigDecimal factor) {
    BigDecimal newAmount = value.multiply(factor);
    return Money.of(newAmount, currency);
  }

  public String getCurrencyCode() {
    return currency.getCurrencyCode();
  }
}
