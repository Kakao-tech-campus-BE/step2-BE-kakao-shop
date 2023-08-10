package com.example.kakao.order.domain.service;

import com.example.kakao.order.domain.converter.OrderItemConverter;
import com.example.kakao.order.domain.exception.NotExistOrderException;
import com.example.kakao.order.domain.model.OrderCashier;
import com.example.kakao.order.domain.model.OrderItem;
import com.example.kakao.order.entity.OrderEntity;
import com.example.kakao.order.entity.OrderItemEntity;
import com.example.kakao.order.web.converter.OrderResponseConverter;
import com.example.kakao.order.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindOrderUsecase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderResponse execute(Long orderId){
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(NotExistOrderException::new);

        List<OrderItemEntity> orderItemEntities = orderItemRepository.findAllByOrder(orderEntity);
        return OrderResponseConverter.from(orderEntity, orderItemEntities,getTotalPrice(orderItemEntities));
    }

    private int getTotalPrice(List<OrderItemEntity>orderItemEntities){
        List<OrderItem> orderItems = orderItemEntities.stream()
                .map(OrderItemConverter::from)
                .collect(Collectors.toList());

        OrderCashier orderCashier = new OrderCashier(orderItems);
        return orderCashier.calculateTotalPrice();
    }

}
