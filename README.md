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

***

## 1주차 과제
### part1(유저 관련)

<details>
<summary>회원가입(signup)</summary>
<div markdown="1"> 

- **1번과제 - 요구사항 시나리오를 보고 부족해 보이는 부분 체크**
    BE요구사항 시나리오
    - Request로 회원정보를 받아 데이터베이스에 저장
    - 회원정보 : 이메일, 이름, 비밀번호
    
    부족한 기능
    - 계정 생성 시 양방향 검증을 위해 서버사이드에서도 유효성 검사를 진행해야 함
    - 화면설계서 상에는 존재하지 않는 이메일 중복 확인이 포함되어 있음. 요구사항 시나리오에서 없던 부분이 완성됐기 때문에 시나리오에는 부족한 부분

- **2번 과제 - 기존 서버의 API 주소와 매칭**
    
    ![로그인.PNG](6%2030%2011a4482822fe4873b7f94e17b20a0b23/signup.PNG)
    
    | button | 연결 url | action |
    | --- | --- | --- |
    | signup-1 | http://localhost:8080/products | 메인 화면으로 전환 |
    | signup-2 | http://localhost:8080/check | 이메일의 중복을 확인 |
    | signup-3 | http://localhost:8080/join | 회원가입 폼을 제출 |
    
    **signup-2(이메일 중복 확인)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/check |
    - Request Body
    
    | 이름 | 타입 | 설명 | 필수 |
    | --- | --- | --- | --- |
    | email | string | 이메일 주소 | O |

    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 이메일 중복이 없을 경우 true, 중복인 경우 false |
    | response |  | null |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |
    
    **signup-3(회원가입 폼 제출)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/join |
    - Request Body
    
    | 이름 | 타입 | 설명 | 필수 |
    | --- | --- | --- | --- |
    | username | string | 유저의 이름 | O |
    | email | string | 이메일 주소 | O |
    | password | string | 비밀번호 | O |
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 회원가입 성공 시 true, 실패 시 false |
    | response |  | null |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |
