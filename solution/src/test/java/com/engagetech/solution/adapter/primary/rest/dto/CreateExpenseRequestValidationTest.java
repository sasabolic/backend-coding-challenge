package com.engagetech.solution.adapter.primary.rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreateExpenseRequestValidationTest {


  static Validator validator;

  @BeforeAll
  static void beforeAll() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void whenRequestValid_thenViolationListEmpty() {
    CreateExpenseRequest request = new CreateExpenseRequest(
      "20/03/2021",
      "20.00",
      "Some reason"
    );

    Set<ConstraintViolation<CreateExpenseRequest>> validate = validator.validate(request);

    assertThat(validate).isEmpty();
  }

  @Test
  void whenDateInvalid_thenViolationForDate() {
    CreateExpenseRequest request = new CreateExpenseRequest(
      "20-03-2021",
      "20.00",
      "Some reason"
    );

    Set<ConstraintViolation<CreateExpenseRequest>> validate = validator.validate(request);

    assertThat(validate)
      .isNotEmpty()
      .hasSize(1)
      .extracting(ConstraintViolation::getMessage)
      .containsOnly(
        "Date should not be in the future and it should be in 'dd/mm/yyyy' format (e.g. '20/03/2021').");
  }

  @Test
  void whenAmountFormatInvalid_thenViolationAmount() {
    CreateExpenseRequest request = new CreateExpenseRequest(
      "20/03/2021",
      "20,00",
      "Some reason"
    );

    Set<ConstraintViolation<CreateExpenseRequest>> validate = validator.validate(request);

    assertThat(validate)
      .isNotEmpty()
      .hasSize(1)
      .extracting(ConstraintViolation::getMessage)
      .containsOnly(
        "Amount should be in '00.00 [EUR]' format (e.g. '599.00' for British Pounds or '599.00 EUR' for Euros).");
  }

  @Test
  void whenAmountCurrencyInvalid_thenViolationAmount() {
    CreateExpenseRequest request = new CreateExpenseRequest(
      "20/03/2021",
      "20.00 RSD",
      "Some reason"
    );

    Set<ConstraintViolation<CreateExpenseRequest>> validate = validator.validate(request);

    assertThat(validate)
      .isNotEmpty()
      .hasSize(1)
      .extracting(ConstraintViolation::getMessage)
      .containsOnly("Currency 'RSD' is not supported");
  }

  @Test
  void whenReasonInvalid_thenViolationForReason() {
    CreateExpenseRequest request = new CreateExpenseRequest(
      "20/03/2021",
      "20.00",
      ""
    );
    Set<ConstraintViolation<CreateExpenseRequest>> validate = validator.validate(request);

    assertThat(validate)
      .isNotEmpty()
      .hasSize(1)
      .extracting(ConstraintViolation::getMessage)
      .containsOnly("Reason must have a value.");
  }

  @Test
  void whenInvalidAllFields_thenViolationListNotEmpty() {
    CreateExpenseRequest request = new CreateExpenseRequest(
      "20-03-2021",
      "20.00 RSD",
      ""
    );

    Set<ConstraintViolation<CreateExpenseRequest>> validate = validator.validate(request);

    assertThat(validate)
      .isNotEmpty()
      .hasSize(3);
  }
}
