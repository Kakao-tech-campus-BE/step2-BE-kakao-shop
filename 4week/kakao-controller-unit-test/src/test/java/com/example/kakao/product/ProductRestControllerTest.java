package com.example.kakao.product;
import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.ArgumentMatchers.anyInt;

@Import({
        SecurityConfig.class,
        FakeStore.class, //테스트에 실제 FakeStore을 써야함
        GlobalExceptionHandler.class //테스트하고싶은 요소중에 하나임
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    //서비스는 테스트할것이 아니므로 mock으로준다
    @MockBean
    private ProductService productService;
    //에러로그도 테스트할것이 아니므로 mock으로준다.
    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    //@WithMockUser(username = "ssar@nate.com", roles = "USER")
    //위에 어노테이션 쓰는 이유는 가짜 UserDetail하나 만들어서 세션에 꼽아주는거임
    // => 유저가 인증되지않았따는 401을 통과시키기위해서
    //하지만 prodcut는 권한 없어도 접근할수잇기떄문에 @WithMockUser필요없음
    @Test
    //확인해야 할것 1. 페이징 처리 잘되었는지(page, limit)
    //            2. 요청 성공인지(success)
    public void find_all_test() throws Exception {
        // given
        int page=0;

        //stub
        FakeStore fakeStore=new FakeStore();
        List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        List<ProductResponse.FindAllDTO> responseDTOs =
                productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

        Mockito.when(productService.findAll(anyInt())).thenReturn(responseDTOs);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .param("page", Integer.toString(page) )
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();

        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        //0번쨰 페이지를 가져오고 limit가 9이므로 함으로 첫번쨰, 마지막 id가 각각 1,9인지확인
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response[8].id").value(9));
        //페이징한 요소들의 limit, 즉 길이가 9가 맞는지 확인
        Assertions.assertThat(new JSONObject(responseBody).getJSONArray("response").length()).isEqualTo(9);

    }


    @Test
    //확인해야 할것 1. 해당 id의 상품정보 잘 가져오는지(id)
    //            2. 해당 id의 상품정보와 관련있는 옵션들 잘 가져오는지(예상하는 옵션의 총 길이와 각 id)
    //            3. 요청 성공인지(success)
    public void find_by_id_test() throws Exception {
        // given
        int id=1;

        //stub
        FakeStore fakeStore=new FakeStore();
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
        Mockito.when(productService.findById(anyInt())).thenReturn(responseDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        //가져온 상품의 id가 1인지 확인
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        //상품과 관련된 옵션들이 잘 왔는지 확인(FakeStore에 미리 저장해놓은 예상 관련옵션 결과들의 길이와 각 id를 검증)
        Assertions.assertThat(new JSONObject(responseBody).getJSONObject("response").getJSONArray("options")
                .length()).isEqualTo(5);
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[3].id").value(4));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[4].id").value(5));


    }



}


