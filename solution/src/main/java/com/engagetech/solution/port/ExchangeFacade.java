package com.engagetech.solution.port;

import com.engagetech.solution.domain.model.ExchangeRate;
import com.engagetech.solution.domain.model.Expense;
import java.util.Currency;

/**
 * Used to get exchange rate for the expense.
 */
public interface ExchangeFacade {

  /**
   * Gets rate for currency of the given expense.
   *
   * @param expense the expense
   * @param to      the to
   * @return the rate
   */
  ExchangeRate getRate(Expense expense, Currency to);
}
