package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})

@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void saveOrderTest() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findByIdTest() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/1")
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
