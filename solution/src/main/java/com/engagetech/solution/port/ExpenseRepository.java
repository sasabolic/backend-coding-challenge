package com.engagetech.solution.port;

import com.engagetech.solution.domain.model.Expense;
import java.util.List;

/**
 * Expense repository.
 */
public interface ExpenseRepository {

  /**
   * Returns all the expenses.
   *
   * @return the list
   */
  List<Expense> findAll();

  /**
   * Saves expense.
   *
   * @param expense the expense
   * @return the expense
   */
  Expense save(Expense expense);
}
