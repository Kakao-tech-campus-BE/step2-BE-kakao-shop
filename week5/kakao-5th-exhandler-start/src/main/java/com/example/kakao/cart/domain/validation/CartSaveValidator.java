package com.example.kakao.cart.domain.validation;

import com.example.kakao.cart.domain.exception.DuplicatedCartRequestException;
import com.example.kakao.cart.web.request.CartSaveReqeust;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class CartSaveValidator {

    public static void validate(final List<CartSaveReqeust> requests){
        isDuplicated(requests);
    }
    private static void isDuplicated(List<CartSaveReqeust>requests){
        Set<Long> notDuplicated = requests.stream()
                .map(CartSaveReqeust::getOptionId)
                .collect(Collectors.toSet());

        if(notDuplicated.size()!= requests.size()){
            throw new DuplicatedCartRequestException();
        }
    }
}
