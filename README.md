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
    roles VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (id)
);
```
> PK는 NOT NULL과 UNIQUE 제약조건을 가진다. 

> email은 로그인 아이디로 사용되어 유저마다 고유해야하므로 UNIQUE 제약조건을 두었다. 

> 프로젝트의 db에 많은 양의 데이터가 들어가지 않으므로, <br/>
> id의 타입은 BIGINT가 아닌 INT를 사용했다.

> INT로 선언하면 INT(11)로 선언하는 것과 같다. <br/> 
> INT는 10자리이지만 음수까지 표현하기 위해 11자리가 default이다.

> roles 필드를 선선하여 사용자의 역할을 표시한다.
> 기본값은 NULL이며, 일반 회원을 뜻한다.
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
    product_id INT(11) DEFAULT NULL,
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
>- User 도메인을 제외한 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? POST와 GET으로만 구현되어 있어도 됨.
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
>- DTO에 타입은 올바르게 지정되었는가?
>- DTO에 이름은 일관성이 있는가? (예를 들어 어떤 것은 JoinDTO, 어떤 것은 joinDto, 어떤 것은 DtoJoin 이런식으로 되어 있으면 일관성이 없는것이다)
>- DTO를 공유해서 쓰면 안된다 (동일한 데이터가 응답된다 하더라도, 화면은 수시로 변경될 수 있기 때문에 DTO를 공유하고 있으면 배점을 받지 못함)
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

## <span style="color:#7DE5ED">**1. 전체 API 주소 설계**</span>
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
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/login
```
* request body
```json
{
  "email":"ssar@nate.com",
  "password":"meta1234!"
}
```
<br/>

**login을 다른 단어로 수정하지 않은 이유**
> login은 동사이므로 Restful하지 않지만,</br>
> <U>**URI를 명확하게 정의할 수 있으므로** REST의 목적에 부합</U>하다.

> 과제 명세에서도 User 도메인을 제외하고 Restful하게 설계하라고 하셨다.

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
<br/>

> join은 동사이지만 **직관적으로 URI를 파악**하기 쉬우므로 </br>Rest 원칙에 어느정도 부합한다고 생각하여 변경하지 않았다.

<br/>

### **3. 이메일 중복 확인**
* POST
```bash
http://kakao-app-env.eba-kfsgeb74.ap-northeast-2.elasticbeanstalk.com/check-email
```
<br/>

> **POST** /check ➡️ **POST** /check-email
> - check뒤 email을 붙여 <U>**이메일**에 대한 중복확인</U> 의도를 명확하게 전달했다.

> 회원가입과 이메일 중복 확인 기능은 연관되어있으므로 함께 설계하려고 했으나, <br/>
> restful한 api에서 / 으로 표현하는 계층관계는 **리소스 포함관계**를 뜻하므로 맞지 않아 분리하였다.


<br/>
<br/>

## <span style="color:#7DE5ED">**2. Mock API Controller 구현**</span>
가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.
## <span style="color:#068FFF">**상품(product)**</span>
<br/>

### **1. 전체 상품 목록 조회 - MOCK**
#### **DTO**
```java
@Getter @Setter
public class ProductRespFindAllDTO {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;

    @Builder
    public ProductRespFindAllDTO(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
```
</br>

#### **Controller**
```java
@RestController
public class ProductRestController {

    @GetMapping("/products")
    public ResponseEntity<?> findAll() {
        List<ProductRespFindAllDTO> responseDTO = new ArrayList<>();

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto1 = ProductRespFindAllDTO.builder()
                .id(1)
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .description("")
                .image("/images/1.jpg")
                .price(1000)
                .build();
        //담기
        responseDTO.add(dto1);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto2 = ProductRespFindAllDTO.builder()
                .id(2)
                .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                .description("")
                .image("/images/2.jpg")
                .price(2000)
                .build();
        //담기
        responseDTO.add(dto2);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto3 = ProductRespFindAllDTO.builder()
                .id(3)
                .productName("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!")
                .description("")
                .image("/images/3.jpg")
                .price(30000)
                .build();
        //담기
        responseDTO.add(dto2);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto4 = ProductRespFindAllDTO.builder()
                .id(4)
                .productName("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종")
                .description("")
                .image("/images/4.jpg")
                .price(4000)
                .build();
        //담기
        responseDTO.add(dto4);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto5 = ProductRespFindAllDTO.builder()
                .id(5)
                .productName("[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주")
                .description("")
                .image("/images/5.jpg")
                .price(5000)
                .build();
        //담기
        responseDTO.add(dto5);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto6 = ProductRespFindAllDTO.builder()
                .id(6)
                .productName("굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전")
                .description("")
                .image("/images/6.jpg")
                .price(15900)
                .build();
        //담기
        responseDTO.add(dto6);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto7 = ProductRespFindAllDTO.builder()
                .id(7)
                .productName("eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제")
                .description("")
                .image("/images/7.jpg")
                .price(26800)
                .build();
        //담기
        responseDTO.add(dto7);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto8 = ProductRespFindAllDTO.builder()
                .id(8)
                .productName("제나벨 PDRN 크림 2개. 피부보습/진정 케어")
                .description("")
                .image("/images/8.jpg")
                .price(25900)
                .build();
        //담기
        responseDTO.add(dto8);

        // 상품 하나씩 집어넣기
        ProductRespFindAllDTO dto9 = ProductRespFindAllDTO.builder()
                .id(9)
                .productName("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감")
                .description("")
                .image("/images/9.jpg")
                .price(797000)
                .build();
        //담기
        responseDTO.add(dto9);
        
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}

```

<br/>

> 생성자에 들어갈 인자가 많으므로,  생성자에서 **builder 패턴**으로 바꾸어 생성했다.

**Mock Test**
```java
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("전체 상품 목록 조회")
    public void findAll_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
        resultActions.andExpect(jsonPath("$.response[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        resultActions.andExpect(jsonPath("$.response[1].description").value(""));
        resultActions.andExpect(jsonPath("$.response[1].image").value("/images/2.jpg"));
        resultActions.andExpect(jsonPath("$.response[1].price").value(2000));
    }
}
```

</br>

### **2. 개별 상품 상세 조회 - MOCK**

<br/>

#### **DTO**

ProductRespFindAllDTO
```java
@Getter @Setter
public class ProductRespFindAllDTO {

    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;

    @Builder
    public ProductRespFindAllDTO(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
```
ProductOptionDTO
```java
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


<br/>

#### **Controller**
```java
@RestController
public class ProductRestController {
@GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 상품을 담을 DTO 생성
        ProductRespFindByIdDTO responseDTO = null;

