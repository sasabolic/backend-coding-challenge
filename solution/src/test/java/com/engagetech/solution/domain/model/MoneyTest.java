package com.engagetech.solution.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Currency;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class MoneyTest {

  @Test
  void verifyEqualsContract() {
    EqualsVerifier.forClass(Money.class)
      .verify();
  }

  @Test
  void givenIncorrectValue_whenNewInstance_thenThrowException() {
    BigDecimal value = null;
    Currency currency = Currency.getInstance("GBP");

    assertThatThrownBy(() -> Money.of(null, currency))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value cannot be null.");
  }

  @Test
  void givenIncorrectCurrency_whenNewInstance_thenThrowException() {
    BigDecimal value = BigDecimal.valueOf(0.233456);
    Currency currency = null;

    assertThatThrownBy(() -> Money.of(value, currency))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Currency cannot be null.");
  }

  @Test
  void whenNewInstance_thenSuccess() {
    Money result = Money.of(new BigDecimal("20.0239099"), Currency.getInstance("GBP"));

    assertThat(result).isNotNull();
    assertThat(result.getValue()).isEqualTo(new BigDecimal("20.02"));
    assertThat(result.getCurrency()).isEqualTo(Currency.getInstance("GBP"));
  }

  @Test
  void whenMultiply_thenCorrectResult() {
    Money money1 = Money.of(new BigDecimal("20.02"), Currency.getInstance("GBP"));

    Money result = money1.multiply(new BigDecimal("20.2134"));

    assertThat(result).isNotNull();
    assertThat(result.getValue()).isEqualTo(new BigDecimal("404.67"));
    assertThat(result.getCurrency()).isEqualTo(Currency.getInstance("GBP"));
  }
}
