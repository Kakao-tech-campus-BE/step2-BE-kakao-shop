package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.cart.CartException;
import com.example.kakao._core.errors.exception.product.option.OptionException;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@DisplayName("장바구니_서비스_테스트")
@ExtendWith(MockitoExtension.class)
public class CartServiceTest extends DummyEntity {
    @InjectMocks
    private CartService cartService;

    @Mock
    private OptionJPARepository optionJPARepository;

    @Mock
    private CartJPARepository cartJPARepository;

    @DisplayName("장바구니_추가_테스트_수정_없음")
    @Test
    public void cart_add_test_no_update() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO();
        s1.setOptionId(1); s1.setQuantity(5);
        s2.setOptionId(2); s2.setQuantity(3);
        saveDTOList.add(s1); saveDTOList.add(s2);
        List<Option> optionList = createOptionList(saveDTOList);

        // mocking
        given(optionJPARepository.findByIdIn(any())).willReturn(optionList);
        given(cartJPARepository.findByUserIdAndOptionIdIn(any(), anyInt())).willReturn(new ArrayList<>());

        // when
        cartService.addCartList(saveDTOList, user);

        // then
    }

    @DisplayName("장바구니_추가_테스트_수정_존재")
    @Test
    public void cart_add_test_contains_update() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO();
        s1.setOptionId(1); s1.setQuantity(5);
        s2.setOptionId(2); s2.setQuantity(3);
        saveDTOList.add(s1); saveDTOList.add(s2);
        List<Option> optionList = createOptionList(saveDTOList);
        List<Cart> cartList = createCartList(optionList,1, user);

        // mocking
        given(optionJPARepository.findByIdIn(any())).willReturn(optionList);
        given(cartJPARepository.findByUserIdAndOptionIdIn(any(), anyInt())).willReturn(cartList);

        // when
        cartService.addCartList(saveDTOList, user);

        // then
    }

    @DisplayName("장바구니_추가_테스트_실패_중복입력")
    @Test
    public void cart_add_test_fail_duplicated_input() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO();
        s1.setOptionId(1); s1.setQuantity(5);
        s2.setOptionId(1); s2.setQuantity(3);
        saveDTOList.add(s1); saveDTOList.add(s2);

        // mocking

        // when & then
        assertThatThrownBy(() -> cartService.addCartList(saveDTOList, user))
                .isInstanceOf(OptionException.DuplicatedOptionException.class);
    }

    @DisplayName("장바구니_추가_테스트_실패_존재하지않는_옵션")
    @Test
    public void cart_add_test_fail_option_not_found() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO();
        s1.setOptionId(1); s1.setQuantity(5);
        s2.setOptionId(2); s2.setQuantity(3);
        saveDTOList.add(s1); saveDTOList.add(s2);

        // mocking
        given(optionJPARepository.findByIdIn(any())).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> cartService.addCartList(saveDTOList, user))
                .isInstanceOf(OptionException.OptionNotFoundException.class);
    }

    @DisplayName("장바구니_추가_테스트_실패_db_에러")
    @Test
    public void cart_add_test_db_save_error() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.SaveDTO> saveDTOList = new ArrayList<>();
        CartRequest.SaveDTO s1 = new CartRequest.SaveDTO();
        CartRequest.SaveDTO s2 = new CartRequest.SaveDTO();
        s1.setOptionId(1); s1.setQuantity(5);
        s2.setOptionId(2); s2.setQuantity(3);
        saveDTOList.add(s1); saveDTOList.add(s2);
        List<Option> optionList = createOptionList(saveDTOList);

        // mocking
        given(optionJPARepository.findByIdIn(any())).willReturn(optionList);
        given(cartJPARepository.findByUserIdAndOptionIdIn(any(), anyInt())).willReturn(new ArrayList<>());
        given(cartJPARepository.saveAllAndFlush(any())).willThrow(new RuntimeException("db error"));

        // when & then
        assertThatThrownBy(() -> cartService.addCartList(saveDTOList, user))
                .isInstanceOf(CartException.CartSaveException.class);

    }


    @DisplayName("장바구니_수정_테스트")
    @Test
    public void cart_update_test() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.UpdateDTO> updateDTOList = new ArrayList<>();
        CartRequest.UpdateDTO s1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO s2 = new CartRequest.UpdateDTO();
        s1.setCartId(1); s1.setQuantity(5);
        s2.setCartId(2); s2.setQuantity(3);
        updateDTOList.add(s1); updateDTOList.add(s2);
        List<Option> optionList = createUpdateOptionList(updateDTOList);
        List<Cart> cartList = createCartList(optionList, 2, user);

        // mocking
        given(cartJPARepository.findAllByUserIdFetchOption(anyInt())).willReturn(cartList);

        // when
        CartResponse.UpdateDTO resultDTO = cartService.update(updateDTOList, user);

        // then
        assertThat(resultDTO.getTotalPrice()).isEqualTo(11000);
        assertThat(resultDTO.getCarts().get(0).getQuantity()).isEqualTo(5);
        assertThat(resultDTO.getCarts().get(0).getPrice()).isEqualTo(5000);
        assertThat(resultDTO.getCarts().get(1).getQuantity()).isEqualTo(3);
        assertThat(resultDTO.getCarts().get(1).getPrice()).isEqualTo(6000);
    }

    @DisplayName("장바구니_수정_테스트_실패_빈_장바구니")
    @Test
    public void cart_update_test_fail_empty_carts() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.UpdateDTO> updateDTOList = new ArrayList<>();
        CartRequest.UpdateDTO s1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO s2 = new CartRequest.UpdateDTO();
        s1.setCartId(1); s1.setQuantity(5);
        s2.setCartId(2); s2.setQuantity(3);
        updateDTOList.add(s1); updateDTOList.add(s2);

        // mocking
        given(cartJPARepository.findAllByUserIdFetchOption(anyInt())).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> cartService.update(updateDTOList, user))
                .isInstanceOf(CartException.CartNotFoundException.class);
    }

    @DisplayName("장바구니_수정_테스트_실패_중복된_장바구니_입력")
    @Test
    public void cart_update_test_fail_duplicated_carts() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.UpdateDTO> updateDTOList = new ArrayList<>();
        CartRequest.UpdateDTO s1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO s2 = new CartRequest.UpdateDTO();
        s1.setCartId(1); s1.setQuantity(5);
        s2.setCartId(1); s2.setQuantity(3);
        updateDTOList.add(s1); updateDTOList.add(s2);
        List<Option> optionList = createUpdateOptionList(updateDTOList);
        List<Cart> cartList = createCartList(optionList, 1, user);

        // mocking

        // when & then
        assertThatThrownBy(() -> cartService.update(updateDTOList, user))
                .isInstanceOf(CartException.DuplicatedCartException.class);
    }

    @DisplayName("장바구니_수정_테스트_실패_회원에_없는_장바구니_입력")
    @Test
    public void cart_update_test_fail_not_exists_user_carts() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.UpdateDTO> updateDTOList = new ArrayList<>();
        CartRequest.UpdateDTO s1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO s2 = new CartRequest.UpdateDTO();
        s1.setCartId(3); s1.setQuantity(5);
        s2.setCartId(4); s2.setQuantity(3);
        updateDTOList.add(s1); updateDTOList.add(s2);
        List<Option> optionList = createUpdateOptionList(updateDTOList);
        List<Cart> cartList = createCartList(optionList, 2, user);

        // mocking
        given(cartJPARepository.findAllByUserIdFetchOption(anyInt())).willReturn(cartList);

        // when & then
        assertThatThrownBy(() -> cartService.update(updateDTOList, user))
                .isInstanceOf(CartException.NotExistsUserCartsException.class);

    }

    @DisplayName("장바구니_수정_테스트_실패_db_에러")
    @Test
    public void cart_update_test_fail_db_error() {
        // given
        User user = User.builder().id(1).build();
        List<CartRequest.UpdateDTO> updateDTOList = new ArrayList<>();
        CartRequest.UpdateDTO s1 = new CartRequest.UpdateDTO();
        CartRequest.UpdateDTO s2 = new CartRequest.UpdateDTO();
        s1.setCartId(1); s1.setQuantity(5);
        s2.setCartId(2); s2.setQuantity(3);
        updateDTOList.add(s1); updateDTOList.add(s2);
        List<Option> optionList = createUpdateOptionList(updateDTOList);
        List<Cart> cartList = createCartList(optionList, 2, user);

        // mocking
        given(cartJPARepository.findAllByUserIdFetchOption(anyInt())).willReturn(cartList);
        given(cartJPARepository.saveAllAndFlush(any())).willThrow(new RuntimeException("db error"));

        // when & then
        assertThatThrownBy(() -> cartService.update(updateDTOList, user))
                .isInstanceOf(CartException.CartUpdateException.class);
    }

    @DisplayName("장바구니_조회_테스트")
    @Test
    public void cart_findAllByUserId_test() {
        // given
        User user = User.builder().id(1).build();
        List<Option> optionList = optionDummyList(productDummyList()).subList(0, 2);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(new Cart(1, user, optionList.get(0), 5, optionList.get(0).getPrice() * 5));
        cartList.add(new Cart(2, user, optionList.get(1), 5, optionList.get(1).getPrice() * 5));
        int totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();

        // mocking
        given(cartJPARepository.findByUserIdOrderByOptionIdAsc(anyInt())).willReturn(cartList);

        // when
        CartResponse.FindAllDTO resultDTO = cartService.findAll(user);

        // then
        assertThat(resultDTO.getProducts().get(0).getCarts().get(0).getId()).isEqualTo(1);
        assertThat(resultDTO.getProducts().get(0).getCarts().get(1).getId()).isEqualTo(2);
        assertThat(resultDTO.getTotalPrice()).isEqualTo(totalPrice);
    }

    @DisplayName("장바구니_조회_테스트_실패_빈_장바구니")
    @Test
    public void cart_findAllByUserId_test_fail_empty_carts() {
        // given
        User user = User.builder().id(1).build();
        List<Option> optionList = optionDummyList(productDummyList()).subList(0, 2);

        // mocking
        given(cartJPARepository.findByUserIdOrderByOptionIdAsc(anyInt())).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> cartService.findAll(user))
                .isInstanceOf(CartException.CartNotFoundException.class);

    }

    private List<Option> createOptionList(List<CartRequest.SaveDTO> dtos) {
        List<Integer> ids = dtos.stream().map(CartRequest.SaveDTO::getOptionId).collect(Collectors.toList());
        return ids.stream().map(i -> Option.builder().id(i).price(1000*i).build()).collect(Collectors.toList());
    }

    private List<Option> createUpdateOptionList(List<CartRequest.UpdateDTO> dtos) {
        List<Integer> ids = dtos.stream().map(CartRequest.UpdateDTO::getCartId).collect(Collectors.toList());
        return ids.stream().map(i -> Option.builder().id(i).price(1000*i).build()).collect(Collectors.toList());
    }

    private List<Cart> createCartList(List<Option> optionList, int limit, User user) {
        List<Cart> cartList = new ArrayList<>();
        for(int i = 1; i <= limit; i++) {
            cartList.add(Cart.builder()
                    .id(i)
                    .option(optionList.get(i-1))
                    .user(user)
                    .quantity(5)
                    .price(optionList.get(i-1).getPrice()*5)
                    .build());
        }
        return cartList;

    }

}
