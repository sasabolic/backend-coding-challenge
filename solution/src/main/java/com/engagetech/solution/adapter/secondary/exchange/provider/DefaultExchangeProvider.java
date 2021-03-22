package com.engagetech.solution.adapter.secondary.exchange.provider;

import com.engagetech.solution.adapter.secondary.exchange.client.ExchangeProviderClient;
import org.springframework.stereotype.Component;

/**
 * Default currency exchange rate provider.
 */
@Component
public class DefaultExchangeProvider extends AbstractExchangeProvider {

  public DefaultExchangeProvider(
    ExchangeProviderClient client,
    ExchangeRateApiProperties properties) {
    super(client, properties);
  }

  @Override
  String baseUri() {
    return getProperties().getPrimary().getUri();
  }
}
