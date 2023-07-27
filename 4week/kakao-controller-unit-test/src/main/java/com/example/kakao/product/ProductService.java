package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public ProductResponse.FindByIdDTO findByIdDTO(Integer id){
        Product product = productJPARepository.findById(id).orElseThrow(
                () -> new Exception400("제품을 찾을 수 없습니다.")
        );
        return new ProductResponse.FindByIdDTO(product,optionJPARepository.findByProductId(id));
    }




}
