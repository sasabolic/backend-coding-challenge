package com.engagetech.solution.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ExpenseTest {

  @Test
  void whenNewInstance_thenCreated() {
    Expense result = Expense
      .create(
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN),
        Reason.of("Mock reason")
      );

    assertThat(result)
      .isNotNull()
      .extracting(Expense::getId, Expense::getDate, Expense::getAmount, Expense::getVat,
        Expense::getReason)
      .contains(
        null,
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN),
        null,
        Reason.of("Mock reason"));
  }

  @Test
  void givenNoDate_whenNewInstance_thenThrowException() {
    LocalDate date = null;
    Money amount = Money.of(BigDecimal.TEN);
    Reason reason = Reason.of("Mock reason");

    assertThatThrownBy(() -> Expense.create(date, amount, reason))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Date cannot be null.");
  }

  @Test
  void givenNoAmount_whenNewInstance_thenThrowException() {
    LocalDate date = LocalDate.parse("2020-01-01");
    Money amount = null;
    Reason reason = Reason.of("Mock reason");

    assertThatThrownBy(() -> Expense.create(date, amount, reason))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Amount cannot be null.");
  }

  @Test
  void givenNoReason_whenNewInstance_thenThrowException() {
    LocalDate date = LocalDate.parse("2020-01-01");
    Money amount = Money.of(BigDecimal.TEN);
    Reason reason = null;

    assertThatThrownBy(() -> Expense.create(date, amount, reason))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Reason cannot be null.");
  }
}
