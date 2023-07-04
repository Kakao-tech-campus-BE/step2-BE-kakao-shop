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

<br/>
<br/>
<br/>

# 1주차 과제

## 도메인 분석

### 회원가입 화면

가장 먼저 회원가입 화면이 나옵니다. 회원가입 화면에서는 가입에 필요한 유저 정보를 입력합니다. 유저 정보로는 이메일, 이름, 비밀번호가 있습니다. 이를 저장하기 위한 테이블 `user_tb` 가 필요합니다.

1. id : int
2. name : string
3. email : string
4. password : string

필드가 필요합니다. 그리고 해당 홈페이지는 쇼핑몰 입니다. 따라서, 판매자와 구매자 그리고 관리자의 구분이 필요해 보입니다. 가입 시에는 판매자, 구매자만 구분해야 하지 가입 과정에서 관리자 확인까지 진행하면 복잡해질 것 같습니다. 관리자 추가는 추후 직접 지정 형식으로 하고, 홈페이지 내에서의 가입은 구매자와 판매자만 구분하면 될 것 같습니다. 따라서 해당 페이지에서는, 판매자와 구매자를 구분해서 가입하는 기능이 부족해 보입니다. 그리고 이를 위해 유저 테이블에 필드를 하나 추가합니다.

5. roles : string

그리고 API를 살펴보면, 이메일 유효성 검증을 위한 `/check` 와 정보 저장을 위한 `/join` 가 필요합니다. 그리고 이미 가입된 회원인지를 검사하는 API가 필요해 보입니다. `/exists` 정도가 괜찮아 보입니다.

</br>

### 로그인 화면

로그인은 가입된 정보와 일치하는지 확인하는 과정입니다. 이메일, 비밀번호를 입력하여 확인합니다. 정보 검증을 위한 화면이므로 테이블을 생성할 필요는 없습니다. 이를 위한 API는 `/check` 와 `/login` 이 필요합니다.

그리고 해당 화면에는 로그인 기능만 존재합니다. 비밀번호 변경, 아이디 찾기 등 회원가입 정보를 잊어버린 경우에 되찾기 위한 방법이 부족합니다. 이를 위한 `find/password` 와 `/find/email` API가 필요해 보입니다.

</br>

### 로그아웃

로그아웃은 프론트에서 진행하므로 별다른 기능이 필요하지 않은 것 같습니다.

</br>

### 전체 상품 목록 조회

쇼핑몰의 전체 상품을 보여주는 페이지 입니다. 상품 사진, 이름, 가격 정보가 필요합니다. 따라서 상품 정보 저장을 위한 테이블 `product_tb` 가 필요합니다. 필드로는

1. id : int
2. name : string
3. description : string
4. price : int
5. image : string

이 필요합니다. 그리고 테이블 조회를 위한 `/products` API 가 필요합니다. 

전체 상품을 확인할 수는 있지만, 검색 및 필터링 기능이 없습니다. 필요한 상품 검색이나 분류를 위한 기능이 추가되면 좋을 것 같습니다. 분류를 위해 상품 테이블에 카테고리 정보를 저장하는 필드를 추가하면 좋을 것 같습니다. 그리고 검색은 프론트에서 하면 될 것이므로 백엔드에서 추가적인 작업은 필요해 보이지 않습니다.

그리고 해당 상품의 재고 확인이 불가능 합니다. 품절 상품에 대한 표시가 있으면 더 좋을 것 같습니다. 재고 정보를 저장하기 위해서 product_tb 에 stock : int 필드를 추가면 될 것 같습니다.

</br>

### 개별 상품 상세 조회

개별 상품 상세 조회 화면입니다. 해당 화면에서는 별점이 보이고, 옵션이라는 추가적인 요소가 보입니다. 우선, 별점 정보 저장을 위해 `product_tb` 에 star_count : int 필드를 추가합니다. 

