package com.engagetech.solution.config.security;

import com.engagetech.solution.config.security.jwt.JwtAuthenticationEntryPoint;
import com.engagetech.solution.config.security.jwt.JwtSecurityConfigurer;
import com.engagetech.solution.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Security configuration.
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String USERNAME = "tom";
  private static final String PASS = "$2a$10$uhp4ZE5N4XHuxuU0I0uQ3edgORti3RrqkupR8ZDlUNp4zI/TMm216";
  private static final String ROLE = "USER";

  private final JwtTokenProvider tokenProvider;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
      .antMatchers(HttpMethod.OPTIONS, "/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf().disable()
      .headers().frameOptions().disable()
      .and()
      .exceptionHandling()
      .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers("/login").permitAll()
      .antMatchers( "/expenses").hasRole("USER")
      .anyRequest().authenticated()
      .and()
      .apply(new JwtSecurityConfigurer(tokenProvider));
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userDetailsService())
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername(USERNAME).password(
      PASS).roles(ROLE).build());
    return manager;
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
