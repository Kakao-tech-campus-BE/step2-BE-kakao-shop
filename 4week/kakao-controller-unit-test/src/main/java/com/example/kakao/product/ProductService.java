package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final FakeStore fakeStore;

    public List<ProductResponse.FindAllDTO> findAll(int page) {

        List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());

        List<ProductResponse.FindAllDTO> responseDTOs =
                productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
        return responseDTOs;
    }

    public ProductResponse.FindByIdDTO findById(int id) throws Exception404 {
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        if(product == null){
            throw new Exception404("해당 상품을 찾을 수 없습니다:"+id);
        }

        // 2. 더미데이터 가져와서 해당 상품에 옵션 찾기
        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());

        // 3. DTO 변환
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
        return responseDTO;
    }
}
