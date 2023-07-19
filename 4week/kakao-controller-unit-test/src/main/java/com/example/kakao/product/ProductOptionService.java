package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
    private final ProductService productService;

    public ProductResponse.FindByIdDTO findbyId(int productId) {
        // 1. 상품 찾기
        Product product = productService.findById(productId);
        // 2. 상품에 옵션 찾기
        List<Option> optionList = productService.findByProductId(productId);
        // DTO생성
        return new ProductResponse.FindByIdDTO(product, optionList);
    }
}
