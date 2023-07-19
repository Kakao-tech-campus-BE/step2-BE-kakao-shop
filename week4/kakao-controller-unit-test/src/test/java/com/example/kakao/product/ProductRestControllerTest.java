package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({FakeStore.class,
        SecurityConfig.class // 이것을 설정해주지 않으면, 기본적으로 프로젝트에 spring security가 설정되어 있기 때문에, get요청 조차도 실패한다. (401 unauthorized)
})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll_test() throws Exception {

        // given

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpectAll(MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                MockMvcResultMatchers.jsonPath("$.response[0].description").value(""),
                MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"),
                MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
                );
    }

    @Test
    void findAll_with_param_test() throws Exception {

        // given
        int page = 0;

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andDo(print());

        // then
        result.andExpectAll(MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                MockMvcResultMatchers.jsonPath("$.response[0].description").value(""),
                MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"),
                MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }

    @Test
    void findById_test() throws Exception {

        // given
        int id = 1;

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/products/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print());

        // then
        result.andExpectAll(MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response.id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                MockMvcResultMatchers.jsonPath("$.response.description").value(""),
                MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"),
                MockMvcResultMatchers.jsonPath("$.response.price").value(1000),
                MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000),
                MockMvcResultMatchers.jsonPath("$.error").isEmpty()
        );
    }
}