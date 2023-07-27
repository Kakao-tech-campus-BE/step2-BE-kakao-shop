package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.OrderItem;
import com.example.kakao.order.item.OrderItemJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    // 유효성 검사 추가하기
    // error 잡기

    private final OrderJPARepository orderJPARepository;
    private final OrderItemJPARepository orderItemJPARepository;

    public List<Order> findAll(){
        return orderJPARepository.findAll();
    }

    public Order findById(int id) {
        // 유효성 검사 등 필요한 로직 구현
        // 주문 조회 로직 추가

        return orderJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다. ID: " + id));
    }
}