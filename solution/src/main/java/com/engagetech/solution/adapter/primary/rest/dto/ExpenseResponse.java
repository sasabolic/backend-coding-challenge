package com.engagetech.solution.adapter.primary.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Expense response DTO.
 */
@Getter
@AllArgsConstructor
public class ExpenseResponse {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("amount")
  private String amount;

  @JsonProperty("vat")
  private String vat;

  @JsonProperty("date")
  private LocalDate date;

  @JsonProperty("reason")
  private String reason;
}
