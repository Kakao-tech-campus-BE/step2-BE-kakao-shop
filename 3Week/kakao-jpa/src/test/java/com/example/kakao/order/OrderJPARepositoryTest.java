package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    private final EntityManager em;

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    private final CartJPARepository cartJPARepository;

    private final OptionJPARepository optionJPARepository;

    private final ProductJPARepository productJPARepository;

    private final OrderJPARepository orderJPARepository;

    private final ItemJPARepository itemJPARepository;

    @Autowired
    public OrderJPARepositoryTest(
            EntityManager em,
            ObjectMapper om,
            UserJPARepository userJPARepository,
            OptionJPARepository optionJPARepository,
            ProductJPARepository productJPARepository,
            OrderJPARepository orderJPARepository,
            CartJPARepository cartJPARepository,
            ItemJPARepository itemJPARepository) {
        this.em = em;
        this.om = om;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.itemJPARepository = itemJPARepository;
    }

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User ssar = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(ssar, optionListPS));
        Order order = orderJPARepository.save(newOrder(ssar));

        itemJPARepository.saveAll(itemDummyList(cartListPS, order));

        em.clear();
    }

    @DisplayName("주문 테이블 영속화 테스트")
    @Test
    public void order_join_test(){
        Order order = newOrder(newUser("cos"));

        System.out.println("영속화 되기 전 id : " + order.getId());
        orderJPARepository.save(order);
        System.out.println("영속화 되기 후 id : " + order.getId());

        assertEquals(2, order.getId());
    }

    @DisplayName("아이템 테이블 영속화 테스트")
    @Test
    public void item_join_test(){
        Order order = newOrder(newUser("cos"));
        Cart cart = newCart(newUser("cos"), optionDummyList(productDummyList()).get(1), 1);
        Item item= newItem(cart, order);

        System.out.println("영속화 되기 전 id : " + item.getId());
        itemJPARepository.save(item);
        System.out.println("영속화 되기 후 id : " + item.getId());

        assertEquals(3, item.getId());
    }

    @DisplayName("데이터 조회 테스트")
    @Test
    public void order_read_test() throws JsonProcessingException {
        int userId = 1;

        List<Order> orders = orderJPARepository.mFindByUserId(userId);
        List<Item> items = itemJPARepository.mFindByOrderId(orders.get(0).getId());

        String orderResult = om.writeValueAsString(orders);
        System.out.println(orderResult);
        String itemResult = om.writeValueAsString(items);
        System.out.println(itemResult);
        //then 구현
    }

    @DisplayName("전체 데이터 삽입 테스트")
    @Test
    public void create_test() {
        int userId = 1;

        User user = userJPARepository.findById(userId).orElseThrow(RuntimeException::new);
        Order order = newOrder(user);

        orderJPARepository.save(order);

        //cart를 선택해서 주문하는 기능에 관해 고민 중...
        List<Cart> carts = cartJPARepository.mFindAllByUserId(userId);
        List<Item> items = itemDummyList(carts, order);

        itemJPARepository.saveAll(items);

        assertEquals(2, order.getId());
        assertEquals(user.getId(), order.getUser().getId());
        assertEquals(user.getEmail(), order.getUser().getEmail());
        assertEquals(user.getPassword(), order.getUser().getPassword());
        assertEquals(user.getUsername(), order.getUser().getUsername());
        assertEquals(user.getRoles(), order.getUser().getRoles());
        //item에 대한 then
    }
}
