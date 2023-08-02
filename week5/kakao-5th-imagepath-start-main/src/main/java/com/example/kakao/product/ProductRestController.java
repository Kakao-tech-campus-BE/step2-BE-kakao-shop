package com.example.kakao.product;

import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;

    // (기능1) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // (기능2) 개별 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능2) 개별 상품 상세 조회 v2
    @GetMapping("/{id}/v2")
    public ResponseEntity<?> findByIdv2(@PathVariable int id) {
        ProductResponse.FindByIdDTOv2 responseDTO = productService.findByIdv2(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}