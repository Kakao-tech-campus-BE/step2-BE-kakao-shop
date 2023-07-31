package com.example.kakao._core;

import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@Sql("classpath:db/teardown.sql")
@WithUserDetails(value = "ssarmango@nate.com")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class IntegrationTest {
  @Autowired
  protected ObjectMapper om;

  @Autowired
  protected MockMvc mvc;

  protected ResultActions resultActions;

  @MockBean
  private ErrorLogJPARepository errorLogJPARepository; // Exception Handler 의존성
}

