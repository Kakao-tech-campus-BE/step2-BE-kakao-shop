# 1주차 과제

</br>
</br>

## 부족해 보이는 기능에 대한 생각
- 회원가입 시 패스워드를 그대로 DB에 저장하지 않고 해싱이나 다른 보안적인 부분을 고려해서 넣는 것이 좋아보임
- 옵션 선택할 때나 장바구니에서 상품을 조회할 때나 상품 삭제가 구현이 안되어있음
- 회원들은 정말 이 상품이 구매해도 괜찮은 상품인가? 를 중점으로 상품을 구매할 것인데 상품에 대한 설명이나 리뷰가 없음

## API 주소 매칭과 JSON 응답 분석

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

### 에러 처리

개별 상품을 클릭 해 개별 상품 상세 조회 페이지로 이동한다고 생각해보자.  아래 URL에 GET 요청을 보낼 것이다.

Local URL : http://localhost:8080/products/1

당연히 해당 URL에 POST 요청을 보내면 405 에러가 나와야 한다. 하지만 그렇지 않다.

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "Request method 'POST' not supported",
        "status": 500
    }
}
```

아까와 똑같은 포맷의 에러가 발생한다. 여전히 405로 고치고 싶다.

그리고 장바구니 담기를 통해 POST 요청하는 것을 생각해보자. 토큰 정보가 유효하지 않다면 어떻게 될까?(토큰 정보를 유효하지 않도록 값을 수정했다.)

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "인증되지 않았습니다",
        "status": 401
    }
}
```

401 에러 코드가 발생한다. 이는 적절한 에러를 대입한 것으로 보인다.
([https://ko.wikipedia.org/wiki/HTTP_상태_코드#:~:text=401(권한 없음)%3A 이 요청은 인증이 필요하다. 서버는 로그인이 필요한 페이지에 대해 이 요청을 제공할 수 있다. 상태 코드 이름이 권한 없음(Unauthorized)으로 되어 있지만 실제 뜻은 인증 안됨(Unauthenticated)에 더 가깝다.[2]](https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C#:~:text=401(%EA%B6%8C%ED%95%9C%20%EC%97%86%EC%9D%8C)%3A%20%EC%9D%B4%20%EC%9A%94%EC%B2%AD%EC%9D%80%20%EC%9D%B8%EC%A6%9D%EC%9D%B4%20%ED%95%84%EC%9A%94%ED%95%98%EB%8B%A4.%20%EC%84%9C%EB%B2%84%EB%8A%94%20%EB%A1%9C%EA%B7%B8%EC%9D%B8%EC%9D%B4%20%ED%95%84%EC%9A%94%ED%95%9C%20%ED%8E%98%EC%9D%B4%EC%A7%80%EC%97%90%20%EB%8C%80%ED%95%B4%20%EC%9D%B4%20%EC%9A%94%EC%B2%AD%EC%9D%84%20%EC%A0%9C%EA%B3%B5%ED%95%A0%20%EC%88%98%20%EC%9E%88%EB%8B%A4.%20%EC%83%81%ED%83%9C%20%EC%BD%94%EB%93%9C%20%EC%9D%B4%EB%A6%84%EC%9D%B4%20%EA%B6%8C%ED%95%9C%20%EC%97%86%EC%9D%8C(Unauthorized)%EC%9C%BC%EB%A1%9C%20%EB%90%98%EC%96%B4%20%EC%9E%88%EC%A7%80%EB%A7%8C%20%EC%8B%A4%EC%A0%9C%20%EB%9C%BB%EC%9D%80%20%EC%9D%B8%EC%A6%9D%20%EC%95%88%EB%90%A8(Unauthenticated)%EC%97%90%20%EB%8D%94%20%EA%B0%80%EA%B9%9D%EB%8B%A4.%5B2%5D))

다음에는 GET 요청으로 상품 정보와 JWT 정보를 담아서 보내봤다. 500 에러가 뜨지만, 405로 고치고 싶다.

GET 요청으로 상품 정보만 비워서 보내봤다. 역시나 500 에러가 뜬다.

다음으로 JWT까지 비워서 보내봤다. 401 에러가 뜬다. 이는 적절하다.

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "인증되지 않았습니다",
        "status": 401
    }
}
```

존재하지 않는 옵션의 상품을 장바구니에 담기도 해봤다.

```json
[
  {
	  "optionId":55,  //존재하지 않는 옵션
    "quantity":5
  }
]
```

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "해당 옵션을 찾을 수 없습니다 : 55",
        "status": 404
    }
}
```

