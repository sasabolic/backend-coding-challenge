package com.engagetech.solution.domain;

import com.engagetech.solution.config.security.jwt.JwtTokenProvider;
import com.engagetech.solution.port.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultAuthenticationService implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;

  @Override
  public String authenticate(String username, String password) {
    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username,
      password);

    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return this.tokenProvider.createToken(authentication);
  }
}
