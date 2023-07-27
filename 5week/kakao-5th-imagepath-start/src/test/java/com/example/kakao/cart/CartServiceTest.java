package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.util.MockJPAResultEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when; //BDDMockito Mockito 뭐가 다름?


@ExtendWith(MockitoExtension.class)
public class CartServiceTest extends MockJPAResultEntity {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartJPARepository cartJPARepository;

    @Mock
    private OptionJPARepository optionJPARepository;

    @DisplayName("장바구니에 이미 상품이 존재하는 경우 수량 추가 테스트")
    @Test
    public void addCartList_whenExists_test() {
        //given
        List<Option> givenOptions = optionDummyList(productDummyList());

        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CartRequest.SaveDTO temp = new CartRequest.SaveDTO();
            temp.setOptionId(i);
            temp.setQuantity(2);
            requestDTOs.add(temp);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());
        List<Option> options = new ArrayList<>();

        //mock
        when(optionJPARepository.findById(anyInt()))
                .thenAnswer(invocation -> {
                    int param = invocation.getArgument(0);
                    if (param >= 1 && param <= 48) return Optional.of(givenOptions.get(param - 1));
                    throw new Exception404("해당 옵션을 찾을 수 없습니다 : " + param);
                });

        //optionId 1은 이미 수량 2개로 저장되어 있음
        when(cartJPARepository.findByOptionIdAndUserId(anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    int param = invocation.getArgument(0);
                    if (param == 1) return Optional.of(newCart(sessionUser, givenOptions.get(param - 1), 2));
                    else return Optional.empty();
                });

        when(cartJPARepository.save(any(Cart.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        //when
        List<CartRequest.SaveDTO> saveInfos = cartService.addCartList(requestDTOs, sessionUser);
        for (CartRequest.SaveDTO saveInfo : saveInfos) {
            System.out.println(saveInfo.getOptionId());
            System.out.println(saveInfo.getQuantity());
        }

        //Then
        assertThat(saveInfos.size()).isEqualTo(3);

        //이미 존재하는 장바구니에 수량 추가
        assertThat(saveInfos.get(0).getOptionId()).isEqualTo(1);
        assertThat(saveInfos.get(0).getQuantity()).isEqualTo(4);

        assertThat(saveInfos.get(1).getOptionId()).isEqualTo(2);
        assertThat(saveInfos.get(1).getQuantity()).isEqualTo(2);

        assertThat(saveInfos.get(2).getOptionId()).isEqualTo(3);
        assertThat(saveInfos.get(2).getQuantity()).isEqualTo(2);
    }

    @DisplayName("장바구니 추가에 동일한 옵션id이 두가지 이상 들어왔을 때 예외 발생 테스트")
    @Test
    public void sameOption_fail_test() {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CartRequest.SaveDTO temp = new CartRequest.SaveDTO();
            temp.setOptionId(1);
            temp.setQuantity(2 + i);
            requestDTOs.add(temp);
        }

        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //when
        try {
            cartService.addCartList(requestDTOs, sessionUser);
        } catch (Exception400 e) {
            //then
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("동일한 옵션을 처리할 수 없습니다");
        }
    }

    @DisplayName("장바구니 업데이트 성공 테스트")
    @Test
    public void cartUpdate_test() {
        //given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            CartRequest.UpdateDTO temp = new CartRequest.UpdateDTO();
            temp.setCartId(i);
            temp.setQuantity(4);
            requestDTOs.add(temp);
        }

        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //mock
        List<Option> givenOptions = optionDummyList(productDummyList());

        List<Cart> userCartList = new ArrayList<>();
        userCartList.add(newCart(1, sessionUser, givenOptions.get(0), 3));
        userCartList.add(newCart(2, sessionUser, givenOptions.get(1), 2));

        when(cartJPARepository.findAllByUserId(anyInt()))
                .thenReturn(userCartList);

        //when
        CartResponse.UpdateDTO result = cartService.update(requestDTOs, sessionUser);

        //then
        assertThat(result.getCarts().size()).isEqualTo(2);

        assertThat(result.getCarts().get(0).getCartId()).isEqualTo(1);
        assertThat(result.getCarts().get(0).getOptionId()).isEqualTo(1);
        assertThat(result.getCarts().get(0).getQuantity()).isEqualTo(4);

        assertThat(result.getCarts().get(1).getCartId()).isEqualTo(2);
        assertThat(result.getCarts().get(1).getOptionId()).isEqualTo(2);
        assertThat(result.getCarts().get(1).getQuantity()).isEqualTo(4);
    }

