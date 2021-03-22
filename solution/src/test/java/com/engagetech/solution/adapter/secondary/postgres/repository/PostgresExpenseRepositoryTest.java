package com.engagetech.solution.adapter.secondary.postgres.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.engagetech.solution.adapter.secondary.postgres.entity.ExpenseEntity;
import com.engagetech.solution.domain.model.ExchangeRate;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.domain.model.Money;
import com.engagetech.solution.domain.model.Reason;
import com.engagetech.solution.domain.model.VatRate;
import com.engagetech.solution.port.ExchangeFacade;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PostgresExpenseRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  ExchangeFacade cf = mock(ExchangeFacade.class);

  @Autowired
  PostgresExpenseRepository repository;

  @Test
  void whenSave_thenAllFieldsSaved() {
    given(cf.getRate(isA(Expense.class), isA(Currency.class)))
      .willReturn(
        ExchangeRate.of(Currency.getInstance("GBP"), Currency.getInstance("EUR"), BigDecimal.ONE));

    Expense expense = Expense
      .create(
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN),
        Reason.of("Mock reason")
      )
      .convert(cf)
      .calculateVAT(VatRate.of(new BigDecimal(20)));

    ExpenseEntity result = repository.save(new ExpenseEntity(expense));

    assertThat(result)
      .isNotNull()
      .hasFieldOrProperty("id")
      .hasFieldOrPropertyWithValue("amountValue", BigDecimal.valueOf(1000, 2))
      .hasFieldOrPropertyWithValue("amountCurrency", "GBP")
      .hasFieldOrPropertyWithValue("vatValue", BigDecimal.valueOf(200, 2))
      .hasFieldOrPropertyWithValue("vatCurrency", "GBP")
      .hasFieldOrPropertyWithValue("reason", "Mock reason");
  }

  @Test
  void whenFindAll_thenAllFetched() {
    given(cf.getRate(isA(Expense.class), isA(Currency.class)))
      .willReturn(
        ExchangeRate.of(Currency.getInstance("GBP"), Currency.getInstance("EUR"), BigDecimal.ONE));

    Expense expense = Expense
      .create(
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN),
        Reason.of("Mock reason")
      )
      .convert(cf)
      .calculateVAT(VatRate.of(new BigDecimal("0.2")));

    entityManager.persist(new ExpenseEntity(expense));

    Iterable<ExpenseEntity> result = repository.findAll();

    assertThat(result)
      .isNotNull()
      .hasSizeGreaterThanOrEqualTo(1);
  }
}
