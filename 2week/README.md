# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

</br>

# 2주차
카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제</br>

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

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- User 도메인을 제외한 전체 API 주소 설계가 RestAPI 맞게 설계되었는가? (POST와 GET으로만 구현되어 있어도 됨)
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
>- DTO에 타입은 올바르게 지정되었는가?
>- DTO에 이름은 일관성이 있는가? (예를 들어 어떤 것은 JoinDTO, 어떤 것은 joinDto, 어떤 것은 DtoJoin 이런식으로 되어 있으면 일관성이 없는것이다)
>- DTO를 공유해서 쓰지 않았는가? (동일한 데이터가 응답된다 하더라도, 화면은 수시로 변경될 수 있기 때문에 DTO를 공유하고 있으면 안됨)

</br>

## **1. API주소를 설계하여 README에 내용을 작성하시오.**

```
(기능 1) 회원 가입
- 회원가입 페이지에서 form을 채우고 회원가입 버튼을 누를 때 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/join
```
<details>
<summary>Case</summary>
	
  - Request Body
    ```json
    {
      "username":"mata",
      "email":"meta@nate.com",
      "password":"meta1234!"
    }
    ```
      
  - Response Body
    ```json
    {
      "success": true,
      "response": null,
      "error": null
    }
    ```
      
  - Failure
    - Request Body 1
      ```json
      {
        "username":"mata",
        "email":"metanate.com",
        "password":"meta1234!"
      }
      ```
        
    - Response Body 1
      ```json
      {
        "success": false,
        "response": null,
        "error": {
          "message": "이메일 형식으로 작성해주세요.:email",
          "status": 400
        }
      }
      ```
        
    - Request Body 2
      ```json
      {
        "username":"mata",
        "email":"meta@nate.com",
        "password":"meta1234"
      }
      ```
        
    - Response Body 2
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
        
    - Request Body 3
      ```json
      {
        "username":"mata",
        "email":"mate@nate.com",
        "password":"meta12!"
      }
      ```
        
    - Response Body 3
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
            
</details>

```
(기능 2) 로그인
- 로그인 페이지에서 이메일 비밀번호를 채우고 로그인 버튼을 누를 때 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/login
```
<details>
<summary>Case</summary>
	
  - Request Body
    ```json
    {
      "email":"meta@nate.com",
      "password":"meta1234!"
    }
    ```
      
  - Response Header
    ```json
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRhQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4Nzk3NjR9.FkEGr7U3VT6-u_E7V_Rglv28GhaO30D-wvo1f_MW66E92JSQaHoZDlAyL9Npx7_GjhfPlboKH8BwrJx35xVA-w
    ```
      
  - Response Body
    ```json
    {
      "success": true,
      "response": null,
      "error": null
    }
    ```
      
  - Failure
    - Request Body 1
      ```json
      {
        "email":"metanate.com",
        "password":"meta1234!"
      }
      ```
        
    - Response Body 1
      ```json
      {
        "success": false,
        "response": null,
        "error": {
          "message": "이메일 형식으로 작성해주세요.:email",
          "status": 400
        }
      }
      ```
        
    - Request Body 2
      ```json
      {
        "email":"meta@nate.com",
        "password":"meta1234"
      }
      ```
        
    - Response Body 2
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
        
    - Request Body 3
      ```json
      {
        "email":"meta@nate.com",
        "password":"meta12!"
      }
      ```
        
    - Response Body 3
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
        
    - Request Body 4
      ```json
      {
        "email":"meta1@nate.com",
        "password":"meta1234!"
      }
      ```
        
    - Response Body 4
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
            
</details>

