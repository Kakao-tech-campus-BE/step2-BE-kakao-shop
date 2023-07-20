package com.example.kakao.order;


import java.util.List;

import org.junit.jupiter.api.Test;
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

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;

@Import({
    FakeStore.class,
    GlobalExceptionHandler.class,
    SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {
	
	@Autowired
    private MockMvc mvc;

	@Autowired
    private FakeStore fakeStore;
	
    @MockBean
    private OrderService orderService;
	
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;
    
	@Test
	@WithMockUser(username = "testUser", roles = "USER")
    public void orders_save_test() throws Exception{
		
		// given
		Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
		
        // stub
        Mockito.when(orderService.saveOrder()).thenReturn(responseDTO);
        
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        
        // then
        result.andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
	}
	
	@Test
	@WithMockUser(username = "testUser", roles = "USER")
    public void orders_save_fail_test() throws Exception{
		
		// given
		
		
        // stub
        Mockito.when(orderService.saveOrder()).thenThrow(new Exception500("결제에 실패했습니다."));
        
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        
        // then
        result.andExpect(MockMvcResultMatchers.status().isInternalServerError())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("결제에 실패했습니다."))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(500));
	        
	}
	
	
	@Test
	@WithMockUser(username = "testUser", roles = "USER")
    public void orders_by_id_test() throws Exception{
		// given
		int id = 1;
		Order order = fakeStore.getOrderList().get(id-1);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        
        // stub
        Mockito.when(orderService.getOrder(id)).thenReturn(responseDTO);
        
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
		
	}
	
	@Test
	@WithMockUser(username = "testUser", roles = "USER")
    public void orders_by_id_fail_test() throws Exception{
		
		// given
		int id = 10000;
		
        // stub
        Mockito.when(orderService.getOrder(id)).thenThrow(new Exception404("주문 내역을 찾을 수 없습니다."));
        
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        
        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("주문 내역을 찾을 수 없습니다."))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.error.status").value(404));
	        
	}

}
