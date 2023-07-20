package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@Import({
        SecurityConfig.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc;

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om;

    @Test // url : /products
    public void findAll_test() throws Exception {
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        // 응답값 가져오기
        String responseBody = result.andReturn().getResponse().getContentAsString();

        // 맞게 가져왔는지 확인
        System.out.println("test : " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value( "/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));

    }
}
