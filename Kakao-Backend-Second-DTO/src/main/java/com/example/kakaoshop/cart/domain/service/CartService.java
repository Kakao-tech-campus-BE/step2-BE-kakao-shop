package com.example.kakaoshop.cart.domain.service;

import com.example.kakaoshop.cart.domain.converter.CartConverter;
import com.example.kakaoshop.cart.domain.converter.CashierConverter;
import com.example.kakaoshop.cart.domain.model.Cart;
import com.example.kakaoshop.cart.domain.model.Cashier;
import com.example.kakaoshop.cart.domain.validation.CartValidator;
import com.example.kakaoshop.cart.web.converter.CartFindAllResponseConverter;
import com.example.kakaoshop.cart.web.request.CartReqeust;
import com.example.kakaoshop.cart.web.request.CartSaveRequest;
import com.example.kakaoshop.cart.web.response.CartFindAllResponse;
import com.example.kakaoshop.product.domain.converter.ProductOptionConverter;
import com.example.kakaoshop.product.domain.model.ProductOption;
import com.example.kakaoshop.product.domain.service.ProductOptionRepository;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import com.example.kakaoshop.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CartValidator cartValidator;

    public CartFindAllResponse getCartsByUser(User user) {
        List<Cart> carts = cartRepository.findByUser(user)
                .stream().map(CartConverter::from)
                .collect(Collectors.toList());

        Cashier cashier = CashierConverter.from(carts);
        int totalPrice = cashier.calculateTotalPrice();

        return CartFindAllResponseConverter.from(cashier, totalPrice);
    }
}
