package com.example.kakao.cart;

import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final FakeStore fakeStore;
    public CartResponse.FindAllDTO findAll() {
        /*
        List<Cart> cartList = fakeStore.getCartList();
        return new CartResponse.FindAllDTO(cartList);

         */
        return new CartResponse.FindAllDTO(new LinkedList<>());
        //서비스 로직이 완성되지 않아도 Mockito를 사용하여 테스트를 할 수 있다.
    }

    public void addCartList(List<CartRequest.SaveDTO> requestDTOs) {
        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
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

        return new CartResponse.UpdateDTO(fakeStore.getCartList());
    }
}
