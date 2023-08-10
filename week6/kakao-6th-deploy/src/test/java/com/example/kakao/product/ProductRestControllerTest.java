package com.example.kakao.product;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest extends MyRestDoc {

    @Test
    @DisplayName("상품 전체 조회 성공")
    public void findAll_test() throws Exception {
        // given
        Integer page = 0;   // toString 위해 int 대신 Integer

        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
                        .param("page", page.toString()) // param은 String
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("상품 상세 조회 성공")
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.description").value(""));
        resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response.price").value(1000));
        resultActions.andExpect(jsonPath("$.response.starCount").value(5));
        resultActions.andExpect(jsonPath("$.response.options[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.options[0].price").value(10000));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("유효하지 않은 상품이면 실패")
    public void findById_notFound_test() throws Exception {
        // given
        int id = 100;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isNotFound()); // 404

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
