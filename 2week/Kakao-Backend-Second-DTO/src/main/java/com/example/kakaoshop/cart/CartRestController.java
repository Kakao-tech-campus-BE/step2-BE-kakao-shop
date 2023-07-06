package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartRequestDTO;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    @GetMapping("/carts")
    public ResponseEntity<?> findAllCarts() {
        // 카트 아이템 리스트 만들기
        List<CartOptionDTO> cartOptionDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartOptionDTO cartOptionDTO1 = CartOptionDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        cartOptionDTO1.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                                .price(10000)
                                .build());
        cartOptionDTOList.add(cartOptionDTO1);

        CartOptionDTO cartOptionDTO2 = CartOptionDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        cartOptionDTO2.setOption(ProductOptionDTO.builder()
                                .id(1)
                                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                                .price(10900)
                                .build());
        cartOptionDTOList.add(cartOptionDTO2);

        // productDTO 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartOptionDTOList)
                        .build()
        );

        CartResponseFindAllDTO responseDTO = new CartResponseFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

     @PostMapping("/carts/add")
//    @PostMapping("/carts")
    public ResponseEntity<Void> saveCarts(CartRequestDTO cartRequestDTO){
        return new ResponseEntity(ApiUtils.success(null), HttpStatus.OK);
    }


//     @PutMapping("/carts")
    @PostMapping("/carts/update")
    public ResponseEntity<ApiUtils.ApiResult<CartResponseUpdateDTO>> updateCarts(CartRequestDTO cartRequestDTO){

        List<CartProductDTO> cartProductDTOS = new ArrayList<>();

        CartProductDTO cartProductDTO1 = CartProductDTO.builder()
                .cartId(4L)
                .optionId(1L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        CartProductDTO cartProductDTO2 = CartProductDTO.builder()
                .cartId(5L)
                .optionId(2L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        cartProductDTOS.add(cartProductDTO1);
        cartProductDTOS.add(cartProductDTO2);

        Integer totalPrice = cartProductDTO2.getPrice() + cartProductDTO2.getPrice();

        CartResponseUpdateDTO cartResponseUpdateDTO = new CartResponseUpdateDTO(cartProductDTOS,totalPrice);

        return new ResponseEntity<>(ApiUtils.success(cartResponseUpdateDTO),HttpStatus.OK);

    }
}
