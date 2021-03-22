package com.engagetech.solution.port;

import com.engagetech.solution.domain.model.CreateExpenseCommand;
import com.engagetech.solution.domain.model.Expense;
import java.util.List;

/**
 * Expense service.
 */
public interface ExpenseService {

  /**
   * Returns all expenses.
   *
   * @return the list
   */
  List<Expense> findAll();

  /**
   * Creates an expense.
   *
   * @param createExpenseCommand the create expense command
   * @return the expense
   */
  Expense create(CreateExpenseCommand createExpenseCommand);
}
