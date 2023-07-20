package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final FakeStore fakeStore;

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findAll();
        boolean isValid = cartList.stream().allMatch(
                cart -> cart.getUser().getId() == user.getId()
        );
        if (!isValid){
            throw new Exception403("인증된 user는 해당 장바구니로 접근할 권한이 없습니다" + user.getId());
        }
        return new CartResponse.FindAllDTO(cartList);
    }
    @Transactional
    public void add(List<CartRequest.SaveDTO> saveDTOS, User user){
        // TODO: add cart
        cartJPARepository.saveAll(fakeStore.getCartList());
    }
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> updateDTOS, User user){
        // TODO: update cart
        List<Cart> cartList = fakeStore.getCartList();
        return new CartResponse.UpdateDTO(cartList);
    }
    @Transactional
    public void deleteAll(User user){
        // TODO: delete cart
    }
}
