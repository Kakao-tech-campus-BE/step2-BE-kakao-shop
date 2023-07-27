package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.CartRestController;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})


@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest extends DummyEntity {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;


    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void saveOrder_test() throws Exception{
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void saveOrder_test_failure_unauthenticated() throws Exception {
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
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized()); // 로그인이 안된 사용자는 저장할 수 없음
    }


    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findOrderById_test() throws Exception{
        // given
        int id = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findOrderById_test_failure_notFound() throws Exception {
        // given
        int id = 5; // 존재하지 않는 주문 ID

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
