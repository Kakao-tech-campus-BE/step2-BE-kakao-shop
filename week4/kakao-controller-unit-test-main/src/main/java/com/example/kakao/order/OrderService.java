package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final FakeStore fakeStore;

    @Transactional
    public OrderResponse.FindByIdDTO save(User user){
        // fake
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        if (itemList.size() == 0) {
            throw new Exception400("주문 가능한 물품이 1개 이상이어야 합니다.");
        }
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
    @Transactional
    public OrderResponse.FindByIdDTO findByOrderId(int id){
        // fake
        if (fakeStore.getOrderList().size() <= id)
            throw new Exception404("존재하지 않는 주문 id입니다:" +  id);
        Order order = fakeStore.getOrderList().get(id - 1);
        List<Item> itemList = fakeStore.getItemList();
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}
