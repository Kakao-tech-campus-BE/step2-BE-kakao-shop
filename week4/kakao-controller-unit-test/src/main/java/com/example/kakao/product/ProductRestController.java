package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 9);
        List<ProductResponse.FindAllDTO> responseDTOs = productService.findAllProducts(pageRequest);

        if (responseDTOs.isEmpty()) {
            return ResponseEntity.ok(ApiUtils.error("페이지 범위를 초과하였습니다.", HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        ProductResponse.FindByIdDTO responseDTO;

        try {
            responseDTO = productService.findProductById(id);
        }
        catch (Exception404 exception404) {
            return ResponseEntity.ok().body(ApiUtils.error("존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND));
        }
        
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}