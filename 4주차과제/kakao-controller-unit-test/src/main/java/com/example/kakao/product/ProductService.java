package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {


    private ProductJPARepository productJPARepository;
    private OptionJPARepository optionJPARepository;



    public List<ProductResponse.FindAllDTO> findAll(int page){

        // 1. 데이터 9개씩 페이징
        List<Product> products = productJPARepository.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());

        // 2. DTO 변환
        List<ProductResponse.FindAllDTO> responseDTOS =
                products.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

        return responseDTOS;


    }

    public Product findById(int id){
        // 1. 더미데이터 가져와서 상품 찾기
        Product product = productJPARepository.findById(id).orElseThrow(
                ()-> new Exception404("해당 상품을 찾을 수 없습니다: "+id)
        );

        return product;
    }

    public List<Option> mFindByProductId(int id){


        // 해당 상품에 옵션 찾기
        List<Option> options = optionJPARepository.mFindByProductId(id);
        if(options.isEmpty()){
            throw new Exception404("해당 상품의 옵션을 찾을 수 없습니다: "+id );
        }

        return options;
    }

    public ProductResponse.FindByIdDTO toFindByIdDTO(int id){

        // 3. DTO 변환
        return new ProductResponse.FindByIdDTO(findById(id), mFindByProductId(id));

    }

}