        if(id == 1) {
            List<ProductOptionDTO> optionDTOList = new ArrayList<>();
            optionDTOList.add(
                    ProductOptionDTO.builder()
                    .id(1)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .price(10000)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(2)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .price(10900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(3)
                    .optionName("고무장갑 베이지 S(소형) 6팩")
                    .price(9900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(4)
                    .optionName("뽑아쓰는 키친타올 130매 12팩")
                    .price(16900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(5)
                    .optionName("2겹 식빵수세미 6매")
                    .price(8900)
                    .build());

            responseDTO = ProductRespFindByIdDTO.builder()
                    .id(1)
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .description("")
                    .image("/images/1.jpg")
                    .price(1000)
                    .starCount(5)
                    .options(optionDTOList)
                    .build();

        }else if(id == 2){
            List<ProductOptionDTO> optionDTOList = new ArrayList<>();
            optionDTOList.add(ProductOptionDTO.builder()
                    .id(6)
                    .optionName("22년산 햇단밤 700g(한정판매)")
                    .price(9900)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(7)
                    .optionName("22년산 햇단밤 1kg(한정판매)")
                    .price(14500)
                    .build());

            optionDTOList.add(ProductOptionDTO.builder()
                    .id(8)
                    .optionName("밤깎기+다회용 구이판 세트")
                    .price(5500)
                    .build());

            responseDTO = ProductRespFindByIdDTO.builder()
                    .id(1)
                    .productName("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율")
                    .description("")
                    .image("/images/2.jpg")
                    .price(2000)
                    .starCount(5)
                    .options(optionDTOList)
                    .build();

        }else { //id가 1,2가 아닐 경우
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
```
> Builder 패턴으로 수정했다.


<br/>

**Mock Test**
```java
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;
 @Test
    @DisplayName("개별 상품 상세 조회")
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.description").value(""));
        resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response.price").value(1000));
        resultActions.andExpect(jsonPath("$.response.options[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.options[0].price").value(10000));
        resultActions.andExpect(jsonPath("$.response.options[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.options[1].price").value(10900));
    }

}
```

</br>

## <span style="color:#068FFF">**장바구니(cart)**</span>

### **1. 장바구니 조회 - Mock**


<br/>

#### **DTO**
CartRespFindAllDTO
```java
@Getter @Setter
public class CartRespFindAllDTO {
    private List<ProductDTO> products;
    private int totalPrice;

    @Builder
    public CartRespFindAllDTO(List<ProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
```
ProductDTO
```java
@Getter @Setter
public class ProductDTO {
    private int id;
    private String productName;
    private List<CartItemDTO> cartItems;

    @Builder
    public ProductDTO(int id, String productName, List<CartItemDTO> cartItems) {
        this.id = id;
        this.productName = productName;
        this.cartItems = cartItems;
    }
}
```
CartItemDTO
```java
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
ProductOptionDTO
```java
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

<br/>

#### **Controller**

```java
@RestController
public class CartRestController {

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
        List<ProductDTO> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                ProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
```

<br/>

#### **Mock Test**
```java
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    @DisplayName("장바구니 조회")
    public void findAll_test() throws Exception {

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(104500));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].cartItems[0].price").value(50000));

    }
}
```

<br/>

### **2. 장바구니 담기 - Mock**


<br/>

#### **DTO + Controller**

```java
@RestController
@RequestMapping("/carts")
public class CartRestController {
//장바구니 담기
    @PostMapping("/add") // /carts/add
    public ResponseEntity<?> addCart(@RequestBody List<CartDTO> request) { //리스트 형식으로 요청받음
        return ResponseEntity.ok(ApiUtils.success(null));
    }
    @Data
    static class CartDTO{
        private int optionId;
        private int quantity;

        @Builder
        public CartDTO(int optionId, int quantity) {
            this.optionId = optionId;
            this.quantity = quantity;
        }
    }
}
```
> DTO를 **Inner static class**로 작성하여 DTO 클래스가 너무 많아지는 것을 방지했습니다. 

> 생성자의 파라미터 필드에 대해서만 빌더 메서드를 생성하고자 </br> **생성자 레벨 Builder**를 사용했습니다.

<br/>

#### **Mock Test**
```java
@Test
@WithMockUser
@DisplayName("장바구니 담기")
public void addCart_test() throws Exception {
    //given
    List<CartRestController.CartDTO> cartDTOList = new ArrayList<>();
    CartRestController.CartDTO cartDTO1 = new CartRestController.CartDTO(1,5);
    CartRestController.CartDTO cartDTO2 = new CartRestController.CartDTO(2,5);
    cartDTOList.add(cartDTO1);
    cartDTOList.add(cartDTO2);

    ObjectMapper objectMapper = new ObjectMapper();
    String requestData = objectMapper.writeValueAsString(cartDTOList);
    System.out.println(requestData);

    // when
    ResultActions resultActions = mvc.perform(
            post("/carts/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestData)
    );

    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // verify
    resultActions.andExpect(jsonPath("$.success").value("true"));
    resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
    resultActions.andExpect(jsonPath("$.error").doesNotExist());
}
    
```

<br/>

### **3. 주문하기(장바구니 수정) - Mock**

<br/>

#### **DTO + Controller**

```java
@RestController
@RequestMapping("/carts")
public class CartRestController {
//장바구니 수정
    @PostMapping("/update") //  /carts/update
    public ResponseEntity<?> updateCart(@RequestBody List<CartUpdateRequestDTO> request) {
        // 카트 Info 리스트 만들기
        List<CartInfoDTO> cartInfoDTOList = new ArrayList<>();

        // 카트 Info 리스트에 담기
        CartInfoDTO cartInfoDTO1 = CartInfoDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartInfoDTOList.add(cartInfoDTO1);

        CartInfoDTO cartInfoDTO2 = CartInfoDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartInfoDTOList.add(cartInfoDTO2);

        //responseDTO 만들기
        CartUpdateResponseDTO responseDTO = CartUpdateResponseDTO.builder()
                .carts(cartInfoDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    @Data
    static class CartUpdateRequestDTO{
        private int cartId;
        private int quantity;

        @Builder
        public CartUpdateRequestDTO(int cartId, int quantity) {
            this.cartId = cartId;
            this.quantity = quantity;
        }
    }

    @Data
    static class CartUpdateResponseDTO{
        private List<CartInfoDTO> carts;
        private int totalPrice;

        @Builder
        public CartUpdateResponseDTO(List<CartInfoDTO> carts, int totalPrice) {
            this.carts = carts;
            this.totalPrice = totalPrice;
        }
    }
    @Data
    static class CartInfoDTO{
        private int cartId;
        private int optionId;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public CartInfoDTO(int cartId, int optionId, String optionName, int quantity, int price) {
            this.cartId = cartId;
            this.optionId = optionId;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
```

<br/>

#### **Mock Test**

```java
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DisplayName("장바구니 수정")
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;
@Test
    @WithMockUser
    // 장바구니 수정
    public void updateCart_test() throws Exception {
        //given
        List<CartRestController.CartUpdateRequestDTO> cartList = new ArrayList<>();
        CartRestController.CartUpdateRequestDTO cartDTO1 = new CartRestController.CartUpdateRequestDTO(4,10);
        CartRestController.CartUpdateRequestDTO cartDTO2 = new CartRestController.CartUpdateRequestDTO(5,10);
        cartList.add(cartDTO1);
        cartList.add(cartDTO2);
        //JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(cartList);
        
        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));

        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value(4));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value(1));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));

        resultActions.andExpect(jsonPath("$.response.carts[1].cartId").value(5));
        resultActions.andExpect(jsonPath("$.response.carts[1].optionId").value(2));
        resultActions.andExpect(jsonPath("$.response.carts[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.carts[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[1].price").value(109000));

        resultActions.andExpect(jsonPath("$.error").doesNotExist());
    }
}
```

<br/>

## <span style="color:#068FFF">**주문(Order)**</span>
</br>

### **1. 주문 결과 확인 - Mock**

<br/>

#### **DTO + Controller**

```java
@RestController
@RequestMapping("/orders")
public class OrderRestController {
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 주문을 담을 DTO 생성
        OrderRespFindByIdDTO responseDTO = null;

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
            responseDTO = OrderRespFindByIdDTO.builder()
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

    @Data
    static class OrderRespFindByIdDTO{
        private int id;
        private List<ProductItemDTO> products;
        private int totalPrice;

        @Builder
        public OrderRespFindByIdDTO(int id, List<ProductItemDTO> products, int totalPrice) {
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
```

<br/>

#### **Mock Test**

```java
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DisplayName("주문 결과 확인")
class OrderRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Test
    @WithMockUser //인증된 사용자 생성
    // 주문 결과 확인
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(100000));

        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(109000));

    }
    
}
```
> 주문은 회원만 가능하므로, **@WithMockUser**를 사용하여 인증된 상태로 테스트했다.

</br>

### **2. 결제하기 (주문 저장하기) - Mock**

<br/>

#### **Controller**

```java
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
```
> **주문 결과 확인**과 **결제하기**는 같은 응답구조를 가지므로 동일한 DTO를 사용했다. 

<br/>

#### **Mock Test**
```java
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DisplayName("결제하기(주문 인서트)")
class OrderRestControllerTest {
    @Autowired
    private MockMvc mvc;
 @Test
    @WithMockUser //인증된 사용자 생성
    // 결제하기(주문 인서트)
    public void saveOrder_test() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(209000));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(100000));

        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(109000));
    }
}
```

<br/>

## <span style="color:#068FFF">**유저(User)**</span>
</br>

### **1. 로그인**

<br/>

#### **DTO**
```java
public class UserRequest {
    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }
}

