package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import org.hamcrest.core.IsNull;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class OrderRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void orderSaveTest() throws Exception {
        //given

        //when
        ResultActions resultActions =mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(2));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test() throws Exception{

        //given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + id)
        );

        //eye
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : " + responseBody);

        //then

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    //이미 주문된 건에 한해 오류 발생
    public void afterEmptyCartTest() throws Exception {
        //given
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        //when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        //eye
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : " + responseBody);
        //then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("장바구니가 비어있습니다."));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_exception_test() throws Exception{

        //given
        int id = Integer.MAX_VALUE;
        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + id)
        );

        //eye
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 주문을 찾을 수 없습니다. : " + id));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}
