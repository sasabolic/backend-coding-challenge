package com.engagetech.solution.domain;

import com.engagetech.solution.domain.model.CreateExpenseCommand;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.domain.model.VatRate;
import com.engagetech.solution.port.ExpenseRepository;
import com.engagetech.solution.port.ExpenseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class DefaultExpenseService implements ExpenseService {

  private final ExpenseRepository repository;
  private final DefaultExchangeFacade exchangeFacade;
  private final ExpenseProperties expenseProperties;

  @Override
  public List<Expense> findAll() {
    return repository.findAll();
  }

  @Override
  public Expense create(CreateExpenseCommand createExpenseCommand) {
    Expense expense = Expense
      .create(
        createExpenseCommand.getDate(),
        createExpenseCommand.getAmount(),
        createExpenseCommand.getReason()
      )
      .convert(exchangeFacade)
      .calculateVAT(VatRate.of(expenseProperties.getVatRate()));

    return repository.save(expense);
  }
}
