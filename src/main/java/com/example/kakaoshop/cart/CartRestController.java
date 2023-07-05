package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {

    // Mock DB Data
    // CartItem(ProductOption + Quantity) List 가 실제 DB에 저장되고,
    // 조회 시 Product 를 Join 해서 JSON 형식으로 반환해주는 방식.
    private List<CartItemDTO> cartItemDTOList = new ArrayList<>();

    // cart 비운다.
    public void clearMockCartData() {
        cartItemDTOList.clear();
    }


    // 장바구니 조회
    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {

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

    // 장바구니 담기
    @PostMapping("/carts")
    public ResponseEntity<?> addCartItem(@RequestParam("product-option-id") int productOptionId,
                                         @RequestParam("quantity") int quantity) {

        // Mock Insert
        // 상품 가격 확인 등 ..

        if(!cartItemDTOList.isEmpty()) cartItemDTOList.clear();

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


        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 장바구니 옵션 수량 변경
    // + 또는 - 버튼 하나를 누를때마다, 해당 옵션의 수량이 변경됨.
    // 추후 +,- 버튼 이외에 직접 숫자를 입력하는 경우도 있을수 있으므로, 입력값에서 수량을 숫자로 받는다.
    // 수량이 0이 되면, 장바구니에서 삭제됨.
    @PatchMapping("/carts")
    public ResponseEntity<?> updateCartItem(@RequestParam("cart-item-id") int cartItemId,
                                            @RequestParam("quantity") int quantity) {

        // Mock Update

        // 1. 장바구니에서 해당 옵션 찾기 -> 없으면 예외처리
        // 2. 수량 변경
        // 3. 수량이 0이면 장바구니에서 삭제

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 장바구니 옵션 1개 삭제
    @DeleteMapping("/carts/{cart-item-id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cart-item-id") int cartItemId) {

        // Mock Delete
        cartItemDTOList.removeIf(cartItemDTO -> cartItemDTO.getId() == cartItemId);

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 장바구니 전체 비우기
    @DeleteMapping("/carts")
    public ResponseEntity<?> deleteCart(@RequestParam("cart-item-id") int cartItemId) {

        // Mock Delete
        cartItemDTOList.clear();

        return ResponseEntity.ok(ApiUtils.success(null));
    }

}
