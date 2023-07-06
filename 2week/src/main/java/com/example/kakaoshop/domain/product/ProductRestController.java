package com.example.kakaoshop.domain.product;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.domain.product.response.ProductOptionDto;
import com.example.kakaoshop.domain.product.response.ProductRespFindAllDto;
import com.example.kakaoshop.domain.product.response.ProductRespFindByIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

  private final ProductMockRepository productRepository;


  // 전체 상품 목록 조회
  @GetMapping("/products")
  public ResponseEntity<?> findAll() {

    List<Product> productList = productRepository.findAll();

    List<ProductRespFindAllDto> responseDTO = productList.stream().map(product ->
      ProductRespFindAllDto.builder()
        .id(product.getId())
        .productName(product.getProductName())
        .description(product.getDescription())
        .price(product.getPrice())
        .build()
    ).collect(Collectors.toList());

    return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
  }

  // 개별 상품 상세 조회
  @GetMapping("/products/{id}")
  public ResponseEntity<?> findById(@PathVariable int id) {
    Product product = productRepository.findById(id);

    ProductRespFindByIdDto responseDTO = ProductRespFindByIdDto.builder()
      .id(product.getId())
      .productName(product.getProductName())
      .description(product.getDescription())
      .image(product.getImage())
      .price(product.getPrice())
      .options(product.getProductOptions().stream().map(option ->
        ProductOptionDto.builder()
          .id(option.getId())
          .optionName(option.getName())
          .price(option.getPrice())
          .build()
      ).collect(Collectors.toList()))
      .build();

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
  }

}
