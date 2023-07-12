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

# 과제 조건1
```
1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
```

- <h3>로그인 기능

POSTMAN 요청 테스트 결과 액세스 토큰만 반환해주는거 같은데, 리프레쉬 토큰을 도입안한다면 액세스 토큰의 만료기간이 지난다면 
다시 로그인 해야되는 번거로움이 있지 않을지 궁금합니다! <br><br>
=> jwt token 온거에 exp 보니까 로그인한 기준으로 이틀 후 까지던데, 엑세스 토큰치고 너무 유효기간이 길지 않은지 궁금합니다!

- <h3>상품 옵션 선택 + 옵션 확인 및 수량 결정

상품 옵션을 선택후에 재선택하면, "이미 선택한 옵션입니다" 가아니라 처음에 선택했을때 강조를 해주고, 재선택하면 강조가 풀리면서 취소가 가능해야 될거 같습니다!

- <h3> 장바구니 상품 옵션 확인 및 수량 결정

장바구니에서 상품과 옵션들리스트가 보이는 화면에서도, 개별 취소 기능이 추가되면 좋겠고,
장바구니 비우기 라는 기능도 추가 되면 좋겠습니다.


# 과제 조건 2
```
2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오.
 (카카오 화면설계 시나리오가 있음)
 ```
- <h3> 회원 가입
Method : Post    <br>
Endpoint : /join    <br>
Action : 회원 가입 버튼을 누를떄  <br><br>
Request Body    <br>
```Json
{
  "username": "mata",
  "email": "meta@nate.com",
  "password": "meta1234!"
}
```
Response Body    <br>
- 성공 시 (status code : 200)
```Json
{
  "success": true,
  "response": null,
  "error": null
}
```
- 실패 시 (status code : 400)
1. 이메일의 형식을 지키지 않은 경우
2. 동일한 이메일로 이미 회원이 존재할 경우
3. 비밀번호가 8~20자 이내가 아닐 경우
4. 비밀번호가 영문, 숫자, 특수문자가 포함되어야하고 공백이 포함되어있지 않을 경우
```Json
{
  "success": false,
  "response": null,
  "error": {
    "message": "에러종류에 따른 에러메세지 나타남",
    "status": 400
  }
}
```

- <h3> 로그인
Method : Post    <br>
Endpoint : /login    <br> 
Action : 로그인 버튼을 누를때  <br><br>
Request Body    <br>
```Json
{
  "email":"ssar@nate.com",
  "password":"meta1234!"
}
```
Response Header    <br>
- 성공 시 (status code : 200)
```
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

Response Body    <br>
- 성공 시 (status code : 200)
```Json
{
  "success": true,
  "response": null,
  "error": null
}
```
- 실패 시 (status code : 400)
1. 이메일의 형식을 지키지 않은 경우
2. 비밀번호가 영문, 숫자, 특수문자가 포함되어야하고 공백이 포함되어있지 않을 경우
3. 비밀번호가 8~20자 이내가 아닐 경우
4. 이메일이나 비밀번호가 틀렸을 경우

```Json
{
  "success": false,
  "response": null,
  "error": {
    "message": "에러종류에 따른 에러메세지 나타남",
    "status": 400
  }
}
```
- <h3> 이메일 중복 체크
Method : Post    <br>
Endpoint : /check    <br> 
Action : 로그인 창에서 이메일이 갱신 될떄마다? <br><br>
Request Body    <br>
```Json
{
  "email":"meta@nate.com"
}
```
Response Body    <br>
- 성공 시 (status code : 200)
```Json
{
  "success": true,
  "response": null,
  "error": null
}
```
- 실패 시 (status code : 400)
1. 동일한 이메일이 이미 존재할 경우
2. 이메일 형식으로 작성하지 않은 경우
```Json
{
  "success": false,
  "response": null,
  "error": {
    "message": "에러종류에 따른 에러메세지 나타남",
    "status": 400
  }
}
```


- <h3> 전체 상품 목록 조회
Method : Get    <br>
Endpoint : /products    <br>
Param : page={number} (디폴트 값은 0이다.)  <br>
page를 통해 페이징 기능 제공  <br>
Action : 메인 화면에 접근할 경우 <br><br>
Response Body    <br>
- 성공 시 (status code : 200)
```Json
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
                            .
                            .
                            .

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


