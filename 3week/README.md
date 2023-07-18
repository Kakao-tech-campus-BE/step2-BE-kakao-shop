# 3주차 과제

Repository에 구현한 매서드들은(findby~) 이것은 서비스에서 사용되는데 이번 과제에서는 Service가 따로 있지않아서 Controller 기준으로 Repository 단위 테스트를 진행 해보았습니다.
그리고 추가적으로 DummiyEntity에 구현된 optionDummyList 와 productDummyList를 이용하여 cartDummyList를 추가해주었습니다.

# Product

### 상품 전체 조회

- findAll

### 개별 상품 조회

- option_findByProductId - productId를 통해 옵션을 검색
- findById - controller에서 path variable로 올 id값으로 하나의 product 조회

# User

유저부분은 건들필요 없다고 하셔서 따로 작성하지 않았다.

# Cart

## 장바구니 담기

save하는 과정은 Test전에 BeforeEach 부분에서 사용됨으로 따로 테스트 하지않았다.

## 장바구니 보기

컨트롤러에서 장바구니 조회를 보면 parameter로 UserDetails만 받는것을 볼 수 있다. 따라서 userId를 받아서 해당 user의 장바구니를 보여준다고 이해했다.

- cart_findByUserId

## 장바구니(업데이트)

cart가 영속성 컨텍스트에 올라가서 관리가 된 상 태에서 cart의 값이 변하면 알아서

커밋될때 cart가 업데이트 된다. 즉 Cart Entity 값만 변경시켜주면됨

## 결제하기

cart controller를 보면 cart/clear이다. 주문하기 하면 장바구니를 비워야함으로 이렇게 한것같다.

- deleteByUserId
  - when 에 deleteByUserId를 하고 then에서 삭제됬는지 확인하려면 findByUserId가 null인지 확인하면되는데 이것은 테스트가 독립적이지 않은것같아서 구현하지 않았다.

# Order

## 결제하기

FindyByIdDTO에서 ProductDTO가 쓰이고

ProductDTO에는 productid, productName, List<ItemDTO>이 있고

itemDTO에는 id, optionname, quantity, price

그리고 controller에서 userDetails를 parameter로 받기때문에

내가 생각한 시나리오는 이것이였다.

Order ➡️ Item ➡️ Option ➡️ Product

1. userId로 order를 찾는다. (우리가 필요한 orderId 꺼내서 사용)
   - orderRepository - findByUserId
2. orderId로 item 을 찾는다(그리고 우리가 필요한 itemid, quantity, price를 꺼내서 사용)

- itemRepository - findByOrderId

1. itemId로 option을 찾으려고했으나 item과 option은 1:1 관계인데 fk가 item에만있다. 따라서 itemId로 option을 찾을 수 없다.

   따라서 Option은 Product랑 조인해서 검색하고 Optionid로 item을 찾으면 될것같다는 생각이 들었다.

   - Option이랑 Product랑 조인해서 조회
     - OptionRepository
     ```java
     @Query("select o from Option o join fetch o.product where o.product.id = :productId")`
     ```
   - ItemRepository - findByOptionId
