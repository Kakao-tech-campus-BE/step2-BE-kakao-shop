package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.CartRequestDTO;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartUpdateRespDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    
    @PostMapping("/carts/add")
    public ResponseEntity<?> addToCart(@RequestBody List<CartRequestDTO.AddDTO> cartRequestDTO) {
        
    	// DTO에서 정보 추출
    	// 정보를 레포지토리를 통해 엔티티에 저장

        // 성공적인 응답 반환
        return ResponseEntity.ok(ApiUtils.success(null));
    }
    
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody CartRequestDTO.UpdateDTO[] requests) {
        
        CartItemDTO cartItem1 = CartItemDTO.builder()
                .id(4)// requests[0].getCartId().intValue()
                .option(ProductOptionDTO.builder()
                        .id(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .price(10000)
                        .build())
                .quantity(10)// requests[0].getQuantity()
                .price(10 * 10000)
                .build();

        CartItemDTO cartItem2 = CartItemDTO.builder()
                .id(5)// requests[1].getCartId().intValue()
                .option(ProductOptionDTO.builder()
                        .id(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .price(10900)
                        .build())
                .quantity(10)// requests[1].getQuantity()
                .price(10 * 10900)
                .build();
        
        CartUpdateRespDTO responseDTO = CartUpdateRespDTO.builder()
        		.carts(Arrays.asList(cartItem1, cartItem2))
        		.totalPrice(cartItem1.getPrice() + cartItem2.getPrice())
        		.build();
        
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
