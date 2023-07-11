## 1. API 주소를 설계하여 README에 내용을 작성하시오.

### User

|   기능   |             URL              | Method |
| :------: | :--------------------------: | :----: |
| 회원가입 | http://localhost:8080/users  |  POST  |
|  로그인  | http://localhost:8080/login  |  POST  |
| 로그아웃 | http://localhost:8080/logout |  POST  |

### Product

|        기능         | URL                                 | Method |
| :-----------------: | :---------------------------------- | :----: |
| 전체 상품 목록 조회 | http://localhost:8080/products      |  GET   |
| 개별 상품 상세 조회 | http://localhost:8080/products/{id} |  GET   |

### Cart

|     기능      |               URL                | Method |
| :-----------: | :------------------------------: | :----: |
| 장바구니 담기 |   http://localhost:8080/carts    |  POST  |
| 장바구니 보기 |   http://localhost:8080/carts    |  GET   |
| 장바구니 수정 | http://localhost:8080/carts/{id} |  PUT   |
| 장바구니 삭제 | http://localhost:8080/carts/{id} | DELETE |

### Order

|      기능      |                URL                | Method |
| :------------: | :-------------------------------: | :----: |
|      주문      |   http://localhost:8080/orders    |  POST  |
| 주문 결과 확인 | http://localhost:8080/orders/{id} |  GET   |
