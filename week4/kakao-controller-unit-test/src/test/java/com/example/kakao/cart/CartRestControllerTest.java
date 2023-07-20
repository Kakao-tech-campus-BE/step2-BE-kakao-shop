package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Cart Controller 테스트")
@Import({
        SecurityConfig.class
})
@AutoConfigureMockMvc
@WebAppConfiguration
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartRestControllerTest {

    private final MockMvc mvc;

    private final ObjectMapper om;

    private final AuthenticationManager authenticationManager;

    private String userToken;

    public CartRestControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper om,
            @Autowired AuthenticationManager authenticationManager) {
        this.mvc = mvc;
        this.om = om;
        this.authenticationManager = authenticationManager;
    }

    @BeforeEach
    public void userSetUp() {
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("moon@naver.com");
        request.setPassword("qwer1234!");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userToken = JWTProvider.create(userDetails.getUser());
    }

    @DisplayName("select 테스트")
    @Test
    @Order(1)
    public void selectTest() throws Exception{
        // given

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 응답 결과 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].id").value(3));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].carts[0].option.id").value(12));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].carts[0].quantity").value(5));
    }

    @DisplayName("insert 테스트")
    @Test
    @Order(2)
    public void insertTest() throws Exception {
        // given
        List<CartRequest.SaveDTO> requests = new ArrayList<>();
        requests.add(
                CartRequest.SaveDTO.builder()
                        .optionId(3)
                        .quantity(5)
                        .build());
        requests.add(
                CartRequest.SaveDTO.builder()
                        .optionId(4)
                        .quantity(5)
                        .build()
        );
        String requestBody = om.writeValueAsString(requests);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 응답 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("update 테스트")
    @Test
    @Order(3)
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requests = new ArrayList<>();
        requests.add(
                CartRequest.UpdateDTO.builder()
                        .cartId(1)
                        .quantity(10)
                        .build()
        );
        requests.add(
                CartRequest.UpdateDTO.builder()
                        .cartId(2)
                        .quantity(10)
                        .build()
        );
        String requestBody = om.writeValueAsString(requests);
        System.out.println("테스트 : "+ requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
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

    @DisplayName("clear 테스트")
    @Test
    @Order(4)
    public void clearTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 응답 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
