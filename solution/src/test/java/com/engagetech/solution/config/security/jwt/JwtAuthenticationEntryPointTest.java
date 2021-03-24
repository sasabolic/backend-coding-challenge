package com.engagetech.solution.config.security.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

class JwtAuthenticationEntryPointTest {

  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

  @Test
  void whenCommence_thenUnauthorized() throws IOException {
    HttpServletResponse response = mock(HttpServletResponse.class);

    jwtAuthenticationEntryPoint.commence(mock(HttpServletRequest.class),
      response, mock(AuthenticationException.class));

    then(response).should().sendError(eq(401), isA(String.class));
  }
}
