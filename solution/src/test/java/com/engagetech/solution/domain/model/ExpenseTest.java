package com.engagetech.solution.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

import com.engagetech.solution.port.ExchangeFacade;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpenseTest {

  @Mock
  ExchangeFacade exchangeFacade;

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

  @Test
  void whenConvert_thenCorrectResult() {
    Expense expense = Expense
      .create(
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN, Currency.getInstance("EUR")),
        Reason.of("Mock reason")
      );

    given(exchangeFacade.getRate(isA(Expense.class), isA(Currency.class)))
      .willReturn(ExchangeRate
        .of(Currency.getInstance("EUR"), Currency.getInstance("GBP"), new BigDecimal("0.23456")
        )
      );

    Expense result = expense.convert(exchangeFacade);

    assertThat(result)
      .isNotNull()
      .extracting(Expense::getId, Expense::getDate, Expense::getAmount, Expense::getVat,
        Expense::getReason)
      .contains(
        null,
        LocalDate.parse("2020-01-01"),
        Money.of(new BigDecimal("2.35"), Currency.getInstance("GBP")),
        null,
        Reason.of("Mock reason"));
  }

  @Test
  void whenCalculateVAT_thenCorrectResult() {
    Expense expense = Expense
      .create(
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN),
        Reason.of("Mock reason")
      );

    Expense result = expense.calculateVAT(VatRate.of(BigDecimal.valueOf(20.0)));

    assertThat(result)
      .isNotNull()
      .extracting(Expense::getId, Expense::getDate, Expense::getAmount, Expense::getVat,
        Expense::getReason)
      .contains(
        null,
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN, Currency.getInstance("GBP")),
        Money.of(BigDecimal.valueOf(2.0), Currency.getInstance("GBP")),
        Reason.of("Mock reason"));
  }

  @Test
  void givenInvalidAmountCurrency_whenCalculateVAT_thenThrowException() {
    Expense expense = Expense
      .create(
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN, Currency.getInstance("EUR")),
        Reason.of("Mock reason")
      );
    VatRate rate = VatRate.of(BigDecimal.valueOf(20.0));

    assertThatThrownBy(() -> expense.calculateVAT(rate))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("In order to calculate VAT amount currency must be valid.");
  }
}
