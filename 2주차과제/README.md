# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

# 1주차

카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제
</br>
</br>

## **과제명**
```
1. 요구사항분석/API요청 및 응답 시나리오 분석
2. 요구사항 추가 반영 및 테이블 설계도
```

## **과제 설명**
```
1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)
3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지) 
>- 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
>- 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
>- 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
>- 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_1주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

## **과제명**
```
1. 전체 API 주소 설계
2. Mock API Controller 구현
```

## **과제 설명**
```
1. API주소를 설계하여 README에 내용을 작성하시오.
2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- User 도메인을 제외한 전체 API 주소 설계가 RestAPI 맞게 설계되었는가?  POST와 GET으로만 구현되어 있어도 됨.	
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
>- DTO에 타입은 올바르게 지정되었는가?
>- DTO에 이름은 일관성이 있는가? (예를 들어 어떤 것은 JoinDTO, 어떤 것은 joinDto, 어떤 것은 DtoJoin 이런식으로 되어 있으면 일관성이 없는것이다)
>- DTO를 공유해서 쓰면 안된다 (동일한 데이터가 응답된다 하더라도, 화면은 수시로 변경될 수 있기 때문에 DTO를 공유하고 있으면 배점을 받지 못함)
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_2주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 2주차과제최종

# 과제 상세 답변

### 1. User 도메인을 제외한 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? POST와 GET으로만 구현되어 있어도 됨.

RestAPI에 맞게 설계했다.

### 2. 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)

Postman테스트시 잘 동작함을 확인함.

### 3. DTO에 타입은 올바르게 지정되었는가?

올바르게 지정했다.

### 4. DTO에 이름은 일관성이 있는가? (예를 들어 어떤 것은 JoinDTO, 어떤 것은 joinDto, 어떤 것은 DtoJoin 이런식으로 되어 있으면 일관성이 없는것이다)

사용되는엔티티 + 메서드명 + Res/Req/전달하는데이터 + DTO 형식을 이름을 지었다.

### 5. DTO를 공유해서 쓰면 안된다 (동일한 데이터가 응답된다 하더라도, 화면은 수시로 변경될 수 있기 때문에 DTO를 공유하고 있으면 배점을 받지 못함)

`OrderRestController`에 존재하는 `orderSave()` 와 `orderResult()` 메서드에서 같은 형태의 DTO를 사용했으나 따로 작성했다. 다만, 코드중복이 발생해도 괜찮은지 모르겠다.

# API주소 설계

## 1. 회원 가입

회원가입은 이미 구현되어 있어서 따로 코드 첨부는 하지 않았습니다.

### API

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/join |

```json
// Request
{
	"username" : "meta",
	"email":"meta@nate.com",
  "password":"meta1234!"
}
```

```json
// Response
{
    "success": true,
    "response": null,
    "error": null
}
```

## 2. 로그인

로그인은 이미 구현되어있어 따로 코드는 첨부하지 않았습니다.

### API

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/login |

```json
// Request
{
  "email":"ssar@nate.com",
  "password":"meta1234!"
}
```

- Response Header

