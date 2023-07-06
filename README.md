# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

# 1주차

카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제
<br/>
<br/>

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

<br/>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지) 
>- 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
>- 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
>- 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
>- 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_1주차 과제

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

<br/>

## **1. 요구사항 분석 / API 요청 및 응답 시나리오 분석**
<br/>

### <span style="color:skyblue"> 1) **요구사항 시나리오를 보고 부족해 보이는 기능**을 하나 이상 체크하여 README에 내용을 작성하시오.</span>

<br/>

### 회원 가입
- 이메일(아이디) 중복 체크 기능

### 전체 상품 목록 조회
- 상품 유형, 이름 검색 기능

### 장바구니 
- 장바구니 상품 및 옵션 삭제 기능
- 장바구니에 이미 담겨진 상품 담을 경우 장바구니 업데이트하는 기능  

### 결제
- 배송지 수정 기능
- 배송 요청사항 작성 기능
- 배송지를 고려한 추가 배송비 포함 기능

### 주문 결과 확인
- 구매한 상품의 옵션, 수량, 금액 모두를 보여주는 기능

<br/>

> 개발 범위에 벗어나지만 필요한 기능 
### 마이페이지 
- 비밀번호 수정 기능
- 주문 내역 조회 기능

<br/>

### <span style="color:skyblue"> **2)** 제시된 화면설계를 보고 <U>**해당 화면설계와 배포된 기존 서버의 API 주소를 매칭**</U>하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)</span> 
### <span style="color:skyblue"> **3)** 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 <U>**부족한 데이터가 무엇인지 체크**</U>하여 README에 내용을 작성하시오.</span>
 
<br/>

<img src="img/1.png">

<br/>

### **1. 쇼핑하기(Home page) : 전체 상품 목록 조회**
* GET
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/products
```
* Param
```bash
page={number}
```  
* Response Body
```Json
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
> description은 사용하지 않으므로 삭제하는 것이 좋다.

<br/>

### **2. 상품 선택 : 개별 상품 상세 조회**
* GET
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/products/:id
```
* Response Body
```JSON
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

<br/>

### **3. 장바구니 조회**
* GET
```
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts
```
* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
* Response Body
```Json
{
    "success": true,
    "response": {
        "products": [
            {
                "id": 1,
                "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                "carts": [
                    {
                        "id": 33,
                        "option": {
                            "id": 1,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    },
                    {
                        "id": 34,
                        "option": {
                            "id": 2,
                            "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                            "price": 10900
                        },
                        "quantity": 5,
                        "price": 54500
                    },
                    {
                        "id": 39,
                        "option": {
                            "id": 3,
                            "optionName": "고무장갑 베이지 S(소형) 6팩",
                            "price": 9900
                        },
                        "quantity": 8,
                        "price": 79200
                    },
                    {
                        "id": 40,
                        "option": {
                            "id": 4,
                            "optionName": "뽑아쓰는 키친타올 130매 12팩",
                            "price": 16900
                        },
                        "quantity": 5,
                        "price": 84500
                    },
                    {
                        "id": 31,
                        "option": {
                            "id": 5,
                            "optionName": "2겹 식빵수세미 6매",
                            "price": 8900
                        },
                        "quantity": 1,
                        "price": 8900
                    }
                ]
            },
            {
                "id": 2,
                "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
                "carts": [
                    {
                        "id": 44,
                        "option": {
                            "id": 6,
                            "optionName": "22년산 햇단밤 700g(한정판매)",
                            "price": 9900
                        },
                        "quantity": 8,
                        "price": 79200
                    },
                    {
                        "id": 45,
                        "option": {
                            "id": 7,
                            "optionName": "22년산 햇단밤 1kg(한정판매)",
                            "price": 14500
                        },
                        "quantity": 5,
                        "price": 72500
                    },
                    {
                        "id": 30,
                        "option": {
                            "id": 8,
                            "optionName": "밤깎기+다회용 구이판 세트",
                            "price": 5500
                        },
                        "quantity": 1,
                        "price": 5500
                    }
                ]
            }
        ],
        "totalPrice": 434300
    },
    "error": null
}
```
> 장바구니는 선택된 option과 담은 수량(quantity), 가격(price)을 포함한다.

<br/>


---
<br/>

<img src="img/2.png">

