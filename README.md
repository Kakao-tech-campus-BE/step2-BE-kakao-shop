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


<br>

# 1주차 과제 수행

## **요구사항**

<p align="center"><img src="./images/Untitled.png" height="300px" width="300px"></p>

| 회원 | 상품 | 장바구니 | 주문/결제 |
| --- | --- | --- | --- |
| 회원가입 | 전체 상품 목록 조회 | 장바구니 담기 | 주문 |
| 로그인 | 개별 상품 상세 조회 | 장바구니 보기 | 결제 |
| 로그아웃 | 상품 옵션 선택 | 장바구니 상품 옵션 확인 및 수량 결정 | 주문 결과 확인 |
|  | 옵션 확인 및 수량 결정 |  |  |

<br>

## 1. 부족한/추가될 수 있는 요구사항


- 장바구니

    - 옵션 삭제
    - 상품 삭제
    - 이미 선택된 옵션을 다시 선택할 경우 옵션의 수량 증가
    
    <br>

- 상품

    - 할인 정보 제공
    - 배송 정보 제공
    - 상품 등록, 수정, 삭제
    - 상품 검색, 정렬
    
    <br>

- 주문/결제

    - 배송지, 연락처 등의 개인정보 입력
    - 결제 시 필수 정보 기입과 약관 동의 확인
    - 주문 취소

    <br>

- 고객 개인 페이지 제공

    - 개인 주문 내역 조회
    - 월별 지출 금액 조회
    - 배송지 관리


<br><br>

## 2. 서버의 API 주소와 화면 매칭

### 1. **전체 상품 목록 조회**

<p align="center"><img src="./images/Untitled%201.png" height="300px" width="300px"></p>


