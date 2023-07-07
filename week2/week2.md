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

![Untitled](week2/Untitled.png)

request요청이 들어올 때 request예상에 맞춰 DTO를 만들었다. 추후에 다른 메서드들이 추가되면 한 파일에 request DTO들을 관리할 수 있다.

![Untitled](week2/Untitled%201.png)

**장바구니 추가 API**

add, update 부분이 매핑이 되어있지 않았고, API 문서에서 요청된 형태에 맞게 반환하였다.

update부분은 Putmapping을 사용해서 업데이트를 하는 API임을 명시해주었다.

**URL : /carts/add**

![Untitled](week2/Untitled%202.png)

**장바구니 수정 API**

**URL : /carts/update**

![Untitled](week2/Untitled%203.png)

### 2. OrderRestController

**생성 DTO**

- ItemOptionDTO

주문한 item들의 option을 저장하는 dto이다. product 관련하여 가장 작은 단위이다.

![Untitled](week2/Untitled%204.png)

- OrderItemDTO

주문한 Item이 무엇인지 저장하는 dto이다. 주문한 상품에 대한 정보이며, ItemOptionDTO의 리스트를 속성으로 갖는다.

![Untitled](week2/Untitled%205.png)

- OrderRespFindByIdDTO

주문내역은 모든 ID의 주문내역을 return하는 DTO는 불필요하다고 생각했고, Id로 데이터를 찾는 OrderRespFindByIdDTO는 필수라고 생각했다.

![Untitled](week2/Untitled%206.png)

**주문내역 API**

id에 맞는 주문내역을 read하는 API이므로 Get으로 매핑한다.

**URL : /orders/<int:id>**

![Untitled](week2/Untitled%207.png)

**주문 API**

주문할 정보를 장바구니에서 불러와 주문내역에 저장하는 API로 새로 write하는 기능이기 때문에 Post로 매핑하였다.

**URL : /orders/save**

![Untitled](week2/Untitled%208.png)

## Mock Test

- 테스트 유닛 요소
    
    각각의 테스트 유닛은 Cart, Order, Product의 controller 테스트이며, input에 따른 output이 동일한지를 확인하는 블랙박스 테스팅이다. 결과 값이 예측 값과 동일하다면 테스트가 통과되고 그렇지 않다면 오류가 발생한다. mock mvc를 활용하였다.
    

![Untitled](week2/Untitled%209.png)

- 결과
    
    테스트를 통과하여 체크표시와 함께 빌드가 성공한 것을 확인할 수 있다.
    

![Untitled](week2/Untitled%2010.png)

---