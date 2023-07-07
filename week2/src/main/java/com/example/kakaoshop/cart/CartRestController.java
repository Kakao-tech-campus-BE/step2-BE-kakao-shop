package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartRestController {
    //장바구니 조회
    @GetMapping() // /carts
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
    //장바구니 담기
    @PostMapping("/add") //  /carts/add
    public ResponseEntity<?> addCart(@RequestBody List<CartDTO> request) { //리스트 형식으로 요청받음
        return ResponseEntity.ok(ApiUtils.success(null));
    }
    @Data
    static class CartDTO{
        private int optionId;
        private int quantity;

        @Builder
        public CartDTO(int optionId, int quantity) {
            this.optionId = optionId;
            this.quantity = quantity;
        }
    }
    //장바구니 수정
    @PostMapping("/update") //  /carts/update
    public ResponseEntity<?> updateCart(@RequestBody List<CartUpdateRequestDTO> request) {
        // 카트 Info 리스트 만들기
        List<CartInfoDTO> cartInfoDTOList = new ArrayList<>();

        // 카트 Info 리스트에 담기
        CartInfoDTO cartInfoDTO1 = CartInfoDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartInfoDTOList.add(cartInfoDTO1);

        CartInfoDTO cartInfoDTO2 = CartInfoDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartInfoDTOList.add(cartInfoDTO2);

        //responseDTO 만들기
        CartUpdateResponseDTO responseDTO = CartUpdateResponseDTO.builder()
                .carts(cartInfoDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @Data
    static class CartUpdateRequestDTO{
        private int cartId;
        private int quantity;

        @Builder
        public CartUpdateRequestDTO(int cartId, int quantity) {
            this.cartId = cartId;
            this.quantity = quantity;
        }
    }

    @Data
    static class CartUpdateResponseDTO{
        private List<CartInfoDTO> carts;
        private int totalPrice;

        @Builder
        public CartUpdateResponseDTO(List<CartInfoDTO> carts, int totalPrice) {
            this.carts = carts;
            this.totalPrice = totalPrice;
        }
    }
    @Data
    static class CartInfoDTO{
        private int cartId;
        private int optionId;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public CartInfoDTO(int cartId, int optionId, String optionName, int quantity, int price) {
            this.cartId = cartId;
            this.optionId = optionId;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }

}
