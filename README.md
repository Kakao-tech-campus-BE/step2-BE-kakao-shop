# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

<br>

# 과제 (부산대BE_김정도)
## 1주차 과제  

#### ( 요구사항을 보고 부족해보이는 기능 )
```
- 사용자의 회원정보를 수정할 수 있는 마이페이지
- 주문 취소 및 변경
```
⇒  대체적으로 CRUD 중 Update와 Delete에 관련한 기능이 부족하다는 생각이 든다.  

⇒  GET과 POST 메소드만 API로 사용하기로 했으니, 한편으로는 이에 맞게 요구기능이 간소화된 것 같기도 함.  
(물론 POST의 Body에 담아보내서 해결할 순 있겠지만)  
<br>  
#### ( 화면설계서와 API 주소 매칭하기 )  
```
(기능 1) [POST] 회원가입 : `/join`
(기능 2) [POST] 로그인 : `/login`
(기능 3) [POST] 로그아웃 : `/logout` -> API 시나리오에 없음
(기능 4) [GET] 전체 상품 목록 조회 : `/products`
(기능 5) [GET] 개별 상품 상세 조회 : `/products/{product_id}`
(기능 6) 상품 옵션 선택 : (Front-end Side)
(기능 7) 옵션 확인 및 수량 결정 : (Front-end Side)
(기능 8) [POST] 장바구니 담기 : `/carts/add`
(기능 9) [POST] 장바구니 보기 : `/carts`
(기능10) 장바구니 상품 옵션 확인 및 수량 결정 : (Front-end Side)
(기능11) [POST] 주문 : `carts/update`
(기능12) [POST] 결제 : `orders/save`
(기능13) [GET] 주문 결과 확인 : `orders/{orders_id}`

```


#### ( 배포된 API들을 분석하고 Response 중에서 부족한 데이터 찾아보기 )
```
- 기능 1 [POST] 회원가입 : `/join`

Request Header : 아직 로그인해서 JWT를 발급받기 전

Request Body :
	{
		"username":"{입력받은 유저명}",
		"email":"{입력받은 이메일}",
		"password":"{입력받은 패스워드}"
	}
	
Response Body :

	(회원가입 성공) {
		"success": true,
		"response": null,
		"error": null
	}

	(회원가입 실패) {
		"success" : false,
		"response" : null,
		"error" : {
			(이메일 형식 오류) {
				"message" : "이메일 형식으로 작성해주세요:email"
			}
			(비밀번호 형식 오류 - 표현식) {
				"message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password"
			}
			(이메일 중복 오류) {
				"message": "동일한 이메일이 존재합니다 : {입력받은 이메일}"
			}
			(비밀번호 형식 오류 - 길이) {
				"message": "8에서 20자 이내여야 합니다.:password"
			},
				"status" : 400
		}
	}
```  
<br>  

```
- 기능 2 [POST] 로그인 : `/login`

Request Header : 아직 로그인해서 JWT를 발급받기 전

Request Body :
	{
		"email":"{입력받은 이메일}",
		"password":"{입력받은 패스워드}"
	}
	
Response Body :

	(로그인 성공) {
		"success": true,
		"response": null,
		"error": null
	}

	(로그인 실패) {
		"success" : false,
		"response" : null,
		"error" : {
			(이메일 형식 오류) {
				"message" : "이메일 형식으로 작성해주세요:email",
				"status" : 400
			}
			(비밀번호 형식 오류 - 표현식) {
				"message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
				"status" : 400
			}
			(비밀번호 형식 오류 - 길이) {
				"message": "8에서 20자 이내여야 합니다.:password",
				"status" : 400
			}
			(가입되지 않은 사용자) {
				"message": "인증되지 않았습니다",
				"status" : 401
			}
		}
	}
 ```  

<br>

```
- 기능 3 [POST] 로그아웃 : `/logout`

Request Header : 쿠키의 Authorization Key에 JWT 값을 담아보냄

Request Body :
	{
		뭘 담아보내건 같은 500 Error가 발생
	}
	
Response Body :

	(로그아웃 실패) {
		"success" : false,
		"response" : null,
		"error" : {
				"message": "Request method 'GET' not supported",
        "status": 500
			}
	}
```
=> GET으로 보내나 POST로 보내나 500 Error를 발생시키는 버그가 있음  
=> 보다 정확하고 명확한 에러 처리에 대한 구현이 필요해보임  

<br>

