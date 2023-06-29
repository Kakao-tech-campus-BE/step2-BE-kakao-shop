# 1주차 과제

**과제명**

1. 요구사항분석/API요청 및 응답 시나리오 분석

2. 요구사항 추가 반영 및 테이블 설계도

**과제 설명**

1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.

2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)

3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.

4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.

**1주차**

부족한 기능에 대한 요구사항을 미리 예상할 수 있는가 (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지 이런 부분들을 생각하였는지)

---

요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가 (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)

---

응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가 (예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)

---

테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)

---

테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

<aside>
💡 실습 멘토님과의 QNA

Q : SQL 상의 테이블과 JPA 간의 엔티티를 같은 선상으로 봐도 괜찮은가?
A : 같은 선상으로 보면 안된다. 테이블과 객체는 다르게 보아야 한다. 즉 테이블 설계와 객체(JPA) 설계는 다르다.

Q : 현업에서는 DB 설계가 먼저 인가 아니면 JPA 설계가 먼저인가 
A : 현업에서는 DB가 설계된 경우에서 개발이 되는 경우가 많기에, 이 경우에 자연스럽게 DB 설계가 먼저 진행된다. 

Q : 만약 DB 설계가 되어있지 않는 토이 플젝 같은 경우에는 어떤 설계를 먼저 진행하는지
A : 테이블을 먼저 설계한다. 그 이유는 JPA를 설계하는 다는 것은 객체를 설계 한다는 것인데, 객체는 시스템의 변경에 쉽게 변경되는데, 데이터베이스는 그런 변경마다 쉽게 변경되면 안되기 때문이다.

Q : 현업에서는 ddl-auto를 사용안하는가?
A : 사용을 안한다. 현업에서는 DB 팀이 있는 경우가 많기에, DB 팀이 최적화 시켜놓은 DB에 맞춰서 백엔드가 이용하는 형식이다. 물론 전체적인 틀을 잡는데 사용하면 편리하지만, 그 이후 최적화를 반드시 적용해야한다.

</aside>

### 1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.

*부족한 기능에 대한 요구사항을 미리 예상할 수 있는가 (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지 이런 부분들을 생각하였는지)*

- ~ 한다. : 추후에 반드시 생겨야 할 API, ~ 좋을 것 같다. : 추후에 생긴다면 좋을 것 같은 API
- 전체 상품 목록 조회간 상품에 대한 별점 API도 보여져야 한다..
- 상품에 대한 댓글 갯수 API도 추가 되어야 한다.
- 만약 상품이 할인 중이라면 원가와 할인가도 보여주면 좋을 것 같다.
- 상품에 대한 설명 API도 있어야 한다.
- 상품의 남은 수량 API도 있으면 좋을 것 같다.
- 로그인시 몇 회 이상 틀릴 경우에 일정 시간동안 요청을 보내지 못하게하는 API가 있으면 좋을 것 같다.
    - 아마 내 생각으로는 해당 아이디에 관한 쿠키를 뿌려주고, 해당 쿠키가 만료되지 않는다면 계속 요청을 거부하는 식으로 구현되지 않을까 싶다.
