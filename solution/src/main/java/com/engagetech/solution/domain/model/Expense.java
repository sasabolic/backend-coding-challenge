package com.engagetech.solution.domain.model;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import com.engagetech.solution.port.ExchangeFacade;
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

  /**
   * Converts the expense amount to the default currency.
   *
   * @param ef the exchange facade
   * @return the expense
   */
  public Expense convert(ExchangeFacade ef) {
    ExchangeRate exchangeRate = ef.getRate(this, DEFAULT_EXPENSE_CURRENCY);

    this.amount = Money.of(
      getAmountValue().multiply(exchangeRate.getValue()),
      DEFAULT_EXPENSE_CURRENCY
    );

    return this;
  }

  public BigDecimal getAmountValue() {
    return getAmount().getValue();
  }

  public Currency getAmountCurrency() {
    return getAmount().getCurrency();
  }

  /**
   * Calculates the VAT amount of the gross expense amount.
   *
   * @param vatRate the vat rate
   * @return the expense
   */
  public Expense calculateVAT(VatRate vatRate) {
    isTrue(getAmountCurrency().equals(DEFAULT_EXPENSE_CURRENCY), "In order to calculate VAT amount currency must be valid.");

    this.vat = this.amount.multiply(vatRate.getValue().divide(BigDecimal.valueOf(100)));

    return this;
  }
}
