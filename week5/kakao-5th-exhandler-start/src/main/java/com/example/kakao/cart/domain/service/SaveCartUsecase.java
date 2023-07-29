package com.example.kakao.cart.domain.service;

import com.example.kakao.cart.domain.converter.CartConverter;
import com.example.kakao.cart.domain.validation.CartSaveValidator;
import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.cart.web.request.CartSaveReqeust;
import com.example.kakao.product.domain.service.ProductOptionRepository;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SaveCartUsecase {
    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CartSaveValidator cartSaveValidator;

    @Transactional
    public void execute(User user, List<CartSaveReqeust> cartSaveRequests) {
        cartSaveValidator.validateCreateConstraint(cartSaveRequests);

        cartSaveRequests.forEach(request -> updateOrCreateCart(request,user));
    }

    private void updateOrCreateCart(CartSaveReqeust request, User user) {
        Optional<CartEntity> cartEntity = saveByExisting(request);
        if (cartEntity.isEmpty()) {
            saveCart(request, user);
        }
        updateCart(request, cartEntity);
    }

    private Optional<CartEntity> saveByExisting(CartSaveReqeust request) {
        ProductOptionEntity productOptionEntity = getProductOptionById(request.getOptionId());

        return cartRepository.findByProductOptionEntity(productOptionEntity);
    }

    private void saveCart(CartSaveReqeust request, User user) {
        CartEntity newCartEntity = CartConverter.to(request.getQuantity(), getProductOptionById(request.getOptionId()), user);
        cartRepository.save(newCartEntity);
    }

    private void updateCart(CartSaveReqeust request, Optional<CartEntity> cartEntity) {
        cartEntity.ifPresent(entity -> entity.addQuantity(request.getQuantity()));
    }

    private ProductOptionEntity getProductOptionById(Long optionId) {
        return productOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션"));
    }
}
