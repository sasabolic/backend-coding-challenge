package com.engagetech.solution.domain;

import com.engagetech.solution.domain.model.ExchangeRate;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.port.ExchangeFacade;
import com.engagetech.solution.port.ExchangeService;
import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Component
public class DefaultExchangeFacade implements ExchangeFacade {

  private final ExchangeService exchangeService;

  @Override
  public ExchangeRate getRate(Expense expense, Currency to) {
    return exchangeService
      .getRate(expense.getAmountCurrency(), to, expense.getDate());
  }
}
