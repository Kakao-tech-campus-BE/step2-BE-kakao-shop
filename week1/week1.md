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