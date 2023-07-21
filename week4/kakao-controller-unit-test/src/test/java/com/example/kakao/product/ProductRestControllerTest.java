package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void findAll_test() throws Exception {
        //given
        int page = 1;
        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products?page=" + page)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void findById_test() throws Exception {
        //given
        int id = 1;
        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void findById_fail_test() throws Exception {
        //given
        int id = 16;
        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }
}
