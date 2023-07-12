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
    public ResponseEntity<?> addCart(@RequestBody List<CartReqAddDTO> cartReqAddDTOList) {
        //req 와서 잘 받아들이는지 출력으로 확인
        cartReqAddDTOList.forEach(System.out::println);

        //db와 연동해서 추가되는 로직 구현해야함

        return ResponseEntity.ok(ApiUtils.success(null));

    }


    @PostMapping("/carts/update")
    public ResponseEntity<?> saveCart(@RequestBody List<CartReqUpdateDTO> cartReqUpdateDTOList) {
        //req 와서 잘 받아들이는지 출력으로 확인
        cartReqUpdateDTOList.forEach(System.out::println);

        //최종적으로 응답될 CartRespUpdateDTO 생성
        CartRespUpdateDTO reponseDTO=null;

        //cartDTO 리스트 선언
        List<CartDTO> cartDTOList= new ArrayList<>();

        //첫번쨰 cartDTO 생성
        cartDTOList.add(CartDTO.builder()
                .cartId(1)
                .optionId(3)
                .optionName("고무장갑 베이지 S(소형) 6팩")
                .quantity(102)
                .price(1009800).build());

        //두번쨰 cartDTO 생성
        cartDTOList.add(CartDTO.builder()
                .cartId(2)
                .optionId(4)
                .optionName("뽑아쓰는 키친타올 130매 12팩")
                .quantity(33)
                .price(557700).build());

        // reponseDTO 최종 생성
        reponseDTO=CartRespUpdateDTO.builder()
                .carts(cartDTOList)
                .totalPrice(1567500).build();


        return ResponseEntity.ok(ApiUtils.success(reponseDTO));

    }

}
