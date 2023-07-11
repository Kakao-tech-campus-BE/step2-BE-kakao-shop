package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.CartRestController;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    //주문 결과 확인
    @GetMapping ("/{id}") // /orders/{id}
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 주문을 담을 DTO 생성
        OrderRespDTO responseDTO = null;

        if(id == 1) {
            //ItemInfo 담을 리스트 생성
            List<ItemInfoDTO> itemInfoDTOList = new ArrayList<>();
            //ItemInfo 리스트에 담기
            ItemInfoDTO itemInfoDTO1 = ItemInfoDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            itemInfoDTOList.add(itemInfoDTO1);

            ItemInfoDTO itemInfoDTO2 = ItemInfoDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            itemInfoDTOList.add(itemInfoDTO2);

            //ProductItem 리스트 만들기
            List<ProductItemDTO> productItemDTOList = new ArrayList<>();
            ProductItemDTO productItemDTO1 = ProductItemDTO.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .items(itemInfoDTOList)
                    .build();
            //ProductItem 리스트에 담기
            productItemDTOList.add(productItemDTO1);

            //응답할 dto 생성
            responseDTO = OrderRespDTO.builder()
                    .id(2)
                    .products(productItemDTOList)
                    .totalPrice(209000)
                    .build();
        }
        else { //id가 1이 아닌 경우
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    //결제하기(주문 인서트)
    @PostMapping("/save") //  /orders/save
    public ResponseEntity<?> saveOrder() {
        OrderRespDTO responseDTO = null;

        //ItemInfo 담을 리스트 생성
        List<ItemInfoDTO> itemInfoDTOList = new ArrayList<>();
        //ItemInfo 리스트에 담기
        ItemInfoDTO itemInfoDTO1 = ItemInfoDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemInfoDTOList.add(itemInfoDTO1);

        ItemInfoDTO itemInfoDTO2 = ItemInfoDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemInfoDTOList.add(itemInfoDTO2);

        //ProductItem 리스트 만들기
        List<ProductItemDTO> productItemDTOList = new ArrayList<>();
        ProductItemDTO productItemDTO1 = ProductItemDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemInfoDTOList)
                .build();
        //ProductItem 리스트에 담기
        productItemDTOList.add(productItemDTO1);

        //응답할 dto 생성
        responseDTO = OrderRespDTO.builder()
                .id(2)
                .products(productItemDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    //주문 확인, 결제하기에서 공통으로 쓰이는 DTO
    @Data
    static class OrderRespDTO{
        private int id;
        private List<ProductItemDTO> products;
        private int totalPrice;

        @Builder
        public OrderRespDTO(int id, List<ProductItemDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }
    @Data
    static class ProductItemDTO{
        private String productName;
        private List<ItemInfoDTO> items;

        @Builder
        public ProductItemDTO(String productName, List<ItemInfoDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }
    @Data
    static class ItemInfoDTO{
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public ItemInfoDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
