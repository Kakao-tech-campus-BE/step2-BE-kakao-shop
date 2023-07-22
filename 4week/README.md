# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

</br>

# 4주차
카카오 테크 캠퍼스 2단계 - BE - 4주차 클론 과제</br>

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

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 컨트롤러 단위테스트가 구현되었는가?
>- Mockito를 이용하여 stub을 구현하였는가?
>- 인증이 필요한 컨트롤러를 테스트할 수 있는가?
>- 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
>- 모든 요청과 응답이 json으로 처리되어 있는가?

</br>

## **1. 컨트롤러 단위테스트를 작성한뒤 소스코드를 업로드하시오.**

### UserRestControllerTest.java
```java
@Import({
        SecurityConfig.class,
        GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {UserRestController.class})
public class UserRestControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
}
```
<details>
<summary>Code</summary>
    
  ```
  (기능 1) 회원 가입
  - 회원가입 페이지에서 form을 채우고 회원가입 버튼을 누를 때 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/join
  ```
  
  ```java
  @Test
  @DisplayName("(기능 1) 회원 가입")
  public void user_join_test() throws Exception {
      // given
      UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
      requestDTO.setEmail("ssarmango@nate.com");
      requestDTO.setPassword("meta1234!");
      requestDTO.setUsername("ssarmango");
      String requestBody = om.writeValueAsString(requestDTO);
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .post("/join")
                      .content(requestBody)
                      .contentType(MediaType.APPLICATION_JSON)
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
  
  ```
  (기능 2) 로그인
  - 로그인 페이지에서 이메일 비밀번호를 채우고 로그인 버튼을 누를 때 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/login
  ```
  
  ```java
  @Test
  @DisplayName("(기능 2) 로그인")
  public void user_login_test() throws Exception {
      // given
      UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
      loginDTO.setEmail("ssar@nate.com");
      loginDTO.setPassword("meta1234!");
      User user = User.builder().id(1).roles("ROLE_USER").build();
      String requestBody = om.writeValueAsString(loginDTO);
  
      // stub
      String jwt = JWTProvider.create(user);
      BDDMockito.given(userService.login(any())).willReturn(jwt);
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .post("/login")
                      .content(requestBody)
                      .contentType(MediaType.APPLICATION_JSON)
      );
      String responseHeader = result.andReturn().getResponse().getHeader(JWTProvider.HEADER);
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseHeader);
      System.out.println("테스트 : " + responseBody);
  
      // then
      Assertions.assertTrue(jwt.startsWith(JWTProvider.TOKEN_PREFIX));
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
    
</details>

