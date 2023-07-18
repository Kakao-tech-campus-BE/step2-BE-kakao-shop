package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionRequest;
import com.example.kakao.product.option.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final FakeStore fakeStore;
    private final ProductService productService;
    private final OptionService optionService;
    private final GetProductUsecase getProductUsecase;

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
        // 더미 데이터 저장
        List<ProductRequest.Insert> requests = fakeStore.getProductList().stream().map(product -> ProductRequest.Insert.builder()
                        .name(product.getProductName())
                        .description(product.getDescription())
                        .image(product.getImage())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
        productService.saveAll(requests);

        PageRequest pageRequest = PageRequest.of(page, 9);
        List<ProductResponse.FindAllDTO> responseDTOs = productService.getProducts(pageRequest);

        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 더미 데이터 저장
        List<ProductRequest.Insert> requests = fakeStore.getProductList().stream().map(product -> ProductRequest.Insert.builder()
                        .name(product.getProductName())
                        .description(product.getDescription())
                        .image(product.getImage())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
        productService.saveAll(requests);

        List<OptionRequest.Insert> optionRequests = fakeStore.getOptionList().stream().map(option -> OptionRequest.Insert.builder()
                        .product(option.getProduct())
                        .name(option.getOptionName())
                        .price(option.getPrice())
                        .build())
                .collect(Collectors.toList());
        optionService.saveAll(optionRequests);

        ProductResponse.FindByIdDTO responseDTO = getProductUsecase.execute(id);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}