package com.example.kakao.cart.domain.validation;

import com.example.kakao.cart.web.request.CartUpdateRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class CartUpdateValidator {
    public static void validate(final List<CartUpdateRequest>requests) {
        validateDuplicated(requests);
    }

    private static void validateDuplicated(List<CartUpdateRequest> requests) {
        Set<Long> notDuplicated = requests.stream()
                .map(CartUpdateRequest::getCartId)
                .collect(Collectors.toSet());

        if(notDuplicated.size()!= requests.size()){
            throw new IllegalArgumentException("장바구니 요청에 동일한 옵션이 들어있습니다.");
        }
    }
}
