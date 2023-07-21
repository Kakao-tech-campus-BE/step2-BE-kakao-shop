create table user_entity
(
    user_id      LONG AUTO_INCREMENT NOT NULL,
    username     VARCHAR(45)         NOT NULL,
    email        VARCHAR(20)         NOT NULL,
    password     VARCHAR(20)         NOT NULL,
    role         VARCHAR(20)         NOT NULL,
    created_date TIMESTAMP           NOT NULL,
    roles        VARCHAR(30),
    PRIMARY KEY (user_id),
    CONSTRAINT unique_email UNIQUE KEY (email)
);


create table product_entity
(
    product_id   LONG AUTO_INCREMENT NOT NULL,
    product_name VARCHAR(100)        NOT NULL,
    description  VARCHAR(1000)       NOT NULL,
    image        VARCHAR(500),
    price        INT,
    created_date TIMESTAMP           NOT NULL,
    PRIMARY KEY (product_id)
);

create table product_option_entity
(
    product_option_id LONG AUTO_INCREMENT NOT NULL,
    product_id        LONG                NOT NULL,
    option_name       VARCHAR(100)        NOT NULL,
    price             INT                 NOT NULL,
    PRIMARY KEY (product_option_id),
    CONSTRAINT idx_option_product_id FOREIGN KEY (product_id) REFERENCES product_entity (product_id)
);

create table cart_entity
(
    cart_id           Long AUTO_INCREMENT NOT NULL,
    user_id           LONG                NOT NULL,
    product_option_id Long                NOT NULL,
    quantity          int                 NOT NULL,
    price             int                 NOT NULL,
    PRIMARY KEY (cart_id),
    CONSTRAINT unique_user_option_id UNIQUE KEY (user_id, product_option_id),
    CONSTRAINT idx_cart_user_id FOREIGN KEY (user_id) REFERENCES user_entity (user_id),
    CONSTRAINT idx_cart_option_id FOREIGN KEY (product_option_id) REFERENCES product_option_entity (product_option_id)
);

create table order_entity
(
    order_id    LONG AUTO_INCREMENT NOT NULL,
    user_id     LONG                NOT NULL,
    order_date  LONG                NOT NULL,
    is_cancel   boolean             NOT NULL,
    total_price int                 NOT NULL,
    PRIMARY KEY (order_id)
);

create table order_item_entity
(
    order_item_id LONG AUTO_INCREMENT NOT NULL,
    order_id      LONG                NOT NULL,
    product_id    LONG                NOT NULL,
    option_name   VARCHAR(30)         NOT NULL,
    product_name  VARCHAR(20)         NOT NULL,
    price         int                 NOT NULL,
    quantity      int                 NOT NULL,
    PRIMARY KEY (order_item_id),
    CONSTRAINT idx_orders_id FOREIGN KEY (order_id) REFERENCES order_entity (order_id)
);