```
(기능 +) 이메일 중복 체크
- 해당 API를 호출하여 입력한 이메일의 중복 여부를 확인한다.
- Method: POST
- URL: http://localhost:8080/check
```
<details>
<summary>Case</summary>
	
  - Request Body
    ```json
    {
      "email":"meta@nate.com"
    }
    ```
      
  - Response Body
    ```json
    {
      "success": true,
      "response": null,
      "error": null
    }
      ```
      
  - Failure
    - Request Body 1
      ```json
      {
        "email":"ssarnate.com"
      }
      ```
        
    - Response Body 1
      ```json
      {
        "success": false,
        "response": null,
        "error": {
          "message": "이메일 형식으로 작성해주세요.:email",
          "status": 400
        }
      }
      ```
        
    - Request Body 2
      ```json
      {
        "email":"ssar@nate.com"
      }
      ```
        
    - Response Body 2
      ```json
      {
        "success": false,
        "response": null,
        "error": {
          "message": "동일한 이메일이 존재합니다.:ssar@nate.com",
          "status": 400
        }
      }
      ```
            
</details>

```
(기능 4) 전체 상품 목록 조회
- 해당 페이지에 들어가면 전체 상품 목록 조회 API를 호출하여 page값에 따라 상품의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products
- Param : page={number}, 디폴트 값 0
```
<details>
<summary>Case</summary>
	
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
          "productName": "바른 누룽지맛 발효효소 2박스 역가수치보장/외 7종",
          "description": "",
          "image": "/images/4.jpg",
          "price": 4000
        },
        {
          "id": 5,
          "productName": "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음/중독성 최고/마른안주",
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

```
(기능 5) 개별 상품 상세 조회
- 개별 상품 페이지에 들어가면 해당 API를 호출하여 해당 상품 옵션들의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products/{product_id}
```
<details>
<summary>Case</summary>
	
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

```
(기능 8) 장바구니 담기
- 로그인이 필요하며 개별 상품 페이지에서 구입하려는 옵션을 선택한 후 장바구니 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/add
```
<details>
<summary>Case</summary>
	
  - Request Header
    ```json
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRhQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4Nzk3NjR9.FkEGr7U3VT6-u_E7V_Rglv28GhaO30D-wvo1f_MW66E92JSQaHoZDlAyL9Npx7_GjhfPlboKH8BwrJx35xVA-w
    ```
      
  - Request Body
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
      
  - Response Body
    ```json
    {
      "success": true,
      "response": null,
      "error": null
    }
    ```
        
</details>

```
(기능 9) 장바구니 조회
- 로그인이 필요하며 장바구니 보기 페이지에서 해당 API를 호출하여 현재 유저의 장바구니에 담긴 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/carts
```
<details>
<summary>Case</summary>
	
  - Request Header
    ```json
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRhQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4Nzk3NjR9.FkEGr7U3VT6-u_E7V_Rglv28GhaO30D-wvo1f_MW66E92JSQaHoZDlAyL9Npx7_GjhfPlboKH8BwrJx35xVA-w
    ```
      
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
        
</details>

```
(기능 11) 주문
- 로그인이 필요하며 장바구니 보기 페이지에서 주문하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/update
```
<details>
<summary>Case</summary>
	
  - Request Header
    ```json
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRhQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4Nzk3NjR9.FkEGr7U3VT6-u_E7V_Rglv28GhaO30D-wvo1f_MW66E92JSQaHoZDlAyL9Npx7_GjhfPlboKH8BwrJx35xVA-w
    ```
      
  - Request Body
    ```json
    [
      {
        "cartId":1,
        "quantity":10
      },
      {
        "cartId":2,
        "quantity":10
      }
    ]
    ```
      
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
        
</details>

```
(기능 12) 결제
- 로그인이 필요하며 상품 주문 및 결제 페이지에서 결제하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/orders/save
```
<details>
<summary>Case</summary>
	
  - Request Header
    ```json
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRhQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4Nzk3NjR9.FkEGr7U3VT6-u_E7V_Rglv28GhaO30D-wvo1f_MW66E92JSQaHoZDlAyL9Npx7_GjhfPlboKH8BwrJx35xVA-w
    ```
      
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
        
</details>

```
(기능 13) 주문 결과 확인
- 유저가 주문에 대해 성공적으로 결제하고 나면 주문 결과 확인 페이지에서 해당 API를 호출한다.
- Method: GET
- URL: http://localhost:8080/orders/{order_id}
```
<details>
<summary>Case</summary>
	
  - Request Header
    ```json
    Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXRhQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg4Nzk3NjR9.FkEGr7U3VT6-u_E7V_Rglv28GhaO30D-wvo1f_MW66E92JSQaHoZDlAyL9Npx7_GjhfPlboKH8BwrJx35xVA-w
    ```
      
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

</details>

</br>

## **2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.**

```
(기능 4) 전체 상품 목록 조회
- 해당 페이지에 들어가면 전체 상품 목록 조회 API를 호출하여 page값에 따라 상품의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products
- Param : page={number}, 디폴트 값 0
```
<details>
<summary>Code</summary>
  
  - ApiUtils.java
    ```java
    import lombok.*;
    import org.springframework.http.HttpStatus;
    
    public class ApiUtils {
        public static <T> ApiResult<T> success(T response) {
            return new ApiResult<>(true, response, null);
        }
    
        public static ApiResult<?> error(String message, HttpStatus status) {
            return new ApiResult<>(false, null, new ApiError(message, status.value()));
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiResult<T> {
            private final boolean success;
            private final T response;
            private final ApiError error;
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiError {
            private final String message;
            private final int status;
        }
    }
    ```
      
  - ProductRespFindAllDTO.java
    ```java
    @Getter @Setter
    public class ProductRespFindAllDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
    
        @Builder
        public ProductRespFindAllDTO(int id, String productName, String description, String image, int price) {
            this.id = id;
            this.productName = productName;
            this.description = description;
            this.image = image;
            this.price = price;
        }
    }
    ```
      
  - ProductRestController.java
    ```java
    import com.example.kakaoshop._core.utils.ApiUtils;
    import com.example.kakaoshop.product.response.ProductRespFindAllDTO;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.ArrayList;
    import java.util.List;
    
    @RestController
    public class ProductRestController {
        @GetMapping("/products")
        public ResponseEntity<?> findAll() {
            List<ProductRespFindAllDTO> responseDTO = new ArrayList<>();
    
            // 상품 하나씩 집어넣기
            responseDTO.add(new ProductRespFindAllDTO(
                    1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    4, "바른 누룽지맛 발효효소 2박스 역가수치보장/외 7종", "", "/images/4.jpg", 4000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음/중독성 최고/마른안주", "", "/images/5.jpg", 5000
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900
            ));
            responseDTO.add(new ProductRespFindAllDTO(
                    9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000
            ));
    
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
        }
    }
    ```
        
</details>

```
(기능 5) 개별 상품 상세 조회
- 개별 상품 페이지에 들어가면 해당 API를 호출하여 해당 상품 옵션들의 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/products/{product_id}
```
<details>
<summary>Code</summary>
  
  - ApiUtils.java
    ```java
    import lombok.*;
    import org.springframework.http.HttpStatus;
    
    public class ApiUtils {
        public static <T> ApiResult<T> success(T response) {
            return new ApiResult<>(true, response, null);
        }
    
        public static ApiResult<?> error(String message, HttpStatus status) {
            return new ApiResult<>(false, null, new ApiError(message, status.value()));
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiResult<T> {
            private final boolean success;
            private final T response;
            private final ApiError error;
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiError {
            private final String message;
            private final int status;
        }
    }
    ```
      
  - ProductRespFindByIdDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class ProductRespFindByIdDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private List<ProductOptionDTO> options;
    
        @Builder
        public ProductRespFindByIdDTO(int id, String productName, String description, String image, int price, List<ProductOptionDTO> options) {
            this.id = id;
            this.productName = productName;
            this.description = description;
            this.image = image;
            this.price = price;
            this.options = options;
        }
    }
    ```
      
  - ProductOptionDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter @Setter
    public class ProductOptionDTO {
        private int id;
        private String optionName;
        private int price;
    
        @Builder
        public ProductOptionDTO(int id, String optionName, int price) {
            this.id = id;
            this.optionName = optionName;
            this.price = price;
        }
    }
    ```
      
  - ProductRestController.java
    ```java
    import com.example.kakaoshop._core.utils.ApiUtils;
    import com.example.kakaoshop.product.response.ProductOptionDTO;
    import com.example.kakaoshop.product.response.ProductRespFindByIdDTO;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.ArrayList;
    import java.util.List;
    
    @RestController
    public class ProductRestController {
        @GetMapping("/products/{id}")
        public ResponseEntity<?> findById(@PathVariable int id) {
            // 상품을 담을 DTO 생성
            ProductRespFindByIdDTO responseDTO = null;
    
            if (id == 1) {
                List<ProductOptionDTO> optionDTOList = new ArrayList<>();
                optionDTOList.add(new ProductOptionDTO(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
                optionDTOList.add(new ProductOptionDTO(2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900));
                optionDTOList.add(new ProductOptionDTO(3, "고무장갑 베이지 S(소형) 6팩", 9900));
                optionDTOList.add(new ProductOptionDTO(4, "뽑아쓰는 키친타올 130매 12팩", 16900));
                optionDTOList.add(new ProductOptionDTO(5, "2겹 식빵수세미 6매", 8900));
                responseDTO = new ProductRespFindByIdDTO(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000, optionDTOList);
            } else if (id == 2) {
                List<ProductOptionDTO> optionDTOList = new ArrayList<>();
                optionDTOList.add(new ProductOptionDTO(6, "22년산 햇단밤 700g(한정판매)", 9900));
                optionDTOList.add(new ProductOptionDTO(7, "22년산 햇단밤 1kg(한정판매)", 14500));
                optionDTOList.add(new ProductOptionDTO(8, "밤깎기+다회용 구이판 세트", 5500));
                responseDTO = new ProductRespFindByIdDTO(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000, optionDTOList);
            } else {
                return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
            }
    
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
    }
    ```
        
