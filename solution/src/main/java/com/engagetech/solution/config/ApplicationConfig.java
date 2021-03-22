package com.engagetech.solution.config;

import com.engagetech.solution.domain.ExpenseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ExpenseProperties.class})
public class ApplicationConfig {

}
