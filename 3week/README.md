# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

</br>

# 3주차
카카오 테크 캠퍼스 2단계 - BE - 3주차 클론 과제</br>

</br>

## **과제명**
```
1. 레포지토리 단위테스트
```

## **과제 설명**
```
1. 레포지토리 단위테스트를 구현하여 소스코드를 제출하시오.
2. 쿼리를 테스트하면서 가장 좋은 쿼리를 작성해보시오.
```

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 레포지토리 단위테스트가 구현되었는가?
>- 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
>- Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
>- 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
>- BDD 패턴으로 구현되었는가? (given, when, then)

</br>

## **1. 레포지토리 단위테스트를 구현하여 소스코드를 제출하시오.**

### ProductJPARepositoryTest.java
```java
@Import(ObjectMapper.class)
@DataJpaTest
public class ProductJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));

        em.clear();
    }
}
```
<details>
<summary>Code</summary>
<<<<<<< HEAD
=======

>>>>>>> 18a30fa8866549c4ecb82efbac0c711bda2eb094
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
  public void product_findAll_test() throws JsonProcessingException {
      // given
      int page = 0;
      int size = 9;
  
      // when
      PageRequest pageRequest = PageRequest.of(page, size);
      Page<Product> productPG = productJPARepository.findAll(pageRequest);
      String responseBody = om.writeValueAsString(productPG);
      System.out.println("테스트 : " + responseBody);
  
      // then
      Assertions.assertThat(productPG.getTotalPages()).isEqualTo(2);
      Assertions.assertThat(productPG.getSize()).isEqualTo(9);
      Assertions.assertThat(productPG.getNumber()).isEqualTo(0);
      Assertions.assertThat(productPG.getTotalElements()).isEqualTo(15);
      Assertions.assertThat(productPG.isFirst()).isEqualTo(true);
      Assertions.assertThat(productPG.getContent().get(0).getId()).isEqualTo(1);
      Assertions.assertThat(productPG.getContent().get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
      Assertions.assertThat(productPG.getContent().get(0).getDescription()).isEqualTo("");
      Assertions.assertThat(productPG.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
      Assertions.assertThat(productPG.getContent().get(0).getPrice()).isEqualTo(1000);
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
  public void option_findByProductId_eager_test() throws JsonProcessingException {
      // given
      int id = 1;
  
      // when
      // 충분한 데이터 - product만 0번지에서 빼면 된다.
      // 조인은 하지만, fetch를 하지 않아서, product를 한번 더 select 했다.
      List<Option> optionListPS = optionJPARepository.findByProductId(id); // Eager
  
      System.out.println("json 직렬화 직전========================");
      String responseBody = om.writeValueAsString(optionListPS);
      System.out.println("테스트 : " + responseBody);
  
      // then
  }
  ```
    
</details>

### CartJPARepositoryTest.java
```java
@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User userPS = userJPARepository.save(newUser("ssar"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        cartJPARepository.save(newCart(userPS, optionListPS.get(0), 5));
        cartJPARepository.save(newCart(userPS, optionListPS.get(1), 5));

        em.clear();
    }
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
  @Test
  @DisplayName("(기능 8) 장바구니 담기")
  public void cart_add_test() throws JsonProcessingException {
      // given
      int id = 1;
  
      // when
      List<Cart> cartListPS = cartJPARepository.mFindByUserId(id);
  
      System.out.println("json 직렬화 직전========================");
      String responseBody = om.writeValueAsString(cartListPS);
      System.out.println("테스트 : " + responseBody);
  
      // then
  }
  ```
  
  ```
  (기능 9) 장바구니 조회
  - 로그인이 필요하며 장바구니 보기 페이지에서 해당 API를 호출하여 현재 유저의 장바구니에 담긴 데이터를 받아온다.
  - Method: GET
  - URL: http://localhost:8080/carts
  ```
  
  ```java
  @Test
  @DisplayName("(기능 9) 장바구니 조회")
  public void cart_findAll_test() throws JsonProcessingException {
      // given
  
      // when
      List<Cart> cartListPS = cartJPARepository.mFindAll();
      String responseBody = om.writeValueAsString(cartListPS);
      System.out.println("테스트 : " + responseBody);
  
      // then
      Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(1);
      Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(1);
      Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
      Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(10000);
      Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
      Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(50000);
  }
  ```
  
  ```
  (기능 11) 주문
  - 로그인이 필요하며 장바구니 보기 페이지에서 주문하기 버튼을 누르면, 해당 API를 호출한다.
  - Method: POST
  - URL: http://localhost:8080/carts/update
  ```
  
  ```java
  @Test
  @DisplayName("(기능 11) 주문")
  public void update_cart_test() throws JsonProcessingException {
      // given
      int id = 1;
      int quantity = 10;
  
      // when
      Optional<Cart> cartOP = cartJPARepository.mFindById(id);
      Cart cart = new Cart();
  
      if (cartOP.isPresent()) {
          cart = cartOP.get();
          cart.update(quantity, cart.getOption().getPrice() * quantity);
          em.flush();
  
          String responseBody = om.writeValueAsString(cart); // 직렬화하여 출력
          System.out.println("테스트 : " + responseBody);
      }
  
      // then
      Assertions.assertThat(cart.getId()).isEqualTo(1);
      Assertions.assertThat(cart.getQuantity()).isEqualTo(10);
      Assertions.assertThat(cart.getPrice()).isEqualTo(100000);
  }
  ```
    
</details>

### OrderJPARepositoryTest.java
```java
@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User userPS = userJPARepository.save(newUser("ssar"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartList = Arrays.asList(
                newCart(userPS, optionListPS.get(0), 5),
                newCart(userPS, optionListPS.get(1), 5)
        );
        orderJPARepository.saveAll(Arrays.asList(newOrder(userPS)));
        itemJPARepository.saveAll(
                cartList.stream()
                        .map(cart -> newItem(cart, newOrder(userPS)))
                        .collect(Collectors.toList())
        );

        em.clear();
    }
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
  @Test
  @DisplayName("(기능 12) 결제")
  public void order_save_test() throws JsonProcessingException {
      // given
      int id = 1;
  
      // when
      List<Order> orderListPS = orderJPARepository.mFindByUserId(id);
      String responseBody = om.writeValueAsString(orderListPS);
      System.out.println("테스트 : " + responseBody);
  
      // then
      Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
      Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
      Assertions.assertThat(orderListPS.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
      assertTrue(BCrypt.checkpw("meta1234!", orderListPS.get(0).getUser().getPassword()));
      Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
      Assertions.assertThat(orderListPS.get(0).getUser().getRoles()).isEqualTo("ROLE_USER");
  }
  ```
  
  ```
  (기능 13) 주문 결과 확인
  - 유저가 주문에 대해 성공적으로 결제하고 나면 주문 결과 확인 페이지에서 해당 API를 호출한다.
  - Method: GET
  - URL: http://localhost:8080/orders/{order_id}
  ```
  
  ```java
  @Test
  @DisplayName("(기능 13) 주문 결과 확인")
  public void order_findAll_test() throws JsonProcessingException {
      // given
  
      // when
      List<Order> orderListPS = orderJPARepository.mFindAll();
      String responseBody = om.writeValueAsString(orderListPS);
      System.out.println("테스트 : " + responseBody);
  
      // then
      Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
      Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
      Assertions.assertThat(orderListPS.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
      assertTrue(BCrypt.checkpw("meta1234!", orderListPS.get(0).getUser().getPassword()));
      Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
      Assertions.assertThat(orderListPS.get(0).getUser().getRoles()).isEqualTo("ROLE_USER");
  }
  ```
    
</details>

</br>

## **2. 쿼리를 테스트하면서 가장 좋은 쿼리를 작성해보시오.**

### ProductJPARepository.java
```java
public interface ProductJPARepository extends JpaRepository<Product, Integer> {
}
```

### OptionJPARepository.java
```java
public interface OptionJPARepository extends JpaRepository<Option, Integer> {
    List<Option> findByProductId(@Param("productId") int productId);
}
```

### CartJPARepository.java
```java
public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.user join fetch c.option where c.user.id = :userId")
    List<Cart> mFindByUserId(@Param("userId") int userId);

    @Query("select c from Cart c join fetch c.user join fetch c.option")
    List<Cart> mFindAll();

    @Query("select c from Cart c join fetch c.user join fetch c.option where c.id = :cartId")
    Optional<Cart> mFindById(@Param("cartId") int cartId);
}
```

### OrderJPARepository.java
```java
public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o join fetch o.user where o.user.id = :userId")
    List<Order> mFindByUserId(@Param("userId") int userId);

    @Query("select o from Order o join fetch o.user")
    List<Order> mFindAll();
}
```

### ItemJPARepository.java
```java
public interface ItemJPARepository extends JpaRepository<Item, Integer> {
}
```
