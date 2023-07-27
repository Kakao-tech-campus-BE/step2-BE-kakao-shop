package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final FakeStore fakeStore;

    public Order findById(int id){
        try {
            return fakeStore.getOrderList().get(id - 1);
        }catch (RuntimeException e){
            throw new Exception404("해당 주문을 찾을 수 없습니다:"+id);
        }
    }
}
