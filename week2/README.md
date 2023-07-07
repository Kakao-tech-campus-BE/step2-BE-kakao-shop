# 2주차 과제
**요구사항**
1. 전체 API 주소 설계
	- API 요구사항 반영
	- API 명세를 위해 고민한 것들
2. Mock API Controller 구현
	- Spring Boot 컨트롤러 작성
	- 완성된 소스코드 제출

## Product

### 1. 전체 상품 목록 조회
```ad-note
- Method : Get
- Local URL : http://localhost:8080/products
- Param : page={number}
```

**구현 사항**
- `findAll(@RequestParam(defaultValue = "0") int page)`
	- 전체 상품 목록 조회 API는 Parmeter를 통해 [[페이지네이션]]을 고려하고 있습니다
	- default value를 주어 페이지별 상품 조회도 가능하도록 하였습니다

**Controller**
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

### 2. 개별 상품 상세 조회
```ad-note
- Method : Get
- Local URL : http://localhost:8080/products/{id}
```

해당 API는 개별 상품의 Option 즉 세부 사항을 조회하도록하는 API입니다

**Controller**
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
## User
### 3. 이메일 중복 체크
- Method : Post
- Local URL : http://localhost:8080/check
- 요청
	```json
	{
	  "email":"meta@nate.com"
	}
	```

- 구현 사항
	- 이메일 중복 체크를 위한 `checkDTO`를 생성하였습니다
	- `EmailValidator`를 만들어서, 이메일의 유효성 검증을 하도록 하였습니다

**Request**
```java
@Getter @Setter  
public static class checkDTO {  
	private String email;  
}
```
**Controller**
```java
@GetMapping("/check")
    public ResponseEntity<?> check(@RequestBody UserRequest.checkDTO checkDTO) {
        String email = checkDTO.getEmail();
        // 이메일 형식 검사
        if (!EmailValidator.validateEmail(email)) {
            return ResponseEntity.ok(ApiUtils.error("이메일 형식으로 작성해주세요 : email", HttpStatus.valueOf(400)));
        }
        if (email=="meta@nate.com")
            return ResponseEntity.ok(ApiUtils.success(null));
        else if(email=="ssar@nate.com")
            return ResponseEntity.ok(ApiUtils.error("이미 존재하는 이메일입니다 : "+ email, HttpStatus.valueOf(400)));
        return ResponseEntity.ok(ApiUtils.success(null));
    }
```

**Email Validator**
Email Validator는 정규식으로 이메일의 형식을 검증합니다.

```java
public class EmailValidator {
	private static final String EMAIL_PATTERN =
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String email) {
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
```

### 4. 회원 가입
- Method : Post
- Local URL : http://localhost:8080/join
- 요청
	```json
	{
	  "username":"mata",
	  "email":"meta@nate.com",
	  "password":"meta1234!"
	
	}
	```
- 구현 사항
	- `Password Validator`를 만들어서, 패스워드 유효성 검증을 하였습니다
	- Controller 단에서, 이메일 중복, 패스워드 등의 검증을 합니다

**Password Validator**
```java
public class PasswordValidator {
	private static final String PASSWORD_PATTERN =
			"^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	public static boolean validatePassword(String password) {
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
```

**Controller**
```java
@PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {

		// 이메일 형식 검사
        if (!EmailValidator.validateEmail(joinDTO.getEmail())) {
            return ResponseEntity.ok(ApiUtils.error("이메일 형식으로 작성해주세요 : email", HttpStatus.valueOf(400)));
        }
        if(!PasswordValidator.validatePassword(joinDTO.getPassword())){
            return ResponseEntity.ok(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다 :password", HttpStatus.valueOf(400)));
        }
        if(joinDTO.getPassword().length()>20 || joinDTO.getPassword().length()<8){
            return ResponseEntity.ok(ApiUtils.error("비밀번호는 8자 이상 20자 이내여야 합니다 :password", HttpStatus.valueOf(400)));
        }
        if(joinDTO.getEmail().equals("ssar@nate.com")){
            return ResponseEntity.ok(ApiUtils.error("동일한 이메일입니다 : "+joinDTO.getEmail(), HttpStatus.valueOf(400)));
        }
        User user = User.builder()
                .email(joinDTO.getEmail())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .username(joinDTO.getUsername())
                .roles("ROLE_USER")
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(ApiUtils.success(null) );
    }
```
### 5. 로그인
- Method : Post
- Local URL : http://localhost:8080/login
- 요청
	```json
	{
	  "email":"ssar@nate.com",
	  "password":"meta1234!"
	
	}
	```

