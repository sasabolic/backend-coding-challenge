package com.engagetech.solution.adapter.secondary.exchange.config;

import com.engagetech.solution.adapter.secondary.exchange.provider.ExchangeRateApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Exchange adapter config.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ExchangeRateApiProperties.class})
public class ExchangeConfig {

}
