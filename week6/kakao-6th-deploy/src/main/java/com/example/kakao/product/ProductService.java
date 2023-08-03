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

    public ProductResponse.FindByIdDTOv2 findByIdv2(int id) {
        List<Option> optionListPS = optionRepository.findByProductIdJoinProduct(id);
        if(optionListPS.isEmpty()){
            throw new Exception404("해당 상품을 찾을 수 없습니다 : "+id);
        }
        return new ProductResponse.FindByIdDTOv2(optionListPS);
    }

    public ProductResponse.FindByIdDTO findById(int id) {
        Product productPS = productRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다 : "+id)
        );
        List<Option> optionListPS = optionRepository.findByProductId(productPS.getId());
        return new ProductResponse.FindByIdDTO(productPS, optionListPS);
    }



    public List<ProductResponse.FindAllDTO> findAll(int page) {
        // 1. 페이지 객체 만들기
        Pageable pageable = PageRequest.of(page,9);

        // 2. DB 조회하기
        Page<Product> pageContent = productRepository.findAll(pageable);

        // 3. DTO 만들기
        return pageContent.getContent().stream()
                .map(product -> new ProductResponse.FindAllDTO(product))
                .collect(Collectors.toList());
    }
}