- Local URL : [http://localhost:8080/products](http://localhost:8080/products)
- 페이지로 전체 상품 목록 조회
    - Local URL : [http://localhost:8080/products?page=1](http://localhost:8080/products?page=1)(페이지 번호)

### 2. **개별 상품 상세 조회**


<p align="center"><img src="./images/Untitled%202.png" height="200px" width="300px"></p>

- Local URL : [http://localhost:8080/products/1](http://localhost:8080/products/1)(제품 ID)

### 3. **회원가입**


<p align="center"><img src="./images/Untitled%203.png" height="200px" width="300px"></p>

- Local URL : [http://localhost:8080/join](http://localhost:8080/join)

### 4. 로그인

<p align="center"><img src="./images/Untitled%204.png" height="200px" width="300px"></p>

- Local URL : [http://localhost:8080/login](http://localhost:8080/login)

### 5. 장바구니 담기

<p align="center"><img src="./images/Untitled%205.png" height="400px" width="250px"></p>

- Local URL : [http://localhost:8080/carts/add](http://localhost:8080/carts/add)

### 6. 장바구니 조회

<p align="center"><img src="./images/Untitled%206.png" height="100px" width="400px"></p>

<p align="center"><img src="./images/Untitled%207.png" height="200px" width="400px"></p>

- Local URL : [http://localhost:8080/carts](http://localhost:8080/carts)

### 7. 장바구니 수정

<p align="center"><img src="./images/Untitled%208.png" height="200px" width="200px"></p>


- Local URL : [http://localhost:8080/carts/update](http://localhost:8080/carts/update)

### 8. 결제하기

<p align="center"><img src="./images/Untitled%209.png" height="350px" width="400px"></p>

- Local URL : [http://localhost:8080/orders/save](http://localhost:8080/orders/save)

### 9. 주문 결과 확인

<p align="center"><img src="./images/Untitled%2010.png" height="200px" width="300px"></p>

- Local URL : [http://localhost:8080/orders/1](http://localhost:8080/orders/1)(주문번호)

<br><br>

## 3. Response Data 확인, 부족한 데이터 추가

| 기능 | URL Path | Method | Request | Response |
| --- | --- | --- | --- | --- |
| 전체상품목록조회 | /products | GET |  | id<br>productName<br>description<br>image<br>price |
| 개별상품상세조회 | /products/# | GET |  | id<br>productName<br>description<br>image<br>price<br>starCount<br>options<br>　id<br>　optionName<br>　price |
| 회원가입 | /join | POST | username<br>email<br>password |  |
| 로그인 | /login | POST | email<br>password |  |
| 장바구니 담기 | /carts/add | POST | optionId<br>quantity |  |
| 장바구니 조회 | /carts | GET |  | products<br>　id<br>　productName<br>　carts<br>　　id<br>　　option<br>　　　id<br>　　　optionName<br>　　　price<br>　　quantity<br>　　price<br>totalPrice |
| 장바구니 수정 | /carts/update | POST | cartId<br>quantity | carts<br>　cartId<br>　optionId<br>　optionName<br>　quantity<br>　price<br>totalPrice |
| 결제하기 | /orders/save | POST |  | id<br>products<br>　productName<br>　items<br>　　id<br>　　optionName<br>　　quantity<br>　　price<br>totalPrice |
| 주문결과확인 | /orders/# | GET |  | id<br>products<br>　productName<br>　items<br>　　id<br>　　optionName<br>　　quantity<br>　　price<br>totalPrice |
<br>

### 응답되는 데이터를 화면과 대조하여 부족한 데이터 탐색

- 전체 상품 목록 조회, 개별 상품 상세 조회에 배송정보가 있으나 요구사항에 없으므로 따로 추가할 필요 없어 보인다.
    
<p align="center"><img src="./images/Untitled%2011.png" height="50px" width="100px"></p>
<p align="center"><img src="./images/Untitled%2012.png" height="95px" width="300px"></p>

- 개별 상품 상세 조회에 description 내용은 없으므로 description 필드는 제외해도 될 것으로 보인다.
- 결제하기에서 상품명 없이 옵션명만 있으므로 productName 제외해도 될 것으로 보인다.
    
    <p align="center"><img src="./images/Untitled%2013.png" height="300px" width="300px"></p>
    
- 주문 결과 확인에서 옵션 하나만이 반영되므로 첫번째 옵션 외의 다른 옵션을 제외해도 될 것으로 보인다.
    
    <p align="center"><img src="./images/Untitled%2014.png" height="200px" width="300px"></p>    
        
<br><br>

## 4. 테이블 설계, ERD

- User
    
    
    | Field name | Data Type | Constraint |
    | --- | --- | --- |
    | ID | INT | PK |
    | username | VARCHAR(30) | NOT NULL |
    | email | VARCHAR(50) | NOT NULL, UNIQUE |
    | password | VARCHAR(20) | NOT NULL |
    | created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
- Product
    
    
    | Field name | Data Type | Constraint |
    | --- | --- | --- |
    | ID | INT | PK |
    | product_name | VARCHAR(100) | NOT NULL |
    | image | BLOB |  |
    | price | INT | NOT NULL |
    | created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
- Option
    
    
    | Field name | Data Type | Constraint |
    | --- | --- | --- |
    | ID | INT | PK |
    | product_id | INT | FK |
    | option_name | VARCHAR(50) | NOT NULL |
    | price | INT | NOT NULL |
    | created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
- Cart
    
    
    | Field name | Data Type | Constraint |
    | --- | --- | --- |
    | ID | INT | PK |
    | option_id | INT | FK |
    | user_id | INT | FK |
    | quantity | INT | NOT NULL, CHECK (quantity > 0) |
- Order
    
    
    | Field name | Data Type | Constraint |
    | --- | --- | --- |
    | ID | INT | PK |
    | user_id | INT | FK |
- Order Item
    
    
    | Field name | Data Type | Constraint |
    | --- | --- | --- |
    | ID | INT | PK |
    | option_id | INT | FK |
    | order_id | INT | FK |
    | quantity | INT | NOT NULL, CHECK (quantity > 0) |

![Untitled](./images/Untitled%2015.png)

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
>- User 도메인을 제외한 전체 API 주소 설계가 RestAPI 맞게 설계되었는가?  POST와 GET으로만 구현되어 있어도 됨.	
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
>- DTO에 타입은 올바르게 지정되었는가?
>- DTO에 이름은 일관성이 있는가? (예를 들어 어떤 것은 JoinDTO, 어떤 것은 joinDto, 어떤 것은 DtoJoin 이런식으로 되어 있으면 일관성이 없는것이다)
>- DTO를 공유해서 쓰면 안된다 (동일한 데이터가 응답된다 하더라도, 화면은 수시로 변경될 수 있기 때문에 DTO를 공유하고 있으면 배점을 받지 못함)
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_2주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

## 1. 전체 API 주소 설계

---

- 상품
    - 전체 상품 목록 조회
        
        ```java
        GET /products
        GET /products?page={number} //page의 default 값은 0
        ```
        
    - 개별 상품 상세 조회
        
        ```java
        GET /products/{id}
        ```
        
- 회원
    - 회원가입
        
        ```java
        POST /join
        Content-Type: application/json; charset=UTF-8
        {
        	"email" : "이메일",
          "password" : "비밀번호",
          "username" : "이름"
        }
        ```
        
    - 로그인
        
        ```java
        POST /login
        Content-Type: application/json; charset=UTF-8
        {
          "email" : "이메일",
          "password" : "비밀번호"
        }
        ```
        
- 장바구니
    - 장바구니 담기
        
        ```java
        POST /carts/add
        Content-Type: application/json; charset=UTF-8
        Authorization: Bearer <credentials>
        [
        	{
        	  "optionId" : 옵션 아이디,
        	  "quantity" : 수량
        	}, 
        	{
        	  "optionId" : 옵션 아이디,
        	  "quantity" : 수량
        	} 
        	...
        ]
        ```
        
    - 장바구니 조회
        
        ```java
        GET /carts
        Authorization: Bearer <credentials>
        ```
        
    - 장바구니 수정
        
        ```java
        POST /carts/update
        Content-Type: application/json;charset=UTF-8
        Authorization: Bearer <credentials>
        [
        	{
        	  "optionId" : 옵션 아이디,
        	  "quantity" : 수량
        	}, 
        	{
        	  "optionId" : 옵션 아이디,
        	  "quantity" : 수량
        	} 
        	...
        ]
        ```
        
- 주문/결제
    - 결제하기(주문 인서트)
        
        ```java
        POST /orders/save
        Authorization: Bearer <credentials>
        ```
        
    - 주문결과확인
        
        ```java
        GET /orders/{orderId}
        Authorization: Bearer <credentials>
        ```
        

## 2. Mock API Controller 구현

- 2week 폴더


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