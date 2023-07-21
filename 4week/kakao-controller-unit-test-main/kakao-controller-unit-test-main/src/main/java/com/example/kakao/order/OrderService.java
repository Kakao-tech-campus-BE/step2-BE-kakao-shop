package com.example.kakao.order;

import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderJPARepository orderJPARepository;

    public void save(Order order) {
        orderJPARepository.save(order);
    }

    public Order save(User user) {
        Order order = Order.builder()
                .user(user)
                .build();

        return orderJPARepository.save(order);
    }
}
