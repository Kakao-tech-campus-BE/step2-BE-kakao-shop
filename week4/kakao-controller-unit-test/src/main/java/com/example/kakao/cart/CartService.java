package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final FakeStore fakeStore;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs) {
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            // 이미 추가된 옵션 확인
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getOption().getId() == requestDTO.getOptionId()) {
                    throw new Exception400("이미 추가된 옵션입니다:" + requestDTO.getOptionId());
                }
            }
            // 옵션 유효성 검사
            boolean flag = false;
            for (Option option : fakeStore.getOptionList()) {
                if (option.getId() == requestDTO.getOptionId()) {
                    // 장바구니아이템 저장
                    // fakestore라서 구현 X
                    flag = true;
                    break;
                }
            }
            if (flag==false) {
                throw new Exception404("해당 옵션을 찾을 수 없습니다:" + requestDTO.getOptionId());
            }
        }
    }

    public CartResponse.FindAllDTO findAll() {
        // 장바구니아이템 조회
        List<Cart> cartListPS = fakeStore.getCartList();

        // DTO 변환
        return new CartResponse.FindAllDTO(cartListPS);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs) {
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            // 장바구니아이템 유효성 검사
            boolean flag = false;
            for (Cart cart : fakeStore.getCartList()) {
                if(cart.getId() == requestDTO.getCartId()) {
                    // 장바구니아이템 업데이트
                    cart.update(requestDTO.getQuantity(), cart.getPrice() * requestDTO.getQuantity());
                    flag = true;
                    break;
                }
            }
            if (flag==false) {
                throw new Exception404("해당 장바구니아이템을 찾을 수 없습니다:" + requestDTO.getCartId());
            }
        }

        // 장바구니아이템 조회
        List<Cart> cartListPS = fakeStore.getCartList();

        // DTO 변환
        return new CartResponse.UpdateDTO(cartListPS);
    }

}
