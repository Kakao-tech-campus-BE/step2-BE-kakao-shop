package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
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

    // 수량 변경을 위해 가져옴
    private CartJPARepository cartJPARepository;
    public void checkQuantity(List<CartRequest.SaveDTO> requestDTOs) {
        // 수량 음수 체크
        validateQuantity(requestDTOs);
        // 같은 옵션이 들어가있는지 체크
        validateDuplicateOptions(requestDTOs);

    }

    // 수량 음수 체크
    public void validateQuantity(List<CartRequest.SaveDTO> requestDTOs) {
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            if (requestDTO.getQuantity() < 0) {
                throw new Exception400("수량은 0 이상이어야 합니다. quantity : " + requestDTO.getQuantity());
            }
        }
    }

    // 같은 옵션이 들어가있는지 체크
    public void validateDuplicateOptions(List<CartRequest.SaveDTO> requestDTOs) {
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