</details>

```
(기능 8) 장바구니 담기
- 로그인이 필요하며 개별 상품 페이지에서 구입하려는 옵션을 선택한 후 장바구니 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/add
```
<details>
<summary>Code</summary>
  
  - ApiUtils.java
    ```java
    import lombok.*;
    import org.springframework.http.HttpStatus;
    
    public class ApiUtils {
        public static <T> ApiResult<T> success(T response) {
            return new ApiResult<>(true, response, null);
        }
    
        public static ApiResult<?> error(String message, HttpStatus status) {
            return new ApiResult<>(false, null, new ApiError(message, status.value()));
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiResult<T> {
            private final boolean success;
            private final T response;
            private final ApiError error;
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiError {
            private final String message;
            private final int status;
        }
    }
    ```
      
  - CartRequest.java
    ```java
    import lombok.Getter;
    import lombok.Setter;
    
    public class CartRequest {
        @Getter @Setter
        public static class CartAddDTO {
            private int optionId;
            private int quantity;
        }
    }
    ```
      
  - CartRestController.java
    ```java
    import com.example.kakaoshop._core.utils.ApiUtils;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.ArrayList;
    import java.util.List;
    
    @RestController
    public class CartRestController {
        @PostMapping("/carts/add")
        public ResponseEntity<?> add(@RequestBody List<CartRequest.CartAddDTO> cartAddDTO) {
            return ResponseEntity.ok(ApiUtils.success(null));
        }
    }
    ```
        
