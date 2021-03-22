package com.engagetech.solution.domain.model;

import static org.springframework.util.Assert.notNull;

import lombok.EqualsAndHashCode;

/**
 * ID of the {@link Expense} aggregate root.
 */
@EqualsAndHashCode
public final class ExpenseID {

  private final Long value;

  public static ExpenseID of(Long value) {
    return new ExpenseID(value);
  }

  private ExpenseID(Long value) {
    notNull(value, "Expense ID cannot be null.");

    this.value = value;
  }

  public Long getValue() {
    return value;
  }
}
