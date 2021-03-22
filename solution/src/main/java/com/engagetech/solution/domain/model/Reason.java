package com.engagetech.solution.domain.model;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;

import lombok.EqualsAndHashCode;

/**
 * Reason why expense was created.
 */
@EqualsAndHashCode
public final class Reason {

  private final String value;

  public static Reason of(String value) {
    return new Reason(value);
  }

  private Reason(String value) {
    hasText(value, "Reason cannot be empty.");
    isTrue(value.length() <= 1000, "Reason value is too long.");

    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
