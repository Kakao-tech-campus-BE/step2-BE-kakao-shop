# 🐥 카카오테크캠퍼스 - 2단계 3주차 과제 구현

## 측정 지표

- 레퍼지터리 단위 테스트 구현
- 테스트 메서드의 격리성이 보장 되었는가
- 테스트 코드 쿼리 관련 메서드가 너무 많은 Select를 유발하지 않는가

### 과제 목적

- 핵심은 내가 JPA 테스트를 통해 원하는 데이터를 얻어낼 수 있는가!!
- 그 데이터를 얻기 위해 너무 많은 select가 유발되지 않았는가?
- 테스트를 통해 JPA를 좀 더 잘 이해하는 것이다.

# 과제 상세 코드

## Product

### 전체 상품 목록 조회

```java
@Test
 public void product_findAll_test() throws JsonProcessingException {
	  // given
	  int page = 0;
	  int size = 9;

	  // when
	  PageRequest pageRequest = PageRequest.of(page, size);
	  Page<Product> productPG = productJPARepository.findAll(pageRequest);
	  String responseBody = om.writeValueAsString(productPG);

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

### 개별 상품 상세 조회

```java
	@Test
    public void option_mFindByProductId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Option> optionListPS = optionJPARepository.mFindByProductId(id); // Lazy

        String responseBody = om.writeValueAsString(optionListPS);

        // then
        Assertions.assertThat(optionListPS.size()).isEqualTo(5);
        Assertions.assertThat(optionListPS.get(0).getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(optionListPS.get(0).getProduct().getDescription()).isEqualTo("");
        Assertions.assertThat(optionListPS.get(0).getProduct().getImage()).isEqualTo("/images/1.jpg");
        Assertions.assertThat(optionListPS.get(0).getProduct().getPrice()).isEqualTo(1000);
        Assertions.assertThat(optionListPS.get(0).getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(optionListPS.get(0).getPrice()).isEqualTo(10000);
    }
```

## Cart

### 장바구니 담기

```java
@Test
 public void save(){
	  //given
	  int quantity = 5;
	  String username = "ssal";

	  User user = userJPARepository.findByUsername(username);

	  List<Integer> optionIds = Arrays.asList(1, 2);

	  List<Cart> carts = new ArrayList<>();

	  //when
	  List<Option> options = optionJPARepository.findAllById(optionIds);

	  if(options.size() != optionIds.size()) {
			throw new RuntimeException("해당 옵션을 찾을 수 없습니다.");
	  }

	  for (Option option : options) {
			Cart newCart = newCart(user, option, quantity);
			carts.add(newCart);
	  }

	  List<Cart> savedCarts = cartJPARepository.saveAll(carts);

	  //then
	  for (int i = 0; i < savedCarts.size(); i++) {
			Cart savedCart = savedCarts.get(i);
			Assertions.assertThat(savedCart.getUser().getUsername()).isEqualTo(username);
			Assertions.assertThat(savedCart.getOption().getId()).isEqualTo(optionIds.get(i));
			Assertions.assertThat(savedCart.getQuantity()).isEqualTo(quantity);
	  };
 }
```

### 장바구니 조회

```java
@Test
 public void findAllByUserId() throws JsonProcessingException {
	  //given
	  int id = 1;
	  //when
	  List<Cart> cartList = cartJPARepository.findByUserId(id);
	  List<CartDTO> cartDtoList = cartList.stream()
				 .map(CartDTO::new)
				 .collect(Collectors.toList());

	  String responseBody = om.writeValueAsString(cartDtoList);
	  System.out.println("cartDtoList : "+ responseBody);

	  //then
	  Assertions.assertThat(cartDtoList).hasSize(3);

	  Assertions.assertThat(cartDtoList.get(0).getUser().getName()).isEqualTo("ssal");
	  Assertions.assertThat(cartDtoList.get(0).getOption().getId()).isEqualTo(5);
	  Assertions.assertThat(cartDtoList.get(0).getOption().getPrice()).isEqualTo(8900);
	  Assertions.assertThat(cartDtoList.get(0).getOption().getOptionName()).isEqualTo("2겹 식빵수세미 6매");

	  Assertions.assertThat(cartDtoList.get(1).getOption().getId()).isEqualTo(10);
	  Assertions.assertThat(cartDtoList.get(1).getOption().getPrice()).isEqualTo(49900);
	  Assertions.assertThat(cartDtoList.get(1).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 레드");

	  Assertions.assertThat(cartDtoList.get(2).getOption().getId()).isEqualTo(11);
	  Assertions.assertThat(cartDtoList.get(2).getOption().getPrice()).isEqualTo(49900);
	  Assertions.assertThat(cartDtoList.get(2).getOption().getOptionName()).isEqualTo("JR310BT (무선 전용) - 그린");
 }
```

#### carttDto

```java
public class CartDTO {
    private int id;
    private UserDTO user   ;
    private OptionDTO option;
    private int quantity;
    private int price;

    public UserDTO getUser(){
        return this.user;
    }
    public OptionDTO getOption(){
        return this.option;
    }
    public CartDTO(Cart cart){
        this.id = cart.getId();
        this.user = new UserDTO(cart.getUser());
        this.option = new OptionDTO(cart.getOption());
        this.quantity = cart.getQuantity();
        this.price = cart.getPrice();
    }

    public static class UserDTO{
        private int id;
        private String name;
        private String email;
        public UserDTO(User user){
            this.id = user.getId();
            this.name = user.getUsername();
            this.email = user.getEmail();
        }

        public String getName(){
            return this.name;
        }

    }
    public static class OptionDTO{
        private int id;
        private int price;
        private String optionName;
        public OptionDTO(Option option){
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
        }
        public String getOptionName(){
            return this.optionName;
        }
        public int getPrice(){
            return this.price;
        }
        public int getId(){
            return this.id;
        }
    }
}
```

### 장바구니 수정

```java
@Test
 public void update() {
	  //given
	  int cartId = 1;
	  int quantity = 10;

	  Cart cart = cartJPARepository.findById(cartId).orElseThrow(
				 () -> new RuntimeException("해당 카트를 찾을 수 없습니다.")
	  );
	  //when

	  cart.update(quantity, cart.getOption().getPrice() * quantity);
	  em.flush();
	  //then
	  Assertions.assertThat(cart.getQuantity()).isEqualTo(quantity);
	  Assertions.assertThat(cart.getPrice()).isEqualTo(cart.getOption().getPrice() * quantity);
 }
```

## Order

### 주문하기

```java
@Test
 public void save(){
	  //given
	  int id = 1;
	  List<Cart> cartList = cartJPARepository.findByUserId(id);
	  Order order = orderJPARepository.findByUserId(id);

	  //when
	  // cart에 들어 있는 데이터를 지울 수 잇어야 함
	  Item item1 = itemJPARepository.save(newItem( cartList.get(0),order));
	  Item item2 = itemJPARepository.save(newItem( cartList.get(1),order));

	  cartJPARepository.deleteAll(cartList);

	  //then
	  Assertions.assertThat(cartJPARepository.findByUserId(id)).isEmpty();

	  Assertions.assertThat(item1.getOrder().getUser().getUsername()).isEqualTo("ssal");
	  Assertions.assertThat(item1.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
	  Assertions.assertThat(item2.getOrder().getUser().getUsername()).isEqualTo("ssal");
	  Assertions.assertThat(item2.getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");

 }
```

### 주문 조회하기

```java
@Test
 public void findById(){
	  //given
	  int id = 1;
	  List<Cart> cartList = cartJPARepository.findByUserId(id);
	  Order order = orderJPARepository.findByUserId(id);
	  Item item1 = itemJPARepository.save(newItem( cartList.get(0),order));
	  Item item2 = itemJPARepository.save(newItem( cartList.get(1),order));

	  //when

	  List<Item> itemList = itemJPARepository.findByOrderId(order.getId());
	  //then
	  Assertions.assertThat(itemList.get(0).getOrder().getUser().getUsername()).isEqualTo("ssal");
	  Assertions.assertThat(itemList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
	  Assertions.assertThat(itemList.get(1).getOrder().getUser().getUsername()).isEqualTo("ssal");
	  Assertions.assertThat(itemList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
 }
```
