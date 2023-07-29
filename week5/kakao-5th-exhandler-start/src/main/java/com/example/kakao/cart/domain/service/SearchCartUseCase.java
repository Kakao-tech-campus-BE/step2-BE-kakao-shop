package com.example.kakao.cart.domain.service;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.domain.converter.CartConverter;
import com.example.kakao.cart.domain.converter.CashierConverter;
import com.example.kakao.cart.domain.model.Cart;
import com.example.kakao.cart.domain.model.Cashier;
import com.example.kakao.cart.web.converter.CartFindAllResponseConverter;
import com.example.kakao.cart.web.response.CartFindAllResponse;
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
public class SearchCartUseCase {
    private final CartRepository cartRepository;

    public CartFindAllResponse execute(User user) {
        List<Cart> carts = cartRepository.findByUser(user)
                .stream()
                .map(CartConverter::from)
                .collect(Collectors.toList());

        if (carts.isEmpty()) {
            throw new Exception404("장바구니에 아무것도 없습니다.");
        }

        Cashier cashier = Objects.requireNonNull(CashierConverter.from(carts));
        int totalPrice = calculateTotalPrice(cashier);

        return CartFindAllResponseConverter.from(cashier, totalPrice);
    }

    private int calculateTotalPrice(Cashier cashier) {
        return cashier.calculateTotalPrice();
    }
}