    @DisplayName("유저 장바구니가 비어있는 경우 예외 발생 테스트")
    @Test
    public void emptyUserCartUpdate_fail_test(){
        //given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CartRequest.UpdateDTO temp = new CartRequest.UpdateDTO();
            temp.setCartId(i);
            temp.setQuantity(2);
            requestDTOs.add(temp);
        }

        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //mock
        //유저 장바구니가 비어있음
        when(cartJPARepository.findAllByUserId(anyInt()))
                .thenReturn(new ArrayList<>());

        //when
        try {
            cartService.update(requestDTOs, sessionUser);
        } catch(Exception400 e) {
            //then
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("장바구니가 비어있어 수정할 수 없습니다.");
        }
    }

    @DisplayName("동일한 장바구니 아이디가 두번 들어오는 경우 예외 발생 테스트")
    @Test
    public void sameCartIdInputsWhenUpdate_fail_test(){
        //given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        //동일한 CartId 입력
        for (int i = 1; i <= 2; i++) {
            CartRequest.UpdateDTO temp = new CartRequest.UpdateDTO();
            temp.setCartId(1);
            temp.setQuantity(2);
            requestDTOs.add(temp);
        }
        CartRequest.UpdateDTO temp = new CartRequest.UpdateDTO();
        temp.setCartId(2);
        temp.setQuantity(2);
        requestDTOs.add(temp);

        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //mock
        List<Option> givenOptions = optionDummyList(productDummyList());

        List<Cart> userCartList = new ArrayList<>();
        userCartList.add(newCart(1, sessionUser, givenOptions.get(0), 3));
        userCartList.add(newCart(2, sessionUser, givenOptions.get(1), 2));

        when(cartJPARepository.findAllByUserId(anyInt()))
                .thenReturn(userCartList);

        //when
        try {
            cartService.update(requestDTOs, sessionUser);
        } catch(Exception400 e) {
            //then
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("잘못된 접근입니다. 동일한 장바구니 아이디는 중복해서 들어올 수 없습니다.");
        }
    }

    @DisplayName("유저 장바구니에 없는 cartId가 들어오면 예외 발생 테스트")
    @Test
    public void notValidCartIdInputsWhenUpdate_fail_test() {
        //given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            CartRequest.UpdateDTO temp = new CartRequest.UpdateDTO();
            temp.setCartId(i);
            temp.setQuantity(2);
            requestDTOs.add(temp);
        }
        //유저가 가지고 있지 않는 CartId
        CartRequest.UpdateDTO temp = new CartRequest.UpdateDTO();
        temp.setCartId(3);
        temp.setQuantity(2);
        requestDTOs.add(temp);

        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //mock
        List<Option> givenOptions = optionDummyList(productDummyList());

        List<Cart> userCartList = new ArrayList<>();
        userCartList.add(newCart(1, sessionUser, givenOptions.get(0), 3));
        userCartList.add(newCart(2, sessionUser, givenOptions.get(1), 2));

        when(cartJPARepository.findAllByUserId(anyInt()))
                .thenReturn(userCartList);

        //when
        try {
            cartService.update(requestDTOs, sessionUser);
        } catch(Exception400 e) {
            //then
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("잘못된 접근입니다. 해당 유저가 가지고 있지 않는 장바구니에 접근할 수 없습니다.");
        }
    }
}
