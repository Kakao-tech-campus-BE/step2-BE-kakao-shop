package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private FakeStore fakeStore;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void 페이징된_상품들_가져오기() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].id").hasJsonPath())
                .andExpect(jsonPath("$.response[0].productName").hasJsonPath())
                .andExpect(jsonPath("$.response[0].description").hasJsonPath())
                .andExpect(jsonPath("$.response[0].image").hasJsonPath())
                .andExpect(jsonPath("$.response[0].price").hasJsonPath())
                .andExpect(jsonPath("$.error").hasJsonPath());
    }
}