- <h3> 개별 상품 목록 조회
Method : Get    <br>
Endpoint : /products/{id}    <br>
Action : 제품 하나를 선택해서 제품 상세 페이지에 접근할 경우 <br><br>
Response Body    <br>
- 성공 시 (status code : 200)
```Json
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

- <h3> 장바구니 담기
Method : Post    <br>
Endpoint : /carts/add    <br>
Action : 장바구니 담기 아이콘 버튼을 클릭할 경우 <br><br>

Request Header    <br>
```Text
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

Request Body    <br>
```Json
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

Response Body    <br>
- 성공 시 (status code : 200)
```Json
{
  "success": true,
  "response": null,
  "error": null
}
```

- <h3> 장바구니 조회
Method : Get    <br>
Endpoint : /carts    <br>
Action : 우측 상단에 장바구니 아이콘을 클릭할 경우 <br><br>
Request Header    <br>
```Text
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
Response Body    <br>
- 성공 시 (status code : 200)
```Json
{
  "success": true,
  "response": {
    "products": [
      {
        "id": 1,
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",
        "carts": [
          {
            "id": 4,
            "option": {
              "id": 1,
              "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
              "price": 10000
            },
            "quantity": 5,
            "price": 50000
          }
        ]
      }
    ],
    "totalPrice": 104500
  },
  "error": null
}

```
- <h3> 장바구니 수정(주문하기)
Method : Post    <br>
Endpoint : /carts/update    <br>
Action : 주문하기 버튼을 클릭할 경우 <br><br>
Request Header    <br>
```Text
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```
Request Body    <br>
```Json
[
  {
    "cartId":4,
    "quantity":10
  },
  {
    "cartId":5,
    "quantity":10
  }
]
```
Response Body    <br>
- 성공 시 (status code : 200)
```Json
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
- <h3> 결제하기
Method : Post    <br>
Endpoint : /orders/save    <br>
Action : 결제하기 버튼을 클릭할 경우 <br><br>
Request Header    <br>
```Text
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

Response Body    <br>
- 성공 시 (status code : 200)
```Json
{
  "success": true,
  "response": {
    "id": 2,
    "products": [
      {
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",
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
# 과제 조건 3
```
3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
```
### 1. 고객이 결제한 모든 주문리스트를 가져오는 api 가 있으면 좋겠습니다 <br><br>
예를 들어
- <h3> 주문 목록 조회
Method : Get    <br>
Endpoint : /orders    <br>
Action : 주문내역이 있다고 가정하고, 주문내역 버튼을 클릭할 경우 <br><br>
Request Header    <br>
```Text
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg
```

Response Body    <br>
- 성공 시 (status code : 200)
```Json
{
  "success": true,
  "response": [
    {
      "id": 1,
      "products": [
        {
          "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",
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
    {
    "id": 2,
    "products": [
      {
        "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",
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
    {
      "id": 3,
      "products": [
        {
          "productName": "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션외 주방용품 특가전",
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
     }
    ],
  "error": null
 }
```

이렇게 해당 고객이 주문한 모든 주문 내역을 볼 수 있는 API가 나중에 필요하지 않을까? 
생각이 됩니다.

### 2.장바구니를 id로 조회해서 삭제하는 API가 필요해 보입니다.

- 장바구니 개별 삭제

Method : Post

Endpoint : /carts/delete/{id}

Action : 현재 삭제 액션 구현 되어있지 않음

Request Header

```
Bearer
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6Il
JPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODcwNTM5MzV9.fXlD0NZQXYYfPHV8rokRJTM86nhS869L
Z1KIGi7_qvPOcVbXgvyZLKvnlLxomIiS3YFnQRLzXAJ2G41yI_AmGg

```

Response Body

- 성공 시 (status code : 200)

```json
{ 
  "success": true,
  "response": {
                "id": 34,
                "option": {
                            "id": 1,
                            "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종",
                            "price": 10000
                          },
                  "quantity": 5,
                  "price": 50000
              },
  "error": null
}
```

# 과제 조건 4

```
4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
```

## erd 최종본 png 참조


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
