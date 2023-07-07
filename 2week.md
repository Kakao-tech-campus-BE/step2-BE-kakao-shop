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

## 1. 전체 상품 목록 조회

- Method : Get
- Local URL : http://localhost:8080/products
- Param : page=
  
   ```java
  @GetMapping("/products")
       public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
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

 ## 2. 개별 상품 상세 조회

- Method : Get
- Local URL : http://localhost:8080/products/{id}

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

}
```

## 3. 장바구니 담기
- Method : Post
- Local URL : http://localhost:8080/carts/add
  
## 4. 장바구니 조회
- Method : Get
- Local URL : http://localhost:8080/carts

```java
@RestController
public class CartRestController {

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
}
@PostMapping("/carts/add")
public ResponseEntity<?> add(@RequestBody List<CartRequest.UpdateDTO> addDTO) {
    return ResponseEntity.ok(ApiUtils.success(null));
}
@PostMapping("/carts/update")
public ResponseEntity<?> update(@RequestBody List<CartRequest.UpdateDTO> updateDTO) {
    List<OptionDTO> optionDTOList = new ArrayList<>();
        OptionDTO cartItemDTO1 = OptionDTO.builder()
                .cartId(updateDTO.get(0).getCartId())
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(updateDTO.get(0).getQuantity())
                .price(100000)
                .build();

        optionDTOList.add(cartItemDTO1);
        OptionDTO cartItemDTO2 = OptionDTO.builder()
                .cartId(updateDTO.get(1).getCartId())
                .optionId(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .quantity(updateDTO.get(1).getQuantity())
                .price(109000)
                .build();

        optionDTOList.add(cartItemDTO2);
        CartsFindAllDTO cartsFindAllDTO = new CartsFindAllDTO(optionDTOList, 209000);

        return ResponseEntity.ok(ApiUtils.success(cartsFindAllDTO));
}
@PostMapping("/carts/update")
public ResponseEntity<?> update(@RequestBody List<CartRequest.UpdateDTO> updateDTO) {
    List<OptionDTO> optionDTOList = new ArrayList<>();
    OptionDTO cartItemDTO1 = OptionDTO.builder()
            .cartId(updateDTO.get(0).getCartId())
            .optionId(1)
            .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
            .quantity(updateDTO.get(0).getQuantity())
            .price(100000)
            .build();
    optionDTOList.add(cartItemDTO1);
    OptionDTO cartItemDTO2 = OptionDTO.builder()
            .cartId(updateDTO.get(1).getCartId())
            .optionId(1)
            .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
            .quantity(updateDTO.get(1).getQuantity())
            .price(109000)
            .build();

     optionDTOList.add(cartItemDTO2);
     CartsFindAllDTO cartsFindAllDTO = new CartsFindAllDTO(optionDTOList, 209000);

     return ResponseEntity.ok(ApiUtils.success(cartsFindAllDTO));
}
```

## 5. 주문하기 -> 장바구니 수정
- Method : Post
- Local URL : http://localhost:8080/carts/update

```java
@Getter @Setter
public class ProductOptionDTO {

    private int cartId;
    private int optionId;

    private String optionName;
    private int price;
    private int quantity;

    @Builder
    public ProductOptionDTO(int cartIdid, String optionName, int price, int quantity) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.price = price;
        this.quantity = quantity;
    }
}
@Getter @Setter
//최종적으로 CartsUpdate 데이터 반환 DTO
public class CartsFindAllDTO {
    private List<ProductOptionDTO> carts;
    private int totalPrice;
    @Builder
    public CartsFindAllDTO(List<ProductOptionDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
```

## 6. 결재하기 

- Method : Post
- Local URL : http://localhost:8080/orders/save
  
```java
@Getter @Setter
public class ProductDTO {

    private String productName;
    private List<ProductOptionDTO> cartItems;

    @Builder
    public ProductDTO(String productName, List<ProductOptionDTO> cartItems) {

        this.productName = productName;
        this.cartItems = cartItems;
    }
}
@Getter @Setter
public class OrderRespFindAllDTO {
    private int id;
    private ProductDTO products;
    private int totalPrice;

    @Builder()
    public OrderRespFindAllDTO(int id, ProductDTO products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
```

```java
public class OrderRestController {
    @PostMapping("/orders/save")
    public ResponseEntity<?> save() {
        List<ProductOptionDTO> productOptionDTOList = new ArrayList<>();
        ProductOptionDTO option1 = ProductOptionDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        productOptionDTOList.add(option1);
        ProductOptionDTO option2 = ProductOptionDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        productOptionDTOList.add(option2);
        ProductDTO product1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(optionDTOList)
                .build();
        OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);
        return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
    }
}
```

## 7. 주문 조회
- Method : Get
- Local URL : http://localhost:8080/orders/{id}
  
```java
 @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        List<ProductOptionDTO> productOptionDTOList = new ArrayList<>();
        ProductOptionDTO option1 = ProductOptionDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        ProductOptionDTO option2 = ProductOptionDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        productOptionDTOList.add(option1);
        productOptionDTOList.add(option2);
        ProductDTO product1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(optionDTOList)
                .build();
        OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);

        return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
    }
```
