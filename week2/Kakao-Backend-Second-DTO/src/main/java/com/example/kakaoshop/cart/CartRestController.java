package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.request.AddOptionRequestDTO;
import com.example.kakaoshop.cart.request.UpdateOptionRequestDTO;
import com.example.kakaoshop.cart.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartRestController {
    @PostMapping("/update")
    public ResponseEntity<?> updateOption(@RequestBody List<UpdateOptionRequestDTO> updateDTOList) {
        List<UpdateOptionResponseDTO.Item> itemList = new ArrayList<>();
        itemList.add(
                UpdateOptionResponseDTO.Item.builder()
                        .cartId(4)
                        .optionId(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build());

        itemList.add(
                UpdateOptionResponseDTO.Item.builder()
                        .cartId(5)
                        .optionId(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build());

        UpdateOptionResponseDTO.Response responseDTO = UpdateOptionResponseDTO.Response.builder()
                .carts(itemList)
                .totalPrice(209000)
                .build();
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOption(@RequestBody List<AddOptionRequestDTO.Request> requests) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<FindAllResponseDTO.Item> items = new ArrayList<>();

        FindAllResponseDTO.Option option1 = FindAllResponseDTO.Option.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build();

        // 카트 아이템 리스트에 담기
        FindAllResponseDTO.Item cartItemDTO1 = FindAllResponseDTO.Item.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .option(option1)
                .build();
        items.add(cartItemDTO1);


        FindAllResponseDTO.Option option2 = FindAllResponseDTO.Option.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .price(10900)
                .build();
        FindAllResponseDTO.Item cartItemDTO2 = FindAllResponseDTO.Item.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .option(option2)
                .build();
        items.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<FindAllResponseDTO.Product> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                FindAllResponseDTO.Product.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .carts(items)
                        .build());

        FindAllResponseDTO.Response responseDTO = FindAllResponseDTO.Response.builder()
                .products(productDTOList)
                .totalPrice(104500)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
