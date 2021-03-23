package com.engagetech.solution.adapter.primary.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.engagetech.solution.adapter.primary.rest.dto.assembler.DefaultExpenseResponseAssembler;
import com.engagetech.solution.adapter.primary.rest.dto.assembler.ExpenseResponseAssembler;
import com.engagetech.solution.domain.model.CreateExpenseCommand;
import com.engagetech.solution.domain.model.Expense;
import com.engagetech.solution.domain.model.ExpenseID;
import com.engagetech.solution.domain.model.Money;
import com.engagetech.solution.domain.model.Reason;
import com.engagetech.solution.port.ExpenseService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest extends MvcTestConfig {

  @TestConfiguration
  static class TestConfig {

    @Bean
    public ExpenseResponseAssembler expenseResponseAssembler() {
      return new DefaultExpenseResponseAssembler();
    }
  }

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ExpenseService expenseService;

  @Test
  void whenGetAll_thenReturnListOfExpenses() throws Exception {
    Expense expense1 = new Expense(
      ExpenseID.of(1L),
      LocalDate.parse("2020-01-01"),
      Money.of(BigDecimal.TEN),
      Money.of(new BigDecimal("2.00")),
      Reason.of("Mock reason1")
    );

    Expense expense2 = new Expense(
      ExpenseID.of(2L),
      LocalDate.parse("2020-02-01"),
      Money.of(BigDecimal.ONE),
      Money.of(new BigDecimal("0.2")),
      Reason.of("Mock reason2")
    );

    given(expenseService.findAll()).willReturn(Arrays.asList(expense1, expense2));

    mockMvc
      .perform(
        get("/expenses")
          .accept(MediaType.APPLICATION_JSON)
          .with(user("user").password("pass").roles("USER"))
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[0].amount", equalTo("10.00")))
      .andExpect(jsonPath("$[1].amount", equalTo("1.00")));
  }

  @Test
  void givenUserNotAuthenticated_whenGetAll_thenStatusUnauthorize() throws Exception {
    mockMvc
      .perform(
        get("/expenses")
          .accept(MediaType.APPLICATION_JSON)
//          .with(anonymous())
      )
      .andExpect(status().isUnauthorized());
  }

  @Test
  void givenUserWithDifferentRole_whenGetAll_thenStatusForbidden() throws Exception {
    mockMvc
      .perform(
        get("/expenses")
          .accept(MediaType.APPLICATION_JSON)
          .with(user("user").password("pass").roles("MOCK_ROLE"))
      )
      .andExpect(status().isForbidden());
  }

  @Test
  void whenCreate_thenReturnStatusOk() throws Exception {
    Expense expense = new Expense(
      ExpenseID.of(1L),
      LocalDate.parse("2020-01-01"),
      Money.of(BigDecimal.TEN),
      Money.of(new BigDecimal("2.00")),
      Reason.of("Mock reason1")
    );

    given(expenseService.create(isA(CreateExpenseCommand.class))).willReturn(expense);

    final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
      .post("/expenses")
      .content(readJSON("/fixtures/primary/rest/create_expense_request.json"))
      .contentType(MediaType.APPLICATION_JSON);

    mockMvc
      .perform(
        requestBuilder
          .with(user("user").password("pass").roles("USER"))
      )
      .andExpect(status().isCreated());
  }

  @Test
  void givenUserNotAuthenticated_whenCreate_thenStatusUnauthorized() throws Exception {
    final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
      .post("/expenses")
      .content(readJSON("/fixtures/primary/rest/create_expense_request.json"))
      .contentType(MediaType.APPLICATION_JSON);

    mockMvc
      .perform(
        requestBuilder
          .with(anonymous())
      )
      .andExpect(status().isUnauthorized());
  }

  @Test
  void givenUserWithDifferentRole_whenCreate_thenStatusForbidden() throws Exception {
    Expense expense = new Expense(
      ExpenseID.of(1L),
      LocalDate.parse("2020-01-01"),
      Money.of(BigDecimal.TEN),
      Money.of(new BigDecimal("2.00")),
      Reason.of("Mock reason1")
    );

    given(expenseService.create(isA(CreateExpenseCommand.class))).willReturn(expense);

    final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
      .post("/expenses")
      .content(readJSON("/fixtures/primary/rest/create_expense_request.json"))
      .contentType(MediaType.APPLICATION_JSON);

    mockMvc
      .perform(
        requestBuilder
          .with(user("user").password("pass").roles("MOCK_ROLE"))
      )
      .andExpect(status().isForbidden());
  }

  @Test
  void givenInvalidRequest_whenCreate_thenReturnStatusBadRequest() throws Exception {

    final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
      .post("/expenses")
      .content(readJSON("/fixtures/primary/rest/create_expense_request_invalid.json"))
      .contentType(MediaType.APPLICATION_JSON);

    mockMvc
      .perform(
        requestBuilder
          .with(user("user").password("pass").roles("USER"))
      )
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$.timestamp").exists())
      .andExpect(jsonPath("$.status", equalTo(400)))
      .andExpect(jsonPath("$.message", equalTo("Validation failed")))
      .andExpect(jsonPath("$.errors").isArray())
      .andExpect(jsonPath("$.errors", hasSize(3)));
  }
}
