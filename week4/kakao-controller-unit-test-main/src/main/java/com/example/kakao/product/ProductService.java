package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<ProductResponse.FindAllDTO> findAll(Integer page){
        PageRequest pageRequest = PageRequest.of(page, 9);
        Page<Product> pageList = productJPARepository.findAll(pageRequest);
        return pageList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
    }

    public ProductResponse.FindByIdDTO findById(Integer id){
        Product product = productJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다:" + id)
        );
        List<Option> optionList = optionJPARepository.findByProductId(id);
        return new ProductResponse.FindByIdDTO(product, optionList);
    }
}