| Authorization | Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
// Response
{
  "success": true,
  "response": null,
  "error": null
}
```

## 3. 전체 상품 목록 조회

### API

| Method | Local URL | Param | Param default value |
| --- | --- | --- | --- |
| GET | http://localhost:8080/products | page={number} | 0 |

```json
// Response
{
  "success": true,
  "response": [
    {
      "id": 1,
      "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 주방용품 특가전",
      "description": "",
      "image": "/images/1.jpg",
      "price": 1000
    },
    {
      "id": 2,
      "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
      "description": "",
      "image": "/images/2.jpg",
      "price": 2000
    },
    {
      "id": 3,
      "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
      "description": "",
      "image": "/images/3.jpg",
      "price": 30000
    },
    {
      "id": 5,
      "productName": "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 / 중독성 최고/마른안주",
      "description": "",
      "image": "/images/5.jpg",
      "price": 5000
    },
    {
      "id": 4,
      "productName": "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
      "description": "",
      "image": "/images/4.jpg",
      "price": 4000
      "id": 6,
      "productName": "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전",
      "description": "",
      "image": "/images/6.jpg",
      "price": 15900
      "id": 7,
      "productName": "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제",
      "description": "",
      "image": "/images/7.jpg",
      "price": 26800
      "id": 8,
      "productName": "제나벨 PDRN 크림 2개. 피부보습/진정 케어",
      "description": "",
      "image": "/images/8.jpg",
      "price": 25900
      "id": 9,
      "productName": "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감",
      "description": "",
      "image": "/images/9.jpg",
      "price": 797000
    }
  ],
  "error": null
}
```

### Controller

`@RequestParam` 어노테이션을 사용해 파라미터를 받아주었습니다. 

value는 “page”이며 값은 `number` 변수에 저장합니다. 

파라미터가 주어지지 않는 경우도 있으므로, `required` 속성은 false로 설정해두었습니다.

```java
@GetMapping("/products")
public ResponseEntity<?> findAll(@RequestParam(value = "page", required = false) Long number) {
    List<ProductFindAllResDTO> responseDTO = new ArrayList<>();

    // 상품 하나씩 집어넣기
    responseDTO.add(new ProductFindAllResDTO(
            1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000
    ));
    responseDTO.add(new ProductFindAllResDTO(
            2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000
    ));
    responseDTO.add(new ProductFindAllResDTO(
            3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000
    ));
    responseDTO.add(new ProductFindAllResDTO(
            4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000
    ));
    responseDTO.add(new ProductFindAllResDTO(
            5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 5000
    ));
    responseDTO.add(new ProductFindAllResDTO(
            6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900
    ));
    responseDTO.add(new ProductFindAllResDTO(
            7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800
    ));
    responseDTO.add(new ProductFindAllResDTO(
            8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900
    ));
    responseDTO.add(new ProductFindAllResDTO(
            9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000
    ));

    return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
}
```

### DTO

DTO이름을 맞추기 위해 `ProductResFindAllDTO` → `ProductFindAllResDTO` 이름을 변경했습니다.

```java
// ProductFindAllResDTO.java
@Getter @Setter
public class ProductFindAllResDTO {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;

