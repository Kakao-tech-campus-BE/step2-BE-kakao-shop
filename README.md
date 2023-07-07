# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

<details>
<summary>1주차 과제 설명</summary>

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

</details>

<details>
<summary>1주차 과제 수행</summary>

## 1. 부족해 보이는 기능 체크

### 부족해보이는 기능
1. 회원가입 시 이메일 인증 기능
2. 로그인 시 비밀번호 찾기 기능
3. 상품 판매자 기능
   - 상품 등록
   - 상품 수정
   - 상품 삭제
4. 구매자가 상품을 장바구니에 담지 않고, 바로 구매하기 기능
   - 현재는 장바구니에 담아야만 물품 구매가 가능하다
5. 장바구니 아이템 삭제 기능
6. 장바구니의 특정 아이템만 구매 기능
7. 이벤트 진행으로 인한 가격 세일 (원가, 세일가에 대한 표시)

## 2. 화면설계와 API주소 매칭

![](img/week1/1-signup.png)  
- 회원가입 버튼: `POST /signup`
- 이메일 중복 확인 버튼: `POST /check`
<br>
<br>

![](img/week1/1-login.png)  
- 로그인 버튼: `POST /login`
- 회원가입 버튼: `GET /signup`
<br>
<br>

![](img/week1/1-nav.png)
- 쇼핑하기 버튼: `GET /products`
- 장바구니 버튼: `GET /carts`
- 로그아웃 버튼: `POST /logout`
<br>
<br>

![](img/week1/1-main.png)  
- 메인 페이지: `GET /products`
- 상품의 이미지나 설명 클릭: `GET /products/{productId}`
<br>
<br>

![](img/week1/1-detail.png)  
- 상세 페이지 표시: `GET /products/{productId}`
- 장바구니 담기: `POST /carts/add`
<br>
<br>

![](img/week1/1-cart.png)  
- 장바구니 보기: `GET /carts`
- 주문하기 버튼: `POST /carts/update`
<br>
<br>

![](img/week1/1-order.png)  
- 주문상품 정보 보기: `GET /items`
- 결제하기 버튼 클릭: `POST /orders/save`
<br>
<br>

![](img/week1/1-order-complete.png)  
- 주문 결과 확인: `GET /orders/{orderId}`
- 쇼핑 계속하기: `GET /products`
<br>
<br>


## 3. API를 POSTMAN으로 요청해본 뒤 부족한 데이터가 무엇인지 체크

