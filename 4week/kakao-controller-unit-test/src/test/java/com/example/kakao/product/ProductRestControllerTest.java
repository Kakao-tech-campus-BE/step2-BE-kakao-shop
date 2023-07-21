package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.user.UserService;
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

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));

    }

    @Test
    public void findById_test() throws Exception {
        //given
        int id = 1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.starCount").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));

    }

}
