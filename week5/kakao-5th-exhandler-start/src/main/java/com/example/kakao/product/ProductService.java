package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productRepository;
    private final OptionJPARepository optionRepository;

    /* 전체상품조회 Service Layer */
    public List<ProductResponse.FindAllDTO> findAll(int page) {
        // 1. 페이지 객체 만들기
        Pageable pageable = PageRequest.of(page, 9);

        // 2. DB 조회하기
        Page<Product> pageContent = productRepository.findAll(pageable);

        // 3. DTO 만들기
        List<ProductResponse.FindAllDTO> responseDTOs = pageContent.getContent().stream()
                .map(product -> new ProductResponse.FindAllDTO(product))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    /* 개별상품조회 Product, Option 개별쿼리 Service Layer */
    public ProductResponse.FindByIdDTO findById(int id) {
        /* product 쿼리 */
        Product productPS = productRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다 : " + id)
        );
        /* option 쿼리 */
        List<Option> optionListPS = optionRepository.findByProductId(productPS.getId());
        /* DTO 반환 */
        return new ProductResponse.FindByIdDTO(productPS, optionListPS);
    }

    /* 개별상품조회 한방쿼리 Service Layer */
    public ProductResponse.FindByIdDTOv2 findByIdDTOv2(int id) {
        /* 한방쿼리 호출 */
        List<Option> optionListPS = optionRepository.findByProductIdJoinProduct(id);
        /* DTO 반환 */
        return new ProductResponse.FindByIdDTOv2(optionListPS);
    }
}
