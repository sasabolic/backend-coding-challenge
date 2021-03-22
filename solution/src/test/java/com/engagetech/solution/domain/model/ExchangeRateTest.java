package com.engagetech.solution.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Currency;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ExchangeRateTest {

  @Test
  void verifyEqualsContract() {
    EqualsVerifier.forClass(ExchangeRate.class)
      .verify();
  }

  @Test
  void givenIncorrectFromCurrency_whenNewInstance_thenThrowException() {
    Currency from = null;
    Currency to = Currency.getInstance("GBP");
    BigDecimal value = BigDecimal.valueOf(0.233456);

    assertThatThrownBy(() -> ExchangeRate.of(from, to, value))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Exchange rate from currency cannot be null.");
  }

  @Test
  void givenIncorrectToCurrency_whenNewInstance_thenThrowException() {
    Currency from = Currency.getInstance("EUR");
    Currency to = null;
    BigDecimal value = BigDecimal.valueOf(0.233456);

    assertThatThrownBy(() -> ExchangeRate.of(from, to, value))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Exchange rate to currency cannot be null.");
  }

  @Test
  void givenIncorrectValue_whenNewInstance_thenThrowException() {
    Currency from = Currency.getInstance("EUR");
    Currency to = Currency.getInstance("GBP");
    BigDecimal value = null;

    assertThatThrownBy(() -> ExchangeRate.of(from, to, value))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Exchange rate value cannot be null.");
  }

  @Test
  void whenNewInstance_thenSuccess() {
    VatRate result = VatRate.of(new BigDecimal("20.00"));

    assertThat(result).isNotNull();
    assertThat(result.getValue()).isEqualTo(new BigDecimal("20.00"));
  }
}
