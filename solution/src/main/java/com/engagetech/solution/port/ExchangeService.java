package com.engagetech.solution.port;

import com.engagetech.solution.domain.model.ExchangeRate;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Exchange service.
 */
public interface ExchangeService {

  /**
   * Gets the exchange rate on a given date for the originating currency.
   *
   * @param from the originating currency
   * @param to   the currency for which the exchange rate will be retrieved
   * @param date the exchange date
   * @return the conversion
   */
  ExchangeRate getRate(Currency from, Currency to, LocalDate date);
}
