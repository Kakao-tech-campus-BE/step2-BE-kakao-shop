package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.kakao.product.option.ProductOption;


import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {
    // 유효성 검사 추가하기
    // error 잡기

    private final ProductJPARepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 상품을 찾을 수 없습니다: " + id));
    }

/*
    public Product save(ProductRequest.CreateOrUpdateRequest request) {
        Product product = ProductRequest.toEntity(request);
        return productRepository.save(product);
    }

    public Product update(int id, ProductRequest.CreateOrUpdateRequest request) {
        Product product = findById(id);
        product.update(request.getProductName(), request.getDescription(), request.getImage(), request.getPrice());
        return product;
    }

    public void delete(int id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
*/



}