- 이미 장바구니에 존재하는 물품을 또 다시 한번 장바구니에 담으려고 하면, 오류 발생

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "장바구니 담기 중에 오류가 발생했습니다 : could not execute statement; SQL [n/a]; constraint [\"PUBLIC.UK_CART_OPTION_USER_INDEX_4 ON PUBLIC.CART_TB(USER_ID NULLS FIRST, OPTION_ID NULLS FIRST) VALUES ( /* key:1 */ 1, 1)\"; SQL statement:\ninsert into cart_tb (id, option_id, price, quantity, user_id) values (default, ?, ?, ?, ?) [23505-214]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
        "status": 500
    }
}
```

## 4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.

![](img/week1/1-ERD.png)


## 5. PDF 과제 (테이블 설계)

### User Table

| Column Name | Data Type    | Nullable | Key | Comment     |
|-------------|--------------|----------|-----|-------------|
| id          | bigint       | NOT NULL | PK  | 고유 ID     |
| username    | varchar(20)  | NOT NULL |     | 사용자 이름 |
| email       | varchar(100) | NOT NULL |     | 이메일      |
| password    | varchar(256) | NOT NULL |     | 패스워드    |
| join_date   | datetime     | NOT NULL |     | 가입일      |
| role        | varchar(20)  | NOT NULL |     | 권한        |

### Product Table

| Column Name | Data Type    | Nullable | Key | Comment       |
|------------|--------------|----------|-----|---------------|
| id         | bigint       | NOT NULL | PK  | 고유 ID       |
| name       | varchar(100) | NOT NULL |     | 제품명        |
| description| varchar(1000) | NOT NULL |    | 제품 설명     | 
| image      | varchar(200) | NOT NULL |     | 제품 사진     |
| price      | int          | NOT NULL |     | 제품 가격     |
| create_date | datetime     | NOT NULL |     | 제품 생성날짜 |
| update_date | datetime     | NOT NULL |     | 제품 수정날짜 |

### Option Table

| Column Name | Data Type    | Nullable | Key | Comment       |
|-------------|--------------|----------|-----|---------------|
| id          | bigint       | NOT NULL | PK  | 고유 ID       |
| product_id  | bigint       | NOT NULL | FK  | 제품 ID       |
| name        | varchar(100) | NOT NULL |     | 옵션명        |
| price       | int          | NOT NULL |     | 옵션 가격     |
| create_date | datetime     | NOT NULL |     | 옵션 생성날짜 |
| update_date | datetime     | NOT NULL |     | 옵션 수정날짜 |

### Cart Table

| Column Name | Data Type | Nullable | Key | Comment   |
|-------------|-----------|----------|-----|-----------|
| id          | bigint    | NOT NULL | PK  | 고유 ID   |
| user_id     | bigint    | NOT NULL | FK  | 유저 ID   |
| option_id   | bigint    | NOT NULL | FK  | 옵션 ID   |
| quantity    | int       | NOT NULL |     | 옵션 수량 |

### Order Table

| Column Name | Data Type | Nullable | Key | Comment    |
|-------------|-----------|----------|-----|------------|
| id          | bigint    | NOT NULL | PK  | 고유 ID    |
| user_id     | bigint    | NOT NULL | FK  | 유저 ID    |
| order_date  | datetime  | NOT NULL |     | 주문 날짜  |

### Item Table (order_item)

| Column Name | Data Type | Nullable | Key | Comment  |
|-------------|-----------|----------|-----|----------|
| id          | bigint    | NOT NULL | PK  | 고유 ID    |
| order_id    | bigint    | NOT NULL | FK  | 주문 ID    |
| option_id   | bigint    | NOT NULL | FK  | 옵션 ID    |
 | price      | int       | NOT NULL |      | 주문 옵션 가격 |
| quantity    | int       | NOT NULL |     | 주문 옵션 수량 |


</details>

<br>

<details>
<summary>2주차 과제 설명</summary>

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

</details>

<details>
<summary>2주차 과제 수행</summary>

## 1. API 설계

![img.png](week2/img/img.png)
- 만약 `PATCH` 요청을 사용 가능하다면, `POST /carts/update` 대신,  `PATCH /carts` 를 사용했을 것입니다.
  - 왜냐하면, 장바구니를 수정하는 경우는, 보통 장바구니의 수량을 변경하는 작업이기 때문입니다.
  - 장바구니 아이템의 삭제에 관한 API가 존재한다면, `DELETE /carts/{optionId}` 로 수행했을 것입니다.
- 기존의 주어진 API 설계에서, POST, GET으로만의 구현으로 최대한 동사의 사용을 제외하기를 원해서 API를 조금씩 수정하였습니다.

## 2. 가짜 데이터를 설계하여 MOCK API 구현
- 강사님께서 주신 예제 소스코드를 제외하고, 제가 직접 작성한 코드는 다음과 같습니다.
  - src/main/java/com/example/kakaoshop/cart/
  - src/main/java/com/example/kakaoshop/order/
  - src/test/java/com/example/kakaoshop/cart/CartControllerTest.java
- 최대한 강사님께서 주신 예제 소스코드(구조)를 참고하여 작성하였습니다.

</details>

</br>

<details>
<summary>3주차 과제 설명</summary>

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

</details>

<details>
<summary>3주차 과제 수행</summary>

</details>

</br>

<details>
<summary>4주차 과제 설명</summary>

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

</details>

<details>
<summary>4주차 과제 수행</summary>

</details>

</br>

<details>
<summary>5주차 과제 설명</summary>

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

</details>

<details>
<summary>5주차 과제 수행</summary>

</details>

</br>

<details>
<summary>6주차 과제 설명</summary>

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

</details>

<details>
<summary>6주차 과제 수행</summary>

</details>
