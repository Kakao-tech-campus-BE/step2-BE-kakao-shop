package com.example.kakaoshop.cart.domain.service;

import com.example.kakaoshop.cart.domain.converter.CartConverter;
import com.example.kakaoshop.cart.domain.converter.CashierConverter;
import com.example.kakaoshop.cart.domain.model.Cart;
import com.example.kakaoshop.cart.domain.model.Cashier;
import com.example.kakaoshop.cart.web.converter.CartFindAllResponseConverter;
import com.example.kakaoshop.cart.web.response.CartFindAllResponse;
import com.example.kakaoshop.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void getCartsByUser(User user) {
        List<Cart> cartList = cartRepository.findByUser(user)
                .stream().map(CartConverter::from)
                .collect(Collectors.toList());

        Cashier cashier = CashierConverter.from(cartList);
        int totalPrice = cashier.calculateTotalPrice();
    }
}
