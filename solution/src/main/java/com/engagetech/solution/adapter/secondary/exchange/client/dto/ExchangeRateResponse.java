package com.engagetech.solution.adapter.secondary.exchange.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exchange rate response DTO.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {

  String base;

  LocalDate date;

  Map<String, BigDecimal> rates = new HashMap<>();

  public BigDecimal getRate(String currency) {
    return rates.get(currency);
  }
}
