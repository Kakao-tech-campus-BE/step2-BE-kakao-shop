package com.example.kakao.cart;


import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final FakeStore fakeStore;

    public void addCartItems(List<CartRequest.SaveDTO> requestDTOs) {
        // 장바구니에 아이템 추가 로직
    	// 이번 주차에서는 fakestore를 이용했으므로 구체적인 구현은 하지 않겠습니다!
    	// 존재하지 않는 id의 값을 추가하려고 했을 때, 400error 발생시키기
    }

    public CartResponse.FindAllDTO findAllCartItems() {
        // fakestore에 있는 장바구니들을 가져오는 로직
    	List<Cart> cartList = fakeStore.getCartList();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        return responseDTO;
    }

    public CartResponse.UpdateDTO updateCartItems(List<CartRequest.UpdateDTO> requestDTOs) {
        // 장바구니의 아이템 업데이트 로직
    	// 이 역시 fakestore를 이용하여 장바구니 수량과 총 가격을 업데이트합니다
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }
        
        // DTO를 만들어서 응답한다.
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
        return responseDTO;
    }

    public void clearCart() {
        // 장바구니 비우는 로직
    	//fakeStore.getCartList().clear(); -> 500 서버 오류 발생
    }
}

