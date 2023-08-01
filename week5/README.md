## 과제 수행 조건
```
1. 장바구니 담기 -> 예외 처리하기
2. 장바구니 수정(주문하기) -> 예외 처리하기
3. 결제하기 기능 구현(장바구니가 꼭 초기화 되어야 함)
4. 주문결과 확인 기능 구현
```

## 1. 장바구니 담기 : /carts/add
### **해결해야 할 문제**
- [x] 동일한 옵션이 들어오면 예외처리
- [x] [[Dirty Checking]] : cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트
### Controller
```java
@PostMapping("/carts/add")  
public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {  
	cartService.addCartList(requestDTOs, userDetails.getUser());  
	ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);  
	return ResponseEntity.ok(apiResult);  
}
```
### Service
```java
@Transactional
 public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

	  Map<Integer, Integer> optionIdToQuantityMap = consolidateQuantities(requestDTOs);

	  for (Integer optionId : optionIdToQuantityMap.keySet()) {
			Option option = findOption(optionId);
			int quantity = optionIdToQuantityMap.get(optionId);
			int price = option.getPrice() * quantity;

			Cart cart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId()).orElse(null);
			if (cart != null) {
				 cartJPARepository.updateQuantityAndPrice(cart.getId(), cart.getQuantity()+quantity, cart.getPrice()+price);
			} else {
				 Cart newCart = Cart.builder().user(sessionUser).option(option).quantity(quantity).price(price).build();
				 cartJPARepository.save(newCart);
			}
	  }
 }

 private Map<Integer, Integer> consolidateQuantities(List<CartRequest.SaveDTO> requestDTOs) {
	  Map<Integer, Integer> optionIdToQuantityMap = new HashMap<>();
	  for (CartRequest.SaveDTO requestDTO: requestDTOs) {
			int optionId = requestDTO.getOptionId();
			int quantity = requestDTO.getQuantity();
			optionIdToQuantityMap.put(optionId, optionIdToQuantityMap.getOrDefault(optionId, 0) + quantity);
	  }
	  return optionIdToQuantityMap;
 }

 private Option findOption(Integer optionId) {
	  return optionJPARepository.findById(optionId)
				 .orElseThrow(() -> new Exception404("Could not find that option: " + optionId));
 }
```


## 2. 장바구니 수정(주문하기) : /carts/update
### **해결해야 할 문제**
- [x] 유저 장바구니에 아무것도 없으면 예외처리
- [x] cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
- [x] 유저 장바구니에 없는 cartId가 들어오면 예외처리
### Controller
```java
@PostMapping("/carts/update")  
public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {  
  
	CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs,userDetails.getUser());  
	ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);  
	return ResponseEntity.ok(apiResult);  
}
```
### Service
```java
@Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 사용자의 장바구니가 비어있으면 예외를 발생시킵니다.
        if(cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어 있습니다.");
        }

        // 2. requestDTOs에 동일한 장바구니 ID가 두 번 입력되면 예외를 처리합니다. 예를 들어, cartId:1, cartId:1과 같은 경우입니다.
        Set<Integer> cartIdSet = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toSet());
        if(cartIdSet.size() != requestDTOs.size()) {
            throw new Exception400("동일한 장바구니 ID가 중복 되었습니다.");
        }
        // 3. 사용자의 장바구니에 없는 cartId가 입력되면 예외를 처리합니다.
        Set<Integer> userCartIdSet = cartList.stream()
                .map(Cart::getId)
                .collect(Collectors.toSet());
        if(!userCartIdSet.containsAll(cartIdSet)) {
            throw new Exception400("장바구니에 없는 ID가 있습니다.");
        }

        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO: requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    }
```


## 3. 결제하기 기능 구현 : /carts/orders/save
![](https://i.imgur.com/t3d1xv5.png)

### **해결해야 할 문제**
- [x] 장바구니 결제
- [x] 장바구니 초기화
### Controller
```java
@PostMapping("carts/orders/save")  
public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {  
	OrderResponse.SaveDTO responseDTO = OrderService.save(userDetails.getUser());  
	return ResponseEntity.ok(ApiUtils.success(responseDTO));  
}
```
### Service
```java
@Transactional
 public OrderResponse.SaveDTO save(User user) {

	  // cartList가 비었으면, Exception400을 던진다.
	  List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
	  if(cartList.isEmpty()) {
			throw new Exception400("장바구니가 비어있습니다.");
	  }
	  Order order = Order.builder().user(user).build();
	  orderRepository.save(order);

	  List<Item> items = new ArrayList<>();

	  for (Cart cart : cartList) {
			Item item = Item.builder()
					  .order(order)
					  .option(cart.getOption())
					  .quantity(cart.getQuantity())
					  .price(cart.getPrice())
					  .build();
			items.add(item);
	  }

	  itemRepository.saveAll(items);
	  cartJPARepository.deleteAll(cartList);

	  return new OrderResponse.SaveDTO(order, items);
 }
```


## 4. 주문결과 확인 기능 구현 : /carts/orders/{id}

### Controller
```java
@GetMapping("carts/orders/{id}")  
public ResponseEntity<?> findById(@PathVariable int id) {  
	OrderResponse.FindByIdDTO responseDTO = OrderService.findById(id);  
	return ResponseEntity.ok(ApiUtils.success(responseDTO));  
}
```
### Service
```java
@Transactional
 public OrderResponse.FindByIdDTO findById(int id) {

	  if( orderRepository.findById(id).isEmpty()) {
			throw new Exception404("해당 주문이 존재하지 않습니다 : "+id);
	  }
	  // 주문에 해당하는 Item을 가져온다.
	  List<Item> items = itemRepository.findByOrderId(id);

	  // items의 option에서 product를 추출한다
	  return new OrderResponse.FindByIdDTO(id,items);

 }
```

