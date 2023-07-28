package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final FakeStore fakeStore;


    // 주문 id에 따른 주문 결과 확인
    public OrderResponse.FindByIdDTO getOrder(int id) {
    	
    	// 만약 주문 내역을 찾을 수 없다면 404NOTFOUND를 내보냄
    	// 의미상으로는 403이 맞다고 생각했지만 보안을 위해서는 404가 낫다고 생각했습니다.
    	try {
    		Order order = fakeStore.getOrderList().get(id-1);
            List<Item> itemList = fakeStore.getItemList();
            return new OrderResponse.FindByIdDTO(order, itemList);
    	} catch(Exception e) {
    		throw new Exception404("주문 내역을 찾을 수 없습니다.");
    	}
    	
    }

    // 결제하기
    @Transactional
    public OrderResponse.FindByIdDTO saveOrder() {
    	
    	// 결제에 실패했을 경우 서버 에러를 터뜨림
    	try {
    		Order order = fakeStore.getOrderList().get(0);
            List<Item> itemList = fakeStore.getItemList();
            return new OrderResponse.FindByIdDTO(order, itemList);
    	} catch(Exception e) {
    		throw new Exception500("결제에 실패했습니다.");
    	}
    }
}
