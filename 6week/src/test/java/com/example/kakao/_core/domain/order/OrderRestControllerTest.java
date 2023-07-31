package com.example.kakao._core.domain.order;

import com.example.kakao.docs.ApiDocUtil;
import com.example.kakao.domain.order.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WithUserDetails(value = "ssarmango@nate.com")
class OrderRestControllerTest extends ApiDocUtil {
  @MockBean
  private OrderService orderService;

  @Test
  void 성공_주문내역상세조회() throws Exception {
    // stub
    given(orderService.findById(BDDMockito.anyInt(), BDDMockito.anyInt())).willReturn(null);

    // when
    resultActions = mvc.perform(get("/orders/{id}", 1)
      .contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(jsonPath("$.success").value("true"));
  }

  @Test
  void 실패_주문내역상세조회_id가0일때() throws Exception {
    // when
    resultActions = mvc.perform(get("/orders/{id}", 0)
      .contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(jsonPath("$.success").value("false"))
      .andExpect(jsonPath("$.error.status").value(400));
  }

}