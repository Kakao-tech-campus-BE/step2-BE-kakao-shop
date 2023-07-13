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

---

## **과제 1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.**

</br>

- 회원가입단계인 join단계와 login단계에서 에러(이메일 또는 주소를 잘못입력 했을 경우) 나오는 에러메시지가 동일한데, 보안의 관점에서 이 둘의 메시지가 분리되어야 한다고 생각. -> login단계에서 비밀번호를 잘못 입력했을시 모든 error메시지를 비밀번호를 잘못 입력하셨습니다 라고 통합하는것은 어떨까?
- ID/PW를 찾는 기능이 존재하면 구현에 비해서 기능상 좋을것 같다.

- product가 보여지는 순서가 id순인데 product테이블의 다른 기준점을 바탕으로 정렬하는 기능을 추가하면 좋을것 같다.(낮은 가격순, 높은 별점순 등)
- 장바구니에 담긴 item들을 삭제하는 기능이 필요하다.

---

</br>

## **과제 2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)**

</br>

- ### 기능단위 User

  - 회원가입
    http://localhost:8080/join

    - userAPI와 연동되어 회원정보를 받아 데이터베이스에 저장,
    - 회원정보: 이메일(아이디), 이름, 비밀번호
    - 이메일 중복 검사 API존재 (/check) -> 프론트에서 버튼으로 구현
    - 클라이언트로부터 에러가 나올 경우(잘못된 값 입력) 각 에러에 대한 데이터를 제공

  - 로그인
    http://localhost:8080/login

    - Request로 이메일(아이디)와 비밀번호를 입력 받아, 데이터베이스에 저장
    - UserAPI와 연동되어 해당 사용자가 유효한 사용자 인지 판단 하고 유효할경우 JWT 토큰 발행
    - 로그인 실패시 (특히 PW) join단계와 같은 에러를 출력 -> 보안관점에서 수정 필요

  - 로그아웃(프론트에서 버튼으로 구현)

- ### 기능단위 Product

  - 전체상품 목록조회
    http://localhost:8080/products
    - productAPI와 연동되어 데이터베이스로부터 주문 가능한 각 상품이 productid를 기반으로 하여 정렬되어 보여짐
    - 다른 정렬기준으로 정렬할 수 있는 버튼이나 토글을 만들면 어떨까?
    - 파라미터 사용하여 페이징 구현
  - 개별상품 상세조회
    http://localhost:8080/products/{해당상품id}
    - 전체 프로덕트 페이지에서 개별 프로덕트를 클릭하게 되면 클릭한 상품의 productid를 파라미터로 한 상품 페이지로 이동
    - 해당 상품에 대한 옵션들이 실제 상품역할을 수행
    - 선택된 옵션은 다시 선택 불가능하다는 명세 존재함

- ### 기능단위 Option

- ### 기능단위 Cart

  - 장바구니 보기
    http://localhost:8080/carts
    - CartAPI을 호출해 cart에 저장된 옵션과 수량을 출력
  - 장바구니 담기
    http://localhost:8080/carts/add

    - 개별 프로덕트 상세조회 단계에서 장바구니 담기 버튼을 누르면 CartAPI가 호출되어 옵션과 수량이 장바구니에 저장됨

  - 장바구니 상품 옵션 확인 및 수량 결정
    http://localhost:8080/carts/update
    - 옵션 수정 버튼을 통해서 옵션 수를 조정할 수 있음
    - 주문하는 orderAPI와도 연동된 버튼 존재 -> 주문하기 버튼에서 카트 업데이트 진행됨

- ### 기능단위 Order

  - 주문 및 결제
    http://localhost:8080/order/save

    - 주문하기 버튼을 누르면 결제 절차 없이 주문 진행, 또한 결제로 처리되어서 두번째 send부터 빈 장바구니가 됨.(결제가 이루어 짐)

  - 주문 결과 확인
    http://localhost:8080/order/{주문id}
    - 주문된 옵션에 대한 정보가 orderAPI를 통해 호출됨

