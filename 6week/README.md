# 6주차 과제

# Product

## 전체 상품 조회

- Method: Get
- Local URL : http://localhost:8080/products
- Param : page = {number}
- Param의 디폴트 값은 0이다
- Response Body

```json
{
  "success" : true,
  "response" : [ {
    "id" : 1,
    "productName" : "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
    "description" : "",
    "image" : "/images/1.jpg",
    "price" : 1000
  }, {
    "id" : 2,
    "productName" : "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
    "description" : "",
    "image" : "/images/2.jpg",
    "price" : 2000
  }, {
    "id" : 3,
    "productName" : "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
    "description" : "",
    "image" : "/images/3.jpg",
    "price" : 30000
  }, {
    "id" : 4,
    "productName" : "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
    "description" : "",
    "image" : "/images/4.jpg",
    "price" : 4000
  }, {
    "id" : 5,
    "productName" : "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주",
    "description" : "",
    "image" : "/images/5.jpg",
    "price" : 5000
  }, {
    "id" : 6,
    "productName" : "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전",
    "description" : "",
    "image" : "/images/6.jpg",
    "price" : 15900
  }, {
    "id" : 7,
    "productName" : "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제",
    "description" : "",
    "image" : "/images/7.jpg",
    "price" : 26800
  }, {
    "id" : 8,
    "productName" : "제나벨 PDRN 크림 2개. 피부보습/진정 케어",
    "description" : "",
    "image" : "/images/8.jpg",
    "price" : 25900
  }, {
    "id" : 9,
    "productName" : "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감",
    "description" : "",
    "image" : "/images/9.jpg",
    "price" : 797000
  } ],
  "error" : null
}
```

## 개별 상품 조회

- Method
- Local URL : http://localhost:8080/products/1
- Response Body

```json
{
  "success" : true,
  "response" : {
    "id" : 1,
    "productName" : "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
    "description" : "",
    "image" : "/images/1.jpg",
    "price" : 1000,
    "starCount" : 5,
    "options" : [ {
      "id" : 1,
      "optionName" : "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
      "price" : 10000
    }, {
      "id" : 2,
      "optionName" : "02. 슬라이딩 지퍼백 플라워에디션 5종",
      "price" : 10900
    }, {
      "id" : 3,
      "optionName" : "고무장갑 베이지 S(소형) 6팩",
      "price" : 9900
    }, {
      "id" : 4,
      "optionName" : "뽑아쓰는 키친타올 130매 12팩",
      "price" : 16900
    }, {
      "id" : 5,
      "optionName" : "2겹 식빵수세미 6매",
      "price" : 8900
    } ]
  },
  "error" : null
}
```

## 개별 상품 조회  - 예외 1

*`DB 상품에 등록되지않는 productId를 요청받았을 때`*

http://localhost:8080/products/16

- Response Body

```json
{
  "success" : false,
  "response" : null,
  "error" : {
    "message" : "해당 상품을 찾을 수 없습니다 : 16",
    "status" : 404
  }
}
```

# Cart

## 장바구니 담기

- Method : Post
- Local URL : http://localhost:8080/carts/add
- Request Body

```json
[ {
  "optionId" : 3,
  "quantity" : 5
} ]
```

- Response Body

```json
{
  "success" : true,
  "response" : null,
  "error" : null
}
```

## 장바구니 담기 - 예외 1

*`동일한 OptionId를 추가할 경우`*

- Request Body

```json
[ {
  "optionId" : 3,
  "quantity" : 10
}, {
  "optionId" : 3,
  "quantity" : 6
} ]
```

- Response Body

```json
{
  "success" : false,
  "response" : null,
  "error" : {
    "message" : "중복된 OptionId는 불가합니다",
    "status" : 404
  }
}
```

## 장바구니 담기 - 예외 2

*`DB 옵션에 등록되지않은 OptionId를 요청받았을 때`*

- Request Body

```json
[ {
  "optionId" : 50,
  "quantity" : 5
} ]
```

- Response Body

```json
{
  "success" : false,
  "response" : null,
  "error" : {
    "message" : "해당 옵션을 찾을 수 없습니다 : 50",
    "status" : 404
  }
}
```

## 장바구니 조회

- Method: Get
- Local URL : http://localhost:8080/carts
- Response Body

