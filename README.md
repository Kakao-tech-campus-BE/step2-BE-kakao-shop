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


```ad-note
- 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? 
	- (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지)
- 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? 
	- (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
- 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?
	- (예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
- 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? 
	- (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
```

## 1. 부족한 기능에 대한 요구사항
 - User
	 - 기존 사용자의 이메일/비밀번호 같은 정보를 업데이트하는 기능
	 - 데이터베이스에 등록 된 기존 사용자 삭제 기능
	 - JWT 토큰을 사용하므로, 만료시 토큰 refresh하는 기능
 - Products
	 - 관리자가 새로운 상품을 추가하는 기능
	 - 기존 제품의 정보를 업데이트 하는 기능
	 - 등록된 기존 제품을 삭제하는 기능
	 - 제품 검색하는 기능 : 이름, 카테고리 등을 기준으로 제품 검색
- Carts
	- 카트 항목을 주문으로 전송하고 비우는 기능
- Order
	- 주문 상태를 변경하는 기능 (예시 : 처리 중에서 배송 됨으로 변경하는 등)
- Option
	- Option의 속성을 수정하는 기능
### 추가적인 테이블 : User History
사용자의 로그인과 로그아웃 등등을 기록하는 것 등의 로그를 남기는 것이 필요하다 생각했습니다
사용자 활동 기록을 관리함으로써 추후 사용자 세션 관리나 사용자 활동 추적을 위해 사용합니다
```SQL
CREATE TABLE UserHistory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    loginTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logoutTime TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User(id)
);
```


## API 응답 확인 및 수정 사항 파악
Postman으로 API를 등록해두고, 테스트를 진행하였습니다