---

</br>

## **과제 3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.**

- 회원가입을 하는 join단계의 http://localhost:8080/join 에서 올바른 값(제약조건을 만족했을때)을 requestbody로 전송 했을때의 응답은 다음과 같다.

```json
{
  "success": true,
  "response": null,
  "error": null
}
```

이경우에는 이메일의 중복검사는 이루어지지만 username의 중복은 허용되는지 처리를 안한것인지 모르겠지만 username을 같게하고 가입했을때 정상적인 반환값을 가졌다. -> username중복 검사도 필요할것 같다.

---

- 전체 상품을 보여주는 http://localhost:8080/products의 응답은 다음과 같다.

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
       ...(생략)
        ],
    "error": null
}
```

response를 보면 요구명세에 나와있던 "주문이 가능한 전체 상품 목록"이라는 요구를 충족시켜줄수 있는 데이터에 대한 응답이 부재한것을 알 수 있다. -> 따라서 상품 상태에 관한 데이터가 추가되어야 할 것 같다.

---

http://localhost:8080/cart/add 페이지에서 동일한 품목(optionId가 동일한 경우)를 중복해서 요청하게 되면 에러 발생

```
"message":  "장바구니 담기 중에 오류가 발생했습니다 : could not execute statement; SQL [n/a]; constraint [\"PUBLIC.UK_CART_OPTION_USER_INDEX_4 ON PUBLIC.CART_TB(USER_ID NULLS FIRST, OPTION_ID NULLS FIRST) VALUES ( /* key:3 */ 1, 1)\"; SQL statement:\ninsert into cart_tb (id, option_id, price, quantity, user_id) values (default, ?, ?, ?, ?) [23505-214]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"
```

- 데이터베이스 설계시 CART테이블에서 USER_ID와OPTION_ID의 조합이 UNIQUE해야한다는 제약조건이 존재하기 때문에 동일한 옵션아이디 삽입시 갱신되지 않고 트랩에 걸리는것으로 추정,,
  → 따라서 cart/add시 optionId를 갱신을 해주는 로직 추가하는것이 바람직해 보임.. (아마 현재는 이미 담겨있는 장바구니의 option이나 수량을 변경하기 위해서 장바구니를 들어가야만 함)
  - 로직을 추가하는것이 어렵다면 오류 발생했다는 말이 애매하므로 “이미 장바구니에 담긴 상품입니다”라는 에러 팝업을 출력하는것도 바람직해보임.

---

http://localhost:8080/cart/add 페이지에서 리퀘스트 바디를 다음과 같이 수량을 음수값을 넣어줬을때 정상적으로 코드가 동작한다.

```json
[
  {
    "optionId": 1,
    "quantity": -5
  }
]
```

일때 response body는

```json
{
  "success": true,
  "response": null,
  "error": null
}
```

이므로 요구명세에 수량에 관한 항목이 무한으로 생각하라고 했어도 -로 들어가게 될때에는 반드시 처리해야 하기 때문에 에러 처리를 하던지
양수만 들어갈 수 있게 UNSIGNED처리 해줘야 할것 같다. (도현님 의견 반영)

-> 안해주게 되면? 가격과 수량을 곱해 음수값이 카트에 그대로 전달되서 **실제 결제 할 금액이 감소**하는 엄청난버그..!!

---

http://localhost:8080/orders/save에서 response데이터는 다음과같다

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
                        "quantity": 2,
                        "price": 20000
                    },
                    ...(생략)
               ]
            }
        ],
        "totalPrice": 109500
    },
    "error": null
}
```

이때 배송과 관련된 정보가 누락되어서 추가하거나 따로 API를 생성하는 작업이 필요해 보인다.

주문 결과를 확인 할 때 http://localhost:8080/order/{주문id} 의 응답은 order/save 데이터와 동일한데, 여기서 주문 결과 확인시에는 save와는 다르게 **주문체결시간**을 response데이터에 추가해 주는 작업이 필요해 보인다.

