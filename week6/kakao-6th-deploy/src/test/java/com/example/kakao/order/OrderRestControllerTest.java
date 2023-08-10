package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("상품 저장하기")
    public void order_save() throws Exception {

        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        resultActions
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.id").value(2))
                .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.products[0].items[0].id").value(1))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5))
                .andExpect(jsonPath("$.response.products[0].items[0].price").value(10000))
                .andExpect(jsonPath("$.response.products[0].items[1].id").value(2))
                .andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
                .andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1))
                .andExpect(jsonPath("$.response.products[0].items[1].price").value(10900))
                .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
                .andExpect(jsonPath("$.response.products[1].items[0].id").value(16))
                .andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
                .andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5))
                .andExpect(jsonPath("$.response.products[1].items[0].price").value(50000))
                .andExpect(jsonPath("$.response.totalPrice").value(310900))
                .andExpect(jsonPath("$.error").value(nullValue())
                );
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("상품 불러오기 성공")
    public void order_findById_success() throws Exception {

        // given
        int orderId = 1;

        // when
        mvc.perform(
                        get("/orders/{id}", orderId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.id").value(1))
                .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.products[0].items[0].id").value(1))
                .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5))
                .andExpect(jsonPath("$.response.products[0].items[0].price").value(50000))
                .andExpect(jsonPath("$.response..products[0].items[1].id").value(2))
                .andExpect(jsonPath("$.response..products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
                .andExpect(jsonPath("$.response..products[0].items[1].quantity").value(1))
                .andExpect(jsonPath("$.response..products[0].items[1].price").value(10900))
                .andExpect(jsonPath("$.response.totalPrice").value(1510900))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("상품 불러오기 실패")
    public void order_findById_fail() throws Exception {

        // given
        int orderId = 1000;

        // when
        mvc.perform(
                        get("/orders/{id}", orderId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("해당하는 주문이 없습니다."))
                .andExpect(jsonPath("$.error.status").value(404));
    }

}
