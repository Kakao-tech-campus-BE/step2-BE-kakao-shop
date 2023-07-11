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

</br>

### **과제 1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.**
1. 회원가입 
    - 이메일 중복 체크 API는 있지만 버튼이 없음
    - 이메일 인증 기능
2. 전체 상품 목록 조회
    - 상품 등록, 삭제 기능
3. 개별 상품 상세 조회
    - 상품 수정, 옵션 등록, 옵션 삭제, 옵션 수정 기능(상품 올린 사람만)
    - 특딜가로 구매 버튼 API 추가 필요
    - 장바구니에 담기 전, 선택을 해제할 수 있는 기능이 없음
4. 장바구니 조회
    - 담은 항목 삭제 기능
    - 선택 상품만 결제 기능
5. 로그아웃
    - 프론트에서 관리한다 했지만, 리프레시 토큰 만료를 띄워야 하는데 왜 백엔드에 요청을 주지 않는지 모르겠습니다..(코드리뷰 부탁해야 함!)
6. 주문 결과 확인
    - 계속 쇼핑하기 버튼을 누르면 전체 상품 목록 조회 페이지로 넘어가는지, 이전 페이지로 가는지 명확하게 나와있지 않음
7. 배송 관련
    - 배송비 관련해서는 어떤 테이블에서도 데이터를 관리하고있지 않기에 추후에 추가할 필요성이 있어보임

</br>

### **과제 2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)**
![Alt text](images/image-1.png)        
기존 서버의 API 주소를 정리해 놓은 것은 위와 같다.      

![Alt text](images/image.png)        
(기능 1) 회원가입       
회원가입 버튼을 누르면 /join URL에 Post Method로 이메일, 이름, 비밀번호를 담은 요청이 날아가게 된다.        

![Alt text](images/image-2.png)        
(기능 2) 로그인     
로그인 버튼을 누르면 /login URL에 POST Method로 이메일, 비밀번호를 담은 요청이 날아가게 된다.       

![Alt text](images/image-3.png)        
(기능 3) 로그아웃       
로그아웃 버튼을 누르면 /logout URL에 POST Method로 Bearer 토큰을 담은 요청이 날아가게 된다(이론 상. 강사님이 필요없다 하셨는데... 코드리뷰 요청 해야겠다...)

![Alt text](images/image-4.png)        
(기능 4) 전체 상품 목록 조회        
먼저, 전체 상품 목록 조회 버튼을 누르면 /products URL에 GET Method로 page 번호를 parameter로 가진 요청이 날아가게 된다.     
그리고, 해당 상품을 클릭하면 /products/{id} URL에 GET Method로 요청이 간다. 이 때 pathVariable로 상품 id를 넘겨주게 된다.

![Alt text](images/image-5.png)        
(기능 5) 개별 상품 상세 조회        
옵션을 선택하면 옵션 리스트 하단 바에 선택한 옵션과, 수량을 조절할 수 있는 칸이 나오게 된다(프론트가 구현)      
장바구니 담기 버튼을 누르면 carts/add URL에 POST Method로 Bearer 인증 토큰헤더와 optionId, quantity 바디를 가진 요청이 날아가게 된다.       
톡딜가로 구매하기 버튼은 구현하지 않을 예정이지만, 주문하기와 동일한 버튼이라고 생각했을 때 carts/update URL로 Bearer 인증 토큰 헤더와 optionId, quantity 바디를 가진 POST Method 요청이 날아가게 된다.

![Alt text](images/image-6.png)        
(기능 9) 장바구니 보기      
먼저 장바구니 조회 버튼을 클릭하면 /carts URL에 GET Method로 Bearer 인증 토큰 헤더를 가진 요청이 날아가게 된다.     
그 이후에 주문하기 버튼을 누르면 carts/update URL로 Bearer 인증 토큰 헤더와 optionId, quantity 바디를 가진 POST Method 요청이 날아가게 된다.

![Alt text](images/image-7.png)        
(기능 12) 결제      
결제하기 버튼을 누르면, /orders/save URL에 POST Method로 Bearer 인증 토큰 헤더를 담은 요청이 날아가게 된다.

![Alt text](images/image-8.png)        
(기능 13) 주문 결과 확인        
주문 결과 확인 버튼을 누르면, /orders/{orderId} URL에 GET Method로 Bearer 인증 토큰 헤더를 담은 요청이 날아가게 된다.       
</br>

### **과제 3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 작성하시오.**

</br>

