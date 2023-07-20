package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@Import({
        // 인증이 필요없어서 미포함시키고 실행시켰는데 이게 없다면 responseBody 출력이 안된다. 왜????
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;
    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Test
    public void find_all_success_test() throws Exception {
        // given
        BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
                newProduct(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000),
                newProduct(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", 2, 2000),
                newProduct(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", 3, 30000),
                newProduct(4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", 4, 4000),
                newProduct(5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", 5, 5000),
                newProduct(6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", 6, 15900),
                newProduct(7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", 7, 26800),
                newProduct(8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", 8, 25900),
                newProduct(9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", 9, 797000),
                newProduct(10,"통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정", 10, 8900),
                newProduct(11, "아삭한 궁채 장아찌 1kg 외 인기 반찬 모음전", 11, 6900),
                newProduct(12, "깨끗한나라 순수소프트 30롤 2팩. 무형광, 도톰 3겹", 12, 28900),
                newProduct(13, "생활공작소 초미세모 칫솔 12입 2개+가글 증정", 13, 9900),
                newProduct(14, "경북 영천 샤인머스켓 가정용 1kg 2수 내외", 14, 9900),
                newProduct(15, "[LIVE][5%쿠폰] 홈카페 Y3.3 캡슐머신 베이직 세트", 15, 148000)
        ));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("===== [전체상품조회 테스트] =====");
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void find_by_id_success_test() throws Exception {
        // given
        BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
                newProduct(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000))
        );
        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        BDDMockito.given(fakeStore.getOptionList()).willReturn(Arrays.asList(
                newOption(product, 1,"01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                newOption(product, 2,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                newOption(product, 3,"고무장갑 베이지 S(소형) 6팩", 9900),
                newOption(product, 4,"뽑아쓰는 키친타올 130매 12팩", 16900),
                newOption(product, 5,"2겹 식빵수세미 6매", 8900)
        ));

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                    .get("/products/1")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("===== [개별상품조회 테스트 - 1번항목조회] =====");
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void find_by_id_404error_test() throws Exception {
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("===== [개별상품조회 테스트 - 404 Error] =====");
        System.out.println(responseBody);
        System.out.println();

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
    }
}