# 과제

## Repository 구현

구현되지 않은 리포지토리 - cart, order

- findById는 Optional을 반환하는 함수로 기본 내장되어 있기 때문에 생략했다.
- save와 관련된 테스트는 더미 데이터를 생성하면서 save가 되기 때문에 생략했다.
- **join fetch**를 사용한 이유
    
    join fetch 쿼리를 사용하여 레포지토리를 작성하면 join하는 대상도 같이 영속성 컨텍스트에서 관리하게 할 수 있다. 즉, join으로 작성하면 N+1번 쿼리를 하는 문제가 발생하는데 join fetch는 함께 가져오기 때문에 문제를 해결할 수 있다. Lazy Loading 전략을 기본으로 하면서 같이 fetch해야하는 부분은 fetch하도록 쿼리할 수 있다.
    
- **cart**는 delete와 update가 빈번하게 일어나기 때문에 3개의 테스트를 진행하였다.
- **order**는 주문내역이므로 select와 insert만 발생할 것 같다고 생각해 select 테스트를 진행하였다.

### main/java/com/example/kakao/cart/CartJPARepository.java

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled.png)

- update 쿼리는 Cart 엔티티 내부에 존재

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%201.png)

### main/java/com/example/kakao/test/order/OrderJPARepository.java

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%202.png)

하나의 user와 1:N관계이기 때문에 userId로 데이터를 검색할 수 있어야 한다.

### main/java/order/item/ItemJPARepository.java

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%203.png)

하나의 order와 1:N관계이기 때문에 userId로 데이터를 검색할 수 있어야 한다.

## Dummy code 작성

### test/java/com/example/kakao/_core/util/dummy.java

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%204.png)

- product와 user부분은 더미 데이터가 완성이 되어 있었고 형식에 맞춰 order, cart, item부분의 더미데이터를 생성할 수 있는 코드를 구현했다.
- cart는 하나의 user객체와 N개의 option 리스트들을 받아들이도록 설정했다. 테스트 상에선 2개의 옵션만 갖도록 설정했다.
- order은 id, user객체만을 속성으로 갖는다.
- item 데이터를 생성할 때, item들은 cart에서 가져오도록 했고, 하나의 order에 여러개의 아이템이 생성되는 구조로 만들었다. 테스트 데이터는 하나의 주문에 두개의 아이템이 존재하는 구조이다.

## Repository Test

나머지 레포지토리 테스트들은 구현이 되어 있었고, 위의 레포지토리처럼 order와 cart부분의 테스트를 완성하였다.

테스트는 beforeeach, test 순서로 구성했었으나.. id가 바뀌는 이상한 일이 발생하여 롤백이 제대로 안되는건가 싶어서 aftereach로 데이터를 모두 삭제하도록 후처리를 했지만 끝까지 오류를 해결하지 못했다.

### test/java/com/example/kakao/cart/CartJPARepositoryTest.java

- 전/후처리

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%205.png)

- **findByUserId_findById_test**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%206.png)

ssar@nate.com을 이메일로 하는 유저의 장바구니를 호출하고, 정확검사를 진행하고 통과하였다.

- **updateCart_test**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%207.png)

update테스트를 진행하였다. 시나리오는 ssar@nate.com을 이메일로 하는 유저의 장바구니를 호출하고, 첫번째 카트에서 옵션의 양을 10에서 20으로 변경하는 검사를 하였다. 정확검사를 진행하고 통과하지 못했다.

- **deleteCart_test**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%208.png)

시나리오는 ssar@nate.com을 이메일로 하는 유저의 장바구니를 호출하고, 두번째 카트를 제거하는 검사를 하였다. 정확검사를 진행하고 해당 Optional이 null임을 확인하여 통과하였다.

### test/java/com/example/kakao/order/OrderJPARepositoryTest.java

- 전/후처리는 cart와 비슷하고 좀 더 추가되었다.
- **order_findByUserId_test**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%209.png)

시나리오는 ssar@nate.com을 이메일로 하는 유저의 주문내역을 모두 호출하는 것이다.

- **item_findByOrderId_test**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%2010.png)

시나리오는 ssar@nate.com을 이메일로 하는 유저의 주문내역에 포함된 상품들을 모두 호출하는 것이다.

### 실패

- **updateCart_test**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%2011.png)

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%2012.png)

user의 id가 3, cart의 id가 5로 나타나는데 user의 데이터가 5개가 있지 않는다. 왜 저렇게 뜨는지 모르겠다.

- **item_findByOrderId_test**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%2013.png)

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A6%2045d368c594d945edac9b56c484eefc20/Untitled%2014.png)

item의 id가 3, order의 id가 3로 나타나는데 order의 데이터가 2개, item데이터도 2개를 생성했는데 왜 저렇게 뜨는지 모르겠다.