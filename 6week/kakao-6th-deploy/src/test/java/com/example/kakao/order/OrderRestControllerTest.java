package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderRestControllerTest extends MyRestDoc {


    @WithUserDetails(value = "ssar@nate.com")
    @Test
    public void orderSave_test() throws Exception {
        //given
        // user 정보를 담은 order 객체 하나 만듬 -> order의 id:2
        // 카트에 있는 물품 초기화 -> item 으로 이동 (item의 id는 4, 5, 6)

        //when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        //then

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssar@nate.com")
    @Test
    public void orderSaveFail_test() throws Exception {
        //given

        //when
        ResultActions resultActions1 = mvc.perform(
                post("/orders/save")
        );
        ResultActions resultActions2 = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions2.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions2.andExpect(jsonPath("$.success").value("false"));
        resultActions2.andExpect(jsonPath("$.error.status").value(404));
        resultActions2.andDo(MockMvcResultHandlers.print()).andDo(document);

    }


    @WithUserDetails(value = "ssar@nate.com")
    @Test
    public void orderFindById_test() throws Exception {
        //given
        //user_id 1의 order의 itemlist
        int id = 1;
        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssar@nate.com")
    @Test
    public void orderFindByIdFail_test() throws Exception{
        //given
        int id = 10;

        //when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


}