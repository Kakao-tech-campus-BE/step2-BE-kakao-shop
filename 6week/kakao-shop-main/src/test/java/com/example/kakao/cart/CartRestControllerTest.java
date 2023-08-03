package com.example.kakao.cart;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest extends MyRestDoc {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 추가 테스트")
    public void cart_save_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(3);
        item.setQuantity(5);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("장바구니 추가 인증 에러 테스트")
    public void cart_save_unauthorized_test() throws Exception {
        // given
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(5);
        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(2);
        saveDTO2.setQuantity(3);

        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        saveDTOList.add(saveDTO1);
        saveDTOList.add(saveDTO2);

        String content = om.writeValueAsString(saveDTOList);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isUnauthorized());
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        resultActions.andExpect(jsonPath("$.error.status").value(401));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("장바구니 추가 중복된 optionId 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    public void cart_save_duplicate_optionId_test() throws Exception {
        // given
        int optionId = 1;

        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(optionId);
        saveDTO1.setQuantity(5);
        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(optionId);
        saveDTO2.setQuantity(3);

        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        saveDTOList.add(saveDTO1);
        saveDTOList.add(saveDTO2);

        String content = om.writeValueAsString(saveDTOList);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("옵션이 중복되었습니다 : " + optionId));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("장바구니 추가 존재하지 않는 optionId 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    public void cart_save_not_found_optionId_test() throws Exception {
        // given
        int optionId = 999;

        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(5);
        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(optionId);
        saveDTO2.setQuantity(3);

        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        saveDTOList.add(saveDTO1);
        saveDTOList.add(saveDTO2);

        String content = om.writeValueAsString(saveDTOList);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 옵션을 찾을 수 없습니다 : " + optionId));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("장바구니 조회 테스트")
    @Test
    public void cart_findAll_test() throws Exception {
        // given teardown

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("장바구니 조회 인증 에러 테스트")
    public void cart_findAll_unauthorized_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isUnauthorized());
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("인증되지 않았습니다"));
        resultActions.andExpect(jsonPath("$.error.status").value(401));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 수정하기 테스트")
    public void cart_update_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "error@nate.com")
    @Test
    @DisplayName("비어있는 장바구니 수정하기 에러 테스트")
    public void cart_empty_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니에 존재하는 상품이 없습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 아이디 중복 장바구니 수정하기 에러 테스트")
    public void cart_duplicate_cartId_test() throws Exception {
        // given
        int cartId = 1;

        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(cartId);
        item1.setQuantity(10);
        requestDTOs.add(item1);
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(cartId);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니 아이디가 중복되었습니다 : " + cartId));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("유저 장바구니에 없는 cartId 장바구니 수정하기 에러 테스트")
    public void cart_not_found_cartId_test() throws Exception {
        // given
        int cartId = 999;

        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(10);
        requestDTOs.add(item1);
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(cartId);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(IsNull.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("유저 장바구니에 없는 아이디입니다 : " + cartId));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}