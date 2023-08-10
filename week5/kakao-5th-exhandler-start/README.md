# 5주차 진행사항

## 상품 예외

```java
public enum ProductErrorCode implements ErrorCode {
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "해당하는 상품이 없습니다.");
		
  	// 생략
}
```

```java
public class NotFoundProductException extends BusinessException {
    public  NotFoundProductException() {
        super(ProductErrorCode.NOT_FOUND_PRODUCT);
    }
}
```



### 존재하지 않는 상품 번호로 조회를 요청했을 때 예외

```java
    public ProductReponse.ProductFindByIdResponse getPostByPostId(Long id) {
      	// 예외 처리 부분
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(NotFoundProductException::new);

        Product product = ProductConverter.from(productEntity);

        List<ProductOption> productOptions = productOptionRepository.findByProductEntity(productEntity)
                .stream()
                .map(ProductOptionConverter::from)
                .collect(Collectors.toList());

        return ProductResponseConverter.from(product, productOptions);
    }

```



## 장바구니 예외

```java
public enum CartErrorCode implements ErrorCode {
    NOT_EXIST_CART(HttpStatus.NOT_FOUND, "존재하지 않는 장바구니 번호입니다."),
    EMPTY_CART(HttpStatus.BAD_REQUEST, "장바구니에 아무런 상품이 존재하지 않습니다."),
    NOT_EXISTS_OPTION_IN_CART(HttpStatus.NOT_FOUND, "장바구니에 해당 옵션이 존재하지 않습니다"),
    DUPLICATED_REQUEST(HttpStatus.BAD_REQUEST, "요청이 중복된 옵션이 존재합니다.")
    ;
		// 생략
}
```

```java
public class EmptyCartException extends BusinessException {
    public EmptyCartException() {
        super(CartErrorCode.EMPTY_CART);
    }
}
```

```java
public class NotExistCartException extends BusinessException {
    public NotExistCartException() {
        super(CartErrorCode.NOT_EXIST_CART);
    }
}
```

```java
public class NotExistOptionException extends BusinessException {
    public NotExistOptionException() {
        super(CartErrorCode.NOT_EXISTS_OPTION_IN_CART);
    }
}

```

```java
public class DuplicatedCartRequestException extends BusinessException {
    public DuplicatedCartRequestException() {
        super(CartErrorCode.DUPLICATED_REQUEST);
    }
}	
```



### 장바구니에 담기 한 요청에 중복된 옵션이 들어가있을 경우 예외 처리

Cart에 저장할 수 있는지 검증하는 역할을 가지는 객체로 CartSaveValidator를 두었다.

또한 repository를 이용하여 검증하는 부분은 해당 객체의 책임이 아니라고 판단했다.

단지, 해당 객체는 외부에 의존하지 않고 전달받은 파라미터를 통해서 검증을 수행한다.

```java
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CartSaveValidator {

    public static void validate(final List<CartSaveReqeust> requests){
        isDuplicated(requests);
    }
    private static void isDuplicated(List<CartSaveReqeust>requests){
        Set<Long> notDuplicated = requests.stream()
                .map(CartSaveReqeust::getOptionId)
                .collect(Collectors.toSet());

        if(notDuplicated.size()!= requests.size()){
            throw new DuplicatedCartRequestException();
        }
    }
}
```



### 장바구니 담기 요청을 할 때 존재하지 않는 옵션이 들어가있을 경우 예외 처리

외부에 의존해야 예외 처리를 할 수 있는 경우 SaveCartUsecase 에서 진행했다.



사실 이 부분이 가장 애매했다.

CartSaveValidator 객체명처럼 장바구니 저장 관련은 해당 객체에서 해야할 것 같았기 때문이다.

하지만, CartSaveValidator에서 ```장바구니 담기 요청을 할 때 존재하지 않는 옵션이 들어가있을 경우 예외 처리``` 까지 진행을 한다면

