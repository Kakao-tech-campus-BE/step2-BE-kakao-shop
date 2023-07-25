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

## **과제명**
<details>
<summary>1. 요구사항 시나리오에서 부족해 보이는 기능을 하나 이상 체크</summary>

- 회원정보를 보관할 때 비밀번호의 암호화 보관을 추가하자.
- 옵션 선택 페이지와 장바구니 페이지에서 선택했던 상품의 취소가 가능해야한다.
- 제품의 판매 서비스도 카카오 쇼핑하기에서 이루어져야 한다고 본다. 그러므로 유저의 입장을 구분하여 서비스 할 수 있는 역할을 필드로 추가하고 로그인 이후의 페이지 요청을 추가해야 한다.
- 판매하는 제품의 위치와 배달비에 대한 필드를 추가해야 한다. 이는 화면설계서 상에 있지만 기능으로는 구현되어 있지 않다.
- 제품의 리뷰와 설명을 보여주어야 한다고 생각하므로, 별도의 테이블이 필요하다. 예를 들면 별점에 대한 집계는 리뷰들의 평균으로 산정해야 하는데, 이에 대한 정보가 추가되어야 한다.
- 제품의 가격표시에 대한 정의가 필요하다고 본다. 현재는 옵션의 최소가격으로 표시하되 이 기능은 프론트에서 맡으면 될것 같다.
- 배송지에 대한 테이블을 만들어 배송비를 관리하고 사용자가 여러 배송받을 장소를 정하고 그 중에서 기존 배송지를 저장해둘 수 있도록 제공해야 한다.
- 조회의 성능을 높이기 위해서 제품의 상세 정보 테이블을 생성하여 전체 상품 목록 조회의 성능을 높여야 한다.
- 장바구니에 담기 기능 사용시 기존 장바구니에 담긴 중복 옵션이 추가되는 방식으로 설정되어야 한다.
- 장바구니에서 옵션 수량 결정과 주문하기에 대한 요청의 분리가 필요하다. 옵션 수량은 update라는 요청이 문맥과 일치하지만 주문하기는 일반적인 orders라는 별도의 요청이 필요하다고 본다.
- 장바구니 보기 또는 주문하기 페이지에서 배송비에 대한 처리가 이루어져야 한다. 일단 주문하기 페이지에서 추가되도록 설정하려고 한다.
</details>
<details>
<summary>2. 제시된 화면 설계와 API 주소 매칭</summary>

(기능1) 회원 가입(버튼 클릭) : /join
- request로 받은 회원 정보의 유효성을 재검토 비밀번호를 반드시 암호화하여 저장할 것
![Alt text](readme-src/%EA%B8%B0%EB%8A%A51.PNG)

(기능2) 로그인(버튼 클릭) : /login
- request로 받은 계정정보의 양식의 유효성을 판단 (아이디만)
- 실제로 유효한 계정인지를 DB 검색으로 판단 (비밀번호는 암호화)
- 유효한 경우 JWT 반환
![Alt text](readme-src/%EA%B8%B0%EB%8A%A52.PNG)

(기능3) 로그아웃(버튼 클릭) : /logout
- 사용자 로그인 상태를 종료
- 이미 로그아웃인 경우에 대한 처리 구분도 필요하다고 보임
![Alt text](readme-src/%EA%B8%B0%EB%8A%A53.PNG)

(기능4) 전체 상품 목록 조회 : /products
- 상품을 저장해둔 데이터베이스에서 주문이 가능한 전체 상품 목록을 reponse로 반환
- 페이지에서는 옵션의 최소가격만 보여주는데, 실제로는 최대 최소를 보여주며 해당 옵션의 수량이 매진되면 표시 가격이 변동 되어야하는게 아닌가 고민 중
![Alt text](readme-src/%EA%B8%B0%EB%8A%A54.PNG)

(기능5) 개별 상품 상세 조회(상품 클릭) : /products/[제품_id]
- 전체 상품 목록에서 특정 상품 카드를 클릭하면 해당 상품에 대한 상세 정보를 response로 반환 -> 이를 위해서 제품 테이블과 제품 상세정보에 대한 테이블을 분리해야 한다.
- 톡딜가에 대한 정보도 추가되어야함
- 해당 상품의 옵션들도 response로 반환
![Alt text](readme-src/%EA%B8%B0%EB%8A%A55.PNG)

(기능6) 상품 옵션 선택 : /products/[제품_id]
- 상품 옵션 선택, 이미 선택된 옵션은 다시 선택 불가
- 선택된 옵션은 삭제 및 재선택 가능
![Alt text](readme-src/%EA%B8%B0%EB%8A%A56.PNG)

(기능7) 옵션 확인 및 수량 결정 : /products/[제품_id]
- 아래 기능은 프론트에서 구현되어야하는 기능이 아닌가 궁금함
- 상품 옵션 선택 후 선택한 옵션 재확인 및 수량 결정 가능
- 선택한 옵션과 수량에 따라 합계 금액이 출력됨
![Alt text](readme-src/%EA%B8%B0%EB%8A%A56.PNG)

(기능8) 장바구니 담기(버튼 클릭) : /carts/add
- 옵션 확인 및 수량 결정 후, "장바구니 담기" 버튼을 클릭하면 -> request로 선택항 상품데이터를 데이터베이스에 저장됨
- 위 과정에서 옵션의 중복이 있는지, 옵션 수량이 유효한 값인지와 같은 유효성 검사가 필요함
![Alt text](readme-src/%EA%B8%B0%EB%8A%A58.PNG)

(기능9) 장바구니 보기(버튼 클릭) : /carts
- 장바구니에 담긴 상품 데이터 (이미지, 상품명, 옵션 등)을 reponse로 반환
![Alt text](readme-src/%EA%B8%B0%EB%8A%A59.PNG)

(기능10) 장바구니 상품 옵션 확인 및 수량 결정 : /carts/update
- 상품별 구매금액 소계, 전체 주문 합계 금액등을 화면에 출력
- 주문하기 버튼을 누르면 request로 장바구니 상품 정보를 받고, 데이터베이스를 update
- 하지만 주문하기 버튼과 옵션수량 갱신이 같은 동작이라면 프론트에서 같은 응답을 다르게 처리한다는 것이 된다.
- 이는 서로 다른 목적에 같은 request가 되는데 이는 분리할 수 없는 단일 개체가 되어 수정에 적합하지 않다고 생각한다. 이유는 주문을 시작하게 되면 장바구니 정보외에도 추가적인 정보들이 붙을 수 있게되는데 이렇게 되면 프론트에서는 /carts/update에 더한 다른 추가정보를 받을 수 없게된다.
- 주문하기에 대한 요청은 추후의 데이터가 추가될 수 있음을 고려하여 새로운 API를 생성해야하는게 아닌가 의문이 든다.
![Alt text](readme-src/%EA%B8%B0%EB%8A%A510.PNG)

(기능11) 주문 : /orders
- 장바구니에 담긴 데이터를 response로 반환
- 옵션 데이터 뿐 아니라 해당 옵션들의 상품명, 옵션명도 같이 반환
- 앞의 설명과 같이 해당 요청은 다른 요청을 통하여 반영되어야 한다고 봄
- 예를 들어 배송비의 경우 일정 제품그룹별로 나누어 배송비를 알려줘야하는걸로 보고 있음
![Alt text](readme-src/%EA%B8%B0%EB%8A%A511.PNG)

(기능12) 결제 : /orders/save
- 결제하기 버튼 클릭 시 실제 절차 없이 상품을 주문한 것으로 처리하고 주문결과 페이지를 보여줌
- 전달받은 데이터를 데이터베이스에 저장
![Alt text](readme-src/%EA%B8%B0%EB%8A%A512.PNG)

