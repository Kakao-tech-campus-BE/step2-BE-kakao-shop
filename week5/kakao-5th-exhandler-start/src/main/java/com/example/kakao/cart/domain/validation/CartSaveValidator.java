package com.example.kakao.cart.domain.validation;

import com.example.kakao.cart.web.request.CartSaveReqeust;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CartSaveValidator {

    public void validateCreateConstraint(final List<CartSaveReqeust> cartRequests) {
        isDuplicated(cartRequests);
    }
    private void isDuplicated(List<CartSaveReqeust>cartRequests){
        Set<Long> notDuplicated = cartRequests.stream()
                .map(CartSaveReqeust::getOptionId)
                .collect(Collectors.toSet());

        if(notDuplicated.size()!= cartRequests.size()){
            throw new IllegalArgumentException("장바구니 요청에 동일한 옵션이 들어있습니다.");
        }
    }
}
