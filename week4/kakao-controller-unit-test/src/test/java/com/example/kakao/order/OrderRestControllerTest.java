package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    @DisplayName("상품 결제 테스트")
    void save_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    @DisplayName("주문 조회 테스트")
    void findById_test() throws Exception {
        //given
        int id = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    @DisplayName("잘못된 HTTP 메서드로 서버에 요청 테스트")
    void notAllowed_httpMethod_test() throws Exception {
        //given
        int id = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/{id}", id)
        );

        //then
        // 4xx 에러 검증 - 405
        result.andExpect(status().is4xxClientError()).andDo(print());
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 서버 자원에 접근 테스트")
    void notAuth_access_test() throws Exception {

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(status().is4xxClientError()).andDo(print());
    }
}