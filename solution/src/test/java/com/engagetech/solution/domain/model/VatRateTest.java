package com.engagetech.solution.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

class VatRateTest {

  @Test
  void verifyEqualsContract() {
    EqualsVerifier.forClass(VatRate.class)
      .verify();
  }

  @ParameterizedTest(name = "#{index} - given incorrect value={0} then throw exception={1} with message={2}")
  @CsvSource({
    "'null', 	'java.lang.IllegalArgumentException',  'VAT rate cannot be null.'",
    "'0.00', 	'java.lang.IllegalArgumentException',  'VAT rate is in a wrong range.'",
    "'100.00',  'java.lang.IllegalArgumentException',  'VAT rate is in a wrong range.'"
  })
  void givenIncorrectValue_whenNewInstance_thenThrowException(
    @ConvertWith(NullableConverter.class) BigDecimal value, String exceptionClassName,
    String exceptionMessage) throws ClassNotFoundException {
    assertThatThrownBy(() -> VatRate.of(value))
      .isInstanceOf(Class.forName(exceptionClassName))
      .hasMessage(exceptionMessage);
  }

  @Test
  void whenNewInstance_thenSuccess() {
    VatRate result = VatRate.of(new BigDecimal("20.00"));

    assertThat(result).isNotNull();
    assertThat(result.getValue()).isEqualTo(new BigDecimal("20.00"));
  }
}
