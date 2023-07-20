package com.example.kakao.cart;

import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final FakeStore fakeStore;

    public void addCartList(List<CartRequest.SaveDTO> requestDTOs) {
        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
    }

    public CartResponse.FindAllDTO findAll() {
        List<Cart> cartList = fakeStore.getCartList();
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs) {
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );

        // 가짜 저장소의 값을 변경한다.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                }
            }
        }

        // DTO를 만들어서 응답한다.
        return new CartResponse.UpdateDTO(fakeStore.getCartList());
    }
}
