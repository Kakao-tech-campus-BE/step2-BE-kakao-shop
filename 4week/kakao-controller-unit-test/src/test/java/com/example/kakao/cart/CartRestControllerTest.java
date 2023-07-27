package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.user.UserRequest;
import com.example.kakao.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

@Import({
        FakeStore.class,
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {



    //CartService 나 Error로그남기는 repo는 테스트해보고싶은게 아니므로 가짜로 준다.
    @MockBean
    private CartService cartService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc;

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om;


    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    //가짜 UserDetail하나 만들어서 세션에 꼽아주는거임 => 유저가 인증되지않았따는 401을 통과시키기위해서
    @Test
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs =new ArrayList<>();
        CartRequest.SaveDTO saveDTO=new CartRequest.SaveDTO();
        saveDTO.setOptionId(1);
        saveDTO.setQuantity(33);
        requestDTOs.add(saveDTO);
        String requestBody = om.writeValueAsString(requestDTOs);

        //stub
        Mockito.doNothing().when(cartService).add(Mockito.any());
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        //사실 추가한게 잘들어갔는지도 확인해야 하는데, 그러진 못했습니다...
        result.andExpect(MockMvcResultMatchers.jsonPath("success").value("true"));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void find_all_test() throws Exception {
        // given

        //stub
        FakeStore fakeStore=new FakeStore();
        List<Cart> cartList = fakeStore.getCartList();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        doAnswer(invocation -> responseDTO).when(cartService).findAll();

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        //요청이 성공했는지, 원하는대로 잘들어왔는 key값과 총 가격으로확인
        result.andExpect(MockMvcResultMatchers.jsonPath("success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.products[0].carts[0].option.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.products[0].carts[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.products[0].carts[1].option.id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.totalPrice").value(104500));

    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    //가짜 UserDetail하나 만들어서 세션에 꼽아주는거임 => 유저가 인증되지않았따는 401을 통과시키기위해서
    @Test
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs =new ArrayList<>();
        CartRequest.UpdateDTO updateDTO=new CartRequest.UpdateDTO();
        updateDTO.setCartId(1);
        updateDTO.setQuantity(33);
        requestDTOs.add(updateDTO);
        String requestBody = om.writeValueAsString(requestDTOs);

        //stub
        FakeStore fakeStore=new FakeStore();
        for (CartRequest.UpdateDTO uptDTO : requestDTOs) {
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == uptDTO.getCartId()){
                    cart.update(uptDTO.getQuantity(), cart.getPrice() * uptDTO.getQuantity());
                }
            }
        }
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
        Mockito.when(cartService.update(any())).thenReturn(responseDTO);
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        //성공인지 확인하고, 바꾼 cart의 id가 맞는지, 갯수와 가격이 의도한 대로 바꼈는지 확인
        result.andExpect(MockMvcResultMatchers.jsonPath("success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.carts[0].quantity").value(33));
        result.andExpect(MockMvcResultMatchers.jsonPath("response.carts[0].price").value(330000));
    }



    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    //가짜 UserDetail하나 만들어서 세션에 꼽아주는거임 => 유저가 인증되지않았따는 401을 통과시키기위해서
    @Test
    public void clear_test() throws Exception {
        // given
        //stub
        Mockito.doNothing().when(cartService).clear();
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        //성공인지만 확인
        result.andExpect(MockMvcResultMatchers.jsonPath("success").value("true"));
   
    }
}


