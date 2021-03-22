package com.engagetech.solution.domain.model;

import java.time.LocalDate;
import lombok.Value;

/**
 * Command used to create {@link Expense}.
 */
@Value
public class CreateExpenseCommand {

  Money amount;

  LocalDate date;

  Reason reason;
}