```
<br/>

#### **Controller**

```java
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
@PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader(value = "Authorization", required = false) String authHeader, @RequestBody UserRequest.LoginDTO loginDTO) {
            //검증 단계
            String email = loginDTO.getEmail();
            String password = loginDTO.getPassword();
            //올바른 이메일 형식인지 확인
            if (!email.contains("@"))
                return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email", HttpStatus.BAD_REQUEST));
            //유효한 비밀번호인지 확인
            if (!isValidPassword(password))
                return ResponseEntity.badRequest().body(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password", HttpStatus.BAD_REQUEST));
            //인증 확인
            if (authHeader == null || authHeader.isEmpty())
                return ResponseEntity.badRequest().body(ApiUtils.error("인증되지 않았습니다", HttpStatus.UNAUTHORIZED));
            //비밀번호 길이 검증
            int passwordLength = loginDTO.getPassword().length();
            if(!(passwordLength>=8 && passwordLength <= 20))
                return ResponseEntity.badRequest().body(ApiUtils.error("8에서 20자 이내여야 합니다.:password", HttpStatus.BAD_REQUEST));

            //로그인 수행
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            Authentication authentication;
            //로그인 성공, 실패 여부 확인
            try {
                authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body(ApiUtils.error("email 또는 password가 올바르지 않습니다", HttpStatus.BAD_REQUEST));
            }
            CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = JWTProvider.create(myUserDetails.getUser());

            //로그인 성공
            return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

    //비밀번호 유효성 검사
    private boolean isValidPassword(String password) {
        boolean hasLetter = false; //문자 여부
        boolean hasDigit = false; //숫자 여부
        boolean hasSpecialCharacter = false; //특수문자 여부
        for (char c:password.toCharArray()){
            if(Character.isLetter(c)) hasLetter=true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (isSpecialCharacter(c)) hasSpecialCharacter = true;
            if(hasLetter && hasDigit && hasSpecialCharacter) break;
        }
        //문자,숫자,특수문자가 있어야하고, 공백이 없어야한다.
        return hasLetter && hasDigit && hasSpecialCharacter && !password.contains(" ");
    }
    //특수 문자 포함하는지 확인
    private boolean isSpecialCharacter(char c) {
        String specialCharacters = "!@#$%^&*()-_=+[]{};:'\"\\|<>,.?/~`";
        return specialCharacters.contains(String.valueOf(c));
    }
}
```
> 로그인 성공, 로그인 실패(형식 / 문자 / 인증 / 비밀번호 길이)의 경우를 구현했다.

> API 문서에는 기능이 구현되어있지만, 문자 포함 여부와 비밀번호 길이 검증은 회원가입시 이미 검증하므로 필요없을 것 같다.

> 로그인 시마다 jwt 토큰은 항상 달라지는데, 요청시 토큰을 필요로 하는 이유가 궁금하다. 

</br>

#### **Mock Test**

```java
@Transactional //테스트 후 rollback
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext context;

    //Spring Security 테스트 환경 구성
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }
@Test
    @WithMockUser
    @DisplayName("로그인 성공(가입된 id와 비밀번호)")
    public void login_success_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user1@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);

        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("user1@nate.com");
        loginDTO.setPassword("user1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                post("/login")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData))
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 -가입된 id와 잘못된 비밀번호")
    public void login_fail_pw_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("user@nate.com");
        loginDTO.setPassword("wrongpassword!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                post("/login")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData))
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 존재하지 않는 id와 비밀번호 (미가입)")
    public void login_fail_unregistered_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com"); //이미 존재하는 id
        loginDTO.setPassword("fake1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                post("/login")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData))
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("false"));

    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 이메일 형식 검증")
    public void login_fail_email_format_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newusernate.com"); //올바르지 않은 이메일 (@가 없음)
        loginDTO.setPassword("user1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                    post("/login")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 비밀번호 글자 검증")
    public void login_fail_password_character_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com");
        loginDTO.setPassword("user1234"); //특수문자가 없는 비밀번호
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);
        //jwt Token
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzMzZAbmF0ZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjEsImV4cCI6MTY4ODg5ODkxNn0.2ovT4QRQHAKFsjHZG1g_bFwC3RN9-3TxdgS_gMm3FKVstqrqPrw6C0VZEwmh5buZzz3ek3Ez_Z3IsNqiVnONcQ";

        //when
        mvc.perform(
                    post("/login")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }
    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 인증되지 않은 유저")
    public void login_fail_unauth_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com");
        loginDTO.setPassword("user1234!");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);

        //when
        mvc.perform( //토큰 보내지 않음
                    post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 비밀번호 글자수")
    public void login_fail_password_length_test() throws Exception {
        //given
        //요청 body
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("newuser@nate.com");
        loginDTO.setPassword("us4!"); //적은 글자수의 비밀번호
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(loginDTO);

        //when
        mvc.perform( //토큰 보내지 않음
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }
}

```
> **Test Case**
> - **로그인 성공** (가입된 id와 비밀번호)
> * **로그인 실패** 
>   - 가입된 id와 <U>잘못된 비밀번호</U> 
>   - <U>존재하지 않는 id</U>와 비밀번호 (미가입)
> * 형식, 중복 검증
>   - 올바르지 않은 이메일
>   - 이메일 형식 검증 (@가 없음)
>   - 비밀번호 글자 검증(영문, 숫자, 특수문자 포함, 공백 포함X)
>   - 중복 이메일 검증
>   - 비밀번호 글자수 제한 검증

> **@Transactional** 어노테이션을 붙여 테스트 후 rollback 되도록 하였다.

</br>

### **2. 회원가입**
<br/>

#### **DTO**
```java
public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String email;
        private String password;
        private String username;
    }
}
```

<br/>

#### **Controller**
```java
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        //검증
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        //올바른 이메일 형식인지 확인
        if (!email.contains("@"))
            return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email", HttpStatus.BAD_REQUEST));

        //유효한 비밀번호인지 확인
        if (!isValidPassword(password))
            return ResponseEntity.badRequest().body(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password", HttpStatus.BAD_REQUEST));

        //동일한 이메일이 존재하는지 확인
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail(email);
        ResponseEntity<?> responseEntity = check(checkEmailDTO); //check 메서드 사용
        boolean isSuccessful = responseEntity.getStatusCode().is2xxSuccessful();
        if (!isSuccessful)
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다 : "+email, HttpStatus.BAD_REQUEST));

        //비밀번호 길이 검증
        int passwordLength = password.length();
        if(!(passwordLength>=8 && passwordLength <= 20))
            return ResponseEntity.badRequest().body(ApiUtils.error("8에서 20자 이내여야 합니다.:password", HttpStatus.BAD_REQUEST));

        //회원가입 성공
        //유저 생성
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();
        //repo에 저장
        userRepository.save(user);
        return ResponseEntity.ok(ApiUtils.success(null));
    }
        private boolean isValidPassword(String password) {
        boolean hasLetter = false; //문자 여부
        boolean hasDigit = false; //숫자 여부
        boolean hasSpecialCharacter = false; //특수문자 여부
        for (char c:password.toCharArray()){
            if(Character.isLetter(c)) hasLetter=true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (isSpecialCharacter(c)) hasSpecialCharacter = true;
            if(hasLetter && hasDigit && hasSpecialCharacter) break;
        }
        //문자,숫자,특수문자가 있어야하고, 공백이 없어야한다.
        return hasLetter && hasDigit && hasSpecialCharacter && !password.contains(" ");
    }
    //특수 문자 포함하는지 확인
    private boolean isSpecialCharacter(char c) {
        String specialCharacters = "!@#$%^&*()-_=+[]{};:'\"\\|<>,.?/~`";
        return specialCharacters.contains(String.valueOf(c));
    }
}
```
> 회원가입시 검증코드를 작성했습니다.

<br/>


**Mock Test**
```java
@Transactional //테스트 후 rollback
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext context;

    //Spring Security 테스트 환경 구성
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    //회원가입 요청 메서드
    private ResultActions doPerform(String requestData) throws Exception {
        return mvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestData));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입 성공(가입된 id와 비밀번호)")
    public void join_success_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("newuser1234!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-올바르지않은 이메일")
    public void join_fail_email_format_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newusernate.com"); //@가 없는 올바르지 않은 이메일
        joinDTO.setPassword("newuser1234!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-비밀번호 검증")
    public void join_fail_password_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("newuser1234"); //특수문자가 없는 비밀번호
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-중복 이메일 검증")
    public void join_fail_email_duplicated_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("newuser1234!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        doPerform(requestData)
                .andExpect(jsonPath("$.success").value("true"));
        //중복 이메일
        doPerform(requestData)
                .andDo(print()) //결과 출력
                //then
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입-글자수 검증")
    public void join_fail_password_length_test() throws Exception {
        //given
        //유저 생성
        UserRequest.JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUsername("newuser");
        joinDTO.setEmail("newuser@nate.com");
        joinDTO.setPassword("new12!");
        //JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(joinDTO);

        //when
        //중복 이메일
        doPerform(requestData)
                .andDo(print()) //결과 출력
        //then
                .andExpect(jsonPath("$.success").value("false"));
    }
}
```
> **Test Case**
> - **회원가입 성공** (가입되지않은 id와 비밀번호)
> - **회원가입 실패** 
>      * 이메일 형식 검증 (@가 없음)
>      * 비밀번호 글자 검증(영문, 숫자, 특수문자 포함, 공백 포함X)
>      * 중복 이메일 검증
>      * 비밀번호 글자수 제한 검증

</br>

### **3. 이메일 중복 확인**
<br/>

#### **DTO**
```java
public class UserRequest {
    @Getter
    @Setter
    public static class CheckEmailDTO {
        private String email;
    }
}
```
<br/>

#### **Controller**
```java
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserJPARepository userRepository;
 @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody UserRequest.CheckEmailDTO emailDTO) {
        //요청 email 얻기
        String email = emailDTO.getEmail();

        //repository에서 email이 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);

        if (byEmail.isPresent()) { //email이 이미 존재하면
            return ResponseEntity.badRequest().body(ApiUtils.error("동일한 이메일이 존재합니다:email ", HttpStatus.BAD_REQUEST));
        } else { //email 중복이 아님
            if (email.contains("@")) //이메일 형식이면
                return ResponseEntity.ok(ApiUtils.success(null));
            else
                return ResponseEntity.badRequest().body(ApiUtils.error("이메일 형식으로 작성해주세요:email", HttpStatus.BAD_REQUEST));

        }
    }

}
```
>이메일 중복 확인, 형식 확인을 하는 API이다.

<br/>

**Mock Test**
```java
@Transactional //테스트 후 rollback
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext context;

    //Spring Security 테스트 환경 구성
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }
    @Test
    @WithMockUser
    @DisplayName("이메일 확인 테스트 - 이미 존재하는 email")
    public void check_fail_duplicated_test() throws Exception {
        //given
        //user 생성
        User user = User.builder()
                .email("user1@nate.com")
                .password(passwordEncoder.encode("user1234!"))
                .username("user")
                .roles("ROLE_USER")
                .build();
        //저장
        userJPARepository.save(user);

        //요청 body
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail("user1@nate.com"); //존재하는 email
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(checkEmailDTO);

        //when
        mvc.perform(
                        post("/check")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
                .andDo(print()) //결과 출력
        // verify
                .andExpect(jsonPath("$.success").value("false"));
    }

    @Test
    @WithMockUser
    @DisplayName("이메일 확인 테스트 - 올바르지않은 형식")
    public void check_fail_format_test() throws Exception {
        //given
        //요청 body
        UserRequest.CheckEmailDTO checkEmailDTO = new UserRequest.CheckEmailDTO();
        checkEmailDTO.setEmail("user1nate.com"); //올바르지않은 형식(@가 없음)
        ObjectMapper objectMapper = new ObjectMapper();
        String requestData = objectMapper.writeValueAsString(checkEmailDTO);

        //when
        mvc.perform(
                        post("/check")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestData))
                .andDo(print()) //결과 출력
                // verify
                .andExpect(jsonPath("$.success").value("false"))
                .andExpect(jsonPath("$.error.status").value(400)); //400번 에러
    }
}
```
> **Test Case**
> - **이메일 중복** (이미 가입된 email)
> * **잘못된 이메일 형식** (@가 없는 email)


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
>- BDD 패턴으로 구현되었는가? given, when, then
<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_3주차 과제

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

<br/>
<br/>

## **1. 레포지토리 단위테스트**

### **기본 batch fetch size**
> 조회 성능 개선을 위해 **default_batch_fetch_size: 100**으로 설정한다.

<br/>

## <span style="color:#068FFF">**상품(product)**</span>
<br/>

### **Test SetUp**
* Test
```java
@Import(ObjectMapper.class)
@DataJpaTest
public class ProductJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList()); //상품 리스트 저장
        optionJPARepository.saveAll(optionDummyList(productListPS)); //옵션 리스트 저장
        em.clear(); 
    }
