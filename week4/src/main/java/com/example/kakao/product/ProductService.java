package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.kakao.user.UserRequest;
import com.example.kakao.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public List<ProductResponse.FindAllDTO> findAll(int page) {
        try {
            // 1. 데이터 가져와서 페이징하기
            List<Product> productList =
                    productJPARepository.findAll().stream().skip(page * 9).limit(9).collect(Collectors.toList());
            ;

            // 2. DTO 변환
            List<ProductResponse.FindAllDTO> responseDTOs =
                    productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

            return responseDTOs;
        } catch (Exception e){
            throw new Exception500("unknown server error");
        }
    }

    public ProductResponse.FindByIdDTO findById(Integer id){
        // 1. 상품 찾기
        Product product = productJPARepository.findById(id).stream().filter(p -> p.getId() == id).findFirst().orElseThrow(
                () -> new Exception404("상품을 찾을 수 없습니다. : "+id));

        try {
            // 2. 해당 상품의 옵션 찾기
            List<Option> optionList = optionJPARepository.findAll().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());

            // 3. DTO 변환
            ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
            return responseDTO;
        } catch (Exception e){
            throw new Exception500("unknown server error");
        }
    }



}
