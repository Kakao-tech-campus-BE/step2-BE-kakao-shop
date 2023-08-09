package com.example.kakao.cart;

import com.example.kakao.MyRestDoc;
import com.example.kakao._core.errors.exception.user.UserException;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("장바구니_통합_테스트")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest extends MyRestDoc {

    private final String snippets = "{class-name}/{method-name}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserJPARepository userJPARepository;


    /**
     * 장바구니 추가
     */

    @DisplayName("장바구니_추가_테스트")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(3);
        item.setQuantity(5);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error").value(nullValue())
        );
        resultActions.andDo(print()).andDo(document(
                snippets,
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(JWTProvider.HEADER).description("JWT token")
                ),
                requestFields(
                        List.of(
                                fieldWithPath("[].optionId").type(JsonFieldType.NUMBER).description("0보다 큰 수를 입력해야 합니다."),
                                fieldWithPath("[].quantity").type(JsonFieldType.NUMBER).description("0보다 큰 수를 입력해야 합니다.")
                       )
                )
        ));
    }

    @DisplayName("장바구니_추가_테스트_실패_중복된_입력")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_test_fail_duplicated_input() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item1.setOptionId(1);item1.setQuantity(5);
        item2.setOptionId(1);item2.setQuantity(4);
        requestDTOs.add(item1);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("중복된 옵션이 입력되었습니다."),
                jsonPath("$.error.status").value(400)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_추가_테스트_실패_존재하지_않는_옵션")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_test_fail_option_not_found() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO item3 = new CartRequest.SaveDTO();
        item1.setOptionId(3); item1.setQuantity(4);
        item2.setOptionId(100);item2.setQuantity(5);
        item3.setOptionId(102);item3.setQuantity(4);
        requestDTOs.add(item1);
        requestDTOs.add(item2);
        requestDTOs.add(item3);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("존재하지 않는 옵션입니다. : " + List.of(100, 102)),
                jsonPath("$.error.status").value(404)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_추가_테스트_실패_양수가_아닌_옵션ID_입력")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_test_fail_optionId_is_not_positive() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(0); item1.setQuantity(4);
        requestDTOs.add(item1);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("0보다 큰 수를 입력해야 합니다.:validList[0].optionId"),
                jsonPath("$.error.status").value(400)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_추가_테스트_실패_양수가_아닌_수량_입력")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void addCartList_test_fail_quantity_is_not_positive() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(2); item1.setQuantity(0);
        requestDTOs.add(item1);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("0보다 큰 수를 입력해야 합니다.:validList[0].quantity"),
                jsonPath("$.error.status").value(400)
        );
        resultActions.andDo(print()).andDo(document);
    }

    /**
     * 장바구니 전체 조회
     */

    @DisplayName("장바구니_전체_조회_테스트")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findAll_test() throws Exception {
        // given teardown
        String token = getJwtToken("ssarmango@nate.com");

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
                        .header(JWTProvider.HEADER, token)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response.products[0].id").value(1),
                jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                jsonPath("$.response.products[0].carts[0].id").value(1),
                jsonPath("$.response.products[0].carts[0].option.id").value(1),
                jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                jsonPath("$.response.products[0].carts[0].option.price").value(10000),
                jsonPath("$.response.products[0].carts[0].quantity").value(5),
                jsonPath("$.response.products[0].carts[0].price").value(50000),
                jsonPath("$.response.totalPrice").value(310900)
        );
        resultActions.andDo(print()).andDo(document(
                snippets,
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(JWTProvider.HEADER).description("JWT token")
                )
        ));
    }

//    @DisplayName("장바구니_전체_조회_테스트_실패_빈_장바구니")
//    @WithUserDetails(value = "rjsdnxogh@naver.com")
//    @Test
//    public void findAll_test_fail_empty_cart() throws Exception {
//        // given teardown
//        String token = getJwtToken("rjsdnxogh@naver.com");
//
//        // when
//        ResultActions resultActions = mvc.perform(
//                get("/carts")
//                        .header(JWTProvider.HEADER, token)
//        );
//
//        // eye
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : " + responseBody);
//
//        // verify
//        resultActions.andExpectAll(
//                jsonPath("$.success").value("false"),
//                jsonPath("$.response").value(nullValue()),
//                jsonPath("$.error.message").value("회원님의 장바구니가 비어있습니다."),
//                jsonPath("$.error.status").value(404)
//        );
//        resultActions.andDo(print()).andDo(document);
//    }

    /**
     * 장바구니 수정
     */

    @DisplayName("장바구니_수정_테스트")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response.carts[0].cartId").value("1"),
                jsonPath("$.response.carts[0].optionId").value("1"),
                jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                jsonPath("$.response.carts[0].quantity").value(10),
                jsonPath("$.response.carts[0].price").value(100000)
        );
        resultActions.andDo(print()).andDo(document(
                snippets,
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(JWTProvider.HEADER).description("JWT token")
                ),
                requestFields(
                        List.of(
                                fieldWithPath("[].cartId").type(JsonFieldType.NUMBER).description("0보다 큰 수를 입력해야 합니다."),
                                fieldWithPath("[].quantity").type(JsonFieldType.NUMBER).description("0보다 큰 수를 입력해야 합니다.")
                        )
                )
        ));
    }


    @DisplayName("장바구니_수정_테스트_실패_중복_입력")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_test_fail_duplicated_input() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item1.setCartId(1); item1.setQuantity(10);
        item2.setCartId(1); item2.setQuantity(5);
        requestDTOs.add(item1);
        requestDTOs.add(item2);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("중복된 장바구니가 입력되었습니다."),
                jsonPath("$.error.status").value(400)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_수정_테스트_실패_빈_장바구니")
//    @WithUserDetails(value = "rjsdnxogh@naver.com")
    @Test
    public void update_test_fail_empty_cart() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("rjsdnxogh@naver.com");
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item1.setCartId(4); item1.setQuantity(10);
        item2.setCartId(5); item2.setQuantity(5);
        requestDTOs.add(item1);
        requestDTOs.add(item2);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("회원님의 장바구니가 비어있습니다."),
                jsonPath("$.error.status").value(404)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_수정_테스트_실패_회원에_없는_장바구니_존재")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_test_fail_not_found_carts_in_user() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item1.setCartId(1); item1.setQuantity(10);
        item2.setCartId(5); item2.setQuantity(5);
        requestDTOs.add(item1);
        requestDTOs.add(item2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println(token);
        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message")
                        .value("유저 장바구니에 없는 cartId가 입력되었습니다. : " + List.of(5)),
                jsonPath("$.error.status").value(400)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_수정_테스트_실패_양수가_아닌_장바구니ID_입력")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_test_fail_cartId_is_not_positive() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(0); item1.setQuantity(10);
        requestDTOs.add(item1);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println(token);
        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("0보다 큰 수를 입력해야 합니다.:validList[0].cartId"),
                jsonPath("$.error.status").value(400)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_수정_테스트_실패_양수가_아닌_수량_입력")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void update_test_fail_quantity_is_not_positive() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        String token = getJwtToken("ssarmango@nate.com");
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1); item1.setQuantity(0);
        requestDTOs.add(item1);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println(token);
        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .header(JWTProvider.HEADER, token)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("0보다 큰 수를 입력해야 합니다.:validList[0].quantity"),
                jsonPath("$.error.status").value(400)
        );
        resultActions.andDo(print()).andDo(document);
    }

    private String getJwtToken(String email) {
        User user = userJPARepository.findByEmail(email).orElseThrow(() -> new UserException.UserNotFoundByEmailException(email));
        return JWTProvider.create(user);
    }
}