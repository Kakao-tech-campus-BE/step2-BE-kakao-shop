package com.example.kakao.product;


import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final FakeStore fakeStore;

    // page에 따라 상품들 가져와 DTO 생성하기(전체 상품 조회)
    public List<ProductResponse.FindAllDTO> findAllProducts(int page) {
    	
    	List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
    	return productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
    	
    }

    // 상품 id를 통해 상품 가져오기
    public Product findProductById(int id) {
    	
    	return fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    // 상품을 통해 옵션 가져오기
    public List<Option> findOptionsByProduct(Product product) {
    	
        return fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
    }

    // 상품과 옵션을 통해 DTO 생성하기(개별 상품 조회)
    public ProductResponse.FindByIdDTO convertFindByIdDTO(Product product, List<Option> options) {
    	
        return new ProductResponse.FindByIdDTO(product, options);
    }
}
