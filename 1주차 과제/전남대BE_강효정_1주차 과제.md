## 1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 작성하시오.
### 회원가입
- 이메일 중복 확인을 할 때, 해당 이메일로 직접 메일을 전송하여 사용 가능한 이메일인지 확인할 수 있도록 한다.
    - kakao@kakao.com.com은 올바른 이메일 형식이 아님에도 불구하고 이메일을 구성하는 @와 .이 있기 때문에 사용 가능한 이메일이라고 인식한다.
- 주문을 할 때 기본 배송지를 설정할 수 있으므로 주소를 입력할 수 있도록 한다.
### 로그인
- 이메일이나 비밀번호를 틀렸을 경우, ‘이메일이나 비밀번호가 틀렸습니다. 다시 입력해주세요.’ 등의 문구가 표시되지 않고 로그인 화면이 그대로 유지된다. 이 경우 사용자가 잘못 입력한 것인지 아니면 서버에 문제가 발생한 것인지 사용자는 알 수 없다.
- 비밀번호를 잊어버렸을 경우 자신의 계정을 다시 찾을 수 있도록 비밀번호 찾기 기능이 필요하다.
### 로그아웃
- 로그인 했을 때 생성된 JWT 토큰을 삭제하는 API가 필요하다.
### 전체 상품 목록 조회
- 회원이 원하는 상품을 검색해서 찾을 수 있는 기능이 필요하다.
### 개별 상품 상세 조회
- 해당 상품을 판매하는 판매자에 대한 정보가 필요하다.
- 해당 상품에 대한 리뷰 정보가 필요하다.
### 상품 옵션 선택 & 옵션 확인 및 수량 결정
- 선택한 옵션을 선택해서 삭제할 수 있도록 한다.
### 장바구니 상품 옵션 확인 및 수량 결정
- 장바구니의 상품 옵션을 선택해서 삭제할 수 있도록 한다.
- 장바구니의 상품 옵션을 선택해서 주문할 수 있도록 한다.
### 결제
- 배송지 정보 수정 기능이 필요하다.
### 기타
- 판매자가 상품을 등록할 수 있는 기능이 필요하다.
- 비회원도 상품을 주문할 수 있는 기능이 필요하다.
---
## 2. 제시된 화면 설계를 보고 해당 화면 설계와 배포된 기존 서버의 API 주소를 매칭하여 README에 내용을 작성하시오.
| 기능 | URL | Method | Request Header | Reqeust Body |
| :---: | :---: | :---: | :---: | :---: |
| 회원가입 | http://localhost:8080/join | POST |  | username, email, password |
| 로그인 | http://localhost:8080/login | POST |  | email, password |
| 로그아웃 |  |  |  |  |
| 전체 상품 목록 조회 | http://localhost:8080/products | GET |  |  |
| 개별 상품 상세 조회 | http://localhost:8080/products/1 | GET |  |  |
| 상품 옵션 선택 |  |  |  |  |
| 옵션 확인 및 수량 결정 |  |  |  |  |
| 장바구니 담기 | http://localhost:8080/carts/add | POST | JWT Token | optionId, quantity |
| 장바구니 보기 | http://localhost:8080/carts | GET | JWT Token |  |
| 장바구니 상품 옵션 확인 및 수량 결정 |  |  |  |  |
| 주문 | http://localhost:8080/carts/update | POST | JWT Token | cartId, quantity |
| 결제 | http://localhost:8080/orders/save | POST | JWT Token |  |
| 주문 결과 확인 | http://localhost:8080/orders/1 | GET | JWT Token |  |
---
## 3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
### 회원가입
- **Request**
    - 주문할 때 기본 배송지를 설정할 수 있으므로 주소를 입력할 수 있도록 한다면 → 주소에 대한 정보를 포함해야 한다.
- **Response**
    - 이메일과 이름이 길면 회원가입을 클릭하여도 프론트엔드에서는 회원가입이 완료되지 않으면서 회원가입 화면이 그대로 유지되고 백엔드에서는 서버 오류가 발생한다. 따라서 이메일과 이름의 입력 글자 수에 대한 제한이 필요하고 서버 오류 메시지를 그대로 응답으로 보내지 않고 예외 처리를 통하여 ‘이메일의 길이가 너무 깁니다.’, ‘이름의 길이가 너무 깁니다.’와 같은 오류 메시지를 보내야 한다.
