package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest  {
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductOptionService productOptionService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    private List<Product> mockProducts;
    private List<Option> mockOption;
    @BeforeEach
    public void setUp() {
        FakeStore fakeStore = new FakeStore();
        mockProducts = fakeStore.getProductList();
        mockOption = fakeStore.getOptionList();
    }

    @Test
    public void findAll_test() throws Exception {

        // given
        int page = 0;
        String name = "page";
        String value = "0";


        //stub
        List<Product> mockProductsPaging = mockProducts.stream().skip(page*9).limit(9).collect(Collectors.toList());

        List<ProductResponse.FindAllDTO> mockDtoProducts = mockProductsPaging.stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());

        Mockito.when(productService.findAllPaging(anyInt())).thenReturn(mockProductsPaging);
        Mockito.when(productService.toFindAllDTO(any())).thenReturn(mockDtoProducts);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .queryParam(name,value)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void findById_test() throws Exception{
        //given
        int productId = 1;

        //stub
        Product mockProductFindById = mockProducts.stream()
                        .filter(x -> x.getId() == productId).findFirst().orElse(null);

        List<Option> mockOptionListFindByProductId = mockOption.stream()
                        .filter(x -> x.getProduct().getId() == productId)
                                .collect(Collectors.toList());


        ProductResponse.FindByIdDTO mockFindByIdDTO = new ProductResponse.FindByIdDTO(mockProductFindById, mockOptionListFindByProductId);

        Mockito.when(productOptionService.findById(productId)).thenReturn(mockFindByIdDTO);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

}
