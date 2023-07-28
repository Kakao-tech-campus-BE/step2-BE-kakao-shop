package com.example.kakao.product.web.controller;

import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.product.domain.service.ProductService;
import com.example.kakao.product.web.response.ProductReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<List<ProductReponse.ProductFindAllResponse>>> findAll(@RequestParam(value = "page", defaultValue = "0") Long page) {
        return ResponseEntity.ok().body(ApiUtils.success(productService.getPosts(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiUtils.ApiResult<ProductReponse.ProductFindByIdResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiUtils.success(productService.getPostByPostId(id)));
    }
}


