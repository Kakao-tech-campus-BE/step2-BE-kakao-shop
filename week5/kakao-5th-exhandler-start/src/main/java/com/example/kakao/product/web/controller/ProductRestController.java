package com.example.kakao.product.web.controller;

import com.example.kakao._core.utils.ApiResult;
import com.example.kakao.product.domain.service.ProductService;
import com.example.kakao.product.web.response.ProductReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    public ApiResult<List<ProductReponse.ProductFindAllResponse>> findAll(@RequestParam(value = "page", defaultValue = "0") int page) {
        return ApiResult.success(productService.getPosts(page));
    }

    @GetMapping("/{id}")
    public ApiResult<ProductReponse.ProductFindByIdResponse> findById(@PathVariable Long id) {
        return ApiResult.success(productService.getPostByPostId(id));
    }
}


