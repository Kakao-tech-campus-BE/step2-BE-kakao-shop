package com.example.kakao._core.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void findAll_test() throws Exception {
        //given
        Integer page = 0;

        //when
        ResultActions resultActions = mvc.perform(get("/products").param("page", page.toString()));

        //eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트: " + responseBody);

        //then


    }
}
