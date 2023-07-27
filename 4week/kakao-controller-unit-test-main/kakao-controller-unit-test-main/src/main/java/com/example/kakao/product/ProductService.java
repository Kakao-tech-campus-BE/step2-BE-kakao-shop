package com.example.kakao.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJPARepository productJPARepository;

    public List<Product> findAll() {
        return productJPARepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productJPARepository.findById(id);
    }
}
