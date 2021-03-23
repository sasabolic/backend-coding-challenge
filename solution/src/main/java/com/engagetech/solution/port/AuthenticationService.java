package com.engagetech.solution.port;

/**
 * Authentication service.
 */
public interface AuthenticationService {

  /**
   * Authenticates user with usernname and password.
   *
   * @param username the username
   * @param password the password
   * @return the token
   */
  String authenticate(String username, String password);
}
