package com.engagetech.solution.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

import com.engagetech.solution.config.security.jwt.JwtTokenProvider;
import com.engagetech.solution.port.AuthenticationService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class DefaultAuthenticationServiceTest {

  @Mock
  AuthenticationManager authenticationManager;

  @Mock
  JwtTokenProvider tokenProvider;

  AuthenticationService authenticationService;

  @BeforeEach
  void setUp() {
    authenticationService = new DefaultAuthenticationService(
      authenticationManager,
      tokenProvider
    );
  }

  @Test
  void whenAuthenticate_thenReturnToken() {
    given(authenticationManager.authenticate(isA(Authentication.class)))
      .willReturn(new UsernamePasswordAuthenticationToken("user", "password", Collections
        .emptyList()));
    given(tokenProvider.createToken(isA(Authentication.class))).willReturn("test-token");

    String result = authenticationService.authenticate("mock_user", "mock_password");

    assertThat(result)
      .isNotNull()
      .isEqualTo("test-token");
  }

  @Test
  void givenExceptionThrown_whenAuthenticate_thenThrowException() {
    given(authenticationManager.authenticate(isA(Authentication.class)))
      .willThrow(new BadCredentialsException("Invalid credentials"));

    assertThatThrownBy(() -> authenticationService.authenticate("mock_user", "mock_password"))
      .isInstanceOf(BadCredentialsException.class)
      .hasMessage("Invalid credentials");
  }
}
