package com.engagetech.solution.adapter.secondary.exchange.provider;

import com.engagetech.solution.adapter.secondary.exchange.client.ExchangeProviderClient;
import com.engagetech.solution.adapter.secondary.exchange.client.dto.ExchangeRateResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Common logic for exchange providers.
 */
@RequiredArgsConstructor
public abstract class AbstractExchangeProvider {

  private final ExchangeProviderClient client;
  private final ExchangeRateApiProperties properties;

  /**
   * Gets the exchange rate.
   *
   * @param from the from currency
   * @param to   the to currency
   * @param date the currency date
   * @return the rate
   */
  public BigDecimal getRate(Currency from, Currency to, LocalDate date) {
    if (from.equals(to)) {
      return BigDecimal.ONE;
    }

    String uri = UriComponentsBuilder.fromHttpUrl(baseUri())
      .path(date.toString())
      .queryParam("base", from.getCurrencyCode())
      .queryParam("symbols", to.getCurrencyCode())
      .toUriString();

    ExchangeRateResponse result = client.getRateFromUri(uri);
    if (result != null) {
      BigDecimal rate = result.getRate(to.getCurrencyCode());

      if (rate != null) {
        return rate;
      }
    }

    throw new ExchangeRateException(String
      .format("Could not get the exchange rate from '%s' to '%s'", from.getCurrencyCode(),
        to.getCurrencyCode()));
  }

  /**
   * Creates the base URI used to query exchange rate.
   *
   * @return the string
   */
  abstract String baseUri();

  ExchangeRateApiProperties getProperties() {
    return this.properties;
  }
}
