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

## **1번 과제**

### 1. 회원가입
```
> 회원가입 데이터 검증
```
</br>

### 2. 전체 상품 목록 조회
```
> 카테고리 별 조회
> 톡별가 마감일
> 공유하기
> 찜하기
```
</br>

### 3. 개별 상품 조회
```
> 별점 및 리뷰
> 톡딜가로 바로 구매
```
</br>


### 4. 장바구니 조회
```
> 장바구니 상품 삭제
```
</br>

### 5. 주문
```
> 장바구니 내 선택 구매
> 배송 방법 선택
> 배송지 입력
```
</br>

### 6. 검색
```
> 상품 검색
```
</br>

### 7. 판매자 권한
```
> 업체 상품 등록
```
</br>
</br>

## **2번 과제**
```
회원가입 버튼 -> 
[Post] http://localhost:8080/check
[Post] http://localhost:8080/join
```

```
로그인 버튼 -> 
[Post] http://localhost:8080/login
```

```
로그아웃 -> 
클라이언트 토큰 이용
```

```
전체 상품 조회 -> 
[Get] http://localhost:8080/products
```

```
개별 상품 조회 ->  
[Get] http://localhost:8080/products/[product_id]
```

```
장바구니 담기 -> 
[Post] http://localhost:8080/carts/add
```

```
장바구니 조회 -> 
[Get] http://localhost:8080/carts
```

```
주문하기 버튼 -> 
[Post] http://localhost:8080/carts/update
```

```
결제하기 버튼 -> 
[Post] http://localhost:8080/orders/save
```

```
주문 결과확인 -> 
[Get] http://localhost:8080/orders/[item_id]
```
</br>
</br>

## **3번 과제**
```
> 만약 리뷰 엔티티를 만든다면 [GET]http://localhost:8080/products/1 (개별 상품 조회) 시에 별점 데이터를 넘겨주는 것이 좋을 것 같다. 
```
</br>

```
> [Post] http://localhost:8080/login (로그인) 시에 사용자 권한을 의미하는 role을 Response로 넘겨주어서 구매자, 판매자, 관리자마다 다른 유저 경험을 겪게 하는 것이 좋을 것 같다.
```
</br>

```
> [POST] http://localhost:8080/carts/update (장바구니 업데이트 및 주문) 시에 Response로 옵션 가격 * 담은 수량 결과값을 연산하여 넘겨주어 카트 당 가격을 프론트에서 바로 사용할 수 있게 하는 편이 좋을 것 같다.
```
</br>

```
> 만약 주문하기 이전에 배송지에 대한 정보를 받고 그에 대한 정보를 [POST] http://localhost:8080/carts/update 시에 반영하여 연산해 totalPrice에 추가하면 좋을 것 같다.
```
</br>
</br>

## **4번 과제**
```
CREATE TABLE `User` (
	`id`	int	NOT NULL,
	`email`	varchar(100)	NOT NULL,
	`password`	varchar(256)	NOT NULL,
	`userName`	varchar(45)	NOT NULL,
	`userDate`	datetime	NOT NULL,
	`roles`	list	NULL
);

CREATE TABLE `Product` (
	`id`	int	NOT NULL,
	`productName`	varchar(256)	NOT NULL,
	`image`	varchar(256)	NULL,
	`productPrice`	int	NOT NULL,
	`productDate`	datetime	NOT NULL
);

CREATE TABLE `Option` (
	`id`	int	NOT NULL,
	`productId`	int	NOT NULL,
	`optionName`	varchar(256)	NOT NULL,
	`optionPrice`	int	NOT NULL,
	`optionDate`	datetime	NOT NULL
);

CREATE TABLE `Order` (
	`id`	int	NOT NULL,
	`userId`	int	NOT NULL,
	`totalPrice`	int	NOT NULL,
	`orderDate`	datetime	NOT NULL
);

CREATE TABLE `Cart` (
	`id`	int	NOT NULL,
	`productId`	int	NOT NULL,
	`userId`	int	NOT NULL,
	`cartQuantity`	int	NOT NULL,
	`cartPrice`	int	NOT NULL,
	`cartDate`	datetime	NOT NULL
);

CREATE TABLE `Item` (
	`id`	int	NOT NULL,
	`orderId`	int	NOT NULL,
	`optionId`	int	NOT NULL,
	`itemQuantity`	int	NOT NULL,
	`itemPrice`	int	NOT NULL
);

ALTER TABLE `User` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`id`
);

ALTER TABLE `Product` ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (
	`id`
);

ALTER TABLE `Option` ADD CONSTRAINT `PK_OPTION` PRIMARY KEY (
	`id`
);

ALTER TABLE `Order` ADD CONSTRAINT `PK_ORDER` PRIMARY KEY (
	`id`
);

ALTER TABLE `Cart` ADD CONSTRAINT `PK_CART` PRIMARY KEY (
	`id`
);

ALTER TABLE `Item` ADD CONSTRAINT `PK_ITEM` PRIMARY KEY (
	`id`
);


```
    이미지 링크 : https://ibb.co/6WMK0Cz

