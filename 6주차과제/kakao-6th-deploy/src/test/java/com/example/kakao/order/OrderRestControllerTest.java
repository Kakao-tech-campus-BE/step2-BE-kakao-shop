package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
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
    @Autowired
    private MockMvc mvc;

    // (기능9) 결재하기 - (주문 인서트) POST
    @Test
    @WithUserDetails(value = "ssarmango@nate.com")
    public void save_test() throws Exception{
        // given -> WithuserDetails

        // when
        ResultActions resultActions = mvc.perform(
            post("/carts/orders/save")
                    .contentType(MediaType.APPLICATION_JSON)
        );


        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }

    // (기능10) 주문 결과 확인 GET
    @Test
    @WithUserDetails(value = "ssarmango@nate.com")
    public void findById_test() throws Exception{
        // given
        int orderId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );


        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    // (기능10) 주문 결과 확인 GET
    @Test
    @WithUserDetails(value = "ssarmango@nate.com")
    public void findById_error_test() throws Exception{
        // given
        int orderId = 2;

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );


        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당된 아이템을 찾을 수 없습니다."));
        resultActions.andExpect(jsonPath("$.error.status").value("404"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}
