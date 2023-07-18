package com.example.kakao.domain.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.domain.cart.CartRestController;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@Import({
  FakeStore.class,
  SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
class OrderRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper om;

  @MockBean
  private ErrorLogJPARepository errorLogJPARepository;

  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("주문 결과 확인")
  void findById() throws Exception {
    ResultActions result = mvc.perform(
      MockMvcRequestBuilders
        .get("/orders/1")
    );

    String responseBody = result.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
  }
}