CartSaveValidator에서 ProductOptionEntity를 전달받아야 했고, validate는 검증만 하는 것인데 Entity를 반환한다는 게 맞지 않는 책임같아서 CartSaveUseCase에서 진행했다.

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SaveCartUsecase {
    private final CartRepository cartRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CartSaveValidator cartSaveValidator;

    @Transactional
    public void execute(User user, List<CartSaveReqeust> cartSaveRequests) {
      	// 장바구니에 담기 한 요청에 중복된 옵션이 들어가있을 경우 예외 처리
        cartSaveValidator.validateCreateConstraint(cartSaveRequests);
				
        cartSaveRequests.forEach(request -> updateOrCreateCart(request,user));
    }

    private void updateOrCreateCart(CartSaveReqeust request, User user) {
        Optional<CartEntity> cartEntity = saveByExisting(request);
        if (cartEntity.isEmpty()) {
            saveCart(request, user);
        }
        updateCart(request, cartEntity);
    }

    private Optional<CartEntity> saveByExisting(CartSaveReqeust request) {
        ProductOptionEntity productOptionEntity = getProductOptionById(request.getOptionId());

        return cartRepository.findByProductOptionEntity(productOptionEntity);
    }

    private void saveCart(CartSaveReqeust request, User user) {
        CartEntity newCartEntity = CartConverter.to(request.getQuantity(), getProductOptionById(request.getOptionId()), user);
        cartRepository.save(newCartEntity);
    }

    private void updateCart(CartSaveReqeust request, Optional<CartEntity> cartEntity) {
        cartEntity.ifPresent(entity -> entity.addQuantity(request.getQuantity()));
    }

  	// 장바구니 담기 요청을 할 때 존재하지 않는 옵션이 들어가있을 경우 예외 처리
    private ProductOptionEntity getProductOptionById(Long optionId) {
        return productOptionRepository.findById(optionId)
                .orElseThrow(NotExistOptionException::new);
    }
}
```



## 장바구니 수정 예외

### 장바구니에 수정 한 요청에 중복된 옵션이 들어가있을 경우 예외 처리(장바구니 생성 예외와 동일)

CartSaveValidator과 굉장히 유사해서 추상화를 해보고 싶었지만 전달받는 파라미터가 다른 상황에서 어떻게 추상화를 할 수 있을지 명확한 방법이 떠오르지 않았다.

```java
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class CartUpdateValidator {
    public static void validate(final List<CartUpdateRequest>requests) {
        validateDuplicated(requests);
    }

    private static void validateDuplicated(List<CartUpdateRequest> requests) {
        Set<Long> notDuplicated = requests.stream()
                .map(CartUpdateRequest::getCartId)
                .collect(Collectors.toSet());

        if(notDuplicated.size()!= requests.size()){
            throw new DuplicatedCartRequestException();
        }
    }
}

```



### 장바구니 수정을 위해 전달받은 장바구니 Id가 존재하지 않을 경우 예외 처리

마찬가지로 이 부분도 외부(repository)에 의존해야 하는 점, 또한 예외 처리와 동시에 어떠한 값을 전달해야 하는 점 때문에 CartUpdateValidator가 아닌 UpdateCartUseCase의 책임으로 주었다.



```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UpdateCartUseCase {
    private final CartRepository cartRepository;

    @Transactional
    public CartUpdateResponse execute(User user, List<CartUpdateRequest> requests) {
      	// 장바구니에 수정 한 요청에 중복된 옵션이 들어가있을 경우 예외 처리
        CartUpdateValidator.validate(requests);

        List<Cart> carts = requests.stream()
                .map(request -> updateCart(user, request))
                .map(CartConverter::from)
                .collect(Collectors.toList());

        int totalPrice = getTotalPrice(carts);

        return CartUpdateResponseConverter.from(carts, totalPrice);
    }

    private int getTotalPrice(List<Cart> carts) {
        Cashier cashier = Objects.requireNonNull(CashierConverter.from(carts));
        return cashier.calculateTotalPrice();
    }
	
  	// 장바구니 수정을 위해 전달받은 장바구니 Id가 존재하지 않을 경우 예외 처리
    private CartEntity updateCart(User user, CartUpdateRequest request){
        CartEntity entity = cartRepository.findByCartIdAndUser(request.getCartId(), user)
                .orElseThrow(NotExistCartException::new);

        entity.addQuantity(request.getQuantity());
        return cartRepository.saveAndFlush(entity);
    }
}

```

