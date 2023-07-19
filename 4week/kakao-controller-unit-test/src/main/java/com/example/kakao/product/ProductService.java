package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    public List<Product> findAllPaging(int page) {
        return productJPARepository.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());
    }

    public List<ProductResponse.FindAllDTO> toFindAllDTO(List<Product> productList) {
        return productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
    }
    public Product findById(int productId) {
        return productJPARepository.findById(productId).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다 : "+productId)
        );
    }

    public List<Option> findByProductId(int productId) {
        List<Option> optionList = optionJPARepository.mFindByProductId(productId);
        if(optionList.isEmpty()) {
            throw new Exception404("해당 상품에 대한 옵션을 찾을 수 없습니다 : "+productId);
        }
        return optionList;
    }
}
