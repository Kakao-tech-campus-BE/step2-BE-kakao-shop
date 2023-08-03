package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        //then
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.productDTOS[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.productDTOS[0].itemDTOS[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.productDTOS[0].itemDTOS[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.productDTOS[0].itemDTOS[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.productDTOS[0].itemDTOS[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.productDTOS[0].itemDTOS[1].quantity").value(1));
        resultActions.andExpect(jsonPath("$.response.productDTOS[0].itemDTOS[1].price").value(10900));

        resultActions.andExpect(jsonPath("$.response.productDTOS[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.productDTOS[1].itemDTOS[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.productDTOS[1].itemDTOS[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.productDTOS[1].itemDTOS[0].price").value(250000));

        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
