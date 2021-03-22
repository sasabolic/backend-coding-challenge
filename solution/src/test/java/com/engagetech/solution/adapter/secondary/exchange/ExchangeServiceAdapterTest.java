package com.engagetech.solution.adapter.secondary.exchange;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.engagetech.solution.adapter.secondary.exchange.provider.DefaultExchangeProvider;
import com.engagetech.solution.adapter.secondary.exchange.provider.FallbackExchangeProvider;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ExchangeServiceAdapterTest {

  @MockBean
  private DefaultExchangeProvider defaultExchangeProvider;

  @MockBean
  private FallbackExchangeProvider fallbackExchangeProvider;

  @Autowired
  private ExchangeServiceAdapter exchangeServiceAdapter;

  @Test
  void givenNoError_whenGetRate_thenOnlyDefaultProviderCalled() {
    given(defaultExchangeProvider
      .getRate(isA(Currency.class), isA(Currency.class), isA(LocalDate.class)))
      .willReturn(BigDecimal.ONE);

    exchangeServiceAdapter.getRate(
      Currency.getInstance("EUR"),
      Currency.getInstance("GBP"),
      LocalDate.parse("2020-01-15")
    );

    then(defaultExchangeProvider).should().getRate(
      eq(Currency.getInstance("EUR")),
      eq(Currency.getInstance("GBP")),
      eq(LocalDate.parse("2020-01-15"))
    );

    then(fallbackExchangeProvider).should(never()).getRate(
      isA(Currency.class),
      isA(Currency.class),
      isA(LocalDate.class)
    );
  }

  @Test
  void givenDefaultProviderWillThrowException_whenGetRate_thenCallFallbackProvider() {
    given(defaultExchangeProvider
      .getRate(isA(Currency.class), isA(Currency.class), isA(LocalDate.class)))
      .willThrow(RuntimeException.class);
    given(fallbackExchangeProvider
      .getRate(isA(Currency.class), isA(Currency.class), isA(LocalDate.class)))
      .willReturn(BigDecimal.ONE);

    exchangeServiceAdapter.getRate(
      Currency.getInstance("EUR"),
      Currency.getInstance("GBP"),
      LocalDate.parse("2020-01-15")
    );

    then(defaultExchangeProvider).should().getRate(
      eq(Currency.getInstance("EUR")),
      eq(Currency.getInstance("GBP")),
      eq(LocalDate.parse("2020-01-15"))
    );

    then(fallbackExchangeProvider).should().getRate(
      eq(Currency.getInstance("EUR")),
      eq(Currency.getInstance("GBP")),
      eq(LocalDate.parse("2020-01-15"))
    );
  }
}
