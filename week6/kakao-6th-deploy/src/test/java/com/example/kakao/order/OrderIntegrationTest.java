package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderIntegrationTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "rhalstjr1999@naver.com")
    @Test
    @DisplayName("결제 테스트")
    void save_test() throws Exception {
        //given teardown.sql

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value("2"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value("4"));
        resultActions.andDo(print()).andDo(document);
    }

    @WithUserDetails(value = "rhalstjr1999@naver.com")
    @Test
    @DisplayName("주문 조회 테스트")
    void find() throws Exception {
        //given
        int id = 2;

        //when
        ResultActions resultActions1 = mvc.perform(
                post("/orders/save")
        );

        ResultActions resultActions2 = mvc.perform(
                get("/orders/{id}", id)
        );

        String responseBody = resultActions2.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions2.andExpect(jsonPath("$.success").value("true"));
        resultActions2.andExpect(jsonPath("$.response.id").value("2"));
        resultActions2.andExpect(jsonPath("$.response.products[0].items[0].id").value("4"));
        resultActions2.andDo(print()).andDo(document);
    }

    @WithUserDetails(value = "rhalstjr1999@naver.com")
    @Test
    @DisplayName("존재하지 않는 주문 조회 테스트")
    void notExist_order_find_test() throws Exception {
        //given
        int id = -1;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/{id}", id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print())
                .andDo(document);
    }
}