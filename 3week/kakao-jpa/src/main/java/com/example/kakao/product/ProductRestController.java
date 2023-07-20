package com.example.kakao.product;

import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.ProductOption;
import lombok.RequiredArgsConstructor;
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

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
        // 수정한 부분 : 페이징 처리를 위해 개선
        int pageSize = 9;
        int startIndex = page * pageSize;

        // 1. 더미데이터 가져와서 페이징하기 -> 여기도 수정한 부분에 따라서 코드 수정
        List<Product> productList = fakeStore.getProductList().stream().skip(startIndex).limit(pageSize).collect(Collectors.toList());

        // 2. DTO 변환
        List<ProductResponse.FindAllDTO> responseDTOs =
                productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

        // 3. 공통 응답 DTO 만들기
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 1. 더미데이터 가져와서 상품 찾기
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        // 추가한 부분 : 상품이 없는 경우 404 Not Found 반환
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. 더미데이터 가져와서 해당 상품에 옵션 찾기
        List<ProductOption> productOptionList = fakeStore.getProductOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());

        // 3. DTO 변환
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, productOptionList);

        // 4. 공통 응답 DTO 만들기
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}