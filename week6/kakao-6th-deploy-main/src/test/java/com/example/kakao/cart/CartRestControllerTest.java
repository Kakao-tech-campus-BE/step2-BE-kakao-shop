package com.example.kakao.cart;

import com.example.kakao.MyRestDoc;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CartRestControllerTest extends MyRestDoc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("장바구니 저장 성공")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void add_success_test() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(1);
        item1.setQuantity(5);

        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(2);
        item2.setQuantity(5);

        requestDTOs.add(item1);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //when
        ResultActions result = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error").isEmpty());
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 저장 실패 - 최소 수량 미달")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void add_fail_test1() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(1);
        item.setQuantity(-1); //5에서 -1로 수정

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //when
        ResultActions result = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("addCartList.requestDTOs[0].quantity: 수량은 최소 1 이상 이어야 합니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 저장 실패 - 최대 수량 초과")
    void add_fail_test2() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(1);
        item.setQuantity(1000); //5에서 1000으로 수정

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //when
        ResultActions result = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("addCartList.requestDTOs[0].quantity: 최대 수량은 100개를 넘을 수 없습니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 저장 실패 - 옵션 없음")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void add_fail_test3() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();

        item.setOptionId(null); //option id 정보 누락
        item.setQuantity(1);

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //when
        ResultActions result = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("addCartList.requestDTOs[0].optionId: 옵션 아이디는 필수 입력 값입니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 저장 실패 - 수량 없음")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void add_fail_test4() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(1);
        item.setQuantity(null); //quantity 정보 누락

        requestDTOs.add(item);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //when
        ResultActions result = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("addCartList.requestDTOs[0].quantity: 수량은 필수 입력 값입니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 저장 실패 - 인증 실패")
    //@WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void add_fail_test5() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(1);
        item.setQuantity(5);

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //when
        ResultActions result = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isUnauthorized());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(jsonPath("$.error.status").value(401));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 저장 실패 - 동일한 옵션 아이디 존재")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void add_fail_test6() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(1);
        item1.setQuantity(5);

        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(1);
        item2.setQuantity(5);

        requestDTOs.add(item1);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        //when
        ResultActions result = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("입력 중 동일한 옵션 아이디가 존재합니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 조회")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void findAll_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody= " + responseBody);

        //then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.totalPrice").value(310900));
        result.andExpect(jsonPath("$.response.products[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        result.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 성공")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_success_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO req1 = new CartRequest.UpdateDTO();
        req1.setCartId(1);
        req1.setQuantity(10);

        CartRequest.UpdateDTO req2 = new CartRequest.UpdateDTO();
        req2.setCartId(2);
        req2.setQuantity(10);

        requestDTOs.add(req1);
        requestDTOs.add(req2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        System.out.println("result=" + result);
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(jsonPath("$.success").value(true));
        result.andExpect(jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(jsonPath("$.response.carts[0].price").value(100000));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 최소 수량 미달")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_fail_test1() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(-1); //10에서 -1로 수정

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("update.requestDTOs[0].quantity: 수량은 최소 1 이상 이어야 합니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 최대 수량 초과")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_fail_test2() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(1000); //10에서 1000으로 수정

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("update.requestDTOs[0].quantity: 최대 수량은 100개를 넘을 수 없습니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 카트 없음")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_fail_test3() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(null); //cart id 정보 누락
        item.setQuantity(1);

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("update.requestDTOs[0].cartId: 장바구니 아이디는 필수 입력 값입니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 수량 없음")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_fail_test4() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(null); //quantity 정보 누락

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("update.requestDTOs[0].quantity: 수량은 필수 입력 값입니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 인증 실패")
    //@WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_fail_test5() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isUnauthorized());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        result.andExpect(jsonPath("$.error.status").value(401));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 동일한 카트 아이디 존재")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_fail_test6() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(10);

        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(1);
        item2.setQuantity(10);

        requestDTOs.add(item1);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("입력 중 동일한 장바구니 아이디가 존재합니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 사용자 장바구니에 없는 카트 아이디")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    void update_fail_test7() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(10);
        item.setQuantity(10);

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

       // then
        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("해당 카트를 찾을 수 없습니다."));
        result.andExpect(jsonPath("$.error.status").value(404));
        result.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니 수정 실패 - 빈 장바구니")
    @WithUserDetails(value = "gihae0805@nate.com")
    @Test
    void update_fail_test8() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(3);
        item.setQuantity(10);

        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("requestBody=" + requestBody);

        // when
        ResultActions result = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody=" + responseBody);

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.success").value(false));
        result.andExpect(jsonPath("$.response").isEmpty());
        result.andExpect(jsonPath("$.error.message").value("사용자의 장바구니가 비어있습니다."));
        result.andExpect(jsonPath("$.error.status").value(400));
        result.andDo(print()).andDo(document);
    }
}