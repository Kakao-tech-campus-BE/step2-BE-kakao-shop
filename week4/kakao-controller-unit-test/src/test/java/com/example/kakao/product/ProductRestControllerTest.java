package com.example.kakao.product;
// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
        FakeStore.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {// 다른 클래스의 객체를 autowired로 쉽게 import할수있음
    @Autowired
    private MockMvc mvc;//

    @Autowired
    private ObjectMapper om;//직렬화에 사용할 object mapper

    @Autowired
    private FakeStore fakeStore;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @MockBean
    private ProductService productService;

    @Test
    public void product_findById_test() throws Exception{
        //given
        //request데이터가 없기 때문에 설정이 없음
        // 1. 더미데이터 가져와서 상품 찾기
        int id = 1;
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        // 2. 더미데이터 가져와서 해당 상품에 옵션 찾기
        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
        // 3. DTO 변환
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
        // stub - findById서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(productService.findById(1)).thenReturn(responseDTO);
        //Mockito.when(productService.findById(1000)).thenReturn(responseDTO);
        //when
        // /products/1 의 url로 데이터를 요청함.
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //오류 해결 못함
        //없는 상품에 대해 조회를 하는 경우 - 404 error 시나리오
//        ResultActions result2 = mvc.perform(
//                MockMvcRequestBuilders
//                        .get("/products/1000")
//        );
//        String responseBody2 = result.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : "+responseBody2);

        //then - products리스트에서 1번 상품의 상세정보에 대한 정보 요청이다. 데이터가 일치하는지에 대한 테스트이다.
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.starCount").value(5));
        //option 1
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
        //option 2
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].price").value(10900));
        //option 3
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].optionName").value("고무장갑 베이지 S(소형) 6팩"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].price").value(9900));
        //option 4
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[3].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[3].optionName").value("뽑아쓰는 키친타올 130매 12팩"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[3].price").value(16900));
        //option 5
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[4].id").value(5));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[4].optionName").value("2겹 식빵수세미 6매"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[4].price").value(8900));

        //error에 대한 테스트
//        result2.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
//        result2.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("해당 상품을 찾을 수 없습니다:1000"));
//        result2.andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
    }
    @Test
    public void product_findAll_test() throws Exception{
        //given
        //request 데이터가 없기 때문에 설정이 없음
    // 1. 더미데이터 가져와서 페이징하기
        int page = 0;
        List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        List<ProductResponse.FindAllDTO> responseDTO = productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
        // stub - findById서비스가 실행된다면 주문내역을 찾는 쿼리를 실행하고 결과값을 반환할 것입니다.
        Mockito.when(productService.findAll(page)).thenReturn(responseDTO);

        //when
        // /products 의 url로 데이터를 요청함.
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        //products의 모든 데이터를 get요청으로 호출하는 함수에 대해 1번 ~ 3번 상품까지의 정보와 일치하는지 테스트코드이다.
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].image").value("/images/2.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[1].price").value(2000));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].description").value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].image").value("/images/3.jpg"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].price").value(30000));
    }
}
