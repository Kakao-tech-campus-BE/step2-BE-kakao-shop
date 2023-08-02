package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.kakao.cart.CartJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {
    @Autowired
    private MockMvc mvc;
    private ObjectMapper om;
    private final CartJPARepository cartJPARepository;

    @Autowired
    public OrderRestControllerTest(ObjectMapper om, CartJPARepository cartJPARepository) {
        this.om = om;
        this.cartJPARepository = cartJPARepository;
    }


    @DisplayName("결제 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception{

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        resultActions.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.id").value(2))
                .andExpect(jsonPath("$.response.productDTOList[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.productDTOList[0].itemDTOList[0].id").value(4))
                .andExpect(jsonPath("$.response.productDTOList[0].itemDTOList[0].price").value(50000))
                .andExpect(jsonPath("$.response.productDTOList[0].itemDTOList[0].quantity").value(5))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("주문 결과 확인 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void orderFindById_test() throws Exception{
        //given
        int id = 1;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        //then
        resultActions.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.id").value(1))
                .andExpect(jsonPath("$.response.productDTOList[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.productDTOList[0].itemDTOList[0].id").value(1))
                .andExpect(jsonPath("$.response.productDTOList[0].itemDTOList[0].price").value(50000))
                .andExpect(jsonPath("$.response.productDTOList[0].itemDTOList[0].quantity").value(5))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Transactional
    @DisplayName("결제시 장바구니 조회 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void saveError_test() throws Exception{
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
                .andExpect(jsonPath("$.error.message").value("장바구니에 아무것도 없습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);;

    }
    @DisplayName("주문 조회 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findByOrderIdError_test() throws Exception {
        //given
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
                .andExpect(jsonPath("$.error.message").value("해당 주문이 없습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