(기능13) 주문 결과 확인 : /orders/1
- 결제가 성공적으로 처리되면, 주문한 상품들의 정보를 reponse로 반환
![Alt text](readme-src/%EA%B8%B0%EB%8A%A513.PNG)
</details>
<details>
<summary>3. 현재 배포된 서버의 모든 API를 테스트하고 부족한 데이터를 찾고 개선하기</summary>

- 3.1. 전체 상품 목록 조회 (/products)  
    - 정상요청 - 일치
    - param 사용(?page=1) - 일치
- 3.2. 개별 상품 상세 조회 (/products/1)  
    톡딜가의 경우 옵션 중 최소가격을 표시해야하는데 이렇게 되면 백엔드에서 지정해줄 필요가 없다고 봄  
    프론트에서 처리해주세요  
    배송방법과 배송비에 대한 데이터가 필요함
    - 정상요청 - 일치
- 3.3. 이메일 중복 체크,미사용 기능 (/check) 쓰지않는 기능임  
    - 정상요청 - 일치
    - 실패예시 1(중복) - 일치
    - 실패예시 2(양식) - 일치
- 3.4. 회원가입 (/join)
    - 정상요청 - 일치
    - 실패예시 1(이메일양식) - 일치
    - 실패예시 2(비밀번호양식) - 일치
    - 실패예시 3(중복) - 일치
    - 실패예시 4(비밀번호길이) - 일치
- 3.5. 로그인 (/login)  
    중복전송을 하면 중복로그인이 되므로 세션을 추가하여  
    중복 로그인등을 검사할 필요가 있어보임  
    - 정상요청 - 일치
    - 실패예시 1(이메일양식) - 일치
    - 실패예시 2(비밀번호양식) - 일치
    - 실패예시 3(미등록계정) - 일치
    - 실패예시 4(비밀번호길이) - 일치
- 3.6 장바구니 담기 (/carts/add)
    - 정상요청 - 일치
- 3.7 장바구니 조회 (/carts)
    - 정상요청 - 일치
- 3.8 주문하기 또는 장바구니 수정 (/carts/update)  
    자료상에서는 앞에서 담지 않았던 상품을 담았기에 요청의 일관성이 깨진다.  
    그러므로 앞에서 담은 장바구니의 상품id를 기반으로 재요청했다. 장바구니상의 옵션외에도 배송비에 대한 정보도 추가되어야 한다고 생각함

    - 정상요청 - 성공
- 3.9 결재하기 (/orders/save)  
    장바구니의 내용을 주문 아이템으로 옮긴 뒤 응답 응답 데이터에 배송지와 배송비에 대한 정보도 추가되어야 한다고 생각함  
    - 정상요청 - 성공
- 3.10 주문 결과 확인 (/orders/1)  
    방금 결재한 주문의 결과를 다시 상기시켜주기 위한 응답  
    응답 데이터에 배송지와 배송비에 대한 정보도 추가되어야 한다고 생각함  
    - 정상요청 - 성공
</details>
<details>
<summary>4.테이블을 설계하여 E-R diagram 제출</summary>
    
테이블 제목이 초록색인 경우는 추가로 고려할 수 있는 테이블

![Alt text](<readme-src/E-R diagram.PNG>)
</details>
<details>
<summary>5. 기본 서버의 모든 API를 테스트하고 부족한 데이터를 찾고 개선점 찾기</summary>

- 5.1. 전체 상품 목록 조회 (/products)
    - 미구현
- 5.2. 개별 상품 상세 조회 (/products/1)
    - 미구현
- 5.3. 이메일 중복 체크,미사용 기능 (/check)
    - 정상요청 - 일치
    - 실패예시 1(중복) - 불일치 -> 계정이 추가되어있지 않아서 이럴 수
    - 실패예시 2(양식) - 일치
- 5.4. 회원가입 (/join)
    - 정상요청 - 일치
    - 실패예시 1(이메일양식) - 일치
    - 실패예시 2(비밀번호양식) - 일치
    - 실패예시 3(중복) - 불일치 -> 5.3에서의 추측처럼 계정이 들어가 있지 않았음
    - 실패예시 4(비밀번호길이) - 일치
- 5.5. 로그인 (/login)

    굳이 로그인에서 비밀번호의 자세한 오류를 보여줘야할 필요가 있는지는 궁금함

    - 정상요청 - 일치
    - 실패예시 1(이메일양식) - 일치
    - 실패예시 2(비밀번호양식) - 일치
    - 실패예시 3(미등록계정) - 일치
    - 실패예시 4(비밀번호길이) - 일치
- 5.6 장바구니 담기 (/carts/add)
    - 미구현
- 5.7 장바구니 조회 (/carts)
    - 미구현
- 5.8 주문하기 또는 장바구니 수정 (/carts/update)
    - 미구현
- 5.9 결재하기 (/orders/save)
    - 미구현
- 5.10 주문 결과 확인 (/orders/1)
    - 미구현
</details>
<details>
<summary>6. 테이블 설계</summary>

# 화면 설계 상에서 요구되는 정보만을 다룰 수 있도록 반영
User
```
CREATE TABLE `user_tb` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(30) NOT NULL UNIQUE,
    `password` VARCHAR(256) NOT NULL,
    `email` VARCHAR(256) NOT NULL UNIQUE,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`));
```

Product
```
CREATE TABLE `product_tb` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `image` VARCHAR(1000) NOT NULL,
    `price` int(11) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`));
```

Option
```
CREATE TABLE `option_tb` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `product_id` int(11) NOT NULL,
    `price` int(11) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    FOREIGN KEY (`product_id`) REFERENCES `product_tb`(`id`),
    PRIMARY KEY (`id`));
```

Cart
```
CREATE TABLE `cart_tb` (   
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `option_id` int(11) NOT NULL,
    `quantity` int(11) NOT NULL,
    `price` int(11) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `cart_user_option` (`user_id`, `option_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user_tb`(`id`),
    FOREIGN KEY (`option_id`) REFERENCES `option_tb`(`id`));
```

Order
```
CREATE TABLE `order_tb` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user_tb`(`id`));
```

Order_item
```
CREATE TABLE `order_item_tb` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `order_id` int(11) NOT NULL,
    `option_id` int(11) NOT NULL,
    `price` int(11) NOT NULL,
    `quantity` int(11) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `order_item_order_option` (`order_id`, `option_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order_tb`(`id`),
    FOREIGN KEY (`option_id`) REFERENCES `option_tb`(`id`));
```
</details>

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

## **과제명**
<details>
    <summary>1. API주소를 설계하여 README에 내용을 작성하시오.</summary>

<details>
<summary>3. DTO 설계 규칙</summary>

하나의 URI에 대해 요청과 응답의 DTO 설계 규칙을 정하고  
각 요청과 응답은 아래의 형식을 지키고 있다.
하나의 클래스 안에 이너 클래스 형식으로 구성요소들을 구성하여 벗어나지 못하도록 했다.
### Request
```
@Getter
public class [action]RequestDTO {
    private [type] [member variable name];
}
```
### Response
```
@Getter
@Builder
public class [action]ResponseDTO {
    private [type] [member variable name];

