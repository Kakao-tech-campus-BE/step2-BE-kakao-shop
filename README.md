# kakao 1주차 과제

### 사용자 시나리오

|  | 젊은 여성 | 젊은 남성 | 장년층 여성 | 장년층 남성 |
| --- | --- | --- | --- | --- |
| 기존 |  |  |  |  |
| 신규 |  |  |  |  |
- 계절 학기 수업에서 배운 것 처럼 Jr의 경우 시나리오를 작성하기 쉽지 않기 때문에
    - 1) 자신을 페르소나로 하여 시나리오를 작성하고,
    - 2) 시나리오는 분절해서 작성할 것
- 표와 같이, 젊은 남녀와 장년층 남녀로 구분해서 시나리오를 작성해보려고 한다.
    - 노년층의 서비스 이용률은 낮을 것이라 예상을 해 제외를 했다.
- ***<mark>젊은 여성, 기존 가입자</mark>***
    - 가격의 민감도가 높을 수 있음 ⇒ 다양한 형태의 정렬
    - 유행에 민감 or 자신만의 스타일을 추구하는 과정 ⇒ 제품 확대 및 세부정보
    - 타인과 동일성을 꺼려함 ⇒ 다양한 옵션과 제품군 설정
- <mark>***젊은 여성, 신규 가입자***</mark>
    - 개인정보 등에 대한 불안감이 있을 수 있음 ⇒ 일반적인 가입절차가 아니라 간편 가입절차를 할 확률이 높음
- <mark>***젊은 남성, 기존 가입자***</mark>
    - 가격에 민감하지만, 옵션 설정 등을 귀찮아 함 ⇒ 기존에 구매이력을 보관해서 추천
- <mark>***젊은 남성, 신규 가입자***</mark>
    - 다양한 서비스를 활용 ⇒ 때문에 중복 가입을 하지 않았을까? ⇒ 중복 가입 처리
- <mark>***장년층 여성, 기존 가입자***</mark>
    - 상품의 특성 뿐만 아니라 정보에 민감함 ⇒ 상품 리뷰 및 평점
    - 할인에 대한 선호도가 높음 ⇒ 할인 상품 홍보 및 카테고리 생성
    - 구매 절차에 복잡성을 느낌 ⇒ 사용 카드의 정보 저장 후 원터치 결제
- <mark>***장년층 남녀, 신규 가입자***</mark>
    - 가입 절차에 복잡성을 느낌 ⇒ 그렇다고 간편 가입 절차를 사용할까? ⇒ 기존 회원가입을 간편하게 하면 보안성 문제로 이어지기 때문에 보류
- <mark>***장년층 남성, 기존 가입자***</mark>
    - 목적성 쇼핑을 지향 ⇒ 구체적 검색 기능 구현

### 요구사항 시나리오를 보고 부족해 보이는 기능 확인                                                                                                                             

```java
"기능적으로 보완이 필요한 기능"
- 회원 가입 후 프로필 설정 기능 및 정보 변경 기능
- 장바구니 삭제 기능
- 로그인 시 간편 가입 절차 기능 추가
- 다양한 간편 가입 또는 기존 로그인에서 이미 가입된 사람의 중복 가입 처리
"명세서에는 없지만 필요한 기능"
- 상품 리뷰 및 평점 기능 
- 다양한 정렬 기능 ( ex) 가격순, 평점순...)
- 제품 사진 확대 및 제품에 관한 세부 설명
- 구매이력에 따른 각 유저만의 추천 시스템
- 옵션(색, 사이즈), 제품군 설정 기능
- 상품 리뷰 및 평점
- 할인 상품 홍보 및 카테고리 생성
- 원터치 결제 기능
- 범용성 있는 검색
- 제품 별 카테고리
- 상품 문의 기능
- 상품 및 옵션 등록, 수정, 삭제 기능                                                                                                     
```

### 화면 설계를 보고 기존 서버의 API 주소 매칭

