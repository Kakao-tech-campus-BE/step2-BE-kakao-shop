package com.example.kakao.order;

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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("주문_통합_테스트")
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    private final String snippets = "{class-name}/{method-name}";

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    @Autowired
    public OrderRestControllerTest(ObjectMapper om, UserJPARepository userJPARepository) {
        this.om = om;
        this.userJPARepository = userJPARepository;
    }

    /**
     * teardown 데이터는 아래와 같다.
     * Order :
     *      id       : 1
     *      userId   : 1
     * Item  :
     *      id       : [1,2,3]
     *      optionId : [1,2,16]
     */

    @DisplayName("주문_저장_테스트")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_save_test() throws Exception {
        // given
        String token = getJwtToken("ssarmango@nate.com");

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                        .header(JWTProvider.HEADER, token));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response.id").value(3),
                jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                jsonPath("$.response.products[0].items[0].id").value(4),
                jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                jsonPath("$.response.products[0].items[0].quantity").value(5),
                jsonPath("$.response.products[0].items[0].price").value(50000),
                jsonPath("$.response.products[0].items[1].id").value(5),
                jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
                jsonPath("$.response.products[0].items[1].quantity").value(1),
                jsonPath("$.response.products[0].items[1].price").value(10900),
                jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"),
                jsonPath("$.response.products[1].items[0].id").value(6),
                jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"),
                jsonPath("$.response.products[1].items[0].quantity").value(5),
                jsonPath("$.response.products[1].items[0].price").value(250000),
                jsonPath("$.response.totalPrice").value(310900),
                jsonPath("$.error").value(nullValue())
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

    @DisplayName("주문_저장_테스트_실패_빈_장바구니")
//    @WithUserDetails(value = "rjsdnxogh@naver.com")
    @Test
    public void order_save_test_fail_empty_cart() throws Exception {
        // given
        String token = getJwtToken("rjsdnxogh@naver.com");

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
                        .header(JWTProvider.HEADER, token));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("회원님의 장바구니가 비어있습니다."),
                jsonPath("$.error.status").value(404)
        );
        resultActions.andDo(print()).andDo(document);
    }

    /**
     * 장바구니 조회
     */

    @DisplayName("장바구니_조회_테스트")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_findById_test() throws Exception{
        // given
        String token = getJwtToken("ssarmango@nate.com");
        int findId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/{order_id}", findId)
                        .header(JWTProvider.HEADER, token));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("true"),
                jsonPath("$.response.id").value(1),
                jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"),
                jsonPath("$.response.products[0].items[0].id").value(1),
                jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"),
                jsonPath("$.response.products[0].items[0].quantity").value(5),
                jsonPath("$.response.products[0].items[0].price").value(50000),
                jsonPath("$.response.products[0].items[1].id").value(2),
                jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"),
                jsonPath("$.response.products[0].items[1].quantity").value(1),
                jsonPath("$.response.products[0].items[1].price").value(10900),
                jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"),
                jsonPath("$.response.products[1].items[0].id").value(3),
                jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"),
                jsonPath("$.response.products[1].items[0].quantity").value(5),
                jsonPath("$.response.products[1].items[0].price").value(250000),
                jsonPath("$.response.totalPrice").value(310900),
                jsonPath("$.error").value(nullValue())
        );
        resultActions.andDo(print()).andDo(document(
                snippets,
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(JWTProvider.HEADER).description("JWT token")
                ),
                pathParameters(
                        parameterWithName("order_id").description("주문ID")
                )
        ));
    }

    @DisplayName("장바구니_조회_테스트_실패_없는_주문")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_findById_test_fail_order_not_found() throws Exception{
        // given
        String token = getJwtToken("ssarmango@nate.com");
        int findId = 3;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/{order_id}", findId)
                        .header(JWTProvider.HEADER, token));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("해당하는 주문을 찾을 수 없습니다. : " + findId),
                jsonPath("$.error.status").value(404)
        );
        resultActions.andDo(print()).andDo(document);
    }

    @DisplayName("장바구니_조회_테스트_권한없는_유저의_주문_접근")
//    @WithUserDetails(value = "rjsdnxogh@naver.com")
    @Test
    public void order_findById_test_fail_forbidden_user_access() throws Exception{
        // given
        String token = getJwtToken("rjsdnxogh@naver.com");
        int findId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/{order_id}", findId)
                        .header(JWTProvider.HEADER, token));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("허용되지 않은 접근입니다."),
                jsonPath("$.error.status").value(403)
        );
    }

    @DisplayName("장바구니_조회_테스트_실패_없는_상품")
//    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void order_findById_test_fail_item_not_found() throws Exception{
        // given
        String token = getJwtToken("ssarmango@nate.com");
        int findId = 2;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/{order_id}", findId)
                        .header(JWTProvider.HEADER, token));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpectAll(
                jsonPath("$.success").value("false"),
                jsonPath("$.response").value(nullValue()),
                jsonPath("$.error.message").value("주문상품이 존재하지 않습니다."),
                jsonPath("$.error.status").value(404)
        );
        resultActions.andDo(print()).andDo(document);
    }

    private String getJwtToken(String email) {
        User user = userJPARepository.findByEmail(email).orElseThrow(() -> new UserException.UserNotFoundByEmailException(email));
        return JWTProvider.create(user);
    }
}
