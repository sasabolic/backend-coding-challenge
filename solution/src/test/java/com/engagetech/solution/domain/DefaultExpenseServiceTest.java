package com.engagetech.solution.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.engagetech.solution.domain.model.CreateExpenseCommand;
import com.engagetech.solution.domain.model.ExchangeRate;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.domain.model.Money;
import com.engagetech.solution.domain.model.Reason;
import com.engagetech.solution.port.ExpenseRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultExpenseServiceTest {

  @Mock
  ExpenseRepository expenseRepository;

  @Mock
  DefaultExchangeFacade conversionFacade;

  DefaultExpenseService defaultExpenseService;

  @BeforeEach
  void setUp() {
    defaultExpenseService = new DefaultExpenseService(
      expenseRepository,
      conversionFacade,
      new ExpenseProperties(
        new BigDecimal("0.2")
      )
    );
  }

  @Test
  void whenCreate_thenSaveToRepository() {
    given(conversionFacade.getRate(isA(Expense.class), isA(Currency.class))).willReturn(
      ExchangeRate.of(
        Currency.getInstance("EUR"),
        Currency.getInstance("GBP"),
        new BigDecimal("0.78963")
    ));

    defaultExpenseService.create(new CreateExpenseCommand(
      Money.of(new BigDecimal("100.00")),
      LocalDate.parse("2020-01-15"),
      Reason.of("Mock reason")
    ));

    then(expenseRepository).should().save(isA(Expense.class));
  }

  @Test
  void givenErrorOccurredDuringConversion_whenCreate_thenDoNotSaveToRepository() {
    given(conversionFacade.getRate(isA(Expense.class), isA(Currency.class)))
      .willThrow(new RuntimeException("Error"));

    Money amount = Money.of(new BigDecimal("100.00"));
    LocalDate date = LocalDate.parse("2020-01-15");
    CreateExpenseCommand createExpenseCommand = new CreateExpenseCommand(
      amount,
      date,
      Reason.of("Mock reason")
    );

    assertThatThrownBy(() -> defaultExpenseService.create(createExpenseCommand))
      .isInstanceOf(RuntimeException.class)
      .hasMessage("Error");

    then(expenseRepository).should(never()).save(isA(Expense.class));
  }

  @Test
  void whenFindAll_thenReturnResult() {
    given(expenseRepository.findAll()).willReturn(List.of(
      Expense.create(
        LocalDate.parse("2020-01-15"),
        Money.of(BigDecimal.TEN),
        Reason.of("Mock reason")
      )));

    List<Expense> result = defaultExpenseService.findAll();

    assertThat(result)
      .isNotNull()
      .hasSize(1);
  }
}