<br/>

### **로그인**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/login
```

* Request Body
```json
{
  "email":"ssar@nate.com",
  "password":"meta1234!"
}
```
* Response Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
* Response Body
```json
{
    "success": true,
    "response": null,
    "error": null
}
```
>JWT 토큰 인증 방식을 사용하므로 로그인 후 Bearer 토큰을 얻는다.

<br/>

---
<br/>

<img src="img/3.png">

<br/>

### **1. 이메일 중복 확인**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/check
```
* Request Body
```json
{
  "email":"meta@nate.com"
}
```
* Response Body
```json
{
    "success": true,
    "response": null,
    "error": null
}
```

<br/>


### **2. 회원가입**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/join
```
* Request Body
```json
{
  "username":"meta",
  "email":"meta@nate.com",
  "password":"meta1234!"
}
```
* Response Body
```json
{
    "success": true,
    "response": null,
    "error": null
}
```

<br/>

---
<br/>

<img src="img/4.png">

<br/>

### **장바구니 담기**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts/add
```
* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

* Request Body
```json
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
* Response Body
```json
{
    "success": true,
    "response": null,
    "error": null
}
```
> `존재하는 옵션 Id`로 요청하면 오류가 난다. 장바구니(cart)에 이미 같은 옵션이 담겼기 때문이다.<br/>하지만 고객이 상품A를 장바구니에 담은 후 다른 상품들을 둘러보다가, 다시 상품A를 담을 경우 그만큼 수량이 더해져서 장바구니가 갱신되면 좋을 것 같다. <br/>이를 위해 <U>**이미 존재하는 옵션 Id일 경우 서버에서 장바구니 수정(/update)을 호출**</U>하면 좋을 것 같다.

<br/>

---
<br/>

<img src="img/5.png">

<br/>

### **주문하기 (장바구니 수정)**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts/update
```

* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

* Request Body
```json
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
* Response Body
```json
{
    "success": true,
    "response": {
        "carts": [
            {
                "cartId": 30,
                "optionId": 8,
                "optionName": "밤깎기+다회용 구이판 세트",
                "quantity": 1,
                "price": 5500
            },
            {
                "cartId": 31,
                "optionId": 5,
                "optionName": "2겹 식빵수세미 6매",
                "quantity": 1,
                "price": 8900
            },
            {
                "cartId": 47,
                "optionId": 31,
                "optionName": "궁채 절임 1kg",
                "quantity": 10,
                "price": 69000
            },
            {
                "cartId": 48,
                "optionId": 32,
                "optionName": "양념 깻잎 1kg",
                "quantity": 10,
                "price": 89000
            }
        ],
        "totalPrice": 592300
    },
    "error": null
}
```
> JSON 응답에 상품 이름을 포함하는 것이 좋다. 상품명 밑에 옵션 등이 표시되기 때문이다.

<br/>

---
<br/>

<img src="img/6.png">

<br/>

### **1. 결제하기**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/orders/save
```

* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
* Response Body
```json
{
    "success": true,
    "response": {
        "id": 8,
        "products": [
            {
                "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
                "items": [
                    {
                        "id": 33,
                        "optionName": "밤깎기+다회용 구이판 세트",
                        "quantity": 2,
                        "price": 11000
                    },
                    {
                        "id": 34,
                        "optionName": "22년산 햇단밤 700g(한정판매)",
                        "quantity": 1,
                        "price": 9900
                    }
                ]
            },
            {
                "productName": "[LIVE][5%쿠폰] 홈카페 Y3.3 캡슐머신 베이직 세트",
                "items": [
                    {
                        "id": 35,
                        "optionName": "블랙",
                        "quantity": 1,
                        "price": 148000
                    },
                    {
                        "id": 36,
                        "optionName": "화이트",
                        "quantity": 3,
                        "price": 444000
                    }
                ]
            }
        ],
        "totalPrice": 612900
    },
    "error": null
}
```
> 온라인 쇼핑몰이라면, 요청을 보낼 때 배송지와 배송 요청사항, 배송비 정보를 함께 보내야 한다.
> <br/> 요구사항에 존재하지 않아도 필요하므로 설계시 포함하였다.

<br/>

