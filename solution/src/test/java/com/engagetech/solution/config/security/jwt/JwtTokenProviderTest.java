package com.engagetech.solution.config.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.time.Duration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

class JwtTokenProviderTest {

  JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  void setUp() {
    jwtTokenProvider = new JwtTokenProvider(
      new JwtProperties(
        "mock_key",
        Duration.ofHours(1)
      )
    );
  }

  @Test
  void whenCreateToken_thenTokenCreated() {
    Authentication authentication = new UsernamePasswordAuthenticationToken("mock_user",
      "mock_password", List.of(new SimpleGrantedAuthority("ROLE")));

    String result = jwtTokenProvider.createToken(authentication);

    assertThat(result).isNotEmpty();
  }

  @Test
  void whenValidateToken_thenTrue() {
    Authentication authentication = new UsernamePasswordAuthenticationToken("mock_user",
      "mock_password", List.of(new SimpleGrantedAuthority("ROLE")));

    String token = jwtTokenProvider.createToken(authentication);

    boolean result = jwtTokenProvider.validateToken(token);

    assertThat(result).isTrue();
  }

  @Test
  void givenInvalidToken_whenValidateToken_thenFalse() {
    boolean result = jwtTokenProvider.validateToken("invalid_token");

    assertThat(result).isFalse();
  }

  @Test
  void whenExtractToken_thenReturnToken() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    given(request.getHeader(isA(String.class))).willReturn("Bearer mock_token");

    String result = jwtTokenProvider.extractToken(request);

    assertThat(result)
      .isNotNull()
      .isEqualTo("mock_token");
  }

  @Test
  void givenNoBearerToken_whenExtractToken_thenReturnNull() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    given(request.getHeader(isA(String.class))).willReturn("mock_token");

    String result = jwtTokenProvider.extractToken(request);

    assertThat(result).isNull();
  }

  @Test
  void whenAuthenticationFrom_thenReturnAuthentication() {
    Authentication authentication = new UsernamePasswordAuthenticationToken("mock_user",
      "mock_password", List.of(new SimpleGrantedAuthority("ROLE")));
    String token = jwtTokenProvider.createToken(authentication);

    Authentication result = jwtTokenProvider.authenticationFrom(token);

    assertThat(result)
      .isNotNull()
      .extracting(Authentication::getPrincipal, Authentication::getAuthorities)
      .contains(new User(
          "mock_user",
          "[PROTECTED]",
          true,
          true,
          true,
          true,
          List.of(new SimpleGrantedAuthority("ROLE"))),
        List.of(new SimpleGrantedAuthority("ROLE"))
      );
  }

  @Test
  void givenInvalidToken_whenAuthenticationFrom_thenThrowException() {
    assertThatThrownBy(() -> jwtTokenProvider.authenticationFrom("invalid_token"))
      .isInstanceOf(BadCredentialsException.class)
      .hasMessage("Could not authenticate. Invalid JWT token");
  }
}
