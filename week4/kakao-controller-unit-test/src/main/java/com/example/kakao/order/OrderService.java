package com.example.kakao.order;

import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final FakeStore fakeStore;


    public OrderResponse.FindByIdDTO save() {
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    public OrderResponse.FindByIdDTO findById(int id) {
        Order order = fakeStore.getOrderList().get(id-1);
        List<Item> itemList = fakeStore.getItemList();
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}
