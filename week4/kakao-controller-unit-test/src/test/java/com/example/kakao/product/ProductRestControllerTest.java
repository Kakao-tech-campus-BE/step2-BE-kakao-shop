package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        SecurityConfig.class,
        FakeStore.class,
        GlobalExceptionHandler.class
})
@AutoConfigureMockMvc
@SpringBootTest
public class ProductRestControllerTest extends DummyEntity {
    private final MockMvc mockMvc;
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    public ProductRestControllerTest(
            @Autowired MockMvc mockMvc
    ) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("GET /products?page={idx} 테스트")
    @Test
    public void getProductsTest() throws Exception {
        // given
        long pageIndex = 0L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(pageIndex))
        );
        String response = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("response = " + response);

        // then
        resultActions.andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value("true")
        );
    }

    @DisplayName("GET /products?page={idx} 실패 테스트")
    @Test
    public void getProductsFailTest() throws Exception {
        // given
        long pageIndex = 100L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(pageIndex))
        );
        String response = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("response = " + response);

        // then
        resultActions.andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value("false")
        );
    }

    @DisplayName("GET /products/{id} 테스트")
    @Test
    public void getProductInformationTest() throws Exception {
        // given
        long productId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + productId)
        );
        String response = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("response = " + response);

        resultActions.andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value("true")
        );
    }
}