```
> 필요한 의존객체 타입을 주입한다.

> Dummy Data를 Repository에 저장한다.

> 쿼리를 보기 위해 영속성 컨텍스트를 clear한다.

<br/>

* optionJPARepository
```java
public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    List<Option> findByProductId(@Param("productId") int productId);
    Optional<Option> findById(int id);

    // findById_select_product_lazy_error_fix_test
    @Query("select o from Option o join fetch o.product where o.product.id = :productId") //fetch join
    List<Option> mFindByProductId(@Param("productId") int productId);
}
```
> fetch join 사용하는 쿼리가 존재한다.

<br/>

### **1. 전체 상품 목록 조회**
* Test
```java
@Test
@DisplayName("1. 전체 상품 목록 조회")
public void product_findAll_test() throws JsonProcessingException {
    // given
    int page = 0;
    int size = 9;

    // when
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Product> productPG = productJPARepository.findAll(pageRequest);
    String responseBody = om.writeValueAsString(productPG); //직렬화하여 출력
    System.out.println("테스트 : "+responseBody);

    // then
    Assertions.assertThat(productPG.getTotalPages()).isEqualTo(2); //2페이지
    Assertions.assertThat(productPG.getSize()).isEqualTo(9); //한 페이지당 9개 상품 보임
    Assertions.assertThat(productPG.getNumber()).isEqualTo(0); //첫번째 페이지
    Assertions.assertThat(productPG.getTotalElements()).isEqualTo(15); //상품 개수
    Assertions.assertThat(productPG.isFirst()).isEqualTo(true); //첫번째 페이지인지 확인
    Assertions.assertThat(productPG.getContent().get(0).getId()).isEqualTo(1);
    Assertions.assertThat(productPG.getContent().get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    Assertions.assertThat(productPG.getContent().get(0).getDescription()).isEqualTo("");
    Assertions.assertThat(productPG.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
    Assertions.assertThat(productPG.getContent().get(0).getPrice()).isEqualTo(1000);
}
```
> paging되어 조회되도록 구현된다.

<br/>

### **2. 개별 상품 상세 조회**
* Test
```java
@Test
@DisplayName("2. 개별 상품 상세 조회 : Lazy")
public void option_mFindByProductId_lazy_test() throws JsonProcessingException {
    // given
    int id = 1;

    // when
    List<Option> optionListPS = optionJPARepository.mFindByProductId(id); 

    System.out.println("json 직렬화 직전========================");
    String responseBody = om.writeValueAsString(optionListPS);
    System.out.println("테스트 : "+responseBody);

    // then
}
```
> 연관관계 객체의 FetchType이 Lazy로 설정되어있다.
> fetch join 사용해서 한번에 연관관계 객체를 영속 컨텍스트로 가져온다.

<br/>

## <span style="color:#068FFF">**장바구니(cart)**</span>
<br/>

###  **Test SetUp**
* Test
```java
@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;
    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        List<Product> productListPS = productJPARepository.saveAll(productDummyList()); //상품 리스트 저장
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS)); //옵션 리스트 저장
        userJPARepository.save(userDummy);
        cartJPARepository.saveAll(cartDummyList(optionListPS)); //장바구니 리스트 저장
        em.clear(); //쿼리 보기 위해 PC clear
    }
}
```
> 매 테스트마다 cart 테이블의 id가 1부터 시작되어 저장되도록 하여 검증시 편리하도록 한다.

<br/>


* CartJPARepository
```java
public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.user join fetch c.option")
    List<Cart> mFindFetchAll();

    @Query("select c from Cart c join fetch c.user join fetch c.option where c.id = :cartId")
    Optional<Cart> mFindById(@Param("cartId") int cartId);

}
```
> fetch join 쿼리를 작성하여 N+1문제를 예방한다.

</br>

### **1. 장바구니 조회 (전체 조회)**
* Test
```java
@Test
@DisplayName("1. 장바구니 조회 (전체 조회)")
public void cart_findAll_test() throws JsonProcessingException {
    // given

    // when
    List<Cart> cartPG = cartJPARepository.mFindFetchAll();
    String responseBody = om.writeValueAsString(cartPG); //직렬화하여 출력
    System.out.println("테스트 : "+responseBody);

    // then
    Assertions.assertThat(cartPG.get(0).getId()).isEqualTo(1);
    Assertions.assertThat(cartPG.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    Assertions.assertThat(cartPG.get(0).getOption().getPrice()).isEqualTo(10000);
    Assertions.assertThat(cartPG.get(0).getQuantity()).isEqualTo(5);
}
```
> select문 총 2번 수행(1번: join fetch, 1번:option의 product 조회)

<br/>

### **2. 장바구니 담기 (저장)**
* Test
```java
@Test
@DisplayName("2. 장바구니 담기 (저장)")
public void add_cart_test() throws JsonProcessingException {
    // given
    System.out.println("장바구니 담기 전");
    Cart cart = newCart(userDummy, optionDummyList(productDummyList()).get(0), 3);
    // when
    cartJPARepository.save(cart); //저장
    String responseBody = om.writeValueAsString(cart); //직렬화하여 출력
    System.out.println("테스트 : "+responseBody);

    // then
    Assertions.assertThat(cart.getId()).isEqualTo(3); //dummy로 cart 이미 2개 존재하므로 id는 3
    Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    Assertions.assertThat(cart.getOption().getPrice()).isEqualTo(10000);
    Assertions.assertThat(cart.getQuantity()).isEqualTo(3);
}
```
> insert 쿼리 1번 수행

<br/>

### **3. 주문하기 (장바구니 수정)**
* Test
```java
@Test
@DisplayName("3.주문하기 (장바구니 수정)")
public void update_cart_test() throws JsonProcessingException {
    // given

    // when
    Optional<Cart> cartOptional = cartJPARepository.mFindById(1);
    if(cartOptional.isPresent()){
        Cart c = cartOptional.get();
        c.update(10); //장바구니 수정
        em.flush(); //DB 반영
        String responseBody = om.writeValueAsString(c); //직렬화하여 출력
        System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(c.getId()).isEqualTo(1);
        Assertions.assertThat(c.getQuantity()).isEqualTo(10);

    }

}
```
> select 쿼리 2번 (join fetch 1번+ option의 Product 조회 1번) + update 쿼리 1번 수행

<br/>

## <span style="color:#068FFF">**주문(order)**</span>
<br/>

### **Test SetUp**
```java
@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        userJPARepository.save(userDummy); //유저 저장
        orderJPARepository.saveAll(orderDummyList()); //주문 리스트 저장
        itemJPARepository.saveAll(itemDummyList(cartDummyList(optionDummyList(productDummyList())))); //아이템 리스트 저장
        em.clear(); //쿼리 보기 위해 PC clear
    }
}
```
* OrderJPARepository
```java
public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o join fetch o.user")
    List<Order> mFindFetchAll();

}
```

</br>

### **1. 주문 결과 확인**
* Test
```java
@Test
@DisplayName("1. 주문 결과 확인")
public void order_findAll_test() throws JsonProcessingException {
    // given

    // when
    List<Order> orderList = orderJPARepository.mFindFetchAll(); //join fetch하여 연관관계의 객체 가져오기
    String responseBody = om.writeValueAsString(orderList); //직렬화하여 출력
    System.out.println("테스트 : "+responseBody);

    List<User> userList = userJPARepository.findAll();
    System.out.println(om.writeValueAsString(userList));

    // then
    Assertions.assertThat(orderList.get(0).getId()).isEqualTo(1);
    Assertions.assertThat(orderList.get(0).getUser().getId()).isEqualTo(1);
    Assertions.assertThat(orderList.get(0).getUser().getEmail()).isEqualTo("user1@nate.com");
    assertTrue(BCrypt.checkpw("meta1234!", orderList.get(0).getUser().getPassword()));
    Assertions.assertThat(orderList.get(0).getUser().getUsername()).isEqualTo("user1");
    Assertions.assertThat(orderList.get(0).getUser().getRoles()).isEqualTo("ROLE_USER");
}
```
> select 쿼리 1번 (join fetch)

<br/>

### **2. 결제하기 (주문 저장)**
* Test
```java
@Test
@DisplayName("2.결제하기 (주문 저장)")
public void order_save_test() throws JsonProcessingException {
    // given
    Order order = newOrder(userDummy); //주문 생성

    // when
    //insert 쿼리 1번
    orderJPARepository.save(order); //주문 저장

    // then
    Assertions.assertThat(order.getId()).isEqualTo(2);
    Assertions.assertThat(order.getUser().getId()).isEqualTo(1);
    Assertions.assertThat(order.getUser().getEmail()).isEqualTo("user1@nate.com");
    assertTrue(BCrypt.checkpw("meta1234!", order.getUser().getPassword()));
    Assertions.assertThat(order.getUser().getUsername()).isEqualTo("user1");
    Assertions.assertThat(order.getUser().getRoles()).isEqualTo("ROLE_USER");
}
```
> insert 쿼리 1번 수행

<br/>
<br/>


<br/>

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
>- 모든 요청과 응답이 json으로 처리되어 있는가?
<br/>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_4주차 과제 

<br/>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

<br/>

### **ExceptionHandler**
```java
@RequiredArgsConstructor
@Component
public class GlobalExceptionHandler {

    private final ErrorLogJPARepository errorLogJPARepository;