### ProductRestControllerTest.java
```java
@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest {
    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;
}
```
<details>
<summary>Code</summary>
    
  ```
  (기능 4) 전체 상품 목록 조회
  - 해당 페이지에 들어가면 전체 상품 목록 조회 API를 호출하여 page값에 따라 상품의 데이터를 받아온다.
  - Method: GET
  - URL: http://localhost:8080/products
  - Param : page={number}, 디폴트 값 0
  ```
  
  ```java
  @Test
  @DisplayName("(기능 4) 전체 상품 목록 조회")
  public void product_findAll_test() throws Exception {
      // given
  
      // stub
      BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
              new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000),
              new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000),
              new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000)
      ));
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .get("/products")
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].description").value(""));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].image").value("/images/1.jpg"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].price").value(1000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
  
  ```
  (기능 5) 개별 상품 상세 조회
  - 개별 상품 페이지에 들어가면 해당 API를 호출하여 해당 상품 옵션들의 데이터를 받아온다.
  - Method: GET
  - URL: http://localhost:8080/products/{product_id}
  ```
  
  ```java
  @Test
  @DisplayName("(기능 5) 개별 상품 상세 조회")
  public void product_findById_test() throws Exception {
      // given
      int productId = 1;
  
      // stub
      Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
      BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(product));
      BDDMockito.given(fakeStore.getOptionList()).willReturn(Arrays.asList(
              new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
              new Option(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
              new Option(3, product, "고무장갑 베이지 S(소형) 6팩", 9900)
      ));
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .get("/products/" + productId)
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value(""));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.image").value("/images/1.jpg"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(1000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].price").value(10000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
    
</details>

### CartRestControllerTest.java
```java
@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
}
```
<details>
<summary>Code</summary>
    
  ```
  (기능 8) 장바구니 담기
  - 로그인이 필요하며 개별 상품 페이지에서 구입하려는 옵션을 선택한 후 장바구니 버튼을 누르면, 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/carts/add
  ```
  
  ```java
  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("(기능 8) 장바구니 담기")
  public void cart_add_test() throws Exception {
      // given
      List<CartRequest.AddDTO> requestDTOs = new ArrayList<>();
      CartRequest.AddDTO d1 = new CartRequest.AddDTO();
      d1.setOptionId(1);
      d1.setQuantity(5);
      CartRequest.AddDTO d2 = new CartRequest.AddDTO();
      d2.setOptionId(2);
      d2.setQuantity(5);
      requestDTOs.add(d1);
      requestDTOs.add(d2);
      String requestBody = om.writeValueAsString(requestDTOs);
      System.out.println("테스트 : " + requestBody);
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .post("/carts/add")
                      .content(requestBody)
                      .contentType(MediaType.APPLICATION_JSON)
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
  
  ```
  (기능 9) 장바구니 조회
  - 로그인이 필요하며 장바구니 보기 페이지에서 해당 API를 호출하여 현재 유저의 장바구니에 담긴 데이터를 받아온다.
  - Method: GET
  - URL: http://localhost:8080/carts
  ```
  
  ```java
  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("(기능 9) 장바구니 조회")
  public void cart_findAll_test() throws Exception {
      // given
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .get("/carts")
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.price").value(10000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(5));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(50000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
  
  ```
  (기능 11) 주문
  - 로그인이 필요하며 장바구니 보기 페이지에서 주문하기 버튼을 누르면, 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/carts/update
  ```
  
  ```java
  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("(기능 11) 주문")
  public void update_test() throws Exception {
      // given
      List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
      CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
      d1.setCartId(1);
      d1.setQuantity(10);
      CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
      d2.setCartId(2);
      d2.setQuantity(10);
      requestDTOs.add(d1);
      requestDTOs.add(d2);
      String requestBody = om.writeValueAsString(requestDTOs);
      System.out.println("테스트 : " + requestBody);
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .post("/carts/update")
                      .content(requestBody)
                      .contentType(MediaType.APPLICATION_JSON)
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(100000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(209000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
    
</details>

### OrderRestControllerTest.java
```java
@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {OrderRestController.class})
public class OrderRestControllerTest {
    @Autowired
    private MockMvc mvc;
}
```
<details>
<summary>Code</summary>
    
  ```
  (기능 12) 결제
  - 로그인이 필요하며 상품 주문 및 결제 페이지에서 결제하기 버튼을 누르면, 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/orders/save
  ```
  
  ```java
  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("(기능 12) 결제")
  public void order_save_test() throws Exception {
      // given
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .post("/orders/save")
                      .contentType(MediaType.APPLICATION_JSON)
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
  
  ```
  (기능 13) 주문 결과 확인
  - 유저가 주문에 대해 성공적으로 결제하고 나면 주문 결과 확인 페이지에서 해당 API를 호출한다.
  - Method: GET
  - URL: http://localhost:8080/orders/{order_id}
  ```
  
  ```java
  @WithMockUser(username = "ssar@nate.com", roles = "USER")
  @Test
  @DisplayName("(기능 13) 주문 결과 확인")
  public void order_findById_test() throws Exception {
      // given
      int orderId = 1;
  
      // when
      ResultActions result = mvc.perform(
              MockMvcRequestBuilders
                      .get("/orders/" + orderId)
      );
      String responseBody = result.andReturn().getResponse().getContentAsString();
      System.out.println("테스트 : " + responseBody);
  
      // then
      result.andExpect(MockMvcResultMatchers.status().isOk());
      result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].id").value(1));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].quantity").value(5));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].items[0].price").value(50000));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPrice").value(104500));
      result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue()));
  }
  ```
    
</details>

</br>

## **2. stub을 구현하시오.**

### user_login_test()
```java
String jwt = JWTProvider.create(user);
BDDMockito.given(userService.login(any())).willReturn(jwt);
```

### product_findAll_test()
```java
BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
        new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000),
        new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000),
        new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000)
));
```

### product_findById_test()
```java
Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000);
BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(product));
BDDMockito.given(fakeStore.getOptionList()).willReturn(Arrays.asList(
        new Option(1, product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
        new Option(2, product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
        new Option(3, product, "고무장갑 베이지 S(소형) 6팩", 9900)
));
```