서버측에서 클라이언트측이 요청한 페이지를 찾을 수 없다는 것이다. 당연히 존재하지 않는 옵션에 대한 POST 요청을 했으므로 404 에러 처리는 적절하다.
당연히 존재하지 않는 옵션에 대한 POST 요청을 했으므로 404 에러 처리는 적절하다.

### 동일한 상품 장바구니 담기에 대한 고찰

동일한 상품 아이디를 가진 상품을 장바구니 담기를 시도해봤다.

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "장바구니 담기 중에 오류가 발생했습니다 : could not execute statement; SQL [n/a]; constraint [\"PUBLIC.UK_CART_OPTION_USER_INDEX_4 ON PUBLIC.CART_TB(USER_ID NULLS FIRST, OPTION_ID NULLS FIRST) VALUES ( /* key:5 */ 1, 4)\"; SQL statement:\ninsert into cart_tb (id, option_id, price, quantity, user_id) values (default, ?, ?, ?, ?) [23505-214]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
        "status": 500
    }
}
```

이미 장바구니에 같은 상품이 존재해서 발생하는 오류이다.

([https://ko.wikipedia.org/wiki/HTTP_상태_코드#:~:text=500(내부 서버 오류)%3A 서버에 오류가 발생하여 요청을 수행할 수 없다](https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C#:~:text=500(%EB%82%B4%EB%B6%80%20%EC%84%9C%EB%B2%84%20%EC%98%A4%EB%A5%98)%3A%20%EC%84%9C%EB%B2%84%EC%97%90%20%EC%98%A4%EB%A5%98%EA%B0%80%20%EB%B0%9C%EC%83%9D%ED%95%98%EC%97%AC%20%EC%9A%94%EC%B2%AD%EC%9D%84%20%EC%88%98%ED%96%89%ED%95%A0%20%EC%88%98%20%EC%97%86%EB%8B%A4).)

보통적인 생각으로 같은 상품을 장바구니에 담기하면 그 상품의 개수가 증가해야할 것이다. 하지만 위 에러는 동일한 상품 추가에 대한 에러가 발생하는데 이는 같은 상품 담기를 하게 되면 상품 개수 증가가 될 수 있도록 수정해야한다.

</div>
</details>


<details>
<summary>(3) 회원가입</summary>
<div>
	
### HTTP 메서드 선정

클라이언트측에서 사용자가 가입을 위해 작성한 정보를 서버측으로 전송한다.

HTTP Method : POST

Local URL : http://local:8080/join

### JSON 응답 및 시나리오 분석

- JSON 응답
    
    사용자가 원하는 정보를 입력 후 회원가입 버튼을 클릭
    
    ```json
    {
      "username":"MinseokGo",
      "email":"rhalstjr1999@naver.com",
      "password":"@@alstjr12"
    }
    ```
    
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }  //회원가입 완료
    ```
    

