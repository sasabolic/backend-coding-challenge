package com.engagetech.solution.adapter.secondary.exchange;

import com.engagetech.solution.adapter.secondary.exchange.provider.DefaultExchangeProvider;
import com.engagetech.solution.adapter.secondary.exchange.provider.FallbackExchangeProvider;
import com.engagetech.solution.domain.model.ExchangeRate;
import com.engagetech.solution.port.ExchangeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.LocalDate;
import java.util.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ExchangeServiceAdapter implements ExchangeService {

  private final DefaultExchangeProvider defaultProvider;
  private final FallbackExchangeProvider fallbackProvider;

  @CircuitBreaker(name = "exchange-rate-provider", fallbackMethod = "fallback")
  @Cacheable("exchange-rate")
  @Override
  public ExchangeRate getRate(Currency from, Currency to, LocalDate date) {
    log.debug("Getting rate from default provider {}, {}, {}", from, to, date);
    return ExchangeRate.of(from, to, defaultProvider.getRate(from, to, date));
  }

  @SuppressWarnings("unused")
  private ExchangeRate fallback(Currency from, Currency to, LocalDate date, Throwable cause) {
    log.debug("Getting rate from fallback provider {}, {}, {} because of an error={}", from, to, date, cause.getMessage());
    return ExchangeRate.of(from, to, fallbackProvider.getRate(from, to, date));
  }
}
