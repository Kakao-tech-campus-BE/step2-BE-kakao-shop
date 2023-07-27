package com.example.kakaoshop.cart.domain.validation;

import com.example.kakaoshop.cart.web.request.CartReqeust;
import com.example.kakaoshop.product.domain.service.ProductOptionRepository;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartValidator {
    private final ProductOptionRepository productOptionRepository;

    public void validateCreateConstraint(final CartReqeust cartReqeust) {
        validateExistsPrice(cartReqeust.getOptionId());
        validateSamePrice(cartReqeust.getOptionId(), cartReqeust.getPrice());
    }

    private void validateExistsPrice(Long optionId) {
        productOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("가격이 없다."));
    }

    private void validateSamePrice(Long optionId, int priceByCustomer) {
        int savedPrice = productOptionRepository.findById(optionId)
                .get()
                .getPrice();

        if (savedPrice != priceByCustomer) {
            throw new IllegalArgumentException("가격이 변동되었다.");
        }
    }

}
