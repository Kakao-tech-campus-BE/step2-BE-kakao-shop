package com.example.kakao.cart;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FakeStore fakeStore;

    // 가짜 객체를 만들어서 인증이 된 상태로 실행 가능


    @Test
    @DisplayName("장바구니 조회 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Order(1)
    public void find_test() throws Exception {
        //given
        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        //then - fakestore의 cart Dummy List 값이 잘 들어오고 있음을 확인
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName")
                .value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(50000));
    }

    @Test
    @DisplayName("인증되지 않은 사용자의 카트 조회")
    void find_401_test() throws Exception {
        //given

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("401"));
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    void add_test() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(7);
        requestDTOs.add(saveDTO1);

        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(3);
        saveDTO2.setQuantity(11);
        requestDTOs.add(saveDTO2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("인증되지 않은 사용자의 카트 추가")
    void add_401_test() throws Exception {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(7);
        requestDTOs.add(saveDTO1);

        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(3);
        saveDTO2.setQuantity(11);
        requestDTOs.add(saveDTO2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("401"));
    }

    @Test
    @DisplayName("장바구니 업데이트 테스트")
    @Order(10)
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(10);
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }

    @Test
    @DisplayName("인증되지 않은 사용자의 카트 업데이트")
    void update_401_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(10);
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("401"));
    }

}
