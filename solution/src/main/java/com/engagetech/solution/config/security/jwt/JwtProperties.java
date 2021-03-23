package com.engagetech.solution.config.security.jwt;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties used to configure JWT.
 */
@ConstructorBinding
@Getter
@RequiredArgsConstructor
@ConfigurationProperties("app.solution.jwt")
public class JwtProperties {

  private final String secretKey;
  private final Duration expiration;
}
