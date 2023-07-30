package com.example.kakao.cart.domain.service;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.domain.converter.CartConverter;
import com.example.kakao.cart.domain.converter.CashierConverter;
import com.example.kakao.cart.domain.model.Cart;
import com.example.kakao.cart.domain.model.Cashier;
import com.example.kakao.cart.domain.validation.CartUpdateValidator;
import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.cart.web.converter.CartUpdateResponseConverter;
import com.example.kakao.cart.web.request.CartUpdateRequest;
import com.example.kakao.cart.web.response.CartUpdateResponse;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UpdateCartUseCase {
    private final CartRepository cartRepository;

    @Transactional
    public CartUpdateResponse execute(User user, List<CartUpdateRequest> requests) {
        CartUpdateValidator.validate(requests);

        List<Cart> carts = requests.stream()
                .map(request -> updateCart(user, request))
                .map(CartConverter::from)
                .collect(Collectors.toList());

        int totalPrice = getTotalPrice(carts);

        return CartUpdateResponseConverter.from(carts, totalPrice);
    }

    private int getTotalPrice(List<Cart> carts) {
        Cashier cashier = Objects.requireNonNull(CashierConverter.from(carts));
        return cashier.calculateTotalPrice();
    }

    private CartEntity updateCart(User user, CartUpdateRequest request){
        CartEntity entity = cartRepository.findByCartIdAndUser(request.getCartId(), user)
                .orElseThrow(() -> new Exception404("존재하지 않는 장바구니 번호"));

        entity.addQuantity(request.getQuantity());
        return cartRepository.saveAndFlush(entity);
    }
}
