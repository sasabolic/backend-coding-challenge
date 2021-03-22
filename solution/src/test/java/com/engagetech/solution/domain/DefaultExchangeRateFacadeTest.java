package com.engagetech.solution.domain;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.domain.model.Money;
import com.engagetech.solution.domain.model.Reason;
import com.engagetech.solution.port.ExchangeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultExchangeRateFacadeTest {

  @Mock
  ExchangeService exchangeService;

  DefaultExchangeFacade conversionFacade;

  @BeforeEach
  void setUp() {
    conversionFacade = new DefaultExchangeFacade(exchangeService);
  }

  @Test
  void whenConvert_thenExchangeServiceCalled() {
    Expense expense = Expense.create(
      LocalDate.parse("2020-01-15"),
      Money.of(new BigDecimal("100.00")),
      Reason.of("Mock reason")
    );

    conversionFacade.getRate(expense, Currency.getInstance("EUR"));


    then(exchangeService).should().getRate(eq(Currency.getInstance("GBP")), eq(Currency.getInstance("EUR")), eq(LocalDate.parse("2020-01-15")));
  }
}
