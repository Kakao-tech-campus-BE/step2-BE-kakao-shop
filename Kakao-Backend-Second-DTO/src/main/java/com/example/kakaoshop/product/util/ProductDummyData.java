package com.example.kakaoshop.product.util;

import com.example.kakaoshop.product.entity.ProductEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductDummyData {
    public List<ProductEntity> productDummyList() {
        return Arrays.asList(
                newProductEntity(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000),
                newProductEntity(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", 2, 2000),
                newProductEntity(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", 3, 30000),
                newProductEntity(4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", 4, 4000),
                newProductEntity(5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", 5, 5000),
                newProductEntity(6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", 6, 15900),
                newProductEntity(7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", 7, 26800),
                newProductEntity(8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", 8, 25900),
                newProductEntity(9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", 9, 797000),
                newProductEntity(10, "통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정", 10, 8900),
                newProductEntity(11, "아삭한 궁채 장아찌 1kg 외 인기 반찬 모음전", 11, 6900),
                newProductEntity(12, "깨끗한나라 순수소프트 30롤 2팩. 무형광, 도톰 3겹", 12, 28900),
                newProductEntity(13, "생활공작소 초미세모 칫솔 12입 2개+가글 증정", 13, 9900),
                newProductEntity(14, "경북 영천 샤인머스켓 가정용 1kg 2수 내외", 14, 9900),
                newProductEntity(15, "[LIVE][5%쿠폰] 홈카페 Y3.3 캡슐머신 베이직 세트", 15, 148000)
        );
    }

    public List<ProductOptionEntity> optionDummyList(List<ProductEntity> productList) {
        return Arrays.asList(
                newOptionEntity(productList.get(0), 1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                newOptionEntity(productList.get(0), 2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                newOptionEntity(productList.get(0), 3, "고무장갑 베이지 S(소형) 6팩", 9900),
                newOptionEntity(productList.get(0), 4, "뽑아쓰는 키친타올 130매 12팩", 16900),
                newOptionEntity(productList.get(0), 5, "2겹 식빵수세미 6매", 8900),
                newOptionEntity(productList.get(1), 6, "22년산 햇단밤 700g(한정판매)", 9900),
                newOptionEntity(productList.get(1), 7, "22년산 햇단밤 1kg(한정판매)", 14500),
                newOptionEntity(productList.get(1), 8, "밤깎기+다회용 구이판 세트", 5500),
                newOptionEntity(productList.get(2), 9, "JR310 (유선 전용) - 블루", 29900),
                newOptionEntity(productList.get(2), 10, "JR310BT (무선 전용) - 레드", 49900),
                newOptionEntity(productList.get(2), 11, "JR310BT (무선 전용) - 그린", 49900),
                newOptionEntity(productList.get(2), 12, "JR310BT (무선 전용) - 블루", 49900),
                newOptionEntity(productList.get(2), 13, "T510BT (무선 전용) - 블랙", 52900),
                newOptionEntity(productList.get(2), 14, "T510BT (무선 전용) - 화이트", 52900),
                newOptionEntity(productList.get(3), 15, "선택01_바른곡물효소 누룽지맛 2박스", 17900), //15
                newOptionEntity(productList.get(3), 16, "선택02_바른곡물효소누룽지맛 6박스", 50000),
                newOptionEntity(productList.get(3), 17, "선택03_바른곡물효소3박스+유산균효소3박스", 50000),
                newOptionEntity(productList.get(3), 18, "선택04_바른곡물효소3박스+19종유산균3박스", 50000),
                newOptionEntity(productList.get(4), 19, "01. 말랑컷팅장족 100g", 4900),
                newOptionEntity(productList.get(4), 20, "02. 말랑컷팅장족 300g", 12800),
                newOptionEntity(productList.get(4), 21, "03. 눌린장족 100g", 4900),
                newOptionEntity(productList.get(5), 22, "굳지않는 쑥 앙금 절편 1050g", 15900),
                newOptionEntity(productList.get(5), 23, "굳지않는 흑미 앙금 절편 1050g", 15900),
                newOptionEntity(productList.get(5), 24, "굳지않는 흰 가래떡 1050g", 15900),
                newOptionEntity(productList.get(6), 25, "이너딜리티 1박스", 26800), //25
                newOptionEntity(productList.get(6), 26, "이너딜리티 2박스+사은품 2종", 49800),
                newOptionEntity(productList.get(7), 27, "제나벨 PDRN 자생크림 1+1", 25900),
                newOptionEntity(productList.get(8), 28, "플레이스테이션 VR2 호라이즌 번들", 839000),
                newOptionEntity(productList.get(8), 29, "플레이스테이션 VR2", 797000),
                newOptionEntity(productList.get(9), 30, "홍가리비2kg(50미이내)", 8900), //30
                newOptionEntity(productList.get(10), 31, "궁채 절임 1kg", 6900),
                newOptionEntity(productList.get(10), 32, "양념 깻잎 1kg", 8900),
                newOptionEntity(productList.get(10), 33, "된장 깻잎 1kg", 8900),
                newOptionEntity(productList.get(10), 34, "간장 깻잎 1kg", 7900),
                newOptionEntity(productList.get(10), 35, "고추 무침 1kg", 8900),
                newOptionEntity(productList.get(10), 36, "파래 무침 1kg", 9900),
                newOptionEntity(productList.get(11), 37, "01_순수소프트 27m 30롤 2팩", 28900),
                newOptionEntity(productList.get(11), 38, "02_벚꽃 프리미엄 27m 30롤 2팩", 32900),
                newOptionEntity(productList.get(12), 39, "(증정) 초미세모 칫솔 12개 x 2개", 11900),
                newOptionEntity(productList.get(12), 40, "(증정) 잇몸케어 치약 100G 3개 x 2개", 16900),
                newOptionEntity(productList.get(12), 41, "(증정) 구취케어 치약 100G 3개 x 2개", 16900),
                newOptionEntity(productList.get(12), 42, "(증정)화이트케어 치약 100G 3개 x 2개", 19900),
                newOptionEntity(productList.get(12), 43, "(증정) 어린이 칫솔 12EA", 9900),
                newOptionEntity(productList.get(13), 44, "[가정용] 샤인머스켓 1kg 2수내외", 9900),
                newOptionEntity(productList.get(13), 45, "[특품] 샤인머스켓 1kg 1-2수", 12900), //45
                newOptionEntity(productList.get(13), 46, "[특품] 샤인머스켓 2kg 2-3수", 23900),
                newOptionEntity(productList.get(14), 47, "화이트", 148000),
                newOptionEntity(productList.get(14), 48, "블랙", 148000)
        );
    }

    private ProductEntity newProductEntity(Integer id, String productName, int imageNumber, int price) {
        return ProductEntity.builder()
                .id(id)
                .productName(productName)
                .description("")
                .image("/images/" + imageNumber + ".jpg")
                .price(price)
                .build();
    }

    private ProductOptionEntity newOptionEntity(ProductEntity product, Integer id, String optionName, int price) {
        return ProductOptionEntity.builder()
                .product(product)
                .id(id)
                .optionName(optionName)
                .price(price)
                .build();
    }
}
