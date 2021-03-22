package com.engagetech.solution.adapter.secondary.exchange.provider;

/**
 * Thrown to indicate that exception occurred during exchange rate retrieval.
 */
public class ExchangeRateException extends RuntimeException {

  public ExchangeRateException(String message) {
    super(message);
  }
}
