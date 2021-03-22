package com.engagetech.solution.adapter.secondary.exchange.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties used for exchange client providers.
 */
@ConstructorBinding
@Getter
@RequiredArgsConstructor
@ConfigurationProperties("exchange-rate.api")
public class ExchangeRateApiProperties {

  private final Connection primary;
  private final Connection secondary;

  @Getter
  @RequiredArgsConstructor
  public static class Connection {

    private final String uri;
  }
}
