package com.example.kakao.product.domain.service;

import com.example.kakao.product.domain.converter.ProductConverter;
import com.example.kakao.product.domain.converter.ProductOptionConverter;
import com.example.kakao.product.domain.exception.NotFoundProductException;
import com.example.kakao.product.domain.model.Product;
import com.example.kakao.product.domain.model.ProductOption;
import com.example.kakao.product.entity.ProductEntity;
import com.example.kakao.product.web.converter.ProductResponseConverter;
import com.example.kakao.product.web.response.ProductReponse;
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
                .orElseThrow(NotFoundProductException::new);

        Product product = ProductConverter.from(productEntity);

        List<ProductOption> productOptions = productOptionRepository.findByProductEntity(productEntity)
                .stream()
                .map(ProductOptionConverter::from)
                .collect(Collectors.toList());

        return ProductResponseConverter.from(product, productOptions);
    }

    public List<ProductReponse.ProductFindAllResponse> getPosts(int page) {
        List<Product> products = productRepository.findAll(page)
                .stream()
                .map(ProductConverter::from)
                .collect(Collectors.toList());

        return products.stream()
                .map(ProductResponseConverter::from)
                .collect(Collectors.toList());
    }
}