    public ResponseEntity<?> handle(RuntimeException e, HttpServletRequest request){
        if(e instanceof Exception400){
            Exception400 ex = (Exception400) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception401){
            Exception401 ex = (Exception401) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception403){
            Exception403 ex = (Exception403) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception404){
            Exception404 ex = (Exception404) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else if(e instanceof Exception500){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .userAgent(request.getHeader("User-Agent"))
                    .userIp(request.getRemoteAddr())
                    .build();
            errorLogJPARepository.save(errorLog);
            Exception500 ex = (Exception500) e;
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }else{
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .userAgent(request.getHeader("User-Agent"))
                    .userIp(request.getRemoteAddr())
                    .build();
            errorLogJPARepository.save(errorLog);
            return new ResponseEntity<>(
                    "unknown server error",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
```
> 예외 처리 코드이다.

## <span style="color:#068FFF">**상품(Product)**</span>
<br/>

### **DTO - ProductResponse**
```java
public class ProductResponse {

    @Getter @Setter
    public static class FindAllDTO {

        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }


    @Getter @Setter
    public static class FindByIdDTO {

        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount; // 0~5
        private List<OptionDTO> options;

        public FindByIdDTO(Product product, List<Option> optionList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.starCount = 5; // 임시로 추가해둠 (요구사항에는 없음)
            this.options = optionList.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter @Setter
        public class OptionDTO {
            private int id;
            private String optionName;
            private int price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }
}

```
<br/>


### **Controller**
```java
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final ProductService productService;

    // (기능4) 전체 상품 목록 조회 (페이징 9개씩)
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") String page, HttpServletRequest request) {
        try {
            int pageInt = Integer.parseInt(page); //문자열->Integer
            List<ProductResponse.FindAllDTO> findAllDTOList = productService.findAll(pageInt);
            return ResponseEntity.ok().body(ApiUtils.success(findAllDTOList));
        }
        //page가 정수형인지 유효성 검사
        catch(NumberFormatException e){
            return ResponseEntity.badRequest().body(ApiUtils.error("page는 숫자만 가능합니다", HttpStatus.BAD_REQUEST));
        }
        catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }

    }

    // (기능5) 개별 상품 상세 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable String id, HttpServletRequest request) {
        try {
            int productId = Integer.parseInt(id); //문자열->Integer
            ProductResponse.FindByIdDTO dto = productService.findById(productId);
            return ResponseEntity.ok().body(ApiUtils.success(dto));
        }
        //id가 정수형인지 유효성 검사
        catch(NumberFormatException e){
            return ResponseEntity.badRequest().body(ApiUtils.error("id는 숫자만 가능합니다", HttpStatus.BAD_REQUEST));
        }
        catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
```
> id 파라미터가 int형인지 검증한다.

<br/>

### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public List<ProductResponse.FindAllDTO> findAll(int page) {
        try {
            // 1. 데이터 가져와서 페이징하기
            List<Product> productList =
                    productJPARepository.findAll().stream().skip(page * 9).limit(9).collect(Collectors.toList());
            ;

            // 2. DTO 변환
            List<ProductResponse.FindAllDTO> responseDTOs =
                    productList.stream().map(ProductResponse.FindAllDTO::new).collect(Collectors.toList());

            return responseDTOs;
        } catch (Exception e){
            throw new Exception500("unknown server error");
        }
    }

    public ProductResponse.FindByIdDTO findById(Integer id){
        // 1. 상품 찾기
        Product product = productJPARepository.findById(id).stream().filter(p -> p.getId() == id).findFirst().orElseThrow(
                () -> new Exception404("상품을 찾을 수 없습니다. : "+id));

        try {
            // 2. 해당 상품의 옵션 찾기
            List<Option> optionList = optionJPARepository.findAll().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());

            // 3. DTO 변환
            ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
            return responseDTO;
        } catch (Exception e){
            throw new Exception500("unknown server error");
        }
    }
}
```
> Dummy Data를 실제 DB와 연결된 Repository로 변경했다.
> 로직을 작성했다.

<br/>

### **Repository**
```java
public interface ProductJPARepository extends JpaRepository<Product, Integer> {
    
}
```

<br/>

### **Controller Test**
```java
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {

    // 객체의 모든 메서드는 추상메서드로 구현됩니다. (가짜로 만들면)
    // 해당 객체는 SpringContext에 등록됩니다.
    @MockBean //가짜로 띄움
    private ProductService productService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc; //요청 보낼때 사용

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om; //직렬화

    @Test
    @DisplayName("전체 상품 조회 테스트")
    public void findAll_test() throws Exception{
        //given
        List<Product> productList = Arrays.asList(
                new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000),
                new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000),
                new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000),
                new Product(4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000),
                new Product(5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 5000),
                new Product(6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900),
                new Product(7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800),
                new Product(8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900),
                new Product(9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000)
        );
        List<ProductResponse.FindAllDTO> list = new ArrayList<>();
        for (Product product : productList) {
            ProductResponse.FindAllDTO dto = new ProductResponse.FindAllDTO(product);
            list.add(dto);
        }
        given(productService.findAll(0)).willReturn(list);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")); //요청 uri

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        System.out.println();
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response[0].id").value(1));
        result.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response[0].description").value(""));
        result.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response[0].price").value(1000));
        result.andExpect(jsonPath("$.response[1].id").value(2));
        result.andExpect(jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        result.andExpect(jsonPath("$.response[1].description").value(""));
        result.andExpect(jsonPath("$.response[1].image").value("/images/2.jpg"));
        result.andExpect(jsonPath("$.response[1].price").value(2000));
    }

    @Test
    @DisplayName("개별 상품 상세 조회 테스트")
    public void findById_test() throws Exception{
        //given
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        List<Option> optionList = Arrays.asList(
                new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                new Option(2, product1, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                new Option(3, product1, "고무장갑 베이지 S(소형) 6팩", 9900),
                new Option(4, product1, "뽑아쓰는 키친타올 130매 12팩", 16900),
                new Option(5, product1, "2겹 식빵수세미 6매", 8900));
        given(productService.findById(1)).willReturn(
                (new ProductResponse.FindByIdDTO(product1, optionList)));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/1"));

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.description").value(""));
        result.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        result.andExpect(jsonPath("$.response.price").value(1000));
        result.andExpect(jsonPath("$.response.options[0].id").value(1));
        result.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.options[0].price").value(10000));
        result.andExpect(jsonPath("$.response.options[1].id").value(2));
        result.andExpect(jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(jsonPath("$.response.options[1].price").value(10900));
    }
}
```
> BDDMockito를 이용해 stub 코드를 작성했다.

<br/>

## <span style="color:#068FFF">**장바구니(Cart)**</span>
<br/>

### **DTO - CartRequest**
```java
public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        @Min(value = 1, message = "optionId는 숫자이고 1이상이어야 합니다.")
        private int optionId;

        @Min(value = 1, message = "quantity는 숫자이고 1이상이어야 합니다.")
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {

        @Min(value = 1, message = "cartId는 숫자이고 1이상이어야 합니다.")
        private int cartId;

        @Min(value = 1, message = "quantity는 숫자이고 1이상이어야 합니다.")
        private int quantity;
    }
}

```
> 유효성 검증을 위한 Bean Validation을 작성했다.

<br/>

### **DTO - CartResponse**
```java
public class CartResponse {

    @Getter @Setter
    public static class UpdateDTO{
        private List<CartDTO> carts;
        private int totalPrice;

        public UpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class CartDTO {
            private int cartId;
            private int optionId;
            private String optionName;
            private int quantity;
            private int price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getOption().getPrice() * cart.getQuantity();
            }
        }
    }

    @Getter @Setter
    public static class FindAllDTO{
        private List<ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(List<Cart> cartList) {
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct() // 여러 옵션의 상품이 동일하면 중복을 제거한다.
                    .map(product -> new ProductDTO(cartList, product)).collect(Collectors.toList()); // 중복이 제거된 상품과 장바구니 상품으로 DTO를 만든다.
            this.totalPrice = cartList.stream().mapToInt(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<CartDTO> carts;

            public ProductDTO(List<Cart> cartList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carts = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter @Setter
            public class CartDTO {
                private int id;
                private OptionDTO option;
                private int quantity;
                private int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getOption().getPrice()*cart.getQuantity();
                }

                @Getter @Setter
                public class OptionDTO {
                    private int id;
                    private String optionName;
                    private int price;


                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }
        }
    }
}
```

<br/>

### **Controller**
```java
@RequiredArgsConstructor
@RestController
public class CartRestController {


    private final GlobalExceptionHandler globalExceptionHandler;
    private final CartService cartService;

    @Autowired
    CustomCollectionValidator customCollectionValidator;

    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors,
                                         @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        customCollectionValidator.validate(requestDTOs, errors);
        //유효성 검증 예외 처리
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        //서비스 호출
        try {
            cartService.save(requestDTOs, userDetails);
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }

    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        System.out.println("findAll 실행");
        try {
            CartResponse.FindAllDTO dto = cartService.findAll();
            return ResponseEntity.ok().body(ApiUtils.success(dto));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors,
                                    @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        customCollectionValidator.validate(requestDTOs, errors);
        //유효성 검증 예외 처리
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        //서비스 호출
        try {
            CartResponse.UpdateDTO dto = cartService.update(requestDTOs);
            return ResponseEntity.ok().body(ApiUtils.success(dto));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }


    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
```
> Collection은 @Valid로 유효성 검증이 어렵기때문에 customCollectionValidator 클래스를 만들어 유효성 검증을 했다.

<br/>

### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    //장바구니 조회
    public CartResponse.FindAllDTO findAll(){
        List<Cart> cartList = cartJPARepository.findAll();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        return responseDTO;
    }

    //장바구니 담기
    @Transactional
    public void save(List<CartRequest.SaveDTO> dtoList, CustomUserDetails userDetails){
        dtoList.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        try {
            Option option = null;
            Cart cart = null;
            for (CartRequest.SaveDTO saveDTO : dtoList) {
                Optional<Option> optional = optionJPARepository.findById(saveDTO.getOptionId());
                System.out.println("optional"+optional);
                if (optional.isEmpty()) throw new Exception404("해당 option이 없습니다.");
                option = optional.get();
                cart = Cart.builder()
                        .user(userDetails.getUser())
                        .option(option)
                        .quantity(saveDTO.getQuantity())
                        .price(option.getPrice() * saveDTO.getQuantity())
                        .build();
            }
                cartJPARepository.save(cart);
        }
        catch (Exception404 e){
            throw e;
        }
        catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }
    //장바구니 수정
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs){
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );
        try {
            List<Cart> cartList = cartJPARepository.findAll();
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                for (Cart cart : cartList) {
                    if(cart.getId() == updateDTO.getCartId()){
                        cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                    }
                }
            }
            cartList = cartJPARepository.findAll();
            // DTO를 만들어서 응답한다.
            CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(cartList);
            System.out.println("responseDTO" + responseDTO);
            return responseDTO;
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }
}
```
> 로직 수행시 try-catch문을 이용해 발생할 수 있는 예외를 적절하게 처리했다.

<br/>

### **Repository**
```java
public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findAllCartsByUserId(Integer userId);
}

```
<br/>

### **Controller Test**
```java
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {

    @MockBean
    private CartService cartService;

    @MockBean
    private CustomCollectionValidator validator;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @WithMockUser(username = "ssar@nate.com", roles = "USER") //가짜 인증객체 만들기
    @Test
    @DisplayName("장바구니 조회")
    public void findAll_test() throws Exception {
        //given
        //stub
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User u = new User(1, "user1@nate,com", "fake", "user1", "USER");
        BDDMockito.given(cartService.findAll()).willReturn(
                new CartResponse.FindAllDTO(
                        Arrays.asList(
                                new Cart(1, u, option1, 5, option1.getPrice()),
                                new Cart(2, u, option2, 5, option2.getPrice())
                        )));

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts"));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.products[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        result.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER") //가짜 인증객체 만들기
    @Test
    @DisplayName("장바구니 담기")
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(5);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(5);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        result.andExpect(jsonPath("$.error").doesNotExist());
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER") //가짜 인증객체 만들기
    @Test
    @DisplayName("장바구니 수정")
    public void update_test() throws Exception {
        // given
        //요청 DTO 생성
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(10);
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        //stub
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User u = new User(1, "user1@nate,com", "fake", "user1", "USER");
        CartResponse.UpdateDTO updateDTO = new CartResponse.UpdateDTO(
                Arrays.asList(
                        new Cart(1, u, option1, 5, option1.getPrice()),
                        new Cart(2, u, option2, 5, option2.getPrice())
                ));
        BDDMockito.given(cartService.update(any())).willReturn(updateDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.carts[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.carts[0].price").value(50000));
    }
}
```
<br/>

## <span style="color:#068FFF">**주문(Order)**</span>
<br/>

### **OrderResponse - DTO**
```java
public class OrderResponse {

    @Getter @Setter
    public static class FindByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;

        public FindByIdDTO(Order order, List<Item> itemList) {
            this.id = order.getId();
            this.products = itemList.stream()
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(itemList, product)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }


        @Getter @Setter
        public class ProductDTO {
            private int id;
            private String productName;
            private List<ItemDTO> items;

            public ProductDTO(List<Item> itemList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(ItemDTO::new)
                        .collect(Collectors.toList());
            }

            @Getter @Setter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item) {
                    this.id = item.getId();
                    this.optionName = item.getOption().getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = item.getOption().getPrice()*item.getQuantity();
                }

            }
        }
    }
}
```
<br/>

### **Controller**
```java
@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final OrderService orderService;
    private final UserService userService;

    // (기능12) 결제
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try {
            if(userDetails.getUser()!= null){
                //유저가 존재하는지 확인
                UserResponse.FindById byId = userService.findById(userDetails.getUser().getId());

                if(byId.getId() != 0){ //유저가 존재할 경우 order 저장
                    OrderResponse.FindByIdDTO dto = orderService.save(userDetails.getUser());
                    return ResponseEntity.ok().body(ApiUtils.success(dto));
                }
            }
            return ResponseEntity.ok().body(ApiUtils.success(null));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }

    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable String id, HttpServletRequest request) {
        try {
            int orderId = Integer.parseInt(id); //문자열->Integer
            OrderResponse.FindByIdDTO dto = orderService.findById(orderId);
            return ResponseEntity.ok().body(ApiUtils.success(dto));
        }
        catch(NumberFormatException e){
            return ResponseEntity.badRequest().body(ApiUtils.error("id는 숫자만 가능합니다", HttpStatus.BAD_REQUEST));
        }
        catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
```

<br/>

### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.FindByIdDTO save(User user){
        try {
            //주문 생성하기
            Order order = Order.builder()
                    .user(user)
                    .build();
            orderJPARepository.save(order);

            //장바구니 -> OrderItem으로 옮겨 저장하기
            List<Cart> cartList = cartJPARepository.findAllCartsByUserId(user.getId());
            Item item;
            List<Item> itemList = new ArrayList<>();
            for (Cart c : cartList) {
                item = Item.builder().order(order)
                        .option(c.getOption())
                        .quantity(c.getQuantity())
                        .price(c.getPrice())
                        .build();
                itemJPARepository.save(item);
                itemList.add(item);
            }
            //응답 DTO 생성
            OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
            return responseDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            throw new Exception500("unknown server error : service");
        }
    }

    public OrderResponse.FindByIdDTO findById(int id){
        try{
            Optional<Order> optional = orderJPARepository.findById(id);
            Order order = null;
            if(optional.isPresent()){ //주문이 존재할 경우
                order = optional.get();
                List<Item> itemList = itemJPARepository.findItemsByOrderId(id);
                OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
                return responseDTO;
            }
            else {
                throw new Exception404("해당 주문이 없습니다.");
            }
        }
        catch (Exception404 e){
            throw e;
        }
        catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
                throw new Exception500("unknown server error");
        }
    }
}
```

<br/>

### **Repository**
```java
public interface OrderJPARepository extends JpaRepository<Order, Integer> {
}
```
<br/>

### **Controller Test**
```java
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class,
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {

    // 객체의 모든 메서드는 추상메서드로 구현됩니다. (가짜로 만들면)
    // 해당 객체는 SpringContext에 등록됩니다.
    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc; //요청 보낼때 사용

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om; //직렬화

    @MockBean
    private CustomUserDetails userDetails;


    @BeforeEach
    void beforeEach(){
        //stub
        Product product1 = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
        Option option1 = new Option(1, product1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = new Option(2, product1,"02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        User u = new User(1, "user1@nate,com", "fake", "user1", "USER");
        AtomicInteger counter = new AtomicInteger(1);
        Order order = new Order(1, u);
        List<Cart> cartList = Arrays.asList(
                new Cart(1, u, option1, 5, option1.getPrice()),
                new Cart(2, u, option2, 5, option2.getPrice())
        );
        List<Item> itemList = cartList.stream().map(
                cart -> new Item(counter.getAndIncrement(), cart.getOption(), order, cart.getQuantity(), cart.getPrice())
        ).collect(Collectors.toList());

        BDDMockito.given(userService.findById(any())).willReturn(
                new UserResponse.FindById(u));

        // 인증 객체 설정
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        BDDMockito.given(userDetails.getUser()).willReturn(u);

        BDDMockito.given(orderService.save(any())).willReturn(
                new OrderResponse.FindByIdDTO(
                        order, itemList));
        BDDMockito.given(orderService.findById(anyInt())).willReturn(
                new OrderResponse.FindByIdDTO(
                        order, itemList
                ));

    }

    @Test
    @WithMockUser(username = "user1@nate.com", roles = "USER") //가짜 인증객체 만들기
    @DisplayName("주문 저장하기 테스트")
    public void save_test() throws Exception{
        // given

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/orders/save")
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.totalPrice").value(104500));

        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
    }

    @Test
    @WithMockUser(username = "user1@nate.com", roles = "USER") //가짜 인증객체 만들기
    @DisplayName("주문 결과 확인 테스트")
    public void findById_test() throws Exception{
        //given
        int id =1;

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/orders/"+id)); //요청 uri

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(jsonPath("$.success").value("true"));
        result.andExpect(jsonPath("$.response.id").value(1));
        result.andExpect(jsonPath("$.response.totalPrice").value(104500));

        result.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        result.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        result.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        result.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        result.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        result.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(5));
        result.andExpect(jsonPath("$.response.products[0].items[1].price").value(54500));
    }
}
```
> jwt의 UserDetails 값을 테스트시 사용하기 위해 @WithMockUser를 사용했으나 값이 정상적으로 불러와지지않았다.
> Authentication 객체를 만들어 userDetails 값을 명시적으로 설정하여 해결했다.
> 테스트시 사용하는 stub을 @BeforeEach로 모아 작성했다.

<br/>

## <span style="color:#068FFF">**유저(User)**</span>

<br/>

### **DTO - UserRequest**
```java
public class UserRequest {
    @Getter
    @Setter
    public static class JoinDTO {

        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;

        @Size(min = 8, max = 45, message = "8에서 45자 이내여야 합니다.")
        @NotEmpty
        private String username;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .roles("ROLE_USER")
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class EmailCheckDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
    }

    @Getter
    @Setter
    public static class UpdatePasswordDTO {
        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;
    }
}
```
> 정규식을 이용해 유효성을 검증한다.
> 
<br/>

### **DTO - UserResponse**
```java
public class UserResponse {

    @Getter @Setter
    public static class FindById{
        private int id;
        private String username;
        private String email;

        public FindById(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    @Getter @Setter
    public static class CheckEmail{
        private String email;

        public CheckEmail(String email) {
            this.email = email;
        }
    }
}
```
<br/>

### **Controller**
```java
@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors,
                                  HttpServletRequest request) {
        //유효성 검증 예외 처리
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
        try {
            userService.join(requestDTO); //회원가입
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors,
                                   @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        //유효성 검사
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 ex = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }

        try {
            String jwt = userService.login(requestDTO);
            return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    @PostMapping("/users/{id}/update-password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Integer id,
            @RequestBody @Valid UserRequest.UpdatePasswordDTO requestDTO, Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {

        // 유효성 검사
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 e = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        // 권한 체크 (DB를 조회하지 않아도 체크할 수 있는 것) - 유저 id가 세션에 존재하는지 확인
        if (id != userDetails.getUser().getId()) {
            Exception403 e = new Exception403("인증된 user는 해당 id로 접근할 권한이 없습니다" + id);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        // 서비스 실행 : 내부에서 터지는 모든 익셉션은 예외 핸들러로 던지기
        try {
            userService.updatePassword(requestDTO, id);
            return ResponseEntity.ok().body(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // 클라이언트로 부터 전달된 데이터는 신뢰할 수 없다.
    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Integer id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ) {
        // 권한 체크 (디비를 조회하지 않아도 체크할 수 있는 것)
        if (id != userDetails.getUser().getId()) {
            Exception403 e = new Exception403("인증된 user는 해당 id로 접근할 권한이 없습니다:" + id);
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }

        // 서비스 실행 : 내부에서 터지는 모든 익셉션은 예외 핸들러로 던지기
        try {
            UserResponse.FindById responseDTO = userService.findById(id);
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // 이메일 중복체크 (기능에는 없지만 사용중)
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.EmailCheckDTO emailCheckDTO, Errors errors, HttpServletRequest request) {
        // 유효성 검사
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Exception400 e = new Exception400(fieldErrors.get(0).getDefaultMessage() + ":" + fieldErrors.get(0).getField());
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        // 서비스 실행 : 내부에서 터지는 모든 익셉션은 예외 핸들러로 던지기
        try {
            String email = emailCheckDTO.getEmail();
            userService.sameCheckEmail(email); //중복 체크(중복시 에러 던짐)
            UserResponse.CheckEmail dto = new UserResponse.CheckEmail(email);
            return ResponseEntity.ok().body(ApiUtils.success(dto));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능3) - 로그아웃
    // 사용 안함 - 프론트에서 localStorage JWT 토큰을 삭제하면 됨.
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> user) {
        return ResponseEntity.ok().header(JWTProvider.HEADER, "").body(ApiUtils.success(null));
    }
}
```
<br/>

### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    public UserResponse.FindById findById(Integer id){
        User userPS = userJPARepository.findById(id).orElseThrow(
                () -> new Exception400("회원 아이디를 찾을 수 없습니다. : "+id)
        );
        return new UserResponse.FindById(userPS);
    }

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        sameCheckEmail(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        try {
            userJPARepository.save(requestDTO.toEntity());
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        User userPS = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new Exception400("이메일을 찾을 수 없습니다 : "+requestDTO.getEmail())
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), userPS.getPassword())){
            throw new Exception400("패스워드가 잘못입력되었습니다 ");
        }
        return JWTProvider.create(userPS);
    }

    public void sameCheckEmail(String email) {
        Optional<User> userOP = userJPARepository.findByEmail(email);
        if (userOP.isPresent()) {
            throw new Exception400("동일한 이메일이 존재합니다 : " + email);
        }
    }

    @Transactional
    public void updatePassword(UserRequest.UpdatePasswordDTO requestDTO, Integer id) {
        User userPS = userJPARepository.findById(id).orElseThrow(
                () -> new Exception400("회원 아이디를 찾을 수 없습니다. : "+id)
        );

        // 의미 있는 setter 추가
        String encPassword =
                passwordEncoder.encode(requestDTO.getPassword());
        userPS.updatePassword(encPassword);
    } // 더티체킹 flush
}
```
<br/>

### **Repository**
```java
public interface UserJPARepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
```
<br/>

### **Controller Test**
```java
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {UserRestController.class})
public class UserRestControllerTest {