- **회원가입**
    - 내용 작성 후 회원 가입 버튼을 클릭 시 Post 형식으로 API 주소가 보내진다
    
    <img width="524" alt="Untitled" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/b7986e77-6738-4cb8-bbb7-0fa0f084e1b6">
    
    - Method : Post
    - Local URL : [http://localhost:8080/join](http://localhost:8080/join)
    - Request Body
    
    ```java
    {
    "username":"mata",
    "[email":"meta@nate.com](mailto:email%22:%22meta@nate.com)",
    "password":"meta1234!"
    }
    ```
    
- **로그인**
    - 이메일과 비밀번호 작성 후 로그인 버튼을 클릭 시, User 테이블과 조회 후 존재하는 record이면 JWT 토큰을 발행 후 상품 목록 페이지로 넘어간다
    
    <img width="509" alt="Untitled 1" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/0f76f6a7-6b5b-4b21-b7c5-73475d2922b5">
    
    - Method : Post
    - Local URL : [http://localhost:8080/login](http://localhost:8080/login)
    - Request Body
    
    ```java
    {
    "email":"ssar@nate.com",
    "password":"meta1234!"
    }
    ```
    
- **로그아웃**
    - 상단의 로그아웃 버튼 클릭 시, 발행된 토큰을 회수하고 로그아웃 시킨다
        
        <img width="538" alt="Untitled 2" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/0cf67509-ec60-4442-a522-535e3a58bc53">
        
- **전체 상품 목록 조회**
    - 로그인 버튼 클릭 후 메인 페이지로 들어왔을 때 API 요청을 보내 전체 상품 데이터를 받는다
    
    <img width="450" alt="Untitled 3" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/4f5ffdaf-31dc-4404-b0f3-e1d90f48bcf0">
    
    - Method : Get
    - Local URL : [http://localhost:8080/products](http://localhost:8080/products)
    - Param : page={number}
        - Param의 디폴트 값은 0이다.
        - 총 15개의 상품이 들어가있고 한 페이지 당 10개의 상품을 보여준다
        - 15개의 상품이 존재하니 총 두 페이지로 구성되어 있으며
        - [http://localhost:8080/products](http://localhost:8080/products)?page=1 실행 시 상품 번호 10번 부터 15번까지 나온다
        - 만약 스크롤을 내리다 현재 보여준 상품을 다 보여주고 남은 상품들이 존재할 때 다음 page number를 param으로 담은 api를 요청한다
- **개별 상품 상세 조회**
    - 상품 클릭 시 해당 상품에 관한 데이터를 Get 방식으로 불러오며 페이지에 보여준다
        
        <img width="547" alt="Untitled 4" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/d39717ae-cb58-412b-a24f-bf7f0e59f1de">
        
    - Method : Get
    - Local URL : [http://localhost:8080/products/1](http://localhost:8080/products/1)
- **상품 옵션 선택 및 수량 결정**
    - 옵션 선택 및 수량 결정을 보여주는 경우는 서버와 통신할 필요가 없다 장바구니 담기 또는 구매하기 버튼 클릭 시 해당 내용을 서버로 보내주면 cart 테이블이나 order_item에 담으면 된다
        
        <img width="222" alt="Untitled 5" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/affc3ae5-5fb4-48ce-92c3-7b6f4f59d215">
        
- **장바구니 담기**
    - 옵션과 수량 선택 후 장바구니 담기을 누르면 해당 유저의 토큰 정보와 선택한 옵션과 수량을 Post 형식으로 서버에 보내 cart 테이블에 저장한다
        
        <img width="222" alt="Untitled 5" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/affc3ae5-5fb4-48ce-92c3-7b6f4f59d215">
        
    - Method : Post
    - Local URL : [http://localhost:8080/carts/add](http://localhost:8080/carts/add)
    - Request Header
    
    ```java
    Bearer
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
    JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
    Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
    ```
    
    - Request Body
    
    ```java
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
    
- **장바구니 보기**
    - 우측 상단의 장바구니 버튼을 클릭하면 토큰과 함께 get 형식으로 보낸다
    - cart 테이블의 유저 컬럼과 대조해 응답을 가져온다
        
        <img width="502" alt="Untitled 6" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/bac6c8b4-1294-4f59-928e-923a9ba31027">
        
    - Method : Get
    - Local URL : [http://localhost:8080/carts](http://localhost:8080/carts)
    - Request Header
    
    ```java
    Bearer
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
    JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
    Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
    ```
    
- **장바구니 상품 옵션 확인 및 수량 결정**
    - 장바구니 조회 후 상품 수량을 변경하면 변경 수량이 request body에 담겨 post 형식으로 서버로 전달한다
    - 서버는 변경 수량을 테이블에 저장한다
        
        <img width="502" alt="Untitled 6" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/bac6c8b4-1294-4f59-928e-923a9ba31027">
        
    - Method : Post
    - Local URL : [http://localhost:8080/carts/update](http://localhost:8080/carts/update)
    - Request Header
    
    ```java
    Bearer
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
    JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
    Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
    ```
    
    - Request Body
    
    ```java
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
    
- **주문**
    - 주문하기 버튼을 누르면 장바구니에 담긴 상품들이 프론트 단에서 결제 페이지의 주문 상품 정보로 띄어준다
    - 결제하기 버튼을 클릭하면 상품이 order_item 테이블로 삽입되고 order 테이블의 레코드가 생성된다
        
        <img width="632" alt="화면 캡처 2023-06-29 204902" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/dd3bb591-1ba2-4a01-ba8c-7e6012cf91f2">
        
    - Method : Post
    - Local URL : [http://localhost:8080/orders/save](http://localhost:8080/orders/save)
    - Request Header
    
    ```java
    Bearer
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
    JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
    Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
    ```
    
- **주문 결과 확인**
    - 결제 완료 후 주문 상품의 결과를 확인하기 위해 order 테이블에서 해당 record를 가져온다
    - order_item은 결제가 완료된 상품이 담긴 테이블
        
        <img width="394" alt="Untitled 8" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/57130ff9-8b56-4962-ad37-357df44e7fa4">
        
    - Method : Get
    - Local URL : [http://localhost:8080/orders/1](http://localhost:8080/orders/1)
    - Request Header
    
    ```java
    Bearer
    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
    JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
    Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
    ```
### API를 요청 후 응답되는 데이터를 확인하고 부족한 데이터 체크

- **회원가입**
    - 성공 예시
        - Response Body
        
        ```json
        {
            "success": true,
            "response": null,
            "error": null
        }
        ```
        
    - 실패 예시
        - 동일한 이메일 가입 예시
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "동일한 이메일이 존재합니다 : kim@nate.com",
                "status": 400
            }
        }
        ```
        
        - 잘못된 이메일 형식 예시
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "이메일 형식으로 작성해주세요:email",
                "status": 400
            }
        }
        ```
        
        - 잘못된 비밀번호 형식 예시
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
                "status": 400
            }
        }
        ```
        
        - 잘못된 비밀번호 형식 예시 2
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "8에서 20자 이내여야 합니다.:password",
                "status": 400
            }
        }
        ```
        
- **로그인**
    - 성공 예시
        - Response Body
        
        ```json
        {
            "success": true,
            "response": null,
            "error": null
        }
        ```
        
    - 실패 예시
        - API 자료에는 비밀번호 오류와 이메일 오류를 구분해놨는데, 다른 서비스들을 둘러보니 오류들을 구분하지 않았다
        - 구분해서 메시지를 띄우는 것만으로도 악의적인 사용자들에게 정보를 제공할 수 있기 때문에 다음과 같은 메시지만 띄우는 것이 좋아보인다
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "이메일 또는 비밀번호가 잘못 입력되었습니다",
                "status": 400
            }
        }
        ```
        
- **전체 상품 목록 조회**
    - 성공 예시
        - Response Body
        
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
        
    - 실패 예시
        - post 형식으로 요청
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "Request method 'POST' not supported",
                "status": 500
            }
        }
        ```
        
    
    ⇒ **프론트앤드 화면과 비교해서 배송 관련 비용에 관한 데이터가 없어서 추가를 고려**
    
- **개별 상품 상세 조회**
    - 성공 사례
        - Response Body
        
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
        
        ⇒  **별점에 관한 데이터와 배송 관련 비용, 총 수량, 총 주문금액에 관한 데이터가 없다**
        
          배송 관련 비용 데이터는 앞서 product 테이블에 추가해준다면 해당 option 테이블에는 추가할 필요 없어보인다
        
          총 수량, 총 주문 금액의 경우 컬럼 생성과 연산 로직 구현을 비용과 정합성 등 여러 요소를 가지고 결정해야한다.
        
          장바구니의 경우 담겨 있는 상품의 개수가 많지 않을 것이라 생각이 든다. 따라서 연산을 통해 총 수량과 주문금액을 결정한다.

          만약 연산 때문에 병목 현상이 발생한다면 컬럼 생성을 고려한다
        
- **장바구니 담기**
    - 성공 예시
        - Response Body
        
        ```json
        {
        "success": true,
        "response": null,
        "error": null
        }
        ```
        
    - 실패 예시
        - 장바구니에 이미 담겨 있는 상품 옵션을 다음과 같이 또 보낸다면 에러가 발생한다
        - 옵션이 현재 담겨있을 때는 update 해줘야한다
        - 클라이언트에게 이미 담긴 옵션이라고 메시지를 주는 것 또는 서버에서 같은 상품을 담았으면 개수를 증가시키는 방법으로 해결할 수 있다
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "장바구니 담기 중에 오류가 발생했습니다 : could not execute statement; SQL [n/a]; constraint [\"PUBLIC.UK_CART_OPTION_USER_INDEX_4 ON PUBLIC.CART_TB(USER_ID NULLS FIRST, OPTION_ID NULLS FIRST) VALUES ( /* key:1 */ 1, 5)\"; SQL statement:\ninsert into cart_tb (id, option_id, price, quantity, user_id) values (default, ?, ?, ?, ?) [23505-214]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
                "status": 500
            }
        }
        ```
        
        - 사용자 인증 실패
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "인증되지 않았습니다",
                "status": 401
            }
        }
        ```
        

