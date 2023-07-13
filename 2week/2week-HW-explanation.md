# 2주차 과제

## 전체 상품조회 / 개별 상품조회

git clone 받을 때 전체 상품 조회와 개별 상품조회는 이미 DTO와 RestController가 잘 구현되어있어서 따로 작성하지 않았습니다.

## 장바구니 담기

Method : Post
Local URL : http://localhost:8080/carts/add

Request header에는 토큰 정보가 들어가고 Body에는 다음과 같기 때문에

```json
[
{   "optionId":1,
    "quantity":5
  },
  {
    "optionId":2,
    "quantity":5
} 
]
```

ProductOptionDTO2(요청)을 다음과 같이 만들어 주었다.

```java
package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDTO2 {
    private int optionId;
    private int quantity;
    @Builder
    public ProductOptionDTO2(int optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }
}
```

장바구니 추가하는 요청의 응답이 다음과 같기 때문에

```json
{
    "success": true,
    "response": null,
    "error": null
}
```

RestController는 다음과 같이 작성해주었다.

```java
@PostMapping("carts/add")
 public ResponseEntity<?> addCart(){
    return ResponseEntity.ok((ApiUtils.success(null)));
 }
```

## 주문하기 - (장바구니 수정)

Method : Put
Local URL : http://localhost:8080/carts/update

Request header에는 토큰 정보가 들어가고 Body에는 다음과 같기 때문에

```json
[
{   "cartId":4,
    "quantity":10
  },
  {
    "cartId":5,
    "quantity":10
} 
]
```

CartDTO2(요청)를 다음과 같이 만들어주었다.

```java
package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO2 {
    private int cartId;
    private int quantity;
    @Builder
    public CartDTO2(int cartId, int quantity) {
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
```

장바구니 수정하는 요청의 응답이 다음과 같기 때문에

```json
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
       {    "cartId": 5,
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

CartDTO(응답)를 다음과 같이 만들고 

```java
package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;
    @Builder
    public CartDTO(int cartId, int optionId, String optionName, int quantity, int price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
```

장바구니를 수정하는것 이기때문에 Put메서드를 사용해 RestController를 작성해주었다.

```java
@PutMapping("carts/update")
    public ResponseEntity<?> updateCart(@RequestBody List<CartDTO2> cartDTO2List) {
        //카트 리스트 만들기
        List<CartDTO> cartDTOList = new ArrayList<>();
        CartDTO cartDTO1 = CartDTO.builder()
                .cartId(4)
                .optionId(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        cartDTOList.add(cartDTO1);

        CartDTO cartDTO2 = CartDTO.builder()
                .cartId(5)
                .optionId(2)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        cartDTOList.add(cartDTO2);
        return ResponseEntity.ok(cartDTOList);
    }
```

## 결제하기 - (주문 인서트)

Method : Post
Local URL : http://localhost:8080/orders/save

Request는 바디에 딱히 들어가는 내용은 없고 헤더에 토큰 정보가 들어간다.

응답바디는 다음과 같다

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
											"id": 4,
											"optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종", "quantity": 10,
											"price": 100000
									}, 
									{

											"id": 5,
											"optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종", "quantity": 10,
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

따라서

OrderDTO를 다음과 같이 만들고

```java
package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class OrderDTO {
    private int id;
    private List<ProductDTO> products;
    private int totalPrice;
    @Builder
    public OrderDTO(int id, List<ProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
```

OrderItemDTO를 다음과 같이 만들고

```java
package com.example.kakaoshop.order.response;

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

ProductDTO를 다음과 같이 만들고

```java
package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
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

RestController를 다음과 같이 작성해준다

```java
@PostMapping("/orders/save")
    public ResponseEntity<?> orderSave() {

        // 오더 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        //오더 아이템 리스트에 담기
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
        
        // 상품 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();
        
        // 상품 리스트에 담기
        ProductDTO productDTO1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션의 주방용품 특가전")
                .items(orderItemDTOList)
                .build();

        productDTOList.add(productDTO1);
        
        // 주문 만들기
        OrderDTO orderDTO1 = OrderDTO.builder()
                .id(2)
                .products(productDTOList)
                .totalPrice(20900)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderDTO1));
    }
```

## 주문 결과 확인

Method : Get
Local URL : http://localhost:8080/orders/1

마찬가지로 Request 헤더에는 토큰정보가 들어가고, Path Variable을 이용하여 id가 1인 주문에 접근한다.

응답 Body는 다음과같다.

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
												 "id": 4,
												 "optionName": "01. 슬라이딩 지퍼백 크리스마스에디션 4종", "quantity": 10,
												 "price": 100000
                    },
										{
												 "id": 5,
											 	 "optionName": "02. 슬라이딩 지퍼백 플라워에디션 5종", "quantity": 10,
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

따라서 RestController는 다음과 같이 작성해주면 된다.

```java
@GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        // 오더 아이템 리스트 만들기
        List<OrderItemDTO> orderItemDTOList2 = new ArrayList<>();

        //오더 아이템 리스트에 담기
        OrderItemDTO orderItemDTO1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();
        orderItemDTOList2.add(orderItemDTO1);

        OrderItemDTO orderItemDTO2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();
        orderItemDTOList2.add(orderItemDTO2);

        // 상품 리스트 만들기
        List<ProductDTO> productDTOList = new ArrayList<>();

        // 상품 리스트에 담기
        ProductDTO productDTO1 = ProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션의 주방용품 특가전")
                .items(orderItemDTOList2)
                .build();

        productDTOList.add(productDTO1);

        // 주문 만들기
        OrderDTO orderDTO1 = OrderDTO.builder()
                .id(2)
                .products(productDTOList)
                .totalPrice(20900)
                .build();
        return ResponseEntity.ok(ApiUtils.success(orderDTO1));
    }
```