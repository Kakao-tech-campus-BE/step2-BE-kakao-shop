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


## 1. 요구사항 시나리오에서 부족해 보이는 기능
```
(기능2) 로그인

- 부족해 보이는 기능 : 아이디 기억하기 및 정보 기억하기

<기능 상세 설명>
 아이디 기억하기 또는 로그인 유지하기 박스를  체크하면 cookie를 생성하고 같은 사용자가 다시 접속할 경우 쿠키를 확인해 자동 로그인해준다.

<인터페이스 요구사항>
입력 : 아이디 기억하기(또는 정보 기억하기) 버튼 클릭 
```
```
(기능10) 장바구니 상품 옵션 확인 및 수량 결정

- 부족해 보이는 기능 : 상품 삭제 기능

<기능 상세 설명>
 상품 삭제 버튼을 누르면 장바구니에서 해당 상품을 삭제하고 데이터베이스를 update를 한다.
 
<인터페이스 요구사항>
입력 : 삭제 버튼으로 상품 삭제
출력 : 삭제하겠습니까? 팝업 문구와 옵션 삭제 및 합계 금액 변경
```
</br>

## 2. 화면설계와 기존 서버의 API주소의 매칭
```
(기능1 - 회원가입)  POST - localhost:8080/join
(기능2 - 로그인)    POST - localhost:8080/login
(기능3 - 로그아웃) POST - localhost:8080/logout
(기능4 - 전체 상품 목록 조회) GET - localhost:8080/products
(기능5 - 개별 상품 상세 조회) GET - localhost:8080/products/{product_id}
(기능6 - 상품 옵션 선택) optionId를 통해
(기능7 - 옵션 확인 및 수량 결정) optionId와 quantity를 통해
(기능8 - 장바구니 담기) POST - localhost:8080/carts/add
(기능9 - 장바구니 보기) GET - localhost:8080/carts
(기능10 - 장바구니 상품 옵션 확인 및 수량 결정) cartID와 quantity를 통해
(기능11 - 주문) POST - localhost:8080/carts/update
(기능12 - 결제) POST - localhost:8080/orders/save
(기능13 - 주문 결과 확인) GET - localhost:8080/orders/{order_id}
```

</br>

## 3. API요청 후 응답데이터 확인 및 부족해보이는 기능 체크

```
- 주문하기  localhost:8080/carts/update 의 response body에 배송관련 금액이 포함되어 있지 않다.

- 장바구니에 동일한 상품을 담는 경우 갯수가 추가되지 않고 새로운 상품이 추가되는 형식으로 나타난다.
 
- 주문 결과 확인 api를 보냈을 때 주문 완료 시간이 포함되면 편리할 거 같다.
```

</br>

## 4. ER-Diagram

<img src = "https://drive.google.com/uc?export=view&id=1aBVtKiymuyLBzqxhKidTyUGll2YCMbsx" />


<details>
<summary>tables</summary>
<div markdown="1">

```
Table User {
  id integer [primary key]
  username varchar 
  email varchar
  password varchar
  date TIMESTAMP
}
```

```
Table Product {
  id integer [primary key]
  productname varchar
  image file
  price int
  date TIMESTAMP
}
```

```
Table Option {
  id integer [primary key]
  product_id integer
  optionname varchar
  price TIMESTAMP
  date varchar
}
```

```
Table Cart {
  id integer [primary key]
  user_id integer 
  option_id integer
  option_num integer
  
}
```

```
Table Order {
  id integer [primary key]
  user_id integer
  product_id integer
  total_price integer
}
```

```
Ref: User.id - Cart.user_id
Ref: Option.id - Cart.option_id
Ref: Order.user_id > User.id
Ref: Order.product_id < Product.id
Ref: Option.product_id - Product.id
```


</div>
</details>

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

</br>
</br>

## 1. API 주소 설계

|  | URL | 기능 | Method | Request Body |
| --- | --- | --- | --- | --- |
| Cart |  |  |  |  |
|  | /carts | 장바구니 조회 | GET |  |
|  | /carts/add | 장바구니 추가 | POST | [{"optionId":1,"quantity":5},{"optionId":2,"quantity":5}] |
|  | /carts/update | 장바구니 업데이트 | POST | [ {"cartId":4,"quantity":10 },{"cartId":5"quantity":10}] |
|  | /carts/{id} | 장바구니 삭제 | DELETE | [{"cartId":4}] |
|  | /carts/change | 장바구니 아이템 수량 변경 | PATCH | [{"cartId":4,"add":true,"subtract":false}] |
| Order |  |  |  |  |
|  | /orders/save | 결제하기 | POST |  |
|  | /orders/{id} | 주문 결과 확인하기 | GET |  |
| Product |  |  |  |  |
|  | /products | 전체 상품 목록 조회 | GET |  |
|  | /products/{id} | 개별 상품 상세 조회 | GET |  |
| User |  |  |  |  |
|  | /join | 회원가입 | POST | {"username":"meta","email":"meta@nate.com","password":"meta1234!"} |
|  | /check | 이메일 중복 체크 | POST | {"email":"meta@nate.com"} |
|  | /login | 로그인 | POST | {"email":"meta@nate.com","password":"meta1234!"} |

</br>
|  | URL | 기능 | Method | Request Body |
| --- | --- | --- | --- | --- |
| Cart |  |  |  |  |
|  | /carts | 장바구니 조회 | GET |  |
|  | /carts/add | 장바구니 추가 | POST | [
{
"optionId":1,
"quantity":5
},
{
"optionId":2,
"quantity":5
}
] |
|  | /carts/update | 장바구니 업데이트 | POST | [
{
"cartId":4,
"quantity":10
},
{
"cartId":5,
"quantity":10
}
] |
|  | /carts/{id} | 장바구니 삭제 | DELETE | [
{
"cartId":4
}
] |
|  | /carts/change | 장바구니 아이템 수량 변경 | PATCH | [
{
"cartId":4,
"add":true,
"subtract":false
}
] |
| Order |  |  |  |  |
|  | /orders/save | 결제하기 | POST |  |
|  | /orders/{id} | 주문 결과 확인하기 | GET |  |
| Product |  |  |  |  |
|  | /products | 전체 상품 목록 조회 | GET |  |
|  | /products/{id} | 개별 상품 상세 조회 | GET |  |
| User |  |  |  |  |
|  | /join | 회원가입 | POST | {
"username":"meta",
"mailto:email%22:%22meta@nate.com",
"password":"meta1234!"
} |
|  | /check | 이메일 중복 체크 | POST | {
"mailto:email%22:%22meta@nate.com"
} |
|  | /login | 로그인 | POST | {
"mailto:email%22:%22ssar@nate.com",
"password":"meta1234!"
} |
</br>

## 2. 가짜 데이터 설계 후 응답하는 스프링부트 컨트롤러 작성 
- 2week에 첨부.
</br>
</br>




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
