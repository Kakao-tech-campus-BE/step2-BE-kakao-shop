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

    // (기능1) 전체 상품 목록 조회 (페이징 9개씩)
    // /products // products는 인증 필요없음
    @GetMapping("/products") // get이니까 유효성 검사 필요없음
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        // 서비스 호출하고 dto 응답
        List<ProductResponse.FindAllDTO> responseDTOs = productService.findAll(page);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTOs));
     }

    // (기능2) 개별 상품 상세 조회
// /products/{id}
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        ProductResponse.FindByIdDTO responseDTO = productService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // (기능2) 개별 상품 상세 조회 v2
    // /products/{id}/v2
    @GetMapping("/products/{id}/v2")
    public ResponseEntity<?> findByIdv2(@PathVariable int id) {
        ProductResponse.FindByIdDTOv2 responseDTO = productService.findByIdv2(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
} // 2가지를 비교해보자
// 1번으로 봐야할 것은 결과가 나오는지
// 2번은 쿼리를 봐야한다
// 3번은 예외를 봐야함 // 지금은 예외 안보고 결과랑 쿼리만 봄

// v1
// product조회하고 option조회했는데 쓸데없이 join을
// 그냥 product select하고 option select하면 끝임
// 결과 option 5개 있고 product 있음 제대로 나옴
// 쿼리 바꾸자

