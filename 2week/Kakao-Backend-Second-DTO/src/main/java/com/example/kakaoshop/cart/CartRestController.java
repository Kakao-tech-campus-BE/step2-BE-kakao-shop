package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<?> add(
            @RequestBody List<CartRequest.AddDTO> addDTOList
    ){
        // 사실 여기서 어떻게 토큰을 검증하나 고민을 했는데 생각해보니
        // 굳이 여기서 토큰 검증으로 뭘 자르거나 그러지 않아도 필터 선에서 걸러지기 때문에 무조건 200을 반환해도 좋다.
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/carts/update")
    public ResponseEntity<?> update(
            @RequestBody List<CartRequest.UpdateDTO> updateDTOList
    ){
        CartUpdateRespDTO responseDTO = null;

        // cartId만 추출하여 실제로 존재하는 cart인지 검사. 여기서는 4번과 5번만 가능
        List<Integer> cartIdList = new ArrayList<>();

        for(CartRequest.UpdateDTO updateDTO : updateDTOList){
            cartIdList.add(updateDTO.getCartId());
        }

        if(cartIdList.contains(4) && cartIdList.contains(5)){
            List<CartDTO> cartDTOList = new ArrayList<>();
            cartDTOList.add(
                    CartDTO.builder()
                            .cartId(4)
                            .optionId(1)
                            .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                            .quantity(10)
                            .price(100000)
                            .build()
            );

            cartDTOList.add(
                    CartDTO.builder()
                            .cartId(5)
                            .optionId(2)
                            .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                            .quantity(10)
                            .price(109000)
                            .build()
            );
            responseDTO = new CartUpdateRespDTO(cartDTOList, 209000);
        }
        else if(!cartIdList.contains(4)){
            return ResponseEntity.badRequest().body(ApiUtils.error("장바구니에 없는 상품은 주문할 수 없습니다 : " + 4, HttpStatus.BAD_REQUEST));
        }
        else{
            return ResponseEntity.badRequest().body(ApiUtils.error("장바구니에 없는 상품은 주문할 수 없습니다 : " + 5, HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
