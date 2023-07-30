package com.example.kakao.cart.domain.service;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.cart.web.request.CartUpdateRequest;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UpdateCartUseCase {
    private final CartRepository cartRepository;

    private void updateCart(User user, CartUpdateRequest request){
        CartEntity entity = cartRepository.findByCartIdAndUser(request.getCartId(), user)
                .orElseThrow(() -> new Exception404("존재하지 않는 장바구니 번호"));

        entity.addQuantity(request.getQuantity());
    }
}
