package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao.user.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("Order Controller 테스트")
@Import({
        SecurityConfig.class
})
@AutoConfigureMockMvc
@SpringBootTest
public class OrderRestControllerTest {
    private final MockMvc mvc;

    private final AuthenticationManager authenticationManager;

    private String userToken;

    public OrderRestControllerTest(
            @Autowired MockMvc mvc,
            @Autowired AuthenticationManager authenticationManager) {
        this.mvc = mvc;
        this.authenticationManager = authenticationManager;
    }

    @BeforeEach
    public void setUp() {
        UserRequest.LoginDTO request = new UserRequest.LoginDTO();
        request.setEmail("moon@naver.com");
        request.setPassword("qwer1234!");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userToken = JWTProvider.create(userDetails.getUser());
    }

    @DisplayName("insert 테스트")
    @Test
    public void insertTest() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 응답 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("select 테스트")
    @Test
    public void selectTest() throws Exception {
        // given
        int orderId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + orderId)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 응답 : " + responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
