package com.example.kakao.product;

import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;

    /* 전체상품조회 Controller method */
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }

    /* 개별상품조회 Product, Option 개별쿼리 Controller method */
    @GetMapping("products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    /* 개별상품조회 한방쿼리 Controller method */
    @GetMapping("/products/{id}/v2")
    public ResponseEntity<?> findByIdv2(@PathVariable int id) {
        ProductResponse.FindByIdDTOv2 responseDTO = productService.findByIdDTOv2(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}