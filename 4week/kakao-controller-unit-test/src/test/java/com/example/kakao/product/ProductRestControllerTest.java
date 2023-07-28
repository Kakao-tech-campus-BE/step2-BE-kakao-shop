package com.example.kakao.product;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.kakao._core.security.SecurityConfig;

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

@Import({
	FakeStore.class,
	SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
	
	@MockBean
	private ProductService productService;

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper om;
	
	@Autowired
	private FakeStore fakeStore;
	
	
	// 전체 상품 조회 성공 테스트
	@Test
    public void findAll_test() throws Exception{
		
		// given -> 응답 DTO는 fakeStore에서 가져옴 -> 첫번째 페이지 테스트
        int page = 0;
        List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        List<ProductResponse.FindAllDTO> productDTOList = productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
	    
        String fakeResponseBody = om.writeValueAsString(productDTOList);
        System.out.println("테스트 : "+fakeResponseBody);
        
        // stub (가정법)
        Mockito.when(productService.findAllProducts(page)).thenReturn(productDTOList);

        // when
		ResultActions result = mvc.perform(
				MockMvcRequestBuilders
					.get("/products")
					.contentType(MediaType.APPLICATION_JSON)
		);
		
		String responseBody = result.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : "+responseBody);
		
		// then
		result.andExpect(status().isOk());  // HTTP 상태 코드가 200인지 확인
		
	}
	
	// 전체 상품 조회 실패 테스트
	@Test
    public void findAll_fail_test() throws Exception{
		
		// given -> 응답 DTO는 fakeStore에서 가져옴 -> 첫번째 페이지 테스트
        long page = 1000000000000L;
        
        // stub (가정법)
    	Mockito.when(productService.findAllProducts((int) page)).thenThrow(new IndexOutOfBoundsException());

        // when
		ResultActions result = mvc.perform(
				MockMvcRequestBuilders
					.get("/products")
					.param("page", String.valueOf(page))
					.contentType(MediaType.APPLICATION_JSON)
		);
		
		String responseBody = result.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : "+responseBody);
		
		// then
		// GlobalExceptionHandler2가 작동했는지 테스트
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.success").value(false)) 
			.andExpect(jsonPath("$.response").doesNotExist())  
			.andExpect(jsonPath("$.error.message").value("유효하지않는 요청입니다!")) 
			.andExpect(jsonPath("$.error.status").value(400));
	}
	
	@Test
    public void find_by_id_test() throws Exception{
		
		// given
		int id = 1;
		Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		List<Option> options = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
		ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, options);
		
        String fakeResponseBody = om.writeValueAsString(responseDTO);
        System.out.println("테스트 : "+fakeResponseBody);
        
        // stub (가정법)
        Mockito.when(productService.findProductById(id)).thenReturn(product);
        Mockito.when(productService.findOptionsByProduct(product)).thenReturn(options);
        Mockito.when(productService.convertFindByIdDTO(product, options)).thenReturn(responseDTO);

        // when
		ResultActions result = mvc.perform(
				MockMvcRequestBuilders
					.get("/products/" + id)
					.contentType(MediaType.APPLICATION_JSON)
		);
		
		String responseBody = result.andReturn().getResponse().getContentAsString();
		System.out.println("테스트 : "+responseBody);
		
		// then
		result.andExpect(status().isOk());  // HTTP 상태 코드가 200인지 확인
	}
	
    @Test
    public void find_by_id_fail_test() throws Exception{
        
        // given
        int id = Integer.MAX_VALUE + 1;  // int 범위를 넘는 값
        String expectedErrorMessage = "해당 상품을 찾을 수 없습니다:" + id;
        
        // stub (가정법)
        Mockito.when(productService.findProductById(id)).thenReturn(null);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                    .get("/products/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        
        // then
        result
            .andExpect(status().isNotFound())  // HTTP 상태 코드가 404인지 확인
            .andExpect(jsonPath("$.success").value(false))  // success 필드가 false인지 확인
            .andExpect(jsonPath("$.response").doesNotExist())  // response 필드가 없는지 확인
            .andExpect(jsonPath("$.error.message").value(expectedErrorMessage))  // error.message 필드가 예상한 에러 메시지인지 확인
            .andExpect(jsonPath("$.error.status").value(404));  // error.status 필드가 404인지 확인
    }

}
