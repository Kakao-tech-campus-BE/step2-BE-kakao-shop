package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

  // 결제하기 성공 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void save_test() throws Exception {
    // given

    // when
    ResultActions resultActions = mvc.perform(
        post("/orders/save")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    // eye
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // verify
    resultActions.andExpect(jsonPath("$.success").value("true"));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  // 주문 결과 확인 성공 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void findById_test() throws Exception {
    // given
    int id = 1;

    // when
    ResultActions resultActions = mvc.perform(
        get("/orders/" + id)
    );

    // eye
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // verify
    resultActions.andExpect(jsonPath("$.success").value("true"));
    resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
    resultActions.andExpect(jsonPath("$.response.products[0].productName").value(
        "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
    resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
    resultActions.andExpect(
        jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
    resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
    resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
    resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

  // 주문 결과 확인 실패 테스트
  @WithUserDetails(value = "ssarmango@nate.com")
  @Test
  public void findById_fail_test() throws Exception {
    // given
    int id = 10;

    // when
    ResultActions resultActions = mvc.perform(
        get("/orders/" + id)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("해당 주문 내역을 찾을 수 없습니다."));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

  }

}
