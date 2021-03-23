package com.engagetech.solution.config.security.jwt;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Authentication entry point executed in case authentication fails.
 * <p>
 * If authentication fails all responses will return unauthorized status code {@code
 * HttpServletResponse#SC_UNAUTHORIZED}.
 * </p>
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException authException) throws IOException {

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
