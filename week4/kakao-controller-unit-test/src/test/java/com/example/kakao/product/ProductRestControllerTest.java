package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    void 상품_자세히보기_성공_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response").hasJsonPath())
                .andExpect(jsonPath("$.response.id").hasJsonPath())
                .andExpect(jsonPath("$.response.productName").hasJsonPath())
                .andExpect(jsonPath("$.response.description").isEmpty())
                .andExpect(jsonPath("$.response.image").hasJsonPath())
                .andExpect(jsonPath("$.response.price").hasJsonPath())
                .andExpect(jsonPath("$.response.starCount").hasJsonPath())
                .andExpect(jsonPath("$.response.options").isArray())
                .andExpect(jsonPath("$.response.options[0].id").hasJsonPath())
                .andExpect(jsonPath("$.response.options[0].optionName").hasJsonPath())
                .andExpect(jsonPath("$.response.options[0].price").hasJsonPath())
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    void 상품_자세히보기_실패_테스트() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/100")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error").hasJsonPath())
                .andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:100"))
                .andExpect(jsonPath("$.error.status").value(404));
    }
}
