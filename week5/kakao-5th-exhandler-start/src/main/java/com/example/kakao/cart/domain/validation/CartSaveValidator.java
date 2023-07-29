package com.example.kakao.cart.domain.validation;

import com.example.kakao.cart.web.request.CartSaveReqeust;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
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
