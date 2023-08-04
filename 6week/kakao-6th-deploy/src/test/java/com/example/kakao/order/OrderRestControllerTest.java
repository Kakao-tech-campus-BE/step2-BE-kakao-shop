package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception{
        //given teardown.sql

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test_withEmptyCart() throws Exception{
        //given
        mvc.perform(
                post("/orders/save") //장바구니 비우기
        );

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test() throws Exception{
        //given teardown.sql
        Integer id = 1;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
        );


        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test_withInvalidId() throws Exception{
        //given teardown.sql
        Integer id = 3;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
        );

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다 : " + id));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

}
