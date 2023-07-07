package com.example.kakaoshop.cart;


import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartItemAddDTO;
import com.example.kakaoshop.cart.request.CartItemUpdateDTO;
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

    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {

        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

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

        List<ProductDTO> productDTOList = new ArrayList<>();

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

    @PostMapping("/carts/add")
    public ResponseEntity<?> addToCart(@RequestBody List<CartItemAddDTO> cartItems) {

        return ResponseEntity.ok(ApiUtils.success("장바구니에 아이템이 성공적으로 추가되었습니다."));
    }
    @PostMapping("/carts/update")
    public ResponseEntity<?> updateCart(@RequestBody List<CartItemUpdateDTO> cartUpdates) {
        // 임시 응답 리스트 생성
        List<CartItemResponseDTO> cartResponse = new ArrayList<>();
        int totalPrice = 0;

        for (CartItemUpdateDTO update : cartUpdates) {
            CartItemResponseDTO updatedItem;
            if (update.getCartId() == 4) {
                updatedItem = CartItemResponseDTO.builder()
                        .cartId(4)
                        .optionId(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(update.getQuantity())
                        .price(10000 * update.getQuantity())
                        .build();
            } else {
                updatedItem = CartItemResponseDTO.builder()
                        .cartId(5)
                        .optionId(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(update.getQuantity())
                        .price(10900 * update.getQuantity())
                        .build();
            }

            cartResponse.add(updatedItem);
            totalPrice += updatedItem.getPrice();
        }

        CartUpdateResponseDTO responseDTO = CartUpdateResponseDTO.builder()
                .carts(cartResponse)
                .totalPrice(totalPrice)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}