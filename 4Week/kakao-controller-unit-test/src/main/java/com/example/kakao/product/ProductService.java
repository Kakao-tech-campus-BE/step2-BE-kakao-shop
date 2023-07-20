package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public List<Product> findAllProduct(int page){
        List<Product> productList;

        try {
            productList = productJPARepository.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());
            return productList;
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }
    }

    public List<ProductResponse.FindAllDTO> findAll(List<Product> productList){
        return productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
    }

    public Product findByIdProduct(int id){
        Product product = productJPARepository.findById(id).orElseThrow(
        () -> new Exception404("해당 상품을 찾을 수 없습니다 : " + id)
        );

        return product;
    }

    public List<Option> findOptionByProductID(int productId) {
        List<Option> optionList;

        try {
            optionList = optionJPARepository.findByProductId(productId);
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }

        return optionList;
    }

    public ProductResponse.FindByIdDTO findByIdDTO(Product product, List<Option> optionList){
        return new ProductResponse.FindByIdDTO(product, optionList);
    }
}
