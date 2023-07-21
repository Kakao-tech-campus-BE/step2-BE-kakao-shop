package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.ProductResponse;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public Page<Product> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        try {
            Page<Product> products = productJPARepository.findAll(pageRequest);
            if (products.isEmpty())
                throw new Exception400("존재하지 않는 페이지입니다.");
            return products;
        } catch (RuntimeException e) {
            throw new Exception400("존재하지 않는 페이지 입니다");
        }
    }

    public Product findById(int id){
        Product product = productJPARepository.findById(id).orElseThrow(
                ()-> new Exception400("존재하지 않는 상품입니다")
        );

        return product;
    }

    public List<Option> findOptionByProductId(int id){
        List<Option> product = optionJPARepository.findByProductId(id);
        if (product.isEmpty()) {
            throw new Exception400("존재하지 않는 상품입니다");
        }
        return product;
    }
}
