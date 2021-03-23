package com.engagetech.solution.adapter.primary.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.engagetech.solution.port.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest extends MvcTestConfig {

  @MockBean
  AuthenticationService authenticationService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void whenLogin_ThenReturnTokenInformation() throws Exception {
    given(authenticationService.authenticate(isA(String.class), isA(String.class)))
      .willReturn("test-token");

    mockMvc
      .perform(
        post("/login")
          .content(readJSON("/fixtures/primary/rest/login_request.json"))
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.accessToken", equalTo("test-token")))
      .andExpect(jsonPath("$.tokenType", equalTo("Bearer")));
  }

  @Test
  void givenInvalidCredentials_whenLogin_ThenStatusUnathorized() throws Exception {
    given(authenticationService.authenticate(isA(String.class), isA(String.class)))
      .willThrow(new BadCredentialsException("Invalid credentials"));

    mockMvc
      .perform(
        post("/login")
          .content(readJSON("/fixtures/primary/rest/login_request_invalid_credentials.json"))
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isUnauthorized());
  }
}
