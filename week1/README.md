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

</br>

# 1주차 과제 제출
## 요구사항 분석 및 부족한 기능 체크
- 기능 1. 회원가입
    - 회원가입에 대한 기능은 존재하지만 , id, password변경 등 회원정보 수정이 불가능하다.
- 기능 4. 전체 상품 목록 조회
    - 데이터가 같은 페이지에 계속해서 로딩되지 않도록 페이징이 필요하다.
- 기농 5. 개별 상품 상세 조회
    - 개별 상품에 대한 조회 시 유사 상품 또는 다음 상품을 제시하여 쇼핑을 이어나갈 수 있도록 하는 페이지 이동이 없다.
- 기능 13. 주문 결과 확인
    - 현재는 결제 종료 후 로딩되는 방식 이외의 방법으로는 주문 결과를 확인할 수 없다. 주문 결과를 확인할 수 있는 기능을 추가해야 한다.
    - 결제 건에 대한 취소 기능이 존재하지 않는다. 주문 결과 확인에서 결제 취소를 할 수 있는 기능이 필요해 보인다.

## API요청 및 응답 시나리오 분석
> 과제 설명 2와 3을 포함합니다.
### 화원 가입
![회원 가입](/image/week1/signin.png)
- API : /join
- 화면의 회원가입 버튼을 누르면 API가 실행된다.
- Method : `POST`
- Request Body
    ```json
    {
        "username":"meta",
        "email":"meta@nate.com",
        "password":"meta1234!"
    }
    ```
- 응답 데이터
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```

### 로그인
![로그인](/image/week1/login.png)
- API : /login
- 화면의 로그인 버튼을 누르면 API가 호출된다.
- Method : `POST`
- Request Body
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```
- 응답 데이터
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```

### 전체 상품 목록 조회
![전체 상품 목록 조회](/image/week1/products.png)
- API : /products
- 메인 화면 로딩 시 API가 호출된다.
- Method : `GET`
- 응답 데이터
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
        }
    ```

### 개별 상품 상세 조회
![개별 상품 상세 조회](/image/week1/product.png)
- API : /products/${id}
- 개별 상품 이미지를 클릭 시 API가 호출된다.
- Method : `GET`
- 응답 데이터
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
                    "id": 5,
                    "optionName": "2겹 식빵수세미 6매",
                    "price": 8900
                }
            ]
        },
        "error": null
    }
    ```

### 장바구니 담기
![장바구니 담기](/image/week1/addcart.png)
- API : /carts/add
- **배송방법** 위의 옵션 항목을 클릭하면 API가 실행되면서 cart에 추가된다.
- Method : `POST`
- Request Body
    ```json
    [
        {
            "optionId":1,
            "quantity":5
        },
        {
            "optionId":2,
            "quantity":10
        }
    ]
    ```
- 응답 데이터
    ```json
    [
        {
            "optionId":1,
            "quantity":5
        },
        {
            "optionId":2,
            "quantity":10
        }
    ]
    ```

### 장바구니 보기(조회)
![장바구니 업데이트](/image/week1/addcart.png)
![장바구니 보기](/image/week1/cart.png)
- API : /carts
- **총 수량** 밑의 검정색 카트 이미지 버튼을 누르면 API가 실행되면서 현재 페이지의 상품(옵션)이 cart(장바구니)에 담긴다.
- Method : `GET`
- 응답 데이터
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
                                "id": 1,
                                "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                                "price": 10000
                            },
                            "quantity": 1,
                            "price": 10000
                        },
                        {
                            "id": 2,
                            "option": {
                                "id": 2,
                                "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                "price": 10900
                            },
                            "quantity": 1,
                            "price": 10900
                        }
                    ]
                }
            ],
            "totalPrice": 20900
        },
        "error": null
    }
    ```

### 주문(장바구니 수정)
![주문](/image/week1/cart.png)
- API : /carts/update
- 주문하기 버튼을 누르면 API가 실행되면서 cart가 업데이트되고 결제 화면으로 전환된다.
- Method : `POST`
- Request Body
    ```json
    [
        {
            "cartId":1,
            "quantity":10
        },
        {
            "cartId":2,
            "quantity":10
        }
    ]
    ```
- 응답 데이터
    ```json
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
    - 현재는 장바구니의 Product항목이 하나밖에 없는 상황이므로 옵션들(cartId)의 수량 조절에 문제가 없다. 하지만 장바구니에 Product 항목이 여러 개 있다면 이 외에도 상품에 대한 정보가 추가적으로 요구된다.

### 결제
![결제](/image/week1/save.png)
- API : /orders/save
- 결제하기 버튼을 누르면 API가 호출되면서 결제가 진행된다. 결제 시에 cart의 데이터를 그대로 전달하기 때문에 Request Body가 없는 구조이다.(프론트와 협의된 사항이라고 한다.)
- Method : `POST`
- 응답 데이터
    ```json
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
                            "quantity": 10,
                            "price": 100000
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
            "totalPrice": 209000
        },
        "error": null
    }
    ```

### 주문 결과 확인
![주문 결과 확인](/image/week1/orders.png)
- API : /orders/${id}
- 결제가 종료된 후 페이지 로딩 시 API가 호출된다.
- Method : `GET`
- 응답 데이터
    ```json
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

</br>

## 테이블 설계 및 ER-Diagram
### User
```SQL
CREATE TABLE `User` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Product
```SQL
CREATE TABLE `Product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `product_image` varchar(1000) DEFAULT NULL,
  `product_price` int DEFAULT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_UNIQUE` (`id`),
  UNIQUE KEY `product_name_UNIQUE` (`product_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Option
```SQL
CREATE TABLE `Option` (
  `id` int NOT NULL AUTO_INCREMENT,
  `option_name` varchar(100) NOT NULL,
  `option_price` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Cart
```SQL
CREATE TABLE `Cart` (
  `PK` int NOT NULL AUTO_INCREMENT,
  `option_fk` int DEFAULT NULL,
  `user_fk` int DEFAULT NULL,
  `option_amount` int NOT NULL,
  PRIMARY KEY (`PK`),
  UNIQUE KEY `PK_UNIQUE` (`PK`),
  KEY `option_fk_idx` (`option_fk`),
  KEY `user_fk_idx` (`user_fk`),
  CONSTRAINT `option_fk` FOREIGN KEY (`option_fk`) REFERENCES `Option` (`id`),
  CONSTRAINT `user_fk_from_cart` FOREIGN KEY (`user_fk`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Order
```SQL
CREATE TABLE `Order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_fk` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_fk_idx` (`user_fk`),
  CONSTRAINT `user_fk_from_Order` FOREIGN KEY (`user_fk`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Order Item
```SQL
CREATE TABLE `Order_Item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `option_fk` int DEFAULT NULL,
  `option_amount` int DEFAULT NULL,
  `order_number` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `option_fk_from_Order_item_idx` (`option_fk`),
  CONSTRAINT `option_fk_from_Order_item` FOREIGN KEY (`option_fk`) REFERENCES `Option` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### ER-Diagram
![ER-Diagram](/image/week1/er-diagram.png)

</br>

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
