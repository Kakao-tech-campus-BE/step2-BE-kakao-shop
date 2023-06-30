# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

# 1주차

카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제
</br>
</br>

1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
   - 회원가입 시 패스워드를 그대로 DB에 저장하지 않고 해싱이나 다른 보안적인 부분을 고려해서 넣는 것이 좋아보임
   - 옵션 선택할 때나 장바구니에서 상품을 조회할 때나 상품 삭제가 구현이 안되어있음
   - 회원들은 정말 이 상품이 구매해도 괜찮은 상품인가? 를 중점으로 상품을 구매할 것인데 상품에 대한 설명이나 리뷰가 없음
3. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)

<details>
<summary>(1) 전체 상품 목록 조회</summary>
<div>
   
### HTTP 메서드 선정

클라이언트측에서 서버측으로 전송하는 데이터가 없다. 그러므로 HTTP GET 요청을 한다.

HTTP Method : GET </br>
Local URL : http://localhost:8080/products </br>

### JSON 응답 및 시나리오 분석
JSON 응답을 살펴보면 Response Body에 id, productName, description, image, price를 배열 형식으로 담아서 응답하고 있다. 화면을 살펴보면 상품 이름, 가격, 이미지는 화면상에 명시하여 필요하지만, 설명(description)은 그렇지 않아 설명 속성의 필요성은 보이지 않는다. </br>

→ 필요한 테이블 : Product(상품) </br>

### 에러 처리

HTTP POST 메서드 이용 시,

```java
{
    "success": false,
    "response": null,
    "error": {
        "message": "Request method 'POST' not supported",
        "status": 500
    }
}
```

status 500, 즉 서버측의 잘못된 응답을 나타내는 에러를 의미한다.

하지만 나는 클라이언트측에서 GET이 아닌 POST 메서드를 사용하였는데 왜 500인지?

**내 생각에는 HTTP error 405가 맞는 에러인 거 같다.**

> **HTTP 405 에러란?**
”HTTP 405 오류는 **웹 서버에서 요청된 URL에 대해 HTTP 메서드를 허용하지 않을 때 발생합니다.”**
> 

