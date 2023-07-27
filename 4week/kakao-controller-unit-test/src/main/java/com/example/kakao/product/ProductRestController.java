package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception204;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.product.option.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    // controller에 유효성 검사 넣기
    // error 잡기

    @Autowired
    private final FakeStore fakeStore;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionService productOptionService;

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {

        if (page < 0) {
            Exception404 ex = new Exception404("페이지를 찾을 수 없습니다.:"+page);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        // 1. 더미데이터 가져와서 페이징하기
        List<Product> productList = productService.findAll().stream().skip(page*9).limit(9).collect(Collectors.toList());


        if (page > productList.size()) {
            Exception404 ex = new Exception404("페이지를 찾을 수 없습니다.:"+page);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        if (productList.isEmpty()) {
            Exception204 ex = new Exception204("데이터가 없습니다.:"+page);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        // 2. DTO 변환
        List<ProductResponse.FindAllDTO> responseDTOs =
                productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

        // 3. 공통 응답 DTO 만들기
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        if (id <= 0 || id >= Math.pow(2,32)) {
            Exception404 ex = new Exception404("유효하지 않은 상품 ID입니다. ID: "+id);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        // 1. 더미데이터 가져와서 상품 찾기
        Product product = productService.findById(id);

        if(product == null){
            Exception404 ex = new Exception404("해당 상품을 찾을 수 없습니다.:"+id);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        // 2. 더미데이터 가져와서 해당 상품에 옵션 찾기
        //List<ProductOption> productOptionList = fakeStore.getProductOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
        List<ProductOption> productOptionList = productOptionService.findAll().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());

        // 3. DTO 변환
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, productOptionList);

        // 4. 공통 응답 DTO 만들기
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}