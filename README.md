# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.


[1주차](#1주차)

[2주차](#2주차)

[3주차](#3주차)

[4주차](#4주차)

[5주차](#5주차)

[6주차](#6주차)





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

## 과제1. 요구사항분석/API요청 및 응답 시나리오 분석
1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
```
1. (회원가입)이메일 아이디 유효성 검사
   -영문+숫자@영문+숫자.영문+숫자

2. (회원가입)비밀번호 유효성 검사
   -영문,숫자,특수문자 포함
   -공백없음
   -8~20자

3. (회원가입)이메일중복확인
   -데이터베이스에 동일한 이메일이 있는지 체크

4. (로그인,로그아웃)로그인유지,로그아웃
   -리프레시토큰
   -redis

5.(장바구니 담기) 상품 중복 담기
   -같은 상품 장바구니 담을 시 에러발생
   -상품의 수가 추가되는 방식으로 수정하여야 한다.


아래는 요구사항 시나리오에는 없지만 필요하다고 생각되는 api
1. 찜하기
   - 상품찜: 해당상품의 하트버튼을 클릭시 해당 상품이 데이터베이스에 저장됨
   - 스토어찜: 해당스토어의 하트버튼을 클릭시 해당 스토어가 데이터베이스에 저장됨

2. 유저 구분
   - 상품판매자와 구매자를 구분해야 한다.
   - 상품판매자의 경우 상품, 옵션을 추가, 수정, 삭제하는 기능이 필요하다.

3. 환불
   - 환불기능이 있어야 한다.

4. 유저
   - 회원등급
   - 상태: 휴면, 탈퇴등
   - 쇼핑포인트
   - 환불머니
   - 판매자, 관리자, 소비자 role구분

5. 공지사항
   - 작성
   - 조회: 제목, 내용, 작성날짜

6. 리뷰
   - 작성
   - 수정
   - 조회: 작성자, 구매옵션, 사진, 리뷰내용, 도움돼요수, 작성일, 별점, 배송만족글, 작성시각

7. 문의
   - 작성
   - 수정
   - 조회: 작성자, 제목, 글내용, 작성시각,비밀글 여부, 답변여부

8. 배송
   - 배송상태

9. 스토어
   - 상품을 판매하는 스토어 정보를 제공해야한다.

10. 장바구니의 일부품목만 구매하거나 삭제가능해야 한다.

11. 검색기능
   - 상품검색기능이 추가되어야 한다.

12. 배송과 관련된 기능이 필요하다.

13. 유저의 편의성을 위해 소셜간편가입 기능이 필요하다.

14. 장바구니가 담기가 아닌 바로구매 기능이 필요하다.

15. 배송주소정보관련 기능
   -배송지정보를 저장하고 불러올 수 있는 기능이 필요하다

16. 결제관련 기능
	 -무통장
	 -카드

```
2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)
```
회원가입
- API 요청 시기: 회원가입 버튼 클릭시
- Method: Post
- URL: /join

로그인
- API 요청 시기: 로그인 버튼 클릭시
- Method: Post
- URL: /login

로그아웃
- API 요청 시기: 로그아웃 버튼 클릭시
- Method: Post
- URL: 문서에 없음

전체 상품 목록 조회
- API 요청 시기: 페이지 방문시, 스크롤을 내릴시(더보기 버튼누를시)
- Method : Get
- URL : /products 
- Param : page={number}

개별 상품 상세 조회
- Method : Get
- API 요청 시기: 개별 상품 페이지 방문시
- URL : /products/{상품id}

상품 옵션 선택
- API 요청 시기: 개별 상품 페이지 방문시
- Method : Get
- URL : /products/{상품id}

옵션 확인 및 수량 결정
- API 요청 시기: 개별 상품 페이지 방문시
- Method : Get
- URL : /products/{상품id}

장바구니 담기
- API 요청 시기: 장바구니 담기 버튼 클릭시
- Method: Post
- URL: /carts/add

장바구니 보기
- API 요청 시기: 장바구니 페이지 방문시
- Method: Get
- URL: /carts

장바구니 상품 옵션 확인 및 수량 결정 / 주문
- API 요청 시기: 주문하기 버튼 클릭시
- Method: Post
- URL: carts/update

결제(실제 결제 절차없이 상품을 주문한 것으로 처리)
- API 요청 시기: 결제하기 버튼 클릭시
- Method: Post
- URL: /orders/save

주문 결과 확인
- API 요청 시기: 결제완료 후 구매완료 페이지 방문시
- Method: Get
- URL: /orders/{주문번호}
```
3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
```
아래는 배포된서버에 있는 API에서 부족한 데이터를 적은 것이다.


전체상품목록조회
- 배송비: 무료배송인지, 배송비가 얼마인지 필요하다.
- 구매한 인원수
- 찜 여부

개별상품상세조회
- 별점
- 배송비
- 배송방법
- 스토어 정보
- 찜 여부
- 구매자 수
- 남은 재고 수

회원가입
- 패스워드 암호화: 패스워드를 그대로 보내게 되면 보안상 위험하다.

로그인
- 패스워드 암호화: 패스워드를 그대로 보내게 되면 보안상 위험하다.

장바구니 조회
- 배송비
- 스토어명
- 할인되는 금액
- 장바구니에 들어있는 건수(상품수): 스토어별로 배송이 따로됨
- 스토어별 금액합계
- 전체 총 합계

결제하기-(주문인서트)
- 배송비
- 스토어명
- 할인되는 금액
- 옵션의 총 수량
- 전체 총 합계

주문결과확인
- 스토어명
- 스토어별 결제금액

아래는 배포서버에는 없지만 필요하다고 생각되는 api들이다.

찜 관련 api

유저 관련 api
-프로필
-등급
-상태: 휴먼, 탈퇴 등
-쇼핑포인트
-환불머니
-역할: 판매자,구매자,관리자

환불 관련 api

공지사항 관련 api

리뷰 관련 api

문의 관련 api

배송관련 api

스토어 관련 api

바로구매 api

검색관련 api

판매자 관련 api
-판매통계
-상품등록,삭제

관리자 api

소셜간편로그인,회원가입이 지원되는 로그인api

배송지관련 api
-배송지 저장,수정
-배송지 조회

결제관련 api
-무통장
-카드

```
## 과제2. 요구사항 추가 반영 및 테이블 설계도
4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.

![ERD](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fd3SBK5%2FbtslZDYv5ue%2FHXYYKJpghwL5cuc6uWMhAk%2Fimg.png)

PDF과제
```
유저
- id(PK)
- 이메일
- 이름
- 비밀번호
- 역할
- 회원등급
- 상태: 휴먼 탈퇴 등
- 쇼핑포인트
- 환불머니
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`user_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(45) NOT NULL DEFAULT 'normal',
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'normal',
  `level` VARCHAR(45) NOT NULL DEFAULT 'normal',
  `shopping_point` INT(13) NOT NULL DEFAULT 0,
  `refund_money` INT(13) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))




상품
- id(PK)
- 상품명
- 상세정보
- 상품 가격(옵션 최저가)
- 상태
- 배송비
- 카테고리
- 스토어id(FK,N:1)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`product_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NULL,
  `image` VARCHAR(500) NULL,
  `price` INT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `store_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_tb_store_tb1_idx` (`store_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_tb_store_tb1`
    FOREIGN KEY (`store_tb_id`)
    REFERENCES `mydb`.`store_tb` (`id`)
    )




옵션
- id(PK)
- 옵션명
- 가격
- 재고수
- 상품id(FK, N:1)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`option_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `option_name` VARCHAR(100) NOT NULL,
  `price` INT NOT NULL,
  `quantity` INT NOT NULL,
  `product_tb_id` INT NOT NULL,
  `cart_tb_id` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_option_tb_product_tb_idx` (`product_tb_id` ASC) VISIBLE,
  INDEX `fk_option_tb_cart_tb1_idx` (`cart_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_option_tb_product_tb`
    FOREIGN KEY (`product_tb_id`)
    REFERENCES `mydb`.`product_tb` (`id`)
    ,
  CONSTRAINT `fk_option_tb_cart_tb1`
    FOREIGN KEY (`cart_tb_id`)
    REFERENCES `mydb`.`cart_tb` (`id`)
   )



장바구니
- id(PK)
- 수량
- 가격
- 옵션id(FK ,1:N)
- 유저id(FK, 1:1)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`cart_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `price` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `user_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cart_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
)





주문
- id
- 유저id(FK, N:1)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`order_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_tb_id` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
   )





주문 아이템
- id
- 주문 수량
- 가격
- 옵션id(FK, 1:1)
- 주문id(FK, N:1)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`item_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `price` VARCHAR(13) NOT NULL,
  `option_tb_id` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `order_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_item_tb_option_tb1_idx` (`option_tb_id` ASC) VISIBLE,
  INDEX `fk_item_tb_order_tb1_idx` (`order_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_item_tb_option_tb1`
    FOREIGN KEY (`option_tb_id`)
    REFERENCES `mydb`.`option_tb` (`id`)
    ,
  CONSTRAINT `fk_item_tb_order_tb1`
    FOREIGN KEY (`order_tb_id`)
    REFERENCES `mydb`.`order_tb` (`id`)
    )






유저 이미지
- id
- 이미지 주소
- 생성 시각
- 수정 시각
- 유저id(FK, N:1)

CREATE TABLE IF NOT EXISTS `mydb`.`user_image_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(500) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `user_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_image_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_image_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
    )







상품이미지
- id
- 이미지 주소
- 생성 시각
- 수정 시각
- 상품id(FK, N:1)

CREATE TABLE IF NOT EXISTS `mydb`.`product_image_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(500) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `product_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_image_tb_product_tb1_idx` (`product_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_image_tb_product_tb1`
    FOREIGN KEY (`product_tb_id`)
    REFERENCES `mydb`.`product_tb` (`id`)
    )


스토어이미지
- id
- 이미지 주소
- 생성 시각
- 수정 시각
- 스토어id(FK, N:1)

CREATE TABLE IF NOT EXISTS `mydb`.`store_image_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(500) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `store_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_store_image_tb_store_tb1_idx` (`store_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_store_image_tb_store_tb1`
    FOREIGN KEY (`store_tb_id`)
    REFERENCES `mydb`.`store_tb` (`id`)
    )






리뷰이미지
- id
- 이미지 주소
- 생성 시각
- 수정 시각
- 리뷰id(FK, N:1)

CREATE TABLE IF NOT EXISTS `mydb`.`review_image_tb` (
  `id` INT NOT NULL,
  `image` VARCHAR(500) NOT NULL,
  `creatd_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `review_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_review_image_tb_review_tb1_idx` (`review_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_image_tb_review_tb1`
    FOREIGN KEY (`review_tb_id`)
    REFERENCES `mydb`.`review_tb` (`id`)
    )






문의이미지
- id
- 이미지 주소
- 생성 시각
- 수정 시각
- 문의id(FK, N:1)

CREATE TABLE IF NOT EXISTS `mydb`.`inquiry_image_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(500) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `inquiry_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_inquiry_image_tb_inquiry_tb1_idx` (`inquiry_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_inquiry_image_tb_inquiry_tb1`
    FOREIGN KEY (`inquiry_tb_id`)
    REFERENCES `mydb`.`inquiry_tb` (`id`)
    )






스토어
- id
- 스토어 이름
- 스토어 설명
- 상호명
- 대표자
- 대표전화
- 대표메일
- 사업자등록번호
- 통신판매업번호
- 사업장 소재지
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`store_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `store_name` VARCHAR(100) NOT NULL,
  `business_name` VARCHAR(100) NULL,
  `description` VARCHAR(8000) NULL,
  `representative` VARCHAR(50) NULL,
  `representative_number` VARCHAR(50) NULL,
  `representative_email` VARCHAR(100) NULL,
  `company_registration_number` VARCHAR(100) NULL,
  `mail_order_sales_registration_number` VARCHAR(100) NULL,
  `address` VARCHAR(200) NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`))


상품 찜(wish)
- id
- 유저id(FK, N:1)
- 상품id(FK, 1:N)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`product_wish_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `user_tb_id` INT NOT NULL,
  `product_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_wish_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  INDEX `fk_wish_tb_product_tb1_idx` (`product_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_wish_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
    ,
  CONSTRAINT `fk_wish_tb_product_tb1`
    FOREIGN KEY (`product_tb_id`)
    REFERENCES `mydb`.`product_tb` (`id`)
   )






스토어 찜
- id
- 유저id(FK, N:1)
- 스토어id(FK, 1:N)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`store_wish_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `user_tb_id` INT NOT NULL,
  `store_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_wish_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  INDEX `fk_wish_tb_store_tb1_idx` (`store_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_wish_tb_user_tb10`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
    ,
  CONSTRAINT `fk_wish_tb_store_tb10`
    FOREIGN KEY (`store_tb_id`)
    REFERENCES `mydb`.`store_tb` (`id`)
    )








리뷰
- id
- 유저id(FK,N:1)
- 상품id(FK , N:1)
- 제목
- 글내용
- 비밀글 여부
- 별점
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`review_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `content` VARCHAR(8000) NOT NULL,
  `star` INT NOT NULL,
  `private` TINYINT NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `product_tb_id` INT NOT NULL,
  `user_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_review_tb_product_tb1_idx` (`product_tb_id` ASC) VISIBLE,
  INDEX `fk_review_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_tb_product_tb1`
    FOREIGN KEY (`product_tb_id`)
    REFERENCES `mydb`.`product_tb` (`id`)
    ,
  CONSTRAINT `fk_review_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
    )






도움돼요
- id
- 유저id(FK, 1:N)
- 리뷰id(FK, 1:N)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`helpful_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `user_tb_id` INT NOT NULL,
  `review_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_helpful_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  INDEX `fk_helpful_tb_review_tb1_idx` (`review_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_helpful_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
   ,
  CONSTRAINT `fk_helpful_tb_review_tb1`
    FOREIGN KEY (`review_tb_id`)
    REFERENCES `mydb`.`review_tb` (`id`)
    )






문의 
- id
- 유저id(FK,
- 상품id(FK,
- 문의유형(상품, 배송, 반품, 취소 및 환불, 교환, 기타)
- 문의내용(한글 3000자)
- 비밀글 설정여부
- 프로필 공개여부
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`inquiry_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(80) NOT NULL,
  `content` VARCHAR(8000) NOT NULL,
  `content_private` TINYINT NOT NULL DEFAULT 0,
  `profile_private` TINYINT NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`))








댓글(답변)
- id
- 유저id(FK,N:1)
- 스토어id(FK, N:1)
- 내용
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`comment_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(8000) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `inquiry_tb_id` INT NOT NULL,
  `user_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_tb_inquiry_tb1_idx` (`inquiry_tb_id` ASC) VISIBLE,
  INDEX `fk_comment_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_tb_inquiry_tb1`
    FOREIGN KEY (`inquiry_tb_id`)
    REFERENCES `mydb`.`inquiry_tb` (`id`)
    ,
  CONSTRAINT `fk_comment_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
    )







