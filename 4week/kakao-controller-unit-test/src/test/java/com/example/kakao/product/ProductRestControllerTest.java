package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({
        FakeStore.class,
        SecurityConfig.class,
})

@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("전체 상품 목록 조회 테스트")
    public void findAll_test() throws Exception{
        //given - 0번 페이지 확인
        int page=0;

        //when - 상품 요청 시뮬레이션, 반환 결과를 result에 저장 -> json 출력, 확인
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page",String.valueOf(page))
                        .contentType(MediaType.APPLICATION_JSON)
        //200 검증
        ).andExpect(status().isOk());//.andDo(print());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then - 검증
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));
    }

    @Test
    @DisplayName("존재하지 않는 페이지 404 테스트")
    public void findAll_404_test() throws Exception{
        //given - 10번 페이지 확인 -> 존재하지 않는 페이지
        int page=10;

        //when - 상품 요청 시뮬레이션, 반환 결과를 result에 저장 -> json 출력, 확인
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page",String.valueOf(page))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then - 검증
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("404"));
    }

    @Test
    @DisplayName("개별 상품 상세 조회 테스트")
    public void findById_test() throws Exception {
        //given - 조회할 상품 id
        int id=1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                //200 상태 확인
        ).andExpect(status().isOk());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then 검증
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
    }

    @Test
    @DisplayName("존재하지 않는 상품 404 테스트")
    public void findById_404_test() throws Exception {
        //given - 조회할 상품 id (존재 X)
        int id = -1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON));


        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then 검증
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value("404"));
    }
}
