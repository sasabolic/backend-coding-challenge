package com.engagetech.solution.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ExpenseIDTest {

  @Test
  void verifyEqualsContract() {
    EqualsVerifier.forClass(ExpenseID.class)
      .verify();
  }

  @Test
  void givenIncorrectValue_whenNewInstance_thenThrowException() {
    assertThatThrownBy(() -> ExpenseID.of(null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Expense ID cannot be null.");
  }

  @Test
  void whenNewInstance_thenSuccess() {
    ExpenseID result = ExpenseID.of(1L);

    assertThat(result).isNotNull();
    assertThat(result.getValue()).isEqualTo(1L);
  }
}