그리고 옵션이 존재합니다. 옵션은 하나의 상품에 여러 개가 존재할 수 있기 때문에 상품 테이블에 필드를 추가하기 보다는, 새로운 테이블을 만들어 연결시키는 것이 좋을 것 같습니다. 따라서 옵션을 저장하기 위한 테이블 `option_tb` 가 필요합니다. 그리고 해당 테이블에는

1. id : int
2. name : string
3. price : int
4. product_id : int

가 필요합니다. product_id는 해당 옵션이 속한 상품의 정보를 가리키기 위해 필요합니다. 그리고 옵션 밑에 보면, 배송비 정보가 있습니다. 무료 배송인지, 추가 배송비가 필요한지, 필요한 배송비의 정보를 저장하기 위해서 상품 테이블에 추가적인 필드가 필요합니다. 따라서 `product_tb` 에 delivery_price : int 필드를 추가합니다. 

상품의 정보 조회를 위한 `/products/{id}` API가 필요합니다. 

또한, 해당 상품의 리뷰와 판매자의 정보를 확인할 수 있는 기능이 없습니다. 이를 추가하기 위해서는, 리뷰 테이블이 필요합니다. 그리고 `product_tb` 에서 판매자의 유저 아이디를 저장할 필드도 필요합니다. 리뷰 테이블의 필드는

1. id : int
2. product_id : int
3. contents : string
4. star_count : int
5. image : string

의 정보가 필요합니다. 리뷰 내용과 사진, 별점의 정보가 필요하고 어떤 상품의 리뷰인지를 구분하기 위한 product_id 필드도 필요합니다.

</br>

### 장바구니 담기

선택한 상품을 장바구니에 담는 페이지 입니다. 장바구니 정보를 저장해야 하므로, 우선 `cart_tb` 가 필요합니다. 장바구니는 해당 유저에 속한 정보이고 해당 테이블에는 상품의 정보가 저장되어야 하므로, 필요한 필드는

1. id : int
2. user_id : int
3. product_id : int
4. option_id : int
5. quantity : int
6. price : int

가 있습니다. 그리고 해당 정보를 저장하기 위해서 `/carts/add` API가 필요합니다. 해당 화면에서 부족한 기능은 없어 보입니다.

</br>

### 장바구니 보기

저장한 장바구니를 보여주는 페이지 입니다. 우선, 장바구니를 불러오기 위한 `/carts` API가 필요합니다. 그리고 수량 조절을 위한 `/carts/update` 도 필요합니다. 

그러나 해당 페이지에서는 장바구니에 담은 물건을 삭제하는 기능이 없습니다. 그리고 특정 상품만 선택하여 주문할 수 있는 기능이 없습니다. 따라서 장바구니 삭제를 위해 `/carts/delete/` API가 필요해 보입니다. 

</br>

### 주문

주문하는 페이지 입니다. 장바구니에서 선택한 아이템을 주문합니다. 현재 페이지에서는 수량만 조절 가능하므로, 특정 아이템만 선택할 수 있는 기능이 부족해 보입니다. 그리고 상품을 수령하기 위한 배송지 정보를 입력하는 부분이 없어 배송지 입력 기능이 추가되면 좋을 것 같습니다. 

그리고 포인트나 쿠폰 등 추가 할인을 위한 기능이 추가되면 좋을 것 같습니다. API는 `/coupons/` 나 포인트의 경우에는 유저 테이블에 추가해서 같이 관리하면 좋을 것 같습니다. 쿠폰은 모든 구매자에게 공통적으로 지급되는 것이므로, 쿠폰 테이블을 생성하여 쿠폰 정보를 저장하고, 유저가 가지고 있는 쿠폰은 다른 테이블을 하나 생성하여 유저 아이디와 쿠폰 아이디를 매핑시키면 될 것 같습니다.

</br>

### 결제

결제하는 화면에서는 장바구니에 있는 정보들을 주문 정보로 옮기고, 주문한 상품은 장바구니에서 삭제해야 합니다. 우선, 결제 정보 저장 및 확인을 위한 API `orders/save` 와 `orders/{id}` 가 필요합니다. 