    // 객체의 모든 메서드는 추상메서드로 구현됩니다. (가짜로 만들면)
    // 해당 객체는 SpringContext에 등록됩니다.
    @MockBean //가짜로 띄움
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    // @WebMvcTest를 하면 MockMvc가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private MockMvc mvc; //요청 보낼때 사용

    // @WebMvcTest를 하면 ObjectMapper가 SpringContext에 등록되기 때문에 DI할 수 있습니다.
    @Autowired
    private ObjectMapper om; //직렬화

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");
        String requestBody = om.writeValueAsString(requestDTO);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join") //post 요청 uri
                        .content(requestBody) //요청 본문
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void login_test() throws Exception {
        // given
        // stub
        User user = User.builder().id(1).roles("ROLE_USER").build(); //유저 생성
        String jwt = JWTProvider.create(user); //토큰 생성
        Mockito.when(userService.login(any())).thenReturn(jwt); //가짜 객체가 로그인 요청시 생성한 jwt 반환
        //DTO
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setEmail("ssar@nate.com");
        loginDTO.setPassword("meta1234!");
        String requestBody = om.writeValueAsString(loginDTO); //직렬화

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
        System.out.println("테스트 : "+responseBody);
        System.out.println("테스트 : "+responseHeader);

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX)); //Bearer 토큰인지 확인(prefix)
    }
}
```

<br/>

# 5주차

카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제
</br>
</br>

## **과제명**
```
코드 리팩토링
```

## **과제 설명**
```
카카오 쇼핑 프로젝트 전체 코드를 리팩토링한다
 - AOP로 유효성검사 적용하기
 - 구현하기
 - 장바구니 담GlobalExceptionHanlder 기 -> 예외 처리하기
 - 장바구니 수정(주문하기) -> 예외처리하기
 - 결재하기 기능 구현 (장바구니가 꼭 초기화 되어야함)
 - 주문결과 확인 기능 구현
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- AOP가 적용되었는가?
>- GlobalExceptionHandler가 적용되었는가?
>- 장바구니 담기시 모든 예외가 처리 완료되었는가?
>- 장바구니 수정시 모든 예외가 처리 완료되었는가?
>- 결재하기와 주문결과 확인 코드가 완료되었는가?

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_5주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

<br/>

## **과제**

> AOP와 GlobalExceptionHandler는 week5 폴더에서 확인해주세요.

<br/>

## <span style="color:#068FFF">**장바구니(Cart)**</span>

<br/>

### **1. 장바구니 - 장바구니 담기 + 예외 처리하기**
<br/>

### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional //트랜잭션 시작
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        HashSet<Integer> set = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 1. 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
            if (set.contains(optionId)) //중복 확인
                throw new Exception400("동일한 옵션 여러개를 추가할 수 없습니다.");
            else set.add(optionId);

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            // Cart {cartId:1, optionId:1, quantity:3, userId:1} -> DTO {optionId:1, quantity:5}
            Optional<Cart> optional = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            Option option = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            if(optional.isPresent()){ //이미 장바구니에 담긴 옵션이라면
                Cart cart = optional.get();
                int updateQuantity = cart.getQuantity() +quantity;
                int price = quantity * option.getPrice();
                cart.update(updateQuantity, price); //더티체킹 수행
            }
            // 3. [2번이 아니라면] 유저의 장바구니에 담기
            else{
                int price = option.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(option).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }

    } //변경감지, 더티체킹, flush, 트랜잭션 종료
}
```
> 수행 쿼리 : 반복문마다 **3번** 나간다. <br/>
> cart 조회 1번, option 조회 1번, insert(또는 update) 1번

<br/>

### **2. 장바구니 수정(update) + 예외처리하기**
<br/>

### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserIdWithOption(user.getId()); //쿼리 수정
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("담은 장바구니가 없습니다.");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        HashSet<Integer> set = new HashSet<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            if (set.contains(cartId)) //중복 확인
                throw new Exception400("동일한 장바구니를 동시에 update할 수 없습니다.");
            else set.add(cartId);
            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            boolean b = cartList.stream().anyMatch(cart -> cart.getId() == cartId);
            if(!b) throw new Exception404("존재하지않는 cartId입니다.");
        }

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
```
<br/>