배송지 정보
- id
- 이름
- 연락처
- 배송지주소
- 배송시 요청사항
- 유저id(FK, N:1)
- 주문id(FK, 1:1)
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`address_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(20) NOT NULL,
  `address` VARCHAR(80) NOT NULL,
  `address_detail` VARCHAR(80) NOT NULL,
  `shipping_request` VARCHAR(45) NULL,
  `default_address` TINYINT NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NULL,
  `updated_at` TIMESTAMP NULL,
  `order_tb_id` INT NOT NULL,
  `user_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_tb_order_tb1_idx` (`order_tb_id` ASC) VISIBLE,
  INDEX `fk_address_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_tb_order_tb1`
    FOREIGN KEY (`order_tb_id`)
    REFERENCES `mydb`.`order_tb` (`id`)
    ,
  CONSTRAINT `fk_address_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
    )







공지사항
- id
- 유저id(FK, N:1)
- 제목
- 내용
- 생성 시각
- 수정 시각

CREATE TABLE IF NOT EXISTS `mydb`.`announcement_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(80) NOT NULL,
  `content` VARCHAR(8000) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  `user_tb_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_announcement_tb_user_tb1_idx` (`user_tb_id` ASC) VISIBLE,
  CONSTRAINT `fk_announcement_tb_user_tb1`
    FOREIGN KEY (`user_tb_id`)
    REFERENCES `mydb`.`user_tb` (`id`)
    )

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

##과제1: 전체 API주소 설계
```
전체 상품 목록 조회
GET /products

개별 상품 상세 조회
GET /products/{productId}

이메일 중복 체크
기존: POST /check 
수정: Get /emails/check/{email}

회원가입
기존: POST /join
수정: POST /users

로그인
POST /login

장바구니 담기
기존: POST /carts/add
수정: POST /carts

장바구니 조회
GET /carts

장바구니 수정
기존: POST /carts/update
수정: PUT /carts

주문하기(결제하기)
기존: POST /orders/save
수정: POST /orders

주문 확인
GET /orders/{orderId}
```

##과제2:  Mock API Controller 구현
```
코드로 제출
```






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
