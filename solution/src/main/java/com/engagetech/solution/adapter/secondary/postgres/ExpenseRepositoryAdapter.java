package com.engagetech.solution.adapter.secondary.postgres;

import com.engagetech.solution.adapter.secondary.postgres.entity.ExpenseEntity;
import com.engagetech.solution.adapter.secondary.postgres.repository.PostgresExpenseRepository;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.port.ExpenseRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExpenseRepositoryAdapter implements ExpenseRepository {

  private final PostgresExpenseRepository repository;

  @Override
  public List<Expense> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
      .map(ExpenseEntity::toExpense)
      .collect(Collectors.toList());
  }

  @Override
  public Expense save(Expense expense) {
    return repository.save(new ExpenseEntity(expense)).toExpense();
  }
}