---

## **과제 4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.**

- ### ER 다이어그램을 만들면서 고민했던 것들

  - option의 아이템들이 order과 cart에서 별도의 테이블을 두어 관리를 해야하는가에 대한 고민들을 하게되었다.

    -> 나는 각 테이블에 item 테이블을 하위 테이블로 두어서 각 항목에 관한 관리는 item에 책임을 주기로 하고 상위 테이블에서는 관리와 집계를 하는 형태로 테이블을 만들려 노력했다.

  - 카트와 유저와의 관계가 어떻게 설정하는지에 대한 고민을 많이 했다.

    -> 위와 같이 카트를 카트아이템과 분리함으로써 유저들은 하나의 카트를 가지고 각 카트에 담긴 옵션들은 카트 아이템으로 관리되는 형태를 구상해봤다.

  - CartItem와 OrderItem을 각각 Option을 참조하는 형식으로 관리함으로써 상품과 옵션에 대한 정보를 직접 저장하는 대신 참조를 사용함하기로 해, 데이터의 일관성을 보장할수 있을것이다.

  - 카트아이템을 카트와 합쳐버릴수도 있지만, 그렇지 않은 이유는?
    - 확장성 고려해서 어떤 상품이 가장 자주 카트에 추가되는지, 사용자들이 카트에 상품을 얼마나 오래 두는지, 어떤 상품이 카트에서 가장 자주 제거되는지 등의 통계를 쉽게 추적하기 용이할것 같아서..
    - 또한 지금은 카트가 유저와 1대1관계로 생각했는데, 확장가능성을 열어둘 수 있을거 같아서..

### 다음은 과제로 나온 **테이블 생성 코드**들이다..

```SQL
-- Database 생성
create database kakaoshop;
use kakaoshop;

-- 테이블 생성
CREATE TABLE User (
    userId INT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE Product (
    ProductId INT PRIMARY KEY,
    productName VARCHAR(255),
    description TEXT,
    image VARCHAR(255),
    price DECIMAL(10, 2),
    starCount INT,
    available BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Options (
    optionId INT PRIMARY KEY,
    optionName VARCHAR(255),
    price DECIMAL(10, 2),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES Product (ProductId)
);

CREATE TABLE Cart (
    CartId INT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES User (userId)
);

CREATE TABLE CartItem (
    CartItemId INT PRIMARY KEY,
    quantity INT UNSIGNED,
    price DECIMAL(10, 2),
    option_id INT,
    cart_id INT,
    FOREIGN KEY (option_id) REFERENCES Options (optionId),
    FOREIGN KEY (cart_id) REFERENCES Cart (CartId)
);

CREATE TABLE Orders (
    orderId INT PRIMARY KEY,
    user_id INT,
    totalPrice DECIMAL(10, 2),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User (userId)
);

CREATE TABLE OrderItem (
    orderItemId INT PRIMARY KEY,
    quantity INT UNSIGNED,
    price DECIMAL(10, 2),
    option_id INT,
    order_id INT,
    FOREIGN KEY (option_id) REFERENCES Options (optionId),
    FOREIGN KEY (order_id) REFERENCES Orders (orderId)
);

```

이고 ERD는 따로 파일 첨부하였다.

./kakaoshopERD-BE5조 김철호.png

참고로 ERD 테이블간 관계는 아래와 같다.