### 로그인
- **Response**
    - 이메일이 길면 로그인을 클릭하여도 프론트엔드에서는 로그인이 되지 않으면서 로그인 화면이 그대로 유지되고 백엔드에서는 서버 오류가 발생한다. 따라서 이메일의 입력 글자 수에 대한 제한이 필요하고 서버 오류 메시지를 그대로 응답으로 보내지 않고 예외 처리를 통하여 ‘이메일의 길이가 너무 깁니다.’와 같은 오류 메시지를 보내야 한다.
### 전체 상품 조회
- **Response**
    - 각각의 상품의 무료 배송 여부에 대한 정보를 포함해야 한다.
    - 각각의 상품의 톡딜가에 대한 정보를 포함해야 한다.
    - 각각의 상품이 주문 가능한 상품인지의 여부에 대한 정보를 포함해야 한다.
### 개별 상품 조회
- **Response**
    - 상품의 톡딜가에 대한 정보를 포함해야 한다.
    - 상품의 배송 방법에 대한 정보를 포함해야 한다.
    - 상품의 배송비에 대한 정보를 포함해야 한다.
### 장바구니 담기
- **Request**
    - 상품의 배송비에 대한 정보를 포함해야 한다.
    - 상품의 배송 방법에 대한 정보를 포함해야 한다.
- **Response**
    - API 문서에 의하면 장바구니 담기는 Response가 없다.
    - 장바구니에 동일한 옵션을 담았을 경우, Response는 없지만 내부적으로 동일한 옵션에 대해 수량을 증가하는 방식으로 구현해야 한다.
    - 웹사이트를 실행하면 프론트엔드에서 옵션의 수량을 음수로 설정하는 것을 방지한다. 그러나, Postman에서 옵션의 수량을 음수 데이터로 설정하면 해당 옵션에 대한 수량 뿐만 아니라 가격에도 음수 데이터가 반영된다. 따라서, 옵션의 수량이 음수인 경우 예외 처리를 통하여 ‘수량은 0보다 작을 수 없습니다.’와 같은 오류 메시지를 보내야 한다.
### 장바구니 상품 옵션 확인 및 수량 결정
- **Response**
    - 웹사이트를 실행하면 프론트엔드에서 옵션의 수량을 음수로 설정하는 것을 방지한다. 그러나, Postman에서 옵션의 수량을 음수 데이터로 설정하면 해당 옵션에 대한 수량 뿐만 아니라 가격에도 음수 데이터가 반영된다. 따라서, 옵션의 수량이 음수인 경우 예외 처리를 통하여 ‘수량은 0보다 작을 수 없습니다.’와 같은 오류 메시지를 보내야 한다.
### 결제
- **Request**
    - 배송지에 대한 정보를 포함한다.
    - 배송 요청사항에 대한 정보를 포함한다.
    - 법적 고지사항에 대한 정보를 포함한다.
- **Response**
    - 배송지에 대한 정보를 포함한다.
    - 배송 요청사항에 대한 정보를 포함한다.
### 주문 결과 확인
- **Response**
    - 배송지에 대한 정보를 포함한다.
---
## 4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
### Kakao Shop ERD
![Kakao Shop ERD](./KakaoShop%20ERD.jpg)

---
## 5. 테이블 설계하기
### User
```sql
CREATE TABLE User (
    user_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(25) NOT NULL,
    password VARCHAR(20) NOT NULL CHECK (LENGTH(password) >= 8),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE unique_email (email)
);
```
### Product
```sql
CREATE TABLE Product (
	product_id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    image VARCHAR(1000) NOT NULL,
    description TEXT,
    product_price INT NOT NULL,
    star_count INT NOT NULL CHECK (0 <= star_count <= 5),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (product_id),
);
```
### Option
```sql
CREATE TABLE Option (
	option_id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    option_name VARCHAR(255) NOT NULL,
    option_price INT NOT NULL,
    PRIMARY KEY (option_id),
	FOREIGN KEY (product_id) REFERENCES Product (product_id)
);
```
### Cart
```sql
CREATE TABLE Cart (
	cart_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    option_id INT NOT NULL,
    quantity INT UNSIGNED NOT NULL,
    PRIMARY KEY (cart_id),
	FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (option_id) REFERENCES Option (option_id)
);
```
### Order
```sql
CREATE TABLE Order (
	order_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    PRIMARY KEY (order_id),
	FOREIGN KEY (user_id) REFERENCES User (user_id),
);
```
### Item
```sql
CREATE TABLE Item (
	item_id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    option_id INT NOT NULL,
    quantity INT UNSIGNED NOT NULL,
    total_price INT UNSIGNED NOT NULL,
    PRIMARY KEY (item_id),
	FOREIGN KEY (order_id) REFERENCES Order (order_id),
    FOREIGN KEY (option_id) REFERENCES Option (option_id)
);
```