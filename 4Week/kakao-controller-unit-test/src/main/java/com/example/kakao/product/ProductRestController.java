package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductRestController {
    private final FakeStore fakeStore;
    private final ProductService productService;
    private final GlobalExceptionHandler globalExceptionHandler;

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        try {
            List<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(productService.findAllProduct(page));
            return ResponseEntity.ok(ApiUtils.success(responseDTOs));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, HttpServletRequest request) {
        Product product;
        List<Option> optionList = null;

        try {
            product = productService.findByIdProduct(id);
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }

        try {
            optionList = productService.findOptionByProductID(product.getId());
        }catch (RuntimeException e){
            globalExceptionHandler.handle(e, request);
        }

        ProductResponse.FindByIdDTO responseDTO = productService.findByIdDTO(product, optionList);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}