### **Repository**
```java
public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserId(int userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.option o WHERE c.user.id = :userId")
    List<Cart> findAllByUserIdWithOption(@Param("userId") int userId);
}
```
> cart 조회시 option을 join fetch하여 성능을 최적화했다.
> 1. cart와 option을 한번에 가져오는 쿼리 2.장바구니마다 update 쿼리

> **한번에 update**하는 쿼리를 만들어 수행해도 되겠지만, <br/>
> 과제에서 더티체킹을 이용해 update를 수행하라고 하였으므로 추가 수정하지않았다.

<br/>

## <span style="color:#068FFF">**주문(Order)**</span>
<br/>

### **1. 결제하기(주문 인서트)**
<br/>

### **DTO**
```java
public class OrderResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int id;
        private List<OrderResponse.FindAllDTO.ProductDTO> products;
        private int totalPrice;

        public FindAllDTO(Order order, List<Item> itemList) {
            this.id = order.getId();
            this.products = itemList.stream()
                    // 중복되는 상품 걸러내기
                    .map(item -> item.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
            this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
        }

        @Getter
        @Setter
        public class ProductDTO {
            private String productName;
            private List<OrderResponse.FindAllDTO.ProductDTO.ItemDTO> items;

            public ProductDTO(Product product, List<Item> itemList) {
                this.productName = product.getProductName();
                // 현재 상품과 동일한 장바구니 내역만 담기
                this.items = itemList.stream()
                        .filter(item -> item.getOption().getProduct().getId() == product.getId())
                        .map(item -> new ItemDTO(item, item.getOption()))
                        .collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class ItemDTO {
                private int id;
                private String optionName;
                private int quantity;
                private int price;

                public ItemDTO(Item item, Option option) {
                    this.id = item.getId();
                    this.optionName = option.getOptionName();
                    this.quantity = item.getQuantity();
                    this.price = quantity * option.getPrice();
                }
            }
        }
    }
}
```
> DTO 클래스 구조를 먼저 작성한 후, 생성자에서 **stream**을 이용하여 주어진 order와 itemList로 DTO를 한번에 생성할 수 있다. <br/>
> 사용자가 매개변수 하나하나 넣어가며 만들 필요가 없어 편리하다.

> ✳️ Repository에서 가져올때 DTO에서 사용할 entity들은 프록시 객체가 아닌 실제로 가져온 객체(join fetch또는 직접 조회)여야 직렬화 문제가 발생하지 않는다.

<br/>

### **Controller**
```java
@RequiredArgsConstructor
@RestController
public class OrderRestController {
    private final OrderService orderService;

    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindAllDTO dto = orderService.save(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(dto));
    }
}
```
<br/>

### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    //결재하기(주문 인서트)
    @Transactional
    public OrderResponse.FindAllDTO save(User sessionUser) {
        // 사용자가 담은 장바구니가 없을때 예외처리
        int userId = sessionUser.getId();
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(userId);
        if(cartList.isEmpty()){
            throw new Exception404("담은 장바구니가 없습니다");
        }
        //1. 주문 생성하기
        Order order = Order.builder().user(sessionUser).build();
        orderJPARepository.save(order);

        //2. 장바구니를 Item에 저장하기
        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = Item.builder().option(cart.getOption()).order(order)
                    .quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemJPARepository.save(item);
            itemList.add(item);
        }
        //3. 장바구니 초기화하기
        cartJPARepository.deleteByUserId(userId);

        //4. DTO 응답
        return new OrderResponse.FindAllDTO(order, itemList);
    }
}
```
<br/>

### **Repository**
```java
public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
    void deleteByUserId(int userId);
}
```
> option은 Item 저장과 DTO에서 사용되고 product도 DTO에서 속성이 사용되므로 한번에 가져오는 것이 좋다.

>✳️ 만약 join fetch를 하지 않으면 entity 사용시 따로 쿼리가 나가게 된다.

<br/>

> deleteByUserId 쿼리를 커스텀하여 삭제시 user를 조회하지 않고, 한번에 삭제가 가능하도록 하였다. <br/>

> **@Modifying**을 붙여준 이유는 JPA에게 INSERT, UPDATE, DELETE 쿼리를 수행함을 명시적으로 알려주어서 더 안전하게 트랜잭션을 처리하기 위해서이다.

<br/>

### **2. 주문 결과 확인**
<br/>

> 위와 응답 JSON이 동일하므로 응답 DTO도 동일하다.

<br/>

### **Controller**
```java
@RequiredArgsConstructor
@RestController
public class OrderRestController {
    private final OrderService orderService;

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindAllDTO dto = orderService.findById(id);
        return ResponseEntity.ok(ApiUtils.success(dto));
    }
}
```
### **Service**
```java
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    //주문 결과 확인
    public OrderResponse.FindAllDTO findById(int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("존재하지않는 orderId 입니다")
        );
        List<Item> itemList = itemJPARepository.findByOrderId(id);
        return new OrderResponse.FindAllDTO(order, itemList);
    }
}
```
<br/>

### **Repository**
```java
public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i join fetch i.option o join fetch o.product p where  i.order.id = :orderId")
    List<Item> findByOrderId(int orderId);
}
```
> DTO 생성을 위해 option과 product를 join fetch하여 item을 가져온다. 

> ✳️ option과 product의 FetchType이 EAGER이라면 join fetch하지 않아도 함께 가져온다.

> 총 2번의 쿼리가 수행되었다.
> 1. order 조회 2. item 조회 (option, product join fetch)

<br/>
<br/>
<br/>

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

<br/>

## **1. 통합테스트 구현**
### **ProductRestControllerTest**
```java
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test") //test profile 사용
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //Test 메서드 실행 전에 Sql 실행
@AutoConfigureMockMvc //MockMvc 사용
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //통합테스트(SF-F-DS(Handler, ExHandler)-C-S-R-PC-DB) 다 뜬다.
public class ProductRestControllerTest extends MyRestDoc {

