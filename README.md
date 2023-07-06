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
