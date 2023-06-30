# 1주차 과제

## 1. 요구사항 분석 / API요청 및 응답 시나리오 분석

1. 회원가입 <회원가입 버튼 클릭 시>

   ![Untitled](images/Untitled.png)

   - 회원가입 - 성공
     - POST : [http://localhost:8080/join](http://localhost:8080/join)
     - Request Body
       ```json
       {
         "username": "mata",
         "email": "meta@nate.com",
         "password": "meta1234!"
       }
       ```
     - 예상 응답
       - 이메일, 이름, 비밀번호가 모두 입력됨을 확인
       - 이메일 유효성 검사 성공
       - 비밀번호 유효성 검사 성공
       - user 테이블에서 동일한 이메일이 존재하는지 조회 시, 조회 결과가 없으므로 성공
       - user 테이블에 1개의 유저정보를 추가
     - Response Body
       ```json
       {
         "success": true,
         "response": null,
         "error": null
       }
       ```
     - 분석
       - user (유저) 테이블
         - PK : id (유저아이디)
         - email (이메일)
         - username (이름)
         - password (비밀번호)
   - 회원가입 - 이메일, 이름, 비밀번호 중 하나라도 입력X 시 실패
     - POST : [http://localhost:8080/join](http://localhost:8080/join)
     - Request Body
       ```json
       {
         "username": "mata",
         "email": "meta@nate.com"
       }
       ```
     - 예상 응답
       - 비밀번호가 입력되지 않았으므로 실패
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "비어 있을 수 없습니다:password",
           "status": 400
         }
       }
       ```
   - 회원가입 - 이메일 유효성 검사 실패
     - POST : [http://localhost:8080/join](http://localhost:8080/join)
     - Request Body
       ```json
       {
         "username": "mata",
         "email": "metanate.com",
         "password": "meta1234!"
       }
       ```
     - 예상 응답
       - 이메일에 @가 없으므로, 이메일 유효성 검사 실패
         - 요구사항의 이메일 유효성 검사 : **영문+숫자@영문+숫자.영문+숫자** 형식
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "이메일 형식으로 작성해주세요:email",
           "status": 400
         }
       }
       ```
   - 회원가입 - 이메일 유효성 검사 성공
     - POST : [http://localhost:8080/join](http://localhost:8080/join)
     - Request Body
       ```json
       {
         "username": "mata",
         "email": "1@h.sf",
         "password": "meta1234!"
       }
       ```
     - 예상 응답
       - 이메일에 @ 앞에 숫자만 입력되었으므로, 이메일 유효성 검사 실패
     - Response Body
       ```json
       {
         "success": true,
         "response": null,
         "error": null
       }
       ```
     - 분석
       - 요구사항 시나리오에 부족한 기능
         - 이메일에 @ 앞에 숫자만 입력되어도, 이메일 유효성 검사 성공
         - 요구사항 시나리오 : **영문+숫자@영문+숫자.영문+숫자** 형식
         - 숫자만 입력 불가, 2글자 이상 필수 등 이메일 형식에 대한 내용이 수정 필요
   - 회원가입 - 비밀번호 유효성 검사 실패1
     - POST : [http://localhost:8080/join](http://localhost:8080/join)
     - Request Body
       ```json
       {
         "username": "mata",
         "email": "meta@nate.com",
         "password": "meta1234"
       }
       ```
     - 예상 응답
       - 비밀번호에 특수문자가 포함되지 않았으므로, 비밀번호 유효성 검사 실패
         - 요구사항의 비밀번호 유효성 검사 : **영문+숫자+특수문자 포함, 공백없음**, 8~20자
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
           "status": 400
         }
       }
       ```
   - 회원가입 - 비밀번호 유효성 검사 실패2
     - POST : [http://localhost:8080/join](http://localhost:8080/join)
     - Request Body
       ```json
       {
         "username": "mata",
         "email": "mate@nate.com",
         "password": "meta12!"
       }
       ```
     - 예상 응답
       - 비밀번호가 8~20자가 아니므로, 비밀번호 유효성 검사 실패
         - 요구사항의 비밀번호 유효성 검사 : 영문+숫자+특수문자 포함, 공백없음, **8~20자**
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "8에서 20자 이내여야 합니다.:password",
           "status": 400
         }
       }
       ```
   - 회원가입 - 동일한 이메일이 존재하는지 확인 시 실패
     - POST : [http://localhost:8080/join](http://localhost:8080/join)
     - Request Body
       ```json
       {
         "username": "mata",
         "email": "ssar@nate.com",
         "password": "meta1234!"
       }
       ```
     - 예상 응답
       - user 테이블에서 동일한 이메일이 존재하는지 조회 시, "ssar@nate.com" 로 이미 회원가입한 적이 있으므로 실패
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "동일한 이메일이 존재합니다 : ssar@nate.com",
           "status": 400
         }
       }
       ```
   - 이메일 중복 체크 (사용X)
     - 이메일 중복 체크 - 성공
       - POST : [http://localhost:8080/check](http://localhost:8080/check)
       - Request Body
         ```json
         {
           "email": "meta@nate.com"
         }
         ```
       - 예상 응답
         - 이메일 유효성 검사 성공
         - user 테이블에서 동일한 이메일이 존재하는지 조회 시, 조회 결과가 없으므로 성공
       - Response Body
         ```json
         {
           "success": true,
           "response": null,
           "error": null
         }
         ```
       - 분석
         - 요구사항 시나리오에 부족한 기능
           - 회원가입(join) API에서 이메일 중복 체크를 진행하므로, 이메일 중복 체크(check) API가 사용되는 곳이 없다.
     - 이메일 중복 체크 - 이메일 유효성 검사 실패
       - POST : [http://localhost:8080/check](http://localhost:8080/check)
       - Request Body
         ```json
         {
           "email": "ssarnate.com"
         }
         ```
       - 예상 응답
         - 이메일에 @가 없으므로, 이메일 유효성 검사 실패
           - 요구사항의 이메일 유효성 검사 : **영문+숫자@영문+숫자.영문+숫자** 형식
       - Response Body
         ```json
         {
           "success": false,
           "response": null,
           "error": {
             "message": "이메일 형식으로 작성해주세요:email",
             "status": 400
           }
         }
         ```
       - 분석
         - 이메일 아이디 유효성 검사는 FE에서 진행
     - 이메일 중복 체크 - 이미 가입된 이메일이라 실패
       - POST : [http://localhost:8080/check](http://localhost:8080/check)
       - Request Body
         ```json
         {
           "email": "ssar@nate.com"
         }
         ```
       - 예상 응답
         - user 테이블에서 동일한 이메일이 존재하는지 조회 시, "ssar@nate.com" 로 이미 회원가입한 적이 있으므로 실패
       - Response Body
         ```json
         {
           "success": false,
           "response": null,
           "error": {
             "message": "동일한 이메일이 존재합니다 : ssar@nate.com",
             "status": 400
           }
         }
         ```

2. 로그인 <로그인 버튼 클릭 시>

   ![Untitled](images/Untitled%201.png)

   - 로그인 - 성공
     - POST : [http://localhost:8080/login](http://localhost:8080/login)
     - Request Body
       ```json
       {
         "email": "ssar@nate.com",
         "password": "meta1234!"
       }
       ```
     - 예상 응답
       - user 테이블에서 이메일로 조회 성공
       - 비밀번호가 매칭되면 인증 성공
       - 서버가 JWT를 유저에게 전달
         - 추후, 유저가 요청 시 JWT를 포함하여 전달 → 서버는 JWT를 확인하고 작업 진행
     - Response Body
       ```json
       {
         "success": true,
         "response": null,
         "error": null
       }
       ```
   - 로그인 - 이메일 유효성 검사 실패
     - POST : [http://localhost:8080/login](http://localhost:8080/login)
     - Request Body
       ```json
       {
         "email": "ssarnate.com",
         "password": "meta1234!"
       }
       ```
     - 예상 응답
       - 이메일에 @가 없으므로, 이메일 유효성 검사 실패
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "이메일 형식으로 작성해주세요:email",
           "status": 400
         }
       }
       ```
   - 로그인 - 비밀번호 유효성 검사 실패1
     - POST : [http://localhost:8080/login](http://localhost:8080/login)
     - Request Body
       ```json
       {
         "email": "ssar@nate.com",
         "password": "meta1234"
       }
       ```
     - 예상 응답
       - 비밀번호에 특수문자가 포함되지 않았으므로, 비밀번호 유효성 검사 실패
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
           "status": 400
         }
       }
       ```
       - 400 vs 401
         - 400 : 클라이언트의 잘못된 요청
         - 401 : 권한 없음 (인증 안됨)
   - 로그인 - 비밀번호 유효성 검사 실패2
     - POST : [http://localhost:8080/login](http://localhost:8080/login)
     - Request Body
       ```json
       {
         "email": "ssar@nate.com",
         "password": "meta12!"
       }
       ```
     - 예상 응답
       - 비밀번호가 8~20자가 아니므로, 비밀번호 유효성 검사 실패
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "8에서 20자 이내여야 합니다.:password",
           "status": 400
         }
       }
       ```
       - 400 vs 401
         - 400 : 클라이언트의 잘못된 요청
         - 401 : 권한 없음 (인증 안됨)
   - 로그인 - 인증 실패
     - POST : [http://localhost:8080/login](http://localhost:8080/login)
     - Request Body
       ```json
       {
         "email": "ssar1@nate.com",
         "password": "meta1234!"
       }
       ```
     - 예상 응답
       - user 테이블에서 이메일로 조회 성공
       - 비밀번호가 매칭되지 않아서 인증 실패
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "인증되지 않았습니다",
           "status": 401
         }
       }
       ```
       - 400 vs 401
         - 400 : 클라이언트의 잘못된 요청
         - 401 : 권한 없음 (인증 안됨)

3. 로그아웃 <로그아웃 버튼 클릭 시>

   ![Untitled](images/Untitled%202.png)

   - 분석
     - 요구사항 시나리오에 부족한 기능
       - 로그아웃 버튼 클릭 시, JWT를 만료시켜 로그아웃 시킨다는 내용을 추가

4. 전체 상품 목록 조회 <메인페이지>

   ![Untitled](images/Untitled%203.png)

   - 전체 상품 목록 조회
     - GET : [http://localhost:8080/products](http://localhost:8080/products)
     - 예상 응답
       - product 테이블에서 1번~9번(0페이지) 총 9개의 상품정보를 조회
     - Response Body
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
     - 분석
       - API에 부족한 데이터
         - description은 요구사항에 없으므로 제외
       - product (상품) 테이블
         - PK : id (상품아이디)
         - productName (상품명)
         - price (상품가격)
         - image (상품이미지)
   - Param을 이용한 전체 상품 목록 조회
     - GET : [http://localhost:8080/products?page=1](http://localhost:8080/products?page=1)
     - 예상 응답
       - product 테이블에서 상품아이디 10번~18번(1페이지) 총 9개의 상품정보를 조회
     - Response Body
       ```json
       {
         "success": true,
         "response": [
           {
             "id": 10,
             "productName": "통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정",
             "description": "",
             "image": "/images/10.jpg",
             "price": 8900
           },
           {
             "id": 11,
             "productName": "아삭한 궁채 장아찌 1kg 외 인기 반찬 모음전",
             "description": "",
             "image": "/images/11.jpg",
             "price": 6900
           },
           {
             "id": 12,
             "productName": "깨끗한나라 순수소프트 30롤 2팩. 무형광, 도톰 3겹",
             "description": "",
             "image": "/images/12.jpg",
             "price": 28900
           },
           {
             "id": 13,
             "productName": "생활공작소 초미세모 칫솔 12입 2개+가글 증정",
             "description": "",
             "image": "/images/13.jpg",
             "price": 9900
           },
           {
             "id": 14,
             "productName": "경북 영천 샤인머스켓 가정용 1kg 2수 내외",
             "description": "",
             "image": "/images/14.jpg",
             "price": 9900
           },
           {
             "id": 15,
             "productName": "[LIVE][5%쿠폰] 홈카페 Y3.3 캡슐머신 베이직 세트",
             "description": "",
             "image": "/images/15.jpg",
             "price": 148000
           }
         ],
         "error": null
       }
       ```
     - 분석
       - API에 부족한 데이터
         - 1페이지에서 10번~15번 총 6개의 상품정보만 조회되므로, 상품정보가 더 등록되면 좋음

5. 개별 상품 상세 조회 <상품 클릭 시>

   ![Untitled](images/Untitled%204.png)

   - 개별 상품 상세 조회

     - GET : [http://localhost:8080/products/1](http://localhost:8080/products/1)
     - 예상 응답
       - product 테이블에서 상품아이디에 해당하는 1개의 상품정보를 조회
       - option 테이블에서 상품아이디에 해당하는 1개이상의 옵션정보를 조회
       - 조회된 옵션의 개수를 계산하여 starCount로 함께 반환
     - Response Body
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
     - 분석

       - 요구사항 시나리오에 부족한 기능
         - 상품이 가진 옵션 개수인 starCount 내용을 추가
       - option (옵션) 테이블

         - id (옵션아이디)
         - optionName (옵션명)
         - price (옵션가격)
         - FK : productId (상품아이디)

         ![Untitled](images/Untitled%205.png)

6. 상품 옵션 선택

   ![Untitled](images/Untitled%206.png)

   - FE
     - 옵션 클릭 시 선택한 상품 리스트로 보여주고, 이미 선택된 옵션은 다시 선택 불가
     - 선택한 총 수량, 총 주문금액 계산

7. 옵션 확인 및 수량 결정

   ![Untitled](images/Untitled%207.png)

   - FE
     - +/- 버튼으로 주문 수량 수정
     - 선택한 총 수량, 총 주문금액 계산

8. 장바구니 담기 <장바구니 담기 버튼 클릭 시>

   ![Untitled](images/Untitled%208.png)

   - 장바구니 추가 - 성공

     - POST : [http://localhost:8080/carts/add](http://localhost:8080/carts/add)
     - Request Header

       ```json
       Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyMjY4NTV9.gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnrElyAhNW8FAA
       ```

         <aside>
         💡 로그인 성공 시, Response Header의 Authorization이다.
         (Request Header 대신, Auth에 Token으로 넣어도 됨)
         
         </aside>

     - Request Body
       ```json
       [
         {
           "optionId": 1,
           "quantity": 5
         },
         {
           "optionId": 2,
           "quantity": 5
         }
       ]
       ```
     - 예상 응답
       - JWT 토큰이 유효한지 확인 시 인증 성공
       - cart 테이블에 옵션 1개씩에 대해, 장바구니에 담은 아이템 정보를 추가 (해당 옵션아이디, 유저아이디 포함)
     - Response Body
       ```json
       {
         "success": true,
         "response": null,
         "error": null
       }
       ```
     - 분석
       - cart (장바구니) 테이블
         - PK : id (장바구니아이디)
         - quantity (옵션수량)
         - FK : optionId (옵션아이디)
         - FK : userId (유저아이디)
       - API에 부족한 데이터
         - 배송비는 요구사항에 없으므로, 장바구니에 담을 때 고려하지 않음
         - 톡딜가로 구매하기는 요구사항에 없으므로, API를 구현하지 않음

   - 장바구니 추가 - 인증 실패
     - POST : [http://localhost:8080/carts/add](http://localhost:8080/carts/add)
     - Request Body
       ```json
       [
         {
           "optionId": 1,
           "quantity": 5
         },
         {
           "optionId": 2,
           "quantity": 5
         }
       ]
       ```
     - 예상 응답
       - JWT 토큰이 유효한지 확인 시 인증 실패
     - Response Body
       ```json
       {
         "success": false,
         "response": null,
         "error": {
           "message": "인증되지 않았습니다",
           "status": 401
         }
       }
       ```
     - 분석
       - 요구사항 시나리오에 부족한 기능
         - 로그아웃 되어있을 때, 장바구니 담기가 불가하도록 팝업 안내 해야된다는 내용을 추가

9. 장바구니 보기 <장바구니페이지>

   ![Untitled](images/Untitled%209.png)

   - 장바구니 조회 - 담긴 상품이 있는 경우
     - GET : [http://localhost:8080/carts](http://localhost:8080/carts)
     - Request Header
       ```json
       Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyMjY4NTV9.gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnrElyAhNW8FAA
       ```
     - 예상 응답
       - JWT 토큰이 유효한지 확인 시 인증 성공
       - cart 테이블에서 유저아이디로 카트아이템을 조회
       - 옵션정보와, 상품정보까지 함께 조회
         - 옵션정보 : 옵션아이디, 옵션명, 옵션가격
         - 상품정보 : 상품아이디, 상품명
       - 옵션총가격, 전체가격을 계산하여 함께 반환
         - 옵션총가격(price) : 옵션가격x옵션수량
         - 전체가격(totalPrice) : 옵션총가격의 합
     - Response Body
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
                   "quantity": 5,
                   "price": 50000
                 },
                 {
                   "id": 2,
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
     - 분석
       - 요구사항 시나리오에 부족한 기능
         - 요구사한 시나리오에는 상품이미지까지 반환하라고 나와있지만, 화면설계와 API에는 상품이미지가 없으므로 제거
         - 화면설계와 API에 있는 **옵션총가격, 전체가격**을 반환하라는 내용을 추가
   - 장바구니 조회 - 담긴 상품이 없는 경우
     - GET : [http://localhost:8080/carts](http://localhost:8080/carts)
     - Request Header
       ```json
       Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyMjY4NTV9.gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnrElyAhNW8FAA
       ```
     - 예상 응답
       - JWT 토큰이 유효한지 확인 시 인증 성공
       - cart 테이블에서 유저아이디로 카트아이템을 조회
       - 0인 전체가격도 함께 반환
     - Response Body
       ```json
       {
         "success": true,
         "response": {
           "products": [],
           "totalPrice": 0
         },
         "error": null
       }
       ```
     - 분석
       - 요구사항 시나리오에 부족한 기능
         - 장바구니에 아무것도 담기지 않은 경우, FE에서 “아무것도 없습니다”와 같은 화면 출력 내용이 필요

10. 장바구니 상품 옵션 확인 및 수량 결정 <+/- 버튼 클릭 시>

    ![Untitled](images/Untitled%209.png)

    - 장바구니 수정 - 성공
      - POST : [http://localhost:8080/carts/update](http://localhost:8080/carts/update)
      - Request Header
        ```json
        Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyMjY4NTV9.gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnrElyAhNW8FAA
        ```
      - Request Body
        ```json
        [
          {
            "cartId": 1,
            "quantity": 10
          },
          {
            "cartId": 2,
            "quantity": 10
          }
        ]
        ```
      - 예상 응답
        - JWT 토큰이 유효한지 확인 시 인증 성공
        - cart 테이블에서 카트아이디로, 카트아이템의 옵션수량을 수정
        - 수정된 카트아이템과 옵션총가격, 전체가격을 계산하여 함께 반환
          - 옵션총가격(price) : 옵션가격x옵션수량
          - 전체가격(totalPrice) : 옵션총가격의 합
      - Response Body
        ```json
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
    - 장바구니 수정 - 실패
      - POST : [http://localhost:8080/carts/update](http://localhost:8080/carts/update)
      - Request Header
        ```json
        Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyMjY4NTV9.gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnrElyAhNW8FAA
        ```
      - Request Body
        ```json
        [
          {
            "cartId": 4,
            "quantity": 10
          },
          {
            "cartId": 5,
            "quantity": 10
          }
        ]
        ```
      - 예상 응답
        - JWT 토큰이 유효한지 확인 시 인증 성공
        - cart 테이블에 해당 카트아이디가 없어서 수정 실패
      - Response Body
        ```json
        {
          "success": false,
          "response": null,
          "error": {
            "message": "장바구니에 없는 상품은 주문할 수 없습니다 : 4",
            "status": 400
          }
        }
        ```

11. 주문 <주문하기 버튼 클릭 시>

    ![Untitled](images/Untitled%2010.png)

    - 장바구니 조회 - 담긴 상품이 있는 경우
      - GET : [http://localhost:8080/carts](http://localhost:8080/carts)

12. 결제 <결제하기 버튼 클릭 시>

    ![Untitled](images/Untitled%2011.png)

    - 결제 - 성공

      - POST : [http://localhost:8080/orders/save](http://localhost:8080/orders/save)
      - Request Header

        ```json
        Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOj2ODgyMjY4NTV9gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnElyAhNW8FAA
        ```

      - 예상 응답
        - JWT 토큰이 유효한지 확인 시 인증 성공
        - order 테이블에 주문정보를 추가
        - items 테이블에 카트아이템 정보를 그대로 주문아이템으로 추가 (해당 유저아이디, 주문아이디 포함)
        - cart 테이블의 카트아이템을 삭제
        - 주문정보에 해당하는 주문아이템과 옵션총가격, 전체가격을 계산하여 함께 반환
          - 옵션총가격(price) : 옵션가격x옵션수량
          - 전체가격(totalPrice) : 옵션총가격의 합
      - Response Body
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
                    "quantity": 10,
                    "price": 100000
                  },
                  {
                    "id": 2,
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
      - 분석

        - items (주문아이템) 테이블
          - PK : id (장바구니아이디)
          - quantity (옵션수량)
          - FK : optionId (옵션아이디)
          - FK : orderId (주문아이디)
        - order (주문) 테이블
          - PK : id (주문아이디)

        ![Untitled](images/Untitled%2012.png)

    - 결제 - 실패
      - POST : [http://localhost:8080/orders/save](http://localhost:8080/orders/save)
      - Request Header
        ```json
        Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyMjY4NTV9.gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnrElyAhNW8FAA
        ```
      - 예상 응답
        - JWT 토큰이 유효한지 확인 시 인증 성공
        - 이미 카트아이템이 삭제되어, 장바구니가 비어있는 경우 결제 실패
      - Response Body
        ```json
        {
          "success": false,
          "response": null,
          "error": {
            "message": "장바구니에 아무 내역도 존재하지 않습니다",
            "status": 404
          }
        }
        ```

13. 주문 결과 확인 <결제하기 버튼 클릭 시>

    ![Untitled](images/Untitled%2013.png)

    - 주문내역 조회
      - GET : [http://localhost:8080/orders/1](http://localhost:8080/orders/1)
      - Request Header
        ```json
        Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyMjY4NTV9.gH-Xcbzejhc-x23RIe0JoskCnRQJw5_qMrP0tC2WhVfRrbmpyHBP1lmsKZPC3b5vMN8Bt3mXlnrElyAhNW8FAA
        ```
      - 예상 응답
        - JWT 토큰이 유효한지 확인 시 인증 성공
        - order 테이블에서 주문아이디로 주문정보를 조회
        - 옵션정보와, 상품정보까지 함께 조회
          - 옵션정보 : 옵션아이디, 옵션명, 옵션가격
          - 상품정보 : 상품아이디, 상품명
        - 옵션총가격, 전체가격을 계산하여 함께 반환
          - 옵션총가격(price) : 옵션가격x옵션수량
          - 전체가격(totalPrice) : 옵션총가격의 합
      - Response Body
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
                    "quantity": 10,
                    "price": 100000
                  },
                  {
                    "id": 2,
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
      - 분석
        - 요구사항 시나리오에 부족한 기능
          - 화면설계와 API에 있는 **옵션총가격, 전체가격**을 반환하라는 내용을 추가
        - API에 부족한 데이터
          - 상품아이디도 함께 있어야 함

## 2. 요구사항 추가 반영 및 테이블 설계도

- 상품등록 API

  - POST : [http://localhost:8080/products/add](http://localhost:8080/products/add)
  - 판매자의 상품 및 옵션 추가 기능
      <aside>
      💡 판매자만 상품등록 가능하므로, user 테이블에 roles 추가하여 판매자와 구매자의 구분 필요
      
      </aside>

- 상품수정 API
  - POST : [http://localhost:8080/products/1/update](http://localhost:8080/products/1/update)
  - 판매자의 상품 및 옵션 수정 기능
- 상품삭제 API
  - POST : [http://localhost:8080/products/1/delete](http://localhost:8080/products/1/delete)
  - 판매자의 상품 및 옵션 삭제 기능
- 장바구니 삭제 API
  - POST : [http://localhost:8080/carts/delete](http://localhost:8080/carts/delete)
  - 장바구니에 담은 옵션에 x버튼을 클릭하면, 장바구니아이디에 해당하는 장바구니아이템이 삭제되는 기능

![Untitled](images/Untitled%2014.png)

```SQL
CREATE TABLE `Product` (
	`id`	INT	PRIMARY KEY,
	`date`	DATETIME	NOT NULL,
	`productName`	VARCHAR(100)	NOT NULL,
	`price`	INT	NOT NULL,
	`image`	VARCHAR(500)	NOT NULL
);

CREATE TABLE `Option` (
	`id`	INT	PRIMARY KEY,
	`date`	DATETIME	NOT NULL,
	`optionName`	VARCHAR(100)	NOT NULL,
	`price`	INT	NOT NULL,
	FOREIGN KEY (productId) REFERENCES `Product` (`id`)
);

CREATE TABLE `User` (
	`id`	INT	PRIMARY KEY,
	`date`	DATETIME	NOT NULL,
	`email`	VARCHAR(100)	NOT NULL,
	`username`	VARCHAR(50)	NOT NULL,
	`password`	VARCHAR(256)	NOT NULL,
);

CREATE TABLE `Cart` (
	`id`	INT	PRIMARY KEY,
	`date`	DATETIME	NOT NULL,
	`quantity`	INT	NOT NULL,
	FOREIGN KEY (optionId) REFERENCES `Option` (`id`),
	FOREIGN KEY (userId) REFERENCES `User` (`id`)
);

CREATE TABLE `Item` (
	`id`	INT	PRIMARY KEY,
	`date`	DATETIME	NOT NULL,
	`quantity`	INT	NOT NULL,
	FOREIGN KEY (optionId) REFERENCES `Option` (`id`),
	FOREIGN KEY (orderId) REFERENCES `Order` (`id`)
);

CREATE TABLE `Order` (
	`id`	INT	PRIMARY KEY,
	`date`	DATETIME	NOT NULL,
	FOREIGN KEY (userId) REFERENCES `User` (`id`)
);
```
