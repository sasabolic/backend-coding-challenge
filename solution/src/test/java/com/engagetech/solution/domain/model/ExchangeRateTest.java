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
    Currency to = Currency.getInstance("GBP");
    BigDecimal value = BigDecimal.valueOf(0.233456);

    assertThatThrownBy(() -> ExchangeRate.of(null, to, value))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Exchange rate from currency cannot be null.");
  }

  @Test
  void givenIncorrectToCurrency_whenNewInstance_thenThrowException() {
    Currency from = Currency.getInstance("EUR");
    BigDecimal value = BigDecimal.valueOf(0.233456);

    assertThatThrownBy(() -> ExchangeRate.of(from, null, value))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Exchange rate to currency cannot be null.");
  }

  @Test
  void givenIncorrectValue_whenNewInstance_thenThrowException() {
    Currency from = Currency.getInstance("EUR");
    Currency to = Currency.getInstance("GBP");

    assertThatThrownBy(() -> ExchangeRate.of(from, to, null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Exchange rate value cannot be null.");
  }

  @Test
  void whenNewInstance_thenSuccess() {
    Currency from = Currency.getInstance("EUR");
    Currency to = Currency.getInstance("GBP");
    BigDecimal value = new BigDecimal("0.6543");
    ExchangeRate result = ExchangeRate.of(from, to, value);

    assertThat(result).isNotNull();
    assertThat(result.getValue()).isEqualTo(new BigDecimal("0.6543"));
    assertThat(result.getFrom()).isEqualTo(Currency.getInstance("EUR"));
    assertThat(result.getTo()).isEqualTo(Currency.getInstance("GBP"));
  }
}
