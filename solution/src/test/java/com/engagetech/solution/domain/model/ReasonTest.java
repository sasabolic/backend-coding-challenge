package com.engagetech.solution.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

class ReasonTest {

  @Test
  void verifyEqualsContract() {
    EqualsVerifier.forClass(Reason.class)
      .verify();
  }

  @ParameterizedTest(name = "#{index} - given incorrect value={0} then throw exception={1} with message={2}")
  @CsvSource({
    "'null', 	'java.lang.IllegalArgumentException',  'Reason cannot be empty.'",
    "'', 	    'java.lang.IllegalArgumentException',  'Reason cannot be empty.'",
  })
  void givenIncorrectValue_whenNewInstance_thenThrowException(
    @ConvertWith(NullableConverter.class) String value, String exceptionClassName,
    String exceptionMessage) throws ClassNotFoundException {
    assertThatThrownBy(() -> Reason.of(value))
      .isInstanceOf(Class.forName(exceptionClassName))
      .hasMessage(exceptionMessage);
  }

  @Test
  void givenIncorrectSize_whenNewInstance_thenThrowException() {
    String valueSizeOf1001 = "mock test_".repeat(100) + "a";

    assertThatThrownBy(() -> Reason.of(valueSizeOf1001))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Reason value is too long.");
  }

  @Test
  void whenNewInstance_thenSuccess() {
    Reason result = Reason.of("Mock reason");

    assertThat(result).isNotNull();
    assertThat(result.getValue()).isEqualTo("Mock reason");
  }
}
