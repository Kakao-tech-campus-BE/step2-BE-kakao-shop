package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final FakeStore fakeStore;

    public boolean findAll(){
        return true;
    }

    public Product findById(int id){
        try {
            return fakeStore.getProductList().get(id-1);
        }catch (RuntimeException e){
            throw new Exception404("해당 상품을 찾을 수 없습니다:"+id);
        }
    }
}
