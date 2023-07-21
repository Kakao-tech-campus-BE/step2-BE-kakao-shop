package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("Product_컨트롤러_테스트")
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity {

    private final MockMvc mvc;
    private final ObjectMapper om;

    private List<Product> productList = mock.getProductList();
    private List<Option> optionList = mock.getOptionList();

    @MockBean
    private FakeStore fakeStore;

    @MockBean
    private ProductService productService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    public ProductRestControllerTest(MockMvc mockMvc, ObjectMapper om) {
        this.mvc = mockMvc;
        this.om = om;
    }

    @DisplayName("전체_상품_목록_조회_mock_Controller_테스트")
    @Test
    public void product_findAll_mock_test() throws Exception {
        // given

        // when
        System.out.println(productList.get(0).getId());
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products-mock")
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
        resultActions.andExpect(jsonPath("$.response[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        resultActions.andExpect(jsonPath("$.response[1].description").value(""));
        resultActions.andExpect(jsonPath("$.response[1].image").value("/images/2.jpg"));
        resultActions.andExpect(jsonPath("$.response[1].price").value(2000));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));

    }

    @DisplayName("전체_상품_목록_조회_2페이지_mock_Controller_테스트")
    @Test
    public void product_findAll_page_2_test() throws Exception {
        // given
        int page = 1;

        // when
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products-mock")
                        .queryParam("page", String.valueOf(page))
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(10));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/10.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(8900));
        resultActions.andExpect(jsonPath("$.response[1].id").value(11));
        resultActions.andExpect(jsonPath("$.response[1].productName").value("아삭한 궁채 장아찌 1kg 외 인기 반찬 모음전"));
        resultActions.andExpect(jsonPath("$.response[1].description").value(""));
        resultActions.andExpect(jsonPath("$.response[1].image").value("/images/11.jpg"));
        resultActions.andExpect(jsonPath("$.response[1].price").value(6900));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));

    }

    @DisplayName("전체_상품_목록_조회_3페이지_mock_Controller_테스트_빈값")
    @Test
    public void product_findAll_page_3_test() throws Exception {
        // given
        int page = 2;

        // when
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products-mock")
                        .queryParam("page", String.valueOf(page))
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.size()").value(0));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));

    }

    @DisplayName("상세_상품_목록_조회_mock_Controller_테스트")
    @Test
    public void product_findById_mock_test() throws Exception {
        // given
        int id = 1;

        // when
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products-mock/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.price").value(1000));
        resultActions.andExpect(jsonPath("$.response.description").value(""));
        resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response.options[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.options[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.options[2].id").value(3));
        resultActions.andExpect(jsonPath("$.response.options[3].id").value(4));
        resultActions.andExpect(jsonPath("$.response.options[4].id").value(5));
        resultActions.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.options[2].optionName").value("고무장갑 베이지 S(소형) 6팩"));
        resultActions.andExpect(jsonPath("$.response.options[3].optionName").value("뽑아쓰는 키친타올 130매 12팩"));
        resultActions.andExpect(jsonPath("$.response.options[4].optionName").value("2겹 식빵수세미 6매"));
        resultActions.andExpect(jsonPath("$.response.options[0].price").value(10000));
        resultActions.andExpect(jsonPath("$.response.options[1].price").value(10900));
        resultActions.andExpect(jsonPath("$.response.options[2].price").value(9900));
        resultActions.andExpect(jsonPath("$.response.options[3].price").value(16900));
        resultActions.andExpect(jsonPath("$.response.options[4].price").value(8900));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("상세_상품_목록_조회_mock_Controller_실패_테스트_자료없음")
    @Test
    public void product_findById_mock_fail_test_no_data() throws Exception {
        // given
        int id = 100;

        // when
        Mockito.when(fakeStore.getProductList()).thenReturn(productList);
        Mockito.when(fakeStore.getOptionList()).thenReturn(optionList);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products-mock/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(Matchers.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:"+id));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
    }

    // ========================== real ==========================

    @DisplayName("전체_상품_목록_조회_Controller_테스트")
    @Test
    public void product_findAll_test() throws Exception {
        // given

        // when
        List<ProductResponse.FindAllDTO> findAllDTOS = productList.stream()
                .map(ProductResponse.FindAllDTO::new)
                .limit(9)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0, 9);
        Mockito.when(productService.getProducts(pageable)).thenReturn(findAllDTOS);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products"));

        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
        resultActions.andExpect(jsonPath("$.response[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        resultActions.andExpect(jsonPath("$.response[1].description").value(""));
        resultActions.andExpect(jsonPath("$.response[1].image").value("/images/2.jpg"));
        resultActions.andExpect(jsonPath("$.response[1].price").value(2000));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("전체_상품_목록_조회_2페이지_Controller_테스트")
    @Test
    public void product_findAll_page2_test() throws Exception {
        // given
        int page = 1;
        // when
        List<ProductResponse.FindAllDTO> findAllDTOS = productList.stream()
                .skip(9*page)
                .map(ProductResponse.FindAllDTO::new)
                .limit(9)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, 9);
        Mockito.when(productService.getProducts(pageable)).thenReturn(findAllDTOS);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .queryParam("page", String.valueOf(page))
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(10));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/10.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(8900));
        resultActions.andExpect(jsonPath("$.response[1].id").value(11));
        resultActions.andExpect(jsonPath("$.response[1].productName").value("아삭한 궁채 장아찌 1kg 외 인기 반찬 모음전"));
        resultActions.andExpect(jsonPath("$.response[1].description").value(""));
        resultActions.andExpect(jsonPath("$.response[1].image").value("/images/11.jpg"));
        resultActions.andExpect(jsonPath("$.response[1].price").value(6900));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("전체_상품_목록_조회_3페이지_Controller_테스트")
    @Test
    public void product_findAll_page3_test() throws Exception {
        // given
        int page = 2;

        // when
        List<ProductResponse.FindAllDTO> findAllDTOS = productList.stream()
                .skip(9*page)
                .map(ProductResponse.FindAllDTO::new)
                .limit(9)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, 9);
        Mockito.when(productService.getProducts(pageable)).thenReturn(findAllDTOS);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .queryParam("page", String.valueOf(page))
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.size()").value(0));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("상세_상품_목록_조회_Controller_테스트")
    @Test
    public void product_findById_test() throws Exception {
        // given
        int id = 1;

        // when
        Product mockProduct = productList.get(id-1);
        List<Option> mockOptions = optionList.stream()
                .filter(o -> o.getProduct().getId() == id)
                .collect(Collectors.toList());
        ProductResponse.FindByIdDTO findByIdDTOs = new ProductResponse.FindByIdDTO(mockProduct, mockOptions);
        Mockito.when(productService.getProductDetails(id)).thenReturn(findByIdDTOs);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.price").value(1000));
        resultActions.andExpect(jsonPath("$.response.description").value(""));
        resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response.options[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.options[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.options[2].id").value(3));
        resultActions.andExpect(jsonPath("$.response.options[3].id").value(4));
        resultActions.andExpect(jsonPath("$.response.options[4].id").value(5));
        resultActions.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.options[2].optionName").value("고무장갑 베이지 S(소형) 6팩"));
        resultActions.andExpect(jsonPath("$.response.options[3].optionName").value("뽑아쓰는 키친타올 130매 12팩"));
        resultActions.andExpect(jsonPath("$.response.options[4].optionName").value("2겹 식빵수세미 6매"));
        resultActions.andExpect(jsonPath("$.response.options[0].price").value(10000));
        resultActions.andExpect(jsonPath("$.response.options[1].price").value(10900));
        resultActions.andExpect(jsonPath("$.response.options[2].price").value(9900));
        resultActions.andExpect(jsonPath("$.response.options[3].price").value(16900));
        resultActions.andExpect(jsonPath("$.response.options[4].price").value(8900));
        resultActions.andExpect(jsonPath("$.error").value(Matchers.nullValue()));
    }

    @DisplayName("상세_상품_목록_조회_Controller_실패_테스트_데이터_없음")
    @Test
    public void product_findById_fail_test_no_data() throws Exception {
        // given
        int id = 100;

        // when
        // any의 경우 객체면 any(), int형이면 anyInt() 형식처럼 타입을 맞춰 넣어야 한다.
        Mockito.when(productService.getProductDetails(anyInt())).thenThrow(
                new Exception404("해당 상품을 찾을 수 없습니다:" + id));

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + id)
        );
        resultActions.andDo(print());

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.response").value(Matchers.nullValue()));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:" + id));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
    }
}

