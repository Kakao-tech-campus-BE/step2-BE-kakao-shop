package com.example.kakao.cart;

import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final FakeStore fakeStore;
    private final CartJPARepository cartJPARepository;
    private User user;
    private int id ;
    public void save(List<CartRequest.SaveDTO> requestDTOs){
        cartJPARepository.saveAll(fakeStore.getCartList());
    }

    public CartResponse.FindAllDTO findAll() {
        List<Cart> cartList = fakeStore.getCartList();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        return responseDTO;
    }
}
