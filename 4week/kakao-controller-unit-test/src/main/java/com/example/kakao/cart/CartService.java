package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartJPARepository cartJPARepository;

    public CartResponse.FindAllDTO findAll(CustomUserDetails userDetails) {
        List<Cart> cartList = cartJPARepository.findByUserId(userDetails.getUser().getId());
        if(cartList.isEmpty()) {
            throw new Exception404("해당 장바구니를 찾을 수 없습니다");
        }
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.UpdateDTO updateCart(List<CartRequest.UpdateDTO> requestDTOs, CustomUserDetails userDetails) {
        int userId = userDetails.getUser().getId();
        List<Cart> cartList = cartJPARepository.findByUserId(userId);
        if(cartList.isEmpty()) {
            throw new Exception404("해당 유저에 대한 장바구니를 찾을 수 없습니다 : "+userId);
        }
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : cartList) {
                if(cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                }
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    }
}
