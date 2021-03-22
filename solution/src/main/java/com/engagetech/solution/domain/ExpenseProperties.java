package com.engagetech.solution.domain;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties used to configure expense properties.
 */
@ConstructorBinding
@Getter
@RequiredArgsConstructor
@ConfigurationProperties("app.solution.expense")
public class ExpenseProperties {

  private final BigDecimal vatRate;
}