```json
{
  "success" : true,
  "response" : {
    "products" : [ {
      "id" : 1,
      "productName" : "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
      "carts" : [ {
        "id" : 1,
        "option" : {
          "id" : 1,
          "optionName" : "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
          "price" : 10000
        },
        "quantity" : 5,
        "price" : 50000
      }, {
        "id" : 2,
        "option" : {
          "id" : 2,
          "optionName" : "02. 슬라이딩 지퍼백 플라워에디션 5종",
          "price" : 10900
        },
        "quantity" : 1,
        "price" : 10900
      } ]
    }, {
      "id" : 4,
      "productName" : "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
      "carts" : [ {
        "id" : 3,
        "option" : {
          "id" : 16,
          "optionName" : "선택02_바른곡물효소누룽지맛 6박스",
          "price" : 50000
        },
        "quantity" : 5,
        "price" : 250000
      } ]
    } ],
    "totalPrice" : 310900
  },
  "error" : null
}
```

## 장바구니 수정

- Method : Post
- Local URL : http://localhost:8080/carts/update
- Request Body

```json
[ {
  "cartId" : 1,
  "quantity" : 10
} ]
```

- Response Body

```json
{
  "success" : true,
  "response" : {
    "carts" : [ {
      "cartId" : 1,
      "optionId" : 1,
      "optionName" : "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
      "quantity" : 10,
      "price" : 100000
    }, {
      "cartId" : 2,
      "optionId" : 2,
      "optionName" : "02. 슬라이딩 지퍼백 플라워에디션 5종",
      "quantity" : 1,
      "price" : 10900
    }, {
      "cartId" : 3,
      "optionId" : 16,
      "optionName" : "선택02_바른곡물효소누룽지맛 6박스",
      "quantity" : 5,
      "price" : 250000
    } ],
    "totalPrice" : 360900
  },
  "error" : null
}
```

## 장바구니 수정 - 예외 1

*`유저 장바구니에 아무것도 없을 때`*

- Request Body

```json
[ {
  "cartId" : 1,
  "quantity" : 10
} ]
```

- Response Body

```json
{
  "success" : false,
  "response" : null,
  "error" : {
    "message" : "유저 장바구니가 비어있습니다",
    "status" : 404
  }
}
```

## 장바구니 수정 - 예외 2

*`중복된 cartId가 요청으로 들어올 경우`*

- Request Body

```json
[ {
  "cartId" : 1,
  "quantity" : 10
}, {
  "cartId" : 1,
  "quantity" : 20
} ]
```

- Response Body

```json
{
  "success" : false,
  "response" : null,
  "error" : {
    "message" : "중복된 OptionId는 불가합니다",
    "status" : 404
  }
}
```

## 장바구니 수정 - 예외 3

*`DB cart에 등록되지않은 cartId를 요청받았을 때 예외`*

- Request Body

```json
[ {
  "cartId" : 4,
  "quantity" : 10
} ]
```

- Response Body

```json
{
  "success" : false,
  "response" : null,
  "error" : {
    "message" : "DB에 저장되지않은 cartId가 요청되었습니다",
    "status" : 404
  }
}
```

# Order

## 결제하기

- Method : Post
- Local URL : http://localhost:8080/orders/save
- Response Body

```json
{
  "success" : true,
  "response" : {
    "id" : 2,
    "products" : [ {
      "productName" : "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
      "items" : [ {
        "id" : 6,
        "optionName" : "선택02_바른곡물효소누룽지맛 6박스",
        "quantity" : 5,
        "price" : 250000
      } ]
    }, {
      "productName" : "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
      "items" : [ {
        "id" : 4,
        "optionName" : "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
        "quantity" : 5,
        "price" : 50000
      }, {
        "id" : 5,
        "optionName" : "02. 슬라이딩 지퍼백 플라워에디션 5종",
        "quantity" : 1,
        "price" : 10900
      } ]
    } ],
    "totalPrice" : 1510900
  },
  "error" : null
}
```

## 주문결과 확인하기

- Method : Get
- Local URL : http://localhost:8080/orders/1
- Response Body

```json
{
  "success" : true,
  "response" : {
    "id" : 1,
    "products" : [ {
      "productName" : "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
      "items" : [ {
        "id" : 1,
        "optionName" : "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
        "quantity" : 5,
        "price" : 50000
      }, {
        "id" : 2,
        "optionName" : "02. 슬라이딩 지퍼백 플라워에디션 5종",
        "quantity" : 1,
        "price" : 10900
      } ]
    }, {
      "productName" : "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
      "items" : [ {
        "id" : 3,
        "optionName" : "선택02_바른곡물효소누룽지맛 6박스",
        "quantity" : 5,
        "price" : 250000
      } ]
    } ],
    "totalPrice" : 1510900
  },
  "error" : null
}
```

## 주문결과 확인하기 - 예외 1

`DB Item에 요청으로 온 orderId에 해당하는 item이 없는 경우`

- Response Body

```json
{
  "success" : false,
  "response" : null,
  "error" : {
    "message" : "item 리스트에 null이 들어갔거나 orderId에 해당하는 item을 찾을 수 없습니다",
    "status" : 500
  }
}
```