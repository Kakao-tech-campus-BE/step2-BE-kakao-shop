# step2-BE-kakao-shop
카카오 테크 캠퍼스 2단계 카카오 쇼핑하기 백엔드 클론 프로젝트 레포지토리입니다.

# 1주차

카카오 테크 캠퍼스 2단계 - BE - 1주차 클론 과제
</br>
</br>

## **과제명**
```
1. 요구사항분석/API요청 및 응답 시나리오 분석
2. 요구사항 추가 반영 및 테이블 설계도
```

## **과제 설명**
```
1. 요구사항 시나리오를 보고 부족해 보이는 기능을 하나 이상 체크하여 README에 내용을 작성하시오.
2. 제시된 화면설계를 보고 해당 화면설계와 배포된 기존 서버의 API주소를 매칭하여 README에 내용을 작성하시오. (카카오 화면설계 시나리오가 있음)
3. 배포된 서버에 모든 API를 POSTMAN으로 요청해본 뒤 응답되는 데이터를 확인하고 부족한 데이터가 무엇인지 체크하여 README에 내용을 작성하시오.
4. 테이블 설계를 하여 README에 ER-Diagram을 추가하여 제출하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 부족한 기능에 대한 요구사항을 미리 예상할 수 있는가? (예를 들면 상품등록 api가 기존 요구사항에는 없는데 추후 필요하지는 않을지, 이런 부분들을 생각하였는지) 
>- 요구사항에 맞는 API를 분석하고 사용자 시나리오를 설계하였는가? (예를 들어 배포된 서버와 화면 설계를 제시해줄 예정인데, 특정 버튼을 클릭했을 때 어떤 API가 호출되어야 할지를 아는지)
>- 응답되는 데이터가 프론트앤드 화면에 모두 반영될 수 있는지를 체크하였는가?(예를 들어 배송관련 비용이 있는데, 이런것들이 API에는 없는데 이런 부분을 캐치할 수 있는지)
>- 테이블 설계가 모든 API를 만족할 수 있게 나왔는가? (테이블이 효율적으로 나왔는가 보다는 해당 테이블로 요구사항을 만족할 수 있는지에 대한 여부만)
>- 테이블명이 이해하기 쉽게 만들어졌는가? (상품테이블이 product이면 이해하기 쉽지만, material이라고 하면 이해하기 어렵기 때문)

</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_1주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 2주차

카카오 테크 캠퍼스 2단계 - BE - 2주차 클론 과제
</br>
</br>

## **과제명**
```
1. 전체 API 주소 설계
2. Mock API Controller 구현
```

## **과제 설명**
```
1. API주소를 설계하여 README에 내용을 작성하시오.
2. 가짜 데이터를 설계하여 응답하는 스프링부트 컨트롤러를 작성하고 소스코드를 업로드하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- User 도메인을 제외한 전체 API 주소 설계가 RestAPI 맞게 설계되었는가?  POST와 GET으로만 구현되어 있어도 됨.	
>- 가짜 데이터를 설계하여 Mock API를 잘 구현하였는가? (예를 들어 DB연결없이 컨트롤러만 만들어서 배포된 서버의 응답과 동일한 형태로 데이터가 응답되는지 여부)
>- DTO에 타입은 올바르게 지정되었는가?
>- DTO에 이름은 일관성이 있는가? (예를 들어 어떤 것은 JoinDTO, 어떤 것은 joinDto, 어떤 것은 DtoJoin 이런식으로 되어 있으면 일관성이 없는것이다)
>- DTO를 공유해서 쓰면 안된다 (동일한 데이터가 응답된다 하더라도, 화면은 수시로 변경될 수 있기 때문에 DTO를 공유하고 있으면 배점을 받지 못함)
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_2주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 3주차

카카오 테크 캠퍼스 2단계 - BE - 3주차 클론 과제
</br>
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

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 레포지토리 단위테스트가 구현되었는가?
>- 테스트 메서드끼리 유기적으로 연결되지 않았는가? (테스트는 격리성이 필요하다)
>- Persistene Context를 clear하여서 테스트가 구현되었는가? (더미데이터를 JPA를 이용해서 insert 할 예정인데, 레포지토리 테스트시에 영속화된 데이터 때문에 쿼리를 제대로 보지 못할 수 있기 때문에)
>- 테스트 코드의 쿼리 관련된 메서드가 너무 많은 select를 유발하지 않는지? (적절한 한방쿼리, 효율적인 in query, N+1 문제 등이 해결된 쿼리)
>- BDD 패턴으로 구현되었는가? given, when, then
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_3주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 과제

