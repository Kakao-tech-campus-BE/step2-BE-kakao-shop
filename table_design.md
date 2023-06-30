## User 테이블
```
CREATE TABLE Users (
	user_id	INTEGER	NOT NULL	DEFAULT auto_increment,
	user_name	VARCHAR(20)	NOT NULL,
	email	VARCHAR(30)	NOT NULL,
	password	VARCHAR(20)	NOT NULL,
	created_at	DATE	NOT NULL	DEFAULT DATE(NOW()),
    PRIMARY KEY (user_id)
);
```

## Product 테이블
```
CREATE TABLE Products (
	product_id	INTEGER	NOT NULL	DEFAULT auto_increment,
	product_name	VARCHAR(100)	NOT NULL,
	product_img	VARCHAR(100)	NULL,
	product_price	INTEGER	NOT NULL	DEFAULT 0,
	created_at	DATE	NOT NULL	DEFAULT DATE(now()),
    PRIMARY KEY (product_id)
);
```

## Option 테이블
```
CREATE TABLE Options (
	option_id	INTEGER	NOT NULL	DEFAULT auto_increment,
	product_id	INTEGER	NOT NULL,
	option_name	VARCHAR(100)	NOT NULL,
	option_price	INTEGER	NOT NULL	DEFAULT 0,
	created_at	DATE	NOT NULL	DEFAULT DATE(now()),
    PRIMARY KEY (option_id, product_id),
    FOREIGN KEY(product_id) REFERENCES Products(product_id) ON CASCADE
);
```

## Cart 테이블
```
CREATE TABLE Carts (
	cart_id	VARCHAR(255)	NOT NULL	DEFAULT auto_increment,
	count	INTEGER	NOT NULL	DEFAULT 1,
	user_id	INTEGER	NOT NULL,
	option_id	INTEGER	NOT NULL    DEFAULT 0,
	product_id	INTEGER	NOT NULL    DEFAULT 0,
    PRIMARY KEY (cart_id),
    FOREIGN KEY(user_id) REFERENCES Users(user_id) ON CASCADE,
    FOREIGN KEY(option_id) REFERENCES Options(option_id) ON SET DEFAULT,
    FOREIGN KEY(product_id) REFERENCES Products(product_id) ON SET DEFAULT
);
```

## Order 테이블
```
CREATE TABLE Orders (
	order_id	INTEGER	NOT NULL	DEFAULT auto_increment,
	user_id	INTEGER	NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY(user_id) REFERENCES Users(user_id) ON SET DEFAULT
);
```

## Item 테이블
```
CREATE TABLE Order_items (
	order_id	INTEGER	NOT NULL,
	option_id	INTEGER	NOT NULL,
	product_id	INTEGER	NOT NULL,
	count	INTEGER	NOT NULL	DEFAULT 1,
    PRIMARY KEY (order_id, option_id, product_id),
    FOREIGN KEY(order_id) REFERENCES Orders(order_id) ON CASCADE,
    FOREIGN KEY(option_id) REFERENCES Options(option_id) ON ACTION,
    FOREIGN KEY(product_id) REFERENCES Products(product_id) ON ACTION
);
```