![](https://i.imgur.com/9Y1nf7i.png)

현재 API에 잘못된 요청을 하면, 에러 상태 코드와 함께 __테이블의 상태__ 도 함께 반환합니다
이는 공격자에게 테이블 정보를 제공하여 취약점을 발생시킵니다

### User(사용자)
![](https://i.imgur.com/Mt8Il7y.png)


1. **이메일 중복 확인**
    - 응답/요청 방식 : GET
    - URL: [http://localhost:8080/check](http://localhost:8080/check)
    - 요청
	```JSON
	{
		"email":"ssar@nate.com",
		"password":"meta1234!"
	}
	```
	- 응답
	```JSON
	{
		"success": true,	
		"response": null,
		"error": null
	}
	```
2. **회원가입**
    - 응답/요청 방식: POST
    - URL: [http://localhost:8080/join](http://localhost:8080/join)
	- 요청
	```JSON
	{
		"email":"ssar@nate.com",
		"password":"meta1234!"
	}
	```
	- 응답
	```JSON
	{
		"success": true,	
		"response": null,
		"error": null
	}
	```
3. **로그인**
    - 응답/요청 방식: POST
    - URL: [http://localhost:8080/login](http://localhost:8080/login)
	- 요청
	```JSON
	{
		"email":"ssar@nate.com",
		"password":"meta1234!"
	}
	```
	- 응답
	```JSON
	{
		"success": true,	
		"response": null,
		"error": null
	}
	```
### Product(상품)
![](https://i.imgur.com/CYCcsIZ.png)

```json
{
  "success": true,
  "response": [
    {
      "id": 1,
      "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
      "description": "",
      "image": "/images/1.jpg",
      "price": 1000
    },
    {
      "id": 2,
      "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
      "description": "",
      "image": "/images/2.jpg",
      "price": 2000
    },
  ],
  "error": null
}
```


1. **전체 상품리스트 검색**
    - 응답/요청 방식 : GET
    - URL: [http://localhost:8080/products](http://localhost:8080/products)
    - 매개변수: page={번호}(기본값은 0)
    - 응답
        - ![](https://i.imgur.com/gzE5OQT.png)

2. **Param을 사용하여 전체 제품 목록 검색**
    - 응답/요청 방식 : GET
    - URL: [http://localhost:8080/products](http://localhost:8080/products)
    - 매개변수: 페이지=0
    - 응답
        - ![](https://i.imgur.com/KZ9ubVO.png)

3. **개별상품 상세검색**
    - 응답/요청 방식 : GET
    - URL: [http://localhost:8080/products/{id}](http://localhost:8080/products/%7Bid%7D)
    - 매개변수: 해당 없음
    - 응답
        - ![](https://i.imgur.com/MvIjCo7.png)
### Option(옵션)
![](https://i.imgur.com/RcxU8Re.png)

옵션은 제품 상세보기 API를 통해서, 확인 할 수 있습니다

### Cart (장바구니)
![](https://i.imgur.com/bbqbsgw.png)



1. **장바구니에 추가**
    - 응답/요청 방식: POST
    - URL: [http://localhost:8080/carts/add](http://localhost:8080/carts/add)
    - 요청
        - ![](https://i.imgur.com/jzSh9GU.png)
    - 응답
		```JSON
		{
		  "success": true,
		  "response": null,
		  "error": null
		}
	   ```
2. **장바구니 조회**
    - 응답/요청 방식 : GET
    - URL: [http://localhost:8080/carts](http://localhost:8080/carts)
    - 응답
        - ![](https://i.imgur.com/2YVXkeo.png)

3. **장바구니 수정**
    - 응답/요청 방식: POST
    - URL: [http://localhost:8080/carts/update](http://localhost:8080/carts/update)
    - 요청
        - ![](https://i.imgur.com/PcdKTGS.png)
    - 응답
	```JSON
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

### Order(주문)
![](https://i.imgur.com/vI5LrbL.png)

```json
{
    "success": true,
    "response": {
        "products": [
            {
                "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                "items": [
                    {
                        "id": 65,
                        "option": {
                            "id": 1,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    }
                ],
            },
            {
                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                "items": [
                    {
                        "id": 3,
                        "option": {
                            "id": 1,
                            "optionName": "JR310BT (무선 전용) - 레드",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    }
                ],
            }
        ],
        "totalPrice": 104500
    },
    "error": null
}
```
1. **주문**
    - 응답/요청 방식: POST
    - URL: [http://localhost:8080/carts/orders/save](http://localhost:8080/carts/orders/save)
    - 응답
	```json
	{
	    "success": true,
	    "response": {
	        "products": [
	            {
	                "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
	                "items": [
	                    {
	                        "id": 65,
	                        "option": {
	                            "id": 1,
	                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
	                            "price": 10000
	                        },
	                        "quantity": 5,
	                        "price": 50000
	                    }
	                ],
	            },
	            {
	                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
	                "items": [
	                    {
	                        "id": 3,
	                        "option": {
	                            "id": 1,
	                            "optionName": "JR310BT (무선 전용) - 레드",
	                            "price": 10000
	                        },
	                        "quantity": 5,
	                        "price": 50000
	                    }
	                ],
	            }
	        ],
	        "totalPrice": 104500
	    },
	    "error": null
	}
	```
2. **주문 조회**
    - 응답/요청 방식 : GET
    - URL: [http://localhost:8080/carts/orders/{id}](http://localhost:8080/carts/orders/%7Bid%7D)
    - 응답
	```json
	{
	    "success": true,
	    "response": {
	        "products": [
	            {
	                "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
	                "items": [
	                    {
	                        "id": 65,
	                        "option": {
	                            "id": 1,
	                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
	                            "price": 10000
	                        },
	                        "quantity": 5,
	                        "price": 50000
	                    }
	                ],
	            },
	            {
	                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
	                "items": [
	                    {
	                        "id": 3,
	                        "option": {
	                            "id": 1,
	                            "optionName": "JR310BT (무선 전용) - 레드",
	                            "price": 10000
	                        },
	                        "quantity": 5,
	                        "price": 50000
	                    }
	                ],
	            }
	        ],
	        "totalPrice": 104500
	    },
	    "error": null
	}
	```
### Item(주문 아이템)
![](https://i.imgur.com/AmOTWKc.png)

```json
{
    "success": true,
    "response": {
        "products": [
            {
                "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                "items": [
                    {
                        "id": 65,
                        "option": {
                            "id": 1,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    }
                ],
            },
            {
                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                "items": [
                    {
                        "id": 3,
                        "option": {
                            "id": 1,
                            "optionName": "JR310BT (무선 전용) - 레드",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    }
                ],
            }
        ],
        "totalPrice": 104500
    },
    "error": null
}
```
주문 조회의 응답 데이터를 보면, Item 테이블에 필요한 필드를 알 수 있습니다

## 4. 전체 ER - Diagram
![](https://i.imgur.com/4mQDKLq.png)

### User(사용자)
```json
{
	"username" : "meta",
	"email" : "meta@nate.com",
	"password" : "meta1234!"
}
```
API 응답을 보면, username, email, password 필드가 필요합니다
User 테이블을 보면, 해당 서비스를 사용하는 모든 사용자를 말합니다.

```
- id : PK
- userName : 유저 이름 
- email : 이메일
- password : 비밀번호
- status : 관지라/일반 사용자
- createDate : 생성 날짜
- updateDate : 수정 날짜
```

```SQL
CREATE TABLE User (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status ENUM('NORMAL', 'ADMIN') DEFAULT "NORMAL",
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY `email_UNIQUE` (`email`)
);
```
기본적으로 username,email,password는 NOT NULL을 사용해서, 무조건 데이터를 받게 합니다.

User의 역할을 NORMAL(일반 사용자)를 Default로 가집니다
Uaer 역할을 나누는 이유는 일반 유저와 관리자 테이블을 하나로 쓰는 장점이 있기 때문입니다

user의 메타 정보 관리하는 userMeta를 만들까했지만, 아직은 User 테이블에 속성으로 넣는 것이 적합하다고 생각했습니디.

PK는 AUTO_INCREMENT를 사용해서, 자동으로 증가하도록 하였습ㄱ니다.
PK 특성 상 Unqiue하게 구분 되야하기 때문에, AUTO_INCREMENT가 적합하다 생각했습니다

email에 Unique를 주는 이유는 하나의 아이디로 여러 계정을 생성하지 못하도록하기 위함입니다.

### Product(상품)
상품 상세 보기 API의 반환 값은 아래와 같습니다
```json
{
  "success": true,
  "response": [
    {
      "id": 1,
      "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
      "description": "",
      "image": "/images/1.jpg",
      "price": 1000
    },
    {
      "id": 2,
      "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
      "description": "",
      "image": "/images/2.jpg",
      "price": 2000
    },
  ],
  "error": null
}
```

```
- id : PK
- productName : 제품 명
- description : 제품 설명
- image : 제품 사진
- price : 제품 가격
- createDate : 생성 날짜
- updateDate : 수정 날짜
```

```SQL
CREATE TABLE Product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    productName VARCHAR(255) NOT NULL,
    description TEXT,
    image VARCHAR(255),
    price INT NOT NULL,
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```
API 응답에 따라, 제품 명,제품 설명, 제품 사진,제품 가격 필드가 필요함을 알 수 있습니다
제품 이름과 가격은 필수 내용이기에 NOT NULL 옵션을 주었습니다
### Option(옵션)
제품 상세 보기의 응답을 다시 확인해 봅시다.
```json
{
  "success": true,
  "response": {
    "id": 1,
    "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
    "description": "",
    "image": "/images/1.jpg",
    "price": 1000,
    "starCount": 5,
    "options": [
      {
        "id": 1,
        "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
        "price": 10000
      },
      {
        "id": 2,
        "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
        "price": 10900
      },
      {
        "id": 3,
        "optionName": "고무장갑 베이지 S(소형) 6팩",
        "price": 9900
      },
      {
        "id": 4,
        "optionName": "뽑아쓰는 키친타올 130매 12팩",
        "price": 16900
      },
      {
        "id": 5,
        "optionName": "2겹 식빵수세미 6매",
        "price": 8900
      }
    ]
  },
  "error": null
}
```

```
- id : PK
- productId : FK -> Product.id
- optionName : 옵션 이름
- price : 옵션 가격
- createDate : 생성 날짜
- updateDate : 수정 날짜
```

```SQL
CREATE TABLE Option (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    productId BIGINT,
    optionName VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (productId) REFERENCES Product(id)
);
```
일단, Product ID와 Option 이름과 가격은 필수입니다.

Option 테이블은 Product 테이블과 1 : N 관계를 가집니다.
제품 아래에 여러 옵션을 두는 구조이며, Product Id로 Option들이 어떤 제품에 속하는지 알 수 있습니다.

Option 테이블을 분리하며 얻는 장점은, Product 테이블과 묶여있지 않기 때문에 Option 테이블 필드 확장이 용이해집니다.

### Cart (장바구니)
장바구니 조회 API의 응답 데이터를 확인합니다
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
                        "id": 65,
                        "option": {
                            "id": 1,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    },
                    {
                        "id": 66,
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

```
- id : PK
- userId : FK -> User.id
- optionId : FK -> Option.id
- totalPrice : 장바구니 가격
- quantity : 장바구니에 들어간 갯수
- createDate : 생성 날짜
- updateDate : 수정 날짜
```

```SQL
CREATE TABLE cart (
  id BIGINT PRIMARY KEY,
  userId BIGINT NOT NULL,
  optionId BIGINT NOT NULL,
  totalPrice BIGINT NOT NULL,
  quantity INT NOT NULL,
  createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (userId) REFERENCES User(id),
  FOREIGN KEY (optionId) REFERENCES Option(id)
);
```
일단 어떤 user의 cart인지 구별 할 수 있어야 하기 때문에, user 테이블을 참조하엿습니다.
또한 , 비슷한 매락으로 Option Id와 totalPrice,quantity의 NOT NULL 옵션을 적용합니다
로직 상 Cart 테이블에는 Product와 Option 을 받을 수 있도록 하였습니다.
왜냐하면, Option 테이블이 ProductId를 가지고 있어 어떤 제품을 담당하는지 바로 알 수 있기 때문입니다.

### Order(주문)
```json
{
    "success": true,
    "response": {
        "products": [
            {
                "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                "items": [
                    {
                        "id": 65,
                        "option": {
                            "id": 1,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    }
                ],
            },
            {
                "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
                "items": [
                    {
                        "id": 3,
                        "option": {
                            "id": 1,
                            "optionName": "JR310BT (무선 전용) - 레드",
                            "price": 10000
                        },
                        "quantity": 5,
                        "price": 50000
                    }
                ],
            }
        ],
        "totalPrice": 104500
    },
    "error": null
}
```

```
- id : 주문 번호(PK)
- userId : FK -> User.id
- totalPrice : 장바구니 가격
- createDate : 생성 날짜
- updateDate : 수정 날짜
```


```SQL
CREATE TABLE `order` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  userId BIGINT,
  totalPrice BIGINT,
  createDate DATETIME,
  updateDate DATETIME,
  FOREIGN KEY (userId) REFERENCES User(id),
);
```
어떤 user의 주문인지 알 수 있도록 userId와 전체 가격을 위한 totalPrice 필드를 추가합니다.

과제의 시나리오에 따르면 하나의 주문은 여러 개의 아이템을 가질 수 있습니다.
user id를 가짐으로써, item 테이블의 order ID를 통해 아이템 등을 파악 할 수 있도록합니다
### Item(주문 아이템)
위의 주문 Item 필드를 확인하면 아래와 같은 데이터가 필요합니다

```
- id : PK
- orderId : FK -> Order.id : 주문 번호
- optionId : FK -> Option.id
- quantity : 옵션 갯수
- price : 가격
- createDate : 생성 날짜
- updateDate : 수정 날짜
```


```SQL
CREATE TABLE item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  orderId BIGINT NOT NULL,
  optionId BIGINT NOT NULL,
  quantity INT NOT NULL,
  price INT NOT NULL,
  createDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  FOREIGN KEY (orderId) REFERENCES `order`(id),
  FOREIGN KEY (optionId) REFERENCES `option`(id)
);
```
item 여러 개가 하나의 주문에 들어가기 때문에, 단일 item은 제품의 옵션 정보, 수량, 가격에 대한 정보가 필요합니다
또한, 어떤 주문의 아이템인지 알기 위해, orderId를 추가합니다


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
