package com.example.kakao.product;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public List<ProductResponse.FindAllDTO> getProducts(Pageable pageable) {
        Page<Product> productPage = productJPARepository.findAll(pageable);
        return productPage.map(ProductResponse.FindAllDTO::new).toList();
    }

    public ProductResponse.FindByIdDTO getProductDetails(int id){
        Product product = findById(id);

        List<Option> optionList = optionJPARepository.findByProductId(product.getId());

        return new ProductResponse.FindByIdDTO(product, optionList);
    }

    private Product findById(int id) {
        return productJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다:" + id));
    }
}
