package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderNotFoundException;
import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
import com.example.kakaoshop.order.response.OrderRespSaveDTO;
import com.example.kakaoshop.order.response.ProductDTO;
import com.example.kakaoshop.order.response.ProductItemDTO;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
  private <OrderRespDTO> OrderRespDTO getMockOrderDTO(Class<OrderRespDTO> cls)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException,
          IllegalAccessException {
    final List<ProductItemDTO> cartItemDTOList = new ArrayList<>();
    cartItemDTOList.add(
        ProductItemDTO.builder()
            .id(1)
            .quantity(3)
            .price(30000)
            .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
            .build());

    cartItemDTOList.add(
        ProductItemDTO.builder()
            .id(2)
            .quantity(5)
            .price(54500)
            .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
            .build());

    ProductDTO productDTO =
        ProductDTO.builder()
            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
            .items(cartItemDTOList)
            .build();

    final var totalPrice = cartItemDTOList.stream().mapToInt(ProductItemDTO::getPrice).sum();
    return cls.getConstructor(int.class, List.class, int.class)
        .newInstance(1, List.of(productDTO), totalPrice);
  }

  @PostMapping("/orders/save")
  public ResponseEntity<?> save()
      throws InvocationTargetException, NoSuchMethodException, InstantiationException,
          IllegalAccessException {
    return ResponseEntity.ok(ApiUtils.success(getMockOrderDTO(OrderRespSaveDTO.class)));
  }

  @GetMapping("/orders/{id}")
  public ResponseEntity<?> findById(@PathVariable int id)
      throws OrderNotFoundException, InvocationTargetException, NoSuchMethodException,
          InstantiationException, IllegalAccessException {
    if (id == 1)
      return ResponseEntity.ok(ApiUtils.success(getMockOrderDTO(OrderRespFindByIdDTO.class)));
    throw new OrderNotFoundException();
  }
}
