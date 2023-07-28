package com.example.kakao.cart.domain.validation;

import com.example.kakao.cart.web.request.CartReqeust;
import com.example.kakao.product.domain.service.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartValidator {
    private final ProductOptionRepository productOptionRepository;

    public void validateCreateConstraint(final List<CartReqeust> cartReqeusts) {
        isDuplicated(cartReqeusts);

    }
    private void isDuplicated(List<CartReqeust>cartReqeusts){
        Set<Long> notDuplicated = cartReqeusts.stream()
                .map(CartReqeust::getOptionId)
                .collect(Collectors.toSet());

        if(notDuplicated.size()!= cartReqeusts.size()){
            throw new IllegalArgumentException("장바구니 요청에 동일한 옵션이 들어있습니다.");
        }
    }


}
