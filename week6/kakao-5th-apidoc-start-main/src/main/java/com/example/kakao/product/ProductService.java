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
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public List<ProductResponse.FindAllDTO> findAll(int page) {
        // 1. 페이지 객체 만들기
        Pageable pageable = PageRequest.of(page, 9);

        // 2. DB 조회하기
        Page<Product> pageContent = productJPARepository.findAll(pageable);

        // 3. DTO 만들기
        // 페이지 객체의 content는 List이다
        // List를 stream()으로 변환 -> 자바 오브젝트의 타입이 없어진다.
        // map으로 순회하면서 값을 변환
        // 가공된 데이터를 다시 자바 오브젝트로 변환한다
        List<ProductResponse.FindAllDTO> responseDTOs = pageContent.getContent().stream()
                .map(product -> new ProductResponse.FindAllDTO(product))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public ProductResponse.FindByIdDTO findById(int id) {
        List<Option> optionList = optionJPARepository.findByProductIdJoinProduct(id);
        if (optionList.isEmpty()) {
            throw new Exception404("해당 상품을 찾을 수 없습니다. : " + id);
        }
        return new ProductResponse.FindByIdDTO(optionList);
    }
}
