package com.engagetech.solution.adapter.secondary.exchange.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.engagetech.solution.adapter.secondary.exchange.client.dto.ExchangeRateResponse;
import com.engagetech.solution.common.JsonFixtureTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest({ExchangeProviderClient.class})
class ExchangeProviderClientTest extends JsonFixtureTest {

  @Autowired
  private ExchangeProviderClient exchangeProviderClient;

  @Autowired
  private MockRestServiceServer server;

  @Test
  void whenGetRate_thenReturnResult() {
    String expected = readJSON("/fixtures/secondary/exchange/exchange_response.json");
    String uri = "https://api.exchangeratesapi.io/2021-03-18?base=EUR&symbols=GBP";

    this.server.expect(requestTo(uri))
      .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON));

    ExchangeRateResponse result = exchangeProviderClient.getRateFromUri(uri);

    assertThat(result)
      .isNotNull()
      .hasFieldOrPropertyWithValue("base", "EUR")
      .hasFieldOrPropertyWithValue("date", LocalDate.parse("2021-03-18"))
      .hasFieldOrPropertyWithValue("rates",
        Collections.singletonMap("GBP", new BigDecimal("0.855871")));
  }
}
