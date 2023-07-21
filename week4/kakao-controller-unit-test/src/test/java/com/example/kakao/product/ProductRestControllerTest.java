package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

//    @Autowired
//    private ObjectMapper om;

    @Autowired
    private FakeStore fakeStore;

    @Test
    @DisplayName("전체 상품 조회 테스트")
    public void findAll_test() throws Exception {
        // given
        int page = 100;

        // 페이지 조회
        List<Product> productListPS = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        // 페이지 유효성 검사
        if(productListPS.isEmpty()) {
            throw new Exception404("페이지를 찾을 수 없습니다:"+page);
        }

        // DTO 변환
        List<ProductResponse.FindAllDTO> responseDTOs = productListPS.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

        // stub
        Mockito.when(productService.findAll(page)).thenReturn(responseDTOs);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products?page="+page)
        );

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("특정 상품 조회 테스트")
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // 상품 조회
        Product productPS = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        // 상품 유효성 검사
        if(productPS == null) {
            throw new Exception404("해당 상품을 찾을 수 없습니다:"+id);
        }
        // 옵션 조회
        List<Option> optionListPS = fakeStore.getOptionList().stream().filter(option -> productPS.getId() == option.getProduct().getId()).collect(Collectors.toList());

        // DTO 변환
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(productPS, optionListPS);

        // stub
        Mockito.when(productService.findById(id)).thenReturn(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/"+id)
        );

        System.out.println("========================================responseBody 시작=========================================");
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println(getPrettyString(responseBody));
        System.out.println("========================================responseBody 종료=========================================");

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
}