```
- 기능 4 [GET] 전체 상품 목록 조회 : `/products`

Request Header : 필요없음
Request Parameter :
	`?page={num} (default: 0)`
	
Response Body :
	(조회 성공) {
		"success": true,
		"response": [
			(id 1~9까지 반복) [+num*9] {
				"id": 1,
				"productName": "{해당 product_id의 제품명}",
				"description": "",
				"image": "{해당 product_id의 이미지 URL}",
				"price": {해당 product_id의 가격}
			}, x 9
		],
		"error": null
	}
	
	(렌더링 가능한 페이지를 넘은 경우) {
		"success": true,
		"response": [],
		"error": null
	}

```

=> 요청 가능한 페이지를 넘겨도 오류가 발생하지 않는다  
=> 페이지네이션을 구현하려면 Front-end와 요청한 페이지 넘버를 서로 확인할 필요성도 있어보인다  

<br>

```
- 기능 5 [GET] 개별 상품 상세 조회 : `/products/{product_id}`

Request Header : 필요없음
Request Parameter : 없음
	
Response Body :
	(조회 성공){
		"success": true,
		"response": {
		        "id": {해당 product_id},
		        "productName": "{해당 product의 제품명}",
		        "description": "",
		        "image": "{해당 product_id의 이미지 URL}",
		        "price": {해당 product_id의 가격},
		        "starCount": {해당 product_id의 rate},
		        "options": [
				(해당 product의 option 개수만큼 반복) {
					"id": {해당 option_id},
					"optionName": "{해당 option_id의 옵션명}",
					"price": {해당 product_id의 가격}
				}, [x N]
		        ]
		},
		"error": null
	  }
	  
	(보유한 product_id 데이터 범위를 넘어선 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "해당 상품을 찾을 수 없습니다 : {요청한 product_id}",
			"status": 404
		}
	}

```

<br>  

```
- 기능 8 [POST] 장바구니 담기 : `/carts/add`
Request Header : 쿠키의 Authorization Key에 JWT 값을 담아보냄
Request Body :
	[
		(client가 선택한 option의 개수만큼 반복) {
			"optionId": {선택한 option의 id},
			"quantity": {선택한 option의 개수}
		}, [x N]
	]
	
Response Body :
	(장바구니 담기 성공) {
		"success": true,
		"response": null,
		"error": null
	}
	
	
	(JWT가 유효하지 않은 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "인증되지 않았습니다",
			"status": 401
		}
	}
	
	(동일한 옵션을 장바구니에 다시 추가할 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "장바구니 담기 중에 오류가 발생했습니다 : could not execute statement; SQL [n/a]; constraint [\"PUBLIC.UK_CART_OPTION_USER_INDEX_4 ON PUBLIC.CART_TB(USER_ID NULLS FIRST, OPTION_ID NULLS FIRST) VALUES ( /* key:1 */ 1, 1)\"; SQL statement:\ninsert into cart_tb (id, option_id, price, quantity, user_id) values (default, ?, ?, ?, ?) [23505-214]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
			"status": 500
		}
	}
	
	(보유한 범위를 넘어선 option_id를 요청할 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "해당 옵션을 찾을 수 없습니다 : {요청한 option_id}",
			"status": 404
		}
	}

```

<br>  

```
- 기능 9 [POST] 장바구니 보기 : `/carts`

Request Header : 쿠키의 Authorization Key에 JWT 값을 담아보냄
Request Body : 없음
	
Response Body :
	(장바구니 조회 성공) {
		"success": true,
		"response": {
			"products": [
				(장바구니에 담긴 product_id의 개수만큼 반복) {
					"id": {해당 옵션을 포함하는 product_id},
					"productName": "{해당 product의 제품명}",
					"carts": [
						(해당 product에서 장바구니에 추가된 옵션 수만큼 반복) {
							"id": {cart_id = 장바구니에 추가된 순서대로 Auto-Increment},
							"option": {
								"id": {장바구니에 추가된 option_id},
								"optionName": "{해당 option_id의 옵션명}",
								"price": {해당 option_id의 가격}
							},
							"quantity": {장바구니에 추가한 수량},
							"price": {옵션 가격 x 추가한 수량}
						}, [x N1]
					]
				}, [x N2]
			],
			"totalPrice": {장바구니에 추가된 모든 price의 합}
		},
		"error": null
	}
	
	
	(JWT가 유효하지 않은 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "인증되지 않았습니다",
			"status": 401
		}
	}

```

<br>  

