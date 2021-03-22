package com.engagetech.solution.adapter.primary.rest.dto.assembler;

import com.engagetech.solution.adapter.primary.rest.dto.ExpenseResponse;
import com.engagetech.solution.domain.model.Expense;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link ExpenseResponseAssembler}.
 */
@Component
public class DefaultExpenseResponseAssembler implements ExpenseResponseAssembler {

    @Override
    public ExpenseResponse of(Expense entity) {
        return new ExpenseResponse(
          entity.getId().getValue(),
          entity.getAmount().getValue().toString(),
          entity.getVat().getValue().toString(),
          entity.getDate(),
          entity.getReason().getValue()
        );
    }
}
