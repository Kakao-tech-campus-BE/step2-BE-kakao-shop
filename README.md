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

# 1주차 과제

### 1. 요구사항 시나리오를 보고 부족해보이는 기능을 하나 이상 체크하여 README에 내용을 작성하세요.

1) 회원가입

- 이메일 중복 확인 기능
- 소셜 로그인 기능

2) 로그인 기능

- 비밀 번호 잊었을 때 찾을 수 있는 기능
- 자동 로그인 기능

3) 로그아웃 기능

4) 전체 상품 목록 조회

5) 개별 상품 상세 조회

- 상품 찜하기 기능

6) 상품 옵션 선택

7) 옵션 확인 및 수량 결정

- 선택한 옵션 지우기

8) 장바구니 담기

9) 장바구니 보기

10) 장바구니 상품 옵션 확인 및 수량 결정

- 장바구니 안에서 상품을 선택할 수 있는 기능
- 담은 상품 지울 수 있는 기능
- 장바구니 비우기 기능

11) 주문

- 음수 수량인지 확인하는 기능
- 수량 제한 기능

12) 결제

13) 주문 결과 확인

- 쇼핑 계속하기 기능

상품 문의 기능 (추가)

### 2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하세요. (카카오 화면설계 시나리오가 있음)

#### 1) 회원가입 버튼 클릭 시

[![회원가입 버튼](https://i.postimg.cc/SQz2KCR3/image.png)](https://postimg.cc/vDbHq1Wt)

**API 주소 : http://localhost:8080/join**

**회원가입 성공**
```
{
  "success": true,
  "response": null,
  "error": null
}
```

**실패 예시 1**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "Request method 'GET' not supported",
    "status": 500
  }
}
```

**이메일 형식이 아닌 경우**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "이메일 형식으로 작성해주세요:email",
    "status": 400
  }
}
```

**비밀번호 형식이 안 맞는 경우**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
    "status": 400
  }
}
```
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "8에서 20자 이내여야 합니다.:password",
    "status": 400
  }
}
```

**동일한 이메일이 존재하는 경우**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "동일한 이메일이 존재합니다 : [ssar@nate.com](mailto:ssar@nate.com)",
    "status": 400
  }
}
```

#### 2) 로그인 버튼 클릭 시 

[![로그인 버튼](https://i.postimg.cc/XJBJp6jd/image.png)](https://postimg.cc/KkFFHVg8)

**API 주소 : http://localhost:8080/login**

**로그인 성공**
```
{
  "success": true,
  "response": null,
  "error": null
}
```

**실패예시 1**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "Request method 'GET' not supported",
    "status": 500
  }
}
```

**이메일 형식으로 작성하지 않은 경우**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "이메일 형식으로 작성해주세요:email",
    "status": 400
  }
}
```

**비밀번호 형식이 맞지 않는 경우**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
    "status": 400
  }
}
```
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "8에서 20자 이내여야 합니다.:password",
    "status": 400
  }
}
```

**회원가입이 안된 경우**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "인증되지 않았습니다",
    "status": 401
  }
}
```

#### 3) 전체 상품 조회

[![전체 상품 조회](https://i.postimg.cc/44zfM5mf/image.png)](https://postimg.cc/RqZxJw7y)

**API 주소 : http://localhost:8080/products**

**전체 상품 보기**
```
{
  "success": true,
  "response": [
    {
      "id": 1,
      "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
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
      "productName": "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주",
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

#### 4) 개별 상품 상세 조회 (전체 상품 조회에서 개별 상품을 클릭했을 때)

[![개별 상품 상세 조회](https://i.postimg.cc/WzjvjBh2/image.png)](https://postimg.cc/LYy730t7)

**API 주소 : http://localhost:8080/products/{product_id}**

**없는 상품에 대한 요청**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "해당 상품을 찾을 수 없습니다 : 100",
    "status": 404
  }
}
```

**상품이 있는 경우 요청**
```
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

#### 5) 장바구니 담기

[![장바구니 담기](https://i.postimg.cc/CxtpdBzr/image.png)](https://postimg.cc/BjxVVv52)

**API 주소 : http://localhost:8080/carts/add**

**로그인 하지 않은 상태에서 장바구니 담기**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "인증되지 않았습니다",
    "status": 401
  }
}
```

**로그인한 상태에서 장바구니 담기**
```
{
  "success": true,
  "response": null,
  "error": null
}
```

**없는 옵션에 대한 장바구니 담기**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "해당 옵션을 찾을 수 없습니다 : 100",
    "status": 404
  }
}
```

#### 6) 장바구니 조회

[![장바구니 조회](https://i.postimg.cc/Tw6SWrG0/image.png)](https://postimg.cc/WD5SBqdq)

**API 주소 : http://localhost:8080/carts**

**로그인 하지 않은 상태에서 장바구니 조회** 
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "인증되지 않았습니다",
    "status": 401
  }
}
```

**로그인한 상태**

**장바구니 비어있는 경우**
```
{
  "success": true,
  "response": {
    "products": [],
    "totalPrice": 0
  },
  "error": null
}
```

**장바구니에 상품이 있는 경우**
```
{
  "success": true,
  "response": {
    "products": [
      {
        "id": 1,
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
        "carts": [
          {
            "id": 1,
            "option": {
              "id": 1,
              "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
              "price": 10000
            },
            "quantity": 5,
            "price": 50000
          },
          {
            "id": 2,
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

#### 7) 주문하기 버튼 눌렀을 때 (장바구니 수정)

[![장바구니 수정](https://i.postimg.cc/rsyPfMDt/image.png)](https://postimg.cc/TLS0w8qR)

**API 주소 : http://localhost:8080/carts/update**

**장바구니에 없는 상품 요청** 
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "장바구니에 없는 상품은 주문할 수 없습니다 : 3",
    "status": 400
  }
}
```

