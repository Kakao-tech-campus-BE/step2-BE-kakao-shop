package com.example.kakaoshop.product.web.controller;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.product.domain.service.ProductService;
import com.example.kakaoshop.product.web.response.ProductReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

//    @GetMapping("/products")
//    public ResponseEntity<ApiUtils.ApiResult<List<ProductReponse.ProductFindAllResponse>>> findAll(@RequestParam(value = "page", defaultValue = "0") int page) {
//
//        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
//    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiUtils.ApiResult<ProductReponse.ProductFindByIdResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiUtils.success(productService.getPostByPostId(id)));
    }
}


