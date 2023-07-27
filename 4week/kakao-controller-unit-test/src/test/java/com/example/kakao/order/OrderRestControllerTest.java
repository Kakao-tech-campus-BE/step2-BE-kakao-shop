package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
    FakeStore.class,
    SecurityConfig.class,
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper om;


  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("결제 테스트")
  public void order_save_test() throws Exception {
    // given

    // when
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders
            .post("/orders/save")
            .contentType(MediaType.APPLICATION_JSON)
    );

    String responseBody = result.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
  }

  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("주문 결과 확인 테스트")
  public void order_findById_test() throws Exception {
    // given

    // when
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders
            .get("/orders/1")
            .contentType(MediaType.APPLICATION_JSON)
    );

    String responseBody = result.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

  }

}
