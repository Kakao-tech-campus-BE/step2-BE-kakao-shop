package com.example.kakao.cart;

import com.example.kakao.MyRestDoc;
import com.example.kakao.user.UserService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartIntegrationTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("post - /carts/add 장바구니 추가")
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
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("post - /carts/add 장바구니 추가 실패 : 동일 옵션이 두개 이상")
    public void addCartList_test_fail1() throws Exception {

        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(3);
        item1.setQuantity(5);
        requestDTOs.add(item1);
        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(3);
        item2.setQuantity(5);
        requestDTOs.add(item2);
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
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("요청에 동일한 옵션이 두개이상 포함되어있습니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("get - /carts 장바구니 조회")
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
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.totalPrice").value("310900"))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("post - /carts/update 장바구니 수정")
    public void update_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(11);
        requestDTOs.add(item1);
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(2);
        item2.setQuantity(12);
        requestDTOs.add(item2);

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
        resultActions.andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.response.carts[0].cartId").value("1"))
                .andExpect(jsonPath("$.response.carts[0].optionId").value("1"))
                .andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
                .andExpect(jsonPath("$.response.carts[0].quantity").value(11))
                .andExpect(jsonPath("$.response.carts[0].price").value(110000))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "testuser@naver.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("장바구니 수량 업데이트 테스트 실패 : 유저 장바구니에 아무것도 없을 경우")
    public void update_test_fail1() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(11);
        requestDTOs.add(item1);
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(2);
        item2.setQuantity(12);
        requestDTOs.add(item2);

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
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 유저의 장바구니가 비어있습니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("장바구니 수량 업데이트 테스트 실패 : 동일한 장바구니 아이디가 두번 들어올 경우")
    public void update_test_fail2() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(11);
        requestDTOs.add(item1);
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(1);
        item2.setQuantity(12);
        requestDTOs.add(item2);

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
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("요청에 동일한 장바구니가 두개이상 포함되어있습니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("장바구니 수량 업데이트 테스트 실패 : 유저의 장바구니가 아닌 장바구니아이디가 들어올 경우")
    public void update_test_fail3() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(4);
        item1.setQuantity(10);
        requestDTOs.add(item1);
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(5);
        item2.setQuantity(10);
        requestDTOs.add(item2);

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
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("요청하신 장바구니는 해당 유저의 장바구니가 아닙니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "ssarmango@nate.com" , userDetailsServiceBeanName = "userService")
    @Test
    @DisplayName("장바구니 수량 업데이트 테스트 실패 : 갯수가 0이하")
    public void update_test_fail4() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(0);
        requestDTOs.add(item1);
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(2);
        item2.setQuantity(-1);
        requestDTOs.add(item2);

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
        resultActions.andExpect(jsonPath("$.success").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("옵션의 개수가 1보다 작을 수 없습니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(400))
                .andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}