## 테스트 전 분석 및 고민

### Test LIST

1. Product
2. Option
3. User
4. Cart
5. Order
6. Item

### 고민

1. 어떤 기준으로 Repository Test를 작성할 것인가?

→ 요구사항 기준으로 Service에 들어갈 쿼리 및 Repository 메서드들을 선별해서 작성한다.

→ 컨트롤러와 DTO, 요구사항을 비교해가면서 필요한 데이터를 얻기위해 repository에 어떤 메서드가 필요할지 고민해본다.

1. Order관련 요구사항의 경우 결제하기와 주문결과 확인의 경우 조회쿼리를 어떻게 날려야할까?
    1. fetch join을 사용해 Product-Option-item-Order를 한번에 조회한다. select문이 하나만 날아간다.(한방쿼리)
    2. Order 1회, item1회 select문(lazy)를 사용해 조회하고, Option-Product데이터만 fetch join한다.

→ 일단 두번째 방법을 선택했다. 과제에 `적절한 한방쿼리` 라고 언급되어있기 때문이다. 1번방법은 너무 많은 테이블을 한번에 조회하기 때문에 과하다고 판단했다.

### 요구사항&API SPEC 분석

1. Product 컨트롤러에는 전체상품목록조회와 개별상품상세조회가 있다.
    1. 전체상품목록조회
        1. product전체조회 필요(findAll)
    2. 개별상품상세조회
        1. id를 통한 하나의 Product 조회 필요(findById)
        2. ProductId를 통해 조회해 N개의 Option데이터 필요(option_findByProductId)
2. Option은 컨트롤러가 없다.
3. User 컨트롤러에는 회원가입, 로그인, 이메일중복체크가 있다.
    1. 회원가입
        1. 이메일, 패스워드, 유저이름을 받아 저장필요(save)
    2. 로그인
        1. 이메일, 패스워드를 받아 이메일을 통한 유저조회 필요(findByEmail)
        2. 받아온 유저조회 결과에서 패스워드가 맞는지 판단.
    3. 이메일중복체크
4. Cart컨트롤러에는 장바구니담기, 장바구니보기, 장바구니 업데이트(주문하기), 장바구니 삭제(결재)
    1. 장바구니담기
        1. 옵션id와 수량을 리스트형태로 받아 저장 필요(save)
    2. 장바구니보기
        1. 장바구니 전체 목록을 본다. (findAll | findByUserId)
    3. 주문하기(장바구니 업데이트)
        1. cartId와 수량을 리스트형태로 받아 수량을 업데이트한다.(findByCartId, save)
    4. 결재하기(장바구니 삭제)
        1. 장바구니의 모든 데이터를 삭제한다.(deleteAll | deleteByUserId)