    // constructor;
}
```
</details>

## 변경 사항
인증 관련 URI는 통합되어 있지 않아 하나로 묶여있다고 알 수 없다. 그래서 이를 표현하고자 /auth prefix를 추가하여 일관성을 유지하고자 했다. 추가로 다른 도메인의 URI도 공통 prefix가 존재하여 도메인 컨트롤러에 @RequestMapping을 추가하여 중복을 최소화 했다.
### 인증(prefix : /auth)

| method | URI | New-URI | Description |
| --- | --- | --- | --- |
| POST | /join | /join | 회원가입 |
| POST | /login | /login | 로그인 |

### 제품(prefix : /products)

| method | URI | params | Description |
| --- | --- | --- | --- |
| GET |  | page | 전체 상품 목록 조회 |
| GET | /[product_idx] |  | 개별 상품 조회 |

### 장바구니(prefix : /carts)

| method | URI | Description |
| --- | --- | --- |
| POST | /add  | 장바구니 담기 |
| GET |  | 장바구니 조회 |
| POST | /update | 장바구니 수정 |

### 주문(prefix : /orders)

| method | URI | Description |
| --- | --- | --- |
| POST | /save | 결재하기 |
| GET | /[order_index] | 주문 결과 확인 |
</details>
<details>
<summary>2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.</summary>

### 인증
```
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserRestController {
    private final UserJPARepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body(ApiUtils.success(null));
    }
}
```
### 제품
```
@RestController
@RequestMapping("/products")
public class ProductRestController {
    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam int page) {
        List<ProductFindAllResponseDTO.Response> responseDTO = new ArrayList<>();

        // 상품 하나씩 집어넣기
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 5000
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900
        ));
        responseDTO.add(new ProductFindAllResponseDTO.Response(
                9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000
        ));


        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 상품을 담을 DTO 생성
        ProductFindByIdResponseDTO.Response responseDTO = null;

        if(id == 1){
            List<ProductFindByIdResponseDTO.Option> optionDTOList = new ArrayList<>();
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900));
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(3, "고무장갑 베이지 S(소형) 6팩", 9900));
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(4, "뽑아쓰는 키친타올 130매 12팩", 16900));
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(5, "2겹 식빵수세미 6매", 8900));
            responseDTO = new ProductFindByIdResponseDTO.Response(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000, 5, optionDTOList);
        }else if(id == 2){
            List<ProductFindByIdResponseDTO.Option> optionDTOList = new ArrayList<>();
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(6, "22년산 햇단밤 700g(한정판매)", 9900));
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(7, "22년산 햇단밤 1kg(한정판매)", 14500));
            optionDTOList.add(new ProductFindByIdResponseDTO.Option(8, "밤깎기+다회용 구이판 세트", 5500));
            responseDTO = new ProductFindByIdResponseDTO.Response(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000, 5, optionDTOList);
        }else {
            return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
        }

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
```
### 장바구니
```
@RestController
@RequestMapping("/carts")
public class CartRestController {
    @PostMapping("/update")
    public ResponseEntity<?> updateOption(@RequestBody List<UpdateOptionRequestDTO> updateDTOList) {
        List<UpdateOptionResponseDTO.Item> itemList = new ArrayList<>();
        itemList.add(
                UpdateOptionResponseDTO.Item.builder()
                        .cartId(4)
                        .optionId(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build());

        itemList.add(
                UpdateOptionResponseDTO.Item.builder()
                        .cartId(5)
                        .optionId(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build());

        UpdateOptionResponseDTO.Response responseDTO = UpdateOptionResponseDTO.Response.builder()
                .carts(itemList)
                .totalPrice(209000)
                .build();
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOption(@RequestBody List<AddOptionRequestDTO.Request> requests) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<FindAllResponseDTO.Item> items = new ArrayList<>();

        FindAllResponseDTO.Option option1 = FindAllResponseDTO.Option.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build();

        // 카트 아이템 리스트에 담기
        FindAllResponseDTO.Item cartItemDTO1 = FindAllResponseDTO.Item.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .option(option1)
                .build();
        items.add(cartItemDTO1);


        FindAllResponseDTO.Option option2 = FindAllResponseDTO.Option.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .price(10900)
                .build();
        FindAllResponseDTO.Item cartItemDTO2 = FindAllResponseDTO.Item.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .option(option2)
                .build();
        items.add(cartItemDTO2);

        // productDTO 리스트 만들기
        List<FindAllResponseDTO.Product> productDTOList = new ArrayList<>();

        // productDTO 리스트에 담기
        productDTOList.add(
                FindAllResponseDTO.Product.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .carts(items)
                        .build());

        FindAllResponseDTO.Response responseDTO = FindAllResponseDTO.Response.builder()
                .products(productDTOList)
                .totalPrice(104500)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
```
### 주문
```
@RestController
@RequestMapping("/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> save() {
        OrderFindResponseDTO.Item item1 = OrderFindResponseDTO.Item.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderFindResponseDTO.Item item2 = OrderFindResponseDTO.Item.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        List<OrderFindResponseDTO.Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        OrderFindResponseDTO.Product orderProduct = OrderFindResponseDTO.Product.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전")
                .items(itemList)
                .build();

        List<OrderFindResponseDTO.Product> products = new ArrayList<>();
        products.add(orderProduct);

        OrderFindResponseDTO.Response responseDTO = OrderFindResponseDTO.Response.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @GetMapping("/{idx}")
    public ResponseEntity<?> findById(@PathVariable("idx") int idx) {
        OrderSaveResponseDTO.Item item1 = OrderSaveResponseDTO.Item.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스 에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderSaveResponseDTO.Item item2 = OrderSaveResponseDTO.Item.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 4종")
                .quantity(10)
                .price(109000)
                .build();

        List<OrderSaveResponseDTO.Item> orderItemList = new ArrayList<>();
        orderItemList.add(item1);
        orderItemList.add(item2);

        OrderSaveResponseDTO.Product orderProduct = OrderSaveResponseDTO.Product.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemList)
                .build();

        List<OrderSaveResponseDTO.Product> products = new ArrayList<>();
        products.add(orderProduct);

        OrderSaveResponseDTO.Response responseDTO = OrderSaveResponseDTO.Response.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();

        List<OrderSaveResponseDTO.Response> responseDTOs = new ArrayList<>();
        responseDTOs.add(responseDTO);
        OrderSaveResponseDTO.Response order1 = OrderSaveResponseDTO.Response.builder()
                .id(2)
                .products(products)
                .totalPrice(209000)
                .build();
        responseDTOs.add(responseDTO);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTOs.get(idx-1)));
    }
}
```
</details>

<details>
<summary>3. DTO 설계 원칙 정의</summary>

하나의 요청에 대해서 response와 request 를 저장하는 DTO를 설계했다.  
하나의 파일에서 이너클래스를 통하여 외부로 벗어나지 않도록 했다.  
DTO 처리 일관성을 위해서 @Setter 제거했다.  
설계된 모든 DTO는 아래와 같은 양식을 따른다.  
### Response
```
@Getter
public class [action]ResponseDTO {
    // member variable
    private static [type] [variable name]
}
```
### Request
```
@Getter
@Builder
public class [action]RequestDTO {
    // member variable
    private static [type] [variable name]

