package com.example.kakao.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductJPARepository productJPARepository;
    public List<Product> findAllPaging(int page) {
        return productJPARepository.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());
    }

    public List<ProductResponse.FindAllDTO> toFindAllDTO(List<Product> productList) {
        return productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
    }
}
