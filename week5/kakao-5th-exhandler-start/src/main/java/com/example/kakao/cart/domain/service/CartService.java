package com.example.kakao.cart.domain.service;

import com.example.kakao.cart.domain.converter.CartConverter;
import com.example.kakao.cart.domain.converter.CashierConverter;
import com.example.kakao.cart.domain.model.Cart;
import com.example.kakao.cart.domain.model.Cashier;
import com.example.kakao.cart.domain.validation.CartValidator;
import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.cart.web.converter.CartFindAllResponseConverter;
import com.example.kakao.cart.web.request.CartReqeust;
import com.example.kakao.cart.web.response.CartFindAllResponse;
import com.example.kakao.product.domain.service.ProductOptionRepository;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CartValidator cartValidator;

    public CartFindAllResponse getCartsByUser(User user) {
        List<Cart> carts = cartRepository.findByUser(user)
                .stream()
                .map(CartConverter::from)
                .collect(Collectors.toList());

        Cashier cashier = CashierConverter.from(carts);
        int totalPrice = cashier.calculateTotalPrice();

        return CartFindAllResponseConverter.from(cashier, totalPrice);
    }

    @Transactional
    public void addCarts(User user, List<CartReqeust>cartSaveRequests) {
        cartValidator.validateCreateConstraint(cartSaveRequests);

        List<CartEntity> collect = cartSaveRequests.stream()
                .map(x -> CartConverter.to(x.getQuantity(), getProductOptionById(x.getOptionId()), user))
                .collect(Collectors.toList());

        cartRepository.saveAll(collect);
    }
    private ProductOptionEntity getProductOptionById(Long optionId) {
        return productOptionRepository.findById(optionId).get();
    }

    /**
     * 1. CartRequest 에서 OptionId를 기준으로 기존에 존재하는 옵션인지 파악
     * 2. 존재하는 옵션인 경우, quantity update
     * 3. 존재하지 않는 새로운 옵션인 경우, CartSave
     */

}
