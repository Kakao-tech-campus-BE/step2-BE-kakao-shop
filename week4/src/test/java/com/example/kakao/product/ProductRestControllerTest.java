package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionService;
import com.example.kakao.user.User;
import com.example.kakao.user.UserRequest;
import com.example.kakao.user.UserRestController;
import com.example.kakao.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// GlobalExceptionHandler 와 UserRestController를 SpringContext에 등록합니다.

@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    // 객체의 모든 메서드는 추상메서드로 구현됩니다. (가짜로 만들면)
    // 해당 객체는 SpringContext에 등록됩니다.
    @MockBean //가짜로 띄움
    private ProductService productService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc; //요청 보낼때 사용

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om; //직렬화

    Product product1, product2;
    Option option1, option2;
    @BeforeEach()
    void setup(){
        product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        product2 = new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000);
        option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        option2 = new Option(2, product1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
    }

    @Test
    @DisplayName("전체 상품 조회 테스트")
    public void findAll_test() throws Exception{
        //given
        List<Product> productList = Arrays.asList(
                product1, product2,
                new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000),
                new Product(4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000),
                new Product(5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 5000),
                new Product(6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900),
                new Product(7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800),
                new Product(8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900),
                new Product(9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000)
        );
        List<ProductResponse.FindAllDTO> list = new ArrayList<>();
        for (Product product : productList) {
            ProductResponse.FindAllDTO dto = new ProductResponse.FindAllDTO(product);
            list.add(dto);
        }
        given(productService.findAll(0)).willReturn(list);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products"))
                .andDo(MockMvcResultHandlers.print());; //요청 uri

//        String responseBody = result.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : "+responseBody);

        //then
        System.out.println();
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response[0].id").value(product1.getId()));
        result.andExpect(jsonPath("$.response[0].productName").value(product1.getProductName()));
        result.andExpect(jsonPath("$.response[0].description").value(""));
        result.andExpect(jsonPath("$.response[0].image").value(product1.getImage()));
        result.andExpect(jsonPath("$.response[0].price").value(product1.getPrice()));
        result.andExpect(jsonPath("$.response[1].id").value(product2.getId()));
        result.andExpect(jsonPath("$.response[1].productName").value(product2.getProductName()));
        result.andExpect(jsonPath("$.response[1].description").value(""));
        result.andExpect(jsonPath("$.response[1].image").value(product2.getImage()));
        result.andExpect(jsonPath("$.response[1].price").value(product2.getPrice()));
    }

    @Test
    @DisplayName("개별 상품 상세 조회 테스트")
    public void findById_test() throws Exception{
        //given
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        List<Option> optionList = Arrays.asList(
                option1, option2,
                new Option(3, product1, "고무장갑 베이지 S(소형) 6팩", 9900),
                new Option(4, product1, "뽑아쓰는 키친타올 130매 12팩", 16900),
                new Option(5, product1, "2겹 식빵수세미 6매", 8900));
        given(productService.findById(1)).willReturn(
                (new ProductResponse.FindByIdDTO(product1, optionList)));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1"))
                .andDo(MockMvcResultHandlers.print());;

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.productName").value(product1.getProductName()));
        result.andExpect(jsonPath("$.response.description").value(""));
        result.andExpect(jsonPath("$.response.image").value(product1.getImage()));
        result.andExpect(jsonPath("$.response.price").value(product1.getPrice()));
        result.andExpect(jsonPath("$.response.options[0].id").value(1));
        result.andExpect(jsonPath("$.response.options[0].optionName").value(option1.getOptionName()));
        result.andExpect(jsonPath("$.response.options[0].price").value(option1.getPrice()));
        result.andExpect(jsonPath("$.response.options[1].id").value(option2.getId()));
        result.andExpect(jsonPath("$.response.options[1].optionName").value(option2.getOptionName()));
        result.andExpect(jsonPath("$.response.options[1].price").value(option2.getPrice()));
    }

}
