package com.example.kakao.order.item;

import com.example.kakao.order.Order;
import com.example.kakao.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemJPARepository itemJPARepository;

    public List<Item> findAllByOrder(Order order) {
        return itemJPARepository.findAllByOrder(order);
    }

//    public void saveAll(List<OrderResponse.FindByIdDTO.ProductDTO.ItemDTO> itemDTOList, Order order) {
//        itemDTOList.stream().map()
//    }
}
