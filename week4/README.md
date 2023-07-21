## 핵심 지표

- 컨트롤러 단위테스트가 구현되었는가?
- [[Mockito]]를 이용하여 [[stub]]을 구현하였는가?
- 인증이 필요한 컨트롤러를 테스트할 수 있는가?
- 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
- 모든 요청과 응답이 json으로 처리되어 있는가?

## Cart

### 장바구니 담기: Post `/carts/add`

```java
@PostMapping("/carts/add")
public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
    // Code block
}
```

**Test Code**

```java
@DisplayName("장바구니 추가")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void add_test() throws Exception{
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = createSaveDTO(1, 10);
        CartRequest.SaveDTO d2 = createSaveDTO(2, 10);

        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
		  result.andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());
    }
```

### 장바구니 조회: Get `/carts`

```java
@GetMapping("/carts")
public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
    // Code block
}
```

**Test Code**

```java
@DisplayName("장바구니 조회")
    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        // given
        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);
        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    }
```

### 장바구니 수정: Post `/carts/update`

- 인증

```java
@PostMapping("/carts/update")
public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
    // Code block
}
```

```java
@WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = createUpdateDTO(1, 10);
        CartRequest.UpdateDTO d2 = createUpdateDTO(2, 10);

        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
    }
```

## Order

```
- When을 사용해서 테스트를 구현해 보았습니다
```

### **@BeforeEach : 사전 데이터 준비**

```java
@Autowired
    private ObjectMapper om;

    @MockBean
    private FakeStore fakeStore;

    int id;
    private  Order order;
    private List<Item> itemList;
    private List<Option> optionList;

    private Product product;
    @BeforeEach
    public void init(){
        id = 1;
        User user = createUser("ssar");
        product = createProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        order = createOrder(user, id);
        Option option1 = createOption(product, id, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = createOption(product, id, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);
        optionList = Arrays.asList(option1, option2);
        itemList = Arrays.asList(
                createItem(1, order, option1, 5),
                createItem(1, order, option2, 5)
        );

        when(fakeStore.getOrderList()).thenReturn(Arrays.asList(order));
        when(fakeStore.getItemList()).thenReturn(itemList);
        when(fakeStore.getProductList()).thenReturn(Arrays.asList(product));
        when(fakeStore.getOptionList()).thenReturn(optionList);
    }

	 private User createUser(String username) {
        return User.builder().id(id).username(username).build();
    }

    private Product createProduct( String productName) {
        return Product.builder().id(id).productName("Product name").build();
    }
    private Order createOrder(User user, int id) {
        return Order.builder().user(user).id(id).build();
    }

    private Option createOption(Product product, int id, String optionName, int price) {
        return Option.builder().product(product).id(id).optionName(optionName).price(price).build();
    }

    private Item createItem(int id, Order order, Option option, int quantity) {
        return Item.builder().id(id).order(order).option(option).quantity(quantity).build();
    }
```

### 결재: Post `/orders/save`

```java
@PostMapping("/orders/save")
public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
    // Code block
}
```

**Test Code**

```java
@DisplayName("주문하기")
    @WithMockUser(username = "ssar", roles = "USER")
    @Test
    public void save_test() throws Exception{

        OrderResponse.FindByIdDTO requestDTO = new OrderResponse.FindByIdDTO(order, itemList);

        String requestBody = om.writeValueAsString(requestDTO);
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/orders/save")
                        .content(requestBody)
                        .contentType("application/json")
        );
        System.out.println("result = " + result.andReturn().getResponse().getContentAsString());

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
    }
```

### 주문 조회: Get `/orders/{id}`

```java
@GetMapping("/orders/{id}")
public ResponseEntity<?> findById(@PathVariable int id) {
    // Code block
}
```

**Test Code**

```java
@DisplayName("주문 조회하기")
    @WithMockUser(username = "ssar", roles = "USER")
    @Test
    public void findById_test() throws Exception{

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/orders/{id}",id)
                        .contentType("application/json")
        );
        System.out.println("result = " + result.andReturn().getResponse().getContentAsString());

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
    }
```

## Product

### **@BeforeEach : 데이터 준비**

```java
	@MockBean
    private FakeStore fakeStore;

    @Autowired
    private ObjectMapper om;
    private List<Product> productList;
    private List<Option> optionList;

    @BeforeEach
    public void init(){
        productList = new ArrayList<>();

        Product product = Product.builder().id(1)
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .description("")
                .image("/images/" + 1 + ".jpg")
                .price(10000)
                .build();
        optionList = Arrays.asList(
                createOption(product, 1, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                createOption(product, 2, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                createOption(product, 3, "고무장갑 베이지 S(소형) 6팩", 9900),
                createOption(product, 4, "뽑아쓰는 키친타올 130매 12팩", 16900),
                createOption(product, 5, "2겹 식빵수세미 6매", 8900)
        );

        productList.add(product);
    }
	 private Option createOption(Product product, int id, String optionName, int price) {
        return Option.builder()
                .product(product)
                .id(id)
                .optionName(optionName)
                .price(price)
                .build();
    }
```

### 전체 상품 목록 조회: Get `/products`

```java
@GetMapping("/products")
public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
    // Code block
}
```

**Test Code**

```java
	@Test
    public void findAll_test() throws Exception {

        // give
        when(fakeStore.getProductList()).thenReturn(productList);

        List < ProductResponse.FindAllDTO > responseDTOs = productList.stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());

        String responseBody = om.writeValueAsString(ApiUtils.success(responseDTOs));
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                // 테스트 코드
                ).andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(responseBody)
                );
    }
```

### 개별 상품 상세 조회: Get `/products/{id}`

```java
@GetMapping("/products/{id}")
public ResponseEntity<?> findById(@PathVariable int id) {
    // Code block
}
```

**Test Code**

```java
	@Test
    public void findById_test() throws Exception {
        //given
        int id= 1;
        // FakeStore의 getProductList()와 getOptionList() 메서드의 반환값 설정
        when(fakeStore.getProductList()).thenReturn(productList);
        when(fakeStore.getOptionList()).thenReturn(optionList);

        Product product = productList.get(0);
        List<Option> filteredOptionList = optionList.stream().filter(option -> product.getId()== option.getProduct().getId()).collect(Collectors.toList());

        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, filteredOptionList);
        //when

        String responseBody = om.writeValueAsString(ApiUtils.success(responseDTO));
        System.out.println(responseBody);
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                //then
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.content().json(responseBody)
        );
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }
```
