package com.example.kakao.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//GlobalExceptionHandler, PriductResetController를 SpringContext에 등록

@Import({
        FakeStore.class,
        SecurityConfig.class,
})

@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("전체 상품 조회 테스트")
    public void findAll_test() throws Exception{
        //given
        int page=0;

        //when - 상품 요청 시뮬레이션, 반환 결과를 result에 저장
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page",String.valueOf(page))
                //200 상태 확인 - 너무 길게 나와서 고민중...
        );//.andExpect(status().isOk()).andDo(print());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then - 검증 (추가 필요)
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        //데이터를 직접 넣어보는 방식?
//        BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
//                new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000),
//                new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "description", "/image/path", 2000),
//                new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "description", "/image/path", 30000)
//        ));
        //stub
//        List<Product> products = productDummyList();
//        // fakestore의 getProductList가 반환하는 값을 products로 지정
//        Mockito.when(fakeStore.getProductList()).thenReturn(products);
        //then
//        List<Product> pagedProducts = products.stream().skip(page*9).limit(9).collect(Collectors.toList());
//        String productsJson = om.writeValueAsString(ApiUtils.success(pagedProducts));
//        assertThat(responseBody).isEqualTo(productsJson);
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
                //200 상태 확인 - 너무 길게 나와서 고민중...
        );//.andExpect(status().isOk()).andDo(print());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then (검증 - 추가 필요)
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
//        Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);
//        BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
//                new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000)));
//        BDDMockito.given(fakeStore.getOptionList()).willReturn(Arrays.asList(
//                new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
//                new Option(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
//                new Option(3, product, "고무장갑 베이지 S(소형) 6팩", 9900),
//        ));
        //stub - product, option
        //List<Product> products = fakeStore.productDummyList();
        //List<Product> products = productDummyList();
        // Mockito.when(fakeStore.getProductList()).thenReturn(products);
        //List<Option> options = optionDummyList(products);
        //Mockito.when(fakeStore.getOptionList()).thenReturn(options);
        //when
        //then
//        List<Option> selectedOptions = new ArrayList<>();
//
//        selectedOptions.add(options.get(6));
//        selectedOptions.add(options.get(7));
//        selectedOptions.add(options.get(8));
//        System.out.println("선택옵션: ");
//
//        Product selectedProduct = products.get(1);
//        ProductResponse.FindByIdDTO findByIdDTO = new ProductResponse.FindByIdDTO(selectedProduct, selectedOptions);
//        String productJson = om.writeValueAsString(ApiUtils.success(findByIdDTO));
//
//        assertThat(responseBody).isEqualTo(productJson);
    }

    @Test
    @DisplayName("개별 상품 상세 조회 - 없는 id 404 테스트")
    public void findById_404_test() throws Exception {
        //given - 조회할 상품 id
        int id = -1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
                //200 상태 확인 - 너무 길게 나와서 고민중...
        );//.andExpect(status().isOk()).andDo(print());
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then (검증 - 추가 필요)
    }
}
