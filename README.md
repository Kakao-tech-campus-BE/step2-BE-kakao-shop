# 카카오 2주차 과제

## 전체 API 주소 설계

- **Product**

| Description | URI | Method | Parmas |
| --- | --- | --- | --- |
| 전체 상품 조회 | /products | GET | page |
| 개별 상품 조회 | /products/{id} | GET |  |
- **Authentication**

| Description | URI | Method |
| --- | --- | --- |
| 회원가입 | /auth/join | POST |
| 로그인 | /auth/login | POST |
- **cart**

| Description | URI | Method |
| --- | --- | --- |
| 장바구니 조회 | /carts | GET |
| 장바구니 담기 | /carts | POST |
| 장바구니 수정 | /carts | PATCH |
| 장바구니 삭제 | /carts/{id} | DELETE |
- **Order**

| Description | URI | Method |
| --- | --- | --- |
| 결제하기 | /orders | POST |
| 주문 결과 확인 | /orders/{id} | GET |

```java
Rest API에 대해 배운 점은 다음과 같다
- 일관성이 있어야 한다
- 규칙은 case by case이다
- 실무에서는 그 회사의 룰을 따르면 된다
위의 작성한 URI와 /carts/add, /carts/update, /carts/delete에 대해 고민했다
상태가 들어가면 안된다고 생각해서 Method를 통해서 구분을 했는데, 오히려 더 헷갈릴 수 있지 않을까
라는 생각을 해본다

-----------------------------------------------------------------------------------

장바구니 수정 Method와 관련해서 RequestBody에 부분 값들이 들어오기 때문에 PATCH를 사용했지만
클라이언트가 보내는 값들은 신뢰할 수 없기 때문에 PUT으로 전체 데이터를 받아 비교해서 인증하는
절차가 있어야하는지 궁금하다.
```
