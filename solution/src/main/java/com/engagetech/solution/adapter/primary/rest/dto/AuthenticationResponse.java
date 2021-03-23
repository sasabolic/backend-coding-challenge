package com.engagetech.solution.adapter.primary.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Authentication response DTO.
 */
@Getter
@AllArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("accessToken")
  private String accessToken;

  @JsonProperty("tokenType")
  private String tokenType;
}
