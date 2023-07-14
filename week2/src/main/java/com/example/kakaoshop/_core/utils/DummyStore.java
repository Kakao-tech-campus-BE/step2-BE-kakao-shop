package com.example.kakaoshop._core.utils;

import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartReqRespDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.order.OrderDTO;
import com.example.kakaoshop.product.Product;
import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.product.response.ProductRespFindAllDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.kakaoshop.order.OrderDTO.*;

@Getter
@Component
public class DummyStore {
    private List<ProductDTO> productDTOList = getProductDTOS();
    private List<CartReqRespDTO.CartInfoDTO> cartInfoDTOList = getCartInfoDTOS();
    private List<com.example.kakaoshop.product.response.ProductOptionDTO> optionDTOList =  getProductOptionDTOS();
    private List<OrderDTO.ProductItemDTO> productItemDTOList = getProductItemDTOS();
    private List<ProductRespFindAllDTO> respFindAllDTOList = getProductRespFindAllDTO();
    private List<ProductDTO> getProductDTOS() {
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
        return productDTOList;
    }

    private List<CartReqRespDTO.CartInfoDTO> getCartInfoDTOS() {
        List<CartReqRespDTO.CartInfoDTO> cartInfoDTOList = new ArrayList<>();

        CartReqRespDTO.CartInfoDTO cartInfoDTO1 = CartReqRespDTO.CartInfoDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartInfoDTOList.add(cartInfoDTO1);

        CartReqRespDTO.CartInfoDTO cartInfoDTO2 = CartReqRespDTO.CartInfoDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartInfoDTOList.add(cartInfoDTO2);
        return cartInfoDTOList;
    }
    private static List<com.example.kakaoshop.product.response.ProductOptionDTO> getProductOptionDTOS() {
        List<com.example.kakaoshop.product.response.ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(
                com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                        .id(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .price(10000)
                        .build());

        optionDTOList.add(com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .price(10900)
                .build());

        optionDTOList.add(com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                .id(3)
                .optionName("고무장갑 베이지 S(소형) 6팩")
                .price(9900)
                .build());

        optionDTOList.add(com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                .id(4)
                .optionName("뽑아쓰는 키친타올 130매 12팩")
                .price(16900)
                .build());

        optionDTOList.add(com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                .id(5)
                .optionName("2겹 식빵수세미 6매")
                .price(8900)
                .build());

        optionDTOList.add(com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                .id(6)
                .optionName("22년산 햇단밤 700g(한정판매)")
                .price(9900)
                .build());

        optionDTOList.add(com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                .id(7)
                .optionName("22년산 햇단밤 1kg(한정판매)")
                .price(14500)
                .build());

        optionDTOList.add(com.example.kakaoshop.product.response.ProductOptionDTO.builder()
                .id(8)
                .optionName("밤깎기+다회용 구이판 세트")
                .price(5500)
                .build());
        return optionDTOList;
    }

    private List<OrderDTO.ProductItemDTO> getProductItemDTOS() {
        //ItemInfo 담을 리스트 생성
        List<OrderDTO.ItemInfoDTO> itemInfoDTOList = new ArrayList<>();
        //ItemInfo 리스트에 담기
        OrderDTO.ItemInfoDTO itemInfoDTO1 = OrderDTO.ItemInfoDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemInfoDTOList.add(itemInfoDTO1);

        OrderDTO.ItemInfoDTO itemInfoDTO2 = OrderDTO.ItemInfoDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemInfoDTOList.add(itemInfoDTO2);

        //ProductItem 리스트 만들기
        List<OrderDTO.ProductItemDTO> productItemDTOList = new ArrayList<>();
        OrderDTO.ProductItemDTO productItemDTO1 = OrderDTO.ProductItemDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemInfoDTOList)
                .build();
        //ProductItem 리스트에 담기
        productItemDTOList.add(productItemDTO1);
        return productItemDTOList;
    }

    private static List<ProductRespFindAllDTO> getProductRespFindAllDTO() {
        List<ProductRespFindAllDTO> respFindAllDTOList = new ArrayList<>();

        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(1)
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .description("")
                .image("/images/1.jpg")
                .price(1000)
                .build());
        // 상품 하나씩 집어넣기
        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(2)
                .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                .description("")
                .image("/images/2.jpg")
                .price(2000)
                .build());

        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(3)
                .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                .description("")
                .image("/images/3.jpg")
                .price(30000)
                .build());

        // 상품 하나씩 집어넣기
        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(4)
                .productName("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종")
                .description("")
                .image("/images/4.jpg")
                .price(4000)
                .build());

        // 상품 하나씩 집어넣기
        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(5)
                .productName("[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주")
                .description("")
                .image("/images/5.jpg")
                .price(5000)
                .build());

        // 상품 하나씩 집어넣기
        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(6)
                .productName("굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전")
                .description("")
                .image("/images/6.jpg")
                .price(15900)
                .build());

        // 상품 하나씩 집어넣기
        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(7)
                .productName("eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제")
                .description("")
                .image("/images/7.jpg")
                .price(26800)
                .build());

        // 상품 하나씩 집어넣기
        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(8)
                .productName("제나벨 PDRN 크림 2개. 피부보습/진정 케어")
                .description("")
                .image("/images/8.jpg")
                .price(25900)
                .build());

        // 상품 하나씩 집어넣기
        respFindAllDTOList.add(ProductRespFindAllDTO.builder()
                .id(9)
                .productName("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감")
                .description("")
                .image("/images/9.jpg")
                .price(797000)
                .build());
        return respFindAllDTOList;
    }
}
