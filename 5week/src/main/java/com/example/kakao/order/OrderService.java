package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final ItemJPARepository itemRepository;

    public OrderResponse.FindByIdDTO findById(int id) {
        List<Item> itemListPS = itemRepository.findByOrderIdJoinOrder(id);
        if (itemListPS.size() == 0) {
            throw new Exception404("해당 주문을 찾을 수 없습니다 : " + id);
        }
        return new OrderResponse.FindByIdDTO(itemListPS);
    }
}
