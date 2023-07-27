package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;

    public OrderResponse.FindByIdDTO findByIdDTO(Integer id){
        Order orderPS = OrderJPARepository.findById(id).orElseThrow(
                () -> new Exception400("주문내역을 찾을 수 없습니다. : " + id)
        );
        return new OrderResponse.FindByIdDTO(orderPS);
    }

    public OrderJPARepository getOrderJPARepository() {
        return orderJPARepository;
    }
}
