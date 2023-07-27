package com.example.kakao.order;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void save_test() throws Exception {
        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_test() throws Exception {
        //given
        int id = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_fail_test() throws Exception {
        // order가 존재하지 않는 경우
        //given
        int id = 2;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }
}