### 에러 처리
중복된 이메일로 가입 시도 시 JSON 응답

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "동일한 이메일이 존재합니다 : rhalstjr1999@naver.com",
        "status": 400
    }
}
```

400 에러 처리보다는 409 에러 처리가 더 적합해 보인다.

([https://mangoday.tistory.com/137#:~:text=409는 Conflict. "이 응답은 요청이 현재 서버의 상태와 충돌될 때 보냅니다"](https://mangoday.tistory.com/137#:~:text=409%EB%8A%94%20Conflict.%20%22%EC%9D%B4%20%EC%9D%91%EB%8B%B5%EC%9D%80%20%EC%9A%94%EC%B2%AD%EC%9D%B4%20%ED%98%84%EC%9E%AC%20%EC%84%9C%EB%B2%84%EC%9D%98%20%EC%83%81%ED%83%9C%EC%99%80%20%EC%B6%A9%EB%8F%8C%EB%90%A0%20%EB%95%8C%20%EB%B3%B4%EB%83%85%EB%8B%88%EB%8B%A4%22))

([https://deveric.tistory.com/62#:~:text=409 Conflict는 리소스의 충돌을 의미하는 상태코드입니다. ID 중복이라는 것은 결국 ID라는 PK 자원을 점유한 것에 대한 충돌이기 때문에 이 상태코드가 가장 적합하다고 생각하여 409 상태코드를 반영하기로 했습니다](https://deveric.tistory.com/62#:~:text=409%20Conflict%EB%8A%94%20%EB%A6%AC%EC%86%8C%EC%8A%A4%EC%9D%98%20%EC%B6%A9%EB%8F%8C%EC%9D%84%20%EC%9D%98%EB%AF%B8%ED%95%98%EB%8A%94%20%EC%83%81%ED%83%9C%EC%BD%94%EB%93%9C%EC%9E%85%EB%8B%88%EB%8B%A4.%20ID%20%EC%A4%91%EB%B3%B5%EC%9D%B4%EB%9D%BC%EB%8A%94%20%EA%B2%83%EC%9D%80%20%EA%B2%B0%EA%B5%AD%20ID%EB%9D%BC%EB%8A%94%20PK%20%EC%9E%90%EC%9B%90%EC%9D%84%20%EC%A0%90%EC%9C%A0%ED%95%9C%20%EA%B2%83%EC%97%90%20%EB%8C%80%ED%95%9C%20%EC%B6%A9%EB%8F%8C%EC%9D%B4%EA%B8%B0%20%EB%95%8C%EB%AC%B8%EC%97%90%20%EC%9D%B4%20%EC%83%81%ED%83%9C%EC%BD%94%EB%93%9C%EA%B0%80%20%EA%B0%80%EC%9E%A5%20%EC%A0%81%ED%95%A9%ED%95%98%EB%8B%A4%EA%B3%A0%20%EC%83%9D%EA%B0%81%ED%95%98%EC%97%AC%20409%20%EC%83%81%ED%83%9C%EC%BD%94%EB%93%9C%EB%A5%BC%20%EB%B0%98%EC%98%81%ED%95%98%EA%B8%B0%EB%A1%9C%20%ED%96%88%EC%8A%B5%EB%8B%88%EB%8B%A4).)

그리고 POST 요청 대신 GET 요청을 해보았다.

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "Request method 'GET' not supported",
        "status": 500
    }
}
```

서버측의 에러인 500 상태 코드 대신 클라이언트측의 잘못된 HTTP 메서드 요청인 405 상태 코드가 적합해보인다.

이메일 형식이 아닌 형식을 POST 해보았다.

