package com.engagetech.solution.config;

import com.engagetech.solution.config.security.jwt.JwtProperties;
import com.engagetech.solution.domain.ExpenseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ExpenseProperties.class, JwtProperties.class})
public class ApplicationConfig {

}
