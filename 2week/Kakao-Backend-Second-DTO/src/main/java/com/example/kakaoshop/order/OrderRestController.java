package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.item.response.OptionDTO;
import com.example.kakaoshop.order.item.response.OrderResponseDTO;
import com.example.kakaoshop.order.item.response.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderRestController {

    @PostMapping("/orders/save")
    // @PostMapping("/orders")
    public ResponseEntity<ApiUtils.ApiResult<OrderResponseDTO>> saveOrder(){

        List<OptionDTO> items = new ArrayList<>();

        OptionDTO optionDTO1 = OptionDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OptionDTO optionDTO2 = OptionDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        items.add(optionDTO1);
        items.add(optionDTO2);

        ProductDTO productDTO = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(items)
                .build();

        Integer totalPrice = optionDTO1.getPrice() + optionDTO2.getPrice();

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .id(2L)
                .products(productDTO)
                .totalPrice(totalPrice)
                .build();

        return new ResponseEntity<>(ApiUtils.success(orderResponseDTO), HttpStatus.OK);

    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable Long id){

        List<OptionDTO> options = new ArrayList<>();

        OptionDTO optionDTO1 = OptionDTO.builder()
                .id(4L)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OptionDTO optionDTO2 = OptionDTO.builder()
                .id(5L)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        options.add(optionDTO1);
        options.add(optionDTO2);

        ProductDTO productDTO = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(options)
                .build();

        Integer totalPrice = optionDTO1.getPrice() + optionDTO2.getPrice();

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .id(1L)
                .products(productDTO)
                .totalPrice(totalPrice)
                .build();

        return new ResponseEntity<>(ApiUtils.success(orderResponseDTO),HttpStatus.OK);
    }


}
