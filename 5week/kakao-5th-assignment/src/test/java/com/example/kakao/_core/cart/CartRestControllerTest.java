package com.example.kakao._core.cart;

import com.example.kakao.cart.CartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com") // ssarmango@nate.com으로 DB에 있는지 조회 -> 있으면 통과
    @Test
    public void addCartList_test() throws Exception {

        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(3);
        item.setQuantity(5);
        item.setPrice(49500); // 이 코드 추가해줌
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // when
        //mvc.perform(post("/carts/add").content("optionId=3&quantity=5").contentType(MediaType.APPLICATION_FORM_URLENCODED));
        //json data를 보내는 것을 알려줌
        ResultActions resultActions = mvc.perform(post("/carts/add").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(jsonPath("$.success").value("true"));

    }



}
