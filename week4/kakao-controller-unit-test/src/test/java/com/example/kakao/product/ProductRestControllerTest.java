package com.example.kakao.product;


import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = ProductRestController.class)
public class ProductRestControllerTest {

    @Autowired
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Test
    public void find_all_test() throws Exception{
        //given
        String uri = "/products";

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get(uri)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    }
    @Test
    public void find_by_id_test() throws Exception{
        //given
        String uri = "/products/1";

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get(uri)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);


        //then
        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    }
}
