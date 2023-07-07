# 2주차 과제
## API 주소 설계

### User
| API | URI | HTTP Method | 
| --- | --- | --- |
| 회원가입 | /auth/join | POST | 
| 로그인 | /auth/login | POST |

### Product
| API | URI | HTTP Method | 
| --- | --- | --- |
| 전체 상품 조회 | /products | GET | 
| 개별 상품 조회 | /products/{id} | GET |
<br>
(전체 상품 조회는 페이지를 Param으로 받아 매핑한다. page의 디폴트 값은 0이다.)

### Cart
| API | URI | HTTP Method | 
| --- | --- | --- |
| 장바구니 조회 | /carts | GET | 
| 장바구니 담기 | /carts/add | POST |
| 장바구니 수정 | /carts/update | POST |

### Order
| API | URI | HTTP Method | 
| --- | --- | --- |
| 결제하기 | /orders/save | POST |
| 주문 확인 | /orders/{id} | GET |

## Mock API Controller 구현

<br> 강사님이 제공해주신 베이스 소스가 있어, 제가 구현해야할 API는 4가지였습니다.
<br> RequestBody에 상관없이 Controller에서 더미 데이터를 작성하고 ResponseBody만 테스트하는 과제이기에 이에 중점을 뒀습니다.

### 장바구니 담기
클라이언트측에서 요청이 담긴 DTO를 작성하였습니다.
<br> 두개의 소스파일보다 하나의 소스파일에서 이너 클래스로 관리하는 것이 효율적이라 생각하여 이너 클래스로 작성하였습니다.
```java
import lombok.Getter;

public class CartRequest {
    @Getter
    public static class AddDTO {
        private int optionId;
        private int quantity;
    }

    @Getter
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
```
base URI를 기준으로 분기를 하기 위해 base URI를 기준으로 맵핑하였습니다.
<br> RequestBody의 요청은 여러개의 상품 담기를 요청하기 때문에 List 자료형으로 받아옵니다.
```java
@RequestMapping(value = "/carts")
public class CartRestController {
...
  @PostMapping("/add")
  public ResponseEntity<?> cartAdd(@RequestBody List<CartRequest.AddDTO> addDTOs) {
      return ResponseEntity.ok(ApiUtils.success(null));
  }
...
```
JSON 응답입니다.
```json
//과제에서 요구하는 JSON 응답
{
    "success": true,
    "response": null,
    "error": null
}
//요청을 제대로 전달받았는지 확인하는 JSON 응답
{
    "success": true,
    "response": [
        {
            "optionId": 1,
            "quantity": 5
        },
        {
            "optionId": 2,
            "quantity": 5
        }
    ],
    "error": null
}
```

### 장바구니 수정
Setter보다 가독성이 좋은 Builder를 선택하여 객체를 초기화하도록 하였습니다.
```java
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartUpdateDTO {
    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public CartUpdateDTO(int cartId, int optionId, String optionName, int quantity, int price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
```
RequestBody의 요청은 여러개의 상품 수정을 요청하기 때문에 List 자료형으로 받아옵니다.
<br> JSON 응답을 위한 더미 데이터를 작성했습니다.
```java
@PostMapping("/update")
    public ResponseEntity<?> cartUpdate(@RequestBody List<CartRequest.UpdateDTO> updateDTOs) {
        List<CartUpdateDTO> cartUpdateDTOs = new ArrayList<>();

        CartUpdateDTO cartUpdateDTO1 = CartUpdateDTO.builder()
                .cartId(1)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartUpdateDTOs.add(cartUpdateDTO1);

        CartUpdateDTO cartUpdateDTO2 = CartUpdateDTO.builder()
                .cartId(2)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartUpdateDTOs.add(cartUpdateDTO2);

        CartRespUpdateDTO responseDTO = CartRespUpdateDTO.builder()
                .carts(cartUpdateDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
```
JSON 응답입니다.
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

### 결제하기
해당 API의 요구 ResponseBody는 아래와 같습니다.
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
1. 주문 아이템은 옵션 ID로 구분되어 상품에 저장되었습니다. -> 주문 아이템 관련 DTO 1개 -(1)
2. 상품은 상품 이름을 기준으로 주문서에 저장되었습니다. -> 상품 관련 DTO 1개 -(2)
3. 주문서는 주문 ID로 구분됩니다. -> 주문 저장을 위한 DTO 1개 -(3)
<br>
저장 순서는 아래와 같습니다.
<br> 주문 아이템 -> 상품 -> 주문서
<br> 총 3개의 DTO가 필요할 것으로 생각됩니다.
<br> (1) 주문 아이템 관련 DTO - OrderItemDTO
<br>

