package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.nullValue;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @DisplayName("전체 상품 조희 테스트")
    @Test
    public void findAll_test() throws Exception{

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].price").value(2000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].price").value(30000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[3].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[3].price").value(4000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[4].productName").value("[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[4].price").value(5000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[5].productName").value("굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[5].price").value(15900));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[6].productName").value("eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[6].price").value(26800));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[7].productName").value("제나벨 PDRN 크림 2개. 피부보습/진정 케어"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[7].price").value(25900));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[8].productName").value("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[8].price").value(797000));


    }

    @DisplayName("상품 상세 조희 테스트")
    @Test
    public void findById_test() throws Exception {

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.starCount").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].price").value(10900));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].optionName").value("고무장갑 베이지 S(소형) 6팩"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].price").value(9900));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[3].optionName").value("뽑아쓰는 키친타올 130매 12팩"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[3].price").value(16900));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[4].optionName").value("2겹 식빵수세미 6매"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[4].price").value(8900));

    }

    @DisplayName("상품 상세 조희 - 404Exception 테스트")
    @Test
    public void findById_404_test() throws Exception {
        //given
        int id = 12345;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(nullValue()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:"+id));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));

    }
}