</details>

```
(기능 9) 장바구니 조회
- 로그인이 필요하며 장바구니 보기 페이지에서 해당 API를 호출하여 현재 유저의 장바구니에 담긴 데이터를 받아온다.
- Method: GET
- URL: http://localhost:8080/carts
```
<details>
<summary>Code</summary>
  
  - ApiUtils.java
    ```java
    import lombok.*;
    import org.springframework.http.HttpStatus;
    
    public class ApiUtils {
        public static <T> ApiResult<T> success(T response) {
            return new ApiResult<>(true, response, null);
        }
    
        public static ApiResult<?> error(String message, HttpStatus status) {
            return new ApiResult<>(false, null, new ApiError(message, status.value()));
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiResult<T> {
            private final boolean success;
            private final T response;
            private final ApiError error;
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiError {
            private final String message;
            private final int status;
        }
    }
    ```
      
  - CartRespFindAllDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class CartRespFindAllDTO {
        private List<ProductDTO> products;
        private int totalPrice;
    
        @Builder
        public CartRespFindAllDTO(List<ProductDTO> products, int totalPrice) {
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }
    ```
      
  - ProductDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class ProductDTO {
        private int id;
        private String productName;
        private List<CartItemDTO> carts;
    
        @Builder
        public ProductDTO(int id, String productName, List<CartItemDTO> carts) {
            this.id = id;
            this.productName = productName;
            this.carts = carts;
        }
    }
    ```
      
  - CartItemDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter @Setter
    public class CartItemDTO {
        private int id;
        private ProductOptionDTO option;
        private int quantity;
        private int price;
    
        @Builder
        public CartItemDTO(int id, ProductOptionDTO option, int quantity, int price) {
            this.id = id;
            this.option = option;
            this.quantity = quantity;
            this.price = price;
        }
    }
    ```
      
  - ProductOptionDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter @Setter
    public class ProductOptionDTO {
        private int id;
        private String optionName;
        private int price;
    
        @Builder
        public ProductOptionDTO(int id, String optionName, int price) {
            this.id = id;
            this.optionName = optionName;
            this.price = price;
        }
    }
    ```
      
  - CartRestController.java
    ```java
    import com.example.kakaoshop._core.utils.ApiUtils;
    import com.example.kakaoshop.cart.response.CartItemDTO;
    import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
    import com.example.kakaoshop.cart.response.ProductOptionDTO;
    import com.example.kakaoshop.cart.response.ProductDTO;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.ArrayList;
    import java.util.List;
    
    @RestController
    public class CartRestController {
        @GetMapping("/carts")
        public ResponseEntity<?> findAll() {
            // 카트 아이템 리스트 만들기
            List<CartItemDTO> cartItemDTOList = new ArrayList<>();
    
            // 카트 아이템 리스트에 담기
            CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                    .id(1)
                    .quantity(5)
                    .price(50000)
                    .build();
            cartItemDTO1.setOption(ProductOptionDTO.builder()
                    .id(1)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .price(10000)
                    .build());
            cartItemDTOList.add(cartItemDTO1);
    
            CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                    .id(2)
                    .quantity(5)
                    .price(54500)
                    .build();
            cartItemDTO2.setOption(ProductOptionDTO.builder()
                    .id(2)
                    .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                    .price(10900)
                    .build());
            cartItemDTOList.add(cartItemDTO2);
    
            // productDTO 리스트 만들기
            List<ProductDTO> productDTOList = new ArrayList<>();
    
            // productDTO 리스트에 담기
            productDTOList.add(
                    ProductDTO.builder()
                            .id(1)
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .carts(cartItemDTOList)
                            .build()
            );
    
            CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);
    
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
    }
    ```
        
</details>

```
(기능 11) 주문
- 로그인이 필요하며 장바구니 보기 페이지에서 주문하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/update
```
<details>
<summary>Code</summary>
  
  - ApiUtils.java
    ```java
    import lombok.*;
    import org.springframework.http.HttpStatus;
    
    public class ApiUtils {
        public static <T> ApiResult<T> success(T response) {
            return new ApiResult<>(true, response, null);
        }
    
        public static ApiResult<?> error(String message, HttpStatus status) {
            return new ApiResult<>(false, null, new ApiError(message, status.value()));
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiResult<T> {
            private final boolean success;
            private final T response;
            private final ApiError error;
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiError {
            private final String message;
            private final int status;
        }
    }
    ```
      
  - CartRequest.java
    ```java
    import lombok.Getter;
    import lombok.Setter;
    
    public class CartRequest {
        @Getter @Setter
        public static class CartUpdateDTO {
            private int cartId;
            private int quantity;
        }
    }
    ```
      
  - CartRespUpdateDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class CartRespUpdateDTO {
        private List<CartItemUpdateDTO> carts;
        private int totalPrice;
    
        @Builder
        public CartRespUpdateDTO(List<CartItemUpdateDTO> carts, int totalPrice) {
            this.carts = carts;
            this.totalPrice = totalPrice;
        }
    }
    ```
      
  - CartItemUpdateDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter @Setter
    public class CartItemUpdateDTO {
        private int cartId;
        private int optionId;
        private String optionName;
        private int quantity;
        private int price;
    
        @Builder
        public CartItemUpdateDTO(int cartId, int optionId, String optionName, int quantity, int price) {
            this.cartId = cartId;
            this.optionId = optionId;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
    ```
      
  - CartRestController.java
    ```java
    import com.example.kakaoshop._core.utils.ApiUtils;
    import com.example.kakaoshop.cart.response.CartItemUpdateDTO;
    import com.example.kakaoshop.cart.response.CartRespUpdateDTO;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.ArrayList;
    import java.util.List;
    
    @RestController
    public class CartRestController {
        @PostMapping("/carts/update")
        public ResponseEntity<?> update(@RequestBody List<CartRequest.CartUpdateDTO> cartUpdateDTO) {
            // 카트 아이템 리스트 만들기
            List<CartItemUpdateDTO> cartItemUpdateDTOList = new ArrayList<>();
    
            // 카트 아이템 리스트에 담기
            CartItemUpdateDTO cartItemUpdateDTO1 = CartItemUpdateDTO.builder()
                    .cartId(1)
                    .optionId(1)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            cartItemUpdateDTOList.add(cartItemUpdateDTO1);
    
            CartItemUpdateDTO cartItemUpdateDTO2 = CartItemUpdateDTO.builder()
                    .cartId(2)
                    .optionId(2)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            cartItemUpdateDTOList.add(cartItemUpdateDTO2);
    
            CartRespUpdateDTO responseDTO = new CartRespUpdateDTO(cartItemUpdateDTOList, 209000);
    
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
    }
    ```
        
</details>

```
(기능 12) 결제
- 로그인이 필요하며 상품 주문 및 결제 페이지에서 결제하기 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/orders/save
```
<details>
<summary>Code</summary>
  
  - ApiUtils.java
    ```java
    import lombok.*;
    import org.springframework.http.HttpStatus;
    
    public class ApiUtils {
        public static <T> ApiResult<T> success(T response) {
            return new ApiResult<>(true, response, null);
        }
    
        public static ApiResult<?> error(String message, HttpStatus status) {
            return new ApiResult<>(false, null, new ApiError(message, status.value()));
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiResult<T> {
            private final boolean success;
            private final T response;
            private final ApiError error;
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiError {
            private final String message;
            private final int status;
        }
    }
    ```
      
  - OrderRespSaveDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class OrderRespSaveDTO {
        private int id;
        private List<ProductSaveDTO> products;
        private int totalPrice;
    
        @Builder
        public OrderRespSaveDTO(int id, List<ProductSaveDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }
    ```
      
  - ProductSaveDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class ProductSaveDTO {
        private String productName;
        private List<OrderItemSaveDTO> items;
    
        @Builder
        public ProductSaveDTO(String productName, List<OrderItemSaveDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }
    ```
      
  - OrderItemSaveDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter @Setter
    public class OrderItemSaveDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;
    
        @Builder
        public OrderItemSaveDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
    ```
      
  - OrderRestController.java
    ```java
    import com.example.kakaoshop._core.utils.ApiUtils;
    import com.example.kakaoshop.order.response.OrderItemSaveDTO;
    import com.example.kakaoshop.order.response.OrderRespSaveDTO;
    import com.example.kakaoshop.order.response.ProductSaveDTO;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.ArrayList;
    import java.util.List;
    
    @RestController
    public class OrderRestController {
        @PostMapping("/orders/save")
        public ResponseEntity<?> save(){
            // 주문 아이템 리스트 만들기
            List<OrderItemSaveDTO> orderItemSaveDTOList = new ArrayList<>();
    
            // 주문 아이템 리스트에 담기
            OrderItemSaveDTO orderItemSaveDTO1 = OrderItemSaveDTO.builder()
                    .id(1)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            orderItemSaveDTOList.add(orderItemSaveDTO1);
    
            OrderItemSaveDTO orderItemSaveDTO2 = OrderItemSaveDTO.builder()
                    .id(2)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            orderItemSaveDTOList.add(orderItemSaveDTO2);
    
            // productDTO 리스트 만들기
            List<ProductSaveDTO> productSaveDTOList = new ArrayList<>();
    
            // productDTO 리스트에 담기
            productSaveDTOList.add(
                    ProductSaveDTO.builder()
                            .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                            .items(orderItemSaveDTOList)
                            .build()
            );
    
            OrderRespSaveDTO responseDTO = new OrderRespSaveDTO(1, productSaveDTOList, 209000);
    
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
    }
    ```
        
</details>

```
(기능 13) 주문 결과 확인
- 유저가 주문에 대해 성공적으로 결제하고 나면 주문 결과 확인 페이지에서 해당 API를 호출한다.
- Method: GET
- URL: http://localhost:8080/orders/{order_id}
```
<details>
<summary>Code</summary>
  
  - ApiUtils.java
    ```java
    import lombok.*;
    import org.springframework.http.HttpStatus;
    
    public class ApiUtils {
        public static <T> ApiResult<T> success(T response) {
            return new ApiResult<>(true, response, null);
        }
    
        public static ApiResult<?> error(String message, HttpStatus status) {
            return new ApiResult<>(false, null, new ApiError(message, status.value()));
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiResult<T> {
            private final boolean success;
            private final T response;
            private final ApiError error;
        }
    
        @Getter @Setter @AllArgsConstructor
        public static class ApiError {
            private final String message;
            private final int status;
        }
    }
    ```
      
  - OrderRespFindByIdDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class OrderRespFindByIdDTO {
        private int id;
        private List<ProductDTO> products;
        private int totalPrice;
    
        @Builder
        public OrderRespFindByIdDTO(int id, List<ProductDTO> products, int totalPrice) {
            this.id = id;
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }
    ```
      
  - ProductDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.List;
    
    @Getter @Setter
    public class ProductDTO {
        private String productName;
        private List<OrderItemDTO> items;
    
        @Builder
        public ProductDTO(String productName, List<OrderItemDTO> items) {
            this.productName = productName;
            this.items = items;
        }
    }
    ```
      
  - OrderItemDTO.java
    ```java
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter @Setter
    public class OrderItemDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;
    
        @Builder
        public OrderItemDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
    ```
      
  - OrderRestController.java
    ```java
    import com.example.kakaoshop._core.utils.ApiUtils;
    import com.example.kakaoshop.order.response.OrderItemDTO;
    import com.example.kakaoshop.order.response.OrderRespFindByIdDTO;
    import com.example.kakaoshop.order.response.ProductDTO;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.ArrayList;
    import java.util.List;
    
    @RestController
    public class OrderRestController {
        @GetMapping("/orders/{id}")
        public ResponseEntity<?> findById(@PathVariable int id) {
            // 상품을 담을 DTO 생성
            OrderRespFindByIdDTO responseDTO = null;
    
            if (id == 1) {
                // 주문 아이템 리스트 만들기
                List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
    
                // 주문 아이템 리스트에 담기
                OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                        .id(1)
                        .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                        .quantity(10)
                        .price(100000)
                        .build();
                orderItemDTOList.add(orderItemDTO1);
    
                OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                        .id(2)
                        .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                        .quantity(10)
                        .price(109000)
                        .build();
                orderItemDTOList.add(orderItemDTO2);
    
                // productDTO 리스트 만들기
                List<ProductDTO> productDTOList = new ArrayList<>();
    
                // productDTO 리스트에 담기
                productDTOList.add(
                        ProductDTO.builder()
                                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                                .items(orderItemDTOList)
                                .build()
                );
    
                responseDTO = new OrderRespFindByIdDTO(1, productDTOList, 209000);
            } else {
                return ResponseEntity.badRequest().body(ApiUtils.error("해당 주문을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
            }
    
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
    }
    ```
