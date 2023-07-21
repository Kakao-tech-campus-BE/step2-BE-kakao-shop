package com.example.kakao.product.option;

import com.example.kakao._core.errors.exception.Exception204;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductOptionService {

    private final ProductOptionJPARepository productOptionJPARepository;

    public List<ProductOption> findAll() {
        return productOptionJPARepository.findAll();
    }

    public List<ProductOption> findOptionsByProductId(int productId) {
        List<ProductOption> productOptions = productOptionJPARepository.findByProductId(productId);

        // 가져온 데이터가 없으면 Exception204 예외를 던집니다.
        if (productOptions.isEmpty()) {
            throw new Exception204("데이터가 없습니다.:" + productId);
        }

        return productOptions;

    }

}
