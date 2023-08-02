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

> - 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지)
> - 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
> - 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
> - 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
> - 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_1주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

</br>

<details>
<summary>Assignment 1</summary>

- **관리자 페이지 제공하기**

  - 주문 현황 조회 api
  - 상품 등록 api
  - 상품 수정 api
  - 상품 삭제 api

- **회원**

  - 회원 등급 데이터 -> Grade 객체
  - 회원 역할(구매자, 판매자) 데이터
  - 회원 정보 수정 api
  - 회원 탈퇴 api

- **회원가입 & 로그인**

  - 소셜 로그인 api
  - 회원 정보 유효성 검사

- **로그아웃**

  - 로그아웃 api

- **전체 상품 목록 조회**

  - 상품 검색 api
  - 조회 기준 선택
  - 정렬 기준 선택

- **개별 상품 상세 조회**

  - 바로 구매하기 api
  - 등록되지 않은 상품 조회 막기
  - 재고 내역 데이터
  - 별점 데이터
  - 상품 찜하기 관련 api -> Likes 객체
  - 리뷰 관련 api -> Review 객체

- **장바구니 담기**

  - 장바구니에 이미 추가된 옵션을 장바구니 담기를 할 경우, 수량 업데이트 api

- **장바구니 보기**

  - 장바구니 상품 삭제 api

- **장바구니 상품 옵션 확인 및 수량 결정**

  - 옵션 변경 시 데이터베이스에 저장하는 api

- **주문**

  - 장바구니 상품 중 구매할 상품을 선택하는 api

- **결제**
  - 할인 관련 api -> DiscountPolicy 객체
  - 배송지 정보 입력 시 데이터베이스에 저장하는 api

</details>

<details>
<summary>Assignment 2</summary>

</br>

**[기능 1] 회원가입**

</br>

