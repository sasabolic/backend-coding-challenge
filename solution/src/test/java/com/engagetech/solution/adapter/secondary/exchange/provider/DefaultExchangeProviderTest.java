package com.engagetech.solution.adapter.secondary.exchange.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.engagetech.solution.adapter.secondary.exchange.client.ExchangeProviderClient;
import com.engagetech.solution.adapter.secondary.exchange.client.dto.ExchangeRateResponse;
import com.engagetech.solution.adapter.secondary.exchange.provider.ExchangeRateApiProperties.Connection;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultExchangeProviderTest {

  @Mock
  ExchangeProviderClient exchangeProviderClient;

  DefaultExchangeProvider defaultExchangeProvider;

  @BeforeEach
  void setUp() {
    defaultExchangeProvider = new DefaultExchangeProvider(exchangeProviderClient,
      new ExchangeRateApiProperties(
        new Connection("http://mock_primary_url"),
        new Connection("http://mock_secondary_url")
      ));
  }

  @Test
  void whenGetRate_thenCorrectUrlCalled() {
    given(exchangeProviderClient.getRateFromUri(anyString()))
      .willReturn(new ExchangeRateResponse(
        "EUR",
        LocalDate.parse("2020-01-01"),
        Map.of("GBP", new BigDecimal("0.6789"))
      ));

    BigDecimal result = defaultExchangeProvider
      .getRate(Currency.getInstance("EUR"), Currency.getInstance("GBP"), LocalDate
        .parse("2020-01-15"));

    assertThat(result)
      .isNotNull()
      .isEqualTo(new BigDecimal("0.6789"));

    then(exchangeProviderClient).should()
      .getRateFromUri(eq("http://mock_primary_url/2020-01-15?base=EUR&symbols=GBP"));
  }

  @Test
  void givenSameCurrencies_whenGetRate_thenUrlNotCalledReturnOne() {
    BigDecimal result = defaultExchangeProvider
      .getRate(Currency.getInstance("EUR"), Currency.getInstance("EUR"), LocalDate
        .parse("2020-01-15"));

    assertThat(result)
      .isNotNull()
      .isEqualTo(BigDecimal.ONE);

    then(exchangeProviderClient).should(never())
      .getRateFromUri(anyString());
  }

  @Test
  void givenResultIsNull_whenGetRate_thenThrowException() {
    given(exchangeProviderClient.getRateFromUri(anyString())).willReturn(null);
    Currency from = Currency.getInstance("EUR");
    Currency to = Currency.getInstance("GBP");
    LocalDate date = LocalDate.parse("2020-01-15");

    assertThatThrownBy(() -> defaultExchangeProvider.getRate(
      from,
      to,
      date
      )
    ).isInstanceOf(ExchangeRateException.class)
      .hasMessage("Could not get the exchange rate from 'EUR' to 'GBP'");

    then(exchangeProviderClient).should()
      .getRateFromUri(eq("http://mock_primary_url/2020-01-15?base=EUR&symbols=GBP"));
  }

  @Test
  void givenRateIsNull_whenGetRate_thenThrowException() {
    given(exchangeProviderClient.getRateFromUri(anyString()))
      .willReturn(new ExchangeRateResponse(
        "EUR",
        LocalDate.parse("2020-01-01"),
        Map.of()
      ));
    Currency from = Currency.getInstance("EUR");
    Currency to = Currency.getInstance("GBP");
    LocalDate date = LocalDate.parse("2020-01-15");

    assertThatThrownBy(() -> defaultExchangeProvider.getRate(
      from,
      to,
      date
    )).isInstanceOf(ExchangeRateException.class)
      .hasMessage("Could not get the exchange rate from 'EUR' to 'GBP'");

    then(exchangeProviderClient).should()
      .getRateFromUri(eq("http://mock_primary_url/2020-01-15?base=EUR&symbols=GBP"));
  }
}
