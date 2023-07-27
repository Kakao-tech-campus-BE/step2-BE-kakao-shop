package com.example.kakao.order.item;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemJPARepository orderItemJPARepository;

    public List<OrderItem> findAll(){
        return orderItemJPARepository.findAll();
    }

    public OrderItem findById(int id) {
        // 유효성 검사 등 필요한 로직 구현
        // 주문 조회 로직 추가

        return orderItemJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다. ID: " + id));
    }
}
