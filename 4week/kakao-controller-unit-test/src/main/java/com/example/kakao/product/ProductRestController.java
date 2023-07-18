package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(ApiUtils.success(productService.findAll(page, 9)));
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {

        // 1. 더미데이터 가져와서 상품 찾기
        try{
            ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch (Exception404 ex){
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        // 4. 공통 응답 DTO 만들기
    }

}