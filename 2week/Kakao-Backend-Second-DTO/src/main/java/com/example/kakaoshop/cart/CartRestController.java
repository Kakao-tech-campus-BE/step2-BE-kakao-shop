package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartRestController {
    // 강사님 작성
    @GetMapping()
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

    // update이므로 patch를 사용하는 것이 맞다고 판단되지만 명세서에 적힌대로 구현했습니다.
    @PostMapping("/add")
    public ResponseEntity<?> addAll(List<CartItemDTO> cartItemList) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 장바구니에서 주문한 상품들을 삭제하므로 delete를 사용하는 것이 맞다고 판단되지만 명세서에 적힌대로 구현했습니다.
    // product id, name 등의 정보가 함께 나오도록 하는 것이 맞다고 판단되지만 명세서에 적힌대로 구현했습니다.
    // url은 /carts/update이지만 주문 버튼 클릭 시 주문하게 된 장바구니 속 상품(옵션)을 response로 return하도록
    // 동작하기 때문에 orderItemDTO를 사용하였습니다.
    @PostMapping("update")
    public ResponseEntity<?> updateAll() {
        // 주문 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        // 주문 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .option(ProductOptionDTO.builder()
                        .id(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .price(10000)
                        .build())
                .quantity(10)
                .price(100000)
                .build();

        orderItemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .option(ProductOptionDTO.builder()
                        .id(2)
                        .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                        .price(10900)
                        .build())
                .quantity(10)
                .price(109000)
                .build();

        orderItemDTOList.add(orderItemDTO2);

        CartRespUpdateAllDTO responseDTO = new CartRespUpdateAllDTO(orderItemDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
