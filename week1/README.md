## 도메인 분석

### 회원가입 화면

가장 먼저 회원가입 화면이 나옵니다. 회원가입 화면에서는 가입에 필요한 유저 정보를 입력합니다. 유저 정보로는 이메일, 이름, 비밀번호가 있습니다. 이를 저장하기 위한 테이블 `user_tb` 가 필요합니다.

1. id : int
2. name : string
3. email : string
4. password : string

필드가 필요합니다. 그리고 해당 홈페이지는 쇼핑몰 입니다. 따라서, 판매자와 구매자 그리고 관리자의 구분이 필요해 보입니다. 가입 시에는 판매자, 구매자만 구분해야 하지 가입 과정에서 관리자 확인까지 진행하면 복잡해질 것 같습니다. 관리자 추가는 추후 직접 지정 형식으로 하고, 홈페이지 내에서의 가입은 구매자와 판매자만 구분하면 될 것 같습니다. 따라서 해당 페이지에서는, 판매자와 구매자를 구분해서 가입하는 기능이 부족해 보입니다. 그리고 이를 위해 유저 테이블에 필드를 하나 추가합니다.

5. roles : string

그리고 API를 살펴보면, 이메일 유효성 검증을 위한 `/check` 와 정보 저장을 위한 `/join` 가 필요합니다. 그리고 이미 가입된 회원인지를 검사하는 API가 필요해 보입니다. `/exists` 정도가 괜찮아 보입니다.

</br>

### 로그인 화면

로그인은 가입된 정보와 일치하는지 확인하는 과정입니다. 이메일, 비밀번호를 입력하여 확인합니다. 정보 검증을 위한 화면이므로 테이블을 생성할 필요는 없습니다. 이를 위한 API는 `/check` 와 `/login` 이 필요합니다.

그리고 해당 화면에는 로그인 기능만 존재합니다. 비밀번호 변경, 아이디 찾기 등 회원가입 정보를 잊어버린 경우에 되찾기 위한 방법이 부족합니다. 이를 위한 `find/password` 와 `/find/email` API가 필요해 보입니다.

</br>

### 로그아웃

로그아웃은 프론트에서 진행하므로 별다른 기능이 필요하지 않은 것 같습니다.

</br>

### 전체 상품 목록 조회

쇼핑몰의 전체 상품을 보여주는 페이지 입니다. 상품 사진, 이름, 가격 정보가 필요합니다. 따라서 상품 정보 저장을 위한 테이블 `product_tb` 가 필요합니다. 필드로는

1. id : int
2. name : string
3. description : string
4. price : int
5. image : string

이 필요합니다. 그리고 테이블 조회를 위한 `/products` API 가 필요합니다. 

전체 상품을 확인할 수는 있지만, 검색 및 필터링 기능이 없습니다. 필요한 상품 검색이나 분류를 위한 기능이 추가되면 좋을 것 같습니다. 분류를 위해 상품 테이블에 카테고리 정보를 저장하는 필드를 추가하면 좋을 것 같습니다. 그리고 검색은 프론트에서 하면 될 것이므로 백엔드에서 추가적인 작업은 필요해 보이지 않습니다.

그리고 해당 상품의 재고 확인이 불가능 합니다. 품절 상품에 대한 표시가 있으면 더 좋을 것 같습니다. 재고 정보를 저장하기 위해서 product_tb 에 stock : int 필드를 추가면 될 것 같습니다.

</br>

### 개별 상품 상세 조회

개별 상품 상세 조회 화면입니다. 해당 화면에서는 별점이 보이고, 옵션이라는 추가적인 요소가 보입니다. 우선, 별점 정보 저장을 위해 `product_tb` 에 star_count : int 필드를 추가합니다. 

그리고 옵션이 존재합니다. 옵션은 하나의 상품에 여러 개가 존재할 수 있기 때문에 상품 테이블에 필드를 추가하기 보다는, 새로운 테이블을 만들어 연결시키는 것이 좋을 것 같습니다. 따라서 옵션을 저장하기 위한 테이블 `option_tb` 가 필요합니다. 그리고 해당 테이블에는

1. id : int
2. name : string
3. price : int
4. product_id : int

가 필요합니다. product_id는 해당 옵션이 속한 상품의 정보를 가리키기 위해 필요합니다. 그리고 옵션 밑에 보면, 배송비 정보가 있습니다. 무료 배송인지, 추가 배송비가 필요한지, 필요한 배송비의 정보를 저장하기 위해서 상품 테이블에 추가적인 필드가 필요합니다. 따라서 `product_tb` 에 delivery_price : int 필드를 추가합니다. 

그리고 상품의 정보 조회를 위한 `/products/{id}` API가 필요합니다. 

</br>

### 장바구니 담기

선택한 상품을 장바구니에 담는 페이지 입니다. 장바구니 정보를 저장해야 하므로, 우선 `cart_tb` 가 필요합니다. 장바구니는 해당 유저에 속한 정보이고 해당 테이블에는 상품의 정보가 저장되어야 하므로, 필요한 필드는

1. id : int
2. user_id : int
3. product_id : int
4. option_id : int
5. quantity : int
6. price : int

