package com.engagetech.solution.adapter.secondary.postgres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

import com.engagetech.solution.adapter.secondary.postgres.entity.ExpenseEntity;
import com.engagetech.solution.adapter.secondary.postgres.repository.PostgresExpenseRepository;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.domain.model.ExpenseID;
import com.engagetech.solution.domain.model.Money;
import com.engagetech.solution.domain.model.Reason;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpenseRepositoryAdapterTest {

  @Mock
  PostgresExpenseRepository postgresExpenseRepository;

  ExpenseRepositoryAdapter expenseRepositoryAdapter;

  @BeforeEach
  void setUp() {
    expenseRepositoryAdapter = new ExpenseRepositoryAdapter(postgresExpenseRepository);
  }

  @Test
  void whenSave_thenReturnCorrectResult() {
    Expense expense = new Expense(
      ExpenseID.of(1L),
      LocalDate.parse("2020-01-01"),
      Money.of(BigDecimal.TEN),
      Money.of(new BigDecimal("8.00")),
      Reason.of("Mock reason")
    );

    given(postgresExpenseRepository.save(isA(ExpenseEntity.class)))
      .willReturn(new ExpenseEntity(expense));

    Expense result = expenseRepositoryAdapter.save(expense);

    assertThat(result)
      .isNotNull()
      .extracting(Expense::getId, Expense::getDate, Expense::getAmount, Expense::getVat, Expense::getReason)
      .contains(
        ExpenseID.of(1L),
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN),
        Money.of(new BigDecimal("8.00")),
        Reason.of("Mock reason"));
  }

  @Test
  void whenFind_thenReturnCorrectResult() {
    Expense expense = new Expense(
      ExpenseID.of(1L),
      LocalDate.parse("2020-01-01"),
      Money.of(BigDecimal.TEN),
      Money.of(new BigDecimal("8.00")),
      Reason.of("Mock reason")
    );

    given(postgresExpenseRepository.findAll()).willReturn(List.of(new ExpenseEntity(expense)));

    List<Expense> result = expenseRepositoryAdapter.findAll();

    assertThat(result)
      .isNotNull()
      .flatExtracting(Expense::getId, Expense::getDate, Expense::getAmount, Expense::getVat, Expense::getReason)
      .contains(
        ExpenseID.of(1L),
        LocalDate.parse("2020-01-01"),
        Money.of(BigDecimal.TEN),
        Money.of(new BigDecimal("8.00")),
        Reason.of("Mock reason"));
  }
}
