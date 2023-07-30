package com.example.kakao.product.web.controller;

import com.example.kakao._core.utils.ApiResult;
import com.example.kakao._core.utils.ResponseBody;
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
    public ResponseEntity<ApiResult<List<ProductReponse.ProductFindAllResponse>>> findAll(@RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok().body(ResponseBody.success(productService.getPosts(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<ProductReponse.ProductFindByIdResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseBody.success(productService.getPostByPostId(id)));
    }
}


