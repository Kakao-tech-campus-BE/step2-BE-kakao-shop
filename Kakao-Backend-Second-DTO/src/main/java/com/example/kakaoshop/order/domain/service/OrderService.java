package com.example.kakaoshop.order.domain.service;

import com.example.kakaoshop.cart.domain.service.CartRepository;
import com.example.kakaoshop.order.domain.converter.OrderConverter;
import com.example.kakaoshop.order.domain.converter.OrderItemConverter;
import com.example.kakaoshop.order.domain.model.OrderCashier;
import com.example.kakaoshop.order.domain.model.OrderItem;
import com.example.kakaoshop.order.entity.OrderEntity;
import com.example.kakaoshop.order.entity.OrderItemEntity;
import com.example.kakaoshop.order.web.converter.OrderResponseConverter;
import com.example.kakaoshop.order.web.response.OrderResponse;
import com.example.kakaoshop.product.domain.service.ProductOptionRepository;
import com.example.kakaoshop.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponse saveOrder(User user) {
        List<OrderItem> orderItems = findOrderItemsByUser(user);

        OrderCashier orderCashier = new OrderCashier(orderItems);
        int totalPrice = orderCashier.calculateTotalPrice();

        OrderEntity orderEntity = orderRepository.save(OrderConverter.to(user, totalPrice));

        List<OrderItemEntity> orderItemEntities = orderItems.stream()
                .map(x -> OrderItemConverter.to(x, orderEntity))
                .collect(Collectors.toList());

        return OrderResponseConverter.from(orderEntity, orderItemEntities);
    }

    private List<OrderItem> findOrderItemsByUser(User user){
        return cartRepository.findByUser(user).stream()
                .map(x -> OrderItemConverter.to(x.getProductOption(), x.getQuantity()))
                .map(OrderItemConverter::from)
                .collect(Collectors.toList());
    }
}
