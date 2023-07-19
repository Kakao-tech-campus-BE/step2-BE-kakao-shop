package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.product.option.Option;

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
	
    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
	// page값이 유효하지 않을 경우 -> GlobalExceptionHandler2에서 예외처리하도록 함
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
        
    	// 서비스 계층에서 더미데이터 가져와 페이징 한 뒤 DTO 변환한다
    	List<ProductResponse.FindAllDTO> responseDTOs = productService.findAllProducts(page);
    	
        
        // 공통 응답 DTO 만들기
    	return ResponseEntity.ok(ApiUtils.success(responseDTOs));
        
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 1. 더미데이터 가져와서 상품 찾기
    	Product product = productService.findProductById(id);
    	
        if(product == null){
            Exception404 ex = new Exception404("해당 상품을 찾을 수 없습니다:"+id);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        // 2. 더미데이터 가져와서 해당 상품에 옵션 찾기
        List<Option> optionList = productService.findOptionsByProduct(product);
       
        // 3. DTO 변환
        ProductResponse.FindByIdDTO responseDTO = productService.convertFindByIdDTO(product, optionList);

        // 4. 공통 응답 DTO 만들기
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}