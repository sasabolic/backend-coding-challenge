package com.engagetech.solution.domain.model;

import static org.springframework.util.Assert.notNull;

import java.math.BigDecimal;
import java.util.Currency;
import lombok.EqualsAndHashCode;

/**
 * The rate used to exchange origination currency ({@link #from}) to disignated curecny ({@link #to}).
 */
@EqualsAndHashCode
public final class ExchangeRate {

  private final Currency from;
  private final Currency to;
  private final BigDecimal value;

  public static ExchangeRate of(Currency from, Currency to, BigDecimal value) {
    return new ExchangeRate(from, to, value);
  }

  private ExchangeRate(Currency from, Currency to, BigDecimal value) {
    notNull(from, "Exchange rate from currency cannot be null.");
    notNull(to, "Exchange rate to currency cannot be null.");
    notNull(value, "Exchange rate value cannot be null.");

    this.from = from;
    this.to = to;
    this.value = value;
  }

  public Currency getFrom() {
    return from;
  }

  public Currency getTo() {
    return to;
  }

  public BigDecimal getValue() {
    return value;
  }
}
