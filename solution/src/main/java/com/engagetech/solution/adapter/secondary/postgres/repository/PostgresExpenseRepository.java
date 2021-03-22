package com.engagetech.solution.adapter.secondary.postgres.repository;

import com.engagetech.solution.adapter.secondary.postgres.entity.ExpenseEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Expense repository.
 */
public interface PostgresExpenseRepository extends CrudRepository<ExpenseEntity, Long> {

}
