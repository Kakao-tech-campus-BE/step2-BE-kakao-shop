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
- **<mark>젊은 여성, 기존 가입자</mark>**
    - 가격의 민감도가 높을 수 있음 ⇒ 다양한 형태의 정렬
    - 유행에 민감 or 자신만의 스타일을 추구하는 과정 ⇒ 제품 확대 및 세부정보
    - 타인과 동일성을 꺼려함 ⇒ 다양한 옵션과 제품군 설정
- <mark>**젊은 여성, 신규 가입자**</mark>
    - 개인정보 등에 대한 불안감이 있을 수 있음 ⇒ 일반적인 가입절차가 아니라 간편 가입절차를 할 확률이 높음
- <mark>**젊은 남성, 기존 가입자**</mark>
    - 가격에 민감하지만, 옵션 설정 등을 귀찮아 함 ⇒ 기존에 구매이력을 보관해서 추천
- <mark>**젊은 남성, 신규 가입자**</mark>
    - 다양한 서비스를 활용 ⇒ 때문에 중복 가입을 하지 않았을까? ⇒ 중복 가입 처리
- <mark>**장년층 여성, 기존 가입자**</mark>
    - 상품의 특성 뿐만 아니라 정보에 민감함 ⇒ 상품 리뷰 및 평점
    - 할인에 대한 선호도가 높음 ⇒ 할인 상품 홍보 및 카테고리 생성
    - 구매 절차에 복잡성을 느낌 ⇒ 사용 카드의 정보 저장 후 원터치 결제
- <mark>**장년층 남녀, 신규 가입자**</mark>
    - 가입 절차에 복잡성을 느낌 ⇒ 그렇다고 간편 가입 절차를 사용할까? ⇒ 기존 회원가입을 간편하게 하면 보안성 문제로 이어지기 때문에 보류
- <mark>**장년층 남성, 기존 가입자**</mark>
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
