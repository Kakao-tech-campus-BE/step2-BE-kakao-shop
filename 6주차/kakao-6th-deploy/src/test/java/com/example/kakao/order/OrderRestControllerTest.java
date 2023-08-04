package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    private ObjectMapper om;

    @WithUserDetails(value = "ssar@nate.com")
    @Test
    public void saveOrder_test() throws Exception{
        // given teardown

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );


        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody);

        //INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('1', '1', '5', '50000', '1');
        //INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('2', '2', '1', '10900', '1');
        //INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('3', '16', '5', '250000', '1');
        //verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssar@nate.com")
    @Test
    public void findOrderById_test() throws Exception{
        // given teardown
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
        );


        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody);

        //INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('1', '1', '5', '50000', '1');
        //INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('2', '2', '1', '10900', '1');
        //INSERT INTO item_tb (`id`,`option_id`, `quantity`, `price`, `order_id`) VALUES ('3', '16', '5', '250000', '1');
        //verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
    @WithUserDetails(value = "ssar@nate.com")
    @Test
    public void saveOrderCartEmpty_test() throws Exception {
        // given
        //빈 장바구니를 주기위해 먼저 장바구니를 비워준다.
        mvc.perform(
                post("/carts/delete")
        );

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);


        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("장바구니가 비어있습니다"));




    }


}