([https://ko.wikipedia.org/wiki/HTTP_상태_코드#:~:text=405(허용되지 않는 메소드)%3A 요청에 지정된 방법을 사용할 수 없다. 예를 들어 POST 방식으로 요청을 받는 서버에 GET 요청을 보내는 경우%2C 또는 읽기 전용 리소스에 PUT 요청을 보내는 경우에 이 코드를 제공한다](https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C#:~:text=405(%ED%97%88%EC%9A%A9%EB%90%98%EC%A7%80%20%EC%95%8A%EB%8A%94%20%EB%A9%94%EC%86%8C%EB%93%9C)%3A%20%EC%9A%94%EC%B2%AD%EC%97%90%20%EC%A7%80%EC%A0%95%EB%90%9C%20%EB%B0%A9%EB%B2%95%EC%9D%84%20%EC%82%AC%EC%9A%A9%ED%95%A0%20%EC%88%98%20%EC%97%86%EB%8B%A4.%20%EC%98%88%EB%A5%BC%20%EB%93%A4%EC%96%B4%20POST%20%EB%B0%A9%EC%8B%9D%EC%9C%BC%EB%A1%9C%20%EC%9A%94%EC%B2%AD%EC%9D%84%20%EB%B0%9B%EB%8A%94%20%EC%84%9C%EB%B2%84%EC%97%90%20GET%20%EC%9A%94%EC%B2%AD%EC%9D%84%20%EB%B3%B4%EB%82%B4%EB%8A%94%20%EA%B2%BD%EC%9A%B0%2C%20%EB%98%90%EB%8A%94%20%EC%9D%BD%EA%B8%B0%20%EC%A0%84%EC%9A%A9%20%EB%A6%AC%EC%86%8C%EC%8A%A4%EC%97%90%20PUT%20%EC%9A%94%EC%B2%AD%EC%9D%84%20%EB%B3%B4%EB%82%B4%EB%8A%94%20%EA%B2%BD%EC%9A%B0%EC%97%90%20%EC%9D%B4%20%EC%BD%94%EB%93%9C%EB%A5%BC%20%EC%A0%9C%EA%B3%B5%ED%95%9C%EB%8B%A4).)

### Param을 사용하여 페이지 조회

기존 로컬 URL에 Param으로 page=0과 page=1을 줘보자.

Local URL : http://localhost:8080/products?page=0 or 1

Param이 page=0일 때는 디폴트 전체 상품 목록 조회 페이지와 같다. 하지만 page=1일 때는 다르다.

게시물 10번부터 조회되는 것을 알 수 있다. 이로써 게시물은 한 페이지당 최대 개수를 지정하여 SELECT해야할 것 같다.

(1단계 때 배운 limit과 offset을 이용해 구현하면 될 거 같다.)
</div>
</details>

<details>
<summary>(2) 개별 상품 상세 조회</summary>
<div>

### HTTP 메서드 선정

개별 상품 상세 조회 페이지는 클라이언트측 요청의 종류가 두 가지가 있다.

1. 전체 상품 목록 조회 페이지에서 개별 상품을 클릭하면 이 페이지로 이동하게 되는데, 이때 이 페이지는 서버측에 따로 보내는 데이터 없이 개별 상품에 대한 데이터를 요청할 것이다. 
    
    → HTTP GET 요청을 한다.
    
    HTTP Method : GET
    Local URL : http://localhost:8080/products/1(1은 개별 상품의 id)
    
2. 회원이 상품의 옵션과 개수를 선택하여 장바구니 담기 버튼을 클릭하면 클라이언트측은 장바구니에 담겨진 데이터를 서버측에 전송하게 되고 서버측은 이를 DB에 저장한다.
    
    → HTTP POST 요청을 한다.
    
    HTTP Method : POST
    Local URL : http://localhost:8080/carts/add

### JSON 응답 및 시나리오 분석
SON 응답을 살펴보면 상품의 id, productName, image, price와(descriptions과 startCount는 무시한다.) 옵션 id, optionName, optionPrice를 응답한다. 상품의 기본 가격(price)와 옵션 가격(optionPrice)는 헷갈리지 않게 구분한다.

→ 필요한 테이블 : Product(상품), Option(옵션)

→ 옵션 테이블에 **id, optionName, optionPrice**를 추가한다.

(위에서 언급한 것을 고려해보면, 서버측 JSON 응답에는 totalPrice에 대한 응답이 없으므로 클라이언트측에서 데이터를 만들어내는 것 같다.)

2. 클라이언트측에서 장바구니 담기 버튼을 통해 POST 요청을 보냄
    
    이때 클라이언트측 POST 요청에는 장바구니에 담겨질 상품의 정보뿐만 아니라 회원에 대한 JWT(JSON WEB TOKEN) 정보도 포함돼야한다. 이유는 정말 간단하다. 이 회원이 누군지 알아야 그 회원의 장바구니에 상품을 담을 수 있기 때문이다. 
    
    (참고로 클라이언트측에서 Authorization이라는 변수명으로 HTTP header에 담아서 보낼 것이다. 서버측은 이를 파싱해서 해당 회원을 찾아내야 한다.)
    
    ~~(회원 A의 장바구니 목록을 회원 B에게 담을 수는 없다..)~~
    
    서버측에 로그인 정보를 담아 로그인한다.
   그럼 서버측에서 JWT 정보를 보내는데, 아래와 같다.

Bearer ey…Asg(원래 길이는 더 길다. 256바이트였나.. 나름 보안상(?)의 이유로 축약함)

이것을 이용해서 회원에 대한 인가를 할 것이다.

클라이언트측에서 해당 JWT 정보를 HTTP header에 담고 서버측에 장바구니에 담겨진 데이터를 아래의 형식으로 전달하면

```json
[
  {
    "optionId":1,
    "quantity":5
  },
  {
    "optionId":2,
    "quantity":5
  }
]
```

아래의 형식처럼 응답이 온다.

```json
{
    "success": true,
    "response": null,
    "error": null
}
```

서버측에서는 옵션 아이디와, 개수를 장바구니(Cart)에 저장할 것이다.

→ 필요한 테이블 : Cart(장바구니)

→ Cart 테이블에 **optionId, quantity** 속성을 추가한다.
</div>
</details>

5. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
6. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.

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
