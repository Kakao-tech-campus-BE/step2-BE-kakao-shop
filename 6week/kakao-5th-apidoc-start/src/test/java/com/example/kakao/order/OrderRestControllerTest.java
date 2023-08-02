package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.CartRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception {
        // given -> product [1, 2, 3]이 teardown.sql을 통해 들어가 있음.
        List<OrderRequest.SaveDTO> requestDTOS = new ArrayList<>();
        OrderRequest.SaveDTO order1 = new OrderRequest.SaveDTO();
        order1.setId(2);
        
        // items 저장
        List<OrderRequest.ItemDTO> itemsDTOS = new ArrayList<>();

        OrderRequest.ItemDTO itemDTO1 = new OrderRequest.ItemDTO();
        itemDTO1.setId(1);
        itemDTO1.setPrice(50000);
        itemDTO1.setQuantity(5);
        itemDTO1.setOptionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종");

        OrderRequest.ItemDTO itemDTO2 = new OrderRequest.ItemDTO();
        itemDTO2.setId(2);
        itemDTO2.setPrice(10900);
        itemDTO2.setQuantity(1);
        itemDTO2.setOptionName("02. 슬라이딩 지퍼백 플라워에디션 5종");

        itemsDTOS.add(itemDTO1);
        itemsDTOS.add(itemDTO2);

        // products 저장
        List<OrderRequest.ProductDTO> productsDTOS = new ArrayList<>();
        OrderRequest.ProductDTO productDTO1 = new OrderRequest.ProductDTO();
        productDTO1.setProductName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        productDTO1.setItems(itemsDTOS);

        productsDTOS.add(productDTO1);

        order1.setProducts(productsDTOS);
        order1.setTotalPrice(20000);

        requestDTOS.add(order1);

        String requestBody = om.writeValueAsString(requestDTOS);

        // when
        ResultActions resultActions = mvc.perform(
                post("/order")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void failed_save_test() throws Exception {
        // given -> product [1, 2, 3]이 teardown.sql을 통해 들어가 있음.
        List<OrderRequest.SaveDTO> requestDTOS = new ArrayList<>();
        OrderRequest.SaveDTO order1 = new OrderRequest.SaveDTO();
        order1.setId(2);

        // items 저장
        List<OrderRequest.ItemDTO> itemsDTOS = new ArrayList<>();

        OrderRequest.ItemDTO itemDTO1 = new OrderRequest.ItemDTO();
        itemDTO1.setId(1);
        itemDTO1.setPrice(50000);
        itemDTO1.setQuantity(5);
        itemDTO1.setOptionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종");

        OrderRequest.ItemDTO itemDTO2 = new OrderRequest.ItemDTO();
        itemDTO2.setId(2);
        itemDTO2.setPrice(10900);
        itemDTO2.setQuantity(1);
        itemDTO2.setOptionName("02. 슬라이딩 지퍼백 플라워에디션 5종");

        itemsDTOS.add(itemDTO1);
        itemsDTOS.add(itemDTO2);

        // products 저장
        List<OrderRequest.ProductDTO> productsDTOS = new ArrayList<>();
        OrderRequest.ProductDTO productDTO1 = new OrderRequest.ProductDTO();
        productDTO1.setProductName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        productDTO1.setItems(itemsDTOS);

        productsDTOS.add(productDTO1);

        order1.setProducts(productsDTOS);
        order1.setTotalPrice(20000);

        String requestBody = om.writeValueAsString(requestDTOS);

        // when
        ResultActions resultActions = mvc.perform(
                post("/order")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test() throws Exception {
        // given wrong teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/order/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void failed_findById_test() throws Exception {
        // given wrong order id
        int id = 200;

        // when
        ResultActions resultActions = mvc.perform(
                get("/order/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