- **장바구니 조회**
    - 성공 예시
        - Response Body
        
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
                                "id": 2,
                                "option": {
                                    "id": 2,
                                    "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                    "price": 10900
                                },
                                "quantity": 5,
                                "price": 54500
                            },
                            {
                                "id": 1,
                                "option": {
                                    "id": 5,
                                    "optionName": "2겹 식빵수세미 6매",
                                    "price": 8900
                                },
                                "quantity": 5,
                                "price": 44500
                            }
                        ]
                    },
                    {
                        "id": 3,
                        "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                        "carts": [
                            {
                                "id": 8,
                                "option": {
                                    "id": 10,
                                    "optionName": "JR310BT (무선 전용) - 레드",
                                    "price": 49900
                                },
                                "quantity": 5,
                                "price": 249500
                            }
                        ]
                    },
                    {
                        "id": 4,
                        "productName": "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
                        "carts": [
                            {
                                "id": 7,
                                "option": {
                                    "id": 15,
                                    "optionName": "선택01_바른곡물효소 누룽지맛 2박스",
                                    "price": 17900
                                },
                                "quantity": 5,
                                "price": 89500
                            }
                        ]
                    }
                ],
                "totalPrice": 438000
            },
            "error": null
        }
        ```
        

- **장바구니 수정**
    - 성공 예시
        - 프론트앤드단에서 옵션 수량 변경 시 서버와 통신하지 않고 변경했다가 주문하기 버튼 클릭 또는 새로 고침 시 서버에게 post 형식으로 정보를 보내는 것이 비용적인 부분에서 효율적이라고 생각한다
        - Response Body
        
        ```json
        {
            "success": true,
            "response": {
                "carts": [
                    {
                        "cartId": 1,
                        "optionId": 5,
                        "optionName": "2겹 식빵수세미 6매",
                        "quantity": 10,
                        "price": 89000
                    },
                    {
                        "cartId": 2,
                        "optionId": 2,
                        "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                        "quantity": 10,
                        "price": 109000
                    },
                    {
                        "cartId": 7,
                        "optionId": 15,
                        "optionName": "선택01_바른곡물효소 누룽지맛 2박스",
                        "quantity": 5,
                        "price": 89500
                    },
                    {
                        "cartId": 8,
                        "optionId": 10,
                        "optionName": "JR310BT (무선 전용) - 레드",
                        "quantity": 5,
                        "price": 249500
                    }
                ],
                "totalPrice": 537000
            },
            "error": null
        }
        ```
        
    - 실패 예시
        - 장바구니에 없는 상품 업데이트
        
        ```json
        {
            "success": false,
            "response": null,
            "error": {
                "message": "장바구니에 없는 상품은 주문할 수 없습니다 : 10",
                "status": 400
            }
        }
        ```
        

- **결제하기**
    - 성공 예시
        - **결제하기 버튼 클릭 시 상품의 데이터가 order_item 테이블에 저장되고 이러한 데이터가 저장되었다고 알려주기 위해 Response Body를 보내는지 궁금하다 왜냐하면 cart 테이블에서 order_item 테이블로 저장하는 것은 데이터 베이스 내부에서 처리하는 일이기 때문이다.**
        - Response Body
        
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
                                "optionName": "2겹 식빵수세미 6매",
                                "quantity": 10,
                                "price": 89000
                            },
                            {
                                "id": 2,
                                "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                "quantity": 10,
                                "price": 109000
                            }
                        ]
                    },
                    {
                        "productName": "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
                        "items": [
                            {
                                "id": 3,
                                "optionName": "선택01_바른곡물효소 누룽지맛 2박스",
                                "quantity": 5,
                                "price": 89500
                            }
                        ]
                    },
                    {
                        "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                        "items": [
                            {
                                "id": 4,
                                "optionName": "JR310BT (무선 전용) - 레드",
                                "quantity": 5,
                                "price": 249500
                            }
                        ]
                    }
                ],
                "totalPrice": 537000
            },
            "error": null
        }
        ```
        