    @Builder
    public ProductFindAllResDTO(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
```

## 4. 개별 상품 조회

### API

| Method | Local URL |
| --- | --- |
| GET | http://localhost:8080/products/{id} |

```json
// Response
{
  "success": true,
  "response": {
    "id": 1,
    "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
    "description": "",
    "image": "/images/1.jpg",
    "price": 1000,
    "starCount": 5,
    "options": [
      {
        "id": 1,
        "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
        "price": 10000
      },
      {
        "id": 2,
        "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
        "price": 10900
      },
      {
        "id": 3,
        "optionName": "고무장갑 베이지 S(소형) 6팩",
        "price": 9900
      },
      {
        "id": 4,
        "optionName": "뽑아쓰는 키친타올 130매 12팩",
        "price": 16900
      },
      {
        "id": 5,
        "optionName": "2겹 식빵수세미 6매",
        "price": 8900
      }
    ]
  },
  "error": null
}
```

### Controller

```java
@GetMapping("/products/{id}")
public ResponseEntity<?> findById(@PathVariable int id) {
  // 상품을 담을 DTO 생성
  ProductpFindByIdResDTO responseDTO = null;

  if(id == 1){
      List<ProductOptionDTO> optionDTOList = new ArrayList<>();
      optionDTOList.add(new ProductOptionDTO(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
      optionDTOList.add(new ProductOptionDTO(2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900));
      optionDTOList.add(new ProductOptionDTO(3, "고무장갑 베이지 S(소형) 6팩", 9900));
      optionDTOList.add(new ProductOptionDTO(4, "뽑아쓰는 키친타올 130매 12팩", 16900));
      optionDTOList.add(new ProductOptionDTO(4, "2겹 식빵수세미 6매", 8900));
      responseDTO = new ProductpFindByIdResDTO(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000, 5, optionDTOList);

  }else if(id == 2){
      List<ProductOptionDTO> optionDTOList = new ArrayList<>();
      optionDTOList.add(new ProductOptionDTO(6, "22년산 햇단밤 700g(한정판매)", 9900));
      optionDTOList.add(new ProductOptionDTO(7, "22년산 햇단밤 1kg(한정판매)", 14500));
      optionDTOList.add(new ProductOptionDTO(8, "밤깎기+다회용 구이판 세트", 5500));
      responseDTO = new ProductpFindByIdResDTO(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000, 5, optionDTOList);
  }else {
      return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
  }

  return ResponseEntity.ok(ApiUtils.success(responseDTO));
}
```

### DTO

```java
// ProductOptionDTO.java
package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductOptionDTO {

    private int id;
    private String optionName;
    private int price;

    @Builder
    public ProductOptionDTO(int id, String optionName, int price) {
        this.id = id;
        this.optionName = optionName;
        this.price = price;
    }
}
```

DTO 양식을 맞춰주기 위해 `ProductResFindByIdDTO` → `ProductFindByIdResDTO` 로 변경했습니다.

```java
// ProductFindByIdResDTO.java
package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductFindByIdResDTO {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;
    private int starCount; // 0~5
    private List<ProductOptionDTO> options;

    @Builder
    public ProductFindByIdResDTO(int id, String productName, String description, String image, int price, int starCount, List<ProductOptionDTO> options) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.starCount = starCount;
        this.options = options;
    }
}
```

## 5. 장바구니 담기

### API

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/carts/add |
- Request Header

| Authorization | Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
// Request
[
	{
		"optionId":1,
		"quantity":5
	},
	{
		"optionId":2,
		"quantity":5
	}
]
```

```json
// Response
{
	"success": true,
	"response": null,
	"error": null
}
```

### Controller

`@RequestBody` 어노테이션을 통해 JSON형태로 받아주었으며 CartAddReqDTO를 생성했습니다.

배열로 데이터가 들어올 수 있기 때문에 `List<DTO>` 형태로 받아주었습니다.

```java
@PostMapping("/carts/add")
public ResponseEntity<?> addCarts(@RequestBody List<CartAddReqDTO> cartAddReqDTOS){

    return ResponseEntity.ok(ApiUtils.success(null));
}
```

### DTO

```java
package com.example.kakaoshop.cart.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartAddReqDTO {
    private Long optionId;
    private Long quantity;
}
```

## 6. 장바구니 조회

### API

| Method | Local URL |
| --- | --- |
| GET | http://localhost:8080/carts |
- Request Header

| Authorization | Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
// Response
{
  "success": true,
  "response": {
    "products": [
      {
        "id": 1,
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외주방용품특가전",
        "carts": [
          {
            "id": 4,
            "option": {
              "id": 1,
              "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
              "price": 10000
            },
            "quantity": 5,
            "price": 50000
          },
          {
            "id": 5,
            "option": {
              "id": 2,
              "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
              "price": 10900
            },
            "quantity": 5,
            "price": 54500
          }
        ]
      }
    ],
    "totalPrice": 104500
  },
  "error": null
}
```

### Controller

```java
@GetMapping("/carts")
public ResponseEntity<?> findAll() {
    // 카트 아이템 리스트 만들기
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
    List<CartProductDTO> productDTOList = new ArrayList<>();

    // productDTO 리스트에 담기
    productDTOList.add(
            CartProductDTO.builder()
                    .id(1)
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .cartItems(cartItemDTOList)
                    .build()
    );

    CartFindAllResDTO responseDTO = new CartFindAllResDTO(productDTOList, 104500);

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
}
```

### DTO

 양식을 맞추기 위해서`CartResFindAllDTO` → `CartFindAllResDTO` 로 이름을 변경했습니다.

```java
// CartFindAllResDTO
package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartFindAllResDTO {
    private List<CartProductDTO> products;
    private int totalPrice;

    @Builder
    public CartFindAllResDTO(List<CartProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
```

양식을 맞추기 위해 `ProductDTO` → `CartProductDTO` 로 이름을 변경했습니다.

```java
// CartProductDTO.java
package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartProductDTO {
    private int id;
    private String productName;
    private List<CartItemDTO> cartItems;

    @Builder
    public CartProductDTO(int id, String productName, List<CartItemDTO> cartItems) {
        this.id = id;
        this.productName = productName;
        this.cartItems = cartItems;
    }
}
```

```java
// CartItemDTO.java
package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDTO {

    private int id;
    private ProductOptionDTO option;
    private int quantity;
    private int price;

    @Builder
    public CartItemDTO(int id, ProductOptionDTO option, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
```

## 7. 장바구니 상품 옵션 확인 및 수량 결정

### API

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/carts/update |
- Request Header

| Authorization | Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
// Request
[
	{
		"cartId":4,
		"quantity":10
	},
	{
		"cartId":5,
		"quantity":10
	}
]
```

```json
// Response
{
  "success": true,
  "response": {
    "carts": [
      {
        "cartId": 4,
        "optionId": 1,
        "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
        "quantity": 10,
        "price": 100000
      },
      {
        "cartId": 5,
        "optionId": 2,
        "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
        "quantity": 10,
        "price": 109000
      }
    ],
    "totalPrice": 209000
  },
  "error": null
}
```

### Controller

Http Body의 데이터를 받기위해 `@RequestBody` 어노테이션을 사용했습니다.

Body의 데이터는 배열 형태로 보내지기 때문에 `List<DTO>` 형태로 묶었습니다.

```java
@PostMapping("/carts/update")
public ResponseEntity<?> updateCarts(@RequestBody List<CartUpdateReqDTO> cartUpdateReqDTOS){

    List<CartItemUpdateResDTO> cartItemUpdateResDTOS = new ArrayList<>();
    cartItemUpdateResDTOS.add(new CartItemUpdateResDTO(
            4L,
            1L,
            "01. 슬라이딩 지퍼백 크리스마스 에디션 4종",
            10L,
            10000L
    ));
    cartItemUpdateResDTOS.add(new CartItemUpdateResDTO(
            5L,
            2L,
            "02. 슬라이딩 지퍼백 플라워에디션 5종",
            10L,
            109000L
    ));

    CartUpdateResDTO cartUpdateResDTO = new CartUpdateResDTO(
            cartItemUpdateResDTOS,
            209000L
    );
    return ResponseEntity.ok(ApiUtils.success(cartUpdateResDTO));
}
```

### DTO

Http Body로 들어오는 데이터를 담는 DTO입니다.

```java
// CartUpdateReqDTO.java
package com.example.kakaoshop.cart.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartUpdateReqDTO {
    private Long cartId;
    private Long quantity;
}
```

아래부터는 Response에 사용되는 DTO입니다.

```java
// CartUpdateResDTO.java
package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartUpdateResDTO {
    private List<CartItemUpdateResDTO> carts;
    private Long totalPrice;
}
```

```java
// CartItemUpdateResDTO.java
package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemUpdateResDTO {

    private Long cartId;
    private Long optionId;
    private String optionName;
    private Long quantity;
    private Long price;
}
```

## 8. 결제하기

### API

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/orders/save |
- Request Header

| Authorization | Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
// Response
{
  "success": true,
  "response": {
    "id": 2,
    "products": [
      {
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",
        "items": [
          {
            "id": 4,
            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
            "quantity": 10,
            "price": 100000
          },
          {
            "id": 5,
            "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
            "quantity": 10,
            "price": 109000
          }
        ]
      }
    ],
    "totalPrice": 209000
  },
  "error": null
}
```

### Controller

Request없이 장바구니에 있는 데이터를 저장하기만 하기 때문에, Response DTO만 작성해서 Return해주었습니다.

```java
@PostMapping("/orders/save")
public ResponseEntity<?> orderSave(){
    List<OrderSaveItemDTO> orderItemDTOS = new ArrayList<>();
    orderItemDTOS.add(new OrderSaveItemDTO(
            4L,
            "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
            10L,
            100000L
    ));
    orderItemDTOS.add(new OrderSaveItemDTO(
            5L,
            "02. 슬라이딩 지퍼백 플라워에디션 5종",
            10L,
            109000L
    ));
    OrderSaveResDTO orderSaveResDTO = new OrderSaveResDTO(
            2L,
            new OrderSaveProductDTO(
                    "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                    orderItemDTOS),
            209000L
    );

    return ResponseEntity.ok(ApiUtils.success(orderSaveResDTO));
}
```

### DTO

orderSave()에서 최종적으로 Response에 사용되기 때문에 OrderSaveResDTO라고 지었습니다.

```java
// OrderSaveResDTO.java
package com.example.kakaoshop.order.response.orderSave;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderSaveResDTO {

    private Long id;
    private OrderSaveProductDTO products;
    private Long totalPrice;
}
```

orderSave() 에서 Product 데이터를 옮기는 역할이기 때문에 OrderSaveProductDTO라고 지었습니다.

```java
// OrderSaveProductDTO.java
package com.example.kakaoshop.order.response.orderSave;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderSaveProductDTO {

    private String productName;
    private List<OrderSaveItemDTO> items;
}
```

```java
// OrderSaveItemDTO.java
package com.example.kakaoshop.order.response.orderSave;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderSaveItemDTO {

    private Long id;
    private String optionName;
    private Long quantity;
    private Long price;
}
```

## 9. 주문 결과 확인

### API

| Method | Local URL |
| --- | --- |
| GET | http://localhost:8080/orders/{orderId} |
- Request Header

| Authorization | Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
// Response
{
  "success": true,
  "response": {
    "id": 2,
    "products": [
      {
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",
        "items": [
          {
            "id": 4,
            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
            "quantity": 10,
            "price": 100000
          },
          {
            "id": 5,
            "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
            "quantity": 10,
            "price": 109000
          }
        ]
      }
    ],
    "totalPrice": 209000
  },
  "error": null
}
```

### Controller

{orderId}를 파라미터로 받아야 하기 때문에, `@PathVariable` 어노테이션을 사용했습니다.

```java
@GetMapping("/orders/{orderId}")
public ResponseEntity<?> orderResult(@PathVariable String orderId){
    List<OrderResultItemDTO> orderResultItemDTOS = new ArrayList<>();
    orderResultItemDTOS.add(new OrderResultItemDTO(
            4L,
            "01. 슬라이딩 지퍼백 크리스마스에디션 4종.",
            10L,
            100000L
    ));
    orderResultItemDTOS.add(new OrderResultItemDTO(
            5L,
            "02. 슬라이딩 지퍼백 플라워에디션 5종",
            10L,
            109000L
    ));

    OrderResultProductDTO orderResultProductDTO = new OrderResultProductDTO(
            "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
            orderResultItemDTOS
    );

    OrderResultResDTO orderResultResDTO = new OrderResultResDTO(
            2L,
            orderResultProductDTO,
            209000L
    );

    return ResponseEntity.ok(ApiUtils.success(orderResultResDTO));
}
```

### DTO

```java
// OrderResultResDTO.java
package com.example.kakaoshop.order.response.findOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResultResDTO {

    private Long id;
    private OrderResultProductDTO products;
    private Long totalPrice;
}
```

```java
// OrderResultProductDTO.java
package com.example.kakaoshop.order.response.findOne;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderResultProductDTO {

    private String productName;
    private List<OrderResultItemDTO> items;
}
```

```java
// OrderResultItemDTO
package com.example.kakaoshop.order.response.findOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResultItemDTO {

    private Long id;
    private String optionName;
    private Long quantity;
    private Long price;
}
```

# 3주차

카카오 테크 캠퍼스 2단계 - BE - 3주차 클론 과제
</br>
</br>

## **과제명**
```
1. 레포지토리 단위테스트
```

## **과제 설명**
```
1. 레포지토리 단위테스트를 구현하여 소스코드를 제출하시오.
2. 쿼리를 테스트하면서 가장 좋은 쿼리를 작성해보시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 레포지토리 단위테스트가 구현되었는가?
>- 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
>- Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
>- 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
>- BDD 패턴으로 구현되었는가? given, when, then
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_3주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 4주차

카카오 테크 캠퍼스 2단계 - BE - 4주차 클론 과제
</br>
</br>

## **과제명**
```
1. 컨트롤러 단위 테스트
```

## **과제 설명**
```
1. 컨트롤러 단위테스트를 작성한뒤 소스코드를 업로드하시오.
2. stub을 구현하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 컨트롤러 단위테스트가 구현되었는가?
>- Mockito를 이용하여 stub을 구현하였는가?
>- 인증이 필요한 컨트롤러를 테스트할 수 있는가?
>- 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
>- 모든 요청과 응답이 json으로 처리되어 있는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_4주차 과제 

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 5주차

카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제
</br>
</br>

## **과제명**
```
1. 실패 단위 테스트
```

## **과제 설명**
```
1. 컨트롤러 단위테스트를 구현하는데, 실패 테스트 코드를 구현하시오.
2. 어떤 문제가 발생할 수 있을지 모든 시나리오를 생각해본 뒤, 실패에 대한 모든 테스트를 구현하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 실패 단위 테스트가 구현되었는가?
>- 모든 예외에 대한 실패 테스트가 구현되었는가?
>- 예외에 대한 처리를 ControllerAdvice or RestControllerAdvice로 구현하였는가?
>- Validation 라이브러리를 사용하여 유효성 검사가 되었는가?
>- 테스트는 격리되어 있는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_5주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 6주차

카카오 테크 캠퍼스 2단계 - BE - 6주차 클론 과제
</br>
</br>

## **과제명**
```
1. 카카오 클라우드 배포
```

## **과제 설명**
```
1. 통합테스트를 구현하시오.
2. API문서를 구현하시오. (swagger, restdoc, word로 직접 작성, 공책에 적어서 제출 등 모든 방법이 다 가능합니다)
3. 프론트앤드에 입장을 생각해본뒤 어떤 문서를 가장 원할지 생각해본뒤 API문서를 작성하시오.
4. 카카오 클라우드에 배포하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 통합테스트가 구현되었는가?
>- API문서가 구현되었는가?
>- 배포가 정상적으로 되었는가?
>- 프로그램이 정상 작동되고 있는가?
>- API 문서에 실패 예시가 작성되었는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_6주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
