package com.example.kakao.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJPARepository productJPARepository;

    public List<Product> saveAll(List<Product> products) {
        return productJPARepository.saveAll(products);
    }

    public void deleteAll() {
        productJPARepository.deleteAll();
    }

    public List<Product> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productJPARepository.findAll(pageRequest).stream().skip(page*size).collect(Collectors.toList());
    }
}