**장바구니에 있는 상품 요청** 
```
{
  "success": true,
  "response": {
    "carts": [
      {
        "cartId": 1,
        "optionId": 1,
        "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
        "quantity": 10,
        "price": 100000
      },
      {
        "cartId": 2,
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

#### 8) 결제하기 버튼 

[![결제하기 버튼](https://i.postimg.cc/VLVVFymd/image.png)](https://postimg.cc/ppKC269H)

**API 주소 : http://localhost:8080/orders/save**

```
{
  "success": true,
  "response": {
    "id": 1,
    "products": [
      {
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
        "items": [
          {
            "id": 1,
            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
            "quantity": 5,
            "price": 50000
          },
          {
            "id": 2,
            "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
            "quantity": 10,
            "price": 109000
          }
        ]
      }
    ],
    "totalPrice": 159000
  },
  "error": null
}
```

#### 9) 주문 결과 확인 

[![주문 결과 확인](https://i.postimg.cc/vHk0VWtn/image.png)](https://postimg.cc/Mv00kf4Z)

**API 주소 : http://localhost:8080/orders/{order_id}**

**없는 주문 번호 요청**
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "해당 주문을 찾을 수 없습니다 : 2",
    "status": 404
  }
}
```

**있는 주문 번호 요청**
```
{
  "success": true,
  "response": {
    "id": 1,
    "products": [
      {
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
        "items": [
          {
            "id": 1,
            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
            "quantity": 5,
            "price": 50000
          },
          {
            "id": 2,
            "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
            "quantity": 10,
            "price": 109000
          }
        ]
      }
    ],
    "totalPrice": 159000
  },
  "error": null
}
```

### 3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하세요.

**부족한 데이터** 

1. 부족한 API
    
없는 페이지에 대한 요청을 하려고 할 때
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "존재하지 않은 페이지입니다.",
    "status": 404
  }
}
```

회원가입을 하지않은 사용자가 로그인하려고 할 때
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "없는 사용자입니다.",
    "status": 404
  }
}
```

장바구니 담기에서 음수 수량을 넣었을 때
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "유효하지 않은 수량입니다 : -1",
    "status": 400
  }
}
```

장바구니 담기에서 매우 큰 수량을 넣었을 때
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "유효하지 않은 수량입니다 : 10000",
    "status": 400
  }
}
```

장바구니 수정에서 매우 큰 수량을 넣었을 때
```
{
  "success": false,
  "response": null,
  "error": {
    "message": "유효하지 않은 수량입니다 : 10000",
    "status": 400
  }
}
```

2. 부족한 data 
    - 장바구니 보기에서 이미지 data를 넣지 않음
    - 주문결과 확인에서 주문한 상품의 옵션 목록을 1개만 보여줌 (01. 슬라이딩 지퍼백 크리스마스에디션 4종 외 n개 이런식으로 바꿔주기)
    

### 4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하세요.

[![ER-Diagram.png](https://i.postimg.cc/FzdNTydS/ER-Diagram.png)](https://postimg.cc/XBbRJygN)

### 5. pdf 과제

**User 테이블**
```
table user_tb{
    id       INTEGER(11) PRIMARY KEY AUTOINCREMENT,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(256) NOT NULL,
    username VARCHAR(45)  NOT NULL,
    roles    ENUM(Consumer, Producer, Admin) NOT NULL,
	UNIQUE (email)
}
```

**Product 테이블**
```
table product_tb{
    id           INTEGER(11) PRIMARY KEY AUTOINCREMENT,
    product_name VARCHAR(100)     NOT NULL,
    description  VARCHAR(1000)    NOT NULL,
    image        VARCHAR(500)     NOT NULL,
    price        INTEGER(11) NOT NULL
}
```

**Option 테이블**
```
table option_tb{
    id          INTEGER(11) PRIMARY KEY AUTOINCREMENT,
    product_id  INTEGER(11),
    option_name VARCHAR(100)    NOT NULL,
    price       INTEGER(11)     NOT NULL,
    CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES product_tb
}
```

**Order 테이블**
```
table order_tb{
    id      INTEGER(11) PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER(11),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_tb
}
```

**Cart 테이블**
```
table cart_tb{
    id        INTEGER(11) PRIMARY KEY AUTOINCREMENT,
    user_id   INTEGER(11),
    option_id INTEGER(11),
    quantity  INTEGER(11) NOT NULL,
    price     INTEGER(11) NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_tb,
    CONSTRAINT option_id_fk FOREIGN KEY (option_id) REFERENCES option_tb,
    UNIQUE (user_id, option_id)
}
```

**OrderResult 테이블**

Item 이라는 테이블 명이 이해하기 쉽지 않다고 생각이 들어서, OrderResult로 테이블 명을 변경하였습니다.
```
table order_result_tb{
    id        INTEGER(11) PRIMARY KEY AUTOINCREMENT,
    option_id INTEGER(11),
    quantity  INTEGER(11) NOT NULL,
    price     INTEGER(11) NOT NULL,
    order_id  INTEGER(11),
    CONSTRAINT option_id_fk FOREIGN KEY (option_id) REFERENCES option_tb,
    CONSTRAINT order_id_fk FOREIGN KEY (order_id) REFERENCES order_tb
}
```



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
