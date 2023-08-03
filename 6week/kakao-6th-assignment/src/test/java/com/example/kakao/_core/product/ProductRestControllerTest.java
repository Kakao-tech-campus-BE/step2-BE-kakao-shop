package com.example.kakao._core.product;

import com.example.kakao._core.MyRestDoc;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;
    @Test
    public void findAll_test() throws Exception {
        // given
        Integer page = 0;
        // when
        ResultActions resultActions = mvc.perform(get("/products").param("page", page.toString()));

        //eye 응답되는 json data를 직접 확인해볼 수 있음
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response[0].description").value(""))
                .andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"))
                .andExpect(jsonPath("$.response[0].price").value(1000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void findAll_InvalidPage_test() throws Exception {

        // given
        Integer page = -1;
        // when
        ResultActions resultActions = mvc.perform(get("/products").param("page", page.toString()));

        //eye 응답되는 json data를 직접 확인해볼 수 있음
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("잘못된 page 번호입니다. : -1"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void findById_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
        );

        // console
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.id").value(1))
                .andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.description").value(""))
                .andExpect(jsonPath("$.response.image").value("/images/1.jpg"))
                .andExpect(jsonPath("$.response.price").value(1000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void findById_productNotFound_test() throws Exception {
        // given teardown.sql
        int id = 20;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다 : 20"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
