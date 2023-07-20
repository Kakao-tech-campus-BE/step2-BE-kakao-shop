package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    public void updateQuantity(List<CartRequest.UpdateDTO> requestDTOs) {
        // 음수가 들어오는지 체크 
        validateQuantityForCartIds(requestDTOs);
        // 동일한 cartId 가 들어오는지 체크
        validateDuplicateCartIds(requestDTOs);

    }

    private void validateQuantityForCartIds(List<CartRequest.UpdateDTO> requestDTOs) {
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            if (updateDTO.getQuantity() < 0) {
                throw new Exception400("수량은 0 이상이어야 합니다. quantity : " + updateDTO.getQuantity());
            }
        }
    }

    private void validateDuplicateCartIds(List<CartRequest.UpdateDTO> requestDTOs) {
        Set<Integer> cartIdsSet = new HashSet<>();
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();
            if (cartIdsSet.contains(cartId)) {
                throw new Exception400("동일한 카트옵션을 추가할 수 없습니다. cartId : " + updateDTO.getCartId());
            }
            cartIdsSet.add(cartId);
        }
    }







    public void checkQuantity(List<CartRequest.SaveDTO> requestDTOs) {
        // 수량 음수 체크
        validateQuantityForOptionId(requestDTOs);
        // 같은 옵션이 들어가있는지 체크
        validateDuplicateOptionIds(requestDTOs);

    }

    // 수량 음수 체크
    public void validateQuantityForOptionId(List<CartRequest.SaveDTO> requestDTOs) {
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            if (requestDTO.getQuantity() < 0) {
                throw new Exception400("수량은 0 이상이어야 합니다. quantity : " + requestDTO.getQuantity());
            }
        }
    }

    // 같은 옵션이 들어가있는지 체크
    public void validateDuplicateOptionIds(List<CartRequest.SaveDTO> requestDTOs) {
        Set<Integer> optionSet = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            if (optionSet.contains(optionId)) {
                throw new Exception400("동일한 옵션을 추가할 수 없습니다. optionId : " + requestDTO.getOptionId());
            }
            optionSet.add(optionId);
        }
    }
}
