# 2주차 과제
## API 주소 설계

### User
| API | URI | HTTP Method | 
| --- | --- | --- |
| 회원가입 | /auth/join | POST | 
| 로그인 | /auth/login | POST |

### Product
| API | URI | HTTP Method | 
| --- | --- | --- |
| 전체 상품 조회 | /products | GET | 
| 개별 상품 조회 | /products/{id} | GET |
<br>
(전체 상품 조회는 페이지를 Param으로 받아 매핑한다. page의 디폴트 값은 0이다.)

### Cart
| API | URI | HTTP Method | 
| --- | --- | --- |
| 장바구니 조회 | /carts | GET | 
| 장바구니 담기 | /carts/add | POST |
| 장바구니 수정 | /carts/update | POST |

### Order
| API | URI | HTTP Method | 
| --- | --- | --- |
| 결제하기 | /orders/save | POST |
| 주문 확인 | /orders/{id} | GET |