    // constructor
}
```
</details>

<details>
<summary>4. Mock API를 구성하여 테스트한 결과</summary>

API 문서의 응답 결과를 postman을 통해 검증하였다.  
[postman API 테스트 모음](<readme-src/references/API 명세서 기반 요청들.postman_collection.json>)
응답 결과가 정확히 일치하는지 테스트를 했고 결과적으로 전부 일치한 결과를 얻을 수 있었다.  
</details>

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
>- BDD 패턴으로 구현되었는가? given, when, then
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_이재훈_3주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>   - lazy loading 과 fetch join, 영속성유지에 대해서 아직은 서툰거 같습니다. 그래서 결과 출력하는 부분이 좀 어색하네요. 적용한 join fetch 가 적절한지도 의문입니다.
>   - sql 이 미숙하여 native query나 jpql 등 다양한 방법을 썼음에도 적절한지 모르겠습니다.

>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
>   - 제가 문제의 범위를 잘못 이해해서 서비스 시나리오까지 구현한거 같은데 범위를 더 좁혔어야 했을까요?
>   - 앞에서 어려웠던 점에서 fetch join을 했을 때 paging이 안되는 것으로 알고있는데 이런경우에는 어떻게 해결할 수 있을까요?
>   - 지금 같은 경우는 단방향 참조이면서 paging이 적용되는 부분이 fk를 가지지 않았기 때문에 별다는 참조가 없어서 문제가 없었는데, 반대로 paging 해야되는 부분에서 fk를 가지고 해당 정보로 끌어와야하는 경우를 고민하고 있었습니다.
>   - if 로 null 체크를 하는 대신 코드를 간결하게 보기 위해서 assertion을 주로 사용했는데 이 방식은 별로일까요? 지금은 test에서 이루어지기 때문에 junit에서 사용되는 함수를 쓰지만 실제 구현때는 if문으로 구현되는게 맞겠죠?
>   - 주문 레포지토리 테스트 코드에서 product_findById_and_option_findByProductId_lazy_test 메서드에서 적절한 쿼리였는지 아니면 더 개선하게 된다면 어떻게 할 수 있을지 알려주셨으면 좋겠습니다. 
>   - trade-off 의 관계인 dto를 사용하는 것과 단일값이나 엔티티까지만 활용하는 것에 있어서 실제로 부하의 차이가 많이 날거라고 생각하고 있습니다. 그래서 주문 레포지토리 테스트 코드에서 실제로 구현하고자 했던 방식은 mysql의 row_number()를 활용하고자 했지만 안되네요. jpql의 한계와 native query에서도 한계가 있는거 같아서 다른 방식을 찾을 수 없었습니다. 

## **과제명**
<details>
<summary>
1번, 2번 과제 동시 제출(Hibernate로 바로 비교하기 위함)

1. 레포지토리 단위테스트를 구현하여 소스코드를 제출하시오.  

2. 쿼리를 테스트하면서 가장 좋은 쿼리를 작성해보시오.
</summary>

## 장바구니
    
### 1. 장바구니 담기(중복 포함)
given

    유저 정보, saveDTO(옵션 id, 수량)

when

    기존 장바구니 목록에 더한다  
    가정  
    이미 등록한 제품이라면? 추가해야하는데 이걸 중복되게 할 수 있는가  
    아니면 기존에서 찾은다음 추가하는 별도의 로직이 필요한가  
    결론  
    기존 장바구니에 있는 목록과 없는 목록을 분리

**주요 로직**
1. 중복 장바구니 조회 및 담기
2. 남은 장바구니 담기

구현 및 결과  
테스트 코드

```java
@Test
public void cart_included_duplication_add_test() throws Exception  {
    // given
    // 유저 정보, saveDTO 가 주어지고 saveDTO는 일붜 중복된 데이터이다.
    int id = 1;
    CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
    saveDTO1.setOptionId(3);
    saveDTO1.setQuantity(5);

    CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
    saveDTO2.setOptionId(4);
    saveDTO2.setQuantity(5);

    List<CartRequest.SaveDTO> saveDTOs = new ArrayList<>(Arrays.asList(saveDTO1, saveDTO2));

    // when
    // 유저 정보를 조회하고 장바구니에 담게되면
    User user = userJPARepository.findById(id).orElse(null);
    Assertions.assertNotNull(user);
    List<Integer> ids =  saveDTOs.stream()
            .map(x-> new Integer(x.getOptionId()))
            .collect(Collectors.toList());
    Assertions.assertFalse(ids.isEmpty());

    // 중복된 장바구니 조회 및 담기
    System.out.println("중복된 장바구니 조회 및 담기");
    List<Cart> carts =  new ArrayList<>();
    cartJPARepository.findDuplicatedCartsByOptionsIds(user.getId(), ids).forEach(x->{
        System.out.println("option id : "+x.getId());
        CartRequest.SaveDTO saveDTO = saveDTOs.stream()
                .filter(y-> Objects.equals(y.getOptionId(), x.getOption().getId()))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(saveDTO);
        saveDTOs.remove(saveDTO);

        Assertions.assertNotNull(saveDTOs);
        Option findOption = optionJPARepository.findById(saveDTO.getOptionId()).orElse(null);
        Assertions.assertNotNull(findOption);
        x.update(x.getQuantity()+saveDTO.getQuantity(), findOption.getPrice());
        carts.add(x);
    });

    // 나머지 장바구니 담기
    System.out.println("나머지 장바구니 담기");
    saveDTOs.forEach(System.out::println);
    saveDTOs.forEach(x->{
        Option findOption = optionJPARepository.findById(x.getOptionId()).orElse(null);
        Assertions.assertNotNull(findOption);
        carts.add(newCart(user, findOption, x.getQuantity()));
    });
    System.out.println("장바구니 추가");
    cartJPARepository.saveAll(carts);

    // then
    // 정상적으로 담겨야 한다.
    System.out.println("담은 장바구니 검사");
    List<Cart> findCarts = cartJPARepository.findCartByUserId(user.getId());
    findCarts.forEach(x-> {
        Cart first = x;
        Option findOption = first.getOption();
        Product findProduct = findOption.getProduct();
        findProduct = Product.builder()
                .productName(findProduct.getProductName())
                .image(findProduct.getImage())
                .id(findProduct.getId())
                .price(findProduct.getPrice())
                .description(findProduct.getDescription())
                .build();
        findOption = Option.builder()
                .optionName(findOption.getOptionName())
                .product(findProduct)
                .price(findOption.getPrice())
                .id(findOption.getId())
                .build();
        try {
            System.out.println(x.getQuantity() + " : " + om.writeValueAsString(findOption));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    });
}
```

커스텀 쿼리문

```java
@Query("select c " +
        "from Cart c " +
        "join fetch c.option o " +
        "where c.user.id = :user_id and o.id in (:option_ids)")
List<Cart> findDuplicatedCartsByOptionsIds(@Param("user_id")int id, @Param("option_ids") List<Integer> ids);
```

Hibernate 결과

```java
Hibernate:
select
    user0_.id as id1_5_0_,
    user0_.email as email2_5_0_,
    user0_.password as password3_5_0_,
    user0_.roles as roles4_5_0_,
    user0_.username as username5_5_0_
from
    user_tb user0_
where
    user0_.id=?
중복된 장바구니 조회 및 담기
Hibernate:
select
    cart0_.id as id1_0_0_,
    option1_.id as id1_2_1_,
    cart0_.option_id as option_i4_0_0_,
    cart0_.price as price2_0_0_,
    cart0_.quantity as quantity3_0_0_,
    cart0_.user_id as user_id5_0_0_,
    option1_.option_name as option_n2_2_1_,
    option1_.price as price3_2_1_,
    option1_.product_id as product_4_2_1_
from
    cart_tb cart0_
inner join
    option_tb option1_
        on cart0_.option_id=option1_.id
where
    cart0_.user_id=?
    and (
        option1_.id in (
            ? , ?
        )
    )
option id : 3
나머지 장바구니 담기
CartRequest.SaveDTO(optionId=4, quantity=5)
Hibernate:
select
    option0_.id as id1_2_0_,
    option0_.option_name as option_n2_2_0_,
    option0_.price as price3_2_0_,
    option0_.product_id as product_4_2_0_
from
    option_tb option0_
where
    option0_.id=?
장바구니 추가
Hibernate:
insert
into
    cart_tb
    (id, option_id, price, quantity, user_id)
values
    (default, ?, ?, ?, ?)
담은 장바구니 검사
Hibernate:
update
    cart_tb
set
    option_id=?,
    price=?,
    quantity=?,
    user_id=?
where
    id=?
Hibernate:
select
    cart0_.id as id1_0_0_,
    option1_.id as id1_2_1_,
    product2_.id as id1_4_2_,
    cart0_.option_id as option_i4_0_0_,
    cart0_.price as price2_0_0_,
    cart0_.quantity as quantity3_0_0_,
    cart0_.user_id as user_id5_0_0_,
    option1_.option_name as option_n2_2_1_,
    option1_.price as price3_2_1_,
    option1_.product_id as product_4_2_1_,
    product2_.description as descript2_4_2_,
    product2_.image as image3_4_2_,
    product2_.price as price4_4_2_,
    product2_.product_name as product_5_4_2_
from
    cart_tb cart0_
inner join
    option_tb option1_
        on cart0_.option_id=option1_.id
inner join
    product_tb product2_
        on option1_.product_id=product2_.id
where
    cart0_.user_id=?
```

실행 결과

```java
5 : {"id":1,"product":{"id":1,"productName":"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전","description":"","image":"/images/1.jpg","price":1000},"optionName":"01. 슬라이딩 지퍼백 크리스마스에디션 4종","price":10000}
10 : {"id":2,"product":{"id":1,"productName":"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전","description":"","image":"/images/1.jpg","price":1000},"optionName":"02. 슬라이딩 지퍼백 플라워에디션 5종","price":10900}
5 : {"id":3,"product":{"id":1,"productName":"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전","description":"","image":"/images/1.jpg","price":1000},"optionName":"고무장갑 베이지 S(소형) 6팩","price":9900}
```

### 2. 장바구니 조회
given

유저 정보

when & then

유저 정보가 들어간 장바구니 조회  

구현 및 결과  
테스트 코드

```java
@Test
public void cart_without_user_findByUserId() throws JsonProcessingException {
    // given
    // userId 가 다음과 같고
    int id = 1;

    // when
    // 해당 유저가 가진 장바구니를 찾는다면
    List<Cart> cartListPs = cartJPARepository.findCartByUserId(id);

    // then
    // 해당 유저의 장바구니 목록을 출력한다.(유저의 정보가 빠진채로)
    System.out.println(om.writeValueAsString(cartListPs.get(0).getOption()));
    System.out.println(cartListPs.get(0).getId());
    System.out.println(cartListPs.get(0).getQuantity());
    System.out.println(cartListPs.get(0).getPrice());

    Assertions.assertEquals(cartListPs.get(0).getId(),1);
    Assertions.assertEquals(cartListPs.get(1).getId(),2);
    Assertions.assertDoesNotThrow(() -> {
        Assertions.assertNotNull(om.writeValueAsString(cartListPs.get(0).getOption()));
        Assertions.assertNotNull(om.writeValueAsString(cartListPs.get(0).getId()));
        Assertions.assertNotNull(om.writeValueAsString(cartListPs.get(0).getQuantity()));
        Assertions.assertNotNull(om.writeValueAsString(cartListPs.get(0).getPrice()));
    });
    Assertions.assertThrows(InvalidDefinitionException.class,() -> {
        om.writeValueAsString(cartListPs.get(0).getUser());
    });
}
```

테스트용 쿼리

```java
@Query("select c " +
        "from Cart c " +
        "join fetch c.option o " +
        "join fetch o.product " +
        "where c.user.id = :userId")
List<Cart> findCartByUserId(@Param("userId") int userId);
```

hibernate 결과

```java
select
    cart0_.id as id1_0_0_,
    option1_.id as id1_2_1_,
    product2_.id as id1_4_2_,
    cart0_.option_id as option_i4_0_0_,
    cart0_.price as price2_0_0_,
    cart0_.quantity as quantity3_0_0_,
    cart0_.user_id as user_id5_0_0_,
    option1_.option_name as option_n2_2_1_,
    option1_.price as price3_2_1_,
    option1_.product_id as product_4_2_1_,
    product2_.description as descript2_4_2_,
    product2_.image as image3_4_2_,
    product2_.price as price4_4_2_,
    product2_.product_name as product_5_4_2_
from
    cart_tb cart0_
inner join
    option_tb option1_
        on cart0_.option_id=option1_.id
inner join
    product_tb product2_
        on option1_.product_id=product2_.id
where
    cart0_.user_id=?
```

### 3. 장바구니 갱신

given

유저 정보, updateDTO(옵션 id, 수량)  

when & then  

장바구니에서 해당 옵션의 수량을 갱신  

구현 및 결과  

```java
@Test
public void cart_update_test() throws Exception  {
    // given
    // 유저 정보, saveDTO 가 주어지고
    int id = 1;
    CartRequest.UpdateDTO saveDTO1 = new CartRequest.UpdateDTO();
    saveDTO1.setCartId(1);
    saveDTO1.setQuantity(30);

    CartRequest.UpdateDTO saveDTO2 = new CartRequest.UpdateDTO();
    saveDTO2.setCartId(2);
    saveDTO2.setQuantity(20);

    List<CartRequest.UpdateDTO> saveDTOs = new ArrayList<>(Arrays.asList(saveDTO1, saveDTO2));

    // when
    // 유저 정보를 조회하고 장바구니에 담게되면
    User user = userJPARepository.findById(id).orElse(null);
    Assertions.assertNotNull(user);

    List<Integer> ids =  saveDTOs.stream()
            .map(x-> new Integer(x.getCartId()))
            .collect(Collectors.toList());
    Assertions.assertFalse(ids.isEmpty());

    // 중복된 장바구니 조회 및 담기
    System.out.println(ids);
    List<Cart>carts = new ArrayList<>();
    cartJPARepository.findDuplicatedCartsByOptionsIds(user.getId(), ids).forEach(x->{
        System.out.println("option id : "+x.getId());
        CartRequest.UpdateDTO saveDTO = saveDTOs.stream()
                .filter(y-> Objects.equals(y.getCartId(), x.getOption().getId()))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(saveDTO);

        saveDTOs.remove(saveDTO);

        Option findOption = optionJPARepository.findById(saveDTO.getCartId()).orElse(null);
        Assertions.assertNotNull(findOption);
        x.update(saveDTO.getQuantity(), findOption.getPrice()*saveDTO.getQuantity());
        carts.add(x);
    });

    System.out.println("장바구니 추가");
    if (saveDTOs.isEmpty()) {
        cartJPARepository.saveAll(carts);
    } else {
        System.out.println(saveDTOs);
        throw new Exception("기존에 없던 옵션을 추가하려고 했습니다.");
    }

    // then
    // 정상적으로 담겨야 한다.
    List<Cart> findCarts = cartJPARepository.findCartByUserId(user.getId());
    findCarts.forEach(x-> {
        Cart first = x;
        Option findOption = first.getOption();
        Product findProduct = findOption.getProduct();
        findProduct = Product.builder()
                .productName(findProduct.getProductName())
                .image(findProduct.getImage())
                .id(findProduct.getId())
                .price(findProduct.getPrice())
                .description(findProduct.getDescription())
                .build();
        findOption = Option.builder()
                .optionName(findOption.getOptionName())
                .product(findProduct)
                .price(findOption.getPrice())
                .id(findOption.getId())
                .build();
        try {
            System.out.println(x.getQuantity() + " : " + om.writeValueAsString(findOption));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    });
}
```

커스텀 쿼리문

```java
@Query("select c " +
        "from Cart c " +
        "join fetch c.option o " +
        "where c.user.id = :user_id and o.id in (:option_ids)")
List<Cart> findDuplicatedCartsByOptionsIds(@Param("user_id")int id, @Param("option_ids") List<Integer> ids);
```

```java
@Query("select c " +
        "from Cart c " +
        "join fetch c.option o " +
        "join fetch o.product " +
        "where c.user.id = :userId")
List<Cart> findCartByUserId(@Param("userId") int userId);
```

Hibernate 결과

```java
Hibernate:
select
    user0_.id as id1_5_0_,
    user0_.email as email2_5_0_,
    user0_.password as password3_5_0_,
    user0_.roles as roles4_5_0_,
    user0_.username as username5_5_0_
from
    user_tb user0_
where
    user0_.id=?
[1, 2]
Hibernate:
select
    cart0_.id as id1_0_0_,
    option1_.id as id1_2_1_,
    cart0_.option_id as option_i4_0_0_,
    cart0_.price as price2_0_0_,
    cart0_.quantity as quantity3_0_0_,
    cart0_.user_id as user_id5_0_0_,
    option1_.option_name as option_n2_2_1_,
    option1_.price as price3_2_1_,
    option1_.product_id as product_4_2_1_
from
    cart_tb cart0_
inner join
    option_tb option1_
        on cart0_.option_id=option1_.id
where
    cart0_.user_id=?
    and (
        option1_.id in (
            ? , ?
        )
    )
option id : 1
option id : 2
장바구니 추가
Hibernate:
update
    cart_tb
set
    option_id=?,
    price=?,
    quantity=?,
    user_id=?
where
    id=?
Hibernate:
update
    cart_tb
set
    option_id=?,
    price=?,
    quantity=?,
    user_id=?
where
    id=?
```

테스트 결과

```java
30 : {"id":1,"product":{"id":1,"productName":"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전","description":"","image":"/images/1.jpg","price":1000},"optionName":"01. 슬라이딩 지퍼백 크리스마스에디션 4종","price":10000}
20 : {"id":2,"product":{"id":1,"productName":"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전","description":"","image":"/images/1.jpg","price":1000},"optionName":"02. 슬라이딩 지퍼백 플라워에디션 5종","price":10900}
10 : {"id":3,"product":{"id":1,"productName":"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전","description":"","image":"/images/1.jpg","price":1000},"optionName":"고무장갑 베이지 S(소형) 6팩","price":9900}
```

4. 장바구니 비우기

given  

유저 정보  

when & then  

전체 데이터 삭제  

구현 및 결과  
테스트 코드  

```java
@Test
public void delete_by_userId_test() {
    // given
    // 유저 id 가 주어지고
    int id = 1;

    // 다른 장바구니에 간섭여부 확인을 위한 더미데이터
    int otherUserId = 2;
    User other = userJPARepository.save(newUser("hello"));
    List<Product> productListPS = productJPARepository.saveAll(productDummyList());
    List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
    List<Cart> cartListPS = newCarts(other,optionListPS);
    int otherCartCount = cartListPS.size();
    cartJPARepository.saveAll(cartListPS);
    em.clear();

    // when
    // 유저가 가진 모든 장바구니를 삭제하면
    User user = userJPARepository.findById(id).orElse(null);
    Assertions.assertNotNull(user);
    cartJPARepository.deleteByIds(
            cartJPARepository.findCartByUserId(user.getId())
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList())
    );

    // then
    // 장바구니에 남은 옵션이 없어야 한다.
    Assertions.assertEquals(cartJPARepository.findCartByUserId(user.getId()).size(), 0);
    other = userJPARepository.findById(otherUserId).orElse(null);
    Assertions.assertNotNull(other);
    Assertions.assertEquals(cartJPARepository.findCartByUserId(other.getId()).size(), otherCartCount);
}
```

사용된 커스텀 쿼리문

```java
@Modifying
@Query("delete from Cart c " +
        "where c.id in (:ids)")
void deleteByIds(@Param("ids") List<Integer> ids);
```

```java
@Query("select c " +
        "from Cart c " +
        "join fetch c.option o " +
        "join fetch o.product " +
        "where c.user.id = :userId")
List<Cart> findCartByUserId(@Param("userId") int userId);
```

Hibernate 결과

```java
장바구니 삭제
Hibernate:
select
    user0_.id as id1_5_0_,
    user0_.email as email2_5_0_,
    user0_.password as password3_5_0_,
    user0_.roles as roles4_5_0_,
    user0_.username as username5_5_0_
from
    user_tb user0_
where
    user0_.id=?
Hibernate:
select
    cart0_.id as id1_0_0_,
    option1_.id as id1_2_1_,
    product2_.id as id1_4_2_,
    cart0_.option_id as option_i4_0_0_,
    cart0_.price as price2_0_0_,
    cart0_.quantity as quantity3_0_0_,
    cart0_.user_id as user_id5_0_0_,
    option1_.option_name as option_n2_2_1_,
    option1_.price as price3_2_1_,
    option1_.product_id as product_4_2_1_,
    product2_.description as descript2_4_2_,
    product2_.image as image3_4_2_,
    product2_.price as price4_4_2_,
    product2_.product_name as product_5_4_2_
from
    cart_tb cart0_
inner join
    option_tb option1_
        on cart0_.option_id=option1_.id
inner join
    product_tb product2_
        on option1_.product_id=product2_.id
where
    cart0_.user_id=?
Hibernate:
delete
from
    cart_tb
where
    id in (
        ? , ? , ?
    )
Hibernate:
select
    cart0_.id as id1_0_0_,
    option1_.id as id1_2_1_,
    product2_.id as id1_4_2_,
    cart0_.option_id as option_i4_0_0_,
    cart0_.price as price2_0_0_,
    cart0_.quantity as quantity3_0_0_,
    cart0_.user_id as user_id5_0_0_,
    option1_.option_name as option_n2_2_1_,
    option1_.price as price3_2_1_,
    option1_.product_id as product_4_2_1_,
    product2_.description as descript2_4_2_,
    product2_.image as image3_4_2_,
    product2_.price as price4_4_2_,
    product2_.product_name as product_5_4_2_
from
    cart_tb cart0_
inner join
    option_tb option1_
        on cart0_.option_id=option1_.id
inner join
    product_tb product2_
        on option1_.product_id=product2_.id
where
    cart0_.user_id=?
Hibernate:
select
    user0_.id as id1_5_0_,
    user0_.email as email2_5_0_,
    user0_.password as password3_5_0_,
    user0_.roles as roles4_5_0_,
    user0_.username as username5_5_0_
from
    user_tb user0_
where
    user0_.id=?
Hibernate:
select
    cart0_.id as id1_0_0_,
    option1_.id as id1_2_1_,
    product2_.id as id1_4_2_,
    cart0_.option_id as option_i4_0_0_,
    cart0_.price as price2_0_0_,
    cart0_.quantity as quantity3_0_0_,
    cart0_.user_id as user_id5_0_0_,
    option1_.option_name as option_n2_2_1_,
    option1_.price as price3_2_1_,
    option1_.product_id as product_4_2_1_,
    product2_.description as descript2_4_2_,
    product2_.image as image3_4_2_,
    product2_.price as price4_4_2_,
    product2_.product_name as product_5_4_2_
from
    cart_tb cart0_
inner join
    option_tb option1_
        on cart0_.option_id=option1_.id
inner join
    product_tb product2_
        on option1_.product_id=product2_.id
where
    cart0_.user_id=?
```


## 주문

### 1. 결제

given  

유저 정보, 기존 카트 정보  

when & then  
결재 등록  

구현 및 결과  

```java
@Test
public void order_test() {
    // given
    // 사용자 정보와 카트 정보가 주어졌을 때
    User user = userJPARepository.save(newUser("hello"));
    List<Option> optionListPS = optionJPARepository.findAll();
    List<Cart> cartListPS = new ArrayList<>();
    if (optionListPS.size() > 1) {
        for(int i=0;i<2;i++) {
            cartListPS.add(newCart(user, optionListPS.get(i), 3));
        }
    }
    cartJPARepository.saveAll(cartListPS);
    em.clear();

    // when
    // 주문을 저장하고 기존의 장바구니를 비우게 되면
    int cartCountBeforeDelete = cartJPARepository.countByUserId(user.getId());
    int orderCountBeforeDelete = orderJPARepository.countByUserId(user.getId());
    int itemCountBeforeDelete = itemJPARepository.countByUserId(user.getId());
    Order order = orderJPARepository.save(newOrder(user));
    itemJPARepository.saveAll(cartListPS.stream()
            .map(x->newItem(x,order))
            .collect(Collectors.toList()));
    cartJPARepository.deleteByUserId(user.getId());

    // then
    // 비워진 장바구니와 늘어난 주문과, 아이템을 볼 수 있다.
    int cartCountNumAfterDelete = cartJPARepository.countByUserId(user.getId());
    int orderCountAfterDelete = orderJPARepository.countByUserId(user.getId());
    int itemCountAfterDelete = itemJPARepository.countByUserId(user.getId());
    System.out.println("cart : " + cartCountBeforeDelete + "->" + cartCountNumAfterDelete);
    System.out.println("order : " + orderCountBeforeDelete + "->" + orderCountAfterDelete);
    System.out.println("item : " + itemCountBeforeDelete + "->" + itemCountAfterDelete);
}
```

사용된 쿼리문

```java
@Query("select count(*)" +
        "from Item i " +
        "where i.order.user.id = :user_id")
int countByUserId(@Param("user_id") int user_id);
```

```java
@Query("select count(*)" +
        "from Order o " +
        "where o.user.id = :user_id")
int countByUserId(@Param("user_id") int user_id);
```

```java
@Modifying
@Query("delete from Cart c " +
        "where c.user.id in :id")
void deleteByUserId(@Param("id") int id);
```

Hibernate 결과

```java
Hibernate:
insert
into
    user_tb
    (id, email, password, roles, username)
values
    (default, ?, ?, ?, ?)
Hibernate:
select
    option0_.id as id1_2_,
    option0_.option_name as option_n2_2_,
    option0_.price as price3_2_,
    option0_.product_id as product_4_2_
from
    option_tb option0_
Hibernate:
insert
into
    cart_tb
    (id, option_id, price, quantity, user_id)
values
    (default, ?, ?, ?, ?)
Hibernate:
insert
into
    cart_tb
    (id, option_id, price, quantity, user_id)
values
    (default, ?, ?, ?, ?)
Hibernate:
select
    count(*) as col_0_0_
from
    cart_tb cart0_
where
    cart0_.user_id=?
Hibernate:
select
    count(*) as col_0_0_
from
    order_tb order0_
where
    order0_.user_id=?
Hibernate:
select
    count(*) as col_0_0_
from
    item_tb item0_ cross
join
    order_tb order1_
where
    item0_.order_id=order1_.id
    and order1_.user_id=?
Hibernate:
insert
into
    order_tb
    (id, user_id)
values
    (default, ?)
Hibernate:
insert
into
    item_tb
    (id, option_id, order_id, price, quantity)
values
    (default, ?, ?, ?, ?)
Hibernate:
insert
into
    item_tb
    (id, option_id, order_id, price, quantity)
values
    (default, ?, ?, ?, ?)
Hibernate:
delete
from
    cart_tb
where
    user_id in (
        ?
    )
Hibernate:
select
    count(*) as col_0_0_
from
    cart_tb cart0_
where
    cart0_.user_id=?
Hibernate:
select
    count(*) as col_0_0_
from
    order_tb order0_
where
    order0_.user_id=?
Hibernate:
select
    count(*) as col_0_0_
from
    item_tb item0_ cross
join
    order_tb order1_
where
    item0_.order_id=order1_.id
    and order1_.user_id=?
```

실행 결과

```java
cart : 2->0
order : 0->1
item : 0->2
```

2. 주문 결과 확인

given

유저 정보, 주문 인덱스  
문제점이 index를 통해서 해당 주문을 가져오는건데  
jpql은 from 에 subquery가 동작하지 않는다.  
또한 from 으로 가져오는 테이블을 index에 적용할 수 없다 . 
그렇다면 차선책이 유저기반으로 전부 찾아오고 인덱싱해서 추출  

when & then  

find by userId and id

구현 및 결과

테스트 코드

```java
@Test
public void findOrderItemByUser_test() throws Exception {
    // given
    // 유저 정보와 주문 인덱스를 통해서
    int id = 1;
    int orderIndex = 2;

    // 사용자의 주문을 하나 더 추가
    User prevUser = userJPARepository.findById(id).orElse(null);
    Assertions.assertNotNull(prevUser);
    List<Option> optionListPS2 = optionJPARepository.findAll();
    List<Cart> cartListPS2 = new ArrayList<>();
    if (optionListPS2.size() > 8) {
        for(int i=4;i<7;i++) {
            cartListPS2.add(newCart(prevUser, optionListPS2.get(i), 40));
        }
    }
    cartJPARepository.saveAll(cartListPS2);
    orderJPARepository.save(newOrder(prevUser));
    em.clear();

    // when
    // 주문 아이템을 최근 순서로 검색하면
    User user = userJPARepository.findById(id).orElse(null);
    Assertions.assertNotNull(user);
    List<Order> orders = orderJPARepository.findByUserId(user.getId());
    System.out.println("orders size : " + orders.size());
    List<Item> item = null;
    if (orderIndex > 0 && orders.size() > orderIndex-1) {
        int orderId = orders.indexOf(orders.size() - orderIndex);
            item = itemJPARepository.findByOrderId(orderId);
    } else {
        throw new Exception("찾을 없는 인덱스 입니다.");
    }

    // then
    // 해당 주문들을 가져온 것을 알 수 있다.
    Assertions.assertNotNull(item);
    item.forEach(x->{
        System.out.println("order id : " + x.getOrder().getId() + ", option id : " + x.getOption().getId());
    });
}
```

사용된 쿼리문

```java
@Query("select count(*)" +
        "from Order o " +
        "where o.user.id = :user_id")
int countByUserId(@Param("user_id") int user_id);
```

```java
@Query("select i " +
        "from Item i " +
        "where i.order.id = :order_id")
List<Item> findByOrderId(@Param("order_id") int order_id);
```

Hibernate 결과

```java
Hibernate:
select
    user0_.id as id1_5_0_,
    user0_.email as email2_5_0_,
    user0_.password as password3_5_0_,
    user0_.roles as roles4_5_0_,
    user0_.username as username5_5_0_
from
    user_tb user0_
where
    user0_.id=?
Hibernate:
select
    option0_.id as id1_2_,
    option0_.option_name as option_n2_2_,
    option0_.price as price3_2_,
    option0_.product_id as product_4_2_
from
    option_tb option0_
Hibernate:
insert
into
    cart_tb
    (id, option_id, price, quantity, user_id)
values
    (default, ?, ?, ?, ?)
Hibernate:
insert
into
    cart_tb
    (id, option_id, price, quantity, user_id)
values
    (default, ?, ?, ?, ?)
Hibernate:
insert
into
    cart_tb
    (id, option_id, price, quantity, user_id)
values
    (default, ?, ?, ?, ?)
Hibernate:
insert
into
    order_tb
    (id, user_id)
values
    (default, ?)
Hibernate:
select
    user0_.id as id1_5_0_,
    user0_.email as email2_5_0_,
    user0_.password as password3_5_0_,
    user0_.roles as roles4_5_0_,
    user0_.username as username5_5_0_
from
    user_tb user0_
where
    user0_.id=?
Hibernate:
select
    order0_.id as id1_3_0_,
    user1_.id as id1_5_1_,
    order0_.user_id as user_id2_3_0_,
    user1_.email as email2_5_1_,
    user1_.password as password3_5_1_,
    user1_.roles as roles4_5_1_,
    user1_.username as username5_5_1_
from
    order_tb order0_
inner join
    user_tb user1_
        on order0_.user_id=user1_.id
where
    order0_.user_id=?
orders size : 2
Hibernate:
select
    item0_.id as id1_1_,
    item0_.option_id as option_i4_1_,
    item0_.order_id as order_id5_1_,
    item0_.price as price2_1_,
    item0_.quantity as quantity3_1_
from
    item_tb item0_
where
    item0_.order_id=?
```


### 제품

## 1. 전체 상품 목록 조회(페이징 구현)

given

page, size가 주어지면

when & then  

find all

테스트 코드

```java
@Test
public void product_findAll_test() throws JsonProcessingException {
    // given
    int page = 0;
    int size = 9;

    // when
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Product> productPG = productJPARepository.findAll(pageRequest);
    String responseBody = om.writeValueAsString(productPG);
    System.out.println("테스트 : "+responseBody);

    // then
    Assertions.assertThat(productPG.getTotalPages()).isEqualTo(2);
    Assertions.assertThat(productPG.getSize()).isEqualTo(9);
    Assertions.assertThat(productPG.getNumber()).isEqualTo(0);
    Assertions.assertThat(productPG.getTotalElements()).isEqualTo(15);
    Assertions.assertThat(productPG.isFirst()).isEqualTo(true);
    Assertions.assertThat(productPG.getContent().get(0).getId()).isEqualTo(1);
    Assertions.assertThat(productPG.getContent().get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    Assertions.assertThat(productPG.getContent().get(0).getDescription()).isEqualTo("");
    Assertions.assertThat(productPG.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
    Assertions.assertThat(productPG.getContent().get(0).getPrice()).isEqualTo(1000);
}
```

Hibernate 결과

```java
Hibernate:
select
    product0_.id as id1_4_,
    product0_.description as descript2_4_,
    product0_.image as image3_4_,
    product0_.price as price4_4_,
    product0_.product_name as product_5_4_
from
    product_tb product0_ limit ?
Hibernate:
select
    count(product0_.id) as col_0_0_
from
    product_tb product0_
```

## 2. 개별 상품 상세 조회

given

제품 인덱스

when & then

find by id

테스트 구현

```java
@Test
public void product_findById_and_option_findByProductId_lazy_test() throws JsonProcessingException {
    // given
    int id = 1;

    // when
    Product productPS = productJPARepository.findById(id).orElseThrow(
            () -> new RuntimeException("상품을 찾을 수 없습니다")
    );

    // product 상품은 영속화 되어 있어서, 아래에서 조인해서 데이터를 가져오지 않아도 된다.
    List<Option> optionListPS = optionJPARepository.findByProductId(id); // Lazy

    String responseBody1 = om.writeValueAsString(productPS);
    String responseBody2 = om.writeValueAsString(optionListPS);

    System.out.println("테스트 : "+ PrintUtils.getPrettyString(responseBody1));
    System.out.println("테스트 : "+ PrintUtils.getPrettyString(responseBody2));

    // then
}
```

Hibernate 결과

```java
Hibernate:
select
    product0_.id as id1_4_0_,
    product0_.description as descript2_4_0_,
    product0_.image as image3_4_0_,
    product0_.price as price4_4_0_,
    product0_.product_name as product_5_4_0_
from
    product_tb product0_
where
    product0_.id=?
Hibernate:
select
    option0_.id as id1_2_,
    option0_.option_name as option_n2_2_,
    option0_.price as price3_2_,
    option0_.product_id as product_4_2_
from
    option_tb option0_
left outer join
    product_tb product1_
        on option0_.product_id=product1_.id
where
    product1_.id=?
```

사용된 쿼리

```java
@Query("select o from Option o " +
        "join fetch o.product " +
        "where o.product.id = :productId")
List<Option> mFindByProductId(@Param("productId") int productId);
```

Hibernate 결과

```java
Hibernate:
select
    option0_.id as id1_2_0_,
    product1_.id as id1_4_1_,
    option0_.option_name as option_n2_2_0_,
    option0_.price as price3_2_0_,
    option0_.product_id as product_4_2_0_,
    product1_.description as descript2_4_1_,
    product1_.image as image3_4_1_,
    product1_.price as price4_4_1_,
    product1_.product_name as product_5_4_1_
from
    option_tb option0_
inner join
    product_tb product1_
        on option0_.product_id=product1_.id
where
    option0_.product_id=?
```
</details>

<details>
<summary>3. 과제 상세 검사</summary>

>- 레포지토리 단위테스트가 구현되었는가?  
>   - 각 컨트롤러의 요청에 매칭될 수 있도록 레포지토리에서 service를 고려하여 테스트 하였다.
>- 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
>   -  각각의 동작을 구현하기 위해 생성되었지만, 사용하는 레포지토리 메서드들은 완전히 독립되지는 못했다.
>- Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
>   - beforeEach를 통해서 em.clear()를 수행했고, 추가적으로 각 given 단계에서 생성하는 더미데이터 생성을 마친 이후에도 em.clear()를 추가했다.
>- 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
>   - 모든 왜래키들은 lazy fetch로 설정했고, join fecth 구문을 활용하여 한방쿼리를 작성할 수 있도록 했다.
>   - 또한 추가적인 select 문을 해결할 수 있었고, jpql 과 native query를 사용하여 최적화를 할 수 있도록 했다.
>- BDD 패턴으로 구현되었는가? given, when, then
>   - given(어떤 정보가 주어졌을 때 이를 기반으로)
>   - when(동작을 수행하면)
>   - then(동작이 적절하게 의도한데로 수행됨을 확인할 수 있다.)
>   - 와 같은 하나의 문장형으로 BDD를 구현했다.
</details>

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
>- 모든 요청과 응답이 json으로 처리되어 있는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_4주차 과제 

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

## **과제명**
1. 컨트롤러 단위 테스트를 작성한뒤 소스코드를 업로드하시오.
>- 각 컨트롤러에서 에러의 경우가 더 있을거라 생각하여 각 라인별로 검토하여 예외 처리 추가
2. stub을 구현하시오.
>- @MockBean을 통하여 mock객체를 생성하여 형태만 유지

과제 상세 검사
>- 컨트롤러 단위테스트가 구현되었는가?
>   - 각 단위테스트가 구현되었다. 하지만 각 컨트롤러들이 로그인한 유저의 정보를 참조해야하는 문제가 발생했는데 null값이 넘어가는 문제가 발생하여 커스텀 mockuser를 넘겼지만 시간소요가 큰 문제로 생략
>- Mockito를 이용하여 stub을 구현하였는가?
>   - 각 컨트롤러 테스트 단에서 stub을 구현함
>- 인증이 필요한 컨트롤러를 테스트할 수 있는가?
>   - 인증이 필요한 컨트롤러는 @WithMockUser를 통해서 인증을 가정하여 적용됨. 하지만 인증된 유저의 정보는 가져올 수 없음
>- 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
>   - 컨트롤러상에서 논리적 결함이 다수 발견됨. 백엔드 단에 전달되는 데이터를 전부 신뢰하고 있는 상황. 그러므로 전달되는 데이터의 유효성 검증이 전무했음. 전달된 데이터의 신뢰성을 검토하는 로직을 추가하였고 각 응답 상태 코드를 처리함. Test 에서 에러에 대한 상태확인
>- 모든 요청과 응답이 json으로 처리되어 있는가?
>   - postman과 test를 통해서 모든 응답이 json으로 처리되었음을 확인함.


# 5주차

카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제
</br>
</br>

## **과제명**
```
코드 리팩토링
```

## **과제 설명**
```
카카오 쇼핑 프로젝트 전체 코드를 리팩토링한다
 - AOP로 유효성검사 적용하기
 - 구현하기
 - 장바구니 담GlobalExceptionHanlder 기 -> 예외 처리하기
 - 장바구니 수정(주문하기) -> 예외처리하기
 - 결재하기 기능 구현 (장바구니가 꼭 초기화 되어야함)
 - 주문결과 확인 기능 구현
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- AOP가 적용되었는가?
>- GlobalExceptionHandler가 적용되었는가?
>- 장바구니 담기시 모든 예외가 처리 완료되었는가?
>- 장바구니 수정시 모든 예외가 처리 완료되었는가?
>- 결재하기와 주문결과 확인 코드가 완료되었는가?
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
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 통합테스트가 구현되었는가?
>- API문서가 구현되었는가?
>- 배포가 정상적으로 되었는가?
>- 프로그램이 정상 작동되고 있는가?
>- API 문서에 실패 예시가 작성되었는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_6주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
