package com.example.kakao.order;
import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.CartService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class OrderIntegrationTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;


    @Test
    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @DisplayName("post - /orders/save 주문하기")
    void save_test() throws Exception {
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value("310900"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @Test
    @WithUserDetails(value = "testuser@naver.com" , userDetailsServiceBeanName = "userService")
    @DisplayName("post - /orders/save 주문하기 실패 장바구니비어있음")
    void save_test_fail1() throws Exception {
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
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 유저의 장바구니가 비어있습니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @Test
    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @DisplayName("get - /orders 주문조회하기")
    void findById_test() throws Exception {
        // given
        int  userid=1;
        int orderid=1;
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+orderid)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(orderid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @WithUserDetails(value = "testuser@naver.com" , userDetailsServiceBeanName = "userService")
    @DisplayName("get - /orders 주문조회하기 실패 : 요청한 유저의 주문이 아님")
    void findById_test_fail1() throws Exception {
        // given
        int  userid=2;
        int orderid=1;
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+orderid)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("요청한 유저의 주문이 아닙니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @DisplayName("get - /orders 주문조회하기 실패 : 해당 주문번호는 없는 번호입니다.")
    void findById_test_fail2() throws Exception {
        // given
        int  userid=1;
        int orderid=100;
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+orderid)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 주문번호는 없는 번호입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
