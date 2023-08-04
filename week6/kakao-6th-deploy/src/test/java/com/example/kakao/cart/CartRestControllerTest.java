package com.example.kakao.cart;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.web.request.CartSaveReqeust;
import com.example.kakao.cart.web.request.CartUpdateRequest;
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

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 저장 성공")
    public void save_cart() throws Exception {
        List<CartSaveReqeust> body = new LinkedList<>();
        body.add(new CartSaveReqeust(1L, 3));

        mvc.perform(
                        post("/carts/add")
                                .content(om.writeValueAsString(body))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("존재하지 않는 옵션 장바구니 요청으로 장바구니 저장 실패")
    public void save_cart_fail_not_found_option() throws Exception {
        List<CartSaveReqeust> body = new LinkedList<>();
        body.add(new CartSaveReqeust(100L, 3));

        mvc.perform(
                        post("/carts/add")
                                .content(om.writeValueAsString(body))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("존재하지 않는 옵션 요청입니다."))
                .andExpect(jsonPath("$.error.status").value(404));
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 조회 성공")
    public void find_cart() throws Exception {
        ResultActions resultActions = mvc.perform(
                        get("/carts")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.products[0].id").value(4))
                .andExpect(jsonPath("$.response.products[0].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
                .andExpect(jsonPath("$.response.products[0].carts[0].id").value(3))
                .andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(16))
                .andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
                .andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(50000))
                .andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5))
                .andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000))
                .andExpect(jsonPath("$.response.totalPrice").value(310900))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 업데이트 성공")
    public void update_cart_success() throws Exception {
        List<CartUpdateRequest> body = new LinkedList<>();
        body.add(new CartUpdateRequest(1L, 10));
        String bodyJson = om.writeValueAsString(body);

        mvc.perform(
                        post("/carts/update")
                                .content(bodyJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.carts[0].cartId").value("1"))
                .andExpect(jsonPath("$.response.carts[0].optionId").value("1"))
                .andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.carts[0].quantity").value(15))
                .andExpect(jsonPath("$.response.carts[0].price").value(10000));
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("중복된 옵션으로 인해 장바구니 업데이트 실패")
    public void update_cart_fail_duplicated_option() throws Exception {
        List<CartUpdateRequest> body = new LinkedList<>();
        body.add(new CartUpdateRequest(1L, 10));
        body.add(new CartUpdateRequest(1L, 2));
        String bodyJson = om.writeValueAsString(body);

        mvc.perform(
                        post("/carts/update")
                                .content(bodyJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("요청이 중복된 옵션이 존재합니다."))
                .andExpect(jsonPath("$.error.status").value(400));
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("존재하지 않는 장바구니로 인해 장바구니 업데이트 실패")
    public void update_cart_fail_not_found_cart() throws Exception {
        List<CartUpdateRequest> body = new LinkedList<>();
        body.add(new CartUpdateRequest(100L, 10));
        String bodyJson = om.writeValueAsString(body);

        mvc.perform(
                        post("/carts/update")
                                .content(bodyJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("존재하지 않는 장바구니 번호입니다."))
                .andExpect(jsonPath("$.error.status").value(404));
    }
}