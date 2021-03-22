package com.engagetech.solution.domain.model;

import static org.springframework.util.Assert.notNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Expense aggregate root.
 */
@AllArgsConstructor
@Getter
public class Expense {

  private static final Currency DEFAULT_EXPENSE_CURRENCY = Currency.getInstance("GBP");

  private ExpenseID id;

  private LocalDate date;

  private Money amount;

  private Money vat;

  private Reason reason;

  public static Expense create(LocalDate date, Money amount, Reason reason) {
    return new Expense(date, amount, reason);
  }

  private Expense(LocalDate date, Money amount, Reason reason) {
    notNull(date, "Date cannot be null.");
    notNull(amount, "Amount cannot be null.");
    notNull(reason, "Reason cannot be null.");

    this.date = date;
    this.amount = amount;
    this.reason = reason;
  }

  public BigDecimal getAmountValue() {
    return getAmount().getValue();
  }

  public Currency getAmountCurrency() {
    return getAmount().getCurrency();
  }
}
