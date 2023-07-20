package com.example.kakao.product;

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

    private final FakeStore fakeStore;
    private final ProductService productService;
    private final OptionService optionService;

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
        ProductResponse.FindByIdDTO responseDTO = productService.findProductById(id);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}