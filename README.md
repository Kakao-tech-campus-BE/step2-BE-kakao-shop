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

## **과제 내용**

>1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.

</br>

&nbsp;&nbsp;&nbsp;● 결제 전 중간 다리가 필요해보인다. save로 요청이 가는 순간 결제가 완료되는 것 보다 중간에 "결제 하시겠습니까?" 와 같은 경고 창이 하나 뜨는 것이 좋아보인다.

</br>

&nbsp;&nbsp;&nbsp;● id/pw분실 시 대책이 전무하다. 보안 규칙에 따라 기존 pw는 파기 시키고 새로운 임시 pw를 생성 후 변경할 수 있도록 하는 것이 좋아보인다.

</br>

&nbsp;&nbsp;&nbsp;● pw 변경 기능 역시 위의 기능을 수행하기 위해 추가되어야 한다.

</br>

&nbsp;&nbsp;&nbsp;● 장바구니/주문 옵션 선택 창에서 옵션을 삭제하는 기능이 필요해보인다. 특히 장바구니는 DB와 연동되는 부분이므로 백엔드 차원에서 DB에 저장된 내용을 수정 혹은 삭제하는 기능을 추가해야 할 것이다.

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/be240ae1-f994-4d90-b342-0b0bf6eac47c)

</br>

>2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)

</br>

&nbsp;&nbsp;&nbsp;● 회원 가입 → {base_url}/join [POST] + 이메일 중복 체크 → {base_url}/check [POST]

<img width="560" alt="image" src="https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/b0d36d39-0949-4028-bf78-8dd8372adead">


</br>

&nbsp;&nbsp;&nbsp;● 로그인→ {base_url}/login [POST]

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/946d648f-a939-4689-b28b-e8927bb61f41)

</br>

&nbsp;&nbsp;&nbsp;● 상품 목록 조회 → {base_url}/products?page={num} [GET] 여기서 page의 default 값은 0

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/5ebca23d-7271-4963-9298-9edce9355c47)

</br>

&nbsp;&nbsp;&nbsp;● 개별 상품 정보 자세히 조회(옵션 포함) → {base_url}/products/{product_id} [GET]

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/2d815085-62b1-4440-a293-cc96f7c11c7b)

</br>

&nbsp;&nbsp;&nbsp;● 장바구니에 담기 → {base_url}/carts/add [POST]

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/9761132d-f946-4b17-acf3-094cf8f336bc)

</br>

&nbsp;&nbsp;&nbsp;● 장바구니 조회 → {base_url}/carts [GET] + 장바구니 수정 → {base_url}/carts/update [POST]

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/1032a0cb-3f77-4947-b91f-2add3c4f13a9)

</br>

&nbsp;&nbsp;&nbsp;● 결제하기 → {base_url}/orders/save [POST] 

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/2ab1d1a4-3008-4c2b-b031-2646a51d1f00)

</br>

&nbsp;&nbsp;&nbsp;● 주문 내역 확인 → {base_url}/orders/{order_id} [GET]

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/8e2d7b57-42f0-42e2-88f1-1d97b35eb2e5)

</br>

>3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.

</br>

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/da8365e8-b8bc-4795-87a3-9676f196306b)

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/62a5e3e2-f39e-48d1-b4d1-bc279db2fe20)

&nbsp;&nbsp;&nbsp;● 무료/유료 배송 여부, 유료 배송이라면 배송 방법에 따른 비용도 필요해보인다.

&nbsp;&nbsp;&nbsp;● 저 화면에 있는 톡별가의 의미가 무엇일까…? 회원의 등급에 따라 가격이 달라진다면  회원에 따른 할인 비율 정보도 필요해보인다.

&nbsp;&nbsp;&nbsp;+ 김철호 조원의 의견으로, 상품의 구매 가능 여부를 DB에 저장하는게 좋겠다는 의견을 제시해주셨다.요구사항 명세서에 보면 주문이 가능한 상품 목록을 반환 한다고 되어 있기 때문에 명시적으로 표시해 주어야 한다는 것이다.

</br>

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/88de75cd-b013-45de-8999-111a29c37171)

&nbsp;&nbsp;&nbsp;● 기본 배송지 정보 데이터도 사실 DB에서 꺼내와야 하는 데이터인데 아예 매칭되는 api 자체가 없다.

</br>

**post man을 통해 발견한 부족한 점**

</br>

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/7d87ae33-dcac-4f3c-bc82-248720fc515e)

&nbsp;&nbsp;&nbsp;● 이름이 너무 길어서 서버가 터진 모습이다. DB에서 지정한 길이를 넘어가는 데이터에 대해서는 예외 처리를 해줄 필요가 있어 보인다.

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/c606abf1-c1e0-4cf3-9b00-4846a51b936b)

