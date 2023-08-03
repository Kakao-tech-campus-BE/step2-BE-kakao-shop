package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.CartJPARepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {
    private final ObjectMapper om;

    private final CartJPARepository cartJPARepository;

    @Autowired
    public OrderRestControllerTest(ObjectMapper om, CartJPARepository cartJPARepository) {
        this.om = om;
        this.cartJPARepository = cartJPARepository;
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("결재하기 테스트")
    @Test
    public void save_test() throws Exception {
        //given teardown

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.products[0].productName").value( "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.products[0].items[1].price").value(10900))
                .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
                .andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5))
                .andExpect(jsonPath("$.response.totalPrice").value(1510900))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문 결과 확인 테스트")
    @Test
    public void findById_test() throws Exception {
        //given teardown
        int id = 1;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.id").value(1))
                .andExpect(jsonPath("$.response.products[0].productName").value( "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.products[0].items[1].price").value(10900))
                .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
                .andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5))
                .andExpect(jsonPath("$.response.totalPrice").value(1510900))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Transactional
    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("결재하기 시 장바구니 조회 실패 테스트")
    @Test
    public void save_validateCart_fail_test() throws Exception {
        //given
        cartJPARepository.deleteAllById(Arrays.asList(1,2,3));

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("장바구니의 상품을 조회할 수 없습니다"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문 결과 확인 시 주문 조회 실패 테스트")
    @Test
    public void findById_findOrder_fail_test() throws Exception {
        //given teardown
        int id = 0;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("주문 번호를 찾을 수 없습니다"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}