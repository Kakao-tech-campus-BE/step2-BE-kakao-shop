package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.CartJPARepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {


    @Autowired
    private CartJPARepository cartJPARepository;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_save_test() throws Exception{
        // given -> cart db에서 추출하는 service가 존재한다.

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(2));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(50000));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
        // restdoc api문서 작성에 활용
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_save_fail_test() throws Exception{
        // given -> 장바구니가 비어있음을 가정하기 때문에 delete all한 후에 테스트를 시작한다.
        cartJPARepository.deleteAll();
        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then -> service에 예외처리를 했던 부분들을 테스트한다.
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("장바구니에 상품을 추가해주십시오"));
        // restdoc api문서 작성에 활용
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_findById_test() throws Exception{
        // given -> 주문내역확인을 테스트한다.
        int id = 1;
        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+ id)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(310900));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
        // restdoc api문서 작성에 활용
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_findById_fail_test() throws Exception{
        // given -> 존재하지 않은 주문내역을 요청했을 경우를 테스트한다.
        int id = 100;
        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/" + id)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then -> service에 예외처리를 했던 부분들을 테스트한다.
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("false"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("유저의 주문내역이 존재하지 않습니다."));
        // restdoc api문서 작성에 활용
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}