다음으로, 결제 정보 저장을 위한 테이블 `order_tb` 가 필요합니다. 결제 화면을 잘 보면, 여러 아이템이 존재합니다. 따라서 주문 정보는 여러 개의 아이템으로 구성되어 있고 결제 정보는 한 명의 유저에 속하기 때문에 우선 유저와 결제를 연결하기 위해서 2개의 필드

1. id : int
2. user_id : int

가 필요합니다. 그리고 결제 아이템 정보 저장을 위한 테이블 `order_item_tb` 가 필요하고 필드로는 상품 정보 저장을 위해

1. id : int
2. option_id : int
3. order_id : int
4. quantity : int
5. price : int

가 필요합니다. 현재 화면에는 부족한 기능은 없다고 생각됩니다.

</br>
</br>
</br>

## 테이블 생성하기

위 정보들을 바탕으로 테이블을 생성합니다. 깃헙 프로젝트의 build.gradle 을 보면, mariaDB를 사용하는 것을 알 수 있으므로 mariaDB 의 문법으로 테이블을 작성합니다.

```sql
DROP TABLE IF EXISTS user_tb;
DROP TABLE IF EXISTS product_tb;
DROP TABLE IF EXISTS order_tb;
DROP TABLE IF EXISTS option_tb;
DROP TABLE IF EXISTS item_tb;
DROP TABLE IF EXISTS cart_tb;

CREATE TABLE user_tb
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    email    TEXT    NOT NULL,
    password TEXT    NOT NULL,
    roles    TEXT    NOT NULL,
    username TEXT    NOT NULL,
    created  DATETIME NOT NULL,
    UNIQUE (email)
);

CREATE TABLE product_tb
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    description  TEXT    NOT NULL,
    image        TEXT    NOT NULL,
    price        INTEGER NOT NULL,
    name         TEXT    NOT NULL,
    created      DATETIME NOT NULL
);

CREATE TABLE option_tb
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    option_name TEXT    NOT NULL,
    price       INTEGER NOT NULL,
    product_id  INTEGER NOT NULL,
    created     DATETIME NOT NULL,
    CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES product_tb
);

CREATE TABLE cart_tb
(
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    price     INTEGER NOT NULL,
    quantity  INTEGER NOT NULL,
    user_id   INTEGER NOT NULL,
    option_id INTEGER NOT NULL,
    created   DATETIME NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_tb,
    CONSTRAINT option_id_fk FOREIGN KEY (option_id) REFERENCES option_tb,
    UNIQUE (user_id, option_id)
);

CREATE TABLE order_tb
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    created DATETIME NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_tb
);

CREATE TABLE item_tb
(
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    price     INTEGER NOT NULL,
    quantity  INTEGER NOT NULL,
    option_id INTEGER NOT NULL,
    order_id  INTEGER NOT NULL,
    created   DATETIME NOT NULL,
    CONSTRAINT option_id_fk FOREIGN KEY (option_id) REFERENCES option_tb,
    CONSTRAINT order_id_fk FOREIGN KEY (order_id) REFERENCES order_tb
);

CREATE INDEX cart_user_id_idx on cart_tb (user_id);
CREATE INDEX cart_option_id_idx on cart_tb (option_id);
CREATE INDEX item_option_id_idx on item_tb (option_id);
CREATE INDEX item_order_id_idx on item_tb (order_id);
CREATE INDEX option_product_id_idx on option_tb (product_id);
CREATE INDEX order_user_id_idx on order_tb (user_id);
```

<br/>
<br/>
<br/>

## ER 다이어그램

![ER Diagram](https://github.com/SeokjunMoon/step2-BE-kakao-shop/blob/feat-moonseokjun/데이터베이스_ER_다이어그램.png)

</br>
</br>
</br>
</br>
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
