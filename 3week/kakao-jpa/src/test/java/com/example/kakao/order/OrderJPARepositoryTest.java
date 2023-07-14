package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User user = newUser("ssar");
        userJPARepository.save(user);

        List<Product> productList = productDummyList();
        productJPARepository.saveAll(productList);

        List<Option> optionList = optionDummyList(productList);
        optionJPARepository.saveAll(optionList);

        List<Cart> cartList = new ArrayList<>();
        cartList.add(newCart(user, optionList.get(0), 5));
        cartList.add(newCart(user, optionList.get(1), 5));
        cartJPARepository.saveAll(cartList);

        em.clear();

        // itemList에 추가하는 경우는 cart에 임시 저장되어 있던 내용을 삭제하고 item으로 옮겨오는 것이기 때문에
        // 아래 과정은 save_test에서 해주어야 한다.

        /*
        Order order = newOrder(user);
        orderJPARepository.save(order);

        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem(cartList.get(0), order));
        itemList.add(newItem(cartList.get(1), order));
        itemJPARepository.saveAll(itemList);
        */
    }

    @Test // 테스트끼리는 독립적인게 맞다
    public void save_test() throws JsonProcessingException{

        //given

        int id = 1;

        //when

        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다")
        );

        // 먼저 cart에서 해당 유저의 id를 통해 장바구니에 있는 모든 아이템들을 가져와야 한다.
        // 따라서 cartRepository에 새로운 함수를 추가한다. -> join없이 userId로만 접근하도록 작성한다.
        // em.clear()를 추가하니 hibernateLazyInitializer 예외가 발생, join fetch 구문으로 해결
        // 진짜 모든 정보를 받아오려고 했으나 Product가 로딩이 안됨. -> 현재 테스트 취지에 맞지 않는 정보, 굳이 끌어올 필요 없다.
        List<Cart> cartList = cartJPARepository.mFindByUserIdJoinFetchOption(id);

        // 주문 전에 해당 유저에 대한 Order 테이블이 생성될 것이다.
        Order order = newOrder(user);
        // System.out.println("여긴가?"); // 의문의 select 쿼리가 나간다. 도대체 왜? -> id가 이미 지정되어 있어서!
        orderJPARepository.save(order);
        // System.out.println("여기구나");

        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem(cartList.get(0), order));
        itemList.add(newItem(cartList.get(1), order));
        itemJPARepository.saveAll(itemList);

        // 이게 중요하다. 이제 장바구니에 있던 현 유저의 id와 일치하는 모든 값들을 삭제해야 한다.
        cartJPARepository.deleteByUserId(id);

        //then

        //응답할 데이터 X

    }

    private void init(int id) throws JsonProcessingException{
        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다")
        );

        List<Cart> cartList = cartJPARepository.mFindByUserIdJoinFetchOption(id);

        // 주문 전에 해당 유저에 대한 Order 테이블이 생성될 것이다.
        Order order = newOrder(user);
        orderJPARepository.save(order);

        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem(cartList.get(0), order));
        itemList.add(newItem(cartList.get(1), order));
        itemJPARepository.saveAll(itemList);
    }


    // 위의 테스트랑 시작 조건이 다른데?
    @Test
    public void findById_test() throws JsonProcessingException{

        //given

        int id = 1; // 아마 order의 id
        int userId = 1;
        // 안타깝지만 save_test에서 했던 코드를 똑같이 반복해야 한다. 따라서 메소드를 별도로 빼서 사용하였다.
        init(userId);
        em.clear(); // 저장되어 있는 상태에서 보는 것이기 때문에 영속성 컨텍스트를 삭제해야 모든 쿼리가 보인다.

        //when

        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 주문을 찾을 수 없습니다.")
        );

        //List<Item> itemList = itemJPARepository.findByOrderId(id); // 역시 n+1문제 발생 option 개수 만큼 추가 쿼리 나감.
        List<Item> itemList = itemJPARepository.mFindByOrderIdJoinFetchUser(id);
        // 쿼리가 한 번 덜 나가긴 하는데 이번엔 option에 관련된 n+1문제 발생
        // 각 Item에 연관된 Option은 join fetch를 통해 가져왔다. 그러면 그 Option들과 연관된 Product 정보는?
        // Option에 연관된 Product 개수 만큼 또 쿼리가 발생할 것이다. 이번 같은 경우 연관 Product가 하나라 넘어 갔지만
        // 정말 많은 유저가 이런 식으로 주문을 하게 된다면..?? 서버 부하가 커질것이다.


        Set<Integer> productIdSet = itemList.stream().map( item -> item.getOption().getProduct().getId()).collect(Collectors.toSet());
        productJPARepository.findAllById(productIdSet); // 어디에 쓸 목적이 아니라 한 방 쿼리로 product를 조회하여 영속성 컨텍스트로 만들어주기 위한 코드이다.
        /*Object test = Hibernate.unproxy(itemList.get(0).getOption().getProduct());
        Object test1 = Hibernate.unproxy(itemList.get(1).getOption().getProduct());*/
        // -> 혹시 unproxy 메소드를 사용하면 proxy 객체가 일반 객체로 대체될까 궁금하여 해보았다.

        OrderResponse.FindByIdDTO findByIdDTO = new OrderResponse.FindByIdDTO(order, itemList);

        //then


        String responseBody = om.writeValueAsString(findByIdDTO);
        System.out.println("테스트 : " + responseBody);
        Assertions.assertThat(responseBody).isEqualTo(
                "{\"id\":1," +
                        "\"products\":" +
                        "[{\"id\":1,\"productName\":\"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전\"," +
                        "\"items\":[{\"id\":1,\"optionName\":\"01. 슬라이딩 지퍼백 크리스마스에디션 4종\"," +
                        "\"quantity\":5,\"price\":50000}," +
                        "{\"id\":2,\"optionName\":\"02. 슬라이딩 지퍼백 플라워에디션 5종\"," +
                        "\"quantity\":5,\"price\":54500}]}],\"totalPrice\":104500}"
        );

        // responseBody = om.writeValueAsString(itemList); // -> 프록시 객체에 대하여 큰 오해를 하고있는 것 같다.
        // 프록시 객체를 통해 조회쿼리가 들어가서 값을 끌어왔다. != 프록시 객체가 일반 객체로 바뀌었다.
        // System.out.println("될까? : " + itemList);
    }

    /*
    // 이하는 proxy 객체의 수명 테스트
    // return 이후 사용하지 않는 객체로 간주되어 jvm이 회수할 줄 알았는데 잘 작동하더라...
    // 하지만 정상적인 개발자라면 해당 함수 내에서 DTO로 리턴하지 굳이 테이블 하나를 통째로 리턴하진 않을 것이다.

    private Item returnProxy () throws JsonProcessingException{
        int userId = 1;
        init(userId);
        em.clear();


        Item item = itemJPARepository.findById(1).orElse(null);
        return item;
    }


    @Test
    public void proxyLifecycle_test() throws JsonProcessingException{
        Item item = returnProxy();
        int id = 1;
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 주문을 찾을 수 없습니다.")
        );

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        OrderResponse.FindByIdDTO findByIdDTO = new OrderResponse.FindByIdDTO(order, itemList);

        String responseBody = om.writeValueAsString(findByIdDTO);
    }
    */
}
