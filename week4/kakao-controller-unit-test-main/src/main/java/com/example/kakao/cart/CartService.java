package com.example.kakao.cart;

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

    public CartResponse.FindAllDTO findAll() {
        List<Cart> cartList = cartJPARepository.findAll();
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
