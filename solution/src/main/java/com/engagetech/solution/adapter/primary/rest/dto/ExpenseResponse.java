package com.engagetech.solution.adapter.primary.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Expense response DTO.
 */
@Getter
public class ExpenseResponse {

  private Long id;

  private String amount;

  private String vat;

  private LocalDate date;

  private String reason;

  @JsonCreator
  public ExpenseResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("amount") String amount,
    @JsonProperty("vat") String vat,
    @JsonProperty("date") LocalDate date,
    @JsonProperty("reason") String reason) {
    this.id = id;
    this.amount = amount;
    this.vat = vat;
    this.date = date;
    this.reason = reason;
  }
}