```
- 기능11 [POST] 주문 : `carts/update`

Request Header : 쿠키의 Authorization Key에 JWT 값을 담아보냄
Request Body :
	[
		(수정한 품목 수만큼 반복){
			"cartId": {수정한 품목의 cartId},
			"quantity": {주문하기 화면에서 수정한 quantity}
		}, [x N]
	]

	
Response Body :
	(장바구니 수정 성공) {
		"success": true,
		"response": {
			"carts": [
				(장바구니에 추가된 옵션의 개수만큼 반복){
					"cartId": {cartId},
					"optionId": {해당하는 option_id},
					"optionName": "{해당 option_id의 옵션명}",
					"quantity": {장바구니에 추가한 수량},
					"price": {옵션 가격 x 추가한 수량}
				}, [x N]
			],
			"totalPrice": {장바구니에 추가된 모든 price의 합}
		},
		"error": null
	}
	
	(장바구니 수정 실패) {
		"success": false,
		"response": null,
		"error": {
			"message": "장바구니에 없는 상품은 주문할 수 없습니다 : {요청한 cart_id}",
			"status": 400
		}
	}

	(JWT가 유효하지 않은 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "인증되지 않았습니다",
			"status": 401
		}
	}

```

<br>  

```
- 기능12 [POST] 결제 : `orders/save`

Request Header : 쿠키의 Authorization Key에 JWT 값을 담아보냄
Request Body : 없음
	
Response Body :
	(결제 성공) {
		"success": true,
		"response": {
			"id": {order_id = 주문한 순서대로 Auto-Increment},
			"products": [
				(주문된 product_id의 개수만큼 반복){
					"productName": "{해당하는 product_id의 상품명}",
					"items": [
						(구매한 상품 중 해당 product_id에 포함되는 개수만큼 반복){
							"id": {cart_id},
							"optionName": "{해당하는 품목의 옵션명}",
							"quantity": {구매한 개수},
							"price": {옵션 가격 x 구매한 개수}
						}, [x N1]
					]
				}, [x N2]
			],
			"totalPrice": {구매한 상품의 모든 price 총합}
		},
		"error": null
	}
	
	(장바구니에 내역이 존재하지 않을 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "장바구니에 아무 내역도 존재하지 않습니다",
			"status": 404
		}
	}

	(JWT가 유효하지 않은 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "인증되지 않았습니다",
			"status": 401
		}
	}

```

<br>

```
- 기능13 [GET] 주문 결과 확인 : `orders/{orders_id}`
Request Header : 쿠키의 Authorization Key에 JWT 값을 담아보냄
Request Body : 없음
	
Response Body :
	(조회 성공) {
		"success": true,
		"response": {
			"id": {order_id = 주문한 순서대로 Auto-Increment},
			"products": [
				(주문된 product_id의 개수만큼 반복){
					"productName": "{해당하는 product_id의 상품명}",
					"items": [
						(구매한 상품 중 해당 product_id에 포함되는 개수만큼 반복){
							"id": {cart_id},
							"optionName": "{해당하는 품목의 옵션명}",
							"quantity": {구매한 개수},
							"price": {옵션 가격 x 구매한 개수}
						}, [x N1]
					]
				}, [x N2]
			],
			"totalPrice": {구매한 상품의 모든 price 총합}
		},
		"error": null
	}
	
	(해당 주문 내역이 존재하지 않을 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "해당 주문을 찾을 수 없습니다 : {요청한 order_id}",
			"status": 404
		}
	}

	(JWT가 유효하지 않은 경우) {
		"success": false,
		"response": null,
		"error": {
			"message": "인증되지 않았습니다",
			"status": 401
		}
	}

```

<br>

```
- [POST] 이메일 중복 체크 : `/check`

Request Header : 아직 로그인해서 JWT를 발급받기 전

Request Body :
	{
		"email":"{입력받은 이메일}",
	}
	
Response Body :

	(중복 체크 통과) {
		"success": true,
		"response": null,
		"error": null
	}

	(실패) {
		"success" : false,
		"response" : null,
		"error" : {
			(이메일 형식 오류) {
				"message" : "이메일 형식으로 작성해주세요:email",
				"status" : 400
			}
			(중복된 이메일 오류) {
				"message": "동일한 이메일이 존재합니다 : ssar@nate.com",
				"status": 400
			}
		}
	}
 ```
 
<br>

#### ( ERD 테이블 설계 )
```
Week1_ERD.png 파일에 첨부
```
![Week1_ERD](https://github.com/Rizingblare/step2-BE-kakao-shop/assets/77480122/f389dd2a-bfe8-4b57-a7cc-bb465696bbd0)

<br>

---
```
END
```
---

<br>

# 과제 설명

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

<br>

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

<br>

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

<br>

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

<br>

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
