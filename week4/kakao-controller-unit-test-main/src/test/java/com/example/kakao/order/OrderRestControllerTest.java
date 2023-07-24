package com.example.kakao.order;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})
@WebMvcTest(controllers = {OrderRestController.class})
class OrderRestControllerTest {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc;

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om;

    @Autowired
    FakeStore fakeStore;

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void save_test() throws Exception {
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                .post("/orders/save")
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(200));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isNotEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get(String.format("/orders/%d", id))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(200));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isNotEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_403error_test() throws Exception {
        // given
        int id = -1;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get(String.format("/orders/%d", id))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(403));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findById_404error_test() throws Exception {
        // given
        int id = 2;

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get(String.format("/orders/%d", id))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().is(404));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
    }
}