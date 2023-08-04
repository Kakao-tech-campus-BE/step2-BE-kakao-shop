package com.example.kakao.product.repository;

import com.example.kakao.product.domain.service.ProductRepository;
import com.example.kakao.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productRepository;

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductEntity> findAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, 9);
        return productRepository.findAll(pageRequest).getContent();
    }

}
