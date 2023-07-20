package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderItemDTO;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderRestController {

  OrderRespFindByIdDTO responseDTO = null; // 강의 시간에 예시로 보여주셨던 코드에서 이러한 형태로 되어있어서, 작성해보았습니다!
  // *참고* ProductRestController.java의 57~58 line
  @PostMapping("/orders/save")
  public ResponseEntity<?> saveOrder() {
    List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
    List<ProductDTO> productDTOList = new ArrayList<>();

    ProductDTO productDTO1 = ProductDTO.builder()
        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
        .items(orderItemDTOList)
        .build();
    productDTOList.add(productDTO1);

    OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
        .id(4)
        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
        .quantity(10)
        .price(100000)
        .build();
    orderItemDTOList.add(orderItemDTO1);

    OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
        .id(5)
        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
        .quantity(10)
        .price(109000)
        .build();
    orderItemDTOList.add(orderItemDTO2);

    responseDTO = new OrderRespFindByIdDTO(1, productDTOList, 209000);

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
  }

  @GetMapping("/orders/{id}")
  public ResponseEntity<?> findByIdOrder(@PathVariable int id) {

    OrderRespFindByIdDTO responseDTO = null;

    if (id == 1) {
      List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

      OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
          .id(4)
          .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
          .quantity(10)
          .price(100000)
          .build();
      orderItemDTOList.add(orderItemDTO1);

      OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
          .id(5)
          .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
          .quantity(10)
          .price(109000)
          .build();
      orderItemDTOList.add(orderItemDTO2);

      List<ProductDTO> productDTOList = new ArrayList<>();

      ProductDTO productDTO1 = ProductDTO.builder()
          .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
          .items(orderItemDTOList)
          .build();
      productDTOList.add(productDTO1);

      responseDTO = new OrderRespFindByIdDTO(id, productDTOList, 209000);

    } else {
      return ResponseEntity.badRequest()
          .body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
    }

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
  }
}
