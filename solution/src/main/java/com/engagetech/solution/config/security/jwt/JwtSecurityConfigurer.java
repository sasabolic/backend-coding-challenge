package com.engagetech.solution.config.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration adapter for JWT token. Adds JWT authentication filter to the chain.
 */
public class JwtSecurityConfigurer extends
  SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtTokenProvider tokenProvider;

  public JwtSecurityConfigurer(JwtTokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) {
    http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider),
      UsernamePasswordAuthenticationFilter.class);
  }
}