### **2. 주문 결과 확인 (결제 후)**
* GET
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/orders/:id
```
* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
* Response Body
```json
{
    "success": true,
    "response": {
        "id": 9,
        "products": [
            {
                "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
                "items": [
                    {
                        "id": 37,
                        "optionName": "밤깎기+다회용 구이판 세트",
                        "quantity": 1,
                        "price": 5500
                    },
                    {
                        "id": 38,
                        "optionName": "22년산 햇단밤 700g(한정판매)",
                        "quantity": 2,
                        "price": 19800
                    }
                ]
            },
            {
                "productName": "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전",
                "items": [
                    {
                        "id": 39,
                        "optionName": "굳지않는 흰 가래떡 1050g",
                        "quantity": 2,
                        "price": 31800
                    }
                ]
            }
        ],
        "totalPrice": 57100
    },
    "error": null
}
```

<br/>

### <span style="color:skyblue">**4) 테이블 설계**를 하여 README에 **ER-Diagram**을 추가하여 제출하시오.</span>

<br/>

### **1. 데이터베이스 설계**
화면 설계와 응답 데이터를 보며 테이블과 필드, 제약조건을 생각해보았다.

</br>

### **위 설계에서 외래키를 사용하는 이유(인덱스 사용X)** 
> 외래키로 **무결성을 보장**하기 위함과 **인덱스를 적절히 사용하고 최적화하는 것이 어렵기** 때문이다.

> 강의에서도 배우는 입장이므로 외래키 사용을 추천하셨다.

> 실무에서는 <U>**외래키로 인한 제약사항이 존재**</U>하므로 **인덱스를 주로 이용**한다.

<br/>

## **user**
```sql
CREATE TABLE user (
    id INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```
> PK는 NOT NULL과 UNIQUE 제약조건을 가진다. 

> email은 로그인 아이디로 사용되어 유저마다 고유해야하므로 UNIQUE 제약조건을 두었다. 

> 프로젝트의 db에 많은 양의 데이터가 들어가지 않으므로, <br/>
> id의 타입은 BIGINT가 아닌 INT를 사용했다.

> INT로 선언하면 INT(11)로 선언하는 것과 같다. <br/> 
> INT는 10자리이지만 음수까지 표현하기 위해 11자리가 default이다.

<br/>

## **product**
```sql
CREATE TABLE product (
    id INT(11) NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price INT(11) NOT NULL,
    image VARCHAR(500),
    description VARCHAR(1000),
    PRIMARY KEY (id)
);
```

> 이미지 경로를 저장하므로 VARCHAR 타입을 선택했다. <br/> 
> 만약 이미지 자체(바이너리 데이터)를 저장하려면 BLOB 타입을 사용해야 한다. 

<br/>

## **option**
```sql
CREATE TABLE option (
    id INT(11) NOT NULL AUTO_INCREMENT,
    product_id INT(11) NOT NULL,
    option_name VARCHAR(100) NOT NULL,
    price INT(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id)
    ON DELETE CASCADE
);
```
> **제품과 옵션 테이블을 따로 생성하는 이유**<br/>
> 제품 하나에 여러개의 옵션이 존재하고(1:N), 옵션이 새로 추가 또는 삭제될 수 있기 때문이다. <br/>
> 또한 나중에 옵션 안의 속성들이 변경될 수 있다.

<br/>

> **외래키(FK) 사용 이유** <br/>
> 해당 상품 조회시 옵션도 함께 조회해야하므로 조인(join) 연산이 수행된다. <br/>
> 이때 참조 무결성을 위해서 FK를 사용하였다.  <br/>

**제약조건**
> 외래키로 참조하는 product_id는 변경되는 값이 아니므로, **ON UPDATE** 제약조건은 설정하지 않았다. <br/>
> 상품이 삭제될 경우 옵션도 함께 삭제되도록 **ON DELETE CASCADE** 옵션을 사용했다. 

<br/>

## **cart**
```sql
CREATE TABLE cart (
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    option_id INT(11) NOT NULL,
    quantity INT(11) NOT NULL,
    price INT(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
    ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES option(id)
    ON DELETE CASCADE
);
```
>  사용자는 장바구니에 {옵션, 옵션 개수}를 함께 담는다. <br/>

>  장바구니 조회 기능은 유저 id로 cart 테이블을 조회함으로써 구현된다. <br/>

**제약조건**
> 유저 id와 option id는 변경되지않으므로 **ON DELETE** 제약조건만 **CASCADE**로 설정했다.



<br/>

### <span style="color:pink">**주문상품(item) 테이블**과 **주문(order) 테이블**이 필요한 이유</span>
**주문 과정**
1. 결제하기 버튼을 누른다.
2. 담은 장바구니(cart)들이 주문 상품(item)들로 이동한다.
3. 주문(order)이 생성된다.
4. 장바구니가 비워진다.
<br/>

**테이블 설명**
> **주문 상품(item) 테이블**  <br/>
> 옵션마다 생긴 장바구니(cart)들이 주문되어 <U>실제 주문 상품들</U>이 된다.  
> 장바구니와 구조가 거의 같다.(상품 옵션, 옵션 개수 + 주문 번호)

> **주문(order) 테이블** <br/>
> <U>유저의 주문을 기록</U>하고, <U>빈 장바구니로 갱신</U>하기 위해 필요하다.  <br/>
> 주문 id와 유저 id로 구성되어있다. <br/>
> 주문 후, 주문 테이블의 <U>유저 id</U>를 찾아서 해당 유저가 담았던 장바구니(cart)들을 비운다.

<br/>

✳️ 현재 요구사항에는 구매할 장바구니를 선택하는 기능은 없고, 담은 장바구니 모두를 주문하도록 설계되어있다. <br/>
* 만약 <U>장바구니(cart)들을 선택하여 주문</U>할 수 있다면,
  1. 주문된 장바구니들만 장바구니 테이블에서 제거하거나,
  2. 결제한 주문 상품들(item)의 옵션 번호를 조회하여 장바구니에서 그 옵션번호를 가진 장바구니를 삭제하여 <br/>
장바구니 비우기를 구현할 수 있다.

<br/>

### <span style="color:orange">**유저**와 **주문(order) 테이블**, **주문상품(item) 테이블**의 관계</span>
> 유저와 주문(order)은 **1:N 관계**이고, 주문과 주문 상품(item)도 **1:N 관계**이다. <br/>
> 즉 주문들은 유저로 묶일 수 있고, 주문 상품들은 주문으로 묶일 수 있다. <br/>
> 주문 table은 유저 id를 가지면 되고, 주문 상품 table은 주문 id를 가지면 된다.

<br/>

## **order**
```sql
CREATE TABLE order (
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    delivery_id INT(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
    ON DELETE SET NULL,
    FOREIGN KEY (delivery_id) REFERENCES delivery(id)
    ON UPDATE CASCADE
);
```
> 유저의 주문을 기록하고, 장바구니를 비울 때 사용하는 테이블이다. <br/>

**제약조건**
> 유저가 탈퇴하더라도 **판매자에게 주문 정보가 필요**할 수 있다.<br/> 그러므로 **ON DELETE SET NULL**을 사용하여 탈퇴시 유저 id의 값만 NULL이 되도록 했다.
> <br/> 이를 통해 <U>**데이터 유실을 방지**</U>할 수 있다.

> 배달정보가 수정될때 갱신되도록 **ON UPDATE CASCADE**를 사용했다. <br/>
> 주문이 존재할때 배달 정보가 먼저 삭제될 경우는 없으므로 **ON DELETE**는 옵션을 설정하지 않았다.

<br/>

## **item**
```sql
CREATE TABLE item (
    id INT(11) NOT NULL AUTO_INCREMENT,
    order_id INT(11) NOT NULL,
    option_id INT(11) NOT NULL,
    quantity INT(11) NOT NULL,
    price INT(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES order(id)
    ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES option(id)
    ON DELETE SET NULL,
);
```
> 결제 버튼을 누르면, 담은 장바구니(cart)들을 가져와 주문 상품(item) 테이블에 저장한다.

> 장바구니와 거의 구조가 같다. 

**제약조건**
> 주문(order) 자체가 삭제될 경우, item도 함께 삭제되도록 **ON DELETE CASCADE**를 사용했다.

> 상품 옵션이 삭제되더라도 주문 데이터를 유지하기 위해 **ON DELETE SET NULL**을 사용했다.

<br/>

## **delivery**
```sql
CREATE TABLE delivery (
    id INT(11) NOT NULL AUTO_INCREMENT,
    postal_code INT(11) NOT NULL,
    address varchar(300) NOT NULL,
    instruction varchar(500) NOT NULL,
    fee int(11) NOT NULL
    PRIMARY KEY (id)
);
```
우편번호, 주소, 주문 요청사항, 배달비로 구성되며,
배달정보에 매칭되는 유저 id와 주문 id를 외래키로 참조한다. 

> 요구사항에 없더라도 구현에 필요한 테이블이므로 추가로 작성했다.

유저 id를 외래키로 참조하지 않은 이유 <br/>
> 주문시 주문 table에서 배송 table을 참조하는데, 이때 <U>**주문 table이 유저 id를 이미 참조**</U>하고 있기 때문이다. <br/>
> 또한 해당 **유저의 배달 정보는 주문마다 바뀌므로** 굳이 유저 id를 참조할 필요가 없다.

> 유저가 탈퇴하면 배달정보도 함께 삭제되도록 **ON DELETE CASCADE**를 사용했다.
 
### **2. 연관관계 파악 및 ER 다이어그램 작성**
<br/>

<img src="img/erd.png">

<br/>

### **User - Cart**
1:N 관계
> 유저는 여러 장바구니 {선택 옵션, 옵션 개수}들을 가지고 있다.

<br/>

### **Product - Option**
1:N 관계
> 상품은 여러 개의 옵션을 가지고 있다.

<br/>

### **Cart - Option**
1:1 관계
> 유저의 장바구니 하나는 하나의 옵션을 가지고 있다.

<br/>

### **User - Order**
1:N 관계
> 한명의 유저는 여러 번의 주문을 할 수 있다.

<br/>

### **Order - Item**
1:N 관계
> 주문 하나는 여러개의 결제 상품을 가질 수 있다.

<br/>

### **Option - Item**
1:N 관계
> 하나의 옵션은 여러개의 결제 상품을 가질 수 있다. <br/>

ex1) 유저가 옵션을 재주문했을때 한 옵션에 대해 결제 상품 여러개를 가질 수 있다.<br/>
ex2) 여러명의 유저가 같은 옵션을 구매했을때 한 옵션에 대한 결제상품은 여러개이다.

<br/>

### **Order - Delivery**
1:1 관계
> 주문 하나는 하나의 배송정보를 가질 수 있다.

> 주문시 배송지를 **하나로 고정하는 것 (주문 N:배송지 1) 이 아니라**, <br/>
> <U>**주문마다 배송지를 바꿀 수 있도록(주문 1:배송지 1) 구현**</U>하는게 비지니스적으로 좋을 것 같기 때문이다. <br/>

<br/>


* * * 

<br/>

# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
<br/>
<br/>

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

<br/>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? (예를 들어 배포된 서버는 POST와 GET으로만 구현되었는데, 학생들은 PUT과 DELETE도 배울 예정이라 이부분이 반영되었고, 주소가 RestAPI에 맞게 설계되었는지)
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)

<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_2주차 과제

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

<br/>
<br/>

## <span style="color:7DE5ED">**1. 전체 API 주소 설계**</span>
API주소를 설계하여 README에 내용을 작성하시오.

> 작성된 API를 **Restful한 API**로 변경하기

<br/>

**Restful한 API**란?
```typescript
- REST API의 설계 의도를 명확하게 지킨 API
- URI만 보더라도 리소스를 명확하게 인식할 수 있는 API
- 각 리소스 기능을 HTTP 메서드를 이용하여 일관되게 정의한 API
```
<br/>

**HTTP 메서드**
```typescript
GET : 조회
POST : 생성
PUT : 수정
DELETE : 삭제
```
<br/>

## <span style="color:#068FFF">**상품(product)**</span>
<br/>

### **1. 전체 상품 목록 조회**
* GET
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/products
```
<br/>

### **2. 개별 상품 상세 조회**
* GET
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/products/:id
```
<br/>

## <span style="color:#068FFF">**장바구니(cart)**</span>
<br/>

### **1. 장바구니 조회 (전체 조회)**
* GET
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts
```
* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
<br/>

### **2. 장바구니 담기 (저장)**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts
```
* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

* Request Body
```json
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
<br/>

> **POST** /carts/add ➡️ **POST** /carts <br/>
> - URI에 행위에 대한 동사표현이 들어가면 안된다.
> - 객체명은 복수명사를 사용한다. 

<br/>

### **3. 주문하기 (장바구니 수정)**
* PUT
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/carts
```
* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

* Request Body
```json
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
<br/>

> **POST** /carts/update ➡️ **PUT** /carts <br/>
> - 동사로 표현되는 행위들은 HTTP 메서드를 이용하여 정의한다.

<br/>

## <span style="color:#068FFF">**주문(order)**</span>
<br/>

### **1. 주문 결과 확인**
* GET
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/orders/:id
```
* Request Header
```bash
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
<br/>

### **2. 결제하기 (주문 저장)**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/orders
```
<br/>

> **POST** /orders/save ➡️ **POST** /orders <br/>
> - URI에 행위에 대한 동사표현이 들어가면 안된다.

<br/>

## <span style="color:#068FFF">**유저(user)**</span>
자원보다는 행위에 가까운 API들로 구성

<br/>

### **1. 로그인**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/auth
```
* request body
```json
{
  "email":"ssar@nate.com",
  "password":"meta1234!"
}
```
<br/>

> **POST** /login ➡️ **POST** /auth
> - URI에 행위에 대한 동사표현이 들어가면 안된다.

<br/>

### **2. 회원가입**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/users
```
* Request Body
```json
{
  "username":"meta",
  "email":"meta@nate.com",
  "password":"meta1234!"
}
```
<br/>

> **POST** /join ➡️ **POST** /users
> - URI에 행위에 대한 동사표현이 들어가면 안된다.

<br/>

### **3. 이메일 중복 확인**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/check-email
```
<br/>

> **POST** /check ➡️ **POST** /check-email
> - 회원가입과 이메일 중복 확인 기능은 연관되어있으므로 함께 설계하려고 했으나, <br/>
> restful api에서 / 으로 표현하는 계층관계는 **리소스 포함관계**를 뜻하므로 맞지 않아 분리하였다.
> - check뒤 email을 붙여 **이메일**에 대한 중복확인 의도를 명확하게 전달했다.

<br/>
<br/>

## <span style="color:7DE5ED">**2. Mock API Controller 구현**</span>
가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.
<br/>





<br/>


-----------------------------------------------------
<br/>

# 3주차

카카오 테크 캠퍼스 2단계 - BE - 3주차 클론 과제
<br/>
<br/>

## **과제명**
```
1. 레포지토리 단위테스트
```

## **과제 설명**
```
1. 레포지토리 단위테스트를 구현하여 소스코드를 제출하시오.
2. 쿼리를 테스트하면서 가장 좋은 쿼리를 작성해보시오.
```

<br/>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 레포지토리 단위테스트가 구현되었는가?
>- 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
>- Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
>- 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_3주차 과제

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 4주차

카카오 테크 캠퍼스 2단계 - BE - 4주차 클론 과제
<br/>
<br/>

## **과제명**
```
1. 컨트롤러 단위 테스트
```

## **과제 설명**
```
1. 컨트롤러 단위테스트를 작성한뒤 소스코드를 업로드하시오.
2. stub을 구현하시오.
```

<br/>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 컨트롤러 단위테스트가 구현되었는가?
>- Mockito를 이용하여 stub을 구현하였는가?
>- 인증이 필요한 컨트롤러를 테스트할 수 있는가?
>- 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_4주차 과제 

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 5주차

카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제
<br/>
<br/>

## **과제명**
```
1. 실패 단위 테스트
```

## **과제 설명**
```
1. 컨트롤러 단위테스트를 구현하는데, 실패 테스트 코드를 구현하시오.
2. 어떤 문제가 발생할 수 있을지 모든 시나리오를 생각해본 뒤, 실패에 대한 모든 테스트를 구현하시오.
```

<br/>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 실패 단위 테스트가 구현되었는가?
>- 모든 예외에 대한 실패 테스트가 구현되었는가?
<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_5주차 과제

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 6주차

카카오 테크 캠퍼스 2단계 - BE - 6주차 클론 과제
<br/>
<br/>

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

<br/>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 통합테스트가 구현되었는가?
>- API문서가 구현되었는가?
>- 배포가 정상적으로 되었는가?
>- 서비스에 문제가 발생했을 때, 로그를 통해 문제를 확인할 수 있는가?
<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_6주차 과제

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
