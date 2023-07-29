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
import java.util.Optional;
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
    public void addCarts(User user, List<CartReqeust> cartSaveRequests) {
        cartValidator.validateCreateConstraint(cartSaveRequests);

        cartSaveRequests.forEach(cartReqeust -> updateOrCreateCart(cartReqeust,user));
    }

    private ProductOptionEntity getProductOptionById(Long optionId) {
        return productOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션"));
    }

    private Optional<CartEntity> saveByExisting(CartReqeust cartRequest) {
        ProductOptionEntity productOptionEntity = getProductOptionById(cartRequest.getOptionId());

        return cartRepository.findByProductOptionEntity(productOptionEntity);
    }

    private void updateOrCreateCart(CartReqeust cartReqeust, User user) {
        Optional<CartEntity> cartEntity = saveByExisting(cartReqeust);
        if (cartEntity.isEmpty()) {
            saveCart(cartReqeust, user);
        }
        updateCart(cartReqeust, cartEntity);
    }

    private void updateCart(CartReqeust cartReqeust, Optional<CartEntity> cartEntity) {
        cartEntity.ifPresent(entity -> entity.addQuantity(cartReqeust.getQuantity()));
    }

    private void saveCart(CartReqeust cartReqeust, User user) {
        CartEntity newCartEntity = CartConverter.to(cartReqeust.getQuantity(), getProductOptionById(cartReqeust.getOptionId()), user);
        cartRepository.save(newCartEntity);
    }

    public void updateCarts(User user, List<CartReqeust> cartUpdateRequests) {
        cartValidator.validateUpdateConstraint(cartUpdateRequests);

        cartUpdateRequests
                .forEach(request -> findCart(user, request));
    }

    private void findCart(User user, CartReqeust cartReqeust){
        Optional<CartEntity> cartEntity = cartRepository.findByUserAndProductOption(user, cartReqeust.getOptionId());
        cartEntity.orElseThrow(() -> new IllegalArgumentException("잘못된 요청 : 옵션이 존재하지 않습니다."));

        updateCart(cartReqeust, cartEntity);
    }

}
