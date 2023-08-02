package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.ProductResponse.FindAllDTO;
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
        if (optionListPS.isEmpty()) {
            throw new Exception404("해당 상품을 찾을 수 없습니다 : " + id);
        }
        return new ProductResponse.FindByIdDTOv2(optionListPS);
    }

    public ProductResponse.FindByIdDTO findById(int id) {
        Product productPS = productRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다 : " + id)
        );
        List<Option> optionListPS = optionRepository.findByProductId(productPS.getId());
        return new ProductResponse.FindByIdDTO(productPS, optionListPS);
    }


    public List<ProductResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 9);
        Page<Product> pageContent = productRepository.findAll(pageable);
        if (pageContent.isEmpty()) {
            throw new Exception404("해당 페이지에서 product를 찾지 못하였습니다: " + page);
        }
        return pageContent.getContent().stream()
                .map(FindAllDTO::new)
                .collect(Collectors.toList());
    }

}
