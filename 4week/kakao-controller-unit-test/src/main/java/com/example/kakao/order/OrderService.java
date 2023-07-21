package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final FakeStore fakeStore;
    private final OrderJPARepository orderJPARepository;
    public OrderResponse.FindByIdDTO save() {
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return responseDTO;
    }

    public OrderResponse.FindByIdDTO findById(int id) throws Exception404 {
        // 레포지토리에 기존 정보가 저장되어 있지 않아서 레포지토리에서 리턴한 orderID로 가정
        Order order = fakeStore.getOrderList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if(order == null){
            throw new Exception404("해당 상품을 찾을 수 없습니다");
        }
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return responseDTO;
    }
}
