package com.example.kakaoshop.order;

@RestController
public class OrderRestController {
    @PostMapping("/orders/save")
    public ResponseEntity<?> save() {
        List<ProductOptionDTO> productOptionDTOList = new ArrayList<>();
        ProductOptionDTO option1 = ProductOptionDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        productOptionDTOList.add(option1);
        ProductOptionDTO option2 = ProductOptionDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        productOptionDTOList.add(option2);
        ProductDTO product1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(optionDTOList)
                .build();
        OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);
        return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        List<ProductOptionDTO> productOptionDTOList = new ArrayList<>();
        ProductOptionDTO option1 = ProductOptionDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        ProductOptionDTO option2 = ProductOptionDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        productOptionDTOList.add(option1);
        productOptionDTOList.add(option2);
        ProductDTO product1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(optionDTOList)
                .build();
        OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);

        return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
    }
}
