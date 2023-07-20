package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception500;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;

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
}
