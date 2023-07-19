package com.example.kakao.product;


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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.IsNull.nullValue;

@Import({
        GlobalExceptionHandler.class,
        SecurityConfig.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @Test
    public void findByAll_test() throws Exception {
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );

        HttpStatus status = HttpStatus.valueOf(result.andReturn().getResponse().getStatus());
        String errorMessage = result.andReturn().getResponse().getErrorMessage();
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + status);
        System.out.println("테스트: " + errorMessage);
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].price").value(2000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].price").value(30000));
        // 4~7번 product는 생략, 9번 product까지 페이지에 응답
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[7].id").value(8));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[7].productName").value("제나벨 PDRN 크림 2개. 피부보습/진정 케어"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[7].price").value(25900));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[8].id").value(9));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[8].productName").value("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[8].price").value(797000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @Test
    public void findById_test() throws Exception {
        // given


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", 1)
        );
        HttpStatus status = HttpStatus.valueOf(result.andReturn().getResponse().getStatus());
        String errorMessage = result.andReturn().getResponse().getErrorMessage();
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + status);
        System.out.println("테스트: " + errorMessage);
        System.out.println("테스트: " + responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].price").value(10900));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }
}
