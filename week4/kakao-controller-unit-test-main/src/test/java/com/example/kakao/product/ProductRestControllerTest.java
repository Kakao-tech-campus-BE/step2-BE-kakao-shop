package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc;

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om;

    @Test
    void findAll_default_test() throws Exception {
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(200));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    void findAll_test() throws Exception {
        // given
        int page = 0;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page","0")
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(200));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    void findAll_403error_test() throws Exception {
        // given
        int page = -1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page",String.valueOf(page))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(403));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
    }

    @Test
    void findById_test() throws Exception {
        // given
        int id=1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get(String.format("/products/%d", id))
        );

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @Test
    void findById_403error_test() throws Exception {
        // given
        int id=-1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get(String.format("/products/%d", id))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(403));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
    }

    @Test
    void findById_404error_test() throws Exception {
        // given
        int id=40;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get(String.format("/products/%d", id))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(404));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
    }
}