package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.cart.Cart;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public List<ProductResponse.FindAllDTO> findAll(int page) {
        try {
            List<Product> productList = productJPARepository.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());;
            List<ProductResponse.FindAllDTO> findAllDTOS =
                    productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
            return findAllDTOS;
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    @Transactional
    public ProductResponse.FindByIdDTO findById(int id) {
        Product product = productJPARepository.findById(id).orElseThrow(() -> new Exception404("해당 상품을 찾을 수 없습니다:"+id));
        List<Option> optionList = optionJPARepository.findByProductId(id);
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
        return responseDTO;
    }
}
