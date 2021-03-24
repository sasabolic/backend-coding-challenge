package com.engagetech.solution.adapter.primary.rest;

import com.engagetech.solution.adapter.primary.rest.dto.AuthenticationResponse;
import com.engagetech.solution.adapter.primary.rest.dto.LoginRequest;
import com.engagetech.solution.port.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST authentication resource.
 */
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        String token =authenticationService.authenticate(loginRequest.getUsername(), loginRequest
          .getPassword());

        return ResponseEntity.ok().body(new AuthenticationResponse(token, "Bearer"));
    }
}
