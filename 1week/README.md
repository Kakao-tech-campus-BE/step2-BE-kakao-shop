# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

</br>

# 1주차
카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제</br>

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

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지) 
>- 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
>- 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
>- 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
>- 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

</br>

## **1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.**

### 회원 가입
- 이메일 (아이디) 중복 체크

### 로그인
- 계속된 로그인 요청 실패 시 일정 시간 동안 로그인 제한

### 상품 조회
- 상품명을 키워드로 검색하여 상품 조회
- 해당 상품의 총 판매 횟수를 Response로 반환

### 상품 선택
- 선택한 옵션 취소
- 옵션별 최대 수량 제한

### 장바구니 조회
- 장바구니에 담긴 상품 삭제

### 상품 결제
- 상품 결제 취소

### 기타
- 상품 판매 등록 (상품, 옵션)
- 개인정보 변경
- 주문 내역 조회

</br>

## **2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)**

### (기능 1) 회원 가입
- 회원가입 페이지에서 form을 채우고 회원가입 버튼을 누를 때 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/join

### (기능 2) 로그인
- 로그인 페이지에서 이메일 비밀번호를 채우고 로그인 버튼을 누를 때 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/login

### (기능 4) 전체 상품 목록 조회
- 해당 페이지에 들어가면 전체 상품 목록 조회 API를 호출하여 page값에 따라 상품의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products
- Param : page={number}, 디폴트 값 0

### (기능 5) 개별 상품 상세 조회
- 개별 상품 페이지에 들어가면 해당 API를 호출하여 해당 상품 옵션들의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products/{product_id}

### (기능 8) 장바구니 담기
- 로그인이 필요하며 개별 상품 페이지에서 구입하려는 옵션을 선택한 후 장바구니 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/add

### (기능 9) 장바구니 조회
- 로그인이 필요하며 장바구니 보기 페이지에서 해당 API를 호출하여 현재 유저의 장바구니에 담긴 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/carts

### (기능 11) 주문
- 로그인이 필요하며 장바구니 보기 페이지에서 주문하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/update

### (기능 12) 결제
- 로그인이 필요하며 상품 주문 및 결제 페이지에서 결제하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/orders/save

### (기능 13) 주문 결과 확인
- 유저가 주문에 대해 성공적으로 결제하고 나면 주문 결과 확인 페이지에서 해당 API를 호출한다.
- Method: GET
- URL: http://localhost:8080/orders/{order_id}

</br>

## **3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.**

> 에러 메시지가 잘 주어져 있긴 하지만, 응답코드를 400으로 통일하는 것이 아니라,</br>
> 401, 403, 409 등 조금 더 구체적인 코드로 구분하여 Response로 반환하는 것이 좋을 것 같다.</br>

> [POST] http://localhost:8080/join</br>
> 기존의 User 테이블을 조회하여 이메일 중복을 막아야 한다.</br>
> [POST] http://localhost:8080/login</br>
> 계속된 로그인 요청 실패 시 일정 시간 동안 로그인을 제한하는 것이 좋을 것 같다.</br>
> [GET] http://localhost:8080/products</br>
> 해당 상품의 총 판매 횟수를 Response로 반환하는 것이 좋을 것 같다.</br>
> [POST] http://localhost:8080/carts/update</br>
> quantity를 0으로 설정해도 항목이 사라지지 않고 남아있다.

</br>

## **4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.**

### ER-Diagram
![](https://i.imgur.com/5ZFOdK5.png)

### User 테이블
```sql
CREATE TABLE `User` (
    `id`         BIGINT AUTO_INCREMENT,
    `email`      VARCHAR(100) NOT NULL,
    `user_name`  VARCHAR(45) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `roles`      VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY unique_email (`email`)
);
```
```
roles 필드를 사용하여 사용자의 역할을 표시하면, 역할에 따라 사용자에게 부여되는 권한을 관리하기 쉽다.
roles 필드의 값으로는 admin, consumer, seller 등을 포함할 수 있다.
UNIQUE 제약 조건을 통해 중복된 이메일의 가입을 막을 수 있다.
```

### Product 테이블
```sql
CREATE TABLE `Product` (
    `id`            BIGINT AUTO_INCREMENT,
    `product_name`  VARCHAR(255) NOT NULL,
    `description`   TEXT,
    `image`         VARCHAR(500),
    `price`         INT NOT NULL,
    PRIMARY KEY	(`id`)
);
```
```
description 필드의 값이 커질 것을 대비하여 TEXT 타입을 사용하였다.
```

### Option 테이블
```sql
CREATE TABLE `Option` (
    `id`           BIGINT AUTO_INCREMENT,
    `product_id`   BIGINT NOT NULL,
    `option_name`  VARCHAR(255) NOT NULL,
    `price`        INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`)
);
```
```
Product 테이블과 일대다 관계를 가지며, 하나의 상품은 여러 옵션을 포함할 수 있다.
확장성과 유연성을 위해 Option 테이블을 따로 생성하였다.
```

### Cart 테이블
```sql
CREATE TABLE `Cart` (
    `id`         BIGINT,
    `user_id`    BIGINT NOT NULL,
    `option_id`  BIGINT NOT NULL,
    `quantity`   INT NOT NULL,
    `price`      INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
    FOREIGN KEY (`option_id`) REFERENCES `Option` (`id`)
);
```
```
option_id 필드를 사용하여 Product 테이블의 정보를 조회할 수 있다.
```

### Order 테이블
```sql
CREATE TABLE `Order` (
    `id`       BIGINT AUTO_INCREMENT,
    `user_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
);
```
```
User 테이블과 일대다 관계를 가지며, 한 명의 사용자는 여러 주문을 포함할 수 있다.
```

### Item 테이블
```sql
CREATE TABLE `Item` (
    `id`         BIGINT AUTO_INCREMENT,
    `option_id`  BIGINT NOT NULL,
    `order_id`   BIGINT NOT NULL,
    `quantity`   INT NOT NULL,
    `price`      INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`option_id`) REFERENCES `Option` (`id`),
    FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`)
);
```
```
Order 테이블과 일대다 관계를 가지며, 하나의 주문은 여러 아이템을 포함할 수 있다.
```