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

# 6/30(금) 1주차 과제 최종

## 1. 부족한 요구사항

### 1. 회원가입

이메일 중복체크

비밀번호 유효성 검사

### 2. 로그인

없음

### 3. 로그아웃

없음

### 4. 전체 상품 목록 조회

톡별가 마감일

공유하기

하트(찜)

톡딜가, 기존가격

### 5. 상품 조회

별점

상품 설명 이미지

### 6. 상품 선택

배송 방법

### 7. 장바구니

없음

### 8. 장바구니 조회

장바구니 상품 삭제

## 2. 화면설계와 API주소 매칭

### (기능1) 회원 가입

<img src = "./img/1.png">

- 성공

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

- 실패1

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/join |

```json
// Request
{
  "username":"mata",
  "email":"metanate.com",
  "password":"meta1234!"
}
```

```json
// Response
{
  "success" : false,
  "response" : null,
  "error" : {
		"message" : "이메일 형식으로 작성해주세요:email",
	  "status" : 400
  }
}
```

- 실패2

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/join |

```json
// Request
{
  "username":"mata",
  "email":"meta@nate.com",
  "password":"meta1234"
}
```

```json
// Response
{
  "success": false,
  "response": null,
  "error": {
		"message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니 다.:password",
    "status": 400
  }
}
```

- 실패3

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/join |

```json
// Request
{
  "username":"mata",
  "email":"ssar@nate.com",
  "password":"meta1234!"
}
```

```json
// Response
{
  "success": false,
  "response": null,
  "error": {
		"message": "동일한 이메일이 존재합니다 : ssar@nate.com",
    "status": 400
  }
}
```

- 실패4

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/join |

```json
// Request
{
  "username":"mata",
  "email":"mate@nate.com",
  "password":"meta12!"
}
```

```json
// Response
{
  "success": false,
  "response": null,
  "error": {
		"message": "8에서 20자 이내여야 합니다.:password",
    "status": 400
  }
}
```

### (기능2) 로그인

<img src = "./img/2.png">

- 성공

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