**전체 상품 목록 조회**   
먼저, http://localhost:8080/products?page=0 URL로 Get 요청을 보냈을 때의 Response이다.
```{
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

프론트 화면을 띄워보면 무료배송인지 알려주는 아이콘이 있는데, 지금의 response로는 무료배송인지, 배송비가 드는지 알 수 있는 방법이 없다. 따라서 무료 배송인지 알려주는 데이터를 테이블에 추가하고, Dto에도 반영해야 할 것 같다.      
또한, desctiption은 화면에 띄워줄 필요도 없고, 공간도 없기에 Dto에서 빼도 될 것 같다.

</br>

**개별 상품 조회**       
http://localhost:8080/products/1 URL로 Get 요청을 보냈을 때의 response이다.     

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
배송 관련 아직 아무 요구사항이 없기에 이 응답은 프론트에 전부 반영 된 것 같다.

</br>

**이메일 중복 체크**        
http://localhost:8080/check URL로 이메일을 POST로 전달했을 때의 응답이다.       
```
{
    "success": true,
    "response": null,
    "error": null
}
```
중복인지 아닌지 확인하고, 팝업창 등으로 띄워주기만 하면 되기에 응답이 올바르다.

</br>

**회원가입**        
http://localhost:8080/join URL로 이메일, 이름, 비밀번호를 담아 POST로 전달했을 때의 응답이다.       
```
{
    "success": true,
    "response": null,
    "error": null
}
```
따로 띄워줄 게 없기 때문에 올바른 응답이다.    

</br>

**로그인**      
http://localhost:8080/login URL로 이메일, 비밀번호를 담아 POST로 전달했을 때의 응답이다.       

```
{
    "success": true,
    "response": null,
    "error": null
}

Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyNjQyMjB9.SS7RguB9tzsMQOXi7pY9E_X3Cv5YyxUAHImZX3OlyIqM_UC9JpLsGkK8kLLuIy2hKAcRmy95t4ecPAQnljo1Sg
```
성공, 실패만 띄워주고 토큰으로 로그인 지속 여부를 알아야 하기 때문에 올바른 응답이다.    

</br>

**장바구니 담기**       
http://localhost:8080/carts/add URL로 
```
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyNjQyMjB9.SS7RguB9tzsMQOXi7pY9E_X3Cv5YyxUAHImZX3OlyIqM_UC9JpLsGkK8kLLuIy2hKAcRmy95t4ecPAQnljo1Sg
```
위의 Autorization 헤더와 optionId, quantity를 담아서 POST 요청을 보냈을 떄의 응답이다.      

```
{
    "success": true,
    "response": null,
    "error": null
}
```
장바구니에 담겼는지만 확인시켜주면 되기에 올바른 응답이다.      

</br>

**장바구니 조회**       
http://localhost:8080/carts/add URL로 
```
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODgyNjQyMjB9.SS7RguB9tzsMQOXi7pY9E_X3Cv5YyxUAHImZX3OlyIqM_UC9JpLsGkK8kLLuIy2hKAcRmy95t4ecPAQnljo1Sg
```
위의 Autorization 헤더를 담아 Get 요청을 보냈을 때의 응답이다.
```
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
            },
            {
                "id": 2,
                "productName": "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율",
                "carts": [
                    {
                        "id": 3,
                        "option": {
                            "id": 7,
                            "optionName": "22년산 햇단밤 1kg(한정판매)",
                            "price": 14500
                        },
                        "quantity": 1,
                        "price": 14500
                    }
                ]
            }
        ],
        "totalPrice": 119000
    },
    "error": null
}
```
화면과 비교해봤을 때, 응답된 모든 데이터가 프론트 화면에 쓰였으므로 옳게 된 응답이다.       

**결제하기 기능을 구현하지 않는다고 해서 따로 첨부는 하지 않았지만, 응답 데이터가 프론트에 모두 쓰였기 때문에 옳은 응답이다.**

</br>

### **과제 4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.** 
![Alt text](images/image-9.png)
먼저, ERD를 그리기 전에 그린 UML 클래스 다이어그램입니다. 

![Alt text](images/image-11.png)        
두 가지 UML 중에 고민했는데, 공지사항을 보기 전에 진행을 하여 일단 클래스가 6개만 있는 위의 UML로 진행을 하였습니다.

![Alt text](images/image-10.png)
그 뒤, UML 클래스 다이어그램을 토대로 Intellij를 이용해 Entity를 만들고, H2 서버에 임시로 띄운 뒤 생성한 ER 다이어그램입니다.



# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

## **과제명**
```
1. 전체 API 주소 설계
2. Mock API Controller 구현
```

### **과제 1. 전체 API 주소 설계**      
![Alt text](images/week2-image.png)     
Put과 Delete를 포함한 API 명세입니다. Delete는 삭제하는 기능이 없어 사용하지 않았습니다.

</br>

### **과제 2. Mock API Controller 구현**        
</br>       

**전체 상품 목록 조회 Mock API Controller입니다.**      

``` java
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
                4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000
        ));
        responseDTO.add(new ProductRespFindAllDTO(
                5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", "", "/images/5.jpg", 5000
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
```

</br>

**주문 결과 조회**
``` java
@GetMapping("/orders/{id}")
    public ResponseEntity<?> findById (@PathVariable int id) {

        OrderResFindByIdDTO responseDTO = null;

        if(id == 2){
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

            OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                    .id(4)
                    .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                    .quantity(10)
                    .price(100000)
                    .build();
            orderItemDTOList.add(orderItemDTO1);

            OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                    .id(5)
                    .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                    .quantity(10)
                    .price(109000)
                    .build();
            orderItemDTOList.add(orderItemDTO2);

            List<ProductOptionItemDTO> productOptionItemDTOList = new ArrayList<>();
            ProductOptionItemDTO productOptionItemDTO1 = ProductOptionItemDTO.builder()
                    .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                    .items(orderItemDTOList)
                    .build();
            productOptionItemDTOList.add(productOptionItemDTO1);

            responseDTO = OrderResFindByIdDTO.builder()
                    .id(2)
                    .products(productOptionItemDTOList)
                    .totalPrice(209000)
                    .build();

            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }
        else return ResponseEntity.badRequest()
                .body(ApiUtils.error("해당 주문을 찾을 수 없습니다." + id, HttpStatus.BAD_REQUEST));

    }
```

</br>

**결제하기**
```java
@PostMapping("/orders/save")
    public ResponseEntity<?> saveOrder() {
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        orderItemDTOList.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemDTOList.add(orderItemDTO2);

        List<ProductOptionItemDTO> productOptionItemDTOList = new ArrayList<>();
        ProductOptionItemDTO productOptionItemDTO1 = ProductOptionItemDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(orderItemDTOList)
                .build();
        productOptionItemDTOList.add(productOptionItemDTO1);

        OrderSaveResDTO responseDTO = OrderSaveResDTO.builder()
                .id(2)
                .products(productOptionItemDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
```

</br>

**장바구니 수정(주문하기)**
```java
@PutMapping("/carts/update")
    public ResponseEntity<?> updateCarts(@RequestBody List<CartUpdateReqDTO> cartUpdateReqDTOList) {
        //member 찾기
        List<CartUpdateDTO> cartUpdateDTOList = new ArrayList<>();
        CartUpdateDTO cartUpdateDTO1 = CartUpdateDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartUpdateDTOList.add(cartUpdateDTO1);
        CartUpdateDTO cartUpdateDTO2 = CartUpdateDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartUpdateDTOList.add(cartUpdateDTO2);

        CartUpdateResDTO responseDTO = CartUpdateResDTO.builder()
                .carts(cartUpdateDTOList)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
```

</br>

**장바구니 조회**      

``` java
@GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        // 카트 아이템 리스트 만들기
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        // 카트 아이템 리스트에 담기
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
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
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                                .id(1)
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
                        .cartItems(cartItemDTOList)
                        .build()
        );

        CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
```

</br>

**장바구니 담기**

```java
@PostMapping("/carts/add")
    public ResponseEntity<?> addCarts(@RequestBody List<CartAddReqDTO> cartAddReqDTOList) {
        //member 찾기
        return ResponseEntity.ok("ok");
    }
```

</br>

**개별상품 상세 조회**

```java
@GetMapping("/products/{id}")
public ResponseEntity<?> findById(@PathVariable int id) {
    // 상품을 담을 DTO 생성
    ProductRespFindByIdDTO responseDTO = null;

    if(id == 1){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
        optionDTOList.add(new ProductOptionDTO(2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900));
        optionDTOList.add(new ProductOptionDTO(3, "고무장갑 베이지 S(소형) 6팩", 9900));
        optionDTOList.add(new ProductOptionDTO(4, "뽑아쓰는 키친타올 130매 12팩", 16900));
        optionDTOList.add(new ProductOptionDTO(4, "2겹 식빵수세미 6매", 8900));
        responseDTO = new ProductRespFindByIdDTO(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000, 5, optionDTOList);

    }else if(id == 2){
        List<ProductOptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(new ProductOptionDTO(6, "22년산 햇단밤 700g(한정판매)", 9900));
        optionDTOList.add(new ProductOptionDTO(7, "22년산 햇단밤 1kg(한정판매)", 14500));
        optionDTOList.add(new ProductOptionDTO(8, "밤깎기+다회용 구이판 세트", 5500));
        responseDTO = new ProductRespFindByIdDTO(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000, 5, optionDTOList);
    }else {
        return ResponseEntity.badRequest().body(ApiUtils.error("해당 상품을 찾을 수 없습니다 : " + id, HttpStatus.BAD_REQUEST));
    }

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
}
```

로그인, 회원가입 등 유저 관련을 제외한 모든 Controller와 Mock Data이다.


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
