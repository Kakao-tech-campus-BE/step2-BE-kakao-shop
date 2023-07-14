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
>- 모든 요청과 응답이 json으로 처리되어 있는가?
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
>- 예외에 대한 처리를 ControllerAdvice or RestControllerAdvice로 구현하였는가?
>- Validation 라이브러리를 사용하여 유효성 검사가 되었는가?
>- 테스트는 격리되어 있는가?
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