가 있습니다. 그리고 해당 정보를 저장하기 위해서 `/carts/add` API가 필요합니다. 해당 화면에서 부족한 기능은 없어 보입니다.

</br>

### 장바구니 보기

저장한 장바구니를 보여주는 페이지 입니다. 우선, 장바구니를 불러오기 위한 `/carts` API가 필요합니다. 그리고 수량 조절을 위한 `/carts/update` 도 필요합니다. 

그러나 해당 페이지에서는 장바구니에 담은 물건을 삭제하는 기능이 없습니다. 그리고 특정 상품만 선택하여 주문할 수 있는 기능이 없습니다. 따라서 장바구니 삭제를 위해 `/carts/delete/` API가 필요해 보입니다. 

</br>

### 주문

주문하는 페이지 입니다. 장바구니에서 선택한 아이템을 주문합니다. 현재 페이지에서는 수량만 조절 가능하므로, 특정 아이템만 선택할 수 있는 기능이 부족해 보입니다. 그리고 상품을 수령하기 위한 배송지 정보를 입력하는 부분이 없어 배송지 입력 기능이 추가되면 좋을 것 같습니다. 

그리고 포인트나 쿠폰 등 추가 할인을 위한 기능이 추가되면 좋을 것 같습니다. API는 `/coupons/` 나 포인트의 경우에는 유저 테이블에 추가해서 같이 관리하면 좋을 것 같습니다.

</br>

### 결제

결제하는 화면에서는 장바구니에 있는 정보들을 주문 정보로 옮기고, 주문한 상품은 장바구니에서 삭제해야 합니다. 우선, 결제 정보 저장 및 확인을 위한 API `orders/save` 와 `orders/{id}` 가 필요합니다. 

다음으로, 결제 정보 저장을 위한 테이블 `order_tb` 가 필요합니다. 결제 화면을 잘 보면, 여러 아이템이 존재합니다. 따라서 주문 정보는 여러 개의 아이템으로 구성되어 있고 결제 정보는 한 명의 유저에 속하기 때문에 우선 유저와 결제를 연결하기 위해서 2개의 필드

1. id : int
2. user_id : int

가 필요합니다. 그리고 결제 아이템 정보 저장을 위한 테이블 `order_item_tb` 가 필요하고 필드로는 상품 정보 저장을 위해

1. id : int
2. option_id : int
3. order_id : int
4. quantity : int
5. price : int

가 필요합니다. 현재 화면에는 부족한 기능은 없다고 생각됩니다.

</br>
</br>
</br>

## 테이블 생성하기

위 정보들을 바탕으로 테이블을 생성합니다. 깃헙 프로젝트의 build.gradle 을 보면, mariaDB를 사용하는 것을 알 수 있으므로 mariaDB 의 문법으로 테이블을 작성합니다.

```sql
DROP TABLE IF EXISTS user_tb;
DROP TABLE IF EXISTS product_tb;
DROP TABLE IF EXISTS order_tb;
DROP TABLE IF EXISTS option_tb;
DROP TABLE IF EXISTS item_tb;
DROP TABLE IF EXISTS cart_tb;

CREATE TABLE user_tb
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    email    TEXT NOT NULL,
    password TEXT NOT NULL,
    roles    TEXT NOT NULL,
    username TEXT NOT NULL,
    UNIQUE (email)
);

CREATE TABLE item_tb
(
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    price     INTEGER NOT NULL,
    quantity  INTEGER NOT NULL,
    option_id INTEGER,
    order_id  INTEGER,
    CONSTRAINT option_id_fk FOREIGN KEY (option_id) REFERENCES option_tb,
    CONSTRAINT order_id_fk FOREIGN KEY (order_id) REFERENCES order_tb
);

CREATE TABLE option_tb
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    option_name TEXT    NOT NULL,
    price       INTEGER NOT NULL,
    product_id  INTEGER,
    CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES product_tb
);

CREATE TABLE cart_tb
(
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    price     INTEGER NOT NULL,
    quantity  INTEGER NOT NULL,
    user_id   INTEGER,
    option_id INTEGER,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_tb,
    CONSTRAINT option_id_fk FOREIGN KEY (option_id) REFERENCES option_tb,
    UNIQUE (user_id, option_id)
);

CREATE TABLE product_tb
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    description  TEXT    NOT NULL,
    image        TEXT    NOT NULL,
    price        INTEGER NOT NULL,
    product_name TEXT    NOT NULL
);

CREATE TABLE order_tb
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_tb
);

CREATE INDEX cart_user_id_idx on cart_tb (user_id);
CREATE INDEX cart_option_id_idx on cart_tb (option_id);
CREATE INDEX item_option_id_idx on item_tb (option_id);
CREATE INDEX item_order_id_idx on item_tb (order_id);
CREATE INDEX option_product_id_idx on option_tb (product_id);
CREATE INDEX order_user_id_idx on order_tb (user_id);
```

## ER 다이어그램

![ER Diagram](https://github.com/SeokjunMoon/step2-BE-kakao-shop/blob/feat-moonseokjun/데이터베이스_ER_다이어그램.png)

</br>