- **3번 과제 - 모든 API를 요청해 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크**
    
    이메일 중복 확인 API
    
    Request URL : [localhost:8080/check](https://www.notion.so/Check-List-cfe19ef053584ca2ac86a1c9eccc7836?pvs=21)
    
    Request Body 
    
    ```json
    {
    "email":"meta@nate.com"
    }
    ```
    
    Response 
    
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```
    
    **부족한 데이터**
    
    - 없음
    
    회원가입 폼 제출 API
    
    Request URL : [http://localhost:8080/join](http://localhost:8080/join)
    
    Request Body 
    
    ```json
    {
    "email":"meta@nate.com",
    "username":"jinjin",
    "password":"meta1234!"
    }
    ```
    
    Response 
    
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```
    
    부족한 데이터
    
    - 전화번호와 주소 데이터가 추가(추가 기능)
</div>
</details>

<details>
<summary>로그인(login)</summary>
<div markdown="1">       

- **1번 과제 - 요구사항 시나리오를 보고 부족해 보이는 부분 체크**
    
    BE요구사항 시나리오
    
    - Request로 이메일과 비밀번호를 입력받아 데이터베이스에 저장
    - 회원 정보를 저장해 둔 데이터베이스를 검색하여 해당 사용자가 유효한 사용자인지 판단
    - 유효한 사용자일 경우 JWT를 반환
    
    부족한 기능
    
    - 로그인 실패 시 실패 사유 반환
    - 카카오를 제외한 타 소셜 로그인 기능

- **2번 과제 - 기존 서버의 API 주소와 매칭**
    
    ![찐로그인.PNG](6%2030%2011a4482822fe4873b7f94e17b20a0b23/login.PNG)
    
    | button | 연결 url | action |
    | --- | --- | --- |
    | login-1 | http://localhost:8080/products | 메인 화면으로 전환 |
    | login-2 | http://localhost:8080/login | 로그인 가능 여부 확인 |
    | login-3 | 화면 전환 | 회원가입 화면으로 전환 |
    
    **login-2(로그인)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/login |
    - Request Body
    
    | 이름 | 타입 | 설명 | 필수 |
    | --- | --- | --- | --- |
    | email | string | 이메일 주소 | O |
    | password | string | 비밀번호 | O |

    - Response Header : JWT 토큰을 return
    
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 로그인 성공 여부 return |
    | response |  | null |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |
    
- **3번 과제 - 모든 API를 요청해 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크**
    
    로그인 API
    
    Request URL : [http://localhost:8080/login](http://localhost:8080/login)
    
    Request Body
    
    ```json
    {
    "email":"ssar@nate.com",
    "password":"meta1234!"
    }
    ```
    
    Response
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled.png)
    
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```
    
    부족한 데이터
    
    - 없음
</div>
</details>

### part2(상품 관련)

<details>
<summary>상품 조회(main)</summary>
<div markdown="1">       

- **1번과제 - 요구사항 시나리오를 보고 부족해 보이는 부분 체크**
    
    BE요구사항 시나리오
    
    - 전체 상품 조회 API를 통해 주문이 가능한 전체 상품 목록을 가져옴
    - 전체 상품 목록에서 특정 상품 카드를 클릭하면 > 해당 상품에 대한 상세 정보를 Response로 반환
    
    부족한 기능
    
    - 전체 상품 목록에서 주문이 가능한 상품 목록을 가져오므로 품절되거나 준비중인 상품에 대한 조치가 필요함.
    - 페이지네이션을 위해 한 페이지에 몇 개의 상품이 표시되는지의 요구사항 필요
    - 배너로 상품 연결, 배너 관련 데이터베이스
- **2번과제 -** **기존 서버의 API 주소와 매칭**
    
    ![메인.PNG](6%2030%2011a4482822fe4873b7f94e17b20a0b23/main.PNG)
    
    | button | 연결 url | action |
    | --- | --- | --- |
    | main-1 | http://localhost:8080/products | main화면으로 전환하면서 상품 리스트 요청 |
    | main-2 | http://localhost:8080/carts | 장바구니 화면으로 이동 |
    | main-3 | 화면 전환 | login화면으로 이동 |
    | main-4 | 미작동(미구현) | 배너 관련 페이지로 이동 |
    | main-5 | 배너 전환 | - |
    | main-6 | http://localhost:8080/products/1 | 상품 세부 정보 api 요청 |
    
    **main-1(해당 화면)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | GET | http://localhost:8080/products/1 |
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 페이지 요청 성공 시 true |
    | response | json array | 상품 정보, 옵션 정보를 반환 |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |
    
    **main-2(장바구니 페이지 요청)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | GET | http://localhost:8080/carts |

    - Response Header : JWT Token 첨부
    
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 요청 성공 시 true |
    | response | json array | 장바구니에 포함된 상품의 정보와 총가격 반환 |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |
    
    **main-6(상품 세부 페이지 요청)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/products/1 |
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 요청 성공 시 true |
    | response | json array | 상품 정보, 옵션 정보를 반환 |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |

- **3번 과제 - 모든 API를 요청해 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크**
    
    상품 조회 API
    
    Request URL : [http://localhost:8080/products](http://localhost:8080/products)
    
    Response 
    
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
    
    부족한 데이터
    
    - 배송 데이터(화면 상 부족한 기능)
    - 배너 사진 데이터 없음(화면 상 부족한 기능)
    - 별점 데이터가 DB에 있지만 API 상에 존재하지 않음

</div>
</details>

<details>
<summary>상품 상세 조회(product)</summary>
<div markdown="1">       

- **1번 과제 - 요구사항 시나리오를 보고 부족해 보이는 부분 체크**
    
    BE요구사항 시나리오
    
    - 해당 상품의 옵션들도 Response로 반환
    - 상품 옵션 선택, 이미 선택된 옵션은 다시 선택 불가
    - 상품 옵션 선택 후 선택한 옵션 재확인하고 수량을 결정할 수 있음
    - 선택한 옵션과 수량에 따라 합계금액이 출력됨
    - 옵션 확인 및 수량 결정 후, 장바구니 담기 버튼 클릭 시 상품들의 배열이 Request body에 담겨 서버로 전달되고, 장바구니에 저장됨
    
    부족한 기능
    
    - 별점과 후기 기능
    - 배송비가 변할 가능성
    - 2~4번째 요구사항(옵션 확인 및 수량 결정)은 서버사이드에서 처리할 경우 옵션 변경, 개수 변경 시 마다 통신을 해야 하는데 비효율적이기 때문에 BE요구사항에는 부적합하다고 생각함
    - 한번 옵션을 클릭한 후에 선택한 옵션 리스트에서 지우는 기능
    - 상품의 상세 설명
    
- **2번 과제 - 기존 서버의 API 주소와 매칭**
    
    ![제품상세.PNG](6%2030%2011a4482822fe4873b7f94e17b20a0b23/product.PNG)
    
    | button | 연결 url | action |
    | --- | --- | --- |
    | product-1 | api 미요청 | 클릭 시 옵션의 수량을 선택할 수 있는 UI 등장 |
    | product-2 | http://localhost:8080/carts/add | 장바구니에 선택한 옵션 추가 |
    | product-3 | 미작동(미구현) | 장바구니에 추가하지 않고 바로 주문 |
    
    **product-2(장바구니에 옵션 추가)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/login |

    - Request Header : JWT Token 첨부
    
    - Request Body
    
    | 이름 | 타입 | 설명 | 필수 |
    | --- | --- | --- | --- |
    | optionId | int | 선택한 옵션의 id | O |
    | quantity | int | 선택한 옵션의 개수 | O |
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 장바구니 담기에 성공 시 true, 실패 시 false |
    | response |  | null |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |

- **3번 과제 - 모든 API를 요청해 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크**
    
    상품 상세 조회 API
    
    Request URL : [http://localhost:8080/products/1](http://localhost:8080/products/1)
    
    Response 
    
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
    
    부족한 데이터
    
    - 없음

</div>
</details>

<details>
<summary>장바구니(cart)</summary>
<div markdown="1">       

- **1번 과제 - 요구사항 시나리오를 보고 부족해 보이는 부분 체크**
    
    BE요구사항 시나리오
    
    - 해당 상품의 옵션들도 Response로 반환
    - 상품 옵션 선택, 이미 선택된 옵션은 다시 선택 불가
    - 상품 옵션 선택 후 선택한 옵션 재확인하고 수량을 결정할 수 있음
    - 선택한 옵션과 수량에 따라 합계금액이 출력됨
    - 옵션 확인 및 수량 결정 후, 장바구니 담기 버튼 클릭 시 상품들의 배열이 Request body에 담겨 서버로 전달되고, 장바구니에 저장됨
    
    부족한 기능
    
    - 장바구니에서 옵션 삭제 기능

- **2번 과제 - 기존 서버의 API 주소와 매칭**
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled%201.png)
    
    | button | 연결 url | action |
    | --- | --- | --- |
    | cart-1 | http://localhost:8080/carts/update | 장바구니 데이터를 반환하면서 주문 페이지 호출 |
    
    **cart-1(주문 페이지 호출)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/carts/update |

    - Response Header : JWT Token 첨부
    
    - Request Body
    
    | 이름 | 타입 | 설명 | 필수 |
    | --- | --- | --- | --- |
    | cartId | int | 해당 옵션의 장바구니 번호 | O |
    | quantity | int | 해당 옵션의 양 | O |
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 요청 성공 시 true |
    | response | json array | 해당 카트에 포함된 옵션 정보와 양, 총 금액을 반환 |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |

- **3번 과제 - 모든 API를 요청해 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크**
    
    장바구니 API
    
    Request URL : [http://localhost:8080/carts](http://localhost:8080/carts/add)
    
    Request Head
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled.png)
    
    Response 
    
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
                            "id": 2,
                            "option": {
                                "id": 1,
                                "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                                "price": 10000
                            },
                            "quantity": 5,
                            "price": 50000
                        },
                        {
                            "id": 3,
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
    
    부족한 데이터
    
    - 없음
    
    장바구니 추가 API
    
    Request URL : [http://localhost:8080/carts/add](http://localhost:8080/carts/add)
    
    Request Header
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled.png)
    
    Request Body
    
    ```json
    [
    {
    "optionId":1,
    "quantity":5
    },
    {
    "optionId":2,
    "quantity":5
    }
    ]
    ```
    
    Response 
    
    ```json
    {
        "success": true,
        "response": null,
        "error": null
    }
    ```
    
    부족한 데이터
    
    - 없음

</div>
</details>

<details>
<summary>주문(order)</summary>
<div markdown="1">       

- **1번 과제 - 요구사항 시나리오를 보고 부족해 보이는 부분 체크**
    
    BE요구사항 시나리오
    
    - 장바구니에 담긴 데이터를 Response로 반환
    - 옵션 데이터 뿐 아니라 해당 옵션들의 상품명, 옵션명도 같이 반환
    - 결제하기 버튼 클릭 시 > 상품을 주문한 것으로 처리
    - Request로 받은 데이터를 데이터베이스에 저장
    
    부족한 기능
    
    - 유저 테이블과 하위 테이블로 전화번호, 배송지 정보가 포함될 가능성이 있음
    - 추후에 유저의 등급이나 등급에 따른 할인가가 붙을 수 있음
- **2번 과제 - 기존 서버의 API 주소와 매칭**
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled%202.png)
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled%203.png)
    
    | button | 연결 url | action |
    | --- | --- | --- |
    | order-1 | 미작동(미구현) | 배송지 정보 수정 페이지 요청 |
    | order-2 | http://localhost:8080/orders/save | 결제해서 주문을 완료하고 주문완료 창 호출 |
    | order-3 | http://localhost:8080/orders/1 | 버튼으로는 없지만 저장 후, 주문 완료 창 호출 |
    
    **order-2(결제 후 저장)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/orders/save |

    - Request Header : JWT Token 첨부

    - Response Body
        
        
        | 이름 | 타입 | 설명 |
        | --- | --- | --- |
        | success | boolean | 요청 성공 시 true |
        | response | json array | 해당 카트에 포함된 옵션 정보와 양, 총 금액을 반환 = 주문과 같음 |
        | error | json array | error 발생 시 메시지와 상태 코드 반환 |
    
    **order-3(주문 결과 호출)**
    
    - Meta
    
    | method | local URL |
    | --- | --- |
    | GET | http://localhost:8080/orders/1 |

    - Request Header : JWT Token 첨부
    
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 요청 성공 시 true |
    | response | json array | 주문 데이터베이스에서 가져온 주문 정보 |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |

    - **product-2(결제 후 저장)**
    - Meta
    
    | method | local URL |
    | --- | --- |
    | POST | http://localhost:8080/orders/save |

    - Request Header : JWT Token 첨부
    
    - Response Body
    
    | 이름 | 타입 | 설명 |
    | --- | --- | --- |
    | success | boolean | 결제 성공 시 true |
    | response | json array | 결제 관련 정보 |
    | error | json array | error 발생 시 메시지와 상태 코드 반환 |
    
- **3번 과제 - 모든 API를 요청해 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크**
    
    주문 확인 API
    
    Request URL : [http://localhost:8080/carts/update](http://localhost:8080/carts/update)
    
    Request Header
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled.png)
    
    Request Body
    
    ```json
    [
    {
    "cartId":2,
    "quantity":10
    },
    {
    "cartId":3,
    "quantity":10
    }
    ]
    ```
    
    Response 
    
    ```json
    {
        "success": true,
        "response": {
            "carts": [
                {
                    "cartId": 2,
                    "optionId": 1,
                    "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                    "quantity": 10,
                    "price": 100000
                },
                {
                    "cartId": 3,
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
    
    부족한 데이터
    
    - 기본 배송지 정보(화면 상 있지만 API 상 없는 정보)
    
    결제 API
    
    request URL : [http://localhost:8080/orders/save](http://localhost:8080/orders/save)
    
    Request Header
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled.png)
    
    Response 
    
    ```json
    {
        "success": true,
        "response": {
            "id": 2,
            "products": [
                {
                    "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                    "items": [
                        {
                            "id": 2,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "quantity": 10,
                            "price": 100000
                        },
                        {
                            "id": 3,
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
    
    부족한 데이터
    
    - 없음

</div>
</details>

<details>
<summary>주문결과(result)</summary>
<div markdown="1">       


- **1번 과제 - 요구사항 시나리오를 보고 부족해 보이는 부분 체크**
    
    BE요구사항 시나리오
    
    - 결과가 성공적으로 처리되면, 주문한 상품들의 정보를 Response로 반환
    
    부족한 기능
    
    - 배송지, 배송 요청사항 등의 정보를 포함하는게 좋을 것 같다.

- **2번 과제 - 기존 서버의 API 주소와 매칭**
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled%204.png)
    
    | button | 연결 url | action |
    | --- | --- | --- |
    | result-1 | localhost:8080/products | 메인화면으로 이동 |

- **3번 과제 - 모든 API를 요청해 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크**
    
    주문결과 API
    
    Request URL : [http://localhost:8080/orders/1](http://localhost:8080/orders/1)
    
    Request Header
    
    ![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled.png)
    
    Response 
    
    ```json
    {
        "success": true,
        "response": {
            "id": 2,
            "products": [
                {
                    "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                    "items": [
                        {
                            "id": 2,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "quantity": 10,
                            "price": 100000
                        },
                        {
                            "id": 3,
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
    
    부족한 데이터
    
    - 없음

</div>
</details>



## 테이블 설계

*기본키: 빨간색, **외래키: 하늘색

유저(id-auto, 이메일, 이름, 비밀번호)

상품(상품ID, 상품명, 상품사진, 별점, 톡별가, 상품설명, 주문가능여부, 생성일자, 수정일자)

옵션(옵션ID, 상품ID, 옵션명, 옵션별 가격, 옵션 재고, 생성일자, 수정일자)

장바구니(장바구니ID, 옵션ID, 옵션번호, 옵션수량, 가격)

주문(주문ID, 유저ID, 배송지, 배송요청사항)

주문상세(주문상세ID, 주문ID, 옵션ID, 수량, 가격)

![Untitled](6%2030%2011a4482822fe4873b7f94e17b20a0b23/Untitled%205.png)

## PDF 과제
### User 테이블
```
CREATE TABLE user (
  id BIGINT NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE DEFAULT NULL,
  userName VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  tel VARCHAR(255) NULL DEFAULT NULL,
  address VARCHAR(255) NULL DEFAULT NULL,
  createDate TIMESTAMP NOT NULL,
  modifyDate TIMESTAMP NOT NULL,
  PRIMARY KEY (id))
  ``` 

### Product 테이블
```
CREATE TABLE product (
  productID BIGINT UNIQUE NOT NULL,
  productName VARCHAR(255) NOT NULL,
  image TEXT NULL DEFAULT NULL,
  starCount DECIMAL(2,1) NULL DEFAULT NULL,
  price INT NOT NULL,
  description TEXT NULL DEFAULT NULL,
  status TINYINT(1) NULL DEFAULT NULL,
  createDate TIMESTAMP NOT NULL,
  modifyDate TIMESTAMP NOT NULL,
  PRIMARY KEY (productID))
  ```
  
### Option 테이블
```
CREATE TABLE option (
  optionID BIGINT NOT NULL UNIQUE,
  productID BIGINT NOT NULL,
  optionName VARCHAR(255) NOT NULL,
  price INT NOT NULL,
  stock BIGINT NOT NULL,
  createDate TIMESTAMP NOT NULL,
  modifyDate TIMESTAMP NOT NULL,
  PRIMARY KEY (optionID),
  INDEX FK_product_TO_option_1 (productID ASC) VISIBLE,
  CONSTRAINT FK_product_TO_option_1
    FOREIGN KEY (productID)
    REFERENCES product (productID))
```
### Cart 테이블
```
CREATE TABLE cart (
  cart_id BIGINT NOT NULL UNIQUE,
  userID BIGINT NOT NULL,
  optionID BIGINT NOT NULL,
  quantity INT NOT NULL,
  price INT NOT NULL,
  PRIMARY KEY (cart_id),
  INDEX FK_user_TO_cart_1 (userID ASC) VISIBLE,
  INDEX FK_option_TO_cart_1 (optionID ASC) VISIBLE,
  CONSTRAINT FK_option_TO_cart_1
    FOREIGN KEY (optionID)
    REFERENCES option (optionID),
  CONSTRAINT FK_user_TO_cart_1
    FOREIGN KEY (userID)
    REFERENCES user (id))
```
### Order 테이블
```
CREATE TABLE order (
  orderID BIGINT NOT NULL UNIQUE,
  userID BIGINT NOT NULL,
  address VARCHAR(255) NOT NULL,
  request VARCHAR(255) NULL,
  orderDate TIMESTAMP NOT NULL,
  PRIMARY KEY (orderID),
  INDEX FK_user_TO_order_1 (userID ASC) VISIBLE,
  CONSTRAINT FK_user_TO_order_1
    FOREIGN KEY (userID)
    REFERENCES user (id))
```
### Item 테이블
```
CREATE TABLE orderdetail (
  orderDetailID BIGINT NOT NULL UNIQUE,
  orderID BIGINT NOT NULL,
  optionID BIGINT NOT NULL,
  quantity INT NOT NULL,
  price INT NOT NULL,
  PRIMARY KEY (orderDetailID),
  INDEX FK_order_TO_orderDetail_1 (orderID ASC) VISIBLE,
  INDEX FK_option_TO_orderDetail_1 (optionID ASC) VISIBLE,
  CONSTRAINT FK_option_TO_orderDetail_1
    FOREIGN KEY (optionID)
    REFERENCES option (optionID),
  CONSTRAINT FK_order_TO_orderDetail_1
    FOREIGN KEY (orderID)
    REFERENCES order (orderID))
```
***
# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

## **과제명**

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
