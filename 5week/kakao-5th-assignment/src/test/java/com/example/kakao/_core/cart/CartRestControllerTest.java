package com.example.kakao._core.cart;

import com.example.kakao.cart.CartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com") // ssarmango@nate.com으로 DB에 있는지 조회 -> 있으면 통과
    @Test
    public void addCartList_test() throws Exception {

        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(3);
        item.setQuantity(5);
        item.setPrice(49500); // 이 코드 추가해줌
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        //System.out.println("테스트 : "+requestBody);

        // when
        //mvc.perform(post("/carts/add").content("optionId=3&quantity=5").contentType(MediaType.APPLICATION_FORM_URLENCODED));
        //json data를 보내는 것을 알려줌
        ResultActions resultActions = mvc.perform(post("/carts/add").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        // eye
        //String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));

    }

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
        //System.out.println("테스트 : " + responseBody);

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
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);
        item.setPrice(100000);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        //System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        //System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));
    }

}
