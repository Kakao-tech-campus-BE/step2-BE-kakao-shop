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

***
# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

## **과제명**

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

# 2주차 과제

## 1. API주소를 설계하세요.

**이메일 중복 확인 API**

Request URL : [localhost:8080/check](localhost:8080/check)

Request Header

```java
POST /check HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080
```

Request Body 

```json
{
"email":"ssar@nate.com"
}
```

Response 

```json
{
    "success": true,
    "response": null,
    "error": null
}
```

**회원가입 폼 제출 API**

Request URL : [http://localhost:8080/join](http://localhost:8080/join)

Request Header

```java
POST /join HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080
```

Request Body 

```json
{
"email":"ssar@nate.com",
"username":"jinjin",
"password":"1234"
}
```

Response 

```json
ok
```

**로그인 API**

Request URL : [http://localhost:8080/login](http://localhost:8080/login)

Request Header

```java
POST /login HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080
```

Request Body

```json
{
"email":"ssar@nate.com",
"password":"meta1234!"
}
```

Response

```jsx
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4NzA2NjN9.1SRXeFmtse8e2xG4gvg2NzvhZwbHQk9c3iK7516l19Cqf1TZzzdvHkFOhCs8G4iXySu1SSsWwlDS3WfXJQr2Ug
```

```json
{
    "success": true,
    "response": null,
    "error": null
}
```

**상품 조회 API**

Request URL : [http://localhost:8080/products](http://localhost:8080/products)

Request Header

```java
GET /products HTTP/1.1
Host: localhost:8080
```

Request Body

```json

```

Response 

```json
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
}{
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
                "id": 4,
                "optionName": "2겹 식빵수세미 6매",
                "price": 8900
            }
        ]
    },
    "error": null
}
```

**상품 상세 조회 API**

Request URL : [http://localhost:8080/products/1](http://localhost:8080/products/1)

Request Header

```java
GET /products/1 HTTP/1.1
Host: localhost:8080
```

Request Body

```json

```

Response 

```json
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
                "id": 4,
                "optionName": "2겹 식빵수세미 6매",
                "price": 8900
            }
        ]
    },
    "error": null
}
```

**장바구니 추가 API**

Request URL : [http://localhost:8080/carts/add](http://localhost:8080/carts/add)

Request Header

```java
POST /carts/add HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnABearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
```

Request Body

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

Response 

```json
{
    "success": true,
    "response": null,
    "error": null
}
```

**장바구니 조회**

Request URL : [http://localhost:8080/carts](http://localhost:8080/carts/add)

Request Head

```java
GET /carts HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnABearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
```

Request Body

```json

```

Response 

```json
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
                            "id": 5,
                            "optionName": "2겹 식빵수세미 6매",
                            "price": 8900
                        },
                        "quantity": 3,
                        "price": 26700
                    }
                ]
            },
            {
                "id": 3,
                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                "carts": [
                    {
                        "id": 2,
                        "option": {
                            "id": 10,
                            "optionName": "JR310BT (무선 전용) - 레드",
                            "price": 49900
                        },
                        "quantity": 4,
                        "price": 199600
                    },
                    {
                        "id": 3,
                        "option": {
                            "id": 11,
                            "optionName": "JR310BT (무선 전용) - 그린",
                            "price": 49900
                        },
                        "quantity": 5,
                        "price": 249500
                    }
                ]
            }
        ],
        "totalPrice": 475800
    },
    "error": null
}
```

**장바구니 수정 API**

Request URL : [http://localhost:8080/carts/update](http://localhost:8080/carts/update)

Request Header

```java
PUT /carts/update HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnABearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
```

Request Body

```json
[ {
  "cartId" : 1,
  "quantity" : 3
}, {
  "cartId" : 2,
  "quantity" : 5
} ]
```

Response 

```json
{
    "success": true,
    "response": {
        "carts": [
            {
                "cartId": 1,
                "optionId": 5,
                "optionName": "2겹 식빵수세미 6매",
                "quantity": 3,
                "price": 26700
            },
            {
                "cartId": 2,
                "optionId": 10,
                "optionName": "JR310BT (무선 전용) - 레드",
                "quantity": 5,
                "price": 249500
            },
            {
                "cartId": 3,
                "optionId": 11,
                "optionName": "JR310BT (무선 전용) - 그린",
                "quantity": 5,
                "price": 249500
            }
        ],
        "totalPrice": 525700
    },
    "error": null
}
```

**주문 API**

request URL : [http://localhost:8080/orders/save](http://localhost:8080/orders/save)

Request Header

```java
POST /order/save HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnABearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
```

Request Body

```json

```

Response 

```json
{
    "success": true,
    "response": {
        "id": 1,
        "products": [
            {
                "id": 0,
                "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                "items": [
                    {
                        "id": 4,
                        "optionName": "2겹 식빵수세미 6매",
                        "quantity": 3,
                        "price": 26700
                    }
                ]
            },
            {
                "id": 0,
                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                "items": [
                    {
                        "id": 5,
                        "optionName": "JR310BT (무선 전용) - 레드",
                        "quantity": 4,
                        "price": 199600
                    },
                    {
                        "id": 6,
                        "optionName": "JR310BT (무선 전용) - 그린",
                        "quantity": 5,
                        "price": 249500
                    }
                ]
            }
        ],
        "totalPrice": 475800
    },
    "error": null
}
```

**주문내역 API**

request URL : [http://localhost:8080/orders/1](http://localhost:8080/orders/1)

Request Header

```java
GET /order/1 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4MDI3Njd9.fKRctzvLSsiOz08ZgLIdWyCuMXxHYgYeI7EmiS2CcS2OPV18m9cUe7yTOhttfom24rsfsgLP8s1Wu23uKy4TnABearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
```

Request Body

```json

```

Response 

```json
{
    "success": true,
    "response": {
        "id": 1,
        "products": [
            {
                "id": 0,
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
                        "quantity": 5,
                        "price": 54500
                    }
                ]
            },
            {
                "id": 0,
                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                "items": [
                    {
                        "id": 3,
                        "optionName": "JR310BT (무선 전용) - 레드",
                        "quantity": 5,
                        "price": 249500
                    }
                ]
            }
        ],
        "totalPrice": 354000
    },
    "error": null
}
```

## 2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 테스트하세요.

- 스프링부트 프로젝트에서 API문서를 만족시키는 API가 구현되어 있지 않은 부분은 Cart부분과 Order부분이었고, 관련한 DTO를 만들고, URL을 매핑할 함수를 생성해야 한다.

### 1. CartRestController

**생성 DTO**

update를 할 때 API에서 요청한 형태가 달라 DTO를 하나 새로 만들어 주었다.

![Untitled](week2/week2/Untitled.png)

request요청이 들어올 때 request예상에 맞춰 DTO를 만들었다. 추후에 다른 메서드들이 추가되면 한 파일에 request DTO들을 관리할 수 있다.

![Untitled](week2/week2/Untitled%201.png)

**장바구니 추가 API**

add, update 부분이 매핑이 되어있지 않았고, API 문서에서 요청된 형태에 맞게 반환하였다.

update부분은 Putmapping을 사용해서 업데이트를 하는 API임을 명시해주었다.

**URL : /carts/add**

![Untitled](week2/week2/Untitled%202.png)

**장바구니 수정 API**

**URL : /carts/update**

![Untitled](week2/week2/Untitled%203.png)

### 2. OrderRestController

**생성 DTO**

- ItemOptionDTO

주문한 item들의 option을 저장하는 dto이다. product 관련하여 가장 작은 단위이다.

![Untitled](week2/week2/Untitled%204.png)

- OrderItemDTO

주문한 Item이 무엇인지 저장하는 dto이다. 주문한 상품에 대한 정보이며, ItemOptionDTO의 리스트를 속성으로 갖는다.

![Untitled](week2/week2/Untitled%205.png)

- OrderRespFindByIdDTO

주문내역은 모든 ID의 주문내역을 return하는 DTO는 불필요하다고 생각했고, Id로 데이터를 찾는 OrderRespFindByIdDTO는 필수라고 생각했다.

![Untitled](week2/week2/Untitled%206.png)

**주문내역 API**

id에 맞는 주문내역을 read하는 API이므로 Get으로 매핑한다.

**URL : /orders/<int:id>**

![Untitled](week2/week2/Untitled%207.png)

**주문 API**

주문할 정보를 장바구니에서 불러와 주문내역에 저장하는 API로 새로 write하는 기능이기 때문에 Post로 매핑하였다.

**URL : /orders/save**

![Untitled](week2/week2/Untitled%208.png)

## Mock Test

- 테스트 유닛 요소
    
    각각의 테스트 유닛은 Cart, Order, Product의 controller 테스트이며, input에 따른 output이 동일한지를 확인하는 블랙박스 테스팅이다. 결과 값이 예측 값과 동일하다면 테스트가 통과되고 그렇지 않다면 오류가 발생한다. mock mvc를 활용하였다.
    

![Untitled](week2/week2/Untitled%209.png)

- 결과
    
    테스트를 통과하여 체크표시와 함께 빌드가 성공한 것을 확인할 수 있다.
    

![Untitled](week2/week2/Untitled%2010.png)

---

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
