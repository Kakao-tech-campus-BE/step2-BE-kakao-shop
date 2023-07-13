package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartReqAddDTO;
import com.example.kakaoshop.cart.request.CartReqUpdateDTO;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    // 카트에 요청 데이터(requestDTO) 추가
    @PostMapping("/carts/add")
    public ResponseEntity<?> add(@RequestBody CartReqAddDTO requestDTO) {

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 카트(장바구니) 조회
    // CartRespFindAllDTO <- ProductDTO <- CartItemDTO <- ProductOptionDTO
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
                                .id(2)
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

        // 카트 조회했을 때 응답 데이터 생성
        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 카트 업데이트
    // CartRespUpdateDTO <- CartDTO
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody List<CartReqUpdateDTO> requestDTO) {

        // 카트 리스트 만들기
        List<CartDTO> cartDTOList = new ArrayList<>();

        // 카트에 아이템 담기
        cartDTOList.add(CartDTO.builder()
                    .cartId(4)
                    .optionId(1)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build());

        cartDTOList.add(CartDTO.builder()
                    .cartId(5)
                    .optionId(2)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build());

        // 카트 업데이트 했을 때 응답 데이터 생성
        CartRespUpdateDTO responseDTO = new CartRespUpdateDTO(cartDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