(예를들면 

rhalstjr1999naver.com

rhalstjr1999@naver

@naver.com

a@.com형식의 입력)

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "이메일 형식으로 작성해주세요:email",
        "status": 400
    }
}
```

해당 에러 처리는 적절해보인다. 서버측에서 지정한 구문을 충족하지 않은 경우 400 에러를 처리할 수 있다.

([https://jaeseongdev.github.io/development/2021/04/22/REST_API에서의_HTTP_상태코드_상태메시지.md/#:~:text=서에서 지정한 구문을 충족시키지 않은 경우](https://jaeseongdev.github.io/development/2021/04/22/REST_API%EC%97%90%EC%84%9C%EC%9D%98_HTTP_%EC%83%81%ED%83%9C%EC%BD%94%EB%93%9C_%EC%83%81%ED%83%9C%EB%A9%94%EC%8B%9C%EC%A7%80.md/#:~:text=%EC%84%9C%EC%97%90%EC%84%9C%20%EC%A7%80%EC%A0%95%ED%95%9C%20%EA%B5%AC%EB%AC%B8%EC%9D%84%20%EC%B6%A9%EC%A1%B1%EC%8B%9C%ED%82%A4%EC%A7%80%20%EC%95%8A%EC%9D%80%20%EA%B2%BD%EC%9A%B0))

또한 비밀번호에 영문, 숫자, 특수문자가 포함되어야 하는데 포함하지 않은 경우, 비밀번호 길이를 8~20자를 충족하지 않은 경우들은 서버측에서 지정한 구문을 충족시키지 못했기 때문에 400 에러 처리 해야한다.

```json
//특수문자 제외. 영문, 숫자 제외 시 동일
{
    "success": false,
    "response": null,
    "error": {
        "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
        "status": 400
    }
}

//비밀번호 길이 충족하지 않은 경우
{
    "success": false,
    "response": null,
    "error": {
        "message": "8에서 20자 이내여야 합니다.:password",
        "status": 400
    }
}
```
</div>
</details>

<details>
<summary>(4) 로그인</summary>
<div>
	
### HTTP 메서드 선정

로그인 시 사용자가 회원가입 때 작성한 이메일, 비밀번호로 로그인한다. 이때 해당 정보들을 서버측에 POST하여 해당 회원이 존재하는 검사를 받아야한다. 이때 JWT를 로그인 시, 회원에게 발급한다.

HTTP Method : POST

Local URL : http://localhost:8080/login

### JSON 응답 및 시나리오 분석

- JSON 응답
    
    회원가입 시 입력한 대로 POST 요청 시,
    
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```
    
    로그인 성공 JSON이 리턴되며 HTTP header 안에는 JWT가 발급되어 전송된다.
    
    ```json
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaGFsc3RqcjE5OTlAbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjozLCJleHAiOjE2ODgxMTgyOTF9.8WXi3ZtNxjo5l-jAZiBFKv0wqoNSjyWfREix-HCZi61LlLQuL9VYE3q5L4Vf0KUqjw08v6BAtiVv5k1b3bg-Qg
    ```
    

### 에러 처리

로그인 실패의 예는 다음과 같을 것이다.

1. 아이디를 잘못 입력하고 비밀번호는 맞을 경우
2. 아이디와 비밀번호 모두 잘못 입력할 경우
3. 아이디는 맞지만 비밀번호가 일치하지 않을 경우

이렇게 3가지가 존재할 것이다. 아이디와 비밀번호 형식을 지키지 않고 입력하지 않은 경우는 고려하지 않으려고 한다. 이유는 다음과 같다.

> 악의적인 의도로 로그인 시도하려는 사용자에게 로그인에 대한 일말의 여지도 주지 않을 것이다. 만약 악의적인 의도를 가진 사용자에게 아이디가 틀렸다, 비밀번호가 틀렸다, 형식이 맞지 않다 라는 여지를 주게 되면 그 사용자에게 정보를 주는 것이나 다름 없다고 생각한다.
> 

