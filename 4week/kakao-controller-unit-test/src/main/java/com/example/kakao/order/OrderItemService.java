package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {
    private final OrderService orderService;
    private final ItemService itemService;

    public OrderResponse.FindByIdDTO saveOrder(CustomUserDetails userDetails) {
        Order order = orderService.orderFindByUserId(userDetails);
        List<Item> itemList = itemService.itemFindByOrderId(order);
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}
