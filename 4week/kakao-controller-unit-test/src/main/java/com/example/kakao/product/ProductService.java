package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional(readOnly = true)
    public List<ProductResponse.FindAllDTO> findAll(int page, int limit){
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<Product> products = productJPARepository.findAll(pageRequest);
        return products.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse.FindByIdDTO findById(int id){

        Product product = productJPARepository.findById(id).orElseThrow(() -> new Exception404("해당 상품을 찾을 수 없습니다."));

        List<Option> optionList = optionJPARepository.mFindByProductId(id);

        // 3. DTO 변환
        return new ProductResponse.FindByIdDTO(product, optionList);
    }
}
