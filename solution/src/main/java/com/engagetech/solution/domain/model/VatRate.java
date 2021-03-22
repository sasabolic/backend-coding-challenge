package com.engagetech.solution.domain.model;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

/**
 * VAT rate used calculate VAT amount.
 */
@EqualsAndHashCode
public final class VatRate {

  private final BigDecimal value;

  public static VatRate of(BigDecimal value) {
    return new VatRate(value);
  }

  private VatRate(BigDecimal value) {
    notNull(value, "VAT rate cannot be null.");
    isTrue(value.compareTo(BigDecimal.ZERO) > 0 && value.compareTo(BigDecimal.valueOf(100)) < 0,
      "VAT rate is in a wrong range.");

    this.value = value;
  }

  public BigDecimal getValue() {
    return value;
  }
}
