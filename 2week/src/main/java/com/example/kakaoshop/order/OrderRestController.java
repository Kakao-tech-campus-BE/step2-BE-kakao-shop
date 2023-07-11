package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @PostMapping("/payment")
    public ResponseEntity payment(){
        List<OptionDTO> items=new ArrayList<>();
        items.add(
                OptionDTO.builder()
                        .id(4)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build());
        items.add(
                OptionDTO.builder()
                        .id(5)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build());

        List<ProductDTO> products=new ArrayList<>();
        products.add(
                ProductDTO.builder()
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .items(items)
                        .build());
        OrderDTO responseDTO= new OrderDTO(2,products,209000);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable int id){
        List<OrderDetailOptionDTO> items=new ArrayList<>();
        List<OrderDetailProductDTO> products=new ArrayList<>();
        if(id==1){
            items.add(
              OrderDetailOptionDTO.builder()
                      .id(4)
                      .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                      .quantity(10)
                      .price(100000)
                      .build()
            );
            items.add(
                    OrderDetailOptionDTO.builder()
                            .id(5)
                            .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                            .quantity(10)
                            .price(109000)
                            .build()
            );

            products.add(
                    OrderDetailProductDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .items(items)
                            .build()
            );
        }
        OrderDetailDTO responseDTO= new OrderDetailDTO(id,products,209000);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
