package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.Cart;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("결재")
    public void save_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("결재 실패 - 빈 장바구니")
    public void save_fail_test_empty_cart() throws Exception {
        // given
        mvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다."));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("주문 결과 확인")
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("주문 결과 확인 실패 - 찾을 수 없는 orderId")
    public void findById_fail_test_invalid_orderId() throws Exception {
        // given
        int id = 10;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문 내역을 찾을 수 없습니다."));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
