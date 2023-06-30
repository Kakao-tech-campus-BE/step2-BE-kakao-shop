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

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지) 
>- 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
>- 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
>- 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
>- 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_1주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

## 01. 요구사항 분석/ API 요청 및 응답 시나리오 분석
---
### 01) 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 작성
 

 #### 개별 제품 상세 조회
```
- 제품 상세 정보 설명

 - 톡딜가로 구매하기 기능

 - 별점 or 리뷰 기능
 ```
![](https://velog.velcdn.com/images/hyeee19/post/0ddbd5bf-32ac-47cd-b2a6-95aff3548ed4/image.png)
![](https://velog.velcdn.com/images/hyeee19/post/b462e9c6-5b3c-44f6-bc19-c6735d3aaaa4/image.png)





 

#### 주문하기 

 ```
 - 배송지 정보 수정 기능 (구현 되어있지만 제대로 기능하지 x)

ps. 배송지명 추가 (--이네 )
```
![](https://blog.kakaocdn.net/dn/cwnF9R/btslKlbeZre/b5cYfOFKOCDtckTYubEABk/img.png)



#### 주문 완료

```
 - 주문 번호
 ```
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdNPp5n%2FbtslGANKnBb%2FD8kwBajKm5JJNpBS19QzAK%2Fimg.png)
```
⇒ 주문 번호 x
 ```
---
### 02) 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API 주소를 매칭하여 README 내용 작성
 
 ![](https://velog.velcdn.com/images/hyeee19/post/5cf5056c-af53-4dc6-9737-6d5ed71ce089/image.png)


1. 쇼핑하기 - 전체 상품 목록 조회
- GET
http://localhost:8080/products
 

2. 장바구니 - 장바구니 조회
- GET
http://localhost:8080/carts
 

3. 로그인 / 로그아웃
- POST
http://localhost:8080/login
 

4. 개별 상품 조회
- GET
http://localhost:8080/products/1 (각 숫자)
 
---
#### 회원가입 상세
 
![](https://velog.velcdn.com/images/hyeee19/post/2a0bf195-c4ba-4e11-8a3c-1c571f7433a5/image.png)
1. 이메일 중복체크

- POST
- http://localhost:8080/check
- Request Body ⇒ { "email":"ssarnate.com" }
 

2. 회원가입

- Post
- http://localhost:8080/join
- Request Body ⇒ { "username":"mata", "email":"meta@nate.com", "password":"meta1234!" }
 
<br/>

#### 로그인 상세
 ![](https://velog.velcdn.com/images/hyeee19/post/17f8bfe2-6e32-4802-8c13-3331644ed12e/image.png)
- POST
- http://localhost:8080/login
- Request Body ⇒ { "email":"ssar@nate.com", "password":"meta1234!" }
 
<br/>

#### 장바구니 담기
 
![](https://velog.velcdn.com/images/hyeee19/post/082e4a1b-0b40-4356-84be-701ed4e5dee7/image.png)

- POST
- http://localhost:8080/carts/add
- Request Header
⇒ Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
- Request Body 
⇒ [ { "optionId":1, "quantity":5 }, { "optionId":2, "quantity":5 } ]

---

### 03) 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
 

#### 1. 전체 상품 목록 조회

- API : http://localhost:8080/products
- Response body
```
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
        {
            "id": 3,
            "productName": "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!",
            "description": "",
            "image": "/images/3.jpg",
            "price": 30000
        },
        {
            "id": 4,
            "productName": "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종",
            "description": "",
            "image": "/images/4.jpg",
            "price": 4000
        },
        {
            "id": 5,
            "productName": "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주",
            "description": "",
            "image": "/images/5.jpg",
            "price": 5000
        },
        {
            "id": 6,
            "productName": "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전",
            "description": "",
            "image": "/images/6.jpg",
            "price": 15900
        },
        {
            "id": 7,
            "productName": "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제",
            "description": "",
            "image": "/images/7.jpg",
            "price": 26800
        },
        {
            "id": 8,
            "productName": "제나벨 PDRN 크림 2개. 피부보습/진정 케어",
            "description": "",
            "image": "/images/8.jpg",
            "price": 25900
        },
        {
            "id": 9,
            "productName": "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감",
            "description": "",
            "image": "/images/9.jpg",
            "price": 797000
        }
    ],
    "error": null
}
```
- 추가할만한 데이터
> 강의에서 언급하신 것과 같이 별점 기능을 추가한다면, 별점 데이터를 추가해야 할 것 같다.
 

#### 2. 개별 상품 상세 조회

- API : http://localhost:8080/products/1
- Response Body
```
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
- 추가할만한 데이터
 제품 상세 정보 설명 데이터
 별점 or 리뷰 데이터
 배송비
 


#### 3. 이메일 중복 체크

- API : http://localhost:8080/check
- Response body
```
{
    "success": false,
    "response": null,
    "error": {
        "message": "이메일 형식으로 작성해주세요:email",
        "status": 400
    }
}
```
- 추가 데이터 x

#### 4. 회원가입

- API : http://localhost:8080/join
- 회원가입 성공 시 Response body
```
{
"success": true,
"response": null,
"error": null
}
```
- 회원가입 실패 시 Response body
```
{
    "success": false,
    "response": null,
    "error": {
        "message": "8에서 20자 이내여야 합니다.:password",
        "status": 400
    }
}
```
- 추가 데이터 x
 

#### 5. 로그인

- API : http://localhost:8080/login
- Response body
```
{
	"success": true,
	"response": null,
	"error": null
}
```
- 추가 데이터 x
 

#### 6. 장바구니 담기

- API : http://localhost:8080/carts/add
- Response body
```
{
	"success": true,
	"response": null,
	"error": null
}
```
- 배송비가 있다면 배송비 데이터 ?
 

#### 7. 장바구니 조회

- API : http://localhost:8080/carts
- Response body
```
{
	"success": true,
	"response": {
		"products": [
			{
				"id": 1,
				"productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션
외 주방용품 특가전",
				"carts": [
					{
						"id": 4,
						"option": {
							"id": 1,
							"optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 
4종",
							"price": 10000
						},
						"quantity": 5,
						"price": 50000
					},
					{
						"id": 5,
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
- 장바구니 옵션 삭제 기능
- 최근 본 상품 데이터 or 연관있는 상품 데이터
 

#### 8. 주문하기 - 장바구니 수정

- API : http://localhost:8080/carts/update
- Response body
```
{
	"success": true,
	"response": {
		"carts": [
			{
				"cartId": 4,
				"optionId": 1,
				"optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
				"quantity": 10,
				"price": 100000
			},
			{
				"cartId": 5,
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
- 장바구니 옵션 삭제 기능
- 최근 본 상품 데이터 or 연관있는 상품 데이터
 

#### 9. 결제하기 - 주문 인서트

- API : http://localhost:8080/orders/save
- Response body
```
{
	"success": true,
	"response": {
		"id": 2,
		"products": [
			{
				"productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션
외 주방용품 특가전",
				"items": [
					{
						"id": 4,
						"optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
						"quantity": 10,
						"price": 100000
					},
					{
						"id": 5,
						"optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
						"quantity": 10,
						"price": 109000
					}
				]
			}
		],
		"totalPrice": 209000
	},
	"error": null
}
```
- 배송비 있다면 배송비 합계 데이터
 

#### 10. 주문 결과 확인

- API : http://localhost:8080/orders/1
- Response body
```
{
	"success": true,
	"response": {
		"id": 2,
		"products": [
			{
				"productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션
외 주방용품 특가전",
				"items": [
					{
						"id": 4,
						"optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
						"quantity": 10,
						"price": 100000
					},
					{
						"id": 5,
						"optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
						"quantity": 10,
						"price": 109000
					}
				]
			}
		],
		"totalPrice": 209000
	},
	"error": null
}
```
- 주문 번호 데이터


---

### 04) 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
 

카카오테크를 진행하며 SQL에 대해 배우긴 했지만, 사용법과 실습 위주로 배웠기 때문에 이번 과제를 진행하며 SQL 문법들을 하나씩 익혀가보려고 한다.


#### User 테이블

- id : PK
- username : 사용자 이름
- email : 사용자 이메일 ⇒ UNIQUE
- password : 사용자 패스워드

user 테이블에 필요한 데이터는 다음과 같으며, 이를 토대로 sql문은 다음과 같이 작성할 수 있다.

```
CREATE TABLE user (
	id int(11) NOT NULL AUTO_INCREMENT,
	email varchar(100) NOT NULL,
	password varchar(256) NOT NULL,
	username varchar(45) NOT NULL,
 	PRIMARY KEY (id),
	UNIQUE KEY email_UNIQUE (email)
);
```

>User 테이블에서 id, email, password, username은 필수적으로 입력받아야 하는 필드이므로, 'NOT NULL'과 같은 제약 사항을 넣어준다. 

>id 값의 경우 PRIMARY KEY('id')로 입력하여 PK로 설정해주었으며 AUTO_INCREMENT는 데이터가 입력될 때마다 자동으로 1씩 증가시켜 주는 속성으로, id값이 입력될 때마다 1씩 증가하도록 한다.

<참고>
https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=goddlaek&logNo=221005664911 

>email 필드는 UNIQUE KEY를 걸어주어 해당 열에 중복된 값을 허용하지 않으며, 각 행은 해당 열의 값이 고유하도록 한다. 즉, 다른 유저가 같은 이메일로 가입을 할 수 없도록 한다.

<참고>
https://thisisprogrammingworld.tistory.com/133

#### Product 테이블

- id : PK
- product_name : 상품명
- description : 상품 설명
- image : 상품 사진
- price : 상품 가격

Product 테이블에 필요한 데이터는 다음과 같고, SQL문을 다음과 같이 작성한다.

```
CREATE TABLE product (
	id int(11) NOT NULL AUTO_INCREMENT,
	product_name varchar(100) NOT NULL,
	description varchar(1000),
	image varchar(500),
	price int(11),
	PRIMARY KEY (id)
);
 
```
<참고>
https://heavening.tistory.com/85
https://m.blog.naver.com/darkxpavmffj/90089405466



#### Option 테이블

- id : PK
- product_id : FK ⇒ priduct(id)
- option_name : 옵션 이름
- price : 옵션 가격

> Product 테이블과 Option 테이블을 별도로 생성하는 이유는, 이 둘의 관계가 1 대 N 관계이기 때문이다. 데이터 베이스에서는 중복을 최소화하고 데이터의 일관성을 유지 및 효율적으로 구성하기 위해 데이터를 정규화한다. 이때 Product 테이블과 Option 테이블을 나누어 설계함으로써 중복된 데이터를 피하고 데이터의 일관성과 무결성을 유지할 수 있다.
또한, 옵션의 가격, 재고 수량, 제조사 정보 등을 추가로 저장해야 할 수도 있기 때문에, 이러한 확장성과 유연성을 위해 option 테이블을 따로 생성하고 필요한 속성을 추가할 수 있다.

 
<참고>
https://appmaster.io/ko/blog/deiteo-jeonggyuhwaran-mueosinga

```
CREATE TABLE option (
	id int(11) NOT NULL AUTO_INCREMENT,
	product_id int(11) DEFAULT NULL,
	option_name varchar(100) NOT NULL,
	price int(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (product_id) REFERENCES product (id)
);
```

>FK(Foreign Key)를 사용하여 Product 테이블의 id을 불러와 후에 조인하여 제품과 옵션의 데이터를 한번에 가져올 때 사용하는 제약조건(constraint)를 걸어준다.

<참고>
https://jerryjerryjerry.tistory.com/49
https://runtoyourdream.tistory.com/129

>실습 멘토님과의 Q&A 시간에서 외래키를 사용하지 않고 인덱스를 사용하여 참조하는 방법을 많이 사용한다고 하시면서 성능 문제를 들어주셨는데, 외래키를 사용하지 않고 인덱스를 사용하는 방법도 추후에 알아보고 싶다.
 

#### Cart 테이블

- id : PK
- user_id : FK ⇒ user(id)
- option_id : FK ⇒ option(id)
- total_price : 장바구니 가격
- quantity : 장바구니에 들어간 갯수

 ```
CREATE TABLE cart (
	id int(11) NOT NULL AUTO_INCREMENT,
	user_id int(11) NOT NULL,
	option_id int(11) NOT NULL,
	quantity int(11) NOT NULL,
	total_price int(11) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES user(id),
	FOREIGN KEY (option_id) REFERENCES option(id),
);
```
> user 테이블의 id, option 테이블의 id 정보가 필요하기 때문에 두 키를 참조한다. 어떤 제품인지에 대한 정보가 필요없는 이유는 Option 테이블에 이미 해당 옵션의 제품 정보를 가지고 있기 때문이다.
 

#### Order 테이블

- id : 주문 번호(PK)
- user_id : FK ⇒ user(id)
- totalPrice : 장바구니 가격

```
CREATE TABLE order (
	id int(11) NOT NULL AUTO_INCREMENT,
	user_id int(11) NOT NULL,
	totalprice int(11),
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES user(id)
);
```

> Order 테이블에는 유저 정보에 대한 필드만 필요한 이유는 하나의 주문은 여러 명의 유저를 가질 수 없기 때문에 주문 테이블에서는 유저의 정보만 가지고, 아이템 테이블에서 주문에 대한 정보와 구매된 제품 정보를 가지고 있으면 된다.
 

#### Item 테이블

 

- id : PK
- orderId : FK ⇒ order(id)
- optionId : FK ⇒ option(id)
- quantity : 옵션 갯수
- price : 가격

 
```
CREATE TABLE item (
    id int(11) NOT NULL AUTO_INCREMENT,
    option_id int(11) NOT NULL,
    quantity int(11) NOT NULL,
    price int(11) NOT NULL,
    order_id int(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES order(id),
    FOREIGN KEY (option_id) REFERENCES option(id),
);
```
> 구매된 제품의 옵션에 대한 정보가 필요하고 구매한 수량과 가격에 대한 정보가 필요하다. 해당 옵션의 가격이 달라질수 있으므로, (구매 시기의 가격과 현재의 가격의 변동 등) 가격 필드를 추가적으로 넣어준다. 
또한, 어떤 주문의 아이템인지에 대한 정보가 필요하다.
 

#### ER-Diagram
![](https://velog.velcdn.com/images/hyeee19/post/359b6690-8462-4c2b-8abc-7e7e26fca944/image.png)




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