| Authorization | Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9fXlD0NZQXYYfPHV8rokRJM86nhS869LZ1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
// Response
{
  "success": true,
  "response": null,
  "error": null
}
```

- 실패1

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/login |

```json
// Request
{
  "email":"ssarnate.com",
  "password":"meta1234!"
}
```

```json
// Response
{
  "success": false,
  "response": null,
  "error": {
		"message": "이메일 형식으로 작성해주세요:email",
    "status": 400
  }
}
```

- 실패2

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/login |

```json
// Request
{
  "email":"ssar@nate.com",
  "password":"meta1234"
}
```

```json
// Response
{
  "success": false,
  "response": null,
  "error": {
		"message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니 다.:password",
    "status": 400
  }
}
```

- 실패3

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/login |

```json
// Request
{
  "email":"ssar@nate.com",
  "password":"meta12!"
}
```

```json
// Response
{
  "success": false,
  "response": null,
  "error": {
		"message": "8에서 20자 이내여야 합니다.:password",
    "status": 400
  }
}
```

- 실패4

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/login |

```json
// Request
{
  "email":"ssar1@nate.com",
  "password":"meta1234!"
}
```

```json
// Response
{
  "success": false,
  "response": null,
  "error": {
		"message": "인증되지 않았습니다",
    "status": 401
  }
}
```

### (기능3) 로그아웃

<img src = "./img/3.png">

(API문서에 없음)

### (기능4) 전체 상품 목록 조회

<img src = "./img/4.png">

- 성공1

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

- 성공2 - Param사용

| Method | Local URL | Param | Param default value |
| --- | --- | --- | --- |
| GET | http://localhost:8080/products | page=0 | 0 |

```json
{
  "success": true,
  "response": [
    {
      "id": 1,
      "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품특가전",
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
      "id": 4,
      "productName": "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
      "description": "",
      "image": "/images/4.jpg",
      "price": 4000
    },
    {
      "id": 5,
      "productName": "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성최고/마른안주",
      "description": "",
      "image": "/images/5.jpg",
      "price": 5000
    },
    {
      "id": 6,
      "productName": "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전",
      "description": "",
      "image": "/images/6.jpg",
      "price": 15900
    },
    {
      "id": 7,
      "productName": "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제",
      "description": "",
      "image": "/images/7.jpg",
      "price": 26800
    },
    {
      "id": 8,
      "productName": "제나벨 PDRN 크림 2개. 피부보습/진정 케어",
      "description": "",
      "image": "/images/8.jpg",
      "price": 25900
    },
    {
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

### (기능5) 개별 상품 조회

<img src = "./img/5.png">

- 성공

| Method | Local URL |
| --- | --- |
| GET | http://localhost:8080/products/1 |

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

### (기능6) 상품 옵션 선택

<img src = "./img/6.png">

(상품옵션 선택은 URL이 없음.)

### (기능7) 옵션 확인 및 수량 결정

<img src = "./img/7.png">

(옵션 확인 및 수량 결정은 URL이 없음)

### (기능8) 장바구니 담기

<img src = "./img/8.png">

- 성공

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/carts/add |
- Request Header

| Authorization | Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9fXlD0NZQXYYfPHV8rokRJM86nhS869LZ1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
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

### (기능9) 장바구니 조회

<img src = "./img/9.png">

- 성공

| Method | Local URL |
| --- | --- |
| GET | http://localhost:8080/carts |
- Request Header

| Authorization | Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9fXlD0NZQXYYfPHV8rokRJM86nhS869LZ1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
| --- | --- |

```json
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

### (기능10,11) 장바구니 상품 옵션 확인 및 수량 결정

<img src = "./img/10.png">

- 성공

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/carts/update |
- Request Header

| Authorization | Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9fXlD0NZQXYYfPHV8rokRJM86nhS869LZ1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
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

### (기능12) 결제하기

<img src = "./img/11.png">

- 성공

| Method | Local URL |
| --- | --- |
| POST | http://localhost:8080/orders/save |
- Request Header

| Authorization | Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9fXlD0NZQXYYfPHV8rokRJM86nhS869LZ1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
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

### (기능13) 주문 결과 확인

<img src = "./img/12.png">

- 성공

| Method | Local URL |
| --- | --- |
| GET | http://localhost:8080/orders/1 |
- Request Header

| Authorization | Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9fXlD0NZQXYYfPHV8rokRJM86nhS869LZ1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg |
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

## 3. 모든 API POSTMAN 요청 결과 & 부족한 데이터

### 1. 전체 상품 목록 조회

<img src = "./img/13.png">

부족한 데이터 : 무료/유료 배송유무, 

### 1-2. Param을 사용하여 전체 상품 목록 조회

<img src = "./img/14.png">

### 2. 개별 상품 상세 조회

<img src = "./img/15.png">

부족한 데이터 : 유료/무료 배송 유무

### 3. 이메일 중복 체크

<img src = "./img/16.png">

(화면설계상 없었지만, API 문서에는 존재함.)

### 4. 회원가입

<img src = "./img/17.png">

### 5. 로그인

<img src = "./img/18.png">

### 6. 장바구니 담기

<img src = "./img/19.png">

### 7. 장바구니 조회

<img src = "./img/20.png">

### 8. 주문하기 - (장바구니 수정)

<img src = "./img/21.png">

### 9. 결재하기 - (주문 인서트)

<img src = "./img/22.png">

부족한 데이터 : 화면에 상품명이 출력되지 않음

### 10. 주문 결과 확인

<img src = "./img/23.png">

부족한 데이터 : 옵션을 몇 개 샀는지 프론트에 출력되지 않음. 옵션의 가격이 출력되지 않음.

## 4. ERD

<img src = "./img/erd.png">

### 릴레이션 스키마(변경 후)

회원(회원ID, 이메일, 비밀번호, 이름, 회원Role)s

상품(상품ID, 상품명, 상품이미지, 상품가격, 등록일)

옵션(옵션ID, 상품ID<FK>, 옵션명, 옵션가격, 재고)

장바구니(장바구니ID, 회원ID<FK>, 옵션ID<FK>, 옵션개수)

주문(주문ID, 회원ID, 주문일)

주문아이템(주문아이템ID, 주문ID<FK>, 옵션ID<FK>, 주문수량, 주문금액)

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
>- 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? (예를 들어 배포된 서버는 POST와 GET으로만 구현되었는데, 학생들은 PUT과 DELETE도 배울 예정이라 이부분이 반영되었고, 주소가 RestAPI에 맞게 설계되었는지)
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_2주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

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
5. 배포한 뒤 서비스 장애가 일어날 수 있으니, 해당 장애에 대처할 수 있게 로그를 작성하시오. (로그는 DB에 넣어도 되고, 외부 라이브러리를 사용해도 되고, 파일로 남겨도 된다 - 단 장애 발생시 확인을 할 수 있어야 한다)
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 통합테스트가 구현되었는가?
>- API문서가 구현되었는가?
>- 배포가 정상적으로 되었는가?
>- 서비스에 문제가 발생했을 때, 로그를 통해 문제를 확인할 수 있는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_6주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
