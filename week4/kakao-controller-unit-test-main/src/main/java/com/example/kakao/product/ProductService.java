package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ErrorLogJPARepository errorLogJPARepository;

    public List<ProductResponse.FindAllDTO> findAll(int page){
        try{
            List<Product> productList = productJPARepository.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());
            return productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());
        }catch (Exception e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw new Exception500("unknown server error");
        }
    }

    public ProductResponse.FindByIdDTO findById(int id){
        try{
            Product product = productJPARepository.findById(id).orElseThrow(
                    () -> new Exception400("해당 상품을 찾을 수 없습니다. : " + id)
            );
            List<Option> optionList = optionJPARepository.mFindByProductId(product.getId());
            return new ProductResponse.FindByIdDTO(product, optionList);
        }catch (Exception e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw new Exception500("unknown server error");
        }
    }
}
