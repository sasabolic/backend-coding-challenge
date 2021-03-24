package com.engagetech.solution.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Used to create and parse JWT token.
 */
@RequiredArgsConstructor
@Log4j2
@Component
public class JwtTokenProvider {

  private final JwtProperties properties;

  public String createToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    return Jwts.builder()
      .setSubject(authentication.getName())
      .claim("auth", authorities)
      .setExpiration(
        Date.from(LocalDateTime.now().plusSeconds(properties.getExpiration().toSeconds())
          .atZone(ZoneId.systemDefault()).toInstant()))
      .signWith(SignatureAlgorithm.HS512, properties.getSecretKey().getBytes())
      .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
        .setSigningKey(properties.getSecretKey().getBytes())
        .parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
      log.error("Error while validating token={}", e.getMessage());
    }
    return false;
  }

  public Authentication authenticationFrom(String token) {
    try {
      Claims claims = Jwts.parser()
        .setSigningKey(properties.getSecretKey().getBytes())
        .parseClaimsJws(token)
        .getBody();

      if (claims == null || claims.get("auth") == null) {
        return null;
      }

      List<SimpleGrantedAuthority> authorities = Arrays
        .stream(claims.get("auth").toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

      User principal = new User(claims.getSubject(), "", authorities);

      return new UsernamePasswordAuthenticationToken(principal, token, authorities);

    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException e) {
      log.error("Authentication error, invalid token={}", e.getMessage());
      throw new BadCredentialsException("Could not authenticate. Invalid JWT token", e);
    } catch (ExpiredJwtException e) {
      log.error("Token expired={}", e.getMessage());
      throw new BadCredentialsException("JWT token has expired", e);
    }
  }

  public String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
