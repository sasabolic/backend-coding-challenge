package com.engagetech.solution.adapter.secondary.exchange.client;

import com.engagetech.solution.adapter.secondary.exchange.client.dto.ExchangeRateResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * REST client used to query exchange rates.
 */
@Component
public class ExchangeProviderClient {

  private final RestTemplate restTemplate;

  ExchangeProviderClient(RestTemplateBuilder restTemplateBuilder) {
    restTemplate = restTemplateBuilder.build();
  }

  public ExchangeRateResponse getRateFromUri(String uri) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<ExchangeRateResponse> response = restTemplate
      .exchange(uri, HttpMethod.GET, entity, ExchangeRateResponse.class);

    return response.getBody();
  }
}
