package com.example.kakao.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    public OrderResponse.FindByIdDTO save(int userId) {
        return null;
    }

    public OrderResponse.FindByIdDTO findById(int orderId, int userId) {
        // todo 해당 주문이 접속자의 주문인지 체크
        return null;
    }
}
