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

## **과제 제출**
<details>
<summary><H3>1️⃣ 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.</H3></summary>
<div markdown="1">

## 요구사항 화면 설계에서 도출

|전체 상품 목록 조회|개별 상품 상세 조회|회원가입|로그인|
|-----------|--------------|---|---|
|<img src = "https://github.com/jminkkk/TIL/assets/102847513/cd170709-1373-4f24-a62c-c4ed25985352">|<img src = "https://github.com/jminkkk/TIL/assets/102847513/4867b13a-d581-4e7c-a6e8-7181b4ac581b">|<img src = "https://github.com/jminkkk/TIL/assets/102847513/ffffe160-e05c-48ea-ad86-0cf90ecec8c8">|![image](https://github.com/jminkkk/TIL/assets/102847513/2f12b1b9-e652-448b-9b52-b32d34bc8caa)|
| ✅ 전체 상품 목록 조회 <br> **💡 라벨링별 목록 조회(무료배송 등)** | ✅ 상품 내용 조회 <br> ✅ 상품의 옵션 목록 조회|✅ 이메일 중복 체크 <br> ✅ 중복 검사 <br> ✅ 이메일 형식 유효성 검사 <br>  ✅ 비밀번호 형식 유효성 검사| ✅ 로그인 <br> ✅ 로그아웃|

|장바구니 담기(등록)|장바구니 조회|결제하기(주문 저장)|주문 조회|
|-----------|--------------|---|----|
|![image](https://github.com/jminkkk/TIL/assets/102847513/272dbc9c-bfd8-4358-9195-7c595a5fa54e)|![image](https://github.com/jminkkk/TIL/assets/102847513/33fe1885-552f-421a-8014-d5b0a15b4d51)|![image](https://github.com/jminkkk/TIL/assets/102847513/cdcdf678-e1a1-4929-a1c9-0089e47aa51d)|![image](https://github.com/jminkkk/TIL/assets/102847513/2883e1a6-2f01-4d95-9b03-9b4c3035beaa)
|✅ 장바구니 내역 저장| ✅ 장바구니 수량 변경|✅ 주문 저장| ✅ 주문 내역 조회 <br> 💡**주문 정보 변경/취소**|

---
## 요구사항에 없는 기능 도출하기

리액트를 실행한 화면에서 도출하거나 추가적으로 예상한 기능입니다!

### 상품

- 상품 등록
- 이벤트별 상품 목록 조회

### 기타

- 사용자 닉네임 등 정보 수정
- 배송지 내역 추가/조회
# 최종 도출한 추가 기능 목록

### 상품

- 상품 등록
- 라벨링별 상품 목록 조회
- 이벤트별 상품 목록 조회

### 주문

- 주문 취소
- 주문 내역 변경

### 기타

- 사용자 닉네임 등 정보 수정
- 배송지 내역 추가/조회
</div>
</details>
</br>


<details>
<summary><H3>2️⃣ 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)</H3></summary>
<div markdown="1">

# 기능별 API 주소 매칭

|(기능 1) 회원 가입|(기능 2) 로그인|(기능 3) 이메일중복체크|
|-----------|--------------|---|
|![image](https://github.com/jminkkk/TIL/assets/102847513/3f5e42f6-dc8d-453a-a689-a1ce96b48782)|![image](https://github.com/jminkkk/TIL/assets/102847513/fb1fe760-b560-44b6-abd1-b554979efc43)|![image](https://github.com/jminkkk/TIL/assets/102847513/87ea7a55-5fd4-4ced-a2ee-7d4dc269d811)|
|POST <br> http://localhost:8080/join |POST <br> http://localhost:8080/login|POST <br> http://localhost:8080/check|

|(기능 4) 전체 상품 목록 조회|(기능 5) 개별 상품 상세 조회|(기능 6) 장바구니 조회|
|-----------|--------------|---|
|![image](https://github.com/jminkkk/TIL/assets/102847513/1c80febb-5521-4d61-8b87-e39c6a90c8f7)|![image](https://github.com/jminkkk/TIL/assets/102847513/cc76a28c-0c7e-457c-a30e-d3cba3454b97)|![image](https://github.com/jminkkk/TIL/assets/102847513/d7469cbe-4257-4eac-ae2a-5c383525cd83)|
|GET <br> http://localhost:8080/products?page=1 | GET <br> http://localhost:8080/products/1 |GET <br> http://localhost:8080/cart |

|(기능 7) 장바구니 담기|(기능 8) 주문하기|(기능 9) 주문결과 확인|
|-----------|--------------|---|
|![image](https://github.com/jminkkk/TIL/assets/102847513/006d2f2b-6f5d-4316-9011-f7f450628ef5)|![image](https://github.com/jminkkk/TIL/assets/102847513/5a5e5d34-3e60-4c9a-94a3-5e64b0953ad6)|![image](https://github.com/jminkkk/TIL/assets/102847513/b2d6f013-9523-45bb-8369-ba21fbf19046)|
|POST <br> http://localhost:8080/cart | POST <br> http://localhost:8080/orders/save |GET <br> http://localhost:8080/orders/1 <br> 제공된 화면에는 따로 버튼 X <br> -> 주문하기를 누르면 조회 가능 |

</div>
</details>
</br>
<details>
<summary><H3>3️⃣ 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.</H3></summary>
<div markdown="1">

# POSTMAN 실행 목록
|(기능 1) 회원 가입|(기능 2) 로그인|(기능 3) 이메일중복체크|
|-----------|--------------|---|
|![image](https://github.com/jminkkk/TIL/assets/102847513/309246d1-cc28-4509-b6c1-2a086487a7bf)|![image](https://github.com/jminkkk/TIL/assets/102847513/564558e5-95c4-4fab-9095-fa959f63eb16)|![image](https://github.com/jminkkk/TIL/assets/102847513/1dce6882-d42a-469f-bc0e-565714667cfa)
|


|(기능 4) 전체 상품 목록 조회|(기능 5) 개별 상품 상세 조회|(기능 6) 장바구니 조회|
|-----------|--------------|---|
|![image](https://github.com/jminkkk/TIL/assets/102847513/4677cfc3-7b57-48e2-aa13-95918d718ed9)|![image](https://github.com/jminkkk/TIL/assets/102847513/299398e3-9dbf-450b-ae49-af73a3732f5c)|![image](https://github.com/jminkkk/TIL/assets/102847513/8ff54d7e-2575-49c5-a79d-3881ef791fa3)|
|➕ 무료배송과 같은 라벨링 정보|➕ 상품 별점||

|(기능 7) 장바구니 담기|(기능 8) 주문하기|(기능 9) 주문결과 확인|
|-----------|--------------|---|
|![image](https://github.com/jminkkk/TIL/assets/102847513/f65dc413-e967-49a9-ba6d-c37b5fbb43c1)|![image](https://github.com/jminkkk/TIL/assets/102847513/4b9b56b2-eb73-4229-b3bf-9316e6550ff2)|![image](https://github.com/jminkkk/TIL/assets/102847513/ec4afeb0-20c7-4e14-9d2a-8eb77460b84b)|
|➕ 담긴 장바구니의 전체 가격을 response에 메시지로 추가해도 좋을 듯||➕  배송지 내역 <br> ➕ 결제 내역 <br> ➕ 주문 일자|
---
## 부족한 데이터
+ 무료배송과 같은 상품들의 라벨링 정보
+ 상품의 별점	
+ 장바구니 담기 요청 시 전체 가격을 응답으로 전달하기
+ 주문 결과 확인 시 
    + 배송지 내역
    + 결제 내역
    + 주문 일자
</div>
</details>
</br>
<details>
<summary><H3>4️⃣ 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
</H3></summary>
<div markdown="1">
<br>

# 최종 도출한 ERD
![image](https://github.com/jminkkk/TIL/assets/102847513/05e3971d-e779-446a-9afa-6bc9c1b93d9a)

### ✅ user 테이블

- Id (PK)
- email
- password
- username
- role

### ✅ product 테이블

- Id (PK)
- product_name
- image
- price
- description
- startcount
- **label_id(fk)**
    - 라벨이 여러개를 달 수 있다면 테이블 추출
- 배송비

```sql
// 전체 상품 목록 조회
select * from Product; 
```

### ✅ option 테이블

- id (PK)
- product_id(fk)
- option_name
- option_price

```sql
// 개별 상품 상세 조회
select * from product where product_id = 1;
select * from option where product_id = 1; 
```

### ✅ cart 테이블

- id
- optionId(fk)
- userId(fk)
- quantity
- created_at
- status
    - 처리상태

```sql
// 장바구니 조회
select * from cart where user_id = 1;
```

### ✅ order 테이블

- id
- user_id
- **ordered_at**
    - 주문 시간
- total_price

```sql
// 주문 조회
select * from order where user_id = 1;
// select * from order_item where order_id = 1;
```

### ✅ orderItem 테이블

- id
- order_id
- option_id
- quantity
- item_total_price

---
## 추가 도출한 기능들의 테이블
### ✅ Label 테이블

- id
- label_name

### ✅ Address 테이블

- id
- user_id
- address_name
- status
</div>
</details>
</br>

---

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