</br>
</br>

## **작성한 엔티티 코드**
```
package com.example.kakao.user;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_tb")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false, unique = true)
    private String email; // 인증시 필요한 필드
    @Column(length = 256, nullable = false)
    private String password;
    @Column(length = 45, nullable = false)
    private String username;
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Column(length = 30)
    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>(); // role은 한 개 이상

    @Builder
    public User(int id, String email, String password, String username, LocalDateTime date, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.date = date;
        this.roles = roles;
    }
}

```

</br>

```
package com.example.kakao.product;

import lombok.*;
import javax.persistence.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="product_tb")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length = 256, nullable = false)
    private String productName;
    
    @Column(length = 256)
    private String image;
    
    @Column(nullable = false)
    private int price;
    
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Builder
    public Product(int id, String productName, String image, int price, LocalDateTime date) {
        this.id = id;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.date = date;
    }
}

```

</br>

```
package com.example.kakao.cart;

import lombok.*;
import javax.persistence.*;

import com.example.kakao.option.Option;
import com.example.kakao.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="cart_tb")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Builder
    public Cart(int id, User user, Option option, int quantity, int price, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
}

```

</br>

```
package com.example.kakao.order;

import lombok.*;

import javax.persistence.*;

import com.example.kakao.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_tb")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(int id, int totalPrice, LocalDateTime date, User user) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.date = date;
        this.user = user;
    }
}

```

</br>

```
package com.example.kakao.item;

import lombok.*;

import javax.persistence.*;

import com.example.kakao.option.Option;
import com.example.kakao.order.Order;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="item_tb")
public class Item{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @Builder
    public Item(int id, int quantity, Order order, Option option) {
        this.id = id;
        this.quantity = quantity;
        this.order = order;
        this.option = option;
    }
}

```

</br>

```
package com.example.kakao.option;

import lombok.*;
import javax.persistence.*;

import com.example.kakao.product.Product;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="option_tb")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 256, nullable = false)
    private String optionName;

    @Column(nullable = false)
    private int price;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Option(int id, String optionName, int price, LocalDateTime date, Product product) {
        this.id = id;
        this.optionName = optionName;
        this.price = price;
        this.date = date;
        this.product = product;
    }
}

```


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

## **Restful API 설계**

```
- 이메일 중복 체크 -> [POST] http://localhost:8080/users/email-verification
- 회원 가입 -> [POST] http://localhost:8080/users
- 로그인 -> [POST] http://localhost:8080/login
- 전체 상품 조회 -> [GET] http://localhost:8080/products
- 개별 상품 조회 -> [GET] http://localhost:8080/products/{id}
- 장바구니에 상품 추가 -> [POST] http://localhost:8080/carts/{id}
- 장바구니 조회 -> [GET] http://localhost:8080/carts
- 장바구니 상품 수정(주문하기 버튼) -> [PUT] http://localhost:8080/carts/{id}
- 주문하기(결제하기 버튼) -> [POST] http://localhost:8080/orders
- 주문 결과 확인 -> [GET] http://localhost:8080/orders/{id}

추가
- 장바구니 상품 삭제 -> [DELETE] http://localhost:8080/carts/{id}
- 주문 취소 -> [DELETE] http://localhost:8080/orders/{id}
```

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
