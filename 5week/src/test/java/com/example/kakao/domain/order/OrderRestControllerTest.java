package com.example.kakao.domain.order;

import com.example.kakao._core.util.DummyJwt;
import com.example.kakao.log.ErrorLogJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class OrderRestControllerTest {
  @Autowired
  MockMvc mvc;

  @MockBean
  private OrderService orderService;

  @MockBean
  private ErrorLogJPARepository errorLogJPARepository;

  @Test
  @DisplayName("예외 - 주문 내역 상세 조회 - id가 0일 때")
  void findByIdWithIdZero() throws Exception {
    // given
    String jwtHeader = DummyJwt.generate();

    // when
    ResultActions result = mvc.perform(get("/orders/0")
      .header("Authorization", jwtHeader)
      .contentType(MediaType.APPLICATION_JSON));

    // then
    result.andExpect(jsonPath("$.success").value("false"));
    result.andExpect(jsonPath("$.error.status").value(400));
  }

}