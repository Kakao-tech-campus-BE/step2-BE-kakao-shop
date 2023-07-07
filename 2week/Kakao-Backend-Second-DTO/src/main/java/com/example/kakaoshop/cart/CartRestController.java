package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
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
    // 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCart(@RequestBody List<CartItemDTO.AddCartItemDTO> addToCartRequests) {
        System.out.println("============ /carts/add 실행됨 ===============");

        for (CartItemDTO.AddCartItemDTO request : addToCartRequests) {
            Long optionId = request.getOptionId();
            int quantity = request.getQuantity();

            System.out.println("optionId : " + optionId);
            System.out.println("quantity : " + quantity);
        }


        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 장바구니 수정
    @PostMapping("/carts/update")
    public ResponseEntity<?> updateCart(@RequestBody List<CartUpdateDTO.UpdateDTO> updateToCartRequest)
    {
        System.out.println("============= /carts/update 실행됨 ================="); // 잘 실행되는지 체크

        for (CartUpdateDTO.UpdateDTO request : updateToCartRequest) {
            Long cartId = request.getCartId();
            int quantity = request.getQuantity();

            System.out.println("cartId : " + cartId);
            System.out.println("quantity : " + quantity);

        }
        // API 와 똑같이 나오도록 코드 작성
        List<CartUpdateDTO> cartUpdateDTO = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartUpdateDTO cartUpdateDTO1 = CartUpdateDTO.builder()
                .cartId(4L)
                .optionId(1L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(50000)
                .build();
        cartUpdateDTO.add(cartUpdateDTO1);

        CartUpdateDTO cartUpdateDTO2 = CartUpdateDTO.builder()
                .cartId(5L)
                .optionId(2L)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .quantity(10)
                .price(54500)
                .build();

        cartUpdateDTO.add(cartUpdateDTO2);

        // CartRespUpdateDTO 생성
        CartRespUpdateDTO responseDTO = new CartRespUpdateDTO(cartUpdateDTO,  209000);


        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemDTO> cartItemDTO = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4L)
                .quantity(5)
                .price(50000)
                .build();
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                                .id(1L)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartItemDTO.add(cartItemDTO1);

        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5L)
                .quantity(5)
                .price(54500)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                                .id(1L)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartItemDTO.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1L)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTO)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
