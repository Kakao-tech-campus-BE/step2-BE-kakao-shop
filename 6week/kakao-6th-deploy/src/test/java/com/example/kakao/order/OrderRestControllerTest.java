package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void saveOrderList_test() throws Exception {
        // given -> teardown.sql 이용

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findOrderById_test() throws Exception {
        // given teardown

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/1")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(50000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    // 조회를 했는데 결제 내역이 없을 경우, item_tb때문에 참조무결성 제약 조건을 비활성화 해놓음.
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findOrderById_failure1_test() throws Exception {
        // given teardown
    	jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
    	jdbcTemplate.update("DELETE FROM order_tb WHERE user_id = 1");

    	
        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/1")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("주문이 존재하지 않습니다 :1"));
        resultActions.andExpect(jsonPath("$.error.status").value(404));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
}