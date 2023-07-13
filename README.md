# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

# 1주차

### 카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제
</br>

## **과제명**
```
1. 요구사항분석/API요청 및 응답 시나리오 분석
2. 요구사항 추가 반영 및 테이블 설계도
```

### 1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크
***
- 상품 등록
- 개별 구매하기(상품 상세설명창에서 바로 구매할 수 있는 기능)
- 상품 검색
- 카테고리 별로 상품 조회하기
- 장바구니 삭제
***

### 2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭
***
#### 기능1 회원가입

(Post) : http://localhost:8080/join

#### 기능2 로그인

(Post) : http://localhost:8080/login

#### 기능4 전체화면 조회

(Get) : http://localhost:8080/products

#### 기능5 개별상품상세조회

(Get) : http://localhost:8080/products/1

#### 기능8 장바구니 담기

(Post) : http://localhost:8080/carts/add

#### 기능9 장바구니 보기

(Get) : http://localhost:8080/carts

#### 기능11 주문

(Post) : http://localhost:8080/carts/update

#### 기능12 결제

(Post) : http://localhost:8080/orders/save

#### 기능13 주문결과 확인

(Get) : http://localhost:8080/orders/1
***

### 3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크
***

#### 이메일 중복체크
- response에 중복되지 않다는 메세지 추가

#### 주문하기, 주문결과 확인
- response의 id가 무엇을 의미하는지 헷갈릴 수 있음
- 전체 response를 order로 묶음
- items명을 orderItem 으로 변경
- orderItem 테이블 필요
***

### 4. 테이블 설계를 하여 ER-Diagram을 추가하여 제출하시오.
***
#### ER 다이어그램

![image](https://github.com/PHS00/step2-BE-kakao-shop/assets/88030920/0b539152-5397-4266-9e5f-e6765701be39)

#### Table 설계
~~~
CREATE TABLE `USER` (
`id`	int	NOT NULL,
`username`	String	NULL,
`email`	String	NULL,
`password`	String	NULL,
`role`	String	NULL,
`date`	DateTime	NULL
);

CREATE TABLE `PRODUCT` (
`id`	int	NOT NULL,
`product_name`	String	NULL,
`product_image`	String	NULL,
`product_price`	int	NULL,
`star_rate`	double	NULL,
`date`	DateTime	NULL
);

CREATE TABLE `OPTION` (
`id`	int	NOT NULL,
`product_id`	VARCHAR(255)	NOT NULL,
`option_name`	String	NULL,
`option_price`	int	NULL,
`option_stock`	int	NULL,
`date`	DateTime	NULL
);

CREATE TABLE `ORDER` (
`Key`	int	NOT NULL,
`user_id`	VARCHAR(255)	NOT NULL,
`Field`	DateTime	NULL
);

CREATE TABLE `CART` (
`Key`	int	NOT NULL,
`user_id`	VARCHAR(255)	NOT NULL,
`option_id`	VARCHAR(255)	NOT NULL,
`option_number`	int	NULL,
`date`	DateTime	NULL
);

CREATE TABLE `ORDERITEM` (
`Key`	int	NOT NULL,
`order_id`	VARCHAR(255)	NOT NULL,
`option_id`	VARCHAR(255)	NOT NULL,
`option_number`	int	NULL,
`date`	DateTime	NULL
);

ALTER TABLE `USER` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
`id`
);

ALTER TABLE `PRODUCT` ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (
`id`
);

ALTER TABLE `OPTION` ADD CONSTRAINT `PK_OPTION` PRIMARY KEY (
`id`
);

ALTER TABLE `ORDER` ADD CONSTRAINT `PK_ORDER` PRIMARY KEY (
`Key`
);

ALTER TABLE `CART` ADD CONSTRAINT `PK_CART` PRIMARY KEY (
`Key`
);

ALTER TABLE `ORDERITEM` ADD CONSTRAINT `PK_ORDERITEM` PRIMARY KEY (
`Key`
);
~~~


***

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

# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

### 1. 전체 API 주소 설계

---
#### 이메일 체크 : /check → /email
~~~
POST /email HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 30
Host: localhost:8080

{
"email" : "cos@nate.com"
}
~~~
#### 회원가입 하기 : /join → /user
~~~
POST /user HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 78
Host: localhost:8080

{
  "email" : "cosnate.com",
  "password" : "cos1234!",
  "username" : "cos"
}
~~~
#### 로그인하기 : 그대로
~~~
POST /login HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 59
Host: localhost:8080

{
  "email" : "ssar@nate.com",
  "password" : "meta1234!"
}
~~~
#### 장바구니 담기 : /carts/add → /carts
~~~
POST /carts HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Content-Length: 82
Host: localhost:8080

[ {
  "optionId" : 1,
  "quantity" : 5
}, {
  "optionId" : 2,
  "quantity" : 5
} ]
~~~
#### 장바구니 조회 : 그대로
~~~
GET /carts HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
~~~
#### 장바구니 수정 : POST /carts/update → PUT /carts (PUT은 수정할 때 사용)
~~~
PUT /carts HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTZ9.KcuBbtdhvlFng8_VZqGbczxWp1RDoWAqHdbil7ooYywP98RicRS3aK8viZObFJc7MU7KlKAo6FeLk32HKKnDcw
Content-Length: 78
Host: localhost:8080

[ {
  "cartId" : 1,
  "quantity" : 3
}, {
  "cartId" : 2,
  "quantity" : 5
} ]
~~~
#### 주문하기 : /orders/save → /orders
~~~~
POST /orders HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
~~~~
#### 주문 조회 : 그대로
~~~
GET /orders HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoyLCJleHAiOjE2ODcwNTIzNTd9.v-0C5EoV-QfGVC3Qdis1HLfKf4ZaYIBacWQ5ttkdtTOj6QqVJ4KoyQdvxBUz3NvjC-W0gs7EDFgwzMaaV1vuGg
Host: localhost:8080
~~~
---

### 2. Mock API Controller 구현
~~~
week2에 소스코드 작성했음
~~~

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
