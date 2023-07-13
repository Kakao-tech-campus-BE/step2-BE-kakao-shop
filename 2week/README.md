
# 2주차


## **<부산대BE_김돈우_2주차 과제>**
<br>

### 1. API주소를 설계하여 README에 내용을 작성하시오.

```
- DTO를 구현하는 과제에서는 따로 API 주소를 설계해서 구현하진 않았습니다.
- 밑에 테이블은 평소 프로젝트 진행시에 API 주소 설계할 때, 제가 짜왔던 방식대로 한번 짜봤습니다!!!
```

| 기능     | 기능 설명           | Method | url                   |
|--------|-----------------|--------|-----------------------|
| 기능-001 | 회원 가입           | POST   | `/auth/join`          |
| 기능-002 | 이메일 중복 체크       | POST   | `/auth/check-email`   |
| 기능-003 | 로그인             | POST   | `/auth/login`         |
| 기능-004 | 로그아웃            | POST   | `/auth/logout`        |
| 기능-005 | 비밀번호 찾기         | POST   | `/auth/forget/password` |
| 기능-006 | 아이디 찾기          | POST   | `/auth/forget/id`     |
| 기능-011 | 내 정보 조회         | GET    | `/my`                 |
| 기능-012 | 본인의 정보 수정       | PATCH  | `/my/info`            |
| 기능-013 | 비밀번호 수정         | PATCH  | `/my/password`        |
| 기능-014 | 회원 탈퇴           | DELETE | `/my`                 |
| 기능-015 | 본인의 모든 주문 내역 조회 | GET    | `/my/orders`          |
| 기능-016 | 개별 주문 조회        | GET    | `/my/orders/{id}`     |
| 기능-017 | 배송지 수정          | PATCH  | `/my/orders`          |
| 기능-018 | 주문 취소하기         | DELETE | `/my/orders`          |
| 기능-021 | 전체 상품 조회        | GET    | `/products`           |
| 기능-022 | 전체 상품 페이징       | GET    | `/products/page={int}` |
| 기능-023 | 개별 상품 상세 조회     | GET    | `/products/{id}`      |
| 기능-024 | 상품 등록           | POST   | `/products`           |
| 기능-025 | 상품 등록 수정        | PATCH  | `/products`           |
| 기능-026 | 등록한 상품 삭제	      | DELETE | `/products`           |
| 기능-027 | 상품 후기 조회        | GET    | `/products/review`    |
| 기능-031 | 장바구니 확인	        | GET    | `/carts`              |
| 기능-032 | 장바구니에 담기	       | POST   | `/carts`              |
| 기능-033 | 장바구니에서 삭제       | DELETE | `/carts`              |
| 기능-041 | 주문하기            | POST    | `/orders`             |
| 기능-042 | 결제하기            | POST    | `/orders/calculate`   |







<br>

### 2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.

```
- 해당 부분은 Kakao-Backend-Second-DTO 프로젝트에 DTO 구현해놨습니다!
```

