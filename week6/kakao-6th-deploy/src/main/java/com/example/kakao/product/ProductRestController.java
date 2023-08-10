package com.example.kakao.product;

import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;    // 의존시키기

    // (기능1) 전체 상품 목록 조회 (페이징 9개씩)
    // /products
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(page);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTOs));
    }

    // (기능2) 개별 상품 상세 조회
    // /products/{id}
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable @Min(1) int id) {
        ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

}