    @Test
    @DisplayName("전체 상품 목록 조회")
    public void findAll_test() throws Exception {
        // given teardown.sql

        // when
        ResultActions resultActions = mvc.perform(
                get("/products")
        );

        // eye (눈으로 본다.)
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("전체 상품 목록 조회 - Param 사용")
    public void findAll_test_when_using_param() throws Exception {
        // given teardown.sql
            Integer page  = 1; //기본 0페이지, 1페이지(id=10)부터 시작

        // when
        //페이징 확인
        ResultActions resultActions = mvc.perform(
                get("/products")
                        .param("page", page.toString())
        );

        // eye (눈으로 본다.)
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].id").value(10));
        resultActions.andExpect(jsonPath("$.response[0].productName").value("통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/10.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(8900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("개별 상품 상세 조회")
    public void findById_test() throws Exception {
        // given teardown.sql
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/products/" + id)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.description").value(""));
        resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response.price").value(1000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
```
> 예외 처리 부분도 포함하여 작성

### **CartRestControllerTest**
```java
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartJPARepository cartJPARepository;

    @WithUserDetails(value = "ssarmango@nate.com") //시큐리티 인증
    @DisplayName("장바구니 담기(저장)")
    @Test
    public void addCartList_test() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(3);
        item.setQuantity(5);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com") //시큐리티 인증
    @DisplayName("장바구니 담기(저장) 실패 - 중복된 옵션 입력")
    @Test
    public void addCartList_test_if_duplicated_option() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        // [ { optionId:3, quantity:5 }, { optionId:3, quantity:5 } ]
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(3);
        item.setQuantity(5);
        requestDTOs.add(item);
        //동일한 옵션
        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(3);
        item2.setQuantity(5);
        requestDTOs.add(item2);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @WithUserDetails(value = "ssarmango@nate.com") //시큐리티 인증
    @DisplayName("장바구니 담기(저장) - 이미 존재하는 옵션 입력(업데이트)")
    @Test
    public void addCartList_test_if_existed_option() throws Exception {
        // given -> optionId [1,2,16]이 teardown.sql을 통해 들어가 있음
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO item = new CartRequest.SaveDTO();
        item.setOptionId(1);
        item.setQuantity(5);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        //장바구니 담기
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //장바구니 조회
        ResultActions resultActions2 = mvc.perform(
                get("/carts")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("장바구니 담기 : " + responseBody);
        String responseBody2 = resultActions2.andReturn().getResponse().getContentAsString();
        System.out.println("장바구니 조회 결과 : " + responseBody2);

        // verify
        //장바구니 담기
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

        //장바구니 조회(장바구니 업데이트 결과)
        resultActions2.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions2.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        resultActions2.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(10)); //5개 더 추가되어 10개
    }

    @WithUserDetails(value = "ssarmango@nate.com") //UserDetailService의 loadByUsername 실행, email으로 user를 DB 조회
    @DisplayName("장바구니 조회(전체 조회)")
    @Test
    public void findAll_test() throws Exception {
        // given teardown

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문하기(장바구니 수정)")
    @Test
    public void update_test() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문하기(장바구니 수정) 실패 - 장바구니가 비었을때")
    @Test
    public void update_test_if_cart_empty() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        //장바구니 비우기
        cartJPARepository.deleteAll();
        //dto 생성
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isNotFound()); //404번 에러
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문하기(장바구니 수정) 실패 - 중복된 장바구니 입력")
    @Test
    public void update_test_if_duplicated_cart() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        //dto 생성
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(1);
        item.setQuantity(10);
        requestDTOs.add(item);
        //중복된 장바구니
        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(1);
        item2.setQuantity(10);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @DisplayName("주문하기(장바구니 수정) 실패 - 존재하지 않은 장바구니 업데이트 시도")
    @Test
    public void update_test_if_notexisted_cart() throws Exception {
        // given -> cartId [1번 5개,2번 1개,3번 5개]가 teardown.sql을 통해 들어가 있음
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO item = new CartRequest.UpdateDTO();
        item.setCartId(4); //존재하지 않은 cartId
        item.setQuantity(5);
        requestDTOs.add(item);

        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isNotFound()); //404번 에러
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
```

### **OrderRestControllerTest**
```java
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com") //시큐리티 인증
    @Test
    @DisplayName("주문 결과 확인")
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/"+id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(10900));

        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(3));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(250000));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //api docs
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("결재하기-주문 인서트")
    public void save_test() throws Exception {
        // given teardown - cart 담겨져 있음

        // when
        //결재하기
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
        );
        //장바구니 조회(주문 후 장바구니 초기화 확인)
        ResultActions resultActions2 = mvc.perform(
                get("/carts")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("응답(결재) : " + responseBody);
        String responseBody2 = resultActions2.andReturn().getResponse().getContentAsString();
        System.out.println("응답(장바구니) : " + responseBody2);

        // verify
        //결재하기 - 주문 인서트
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value(2));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));

        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value(4));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value(50000));

        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value(10900));

        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value(6));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value(250000));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //api docs

        // carts(장바구니)가 초기화 확인
        resultActions2.andExpect(jsonPath("$.response.products.length()").value(0));
        resultActions2.andExpect(jsonPath("$.response.totalPrice").value(0));

    }

}
```

### **UserRestControllerTest**
```java
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test") //test profile 사용
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //Test 메서드 실행 전에 Sql 실행
@AutoConfigureMockMvc //MockMvc 사용
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //통합테스트(SF-F-DS(Handler, ExHandler)-C-S-R-PC-DB) 다 뜬다.
public class UserRestControllerTest extends MyRestDoc {
    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("로그인")
    public void login_test() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1@nate.com");
        requestDTO.setPassword("user1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        resultActions.andExpect(jsonPath("$.error").doesNotExist());

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("로그인 - 유효하지 않은 이메일")
    public void login_test_if_notvalid_email() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1nate.com"); //@가 없는 이메일
        requestDTO.setPassword("user1234!");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("로그인 - 유효하지 않은 비밀번호(문자종류 포함X)")
    public void login_test_if_notvalid_pw() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1@nate.com");
        requestDTO.setPassword("user1234"); //특수기호 포함하지않은 비밀번호

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("로그인 - 유효하지않은 비밀번호(글자수)")
    public void login_test_if_notvalid_pw2() throws Exception {
        // given teardown.sql - user1 저장되어있음
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("user1@nate.com");
        requestDTO.setPassword("user12!"); //글자수 부족한 비밀번호

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("회원가입")
    public void join_test() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newuser@nate.com");
        requestDTO.setPassword("user1234!");
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                    post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        resultActions.andExpect(jsonPath("$.error").doesNotExist());

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }
    @Test
    @DisplayName("회원가입 - 유효하지않은 이메일")
    public void join_test_if_notvalid_email() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newusernate.com"); //@가 없는 유효하지않은 이메일
        requestDTO.setPassword("user1234!");
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("회원가입 - 유효하지않은 비밀번호(모든 문자종류 포함X)")
    public void join_test_if_notvalid_pw1() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newuser@nate.com");
        requestDTO.setPassword("user1234"); //특수기호가 없는 유효하지않은 비밀번호
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }
    @Test
    @DisplayName("회원가입 - 동일한 이메일 존재")
    public void join_test_if_duplicated_email() throws Exception {
        // given teardown.sql
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("user1@nate.com"); //중복된 이메일
        requestDTO.setPassword("user1234!");
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("회원가입 - 유효하지않은 비밀번호(글자수)")
    public void join_test_if_notvalid_pw2() throws Exception {
        // given teardown.sql된
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("newuser@nate.com");
        requestDTO.setPassword("user12!"); //글자수 적은 비밀번호
        requestDTO.setUsername("newusermango");

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러-유효성 검사 에러(@Valid)

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("이메일 중복확인")
        public void check_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("newuser@nate.com"); //중복되지 않은 이메일

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response").doesNotExist()); //null인지 확인
        resultActions.andExpect(jsonPath("$.error").doesNotExist());

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("이메일 중복확인 - 중복된 이메일")
    public void check_test_if_duplicated() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("user1@nate.com"); //중복된 이메일(이미 저장된 이메일)

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }

    @Test
    @DisplayName("이메일 중복확인 - 유효하지않은 이메일")
    public void check_notvalid_test() throws Exception {
        // given
        UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
        requestDTO.setEmail("newusernate.com"); //유효한 형식이 아닌 이메일

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("요청 데이터 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                post("/check")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(status().isBadRequest()); //400번 에러

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document); //restDoc
    }
}
```
<br/>
<br/>

## **2. API 문서 구현 / 작성**
### **adoc 파일 생성**
```adoc
= 카카오 쇼핑하기 RestAPI
MyungJiKim <audwl03071@gmail.com>

ifndef::snippets[]
:snippets: ./build/generated-snippets
endif::[]

:product: product-rest-controller-test
:cart: cart-rest-controller-test
:order: order-rest-controller-test
:user: user-rest-controller-test

:toc: left
:toclevels: 2
:source-highlighter: highlightjs

== **상품**

=== *1. 전체 상품 목록 조회*
* param : page={number}
* param의 디폴트 값은 0이다.

==== 요청 예시
include::{snippets}/{product}/find-all_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{product}/find-all_test/response-body.adoc[]

==== *1-1. 전체 상품 목록 조회 : Param 사용*
* param : page=1

==== 요청 예시
include::{snippets}/{product}/find-all_test_when_using_param/http-request.adoc[]

==== 응답 예시
include::{snippets}/{product}/find-all_test_when_using_param/response-body.adoc[]


=== *2. 개별 상품 상세 조회*

==== 요청 예시
include::{snippets}/{product}/find-by-id_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{product}/find-by-id_test/response-body.adoc[]

== **장바구니**

=== *1. 장바구니 담기*

==== 요청 예시
include::{snippets}/{cart}/add-cart-list_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/add-cart-list_test/response-body.adoc[]

==== *1-1. 장바구니 담기 : 동일 옵션 입력*

==== 요청 예시
include::{snippets}/{cart}/add-cart-list_test_if_duplicated_option/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/add-cart-list_test_if_duplicated_option/response-body.adoc[]

==== *1-2. 장바구니 담기 : 이미 담은 옵션 입력 (업데이트)*

==== 요청 예시
include::{snippets}/{cart}/add-cart-list_test_if_existed_option/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/add-cart-list_test_if_existed_option/response-body.adoc[]


=== *2. 장바구니 조회*

==== 요청 예시
include::{snippets}/{cart}/find-all_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/find-all_test/response-body.adoc[]

=== *3. 장바구니 수정*
* 주문하기를 할 때 장바구니 데이터를 update하고 그 결과를 응답받는다.
* 결재페이지에서 이 응답을 사용해도 되고, 다시 장바구니 조회 API를 사용해도 된다.

==== 요청 예시
include::{snippets}/{cart}/update_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/update_test/response-body.adoc[]

==== *3-1. 장바구니 수정 : 담은 장바구니가 없을때 (empty)*

==== 요청 예시
include::{snippets}/{cart}/update_test_if_cart_empty/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/update_test_if_cart_empty/response-body.adoc[]

==== *3-2. 장바구니 수정 : 동일한 장바구니 입력*

==== 요청 예시
include::{snippets}/{cart}/update_test_if_duplicated_cart/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/update_test_if_duplicated_cart/response-body.adoc[]

==== *3-3. 장바구니 수정 : 존재하지 않은 장바구니 입력*

==== 요청 예시
include::{snippets}/{cart}/update_test_if_notexisted_cart/http-request.adoc[]

==== 응답 예시
include::{snippets}/{cart}/update_test_if_notexisted_cart/response-body.adoc[]


== **주문**

=== *1. 결재하기 - (주문 인서트)*

==== 요청 예시
include::{snippets}/{order}/save_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{order}/save_test/response-body.adoc[]

=== *2. 주문 결과 확인*

==== 요청 예시
include::{snippets}/{order}/find-by-id_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{order}/find-by-id_test/response-body.adoc[]

== **사용자**

=== *1. 회원가입*

==== 요청 예시
include::{snippets}/{user}/join_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/join_test/response-body.adoc[]

==== *1-1. 회원가입 실패 : 형식에 맞지 않은 이메일*

==== 요청 예시
include::{snippets}/{user}/join_test_if_notvalid_email/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/join_test_if_notvalid_email/response-body.adoc[]

==== *1-2. 회원가입 실패 : 형식에 맞지 않은 비밀번호 (문자종류)*

==== 요청 예시
include::{snippets}/{user}/join_test_if_notvalid_pw1/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/join_test_if_notvalid_pw1/response-body.adoc[]

==== *1-3. 회원가입 실패 : 가입된 이메일*

==== 요청 예시
include::{snippets}/{user}/join_test_if_duplicated_email/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/join_test_if_duplicated_email/response-body.adoc[]

==== *1-4. 회원가입 실패 : 형식에 맞지 않은 비밀번호 (글자수)*

==== 요청 예시
include::{snippets}/{user}/join_test_if_notvalid_pw2/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/join_test_if_notvalid_pw2/response-body.adoc[]


=== *2. 로그인*

==== 요청 예시
include::{snippets}/{user}/login_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/login_test/response-body.adoc[]

==== *2-1. 로그인 실패 : 형식에 맞지 않은 이메일*

==== 요청 예시
include::{snippets}/{user}/login_test_if_notvalid_email/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/login_test_if_notvalid_email/response-body.adoc[]

==== *2-2. 로그인 실패 : 형식에 맞지 않은 비밀번호 (문자종류)*

==== 요청 예시
include::{snippets}/{user}/login_test_if_notvalid_pw/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/login_test_if_notvalid_pw/response-body.adoc[]

==== *2-3. 로그인 실패 : 형식에 맞지 않은 비밀번호 (글자수)*

==== 요청 예시
include::{snippets}/{user}/login_test_if_notvalid_pw2/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/login_test_if_notvalid_pw2/response-body.adoc[]

=== *3. 이메일 중복 확인*

==== 요청 예시
include::{snippets}/{user}/check_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/check_test/response-body.adoc[]

==== *3-1. 이메일 중복 확인 실패 : 가입된 이메일*

==== 요청 예시
include::{snippets}/{user}/check_test_if_duplicated/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/check_test_if_duplicated/response-body.adoc[]

==== *3-2. 이메일 중복 확인 실패 : 형식에 맞지 않은 이메일*

==== 요청 예시
include::{snippets}/{user}/check_notvalid_test/http-request.adoc[]

==== 응답 예시
include::{snippets}/{user}/check_notvalid_test/response-body.adoc[]
```

### **생성된 API 문서**
<img src="img/api-docs/1.jpg">
<img src="img/api-docs/2.jpg">
<img src="img/api-docs/3.jpg">
<img src="img/api-docs/4.jpg">
<img src="img/api-docs/5.jpg">
<img src="img/api-docs/6.jpg">
<img src="img/api-docs/7.jpg">
<img src="img/api-docs/8.jpg">
<img src="img/api-docs/9.jpg">
<img src="img/api-docs/10.jpg">
<img src="img/api-docs/11.jpg">
<img src="img/api-docs/12.jpg">
<img src="img/api-docs/13.jpg">
<img src="img/api-docs/14.jpg">
<img src="img/api-docs/15.jpg">
<img src="img/api-docs/16.jpg">
<img src="img/api-docs/17.jpg">
<img src="img/api-docs/18.jpg">
<br/>

## **3. 카카오 클라우드 배포**
> 배포 url : https://user-app.krampoline.com/k9ce14cd5c016a

