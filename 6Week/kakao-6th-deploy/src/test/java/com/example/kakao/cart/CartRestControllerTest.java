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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest extends MyRestDoc {
    private final ObjectMapper om;

    private final CartJPARepository cartJPARepository;

    @Autowired
    public CartRestControllerTest(ObjectMapper om, CartJPARepository cartJPARepository) {
        this.om = om;
        this.cartJPARepository = cartJPARepository;
    }

    @DisplayName("장바구니 담기 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_test() throws Exception {
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

    @DisplayName("장바구니 조회 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findAll_test() throws Exception {
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

    @DisplayName("장바구니 수정 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_test() throws Exception {
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

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("장바구니 담기 시 동일한 옵션에 관한 실패 테스트")
    @Test
    public void addCartList_sameOption_fail_test() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(5);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(1);
        d2.setQuantity(5);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);

        //when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("동일한 옵션이 존재합니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("장바구니 담기 시 옵션 조회 실패 테스트")
    @Test
    public void addCartList_findOption_fail_test() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(Integer.MAX_VALUE);
        d1.setQuantity(5);
        requestDTOs.add(d1);
        String requestBody = om.writeValueAsString(requestDTOs);

        //when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("해당 옵션을 찾을 수 없습니다 : "+ Integer.MAX_VALUE))
                .andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Transactional
    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("장바구니 수정 시 장바구니에 상품이 없는 예외에 관한 실패 테스트")
    @Test
    public void update_emptyCart_fail_test() throws Exception {
        cartJPARepository.deleteAllById(Arrays.asList(1,2,3));

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

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("장바구니에 상품이 없습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("장바구니 수정 시 동일 장바구니 요청 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_DuplicateCartId_fail_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(10);
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(1);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("동일한 장바구니 요청이 있습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("장바구니 수정 시 해당 장바구니 조회 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_InvalidCartId_fail_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(Integer.MAX_VALUE);
        item.setQuantity(10);
        requestDTOs.add(item);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("해당 장바구니가 없습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("장바구니 수정 시 장바구니 요청 값 유효성 검사 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_validCartIdRequest_fail_test() throws Exception {
        //given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(0);
        item.setQuantity(1);
        requestDTOs.add(item);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("update.requestDTOs[0].cartId: 장바구니 정보가 잘못되었습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("장바구니 수정 시 수량 요청 값 유효성 검사 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_validQuantityRequest_fail_test() throws Exception {
        //given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(0);
        requestDTOs.add(item);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("update.requestDTOs[0].quantity: 수량이 잘못되었습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("장바구니 담기 시 옵션 요청 값 유효성 검사 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_valiOptionIdRequest_fail_test() throws Exception {
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(0);
        item.setQuantity(1);
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
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("addCartList.requestDTOs[0].optionId: 옵션 정보가 잘못되었습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("장바구니 담기 시 수량 요청 값 유효성 검사 실패 테스트")
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_valiQuantitydRequest_fail_test() throws Exception {
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(1);
        item.setQuantity(0);
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
        resultActions.andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("addCartList.requestDTOs[0].quantity: 수량이 잘못되었습니다."))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}