```
User와 Cart: 1:1
Cart와 CartItem: 1:N
User와 Order: 1:N
Order와 OrderItem: 1:N
CartItem / OrderItem와 Product: N:1
CartItem / OrderItem와 Option: N:1
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**

아래 항목은 반드시 포함하여 과제 수행해주세요!

> - 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지)
> - 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
> - 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
> - 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
> - 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_1주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

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

---

## **과제 1. 전체 API 주소 설계하기.**

</br>

- 전체 API 주소를 RESTful API로 재설계 해보는 작업을 해보았다.

- **과제 수행 내용 (필수)**
  - API 주소 설계
    - 이전에 Get Post만 이용하여 설계를 해보았으니 이번에 Restful API를 설계해보면 ..
      | 기능단위 | 기능 | URL | 특이사항 |
      | ----------------- | ---------------------- | ---------------------------------------- | --------------------------------------------------------------------- |
      | users | 회원가입 | POST /user | |
      | | 로그인 | POST /auth/tokens | |
      | | 로그아웃 | | 클라이언트 단에서 토큰 제거로 구현 |
      | product | 전체 상품 목록 조회 | GET /products | |
      | | 개별 상품 상세 조회 | GET /products/{productId} | |
      | option(상품 선택) | 상품 옵션 선택 | GET /products/{productId}/options | |
      | | 옵션 확인 및 수량 결제 | /products/{productId}/options/{optionId} | 옵션 추가의 경우 Post, 옵션 수정의 경우 Put, 옵션 삭제의 경우 delete |
      | cart | 장바구니 보기 | GET /carts | 장바구니 조회의 경우 get |
      | | 장바구니 담기 | POST /carts/{cartId}/items | 장바구니 아이템 추가의 경우 post, 수정과 삭제의 경우 각각 put, delete |
      | | | | |
      | order | 주문 | POST /users/{userId}/orders | 특정 사용자의 주문이 post로 생성 |
      | | | | |
      | | 주문 결과 확인 | GET /users/{userId}/orders/{orderId} | |

---

## **과제 2. Mock API Controller 구현.**

</br>

- 구현되지 않은(구현해야 하는) API주소는 다음과 같다.

  - Cart/Add, Cart/update, Orders/save, Orders/1..

  </br>

- Carts/Add와 Carts/update를 구현할 때에는 기존 코드를 수정하는 형식으로 컨트롤러를 구현해야 했다. 기존에 있던 /carts 엔트포인트에 /carts/add와 /carts/update를 엔드포인트로 추가하고 로직을 짰다.
  - CartItemAddDTO와 CartItemUpdateDTO, CartItemResponseDTO를 새로 만들어 요청과 응답 DTO를 만들어 보았다.

</br>

- Order/save와 Order/1을 구현 할 때에는 OrderRestController에 엔드포인트를 찾고 주문 생성로직을 관심사 분리 해보기로 하였다.

  - OrderService로 주문생성 및 조회를 위한 로직을 처리하는 클래스를 따로 빼서 동작하도록 코드를 만들었다.
  - OrderResponseDTO과 OrderItemDTO를 사용해 OrderService에서 새로운 주문 아이디 생성하고 임의의 상품정보 및 총가격을 설정한뒤 저장하고 주문 번호를 반환하도록 했다. 또한 주문 조회의 경우에는 주문 아이디에 해당하는 주문번호를 반환하도록 하였다.

 </br>
      전체 코드는 2week 폴더에 있습니다.

---

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**

아래 항목은 반드시 포함하여 과제 수행해주세요!

> - 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? (예를 들어 배포된 서버는 POST와 GET으로만 구현되었는데, 학생들은 PUT과 DELETE도 배울 예정이라 이부분이 반영되었고, 주소가 RestAPI에 맞게 설계되었는지)
> - 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_2주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

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

> - 레포지토리 단위테스트가 구현되었는가?
> - 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
> - Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
> - 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_3주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

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

> - 컨트롤러 단위테스트가 구현되었는가?
> - Mockito를 이용하여 stub을 구현하였는가?
> - 인증이 필요한 컨트롤러를 테스트할 수 있는가?
> - 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_4주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

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

> - 실패 단위 테스트가 구현되었는가?
> - 모든 예외에 대한 실패 테스트가 구현되었는가?
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_5주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

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

> - 통합테스트가 구현되었는가?
> - API문서가 구현되었는가?
> - 배포가 정상적으로 되었는가?
> - 서비스에 문제가 발생했을 때, 로그를 통해 문제를 확인할 수 있는가?
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_6주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
