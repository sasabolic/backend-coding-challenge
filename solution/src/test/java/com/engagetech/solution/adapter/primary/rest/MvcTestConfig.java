package com.engagetech.solution.adapter.primary.rest;

import com.engagetech.solution.common.JsonFixtureTest;
import com.engagetech.solution.config.security.jwt.JwtTokenProvider;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class MvcTestConfig extends JsonFixtureTest {

  @MockBean
  JwtTokenProvider tokenProvider;
}