![Image](https://drive.google.com/uc?id=1Ct39QyJYDmroIdk4KVlMLVSmECJAnJ-Z)

- 사용자 시나리오

  - 성공 시나리오
    - 회원가입 페이지에 들어와서 회원 정보(이메일, 이름, 비밀번호, 비밀번호 확인)를 모두 특정 규칙에 맞게 입력하고 회원가입 버튼을 누른다
  - 실패 시나리오
    - 1 : 회원가입 페이지에 들어와서 회원 정보 중 이메일 규칙에 어긋나게 입력하고 회원가입 버튼을 누른다
    - 2 : 회원가입 페이지에 들어와서 회원 정보 중 비밀번호 규칙에 어긋나게 입력하고 회원가입 버튼을 누른다
    - 3 : 회원가입 페이지에 들어와서 등록된 회원의 이메일을 입력하고 회원가입 버튼을 누른다
    - 4 : 회원가입 페이지에 들어와서 회원 정보 중 비밀번호 길이 규칙(8~20자)에 어긋나게 입력하고 회원가입 버튼을 누른다
    - 5 : 회원가입 페이지에 들어와서 회원 정보 중 하나라도 입력하지 않고 회원가입 버튼을 누른다

- Method : POST
- URL : /join
- 정상 응답
    <details>
    <summary>Request Body</summary>

      {
        "username":"mata",
        "email":"meta@nate.com",
        "password":"meta1234!"
      }

    </details>
    <details>
    <summary>Response Body</summary>

      {
          "success": true,
          "response": null,
          "error": null
      }

    </details>

- 실패1 - 이메일 형식 오류
    <details>
    <summary>Request Body</summary>
      
      {
          "username":"mata",
          "email":"metanate.com",
          "password":"meta1234!"
      }  
       
    </details>
    <details>
    <summary>Response Body</summary>

      {
          "success": false,
          "response": null,
          "error": {
              "message": "이메일 형식으로 작성해주세요:email",
              "status": 400
          }
      }

    </details>

- 실패2 - 비밀번호 형식 오류
    <details>
    <summary>Request Body</summary>

      {
          "username":"mata",
          "email":"meta@nate.com",
          "password":"meta1234"
      }

    </details>
    <details>
    <summary>Response Body</summary>

      {
          "success": false,
          "response": null,
          "error": {
              "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
              "status": 400
          }
      }

    </details>

- 실패3 - 동일한 이메일 존재
    <details>
    <summary>Request Body</summary>

        {
            "username":"mata",
            "email":"ssar@nate.com",
            "password":"meta1234!"
        }

    </details>
    <details>
    <summary>Response Body</summary>

        {
            "success": false,
            "response": null,
            "error": {
                "message": "동일한 이메일이 존재합니다 : ssar@nate.com",
                "status": 400
            }
        }

    </details>

- 실패4 - 비밀번호 길이 오류
    <details>
    <summary>Request Body</summary>

      {
          "username":"mata",
          "email":"meta@nate.com",
          "password":"meta12!"
      }

    </details>
    <details>
    <summary>Response Body</summary>
      
      {
          "success": false,
          "response": null,
          "error": {
              "message": "8에서 20자 이내여야 합니다.:password",
              "status": 400
          }
      }
      
    </details>

**[기능] 이메일 중복 체크**

- 사용자 시나리오

  - 성공 시나리오
    - 회원가입 페이지에서 이메일을 특정 규칙에 맞게 입력하고 이메일 중복 확인 버튼을 누른다
  - 실패 시나리오
    - 1 : 회원가입 페이지에서 등록된 이메일을 입력하고 이메일 중복 확인 버튼을 누른다
    - 2 : 회원가입 페이지에서 이메일 규칙에 어긋나게 입력하고 로그인 버튼을 누른다

- Method : POST
- URL : /check
- 정상 응답
    <details>
    <summary>Request Body</summary>

      {
          "email":"ssar1@nate.com"
      }

    </details>
    <details>
    <summary>Response Body</summary>
      
      {
          "success": true,
          "response": null,
          "error": null
      }
      
    </details>

- 실패1 - 동일한 이메일 존재
    <details>
    <summary>Request Body</summary>

      {
          "email":"ssar@nate.com"
      }

    </details>
    <details>
    <summary>Response Body</summary>
      
      {
          "success": false,
          "response": null,
          "error": {
              "message": "동일한 이메일이 존재합니다 : ssar@nate.com",
              "status": 400
          }
      }
      
    </details>

- 실패2 - 이메일 형식 오류
    <details>
    <summary>Request Body</summary>

      {
          "email":"ssarnate.com"
      }

    </details>
    <details>
    <summary>Response Body</summary>
      
      {
          "success": false,
          "response": null,
          "error": {
              "message": "이메일 형식으로 작성해주세요:email",
              "status": 400
          }
      }
      
    </details>

**[기능 2] 로그인**

</br>

![Image](https://drive.google.com/uc?id=1ab-7TpJ9_v0l4jn5_xDCrTmIYPcpCS6n)

- 사용자 시나리오

  - 성공 시나리오
    - 로그인 페이지에 들어와서 이메일, 비밀번호를 특정 규칙에 맞게 입력하고 로그인 버튼을 누른다
  - 실패 시나리오
    - 1 : 로그인 페이지에 들어와서 회원 정보 중 이메일 규칙에 어긋나게 입력하고 로그인 버튼을 누른다
    - 2 : 로그인 페이지에 들어와서 회원 정보 중 비밀번호 규칙에 어긋나게 입력하고 로그인 버튼을 누른다
    - 3 : 로그인 페이지에 들어와서 회원 정보 중 비밀번호 길이 규칙(8~20자)에 어긋나게 입력하고 로그인 버튼을 누른다
    - 4 : 로그인 페이지에 들어와서 등록되지 않은 이메일을 입력하고 로그인 버튼을 누른다

- Method : POST
- URL : /login
- 정상 응답
    <details>
    <summary>Request Body</summary>

      {
        "email":"ssar@nate.com",
        "password":"meta1234!"
      }

    </details>
    <details>
    <summary>Response Body</summary>

      {
        "success": true,
        "response": null,
        "error": null
      }

    </details>
  - Response Header - jwt 토큰

- 실패1 - 이메일 형식 오류
    <details>
    <summary>Request Body</summary>

      {
        "email":"ssarnate.com",
        "password":"meta1234!"
      }

    </details>
    <details>
    <summary>Response Body</summary>

      {
        "success": false,
        "response": null,
        "error": {
          "message": "이메일 형식으로 작성해주세요:email",
          "status": 400
        }
      }

    </details>

- 실패2 - 비밀번호 형식 오류
    <details>
    <summary>Request Body</summary>

      {
        "email":"ssar@nate.com",
        "password":"meta1234"
      }

    </details>
    <details>
    <summary>Response Body</summary>

      {
        "success": false,
        "response": null,
        "error": {
          "message": "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.:password",
          "status": 400
        }
      }

    </details>

- 실패3 - 비밀번호 길이 오류
    <details>
    <summary>Request Body</summary>

      {
        "email":"ssar@nate.com",
        "password":"meta12!"
      }

    </details>
    <details>
    <summary>Response Body</summary>

      {
        "success": false,
        "response": null,
        "error": {
          "message": "8에서 20자 이내여야 합니다.:password",
          "status": 400
        }
      }

    </details>

- 실패4 - 사용자 없음
    <details>
    <summary>Request Body</summary>

      {
        "email":"ssar1@nate.com",
        "password":"meta1234!"
      }

    </details>
    <details>
    <summary>Response Body</summary>

      {
        "success": false,
        "response": null,
        "error": {
          "message": "인증되지 않았습니다",
          "status": 400
        }
      }

    </details>

</br>

**[기능 3] 로그아웃**

</br>

![Image](https://drive.google.com/uc?id=194hYwBLFXFPhy4bX5tFOQFokOBBabziA)

- 프론트엔드 구현
- 사용자 시나리오
  - 로그아웃 버튼을 누른다

</br>

**[기능 4] 전체 상품 목록 조회**

</br>

![Image](https://drive.google.com/uc?id=1vcTusQ0bCu_QHOwgO8ghqmpzTiDimT69)

- 사용자 시나리오
  - 1 : 로그인을 하면 홈 화면에서 전체 상품 목록 조회 화면이 보인다
  - 2 : url로 전체 상품 목록 조회 화면에 들어간다
- Method : GET
- URL : /products
- 정상 응답1 - 파라미터 없음

  - URL : http://localhost:8080/products
    <details>
    <summary>Response Body</summary>
      
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

    </details>

- 정상 응답2 - 파라미터 있음
  - URL : http://localhost:8080/products?page=1
    <details>
    <summary>Response Body</summary>
      
      ```
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
    </details>

</br>

**[기능 5] 개별 상품 상세 조회**

</br>

![Image](https://drive.google.com/uc?id=1LJEZ1fbyCj-2NwEDLQ1V9zrJStgYDrbY)

- 사용자 시나리오
  - 1 : 전체 상품 목록 조회 페이지에서 개별 상품을 클릭하면 개별 상품 상세 조회 페이지에 들어온다
  - 2 : url로 개별 상품 상세 조회 화면에 들어간다
- Method : GET
- URL : /products/{product_id}
- 정상 응답
  - URL : http://localhost:8080/products/1
    <details>
    <summary>Response Body</summary>
      
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
      
    </details>

</br>

**[기능 6] 상품 옵션 선택**

</br>

![Image](https://drive.google.com/uc?id=148hkBch61Q3G3DY91uYpL6PapZsiPVwo)

- 프론트엔드 구현
- 사용자 시나리오
  - 옵션을 선택한다

</br>

**[기능 7] 옵션 확인 및 수량 결정**

</br>

![Image](https://drive.google.com/uc?id=1aSYNhJupApld2Cji80qqPwvqvDCKAvrO)

- 프론트엔드 구현
- 사용자 시나리오
  - 선택한 옵션의 수량을 + 버튼을 이용해서 증가시킨다

</br>

**[기능 8] 장바구니 담기**

</br>

![Image](https://drive.google.com/uc?id=1ZOl9bXjL1HAIp8dcFMVpfFP0Jgvi8uMW)

- 사용자 시나리오
  - 사용자가 옵션 4를 5개, 옵션 5를 5개 추가하고 장바구니 담기 버튼을 누른다
- Method : POST
- URL : /carts/add
- Request Header - jwt 토큰
- 정상 응답
  <details>
  <summary>Request Body</summary>

  ```
  [
      {
          "optionId":4,
          "quantity":5
      },
      {
          "optionId":5,
          "quantity":5
      }
  ]
  ```

  </details>
  <details>
  <summary>Response Body</summary>

  ```
  {
      "success": true,
      "response": null,
      "error": null
  }
  ```

  </details>

</br>

**[기능 9] 장바구니 보기**

</br>

![Image](https://drive.google.com/uc?id=1K5L-LDd_IVPojXF5K98kWf2SRKHTiuDA)

- 사용자 시나리오
  - 1 : 장바구니 아이콘을 클릭하면 장바구니 보기 화면이 나온다
  - 2 : url로 장바구니 화면에 들어간다
- Method : GET
- URL : /carts
- Request Header - jwt 토큰
- 정상 응답
    <details>
    <summary>Response Body</summary>

      {
          "success": true,
          "response": {
              "products": [
                  {
                      "id": 1,
                      "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                      "carts": [
                          {
                              "id": 8,
                              "option": {
                                  "id": 3,
                                  "optionName": "고무장갑 베이지 S(소형) 6팩",
                                  "price": 9900
                              },
                              "quantity": 5,
                              "price": 49500
                          },
                          {
                              "id": 10,
                              "option": {
                                  "id": 4,
                                  "optionName": "뽑아쓰는 키친타올 130매 12팩",
                                  "price": 16900
                              },
                              "quantity": 5,
                              "price": 84500
                          },
                          {
                              "id": 11,
                              "option": {
                                  "id": 5,
                                  "optionName": "2겹 식빵수세미 6매",
                                  "price": 8900
                              },
                              "quantity": 5,
                              "price": 44500
                          }
                      ]
                  },
                  {
                      "id": 2,
                      "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
                      "carts": [
                          {
                              "id": 9,
                              "option": {
                                  "id": 6,
                                  "optionName": "22년산 햇단밤 700g(한정판매)",
                                  "price": 9900
                              },
                              "quantity": 5,
                              "price": 49500
                          }
                      ]
                  }
              ],
              "totalPrice": 332500
          },
          "error": null
      }

  </details>

</br>

**[기능 10] 장바구니 상품 옵션 확인 및 수량 결정**

</br>

![Image](https://drive.google.com/uc?id=17KEEdiEoLtzqi_lRgdKJIezJ1nBR5R1N)

- 사용자 시나리오
  - 성공 시나리오
    - 옵션 수량을 5에서 10로 변경한다
  - 실패 시나리오
    - 1 : 장바구니에 없는 상품을 url을 통해 상품 수량 업데이트를 요청한다
    - 2 : 장바구니에 있는 상품 수량을 url을 통해 -1로 업데이트를 요청한다
- Method : GET
- URL : /carts/update
- Request Header - jwt 토큰
- 정상 응답
    <details>
    <summary>Request Body</summary>

      [
          {
              "cartId":8,
              "quantity":10
          },
          {
              "cartId":10,
              "quantity":10
          }
      ]

    </details>
    <details>
    <summary>Response Body</summary>

      {
          "success": true,
          "response": {
              "carts": [
                  {
                      "cartId": 8,
                      "optionId": 3,
                      "optionName": "고무장갑 베이지 S(소형) 6팩",
                      "quantity": 10,
                      "price": 99000
                  },
                  {
                      "cartId": 9,
                      "optionId": 6,
                      "optionName": "22년산 햇단밤 700g(한정판매)",
                      "quantity": 5,
                      "price": 49500
                  },
                  {
                      "cartId": 10,
                      "optionId": 4,
                      "optionName": "뽑아쓰는 키친타올 130매 12팩",
                      "quantity": 10,
                      "price": 169000
                  },
                  {
                      "cartId": 11,
                      "optionId": 5,
                      "optionName": "2겹 식빵수세미 6매",
                      "quantity": 5,
                      "price": 44500
                  }
              ],
              "totalPrice": 466500
          },
          "error": null
      }

    </details>

- 실패1 - 장바구니에 없는 상품 업데이트
    <details>
    <summary>Request Body</summary>

      [
        {
            "cartId":12,
            "quantity":10
        },
        {
            "cartId":13,
            "quantity":10
        }
      ]

    </details>
    <details>
    <summary>Response Body</summary>

      {
        "success": false,
        "response": null,
        "error": {
            "message": "장바구니에 없는 상품은 주문할 수 없습니다 : 12",
            "status": 400
        }
      }

    </details>

- 실패2 - 수량 -1로 업데이트
    <details>
    <summary>Request Body</summary>

      [
        {
            "cartId":10,
            "quantity":-1
        },
        {
            "cartId":13,
            "quantity":10
        }
      ]

    </details>
    <details>
    <summary>Response Body</summary>

      {
        "success": false,
        "response": null,
        "error": {
            "message": "장바구니에 없는 상품은 주문할 수 없습니다 : 13",
            "status": 400
        }
      }

    </details>

</br>

**[기능 11] 주문**

</br>

![Image](https://drive.google.com/uc?id=1gAXtd2QHMGWh-h2OnqI5FaFCSVQiHmYl)

- 사용자 시나리오
  - 장바구니 보기 화면에서 주문하기 버튼을 누른다
- Method : Post
- URL : /carts/update
- Request Header - jwt 토큰
- 정상 응답
    <details>
    <summary>Request Body</summary>
      
      [
        {
            "cartId":8,
            "quantity":10
        },
        {
            "cartId":10,
            "quantity":10
        }
      ]

    </details>

    <details>
    <summary>Response Body</summary>

      {
        "success": true,
        "response": {
          "carts": [
              {
                  "cartId": 8,
                  "optionId": 3,
                  "optionName": "고무장갑 베이지 S(소형) 6팩",
                  "quantity": 10,
                  "price": 99000
              },
              {
                  "cartId": 9,
                  "optionId": 6,
                  "optionName": "22년산 햇단밤 700g(한정판매)",
                  "quantity": 5,
                  "price": 49500
              },
              {
                  "cartId": 10,
                  "optionId": 4,
                  "optionName": "뽑아쓰는 키친타올 130매 12팩",
                  "quantity": 10,
                  "price": 169000
              },
              {
                  "cartId": 11,
                  "optionId": 5,
                  "optionName": "2겹 식빵수세미 6매",
                  "quantity": 5,
                  "price": 44500
              }
          ],
          "totalPrice": 466500
        },
        "error": null
      }

    </details>

  </br>

**[기능 12] 결제**

</br>

![Image](https://drive.google.com/uc?id=1fYz6Zd1A1e9Y2a9JpFGcr8CZ0OG5Vqx_)

- 사용자 시나리오
  - 주문하기 화면에서 전체 동의 버튼을 누른 후 결제하기 버튼을 누른다
- Method : POST
- URL : /orders/save
- Request Header - jwt 토큰
- 정상 응답
    <details>
    <summary>Response Body</summary>

      {
        "success": true,
        "response": {
            "id": 1,
            "products": [
                {
                    "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                    "items": [
                        {
                            "id": 3,
                            "optionName": "고무장갑 베이지 S(소형) 6팩",
                            "quantity": 10,
                            "price": 99000
                        },
                        {
                            "id": 5,
                            "optionName": "뽑아쓰는 키친타올 130매 12팩",
                            "quantity": 10,
                            "price": 169000
                        },
                        {
                            "id": 6,
                            "optionName": "2겹 식빵수세미 6매",
                            "quantity": 5,
                            "price": 44500
                        }
                    ]
                },
                {
                    "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
                    "items": [
                        {
                            "id": 4,
                            "optionName": "22년산 햇단밤 700g(한정판매)",
                            "quantity": 5,
                            "price": 49500
                        }
                    ]
                }
            ],
            "totalPrice": 466500
        },
        "error": null
      }

    </details>

</br>

**[기능 13] 주문 결과 확인**

</br>

![Image](https://drive.google.com/uc?id=1l7a4ebJCznUyQFC-AcYoDsd7y9-euyu2)

- 사용자 시나리오
  - 성공 시나리오
    - 결제하기 버튼을 누르면 주문 결과 확인 화면이 나온다
  - 실패 시나리오
    - 주문 내역에 없는 주문을 url을 통해 주문 결과 화면을 요청한다
- Method : GET
- URL : /orders/{order_id}
- Request Header - jwt 토큰
- 정상 응답

  - URL : http://localhost:8080/orders/1

    <details>
    <summary>Response Body</summary>

    ```
    {
      "success": true,
      "response": {
          "id": 1,
          "products": [
              {
                  "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전",
                  "items": [
                      {
                          "id": 3,
                          "optionName": "고무장갑 베이지 S(소형) 6팩",
                          "quantity": 10,
                          "price": 99000
                      },
                      {
                          "id": 5,
                          "optionName": "뽑아쓰는 키친타올 130매 12팩",
                          "quantity": 10,
                          "price": 169000
                      },
                      {
                          "id": 6,
                          "optionName": "2겹 식빵수세미 6매",
                          "quantity": 5,
                          "price": 44500
                      }
                  ]
              },
              {
                  "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
                  "items": [
                      {
                          "id": 4,
                          "optionName": "22년산 햇단밤 700g(한정판매)",
                          "quantity": 5,
                          "price": 49500
                      }
                  ]
              }
          ],
          "totalPrice": 466500
      },
      "error": null
    }
    ```

    </details>

- 실패 응답

  - URL : http://localhost:8080/orders/2
    <details>
    <summary>Response Body</summary>

    ```
    {
      "success": false,
      "response": null,
      "error": {
          "message": "해당 주문을 찾을 수 없습니다 : 2",
          "status": 404
      }
    }
    ```

    </details>

</br>

</details>

<details>
<summary>Assignment 3</summary>

</br>

모든 API를 POSTMAN으로 요청하고 응답 데이터를 확인하는 과정은 [Assignment 2](#assignment-2)를 참고합니다.

- 상세 상품 조회
  - 배송비 데이터 전달하기(무료배송 또는 금액)
- 주문
  - 주문상품 정보에 들어갈 제품명 전달하기
  - 배송지 정보에 들어갈 회원 정보(이름, 전화번호, 주소) 전달하기

</br>

</details>

<details>
<summary>Assignment 4</summary>

</br>

![Image](https://drive.google.com/uc?id=1nP-TXrLBTo9nR9HdtX1xLCuWAs1bOkJI)

- user_tb
  ```sql
  CREATE TABLE `user_tb` (
  	`id`		    INTEGER		    NOT NULL AUTO_INCREMENT PRIMARY KEY,
  	`username`	    VARCHAR(100)	NOT NULL,
  	`email`		    VARCHAR(100)	NOT NULL,
  	`password`	    VARCHAR(100)	NOT NULL,
  	`created_at`	TIMESTAMP	    NOT NULL,
  	`updated_at`	TIMESTAMP	    NULL
  );
  ```
- product_tb
  ```sql
  CREATE TABLE `product_tb` (
  	`id`		      INTEGER		NOT NULL AUTO_INCREMENT PRIMARY KEY,
  	`product_name`	  VARCHAR(100)	NOT NULL,
  	`product_price`	  INTEGER		NOT NULL,
  	`description`	  VARCHAR(1000)	NOT NULL,
  	`image`		      VARCHAR(500)	NOT NULL,
  	`created_at`	  TIMESTAMP	    NOT NULL,
  	`updated_at`	  TIMESTAMP	    NULL
  );
  ```
- option_tb
  ```sql
  CREATE TABLE `option_tb` (
  	`id`		      INTEGER		NOT NULL AUTO_INCREMENT PRIMARY KEY,
  	`option_name`	  VARCHAR(100)	NOT NULL,
  	`option_price`	  INTEGER		NOT NULL,
  	`created_at`	  TIMESTAMP	    NOT NULL,
  	`updated_at`	  TIMESTAMP	    NULL,
  	`product_id`	  INTEGER		NOT NULL,
  	CONSTRAINT product_id_fk FOREIGN KEY(product_id) REFERENCES product_tb(id)
  );
  ```
- cart_tb
  ```sql
  CREATE TABLE `cart_tb` (
  	`id`		    INTEGER		NOT NULL AUTO_INCREMENT PRIMARY KEY,
  	`quantity`		INTEGER		NOT NULL,
  	`cart_price`	INTEGER		NOT NULL,
  	`created_at`	TIMESTAMP	NOT NULL,
  	`updated_at`	TIMESTAMP	NULL,
  	`user_id`		INTEGER		NOT NULL,
  	`option_id`	    INTEGER		NOT NULL,
  	CONSTRAINT user_id_fk FOREIGN KEY(user_id) REFERENCES user_tb(id),
  	CONSTRAINT option_id_fk FOREIGN KEY(option_id) REFERENCES option_tb(id),
  	UNIQUE (user_id, option_id)
  );
  ```
- order_tb
  ```sql
  CREATE TABLE `order_tb` (
  	`id`		    INTEGER		NOT NULL AUTO_INCREMENT PRIMARY KEY,
  	`created_at`	TIMESTAMP	NOT NULL,
  	`updated_at`	TIMESTAMP	NULL,
  	`order_price`	INTEGER	    NOT NULL,
  	`user_id`		INTEGER		NOT NULL,
  	CONSTRAINT user_id_fk2 FOREIGN KEY(user_id) REFERENCES user_tb(id)
  );
  ```
- item_tb

  ```sql
  CREATE TABLE `item_tb` (
  	`id`		    INTEGER		NOT NULL AUTO_INCREMENT PRIMARY KEY,
  	`item_price`	INTEGER		NOT NULL,
  	`quantity`		INTEGER		NOT NULL,
  	`created_at`	TIMESTAMP	NOT NULL,
  	`updated_at`	TIMESTAMP	NULL,
  	`order_id`		INTEGER		NOT NULL,
  	`option_id`	    INTEGER		NOT NULL,
  	CONSTRAINT order_id_fk FOREIGN KEY(order_id) REFERENCES order_tb(id),
  	CONSTRAINT option_id_fk2 FOREIGN KEY(option_id) REFERENCES option_tb(id)
  );
  ```

</br>

</details>

</br>
</br>

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

> - 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? (예를 들어 배포된 서버는 POST와 GET으로만 구현되었는데, 학생들은 PUT과 DELETE도 배울 예정이라 이부분이 반영되었고, 주소가 RestAPI에 맞게 설계되었는지)
> - 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_2주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

</br>

<details>
<summary>Assignment 1</summary>

- [API Reference](https://app.gitbook.com/s/o1E7yDwyPFukxZ8B4jd3/introduction/kakao-shop-documentation)는 RESTAPI로 설계된 API 문서입니다.

</details>

<details>
<summary>Assignment 2</summary>

- Assignment 1에서 설계한 api가 아닌 API 문서를 기반으로 구현했습니다.

</br>

### **[3] 이메일 중복 체크**

- UserRequest에서 CheckDTO를 생성합니다.
- CheckDTO를 사용해서 이메일 중복 체크 시 @RequestBody를 통해 request 데이터를 받아옵니다.
- UserRestController에서 checkEmail()를 생성합니다.
- 이메일 중복 체크 로직은 구현되지 않았지만 ok를 반환합니다.

</br>

### **[6] 장바구니 담기**

- cart 패키지에 request 패키지를 생성합니다.
- request 패키지에 optionId, quantity를 가지는 CartReqAddDTO를 생성합니다. 이때, 기본 생성자가 없어서 JSON을 클래스의 인스턴스로 deserialize할 수 없기 때문에 @NoArgsConstructor를 붙입니다.
- CartReqAddDTO를 사용해서 장바구니 담기 시 @RequestBody를 통해 requset 데이터를 받아옵니다.
- CartRestController에서 add()를 생성합니다.
- 장바구니 담기 로직은 구현되지 않았지만 ok를 반환합니다.

</br>

### **[8] 장바구니 수정**

- cartId, quantity를 가지는 CartReqUpdateDTO를 생성합니다.
- CartReqUpdateDTO를 사용해서 장바구니 수정 시 @RequestBody를 통해 request 데이터를 받아옵니다.
- CartRestController에서 update()를 생성합니다.
- 장바구니 수정 로직은 구현되지 않았지만 더미 데이터를 반환합니다.
- 더미 데이터는 carts, totalPrice를 가지는 CartRespUpdateDTO를 통해 생성됩니다.
- CartRespUpdateDTO의 carts는 CartUpdateItemDTO 리스트입니다.

</br>

### **[9] 주문 저장**

- OrderRestController에서 save()를 생성합니다.
- 주문 인서트 로직은 아직 구현되지 않았지만 더미 데이터를 반환합니다.
- 더미 데이터는 id, products, totalPrice를 가지는 OrderRespInsertDTO를 통해 생성됩니다.
- OrderRespInsertDTO의 products는 OrderProductItemDTO 리스트이고 OrderProductItemDTO는 productName, items를 가집니다.
- OrderProductItemDTO의 items는 id, optionName, quantity, price를 가지는 OrderItemDTO 리스트입니다.

</br>

### **[10] 주문 결과 확인**

- OrderRestController에서 findById()를 생성합니다.
- 주문 결과 확인 로직은 아직 구현되지 않았지만 더미 데이터를 반환합니다.
- 더미 데이터는 id, products, totalPrice를 가지는 OrderRespUpdateDTO를 통해 생성됩니다.
- OrderRespInsertDTO의 products는 OrderProductItemDTO 리스트이고 OrderProductItemDTO는 productName, items를 가집니다.
- OrderProductItemDTO의 items는 id, optionName, quantity, price를 가지는 OrderItemDTO 리스트입니다.

</br>

### **예외 처리**

- OrderException을 생성해서 주문 예외를 추가합니다.
- MyControllerAdvice에 @RestControllerAdvice를 사용해서 예외 코드를 분리합니다.
- @ExceptionHandler를 통해 예외 코드를 추가합니다. 기존에 구현된 ApiUtils의 ApiError를 이용해서 데이터를 반환합니다.

</br>

### **ProductRestControllerTest 구현**

- 기존 : findAll(), findById()
- 로그 남기기
- given-when-then

</br>

### **CartRestControllerTest 구현**

- 기존 : findAll()
- 추가 : add(), update()
- request 데이터 넣기
- 로그 남기기
- given-when-then

</br>

### **OrderRestControllerTest 구현**

- 추가 : insertOrder(), findOrderResult(), findById_fail()
- findById_fail()를 통해 예외 처리될 경우 테스트하기
- 로그 남기기
- given-when-then

</br>

### **Entity 구현**

- User, Product, ProductOption, Cart, Order, OrderItem

</details>

</br>
</br>

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

> - 레포지토리 단위테스트가 구현되었는가?
> - 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
> - Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
> - 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_3주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

</br>

<details>
<summary>Assignment 1</summary>

### **CartJPARepositoryTest 구현**

user를 두 명 저장하고 각 user에 따른 cart를 저장합니다.

- clear()
  - @AfterEach로 각 테스트를 격리하기 위해 테스트가 끝나면 id를 1로 설정합니다.
- cart_findAll_test()
  - 장바구니 전체 조회
  - fetch=FetchType.EAGER로 설정할 경우 필요한 추가 쿼리가 모두 발생합니다.
  - fetch=FetchType.LAZY로 설정할 경우 assertion에서 검증한 필드에 대해서만 추가 쿼리가 발생합니다.
  - N+1 문제
- cart_findAll_joinFetch_test()
  - 장바구니 전체 조회 - fetch join
  - ManyToOne, OneToOne 관계에 있는 엔티티를 join fetch 합니다.
  - join fetch를 사용할 경우 모두 영속성 컨텍스트에서 관리되어 N+1 문제가 발생하지 않습니다.
  - 여러 join fetch 쿼리를 테스트합니다.
- cart_findAll_messageConverter_fail_test()
  - 장바구니 전체 조회 - 메시지 컨버터 실패
  - 예외를 잡아서 검증합니다.
- cart_findAll_messageConverter_dto_test()
  - 장바구니 전체 조회 - 메시지 컨버터 dto
  - fetch=FetchType.LAZY로 설정할 경우 엔티티의 프록시 객체로 인해 직렬화가 안되는 것을 방지하기 위해 FindAllDTO를 사용합니다.
- cart_findAll_messageConverter_joinFetch_test()
  - 장바구니 전체 조회 - 메시지 컨버터 fetch join
  - fetch join은 fetch=FetchType.LAZY보다 우선시 됩니다.
  - cart_findAll_joinFetch_test()와 동일하지만 메시지 컨버터 기능이 추가됩니다.
- cart_findById_test()
  - 장바구니 아이디로 조회 성공
  - fetch=FetchType.LAZY로 설정할 경우 user 쿼리가 추가 발생합니다.
- cart_findById_fail_test()
  - 장바구니 아이디로 조회 실패
- cart_findByUserId_test()
  - 사용자의 장바구니 조회 성공
- cart_findByUserId_fail_test()
  - 사용자의 장바구니 조회 실패
- cart_update_test()
  - 장바구니 수정 성공
  - Cart의 update()를 사용해서 수량과 가격을 업데이트합니다.
  - update 쿼리를 날리고 바로 반영하기 위해 em.flush()를 합니다.
- cart_update_fail_test()
  - 장바구니 수정 실패
- cart_deleteAll_test()
  - 장바구니 전체 삭제
- cart_deleteById_test()
  - 장바구니 개별 삭제

</br>

### **OrderJPARepositoryTest 구현**

user를 두 명 저장하고 각 user에 따른 cart, order, item을 생성해서 저장합니다.

- clear()
  - @AfterEach로 각 테스트를 격리하기 위해 테스트가 끝나면 id를 1로 설정합니다.
- order_findAll_test()
  - 전체 주문 내역 조회
  - fetch=FetchType.EAGER로 설정할 경우 필요한 추가 쿼리가 모두 발생합니다.
  - fetch=FetchType.LAZY로 설정할 경우 assertion에서 검증한 필드에 대해서만 추가 쿼리가 발생합니다.
- item_findByOrderId_test()
  - 주문 번호로 주문 아이템 조회 성공
- item_findByOrderId_fail_test()
  - 주문 번호로 주문 아이템 조회 실패
- item_findByOrderId_joinFetch_test()
  - 주문 번호로 주문 아이템 조회 - fetch join
  - 여러 join fetch 쿼리를 테스트합니다.

</br>

### **ProductJPARepositoryTest 구현**

- clear()
  - @AfterEach로 각 테스트를 격리하기 위해 테스트가 끝나면 id를 1로 설정합니다.
- product_findAll_test()
  - 전체 상품 조회
  - fetch=FetchType.EAGER로 설정할 경우 필요한 추가 쿼리가 모두 발생합니다.
  - fetch=FetchType.LAZY로 설정할 경우 assertion에서 검증한 필드에 대해서만 추가 쿼리가 발생합니다.
- option_findByProductId_test()
  - 개별 상품 조회 성공
- option_findByProductId_fail_test()
  - 개별 상품 조회 실패
- option_findByProductId_joinFetch_test()
  - 개별 상품 조회 - fetch join
- product_findById_option_findByProductId_test()
  - 개별 상품 조회 - 상품, 옵션 각각 조회
  - 상품을 찾는 쿼리와 옵션을 가져오는 쿼리를 두 번 사용한다.
  - 상품을 찾는 쿼리를 보냄으로써 product는 영속화되므로 옵션을 가져오는 쿼리에서 조인되지 않아도 됩니다.

</details>

</br>
</br>

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

> - 컨트롤러 단위테스트가 구현되었는가?
> - Mockito를 이용하여 stub을 구현하였는가?
> - 인증이 필요한 컨트롤러를 테스트할 수 있는가?
> - 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_4주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

</br>

## **작성하면서 어려웠던 점**

- 컨트롤러 테스트에 대해 명확하게 알지 못했었는데 과제를 수행하면서 컨트롤러 테스트의 역할에 대해 깨닫게 되었습니다.

</br>

## **리뷰 시 중점적으로 봐주셨으면 하는 부분**

- 각 컨트롤러 테스트의 종류, 내용, 방식이 적절한지 궁금합니다.
- 컨트롤러와 서비스의 구현에서 적절하지 못한 부분과 더 좋은 구현 방식이 있는지 봐주셨으면 좋겠습니다.

</br>

<details>
<summary>Assignment</summary>

- DB 사용
  > - fakeStore에서 DB로 변경했습니다.
  > - data.sql을 생성해서 더미 데이터로 사용했습니다.
- ErrorLog 에러
  > - 서비스에서 ErrorLog를 생성할 경우 몇 개의 데이터가 Null로 들어와서 400이 아닌 500 에러가 발생했습니다.
  > - ErrorLog의 해당 필드를 nullable = true로 변경했습니다.
- List 유효성 검사 실패
  > - List<CartRequest.SaveDTO> requestDTOs로 전달할 경우 List 앞에 @Valid를 걸면 List 내부 요소에 대해 유효성 검사가 적용되지 않았습니다.
  > - 컨트롤러에 @Validated를 추가하고 GlobalExceptionHandler에 ConstraintViolationException 예외를 처리할 수 있는 handler를 추가했습니다.
  > - Errors에 유효성 검사 결과 오류가 담기지 않습니다.

</br>

### **[Controller, Service]**

- User를 제외한 Product, Cart, Order를 구현했습니다.
- 컨트롤러와 서비스의 책임을 분리했습니다.

</br>

### **[CartRestControllerTest]**

- 컨트롤러 테스트에서 컨트롤러가 의존하는 Service, Repository를 호출하지 않고 대체하여 테스트를 수행합니다.
- Service로부터 격리될 수 있도록 Stub으로 Response를 만들어서 응답합니다.
- add_success_test()
  - 장바구니 저장 성공
  - 200 OK
  - Response가 null이므로 stub이 필요하지 않습니다.
- add_fail_test1()
  - 장바구니 저장 실패 - 최소 수량 미달
  - Request에서 수량을 -1로 설정합니다.
  - 400 Bad Request
  - Response가 null이므로 stub이 필요하지 않습니다.
- add_fail_test2()
  - 장바구니 저장 실패 - 최대 수량 초과
  - Request에서 수량을 1000으로 설정합니다.
  - 400 Bad Request
  - Response가 null이므로 stub이 필요하지 않습니다.
- add_fail_test3()
  - 장바구니 저장 실패 - 옵션 없음
  - Request에서 option id 정보를 전달하지 않습니다.
  - 400 Bad Request
  - Response가 null이므로 stub이 필요하지 않습니다.
- add_fail_test4()
  - 장바구니 저장 실패 - 인증 실패
  - @WithMockUser를 주석 처리해서 사용자 미인증 상태로 만듭니다.
  - 401 Unauthorized
  - Response가 null이므로 stub이 필요하지 않습니다.
- findAll_test()
  - 장바구니 조회
  - 200 OK
  - Response로 장바구니 조회 응답 결과를 전달하기 위해 Stub을 사용합니다.
- update_success_test()
  - 장바구니 수정 성공
  - 200 OK
  - Response로 장바구니 수정 응답 결과를 전달하기 위해 Stub을 사용합니다.
- update_fail_test1()
  - 장바구니 수정 실패 - 최소 수량 미달
  - Request에서 수량을 -1로 설정합니다.
  - 400 Bad Request
  - Response가 null이므로 stub이 필요하지 않습니다.
- update_fail_test2()
  - 장바구니 수정 실패 - 최대 수량 초과
  - Request에서 수량을 1000으로 설정합니다.
  - 400 Bad Request
  - Response가 null이므로 stub이 필요하지 않습니다.
- update_fail_test3()
  - 장바구니 수정 실패 - 카트 없음
  - Request에서 cart id 정보를 전달하지 않습니다.
  - 400 Bad Request
  - Response가 null이므로 stub이 필요하지 않습니다.
- update_fail_test4()
  - 장바구니 수정 실패 - 인증 실패
  - @WithMockUser를 주석 처리해서 사용자 미인증 상태로 만듭니다.
  - 401 Unauthorized
  - Response가 null이므로 stub이 필요하지 않습니다.

</br>

### **[OrderRestControllerTest]**

- save_test()
  - 주문 생성
  - 200 OK
  - Response로 주문 생성 응답 결과를 전달하기 위해 Stub을 사용합니다.
- save_fail_test()
  - 주문 생성 실패 - 인증 실패
  - @WithMockUser를 주석 처리해서 사용자 미인증 상태로 만듭니다.
  - 401 Unauthorized
  - Response가 null이므로 stub이 필요하지 않습니다.
- findById_test()
  - 주문 결과 조회
  - 200 OK
  - Response로 주문 결과 조회 응답 결과를 전달하기 위해 Stub을 사용합니다.

</br>

### **[ProductRestControllerTest]**

- findAll_test()
  - 전체 상품 조회
  - 200 OK
  - Response로 전체 상품 조회 응답 결과를 전달하기 위해 Stub을 사용합니다.
- findById_test()
  - 상품 상세 조회
  - 200 OK
  - Response로 상품 상세 조회 응답 결과를 전달하기 위해 Stub을 사용합니다.

</br>

### **[UserRestControllerTest]**

- join_success_test()
  - 회원가입 성공
  - 200 OK
- join_fail_test1()
  - 회원가입 실패 - 이메일 형식 오류
  - 400 Bad Request
- join_fail_test2()
  - 회원가입 실패 - 비밀번호 형식 오류
  - 400 Bad Request
- join_fail_test3()
  - 회원가입 실패 - 이름 길이 오류
  - 400 Bad Request
- login_success_test()
  - 로그인 성공
  - 200 OK
- login_fail_test1()
  - 로그인 실패 - 이메일 형식 오류
  - 400 Bad Request
- login_fail_test2()
  - 로그인 실패 - 비밀번호 형식 오류
  - 400 Bad Request

</details>

</br>

# 5주차

카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제
</br>
</br>

## **과제명**

```
1. 코드 리팩토링
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**

아래 항목은 반드시 포함하여 과제 수행해주세요!

> - AOP가 적용되었는가?
> - GlobalExceptionHandler가 적용되었는가?
> - 장바구니 담기시 모든 예외가 처리 완료되었는가?
> - 장바구니 수정시 모든 예외가 처리 완료되었는가?
> - 결제하기와 주문결과 확인이 완료되었는가?

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_5주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

</br>

## **작성하면서 어려웠던 점**

- 예외와 받아온 데이터를 처리할 때 적절하게 구현하려고 노력했습니다.
- 어떤 쿼리로 가져와야 할지 고민이 됩니다.

</br>

## **리뷰 시 중점적으로 봐주셨으면 하는 부분**

- 서비스가 적절하게 구현되어 있는지 궁금합니다. 

</br>

## **Assignment**

- GlobalValidationHandler
  - Request 유효성 검증하는 부분을 AOP를 만들어서 중복 코드를 추출합니다.
- GlobalExceptionHandler
  - 기존의 try catch 문을 없애고 @RestControllerAdvice를 이용해서 모든 컨트롤러에 대해 전역적으로 예외를 처리합니다.
- CartService
  - 리스트 validation 적용을 위해서 CartController에 @Validated를 추가했습니다.
  - addCartList
    - 추가 1 : 입력 중 동일한 optionId가 있을 경우 400 Bad Request
    - 추가 2 : 입력받은 옵션이 장바구니에 이미 존재하면 수량을 추가합니다. 그렇지 않다면 사용자의 장바구니에 추가합니다.
    - isDuplicated : requestDTOs에 동일한 optionId가 있는지 나타내는 boolean 값
    - findByOptionIdAndUserId는 입력 받은 optionId와 userId로 cart를 찾아오는 쿼리입니다.
    - 카트가 존재할 경우 받아온 데이터에서 데이터를 수정한 후 update를 합니다.
    - 카트가 존재하지 않을 경우 save를 합니다.
    - addCartListV1
      - 입력 받은 optionId가 현재 옵션 목록에 있는지 카트의 존재 유무에 관계없이 확인해야 한다면 옵션을 가져오는 쿼리를 보내야 합니다.
      - findCart를 가져올 때는 옵션을 이미 가져왔기 때문에 join fetch로 가져오지 않아도 됩니다. findCart가 존재하고 업데이트를 위해 가격을 계산할 때도 가져온 옵션을 이용합니다.
    - addCartListV2
      - 업데이트할 옵션이 존재하는지 확인할 필요가 없다면 옵션까지 fetch join으로 가져옵니다.
      - findCart가 존재하지 않는 경우만 option을 가져오면 됩니다.
    - 결국, save를 할 때는 쿼리 개수 차이는 없지만 update 때는 줄어듭니다. 하지만 PK로 가져오기 때문에 쿼리 개수가 하나 증가하더라도 성능에 유의미한 차이는 없기 때문에 확실하게 확인하는 addCartListV1가 좋다고 생각합니다.
  - findAllV1
  - findAllV2
    - ResponseDTO가 이전과 다른 방식으로 구현되었는데 ProductDTO와 CartDTO를 합쳐서 depth는 줄어들었지만 프론트에서 totalPrice를 계산하기 위해 추가 계산을 해야합니다. 
  - update
    - 추가 1 : 사용자의 장바구니가 비어있을 경우 400 Bad Request
    - 추가 2 : 입력 중 동일한 cartId가 있을 경우 400 Bad Request
    - 추가 3 : 사용자 장바구니에 있는 cartId가 들어온 경우만 update 합니다. 그렇지 않다면 404 Not Found
    - 사용자 장바구니에 있는 cartId가 맞는지 확인하기 위해 쿼리를 보낼 수도 있지만 request 개수가 증가할수록 쿼리 개수도 증가하기 때문에 처음 받아온 사용자의 장바구니 목록에 filter를 적용합니다.
- OrderService
  - save
  - findById

</br>

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

> - 통합테스트가 구현되었는가?
> - API문서가 구현되었는가?
> - 배포가 정상적으로 되었는가?
> - 서비스에 문제가 발생했을 때, 로그를 통해 문제를 확인할 수 있는가?
>   </br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**

**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

> - PR 제목 : 부산대BE\_라이언\_6주차 과제

</br>

**2. PR 내용 :**

> - 코드 작성하면서 어려웠던 점
> - 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