5. Order컨트롤러에는 결제하기와 주문결과 확인이 있다.
    1. 결제하기
        1. 유저Id를 받아서 OrderId, productDTO리스트, totalPrice를 (save(Order테이블), findByUserId(Order테이블), findByOrderId(Item테이블), findByOptionId(Option테이블 +fetch join필요)
            1. ProductDTO는 productId, productName, Item리스트를 넣는다.
                1. itemDTO는 itemId, optionName, quantity, price를 넣는다.
    2. 주문 결과 확인
        1. OrderId를 받아서 OrderId, ProductDTO리스트, totalPrice를 반환한다.
            1. ProductDTO는 productId, productName, ItemDTO리스트를 넣는다.
                1. ItemDTO는 itemId, optionName, quantity, price를 넣는다.
6. Item은 컨트롤러가 없다.

### 테이블

Product(id, productName, description, image, price)

Option(id, product(fk_N:1), optionName, price)

User(id, email, password, username, roles)

Cart(id, user(fk_N:1), option(fk_1:1), quantity, price)

Order(id, user(fk_N:1))

## Test

### Product & User

강사님과 실시간 강의시간에 코드 작성을 했으므로 따로 추가하지 않았습니다.

### Cart

- `CartJPARepositoryTest.java`

```java
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User user = userJPARepository.save(newUser("user"));
        Product product = productJPARepository.save(newProduct("product1", 1, 10000));
        Option option = optionJPARepository.save(newOption(product,"optionName",1000));
        Option option2 = optionJPARepository.save(newOption(product,"optionName2",1001));
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        Cart cart2 = cartJPARepository.save(newCart(user, option2, 5));

        em.clear(); // 1차 캐시에서 걸리기 때문에 쿼리 테스트가 제대로 안되서 clear를 통해 쿼리가 가게끔 한다.
    }

    // 장바구니 조회
    @Test
    public void cart_findByUserId_test(){
        // given
        int userId = 1;

        // when
        List<Cart> carts = cartJPARepository.findByUserId(userId);

        // then
        Assertions.assertThat(cartJPARepository.count()).isEqualTo(2);
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOption().getOptionName()).isEqualTo("optionName");
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(0).getPrice()).isEqualTo(1000*5);
        Assertions.assertThat(carts.get(1).getOption().getOptionName()).isEqualTo("optionName2");
    }

    @Test
    public void cart_deleteByUserId_test(){
        // given
        int userId = 1;

        // when
        cartJPARepository.deleteByUserId(userId);
        em.flush();
        // then
        assertTrue(cartJPARepository.findByUserId(1).isEmpty());
    }

    @Test
    public void cart_findById_test(){
        // given
        int cartId = 1;

        // when
        Cart cart = cartJPARepository.findById(1).get();

        // then
        Assertions.assertThat(cart.getId()).isEqualTo(1);
        Assertions.assertThat(cart.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getQuantity()).isEqualTo(5);
        Assertions.assertThat(cart.getPrice()).isEqualTo(1000*5);
    }
}
```

- `CartJPARepository.interface`

```java
public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    // 장바구니 조회
    List<Cart> findByUserId(int userId);
    // 장바구니 업데이트 - findById

    // 결제하기(장바구니 삭제)
    void deleteByUserId(int userId);
}
```

장바구니 조회, 결제하기를 위해 추상메서드를 추가했습니다.

- 결과
    
    ![결과1](./img/%EA%B2%B0%EA%B3%BC1.png)
    

### Option

- `OptionJPARepositoryTest`

```java
@DataJpaTest
public class OptionJPARepositoryTest extends DummyEntity {

    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        Product product = productJPARepository.save(newProduct("product1", 1, 10000));
        Option option = optionJPARepository.save(newOption(product,"optionName",1000));
        em.clear();
    }

    @Test
    public void option_mfindById_test(){
        // given
        int optionId =1;

        // when
        Optional<Option> option = optionJPARepository.mfindById(1);

        // then
        Assertions.assertThat(option.get().getId()).isEqualTo(1);
        Assertions.assertThat(option.get().getOptionName()).isEqualTo("optionName");
        Assertions.assertThat(option.get().getPrice()).isEqualTo(1000);

        Assertions.assertThat(option.get().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(option.get().getProduct().getProductName()).isEqualTo("product1");
        Assertions.assertThat(option.get().getProduct().getDescription()).isEqualTo("");
        Assertions.assertThat(option.get().getProduct().getImage()).isEqualTo("/images/1.jpg");
        Assertions.assertThat(option.get().getProduct().getPrice()).isEqualTo(10000);

    }

}
```

- `OptionJPARepository`

```java
public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    List<Option> findByProductId(@Param("productId") int productId);
    Optional<Option> findById(int id);

    // findById_select_product_lazy_error_fix_test
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> mFindByProductId(@Param("productId") int productId);

    // 결제하기3 - 옵션아이디를 통해 fetch join을 해서 product까지 가져온다.
    @Query("select o from Option o join fetch o.product where o.id = :optionId")
    Optional<Option> mfindById(@Param("optionId")int optionId);
}
```

결제하기 기능을 위해 fetch join을 사용한 JPQL문으로 작성한 `mfindById()` 추상메서드를 추가 했습니다.

- 결과

![결과2](./img/%EA%B2%B0%EA%B3%BC2.png)

### Item

- `ItemJPARepositoryTest`

```java
@DataJpaTest
public class ItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("user"));
        Order order = orderJPARepository.save(newOrder(user));
        Product product = productJPARepository.save(newProduct("product1", 1, 10000));
        Option option = optionJPARepository.save(newOption(product,"optionName",1000));
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        Item item = itemJPARepository.save(newItem(cart, order));

        em.clear();
    }

    @Test
    public void item_findByOrderId_test(){
        // given
        int orderId = 1;

        // when
        List<Item> items = itemJPARepository.findByOrderId(orderId);

        // then
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getPrice()).isEqualTo(1000*5);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(5);

        Assertions.assertThat(items.get(0).getOption().getId()).isEqualTo(1);

        Assertions.assertThat(items.get(0).getOrder().getId()).isEqualTo(1);
    }

}
```

- `OptionJPARepository`

```java
public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    // 결제하기2 - 주문아이디로 아이템인스턴스를 받아온다.
    List<Item> findByOrderId(int orderId);
}
```

결제하기 기능을위해 `findByOrderId()` 추상 메서드를 추가했습니다.

- 결과

![결과3](./img/%EA%B2%B0%EA%B3%BC3.png)

### Order

- `OrderJPARepository`

```java
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("user"));
        Order order = orderJPARepository.save(newOrder(user));

        em.clear();
    }

    @Test
    public void order_findByUserId_test(){
        // given
        int userId = 1;

        // when
        Order order = orderJPARepository.findByUserId(userId).get();

        // then
        Assertions.assertThat(order.getId()).isEqualTo(1);
        Assertions.assertThat(order.getUser().getId()).isEqualTo(1);
    }

}
```

- `OrderJPARepository`

```java
public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    // 결제하기1 - 유저아이디로 주문아이디를 찾는다.
    Optional<Order> findByUserId(int userId);

}
```

결제하기 기능을 위해 `findByUserId()` 추상메서드를 추가했습니다.

- 결과

![결과4](./img/%EA%B2%B0%EA%B3%BC4.png)

# 4주차

카카오 테크 캠퍼스 2단계 - BE - 4주차 클론 과제
</br>
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

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 컨트롤러 단위테스트가 구현되었는가?
>- Mockito를 이용하여 stub을 구현하였는가?
>- 인증이 필요한 컨트롤러를 테스트할 수 있는가?
>- 200 ok만 체크한 것은 아닌가? (해당 컨트롤러에서 제일 필요한 데이터에 대한 테스트가 구현되었는가?)
>- 모든 요청과 응답이 json으로 처리되어 있는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_4주차 과제 

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 5주차

카카오 테크 캠퍼스 2단계 - BE - 5주차 클론 과제
</br>
</br>

## **과제명**
```
1. 실패 단위 테스트
```

## **과제 설명**
```
1. 컨트롤러 단위테스트를 구현하는데, 실패 테스트 코드를 구현하시오.
2. 어떤 문제가 발생할 수 있을지 모든 시나리오를 생각해본 뒤, 실패에 대한 모든 테스트를 구현하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 실패 단위 테스트가 구현되었는가?
>- 모든 예외에 대한 실패 테스트가 구현되었는가?
>- 예외에 대한 처리를 ControllerAdvice or RestControllerAdvice로 구현하였는가?
>- Validation 라이브러리를 사용하여 유효성 검사가 되었는가?
>- 테스트는 격리되어 있는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_5주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분

# 6주차

카카오 테크 캠퍼스 2단계 - BE - 6주차 클론 과제
</br>
</br>

## **과제명**
```
1. 카카오 클라우드 배포
```

## **과제 설명**
```
1. 통합테스트를 구현하시오.
2. API문서를 구현하시오. (swagger, restdoc, word로 직접 작성, 공책에 적어서 제출 등 모든 방법이 다 가능합니다)
3. 프론트앤드에 입장을 생각해본뒤 어떤 문서를 가장 원할지 생각해본뒤 API문서를 작성하시오.
4. 카카오 클라우드에 배포하시오.
```

</br>

## **과제 상세 : 수강생들이 과제를 진행할 때, 유념해야할 것**
아래 항목은 반드시 포함하여 과제 수행해주세요!
>- 통합테스트가 구현되었는가?
>- API문서가 구현되었는가?
>- 배포가 정상적으로 되었는가?
>- 프로그램이 정상 작동되고 있는가?
>- API 문서에 실패 예시가 작성되었는가?
</br>

## **코드리뷰 관련: PR시, 아래 내용을 포함하여 코멘트 남겨주세요.**
**1. PR 제목과 내용을 아래와 같이 작성 해주세요.**

>- PR 제목 : 부산대BE_라이언_6주차 과제

</br>

**2. PR 내용 :**

>- 코드 작성하면서 어려웠던 점
>- 코드 리뷰 시, 멘토님이 중점적으로 리뷰해줬으면 하는 부분
