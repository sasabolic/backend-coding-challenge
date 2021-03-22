package com.engagetech.solution.adapter.secondary.postgres.entity;

import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.domain.model.ExpenseID;
import com.engagetech.solution.domain.model.Money;
import com.engagetech.solution.domain.model.Reason;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Expense DB entity.
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "expense")
public class ExpenseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "amount_value")
  private BigDecimal amountValue;

  @Column(name = "amount_currency")
  private String amountCurrency;

  @Column(name = "vat_value")
  private BigDecimal vatValue;

  @Column(name = "vat_currency")
  private String vatCurrency;

  @Column(name = "reason")
  private String reason;

  public ExpenseEntity(Expense expense) {
    this.id = expense.getId() != null ? expense.getId().getValue() : null;
    this.date = expense.getDate();
    this.amountValue = expense.getAmount().getValue();
    this.amountCurrency = expense.getAmount().getCurrencyCode();
    this.vatValue = expense.getVat().getValue();
    this.vatCurrency = expense.getVat().getCurrencyCode();
    this.reason = expense.getReason().getValue();
  }

  public Expense toExpense() {
    return new Expense(
      ExpenseID.of(this.id),
      this.date,
      Money.of(this.amountValue, this.amountCurrency),
      Money.of(this.vatValue, this.vatCurrency),
      Reason.of(this.reason)
    );
  }
}