결론적으로 위에서 제시한 로그인 실패의 경우는 모두 인증되지 않은 사용자로 간주하고 401에러 처리를 하는 것이 적당해 보인다.

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "인증되지 않았습니다",
        "status": 401
    }
}
```

여기서 주목할 점은 에러 메세지 뒤에 어느 부분이 문제가 되었는지에 대해 정보를 주지 않았다. 회원가입 시에는 이메일 형식이 안맞는지, 패스워드 형식이 안맞는지에 대한 정보를 주었는데 로그인 시에는 그렇지 않다. 그렇다면 여러 에러 경우를 고려할 필요 없이 하나의 에러 처리로 끝내자.
</div>
</details>

<details>
<summary>(5) 장바구니 조회 및 주문하기(장바구니 업데이트)</summary>
<div>
	
### HTTP 메서드 선정

1. 장바구니 조회 시 상품 옵션과 개수를 선택해 담기한 상품들이 보여야할 것이다. 이는 클라이언트측이 서버측에 장바구니에 담긴 데이터를 요청해야한다. 결국 GET 요청을 해야한다.
    
    HTTP Method : GET
    
    Local URL : http://localhost:8080/carts
    
2. 장바구니에서 증감 버튼 클릭 시 클라이언트측은 장바구니 수정 정보를 서버측에 POST 요청을 통해 알려야한다. 서버측은 변경된 개수를 DB에 업데이트 하고 변경된 totalPrice를 반환해야할 것이다.
    
    HTTP Method : POST
    
    Local URL : http://localhost:8080/carts/update

   1. JWT(HTTP header에 담아서)와 carts/add 를 통해 장바구니에 상품을 담고 carts 조회를 해보았다.
    - JSON 응답
        
        ```json
        {
            "success": true,
            "response": {
                "products": [
                    {
                        "id": 1,
                        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                        "carts": [
                            {
                                "id": 1,
                                "option": {
                                    "id": 1,
                                    "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                                    "price": 10000
                                },
                                "quantity": 5,
                                "price": 50000
                            },
                            {
                                "id": 2,
                                "option": {
                                    "id": 2,
                                    "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                    "price": 10900
                                },
                                "quantity": 5,
                                "price": 54500
                            }
                        ]
                    }
                ],
                "totalPrice": 104500
            },
            "error": null
        }
        ```
        
        장바구니 담기한 상품들의 정보와 totalPrice를 계산해서 리턴해준다. totalPrice를 서버측 서비스단에서 연산 후 리턴하는 것으로 보인다.
        
2. 버튼 증감 클릭 시 JWT와 변경된 carts의 id와 개수를 서버측에 전송한 결과는 아래와 같다.
    - JSON 응답
        
        ```json
        {
            "success": true,
            "response": {
                "carts": [
                    {
                        "cartId": 1,
                        "optionId": 1,
                        "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                        "quantity": 10,
                        "price": 100000
                    },
                    {
                        "cartId": 2,
                        "optionId": 2,
                        "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                        "quantity": 10,
                        "price": 109000
                    }
                ],
                "totalPrice": 209000
            },
            "error": null
        }
        ```
        
    
    예상대로 장바구니에 담긴 상품의 개수를 변경해서 POST 요청을 보내니 변경된 totalPrice를 계산해서 리턴해준다.
   
### 에러 처리

JWT 정보가 유효하지 않은 경우를 고려해보았다.

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "인증되지 않았습니다",
        "status": 401
    }
}
```

토큰이 유효하지 않으니 인증도 유효하지 않다. 해당 에러 처리는 적절해보인다.

(마찬가지로 POST 요청 시 405 상태 코드 리턴이 적절해보인다.)

