package com.engagetech.solution.config.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * JWT authentication filter. Used to extract {@link Authentication} from the JWT access token.
 */
@Log4j2
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final JwtTokenProvider tokenProvider;

  public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = tokenProvider.extractToken(httpServletRequest);
    log.debug("Extracted token={}", token);

    if (token != null && tokenProvider.validateToken(token)) {
      Authentication authentication = tokenProvider.authenticationFrom(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }
}
