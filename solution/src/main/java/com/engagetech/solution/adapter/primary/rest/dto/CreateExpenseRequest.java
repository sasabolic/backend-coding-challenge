package com.engagetech.solution.adapter.primary.rest.dto;

import com.engagetech.solution.adapter.primary.rest.dto.validator.Money;
import com.engagetech.solution.adapter.primary.rest.dto.validator.MoneyValidator;
import com.engagetech.solution.adapter.primary.rest.dto.validator.NotFutureDate;
import com.engagetech.solution.domain.model.CreateExpenseCommand;
import com.engagetech.solution.domain.model.Reason;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.regex.Matcher;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Create expense DTO.
 */
public class CreateExpenseRequest {

  private static final String DATE_PATTERN = "dd/MM/uuuu";

  @NotNull(message = "Date must have a value")
  @NotFutureDate(
    pattern = DATE_PATTERN,
    message = "Date is invalid. You should specify date in 'dd/mm/yyyy' format (e.g. '20/03/2021'). The value should not be in the future."
  )
  private final String date;

  @NotNull(message = "Amount must have a value")
  @Money(
    supportedCurrencies = {"GBP", "EUR"},
    message = "Invalid money format. You should specify amount in '00.00 [EUR]' format (e.g. '599.00' for British Pounds or '599.00 EUR' for Euros)."
  )
  private final String amount;

  @NotBlank(message = "Reason must not be empty")
  private final String reason;

  @JsonCreator
  public CreateExpenseRequest(
    @JsonProperty("date") String date,
    @JsonProperty("amount") String amount,
    @JsonProperty("reason") String reason) {
    this.amount = amount;
    this.date = date;
    this.reason = reason;
  }

  public CreateExpenseCommand toCommand() {
    return new CreateExpenseCommand(
      toMoney(this.amount),
      toLocalDate(this.date),
      toReason(this.reason)
    );
  }

  private Reason toReason(String reason) {
    return Reason.of(reason);
  }

  private LocalDate toLocalDate(String date) {
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
  }

  private com.engagetech.solution.domain.model.Money toMoney(String amount) {
    Matcher matcher = MoneyValidator.PATTERN.matcher(amount);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid amount with currency value");
    }

    return matcher.group(3) != null ?
      com.engagetech.solution.domain.model.Money.of(new BigDecimal(matcher.group(1)), Currency.getInstance(matcher.group(3))) :
      com.engagetech.solution.domain.model.Money.of(new BigDecimal(matcher.group(1)));
  }
}
