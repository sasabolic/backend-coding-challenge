package com.engagetech.solution.adapter.primary.rest.dto.assembler;

import com.engagetech.solution.adapter.primary.rest.common.GenericResponseAssembler;
import com.engagetech.solution.adapter.primary.rest.dto.ExpenseResponse;
import com.engagetech.solution.domain.model.Expense;

/**
 * Assembler interface for creating {@link ExpenseResponse}
 * DTOs.
 */
public interface ExpenseResponseAssembler extends GenericResponseAssembler<Expense, ExpenseResponse> {

}