- **주문 결과 확인**
    - 성공 예시
        - Response Body
        
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
                                "optionName": "2겹 식빵수세미 6매",
                                "quantity": 10,
                                "price": 89000
                            },
                            {
                                "id": 2,
                                "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                "quantity": 10,
                                "price": 109000
                            }
                        ]
                    },
                    {
                        "productName": "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
                        "items": [
                            {
                                "id": 3,
                                "optionName": "선택01_바른곡물효소 누룽지맛 2박스",
                                "quantity": 5,
                                "price": 89500
                            }
                        ]
                    },
                    {
                        "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                        "items": [
                            {
                                "id": 4,
                                "optionName": "JR310BT (무선 전용) - 레드",
                                "quantity": 5,
                                "price": 249500
                            }
                        ]
                    }
                ],
                "totalPrice": 537000
            },
            "error": null
        }
        ```
        
    

### ER-Diagram

```
USER                         
-PK (INT)                    PRODUCT
-username (VARCHAR(20))      -PK (INT)
-email (VARCHAR(30))         -productname (VARCHAR(50))
-password (VARCHAR(20))      -image (VARCHAR(30))
-createAt (DATETIME)         -price (INT)
-roles (VARCHAR(10))         -createAt (DATETIME)

OPTION                       CART
-PK (INT)                    -PK (INT)
-optionname (VARCHAH(50))    -USER FK (INT)
-price (INT)                 -OPTION FK (INT)
-PRODUCT FK (INT)            -quantity (INT)
 
ORDER                         ORDER ITEM
-PK (INT)                     -PK (INT)
-USER FK (INT)                -ORDER FK (INT)
-createAt (DATETIME)          -option price (INT)
                              -OPTION FK (INT)
                              -quantity (INT)
                              -USER FK (INT)
                              -updateAt (DATETIME) 
REVIEW
-PK (INT)
-PRODUCT FK (INT)
-description (VARCHAR (1000))
-USER FK (INT)
-createAt (DATETIME)
-updateAt (DATETIME)
-starCount (INT)
```

<img width="487" alt="ER-Diagram" src="https://github.com/ki-met-hoon/go-home-app/assets/101192772/d02f6634-1c71-44ab-864b-c596b20b5e25">