장바구니에 없는 상품의 개수를 업데이트 해달라고 POST 요청을 보내보았다. JSON 응답 결과는 이렇다.

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "장바구니에 없는 상품은 주문할 수 없습니다 : 23",
        "status": 400
    }
}
```

당연히 서버측에서 지정해놓은 구문을 충족하지 못했으니 400 상태 코드 리턴이 맞다.
</div>
</details>

<details>
<summary>(6) 결제하기</summary>
	
### HTTP 메서드 선정

HTTP Method : POST

Local URL : http://localhost:8080/orders/save

### JSON 응답 및 시나리오 분석

orders/save에 아무런 body도 작성하지 않고 POST 요청을 날렸다. 물론 장바구니는 비어있지 않다.

- JSON 응답
    
    ```json
    {
        "success": true,
        "response": {
            "id": 3,
            "products": [
                {
                    "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                    "items": [
                        {
                            "id": 5,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "quantity": 5,
                            "price": 50000
                        },
                        {
                            "id": 6,
                            "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                            "quantity": 11,
                            "price": 119900
                        }
                    ]
                }
            ],
            "totalPrice": 169900
        },
        "error": null
    }
    ```
    
    이후 똑같은 요청을 보냈다.
    
    ```json
    {
        "success": false,
        "response": null,
        "error": {
            "message": "장바구니에 아무 내역도 존재하지 않습니다",
            "status": 404
        }
    }
    ```
    
    장바구니가 비어있다는 말이다. 내가 장바구니에 넣은 상품들이 결제창으로 넘어왔고 orders/save를 통해 주문서에도 저장된다. body를 비워서 보냈는데 어떻게 서버는 저장했는가를 살펴보면 내 짐작은 이렇다.
    
    1. 토큰 정보를 통해 유저 식별
    2. 그 유저의 장바구니에 있는 상품들을 DTO를 통해 리턴
    3. 서버 내부에서는 장바구니에 있는 상품들을 주문서(정확히는 주문 아이템)로 옮기고, 장바구니를 비움

### 에러 처리

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"save\"",
        "status": 500
    }
}
```

GET 메서드로 orders/save를 보내니 주문 컨트롤러 내부에 있는 orders/{int}인 메서드가 존재하므로 NumberFormatException이 발생한다. 이 예외는 숫자가 아닌 문자열을 숫자로 변경하려고 할 때 발생한다.
<div>
</div>
</details>

<details>
<summary>(7) 주문 결과 처리</summary>
<div>

 ### HTTP 메서드 선정

주문 확인 페이지는 이미 주문이 완료된 주문 정보를 불러오는 페이지이다. 즉 서버측에 주문서에 대한 정보를 요청한다.

HTTP Method : GET

Local URL : http://localhost:8080/orders/1

여기서 orders/1의 1은 주문 번호이다.

### JSON 응답 및 시나리오 분석

주문 번호 1의 주문 정보를 불러오도록 orders/1을 GET 요청했다.

- JSON 응답
    
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
                            "quantity": 5,
                            "price": 50000
                        },
                        {
                            "id": 2,
                            "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                            "quantity": 5,
                            "price": 54500
                        }
                    ]
                }
            ],
            "totalPrice": 104500
        },
        "error": null
    }
    ```
    
    주문 정보 안에는 주문 번호와 상품 이름, 그리고 주문 아이템 테이블의 아이디 등의 데이터가 들어있다. 주문 하나를 주문 아이템 여러개로 분할하는 것은 정말 당연하다. 부분 취소할 수 있으니.. 역시나 해당 응답에서도 totalPrice는 서버측에서 계산후 리턴해준다.
    

### 에러 처리

POST 요청 시, 500 상태 코드를 반환한다. 405로 수정하자.

다음으로 존재하지 않은 주문 번호를 요청했다. orders/1444

```json
{
    "success": false,
    "response": null,
    "error": {
        "message": "해당 주문을 찾을 수 없습니다 : 1444",
        "status": 404
    }
}
```

서버측은 1444라는 주문 번호를 가진 주문서를 리턴할 수도 찾을 수도 없다. 그러므로 404 에러는 적절하다.
</div>
</details>

## 테이블 설계

### 기존 테이블 구성

![image](https://github.com/MinseokGo/step2-BE-kakao-shop/assets/96585636/d9ececf3-c653-49de-9698-399bdf53be15)

### 부조간 기능 고려 테이블 

상품 리뷰에 대한 테이블 추가를 구성하였다.

![image](https://github.com/MinseokGo/step2-BE-kakao-shop/assets/96585636/49d78851-54cb-4efd-819c-805e8e625b82)


</br>
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_1주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
