package com.example.kakaoshop.product.domain.service;

import com.example.kakaoshop.product.domain.converter.ProductConverter;
import com.example.kakaoshop.product.domain.converter.ProductOptionConverter;
import com.example.kakaoshop.product.domain.model.Product;
import com.example.kakaoshop.product.domain.model.ProductOption;
import com.example.kakaoshop.product.entity.ProductEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import com.example.kakaoshop.product.web.converter.ProductResponseConverter;
import com.example.kakaoshop.product.web.response.ProductReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public ProductReponse.ProductFindByIdResponse getPostByPostId(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품"));

        Product product = ProductConverter.from(productEntity);

        List<ProductOption> productOptions = productOptionRepository.findByProductEntity(productEntity)
                .stream()
                .map(ProductOptionConverter::from)
                .collect(Collectors.toList());

        return ProductResponseConverter.from(product, productOptions);
    }
}
