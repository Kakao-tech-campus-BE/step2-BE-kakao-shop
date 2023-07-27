package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({
        SecurityConfig.class,
        FakeStore.class,
        GlobalExceptionHandler.class,
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private ProductService productService;

    @Autowired
    private FakeStore fakeStore;

    @DisplayName("전체 상품 목록 조회 테스트")
    @Test
    public void product_findAll_test() throws Exception {
        // given
        int page = 0;
        List<ProductResponse.FindAllDTO> productList = fakeStore.getProductList().stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

        // stub
        Mockito.when(productService.findAll(ArgumentMatchers.any())).thenReturn(productList);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("전체 상품 목록 조회 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));

    }
    @DisplayName("개별 상품 목록 조회 테스트")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void product_findById_test() throws Exception {
        // given
        int productId = 3;

        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == productId).findFirst().orElseThrow(
                () -> new RuntimeException("해당 상품이 존재하지 않습니다.")
        );
        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);

        // stub
        Mockito.when(productService.findById(ArgumentMatchers.any())).thenReturn(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("개별 상품 목록 조회 테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(9));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
    }
}
