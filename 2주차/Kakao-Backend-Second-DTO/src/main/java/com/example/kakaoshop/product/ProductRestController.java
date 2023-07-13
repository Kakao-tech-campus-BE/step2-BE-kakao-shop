package com.example.kakaoshop.product;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.product.response.ProductOptionDTO;
import com.example.kakaoshop.product.response.ProductRespFindAllDTO;
import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestController {

    @GetMapping("/products")
    public ResponseEntity<?> findAll() {
        List<ProductRespFindAllDTO> responseDTO = new ArrayList<>();

        // 상품 하나씩 집어넣기
        responseDTO.add(new ProductRespFindAllDTO(
                1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 5000
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000
        ));


        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

@GetMapping("/products/{id}")
public ResponseEntity<?> findById(@PathVariable int id) {
    // 상품을 담을 DTO 생성
    ProductRespFindByIdDTO responseDTO = null;

    if(id == 1){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
        optionDTOList.add(new ProductOptionDTO(2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900));
        optionDTOList.add(new ProductOptionDTO(3, "고무장갑 베이지 S(소형) 6팩", 9900));
        optionDTOList.add(new ProductOptionDTO(4, "뽑아쓰는 키친타올 130매 12팩", 16900));
        optionDTOList.add(new ProductOptionDTO(4, "2겹 식빵수세미 6매", 8900));
        responseDTO = new ProductRespFindByIdDTO(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000, 5, optionDTOList);

    }else if(id == 2){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(6, "22년산 햇단밤 700g(한정판매)", 9900));
        optionDTOList.add(new ProductOptionDTO(7, "22년산 햇단밤 1kg(한정판매)", 14500));
        optionDTOList.add(new ProductOptionDTO(8, "밤깎기+다회용 구이판 세트", 5500));
        responseDTO = new ProductRespFindByIdDTO(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000, 5, optionDTOList);
    }else if(id == 3){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(9, "JR310 (유선 전용) - 블루", 29900));
        optionDTOList.add(new ProductOptionDTO(10, "JR310 (유선 전용) - 레드", 49900));
        optionDTOList.add(new ProductOptionDTO(11, "JR310 (유선 전용) - 그린", 49900));
        optionDTOList.add(new ProductOptionDTO(12, "JR310 (유선 전용) - 블랙", 49900));
        optionDTOList.add(new ProductOptionDTO(13, "JR310 (유선 전용) - 화이트)", 52900));
        responseDTO = new ProductRespFindByIdDTO(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000, 5, optionDTOList);

    }else if(id == 4){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(15, "선택01_바른곡물효소 누룽지맛 2박스", 17900));
        optionDTOList.add(new ProductOptionDTO(16, "선택02_바른곡물효소누룽지맛 6박스", 50000));
        optionDTOList.add(new ProductOptionDTO(17, "선택03_바른곡물효소3박스+유산균효소3박스", 50000));
        optionDTOList.add(new ProductOptionDTO(18, "선택04_바른곡물효소3박스+19종유산균3박스", 50000));
        responseDTO = new ProductRespFindByIdDTO(4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000, 5, optionDTOList);


    }else if(id == 5){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(19, "01. 말랑컷팅장족 100g", 4900));
        optionDTOList.add(new ProductOptionDTO(20, "01. 말랑컷팅장족 300g", 12800));
        optionDTOList.add(new ProductOptionDTO(21, "01. 말랑컷팅장족 100g", 4900));
        responseDTO = new ProductRespFindByIdDTO(5, "더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 4900, 5, optionDTOList);
    }
    else if(id == 6){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(22, "굳지않는 쑥 앙금 절편 1050g", 15900));
        optionDTOList.add(new ProductOptionDTO(23, "굳지않는 흑미 앙금 절편 1050g", 15900));
        optionDTOList.add(new ProductOptionDTO(24, "굳지않는 흰 가래떡 1050g", 15900));
        responseDTO = new ProductRespFindByIdDTO(6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900, 5, optionDTOList);
    }
    else if(id == 7){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(25, "이너딜리티 1박스", 42680));
        optionDTOList.add(new ProductOptionDTO(26, "이너딜리티 2박스+사은품 2종", 49800));
        responseDTO = new ProductRespFindByIdDTO(7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800, 5, optionDTOList);

    }
    else if(id == 8){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(27, "제나벨 PDRN 자생크림 1+1", 25900));
        responseDTO = new ProductRespFindByIdDTO(8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900, 5, optionDTOList);
    }
    else if(id == 9){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(28, "플레이스테이션 VR2 호라이즌 번들", 839000));
        optionDTOList.add(new ProductOptionDTO(29, "플레이스테이션 VR2", 797000));
        responseDTO = new ProductRespFindByIdDTO(9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797900, 5, optionDTOList);
    }
    else {
        return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
    }

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
}

}
