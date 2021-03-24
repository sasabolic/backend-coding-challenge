package com.engagetech.solution.adapter.primary.rest;

import com.engagetech.solution.adapter.primary.rest.dto.CreateExpenseRequest;
import com.engagetech.solution.adapter.primary.rest.dto.ExpenseResponse;
import com.engagetech.solution.adapter.primary.rest.dto.assembler.ExpenseResponseAssembler;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.port.ExpenseService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Expense REST resource.
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

  private final ExpenseService expenseService;
  private final ExpenseResponseAssembler expenseResponseAssembler;

  @GetMapping
  public List<ExpenseResponse> getAll() {
    return expenseResponseAssembler.of(expenseService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody @Valid CreateExpenseRequest createExpenseRequest) {
    log.debug("Creating expense={}", createExpenseRequest);

    Expense expense = expenseService.create(createExpenseRequest.toCommand());

    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(expense.getId().getValue()).toUri();

    return ResponseEntity.created(location).build();
  }
}
