## **과제 수행**


1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
- 기능 상세 설명
    - 아이디 찾기 페이지
        - FE
            - 이름 / 이메일 입력 후 “로그인 버튼”을 클릭 시 HTTP Request body에 담아 서버로 요청
        - BE
            - Request로 이름 / 이메일을 입력 받아 Service Class로 전달
            - 데이터베이스에서 이름 / 이메일을 조회 후 Service Class에서 유효한 사용자인지 판단
            - 유효한 사용자일 경우 사용자 아이디 반환
    - 비밀번호 찾기 페이지
        - FE
            - 이름 / 이메일 / 아이디 입력 후 “로그인 버튼”을 클릭 시 HTTP Request body에 담아 서버로 요청
        - BE
            - Request로 이름 / 이메일 / 아이디를 입력 받아 Service Class로 전달
            - 데이터베이스에서 이름 / 이메일을 조회 후 Service Class에서 유효한 사용자인지 판단
            - 유효한 사용자일 경우 사용자 비밀번호 변경 페이지로 리턴
- 인터페이스 요구사항
    - 아이디 찾기 페이지
        - 입력
            - 이름, 이메일 입력 후 아이디 찾기 버튼 클릭
            - 사용자 아이디 정보 페이지 리턴
    - 비밀번호 찾기 페이지
        - 입력
            - 이름, 이메일, 아이디 입력 후 비밀번호 찾기 버튼 클릭
        - 출력
            - 사용자 비밀번호 변경 페이지 리턴



2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)

    👉 Local 서버를 띄우고 Postman으로 API URL 호출하면서 테스트

    
    * 회원 가입 버튼 클릭 시
    ![회원가입](https://github.com/boseungk/TIL/assets/95980754/d6b90d51-7580-4800-9694-5244455fb677)
      * [post] http://localhost:8080/join
    

    * 로그인 버튼 클릭 시
    ![로그인](https://github.com/boseungk/TIL/assets/95980754/e86b71f8-2a81-4583-be1a-b0d5d681aec5)
      * [post] http://localhost:8080/login

    
    * 전체 상품 조회 시
    ![전체 상품](https://github.com/boseungk/TIL/assets/95980754/58420baa-7a07-4e78-b12a-5163162fc6be)
      * [get] http://localhost:8080/products?page=1

    
    * 개별 상품 / 옵션 조회 시
    ![개별 상품](https://github.com/boseungk/TIL/assets/95980754/16d690cf-55f9-47c8-88be-037147edb81f)
      * [get] http://localhost:8080/products/1
  
    
    * 장바구니 담기 버튼 클릭 시 
    ![장바구니 담기](https://github.com/boseungk/TIL/assets/95980754/1142d55f-8e94-4321-aab5-d47d33fd20e4)
      * [post] http://localhost:8080/carts/add
   
   * 장바구니 조회 시
   ![장바구니 조회](https://github.com/boseungk/TIL/assets/95980754/cce205fa-1c55-4316-8672-3af26330359e)
     * [get] http://localhost:8080/carts

    * 장바구니 수량 수정 시
    ![장바구니 수정](https://github.com/boseungk/TIL/assets/95980754/cce205fa-1c55-4316-8672-3af26330359e)
      * [post] http://localhost:8080/update

    * 장바구니 주문하기 버튼 클릭 시
    ![장바구니 수정](https://github.com/boseungk/TIL/assets/95980754/cce205fa-1c55-4316-8672-3af26330359e)
      * [post] http://localhost:8080/update
      * request body 부분이 다름

    * 결제하기 버튼 클릭 시
    ![결제하기](https://github.com/boseungk/TIL/assets/95980754/ba97ee67-f544-49d9-9fb6-7766e9517064)
      * [post]  http://localhost:8080/save

    * 주문 조회 시
    ![주문 조회](https://github.com/boseungk/TIL/assets/95980754/314adac8-8935-4980-b2f2-1fd54bd24eba)
      * [get] http://localhost:8080/orders/1


3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.

    👉 요구사항 2과 3번을 같이 진행하면서 Postman으로 API를 전부 각각 요청해보았고, 부족한 데이터만 기입

    ![전체 상품](https://github.com/boseungk/TIL/assets/95980754/342f93b4-2eda-4c51-8bce-6ebf58502260)
    * 전체 상품 조회 시 응답되는 데이터
      * {"id": 10,
      "productName": "통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정",
      "description": "",
      "image": "/images/10.jpg",
      "price": 8900}
    * 무료배송이나 특별가 데이터가 부족함

    ![개별 상품](https://github.com/boseungk/TIL/assets/95980754/44658742-9ea1-4770-a748-9a2f27e80a06)
   * 개별 상품 / 옵션 조회 시
       * {
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
            }, ...
        ]
    },
    "error": null}
   * 옵션 조회 시 배송비에 대한 데이터가 부족함



4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
    
    ![erd](https://github.com/boseungk/TIL/assets/95980754/35a2a042-2554-4fdb-9579-0bb515a48394)

    ```sql
    Create Table product (
    productId int auto_increment primary key not null,
    productName varchar(20) not null,
    picture	varchar(255) not null,
    price int	NOT NULL,
    date	Date	NOT NULL,
    description	varchar(255)	NOT NULL
    );

    Create Table productOption(
    productOptionId int auto_increment primary key not null,
    productOptionName varchar(20) not null,
    price int not null,
    productId int auto_increment not null,
    foreign key (productId) references product(productId)
    );

    Create Table user(
    userId int auto_increment primary key not null,
    userName varchar(10) not null,
    email varchar(20) not null,
    password varchar(20)
    );

    Create Table cart(
    cartId int auto_increment primary key not null,
    optionNum int not null,
    userId int not null,
    productOptionId int not null,
    foreign key(userId) references user(userId),
    foreign key(productOptionId) references productOption(productOptionId)
    );

    Create Table orderItem(
    orderItem int auto_increment primary key not null,
    quantity int not null,
    price int not null,
    ordersId int not null,
    productOptionId int not null,
    foreign key(ordersId) references orders(ordersId),
    foreign key(productOptionId) references productOption(productOptionId)
    );

    Create Table orders(
    ordersId int auto_increment primary key not null,
    userId int not null,
    foreign key(userId) references user(userId)
    );

    ```
    