- 옵션을 선택할 때 원가를 기준으로 +-에 해당하는 가격을 보여주면 좋을 것 같다.
- 장바구니에서 상품을 아예 삭제하는 기능도 있어야 한다.
- 장바구니 수정시 POST 요청을 매번 보내는게 아니라 클라이언트에서 변경되는 갯수를 가지고 있다가 최종적으로 결정 및 해당 페이지를 나갈 때의 갯수를 PUT으로 보내면 서버에 부하가 덜할 것 같다.
- 주문 상품 정보시 해당 주문이 된 시간도 알려주면 좋을 것 같다.
- 주문 상품 정보시 원가와 할인가도 보여주면 좋을 것 같다.
- 최근에 본 상품에 대한 정보들을 출력하는 API가 있으면 좋을 것 같다.
    - 쿠키, 세션으로 구현할 수 있을 것 같다.
    
    [Spring : session과 cookie (최근 본 상품, 장바구니)](https://dev-doodoo.tistory.com/222)
    
- 결제 관련 API와 상품 등록 API가 추가되어야 한다.
- 특딜가 구매 API도 생겨야 한다.

### 2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)

*요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가 (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)*

---

*응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가 (예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)*

화면 설계 시나리오의 스크린샷의 버튼 등과 어떤 API가 연동되어 있는지를 작성해 주시면 됩니다!

- 회원가입(*화면 설계*) & http://~/join(*API 주소*)
    - 회원가입 버튼 클릭시에 User API와 연동되어 User를 등록합니다.
    - 추가 되어야 할 것
        - 이메일 중복 API는 있는데, 화면 설계상 이메일을 중복 확인하는 버튼은 존재하지 않습니다. 따라서 회원가입 화면에 이메일을 중복 확인하는 버튼이 필요할 것 같습니다.

- 로그인(*화면 설계*) & http://~/login(*API 주소*)
    - 로그인 버튼 클릭시에 User API와 연동되어 User를 인증합니다.
    - 인증이 성공하면, 응답 헤더에 JWT 토큰을 생성해서 보내줍니다.
    - 추가 되어야 할 것
        - 현재 access Token만 응답으로 보내주는 것 같은데, Refresh Token 또한 응답으로 보내주어야 할 것 같습니다.
        - 로그인 실패시에 각 필드값의 실패 정보를 보내주면 보안적으로 위협이 될 수 있다고 생각합니다. 이에 따라 단순히 로그인에 실패했다는 error.message만 보내주는게 좋다고 생각됩니다.
    
- 로그아웃(*화면 설계*) & 없음
    - 로그아웃 버튼 클릭시에 유저의 헤더에 존재하는 JWT를 삭제하거나 Redis를 이용해 블랙 리스트에 추가함으로써 로그아웃을 합니다.
    - 추가 되어야 할 것
        - 로그아웃 API가 필요할 것 같습니다.

- 전체 상품 목록 조회(*화면 설계*) & http://~/products(*API 주소*)
    - 해당 페이지에 들어갈 경우 Paging 파라미터에 맞게 페이징된 Product Api들이 출력됩니다.
    - 특정 상품을 클릭하면 해당 특정 상품의 정보를 출력해줍니다.
    - 추가 되어야 할 것
        - 화면 설계간에는 특별가와 무료 배송이 존재하는데, Rroduct API에는 존재하지 않기에 추가해주야 합니다.
        - 타 쇼핑 웹 사이트들같이 무한 스크롤을 적용하고자 할 때는 추가적으로 size라는 쿼리 파라미터도 받아야 합니다.

어쩔 때 쿼리 파라미터를 받고, 어쩔 때 쿼리 스트링을 받을까?

- Path 변수를 사용할 때
    - 어떤 **resource를 식별**하고 싶으면 **Path Variable**을 사용
- 쿼리 파라미터를 사용할 때
    - **정렬이나 필터링**을 한다면 **Query Parameter**를 사용

- 개별 상품 목록 조회(*화면 설계*) & http://~/products/{productsPK}(*API 주소*)
    - Path Variable를 통해 받았던 PK에 해당하는 상품의 정보를 출력하는 API입니다.
    - 추가 되어야 할 것
        - 상품의 옵션 선택을 한 후 구매하기나 장바구니 담기를 통해서 상품, 옵션, 장바구니 AP를 호출하여 상품을 등록하는 API가 필요합니다.
        - 화면 설계간에는 특별가와 배송비가 존재하는데, Rroduct API에는 존재하지 않기에 추가해주야 합니다.
        - 화면 설계에 존재하는 특딜가에 관한 API가 필요합니다.

- 장바구니 담기(*화면 설계*) & http://~/carts/add(*API 주소*)
    - 장바구니 담기 버튼을 누르면 장바구니 API가 호출되어서, 선택했던 상품과 해당 상품의 옵션들의 가격과 갯수를 저장하게 됩니다.
    - 추가 되어야 할 것
        - 화면 설계에 존재하는 특딜가로 구매하기 API가 필요합니다.
        - 화면 설계에 존재하는 배송 방법과 배송비를 전송하고, 저장하는 API가 필요합니다.

- 장바구니 조회(*화면 설계*) & http://~/carts(*API 주소*)
    - 장바구니에 추가를 하였던 상품들의 정보를 출력하는 API입니다.

- 장바구니 수정 및 주문(*화면 설계*) & http://~/carts/update(*API 주소*)
    - 각 상품에 존재하는 갯수 수정 버튼을 통해서 상품 갯수를 추가 및 감소시킬 수 있습니다.
    - 주문하기를 누르면 주문 API가 호출됩니다.

- 결제하기(*화면 설계*) & http://~/orders/save(*API 주소*)
    - 결제하기 버튼을 누르면 해당 장바구니에 있던 상품들이 장바구니에서 제거되고, 주문 API가 호출됩니다.
    - 추가 되어야 할 것
        - 화면 설계에 존재하는 법적 고지에 관한 API가 필요합니다.
        - 상품의 정보를 출력할 때 상품 이미지 필드 또한 API에 추가되면 고객으로써 어떤 상품을 장바구니에 담았었는지 리마인드하기가 좋을 것 같습니다.

- 주문 결과 확인하기(*화면 설계*) & http://~/orders/{orderPK}(*API 주소*)
    - 주문에 대한 API를 출력합니다.
    - 쇼핑 계속하기를 누를 경우 리다이렉트되는 주소에 맞는 API를 호출하여줍니다.

### 3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.

[Postman을 이용해서 API 호출 테스트 "잘" 해보기 - 인증 헤더에 토큰 추가 자동화](https://velog.io/@dhk22/Postman을-이용해서-API-호출-테스트-잘-해보기-인증-헤더에-토큰-추가-자동화)

- http://~/check(*API 주소*)
    - 중복되는 이메일이 있는지 체크합니다.
    - **Response**
        
        ```json
        {
            "success": true,
            "response": null,
            "error": null
        }
        ```
        

- http://~/join(*API 주소*)
    - 회원가입 버튼 클릭시에 User API와 연동되어 User를 등록합니다.
    - **Response**
        
        ```json
        {
            "success": true,
            "response": null,
            "error": null
        }
        ```
        
    - **추가 되어야 할 데이터**
        - 나중에 회원가입 후 ~~님 회원가입에 축하드려요 같은 이벤트나 다른 기능이 추가될 수 있기에 response로 username을 넘겨주는 건 어떨까?? ← 보안적으로 문제가 있지는 않을까?
            - 보안적으로 뺏겨도 괜찮은 데이터는 그러면 뭘까??

- http://~/login(*API 주소*)
    - 로그인 버튼 클릭시에 User API와 연동되어 User를 인증합니다.
    - 인증이 성공하면, 응답 헤더에 JWT 토큰을 생성해서 보내줍니다.
    - **Response**
        - Header : Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YXJkeWFyZEBsaWtlbGlvbi5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjQsImV4cCI6MTY4ODAxNzI0Nn0.H_n_Guo28k-JOJ3-5ck4kMjyguCpwys0q54UQBTkWHk4Pt_X9eI-C_qz51ak_0K9xY8Xnhw0T8f8Amv1oLrjcA
            
            ```json
            {
                "success": true,
                "response": null,
                "error": null
            }
            ```
            
    - **추가 되어야 할 데이터**
        - 회원가입과 마찬가지로 username 정도를 리턴하는 건 어떨까?
    
- http://~/products(*API 주소*)
    - 해당 페이지에 들어갈 경우 Paging 파라미터에 맞게 페이징된 Product Api들이 출력됩니다.
    - 특정 상품을 클릭하면 해당 특정 상품의 정보를 출력해줍니다.
    - **Response**
        
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
        
    - **추가 되어야 할 데이터**
        - 추후 무한 스크롤이 구현될 경우를 생각한다면 현재 Page의 Json Object의 갯수나 총 페이지 갯수, 현재 페이지 수를 Response로 보내주는건 어떨까?
        - 추후 댓글 기능이 생긴다고 하면 댓글 수를 Response로 보내주는 것도 좋을 것 같다.
        - 별점 또한 Response로 보내주는 것도 좋을 것 같다.

- 개별 상품 목록 조회(*화면 설계*) & http://~/products/{productsPK}(*API 주소*)
    - Path Variable를 통해 받았던 PK에 해당하는 상품의 정보를 출력하는 API입니다.
    - Response
        
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
        
    - 추가 되어야 할 데이터
        - 특딜 적용 여부를 구별하는 데이터가 추가되어야 한다.
        - 배송비 데이터가 추가되어야 한다.
        - 배송 방법 데이터가 추가되어야 한다.

- 장바구니 담기(*화면 설계*) & http://~/carts/add(*API 주소*)
    - 장바구니 담기 버튼을 누르면 장바구니 API가 호출되어서, 선택했던 상품과 해당 상품의 옵션들의 가격과 갯수를 저장하게 됩니다.
    - **Response**
        
        ```json
        {
            "success": true,
            "response": null,
            "error": null
        }
        ```
        
    - 추가 되어야 할 데이터
        - response로 선택했던 상품과 해당 상품의 옵션들의 가격과 갯수를 반환해주는 건 어떨까
        - 배송비 데이터도 추가되어야 한다. → 추후 총 주문 금액에 포함이 되어야 하기에

- 장바구니 조회(*화면 설계*) & http://~/carts(*API 주소*)
    - 장바구니에 추가를 하였던 상품들의 정보를 출력하는 API입니다.
    - **Response**
        
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
                                "id": 1,
                                "option": {
                                    "id": 1,
                                    "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                                    "price": 10000
                                },
                                "quantity": 3,
                                "price": 30000
                            },
                            {
                                "id": 2,
                                "option": {
                                    "id": 2,
                                    "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                    "price": 10900
                                },
                                "quantity": 4,
                                "price": 43600
                            }
                        ]
                    }
                ],
                "totalPrice": 73600
            },
            "error": null
        }
        ```
        
    - 추가 되어야 할 데이터
        - 배송비 데이터도 추가되어야 한다.
        - 장바구니에 담은 날짜 데이터도 추가되어야 한다.

- http://~/carts/update(*API 주소*)
    - 각 상품에 존재하는 갯수 수정 버튼을 통해서 상품 갯수를 추가 및 감소시킬 수 있습니다.
    - 주문하기를 누르면 주문 API가 호출됩니다.
    - **Response**
        
        ```json
        {
            "success": true,
            "response": {
                "carts": [
                    {
                        "cartId": 1,
                        "optionId": 1,
                        "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                        "quantity": 3,
                        "price": 30000
                    },
                    {
                        "cartId": 2,
                        "optionId": 2,
                        "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                        "quantity": 4,
                        "price": 43600
                    }
                ],
                "totalPrice": 73600
            },
            "error": null
        }
        ```
        
    - 추가 되어야 할 데이터
        - 배송비 데이터도 추가되어야 한다.
        - 장바구니에 담은 날짜 데이터와 수정한 날짜 데이터도 추가되어야 한다.

- http://~/orders/save(*API 주소*)
    - 결제하기 버튼을 누르면 해당 장바구니에 있던 상품들이 장바구니에서 제거되고, 주문 API가 호출됩니다.
    - **Response**
        
        ```json
        {
            "success": true,
            "response": {
                "id": 1,
                "products": [
                    {
                        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                        "items": [
                            {
                                "id": 1,
                                "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                                "quantity": 3,
                                "price": 30000
                            },
                            {
                                "id": 2,
                                "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                "quantity": 4,
                                "price": 43600
                            }
                        ]
                    }
                ],
                "totalPrice": 73600
            },
            "error": null
        }
        ```
        
    - 추가 되어야 할 데이터
        - 배송비 데이터도 추가되어야 한다.
        - 주문한 날짜 데이터도 추가되어야 한다.

- http://~/orders/{orderPK}(*API 주소*)
    - 주문에 대한 API를 출력합니다.
    - 쇼핑 계속하기를 누를 경우 리다이렉트되는 주소에 맞는 API를 호출하여줍니다.
    - **Response**
        
        ```json
        {
            "success": true,
            "response": {
                "id": 1,
                "products": [
                    {
                        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                        "items": [
                            {
                                "id": 1,
                                "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                                "quantity": 3,
                                "price": 30000
                            },
                            {
                                "id": 2,
                                "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종",
                                "quantity": 4,
                                "price": 43600
                            }
                        ]
                    }
                ],
                "totalPrice": 73600
            },
            "error": null
        }
        ```
        
    - 추가 되어야 할 데이터
        - 배송비와 상품 이미지 데이터가 추가되어야 한다.
        - 주문한 날짜 데이터가 추가되어야 한다.

### 4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.

*테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)*

---

*테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)*

<aside>
💡 한번에 완벽한 설계를 짜는 것은 불가능하다.

지속적인 리마인딩만이 완벽한 설계에 가까워지는 길이다.

</aside>

### User::회원

- PK : id
- username
- email
- password
- created_at
- updated_at

### Role::역할

필드가 아니라 별도의 테이블로 만든 이유

→ User의 필드로 있을경우 추후 Role들이 많아질 때(ex) 판매업자 Role, 고객 Role… etc) 컬럼을 하나 더 만들어줘야 하기에

- PK : id
- FK : user_id
- role
- created_at
- updated_at

### Level::레벨

필드가 아니라 별도의 테이블로 만든 이유

→ level 필드가 point 필드에 함수 종속되기 때문에

- PK : id
- FK : user_id
- level
- point
- created_at
- updated_at

### Product::상품

- PK : id
- FK : user_id
    - 판매자의 정보가 있어야 하기에
- product_name
- description
- image
- price
- created_at
- updated_at

### Delivery::배송

필드가 아니라 별도의 테이블로 만든 이유

→ 배송 가격 필드가 delivery_way 필드에 함수 종속되므로

- PK : id
- FK : product_id
- delivery_way
    - 배송 방법에 대한 필드
- delivery_fee
    - 배송비
- created_at
- updated_at

### Option::옵션

- PK : id
- FK : product_id
- option
- price
- created_at
- updated_at

### Cart::장바구니

- PK : id
- FK : user_id
- FK : option_id
    - 옵션이 제품에 대한 정보를 가지고 있기에
- quantity
- price
- created_at
- updated_at

### Order::주문

- PK : id
- FK : user_id
    - 아이템 id가 필요없는 이유 → order_id를 통해서 아이템의 값을 가져올 수 있기에
    - 유저 1 ↔ N 주문 1 ↔  N 아이템
- created_at
- updated_at

### Item::최종 구매된 제품

- PK : id
- FK : option_id
    - 옵션이 제품에 대한 정보를 가지고 있기에
- FK : order_id
- price
- quantity
- created_at
- updated_at

### 궁금점

---

- 정규화 단계 중 몇 단계까지 정규화를 해야 읽기 성능과 쓰기 성능 모두를 증진시킬 수 있을까요?
- 만약 비밀번호 필드가 8글자이하의 조건이 있다면, 추후 비즈니스 변경을 염두에 두고 varchar(255)로 설정을 해야할까요? 아니면 varchar(8)로 설정을 해야할까요?
- 예를 들어서 상품 가격 같은 경우에는 데이터 타입으로 varchar로 정의하나요? 아니면 bigint로 정의하나요?

### ERD

[DB ERD 관계선(실선, 점선)과 기호에 대한 설명](https://eyecandyzero.tistory.com/246)

[[DATABASE] 식별과 비식별 관계](https://velog.io/@jch9537/DATABASE-식별과-비식별-관계)

여기서 하나 참고할 부분에 대해 말하자면 위의 그림에서 비식별관계는 주문

*상품번호라는 기본키를 임의로 추가하여 만들었다. 이렇게 추가한 키를 인조키라고 하는데 기본키를 인조키로 설정하는 것이 권장된다.이유는 식별관계를 나타내는 그림에서 보면 상품번호와 주문번호 두개의 키가 주문*

상품테이블의 기본키로 되어있다. 기본키는 최대한 변하지 않는 값이어야하는데 어떤이유에서 상품번호 또는 주문번호가 변경이 된다던지 사용을 할 수 없게 된다면 데이터 구조를 다시 만들어야하는 경우가 발생하기 때문이다.

[https://github.com/JNU-econovation/CoNoE/assets/102592414/6e34728c-0cbd-443e-8bbf-72cdceed7ffb](https://github.com/JNU-econovation/CoNoE/assets/102592414/6e34728c-0cbd-443e-8bbf-72cdceed7ffb)