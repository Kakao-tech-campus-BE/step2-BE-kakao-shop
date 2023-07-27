package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final FakeStore fakeStore;
    private final ProductService productService;
    private final GlobalExceptionHandler globalExceptionHandler;

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,HttpServletRequest request) {
        // 1. 더미데이터 가져와서 페이징하기
        //List<Product> productList = fakeStore.getProductList().stream().skip(page*9).limit(9).collect(Collectors.toList());
        int size = 9;

        try {
            Page<Product> productList = productService.findAll(page, size);
            // 2. DTO 변환
            Page<ProductResponse.FindAllDTO> responseDTOs =  productList.map(ProductResponse.FindAllDTO::new);

            // 3. 공통 응답 DTO 만들기
            return ResponseEntity.ok(ApiUtils.success(responseDTOs));
        }catch(RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id,HttpServletRequest request) {
        // 1. 더미데이터 가져와서 상품 찾기
        //Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        //        if(product == null){
//            Exception404 ex = new Exception404("해당 상품을 찾을 수 없습니다:"+id);
//            return new ResponseEntity<>(
//                    ex.body(),
//                    ex.status()
//            );
//        }
        // 2. 더미데이터 가져와서 해당 상품에 옵션 찾기
//        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());


        try{
            Product product = productService.findById(id);
            List<Option> optionList = productService.findOptionByProductId(product.getId());
            ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);

            // 4. 공통 응답 DTO 만들기
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch(RuntimeException e){
            return globalExceptionHandler.handle(e,request);
        }


//

        // 3. DTO 변환



    }
}