```java
import lombok.Builder;
import lombok.Getter;

@Getter
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
(2) 상품 관련 DTO : OrderProductDTO
<br>

```java
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class OrderProductDTO {
    private String productName;
    private List<OrderItemDTO> items;

    @Builder
    public  OrderProductDTO(String productName, List<OrderItemDTO> items) {
        this.productName = productName;
        this.items = items;
    }
}
```
하나의 상품에 여러개의 옵션이 담기기 때문에 주문 아이템을 List 자료형으로 저장합니다.
<br> (3) 주문 저장을 위한 DTO - OrderRespSaveDTO
<br>

```java
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class OrderRespSaveDTO {
    private int id;
    private List<OrderProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespSaveDTO(int id, List<OrderProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
```
과제에서 요구하는 JSON 응답은 1개의 상품이었지만, 하나의 주문에는 여러 상품이 들어갈 수 있습니다.
<br> (JSON 응답을 살펴보면 product는 리스트([]) 자료형으로 응답하고 있습니다.) <br>
<br> 더미 데이터를 작성하여 Controller 구현하였습니다.
```java
@RequestMapping(value = "/orders")
public class OrderRestController {
    @PostMapping("/save")
    public ResponseEntity<?> orderSave() {
        List<OrderItemDTO> itemDTOs = new ArrayList<>();

        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        itemDTOs.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        itemDTOs.add(orderItemDTO2);
        
        List<OrderProductDTO> productDTOs = new ArrayList<>();

        //주문 아이템 리스트를 상품에 담습니다.
        OrderProductDTO orderProductDTO = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(itemDTOs)
                .build();
        //상품 리스트에 상품을 담습니다.
        productDTOs.add(orderProductDTO);
        
        OrderRespSaveDTO responseDTO = OrderRespSaveDTO.builder()
                .id(1)
                .products(productDTOs)
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
```
JSON 응답입니다.
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
### 주문 확인
해당 API는 결제하기와 같은 ResponseBody를 요구합니다.
<br> 중복을 피하기 위해 별도의 DTO를 작성하지 않았습니다.
<br> 주문 번호로 주문을 조회하기 위해 @PathVariable 애노테이션을 작성하였습니다.

```java
@GetMapping("/{id}")
public ResponseEntity<?> orderFindById(@PathVariable int id) {
...
}
```
(내부 더미 데이터 작성과 응답 로직은 결제하기와 중복되어 작성 생략하겠습니다.)

## 리팩토링 단계

### DTO 중복
1. 결제하기와 주문 확인 API의 DTO가 중복되므로 동일한 DTO를 사용하였습니다.
2. 동일한 DTO를 사용하게 되면 둘 중에 하나의 API 응답이 수정되면 다른 DTO를 생성해야합니다.
3. 그래서 나중에 생길 수도 있는 수정 단계를 고려하여 DTO를 구분하여 구현합니다.
4. DTO 네이밍이 복잡하고 어려워졌습니다. 예를들면 OrderItemForSaveDTO 같은 길고 이해하기 어려운 명명을 피하려고 합니다.
5. 수정도 쉽게 하고 하나의 컨트롤러에서 생기는 여러가지 응답을 하나의 소스파일로 관리할 수 있도록 이너 클래스로 작성합니다.
<br>

```java
import lombok.Builder;
import lombok.Getter;

@Getter
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
위 DTO는 중복으로 사용됐는데, 이는 둘중에 하나의 기능이 DTO를 수정하면 문제가 발생하므로 또 다른 DTO를 생성해야 하는 상황이 발생합니다.
```java
import lombok.Builder;
import lombok.Getter;

public class OrderItem {
    @Getter
    public static class SaveDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public SaveDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }

    @Getter
    public static class ConfirmDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public ConfirmDTO(int id, String optionName, int quantity, int price) {
            this.id = id;
            this.optionName = optionName;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
```
그래서 비슷한 성격의 DTO를 이너 클래스들로 구현하여 수정이 간편하도록 구현하였습니다.

### page 단위의 전체 상품 조회 구현
1. 강사님이 구현해두신 전체 상품 조회 기능은 9번의 상품까지만 조회합니다.
2. 이미지 폴더에는 15개의 상품 사진이 등록되어 있고 API 명세서에는 페이지 별로 상품을 다르게 출력하게 되어 있습니다.
3. Controller 수정의 필요성을 느끼게 되어 리팩토링하게 되었습니다.
<br> 아래는 기존의 컨트롤러입니다.
<br>

```java
@GetMapping("/products")
    public ResponseEntity<?> findAll() {
```
page는 입력되지 않을 수 있기 때문에 required = false를 설정하였으며 입력하지 않았을 시 페이지 0을 띄우기 위해 디폴트 값을 0으로 설정하였습니다.
```java
@GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(value="page", required = false, defaultValue = "0")int id) {
```
