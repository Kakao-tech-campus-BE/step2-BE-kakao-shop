package com.example.kakao.product;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest extends MyRestDoc {

  // 전체 상품 목록 조회 성공 테스트
  @Test
  public void findAll_test() throws Exception {
    // given teardown.sql

    // when
    ResultActions resultActions = mvc.perform(
        get("/products")
    );

    // console
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // verify
    resultActions.andExpect(jsonPath("$.success").value("true"));
    resultActions.andExpect(jsonPath("$.response[0].id").value(1));
    resultActions.andExpect(
        jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    resultActions.andExpect(jsonPath("$.response[0].description").value(""));
    resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
    resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
  }

  // 개별 상품 상세 조회 성공 테스트
  @Test
  public void findById_test() throws Exception {
    // given teardown.sql
    int id = 1;

    // when
    ResultActions resultActions = mvc.perform(
        get("/products/" + id)
    );

    // console
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // verify
    resultActions.andExpect(jsonPath("$.success").value("true"));
    resultActions.andExpect(jsonPath("$.response.id").value(1));
    resultActions.andExpect(
        jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    resultActions.andExpect(jsonPath("$.response.description").value(""));
    resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
    resultActions.andExpect(jsonPath("$.response.price").value(1000));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
  }

  @Test
  // 개별 상품 상세 조회 실패 테스트
  public void findById_fail_test() throws Exception {
    // given teardown.sql
    int id = 0;

    // when
    ResultActions resultActions = mvc.perform(
        get("/products/" + id)
    );

    // verify
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다 : " + id));
    resultActions.andExpect(jsonPath("$.error.status").value(404));
    resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
  }

}
