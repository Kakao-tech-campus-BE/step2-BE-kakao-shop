package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity {

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @DisplayName("(기능4)전체_상품_목록_조회_test")
    @Test
    public void findAll_test() throws Exception {

        // given

        // stub
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @DisplayName("(기능4)전체_상품_목록_조회_page_test")
    @Test
    public void findAll_page_test() throws Exception {

        // given
        int page = 1;

        // when
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(10));
    }

    @DisplayName("(기능4)전체_상품_목록_조회_page_실패_test")
    @Test
    public void findAll_page_fail_test() throws Exception {

        // given
        int page = 1000;

        // when
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 페이지를 찾을 수 없습니다:"+page));
    }

    @DisplayName("(기능5)개별_상품_상세_조회_성공_test")
    @Test
    public void findById_test() throws Exception {

        // given
        int id = 5;

        // stub
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(id));
    }

    @DisplayName("(기능5)개별_상품_상세_조회_실패_test")
    @Test
    public void findById_fail_test() throws Exception {

        // given
        int id = -1; //1000

        // stub
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        ).andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:"+id));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }
}
