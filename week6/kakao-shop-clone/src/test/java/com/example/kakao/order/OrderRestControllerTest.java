package com.example.kakao.order;

import com.example.kakao.MyRestDocTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDocTest {

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void save_test() throws Exception {
        // given

        // when

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        result.andDo(MockMvcResultHandlers.print());
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(310900));
    }

    @WithUserDetails(value = "ssar@nate.com")
    @Test
    void save_not_found_test() throws Exception {
        // given

        // when

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        result.andDo(MockMvcResultHandlers.print());
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void  find_by_id_test() throws Exception {
        // given
        int orderId = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + orderId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        result.andDo(MockMvcResultHandlers.print());
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[1].quantity").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(310900));
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void find_by_id_not_found_test() throws Exception {
        // given
        int orderId = 3;
        // when

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + orderId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        result.andDo(MockMvcResultHandlers.print());
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }

    @WithUserDetails(value = "ssar@nate.com")
    @Test
    void find_by_id_forbidden_test() throws Exception {
        // given
        int orderId = 1;
        // when

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + orderId)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        result.andDo(MockMvcResultHandlers.print());
        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(403));
    }
}