**Controller**
```java
@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        if (!EmailValidator.validateEmail(loginDTO.getEmail())) {
            return ResponseEntity.ok(ApiUtils.error("이메일 형식으로 작성해주세요 : email", HttpStatus.valueOf(400)));
        }
        if(!PasswordValidator.validatePassword(loginDTO.getPassword())){
            return ResponseEntity.ok(ApiUtils.error("영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다 :password", HttpStatus.valueOf(400)));
        }

        if(loginDTO.getEmail().equals("ssal1@nate.com")){
            return ResponseEntity.ok(ApiUtils.error("인증 되지 않았습니다", HttpStatus.valueOf(401)));
        }
		if(loginDTO.getPassword().length()>20 || loginDTO.getPassword().length()<8){  
			return ResponseEntity.ok(ApiUtils.error("비밀번호는 8자 이상 20자 이내여야 합니다 :password", HttpStatus.valueOf(400)));  
		}

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = JWTProvider.create(myUserDetails.getUser());

        return ResponseEntity.ok().header(JWTProvider.HEADER, jwt).body("ok");
    }
```
## Carts
### API 수정 사항 : 이렇게 되면 어떨까요?
- 장바구니 조회 : GET - http://localhost:8080/carts/{cartId}
- 장바구니 저장 : POST - http://localhost:8080/carts
- 장바구니 수정 : PUT - http://localhost:8080/carts/{cartId}

- 장바구니 삭제 : DELETE - http://localhost:8080/carts/{cartId}
### 6. 장바구니 담기
- Method : Post
- Local URL : http://localhost:8080/carts/add

- 구현 사항
	- API 명세와 같은 응답을 반환하도록, Controller를 수정하였습니다

**Controller**
```java
@PostMapping("/carts/add")  
	public ResponseEntity<?> add(@RequestBody List<CartRequest.UpdateDTO> addDTO) {  
		return ResponseEntity.ok(ApiUtils.success(null));  
}
```

### 7. 장바구니 조회
- Method : Get
- Local URL : http://localhost:8080/carts
- 구현 사항
	- API 명세와 같은 응답을 반환하도록, Controller를 구현하였습니다

**Controller**
```java
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
### 8. 주문하기 - (장바구니 수정)
- Method : Post
- Local URL : http://localhost:8080/carts/update

- 구현 사항
	- 주문하기를 위한 DTO, Controller를 생성하였습니다

**Request** : Controller로 들어오는 Request 처리 목적으로 생성하였습니다
```java
public class CartRequest {
	@Getter
	@Setter
	public static class UpdateDTO {
		private int cartId;
		private int quantity;
	}
}
```
**DTO**
```java

@Getter 
@Setter
public class OptionDTO {

    private int cartId;
    private int optionId;

    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public OptionDTO(int cartId, int optionId,String optionName ,int quantity, int price) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}

@Getter 
@Setter
//최종적으로 CartsUpdate 데이터 반환 DTO
public class CartsFindAllDTO {
    private List<OptionDTO> carts;
    private int totalPrice;

    @Builder
    public CartsFindAllDTO(List<OptionDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
```

**Controller**
```java
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
## Orders
### 9. 결재하기 - (주문 인서트)
- Method : Post
- Local URL : http://localhost:8080/orders/save

**DTO**
```java
@Getter @Setter
public class ProductDTO {

	private String productName;
	private List<OptionDTO> items;

	@Builder()
	public ProductDTO( String productName, List<OptionDTO> items) {

		this.productName = productName;
		this.items = items;
	}
}
@Getter
@Setter
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
@Getter @Setter
public class ProductDTO {

	private String productName;
	private List<OptionDTO> items;

	@Builder()
	public ProductDTO( String productName, List<OptionDTO> items) {

		this.productName = productName;
		this.items = items;
	}
}
```
**Controller**
```java
	@PostMapping("/orders/save")
	public ResponseEntity<?> save() {
		List<OptionDTO> optionDTOList = new ArrayList<>();
		OptionDTO option1 = OptionDTO.builder()
				.id(4)
				.optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
				.quantity(10)
				.price(100000)
				.build();
		optionDTOList.add(option1);
		OptionDTO option2 = OptionDTO.builder()
				.id(5)
				.optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
				.quantity(10)
				.price(109000)
				.build();
		optionDTOList.add(option2);
		ProductDTO product1 = ProductDTO.builder()
				.productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
				.items(optionDTOList)
				.build();
		OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);
		return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
	}
```
### 10. 주문 조회
- Method : Get
- Local URL : http://localhost:8080/orders/{id}
```java

@RestController
public class OrderRestController {

	@GetMapping("/orders/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		List<OptionDTO> optionDTOList = new ArrayList<>();
		OptionDTO option1 = OptionDTO.builder()
				.id(4)
				.optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
				.quantity(10)
				.price(100000)
				.build();
		OptionDTO option2 = OptionDTO.builder()
				.id(5)
				.optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
				.quantity(10)
				.price(109000)
				.build();
		optionDTOList.add(option1);
		optionDTOList.add(option2);
		ProductDTO product1 = ProductDTO.builder()
				.productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
				.items(optionDTOList)
				.build();
		OrderRespFindAllDTO orderRespFindAllDTO = new OrderRespFindAllDTO(1, product1, 209000);

		return ResponseEntity.ok(ApiUtils.success(orderRespFindAllDTO));
	}

}
```