package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
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
    ProductJPARepository productJPARepository;
    OptionJPARepository optionJPARepository;

    public List<ProductResponse.FindAllDTO> findAll(int page){
        List<ProductResponse.FindAllDTO> responseDTO;
        try {
            List<Product> productList = productJPARepository.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());;
            responseDTO = productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
        } catch (Exception e){
            throw new Exception500("서버에서 상품리스트를 불러오는데 실패했습니다.");
        }
        return responseDTO;
    }
    //id에 맞는 상품과 옵션을 찾아 반환
    public ProductResponse.FindByIdDTO findById(int id){
        //상품이 존재하지 않을 경우 Exception404
        Product productDTO = productJPARepository.findById(id).orElseThrow(
                ()-> new Exception404("상품의 페이지가 존재하지 않습니다."));
        //존재할 경우 entity를 dto로 옮겨담아서 리턴한다.
        List<Option> optionListPS = optionJPARepository.findByProductId(productDTO.getId());

        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(productDTO, optionListPS);

        return responseDTO;

    }
}
