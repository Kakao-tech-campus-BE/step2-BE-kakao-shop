package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
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
@ActiveProfiles("test") // 이렇게 해야 localServerStart 실행안됨 local 프로파일에서만 실행되니까
@Sql(value = "classpath:db/teardown.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//db패키지안에 teardown.sql사용하겠다는 의미
@AutoConfigureMockMvc
//MockMvc 빈으로 spring context에 등록
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //통합테스트를하겠다는 의미
//(SF -F - DS - C -S -R -DB 싹다 메모리올려서 테스트하겠다는 의미)
public class OrderIntergrationTest extends MyRestDoc{

    @Autowired
    ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    //UserDetailService 의 loadByUsername 실행 -> loadByUsername의 로직으로 인해
    // ssarmango@nate.com 이 db에잇으면 => 로그인해서 권환있다고 생각됨
    @Test
    public void findById_test() throws Exception {
        // given teardown
        int id=1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(3));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception {
        // given teardown
        int id=1;

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(6));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
