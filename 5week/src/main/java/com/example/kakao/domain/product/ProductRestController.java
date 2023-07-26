package com.example.kakao.domain.product;

import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    // (기능1) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("")
    public ResponseEntity<ApiResponse> findAll(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(ApiUtils.success( productService.findAll(page) ));
    }

    // (기능2) 개별 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable int id) {
        return ResponseEntity.ok(ApiUtils.success( productService.findById(id) ));
    }

}