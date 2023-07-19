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
public class ItemService {
    private final ItemJPARepository itemJPARepository;

    public List<Item> itemFindByOrderId(Order order) {
        int orderId = order.getId();
        List<Item> itemList = itemJPARepository.findByOrderId(orderId);
        if(itemList.isEmpty()) {
            throw new Exception404("해당 주문에 대한 아이템을 찾을 수 없습니다");
        }
        return itemList;
    }
}
