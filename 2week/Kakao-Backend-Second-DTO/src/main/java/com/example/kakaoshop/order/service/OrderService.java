package com.example.kakaoshop.order.service;

import com.example.kakaoshop.order.request.OrderItemDTO;
import com.example.kakaoshop.order.request.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {
    private final Map<Integer, OrderResponseDTO> orderStore = new HashMap<>();
    private final AtomicInteger orderId = new AtomicInteger(1);

    public OrderResponseDTO createOrder() {

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        orderItemDTOList.add(new OrderItemDTO(4, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10, 100000));
        orderItemDTOList.add(new OrderItemDTO(5, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10, 109000));

        List<OrderProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(new OrderProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", orderItemDTOList));

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(orderId.getAndIncrement());
        response.setProducts(productDTOList);
        response.setTotalPrice(209000); // 임의의 총 가격을 설정

        orderStore.put(response.getId(), response);
        return response;
    }

    public OrderResponseDTO getOrder(int id) {
        return orderStore.get(id);
    }
}
