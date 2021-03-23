package com.engagetech.solution.adapter.primary.rest.dto;

import lombok.Data;

/**
 * Login request DTO.
 */
@Data
public class LoginRequest {

  private String username;
  private String password;
}
