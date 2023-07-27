package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final FakeStore fakeStore;

    public List<ProductResponse.FindAllDTO> findAll(int page) {
        // 페이지 조회
        List<Product> productListPS = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        // 페이지 유효성 검사
        if(productListPS.isEmpty()) {
            throw new Exception404("해당 페이지를 찾을 수 없습니다:"+page);
        }

        // DTO 변환
        return productListPS.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
    }

    public ProductResponse.FindByIdDTO findById(int id) {
        // 상품 조회
        Product productPS = fakeStore.getProductList().stream().filter(product -> product.getId() == id).findFirst().orElse(null);
        // 상품 유효성 검사
        if(productPS == null) {
            throw new Exception404("해당 상품을 찾을 수 없습니다:"+id);
        }
        // 옵션 조회
        List<Option> optionListPS = fakeStore.getOptionList().stream().filter(option -> option.getProduct().getId() == productPS.getId()).collect(Collectors.toList());

        // DTO 변환
        return new ProductResponse.FindByIdDTO(productPS, optionListPS);
    }
}