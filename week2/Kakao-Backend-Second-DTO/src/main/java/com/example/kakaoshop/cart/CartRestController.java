package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CartRestController {

    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemDTOList.add(cartItemDTO1);

        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartItemDTOList.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    /*
        추가되어야 할 API:
        - 장바구니 담기
        /carts/add
        - 주문(장바구니 수정)
        /carts/update
    */
    @PostMapping("/carts/add")
    public ResponseEntity<?> addItem() {
        /*
            현재 API문서 상에서는 optionId, quantity를 Request Body로 전달한다.
            ProductOtpionDTO 에서 option의 id를 받아온다.
            CartitemDTO에서 option의 quantity를 받아온다.
        */

        // Request Body return
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("response", null);
        response.put("error", null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/carts/update")
    public ResponseEntity<?> updateItem() {
        /*
            더미 데이터를 이용하는 Mock API입니다.
            API문서의 출력과 동일한 Response Body를 구현하기 위해
            CartUpdateDTO.java, CartUpdateItemDTO.java를 생성했습니다.
            해당 DTO를 이용하여 API문서와 동일한 형태의 json파일을 출력하지만,
            파일 이름이 적절하지 않은 것 같아 수정이 필요해 보입니다.
        */

        // carts항목의 더미 데이터 생성
        List<CartUpdateItemDTO> carts = new ArrayList<>();
        CartUpdateItemDTO cartUpdateItemDTO01 = CartUpdateItemDTO.builder()
                .quantity(10)
                .price(100000)
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .build();
        CartUpdateItemDTO cartUpdateItemDTO02 = CartUpdateItemDTO.builder()
                .quantity(10)
                .price(100000)
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .build();
        carts.add(cartUpdateItemDTO01);
        carts.add(cartUpdateItemDTO02);

        // Response Body 생성
        CartUpdateDTO responseDTO = CartUpdateDTO.builder()
                .carts(carts)
                .build();

        // Response Body 반환
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
