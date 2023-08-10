# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

</br>

# 5주차
카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제</br>

</br>

## **과제명**
```
1. 코드 리팩토링
```

## **과제 설명**
```
1. 전체 코드를 리팩토링하시오.
```

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- AOP가 적용되었는가?
>- GlobalExceptionHandler가 적용되었는가?
>- 장바구니 담기 시 모든 예외가 처리되었는가?
>- 장바구니 수정 시 모든 예외가 처리되었는가?
>- 결제하기와 주문결과 확인 코드가 구현되었는가?

</br>

## **1. 전체 코드를 리팩토링하시오.**

```
(기능 8) 장바구니 담기
- 로그인이 필요하며 개별 상품 페이지에서 구입하려는 옵션을 선택한 후 장바구니 버튼을 누르면, 해당 API를 호출한다.
- Method: POST
- URL: http://localhost:8080/carts/add
```
<details>
<summary>Code</summary>
  
  - CartJPARepository.java
    ```java
    public interface CartJPARepository extends JpaRepository<Cart, Integer> {
        @Query("select c from Cart c where c.user.id = :userId and c.option.id = :optionId")
        Optional<Cart> findByUserIdAndOptionId(@Param("userId") int userId, @Param("optionId") int optionId);
    }
    ```
      
  - CartRequest.java
    ```java
    public class CartRequest {
        @Getter @Setter @ToString
        public static class SaveDTO {
            @NotNull
            private int optionId;
            @NotNull
            private int quantity;
        }
    }
    ```
      
  - CartRestController.java
    ```java
    @RequiredArgsConstructor
    @RestController
    public class CartRestController {
        private final CartService cartListService;
    
        // (기능 8) 장바구니 담기
        @PostMapping("/carts/add")
        public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
            cartListService.addCartList(requestDTOs, userDetails.getUser());
            ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
            return ResponseEntity.ok(apiResult);
        }
    }
    ```
      
  - CartService.java
    ```java
    @Transactional(readOnly = true)
    @RequiredArgsConstructor
    @Service
    public class CartService {
        private final CartJPARepository cartJPARepository;
        private final OptionJPARepository optionJPARepository;
    
        @Transactional
        public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
            HashSet<Integer> cartSet = new HashSet<>();
            for (CartRequest.SaveDTO requestDTO : requestDTOs) {
                int optionId = requestDTO.getOptionId();
                if (cartSet.contains(optionId)) {
                    throw new Exception400("중복되는 옵션이 존재합니다 : " + optionId);
                } else {
                    cartSet.add(optionId);
                }
            }
            for (CartRequest.SaveDTO requestDTO : requestDTOs) {
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();
                Optional<Cart> cartOP = cartJPARepository.findByUserIdAndOptionId(sessionUser.getId(), optionId);
                if (cartOP.isPresent()) {
                    Cart cart = cartOP.get();
                    int updateQuantity = quantity + cart.getQuantity();
                    int updatePrice = cart.getOption().getPrice() * updateQuantity;
                    cart.update(updateQuantity, updatePrice);
                } else {
                    Option optionPS = optionJPARepository.findById(optionId)
                            .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                    int price = optionPS.getPrice() * quantity;
                    Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                    cartJPARepository.save(cart);
                }
            }
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
  
  - CartJPARepository.java
    ```java
    public interface CartJPARepository extends JpaRepository<Cart, Integer> {
        List<Cart> findAllByUserId(@Param("userId") int userId);
    }
    ```
      
  - CartRequest.java
    ```java
    public class CartRequest {
        @Getter @Setter @ToString
        public static class UpdateDTO {
            @NotNull
            private int cartId;
            @NotNull
            private int quantity;
        }
    }
    ```
      
  - CartResponse.java
    ```java
    public class CartResponse {
        @Getter @Setter
        public static class UpdateDTO {
            private List<CartDTO> carts;
            private int totalPrice;
    
            public UpdateDTO(List<Cart> cartList) {
                this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
                this.totalPrice = cartList.stream().mapToInt(cart -> cart.getPrice()).sum();
            }
    
            @Getter @Setter
            public class CartDTO {
                private int cartId;
                private int optionId;
                private String optionName;
                private int quantity;
                private int price;
    
                public CartDTO(Cart cart) {
                    this.cartId = cart.getId();
                    this.optionId = cart.getOption().getId();
                    this.optionName = cart.getOption().getOptionName();
                    this.quantity = cart.getQuantity();
                    this.price = cart.getPrice();
                }
            }
        }
    }
    ```
      
  - CartRestController.java
    ```java
    @RequiredArgsConstructor
    @RestController
    public class CartRestController {
        private final CartService cartListService;
    
        // (기능 11) 주문
        @PostMapping("/carts/update")
        public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
            CartResponse.UpdateDTO responseDTO = cartListService.updateCartList(requestDTOs,userDetails.getUser());
            ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
            return ResponseEntity.ok(apiResult);
        }
    }
    ```
      
  - CartService.java
    ```java
    @Transactional(readOnly = true)
    @RequiredArgsConstructor
    @Service
    public class CartService {
        private final CartJPARepository cartJPARepository;
    
        @Transactional
        public CartResponse.UpdateDTO updateCartList(List<CartRequest.UpdateDTO> requestDTOs, User sessionUser) {
            List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
            if (cartList.isEmpty()) {
                throw new Exception404("장바구니가 비어있습니다");
            }
    
            HashSet<Integer> cartSet = new HashSet<>();
            for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
                int cartId = requestDTO.getCartId();
                if (cartSet.contains(cartId)) {
                    throw new Exception400("동일한 장바구니 아이디를 주문할 수 없습니다 : " + cartId);
                } else if (cartList.stream().noneMatch(cart -> cart.getId() == cartId)) {
                    throw new Exception400("장바구니에 없는 상품은 주문할 수 없습니다 : " + cartId);
                } else {
                    cartSet.add(cartId);
                }
            }
    
            for (Cart cart : cartList) {
                for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
                    if (cart.getId() == requestDTO.getCartId()) {
                        cart.update(requestDTO.getQuantity(), cart.getOption().getPrice() * requestDTO.getQuantity());
                    }
                }
            }
    
            return new CartResponse.UpdateDTO(cartList);
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
  
  - CartJPARepository.java
    ```java
    public interface CartJPARepository extends JpaRepository<Cart, Integer> {
        void deleteAllByUserId(@Param("userId") int userId);
    
        @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
        List<Cart> findByUserIdOrderByOptionIdAsc(@Param("userId") int userId);
    }
    ```
      
  - OrderResponse.java
    ```java
    public class OrderResponse {
        @Getter @Setter
        public static class SaveCartListDTO {
            private int id;
            private List<ProductDTO> products;
            private int totalPrice;
    
            public SaveCartListDTO(List<Item> itemList) {
                this.id = itemList.get(0).getOrder().getId();
                this.products = itemList.stream()
                        // 중복되는 상품 걸러내기
                        .map(item -> item.getOption().getProduct()).distinct()
                        .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
                this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
            }
    
            @Getter @Setter
            public class ProductDTO {
                private String productName;
                private List<ItemDTO> items;
    
                public ProductDTO(Product product, List<Item> itemList) {
                    this.productName = itemList.get(0).getOption().getProduct().getProductName();
                    this.items = itemList.stream()
                            .filter(item -> item.getOption().getProduct().getId() == product.getId())
                            .map(ItemDTO::new)
                            .collect(Collectors.toList());
                }
    
                @Getter @Setter
                public class ItemDTO {
                    private int id;
                    private String optionName;
                    private int quantity;
                    private int price;
    
                    public ItemDTO(Item item) {
                        this.id = item.getId();
                        this.optionName = item.getOption().getOptionName();
                        this.quantity = item.getQuantity();
                        this.price = item.getPrice();
                    }
                }
            }
        }
    }
    ```
      
  - OrderRestController.java
    ```java
    @RequiredArgsConstructor
    @RestController
    public class OrderRestController {
        private final OrderService orderService;
    
        // (기능 12) 결제
        @PostMapping("/orders/save")
        public ResponseEntity<?> saveCartList(@AuthenticationPrincipal CustomUserDetails userDetails) {
            OrderResponse.SaveCartListDTO responseDTO = orderService.saveCartList(userDetails.getUser());
            ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
            return ResponseEntity.ok(apiResult);
        }
    }
    ```
      
  - OrderService.java
    ```java
    @Transactional(readOnly = true)
    @RequiredArgsConstructor
    @Service
    public class OrderService {
        private final CartJPARepository cartRepository;
        private final OrderJPARepository orderRepository;
        private final ItemJPARepository itemRepository;
    
        public OrderResponse.SaveCartListDTO saveCartList(User sessionUser) {
            List<Cart> cartListPS = cartRepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId());
            if (cartListPS.isEmpty()) {
                throw new Exception400("장바구니가 비어있습니다");
            }
            Order order = Order.builder().user(sessionUser).build();
            orderRepository.save(order);
    
            List<Item> itemListPS = new ArrayList<>();
            for (Cart cartPS : cartListPS) {
                Item item = Item.builder().option(cartPS.getOption()).order(order)
                        .quantity(cartPS.getQuantity()).price(cartPS.getPrice()).build();
                itemListPS.add(item);
            }
            itemRepository.saveAll(itemListPS);
            cartRepository.deleteAllByUserId(sessionUser.getId());
    
            return new OrderResponse.SaveCartListDTO(itemListPS);
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
  
  - ItemJPARepository.java
    ```java
    public interface ItemJPARepository extends JpaRepository<Item, Integer> {
        @Query("select i from Item i join fetch i.option o join fetch o.product where i.order.id = :orderId and i.order.user.id = :userId")
        List<Item> findByOrderIdJoinOrder(@Param("orderId") int orderId, @Param("userId") int userId);
    }
    ```
      
  - OrderResponse.java
    ```java
    public class OrderResponse {
        @Getter @Setter
        public static class FindByIdDTO {
            private int id;
            private List<ProductDTO> products;
            private int totalPrice;
    
            public FindByIdDTO(List<Item> itemList) {
                this.id = itemList.get(0).getOrder().getId();
                this.products = itemList.stream()
                        // 중복되는 상품 걸러내기
                        .map(item -> item.getOption().getProduct()).distinct()
                        .map(product -> new ProductDTO(product, itemList)).collect(Collectors.toList());
                this.totalPrice = itemList.stream().mapToInt(item -> item.getOption().getPrice() * item.getQuantity()).sum();
            }
    
            @Getter @Setter
            public class ProductDTO {
                private String productName;
                private List<ItemDTO> items;
    
                public ProductDTO(Product product, List<Item> itemList) {
                    this.productName = itemList.get(0).getOption().getProduct().getProductName();
                    this.items = itemList.stream()
                            .filter(item -> item.getOption().getProduct().getId() == product.getId())
                            .map(ItemDTO::new)
                            .collect(Collectors.toList());
                }
    
                @Getter @Setter
                public class ItemDTO {
                    private int id;
                    private String optionName;
                    private int quantity;
                    private int price;
    
                    public ItemDTO(Item item) {
                        this.id = item.getId();
                        this.optionName = item.getOption().getOptionName();
                        this.quantity = item.getQuantity();
                        this.price = item.getPrice();
                    }
                }
            }
        }
    }
    ```
      
  - OrderRestController.java
    ```java
    @RequiredArgsConstructor
    @RestController
    public class OrderRestController {
        private final OrderService orderService;
    
        // (기능 13) 주문 결과 확인
        @GetMapping("/orders/{id}")
        public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
            OrderResponse.FindByIdDTO responseDTO = orderService.findById(id, userDetails.getUser());
            ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
            return ResponseEntity.ok(apiResult);
        }
    }
    ```
      
  - OrderService.java
    ```java
    @Transactional(readOnly = true)
    @RequiredArgsConstructor
    @Service
    public class OrderService {
        private final ItemJPARepository itemRepository;
    
        public OrderResponse.FindByIdDTO findById(int id, User sessionUser) {
            List<Item> itemListPS = itemRepository.findByOrderIdJoinOrder(id, sessionUser.getId());
            if (itemListPS.isEmpty()) {
                throw new Exception404("해당 주문을 찾을 수 없습니다 : " + id);
            }
            return new OrderResponse.FindByIdDTO(itemListPS);
        }
    }
    ```
        
</details>
