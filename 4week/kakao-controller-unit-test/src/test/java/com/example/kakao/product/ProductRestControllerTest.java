package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class,
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity{

    // Mock 객체의 모든 메서드는 추상메서드로 구현됩니다.
    /*
     */
    @MockBean
    ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;



    // 전체 상품 조회
    @Test
    public void AllProductFindTest() throws Exception {
        // given

        // stub

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value("1"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].id").value("2"));

    }

    // 파라미터를 주고 페이지 검색
    @Test
    public void ProductFindPageTest() throws Exception {
        // given
        int page = 1;
        // stub

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", Integer.toString(page))
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value("10"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].id").value("11"));

    }

    @Test
    // 개별 상품 상세 조회
    public void ProductDetailFindTest() throws Exception {
        // given
        int id = 4;
        Product product = productDummyList().get(id);
        Integer param = new Integer(id);

        // stub

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + param)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트: " + responseBody);


        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value("4"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value("15"));


    }




}
