package com.engagetech.solution.adapter.secondary.exchange.provider;

import com.engagetech.solution.adapter.secondary.exchange.client.ExchangeProviderClient;
import org.springframework.stereotype.Component;

/**
 * Fallback currency exchange rate provider.
 */
@Component
public class FallbackExchangeProvider extends AbstractExchangeProvider {

  public FallbackExchangeProvider(
    ExchangeProviderClient client,
    ExchangeRateApiProperties properties) {
    super(client, properties);
  }

  @Override
  String baseUri() {
    return getProperties().getSecondary().getUri();
  }
}
