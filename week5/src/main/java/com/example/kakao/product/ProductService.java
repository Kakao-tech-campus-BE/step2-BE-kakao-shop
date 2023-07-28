package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public List<ProductResponse.FindAllDTO> findAll(int page) {

        Pageable pageable = PageRequest.of(page, 9);

        Page<Product> pageContent = productJPARepository.findAll(pageable);

        List<ProductResponse.FindAllDTO> responeDTOs =
                pageContent.getContent().stream()
                        .map(product -> new ProductResponse.FindAllDTO(product))
                        .collect(Collectors.toList());

        return responeDTOs;
    }

    @Transactional
    public ProductResponse.FindByIdDTO findById(int id) {

        // id에 해당하는 product 조회
        Product product = productJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다 : "+id)
        );
        // product에 해당하는 옵션 리스트 불러오기
        List<Option> optionList = optionJPARepository.findByProductId(product.getId()); // id로 해도 되지?

        // 불러온 product와 option으로 응답 DTO 만들기
        return new ProductResponse.FindByIdDTO(product, optionList);
    }

    @Transactional
    public ProductResponse.FindByIdDTOv2 findByIdv2(int id) {

        // 상품 id로 옵션 조회
        List<Option> optionList = optionJPARepository.findByProductIdJoinProduct(id);
        if(optionList.size() == 0){
            throw new Exception404("해당 상품을 찾을 수 없습니다 : "+id);
        }

        // 불러온 product와 option으로 응답 DTO 만들기
        return new ProductResponse.FindByIdDTOv2(optionList);
    }
}
