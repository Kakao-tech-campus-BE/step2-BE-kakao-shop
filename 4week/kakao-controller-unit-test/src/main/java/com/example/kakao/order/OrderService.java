package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJPARepository orderJPARepository;

    public Order orderFindByUserId(CustomUserDetails userDetails) {
        int userId = userDetails.getUser().getId();
        return orderJPARepository.findByUserId(userId).orElseThrow(
                () -> new Exception404("해당 유저에 대한 주문을 찾을 수 없습니다")
        );
    }
}