&nbsp;&nbsp;&nbsp;● e-mail 도 마찬가지로 지나치게 긴 정보를 입력하면 서버가 터지기에 사용자의 입력이 잘못되었다고 알려줄 필요가 있다.

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/b4d006a4-9656-416c-8bea-35a85e2fb705)

&nbsp;&nbsp;&nbsp;● e-mail 중복 체크 시 true가 뜨는데 이게 사용 가능한 이메일인지, 아니면 중복이라는 뜻인지 애매하다. 좀 더 명확한 메세지가 필요하다.

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/6bcf3af1-083e-45ec-b123-e711ab3238af)

&nbsp;&nbsp;&nbsp;● 강의 시간에 언급된, 장바구니에 같은 옵션을 넣었을 경우 발생하는 문제이다. 키가 같은 옵션이 한번 더 DB에 들어오니까 문제가 되는 것 같은데, 내부적으로 같은 옵션이 들어오면 해당 옵션의 개수를 늘리는 로직이 필요해보인다.

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/e9213b89-07fb-4b60-92c3-1b9cc2a7e7e2)

&nbsp;&nbsp;&nbsp;● 옵션의 수량에 음수를 집어넣어도 감지하지 못하는 문제가 있다.

![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/1b9670f0-7519-4e43-823a-1cd2dfb123a3)

&nbsp;&nbsp;&nbsp;● 가격 역시 음수가 되어도 이상함을 감지하지 못한다. 옵션 수량에 음수가 들어오지 못하도록 막으면 자연히 해결될 문제겠지만 혹시 모르니 여기서도 이상값을 감지하는 것이 좋아보인다.

>4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.

</br>

● 기본 요구사항에 부합하는 테이블 설계
<ul>
  <li>
    일단 수량은 반드시 양수여야 하므로 unsigned를 붙여주었다. 또한 비밀번호나 별점 같은 경우 제약 조건 검사 구문을 달아 최종 방어선 같은 느낌으로 동작하도록 하였다.
  </li>
  <li>
    또한 조원들과 논의 끝에 관리의 용이함을 위해 order 와 order item, cart와 cart item을 분리하여 설계하기로 하였다.
  </li>
  <li>
    다만 total price는 갱신 이상을 고려하여 따로 처리하는 로직이 있는 게 훨씬 좋아보여 제거하였다.
  </li>
</ul>

</br>

```
# 세상에서 가장 긴 이름이 746자 임을 감안하여 text타입으로 설정
# 비밀 번호는 8자에서 20자 사이
CREATE TABLE user(
	user_id INTEGER PRIMARY KEY auto_increment,
    username TEXT,
    email VARCHAR(255),
    password VARCHAR(20) CHECK(char_length(password) >= 8),
    created_at DATE
);

# 제품의 이름도 255자 이상일 수 있음을 감안하여 text타입으로 설정
CREATE TABLE product(
	product_id INTEGER PRIMARY KEY auto_increment,
    product_name TEXT,
    description TEXT,
    product_image VARCHAR(255),
    price INTEGER,
    star_count INTEGER CHECK( 0 <= star_count <= 5),
    created_at DATE
);

CREATE TABLE options(
	option_id INTEGER PRIMARY KEY auto_increment,
    option_name TEXT,
    option_price INTEGER,
    created_at DATE,
    product_id INTEGER,
    FOREIGN KEY(product_id) REFERENCES product(product_id)
);

CREATE TABLE cart(
	cart_id INTEGER PRIMARY KEY auto_increment,
    user_id INTEGER,
    FOREIGN KEY(user_id) REFERENCES user(user_id)
);

CREATE TABLE cart_item(
	cart_item_id INTEGER PRIMARY KEY auto_increment,
    quantity INTEGER UNSIGNED,
    price INTEGER,
    option_id INTEGER,
    cart_id INTEGER,
    FOREIGN KEY (option_id) REFERENCES options(option_id),
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id)
);

# total price 항목이 굳이 DB에 있어야 하나?
# 만약 아이템 하나가 가격 변동이 생긴다면? total price 항목 또한 업데이트가 필요
# 데이터의 일관성이 깨질 수 있다. 따라서 order_item 테이블에서 가져온 가격과 수량 정보로
# DTO 전달 시점에서 처리 하는 편이 좋을 것이다.
CREATE TABLE orders(
	order_id INTEGER PRIMARY KEY auto_increment,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE order_item(
	order_item_id INTEGER PRIMARY KEY auto_increment,
    quantity INTEGER UNSIGNED,
	price INTEGER,
    option_id INTEGER,
    order_id INTEGER,
    FOREIGN KEY (option_id) REFERENCES options (option_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);
```
![image](https://github.com/latteisacat/step2-BE-kakao-shop/assets/114455070/356576e7-a0f3-40fb-92ea-bb29a2833798)

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
