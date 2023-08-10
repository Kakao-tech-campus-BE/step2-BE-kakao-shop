package com.example.kakao.cart;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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
public class CartRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    // 장바구니 담기
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

    // 장바구니 조회
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

    // 장바구니 업데이트 (주문하기)
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

    // 동일한 옵션을 담을 경우
    // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartSameItem_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(1);
        item1.setQuantity(5);
        requestDTOs.add(item1);

        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(1);
        item2.setQuantity(10);
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
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 옵션을 선택할 수 없습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 찾을 수 없는 옵션인 경우
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartNotFoundOption_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(100);
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
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 옵션을 찾을 수 없습니다 : " + 100));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 동일한 장바구니 아이디가 들어올 경우
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void updateSameCartId_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(5);
        requestDTOs.add(item1);

        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(1);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("동일한 장바구니 요청입니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(400));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 유저 장바구니에 없는 cartId가 들어올 경우
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void updateNotFoundCartId_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(100);
        item.setQuantity(5);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("유효하지 않은 장바구니입니다."));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}