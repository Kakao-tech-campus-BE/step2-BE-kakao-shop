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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Import(ObjectMapper.class)
@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;


    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = Arrays.asList(
                newCart(user, optionListPS.get(0), 5),
                newCart(user, optionListPS.get(1), 5),
                newCart(user, optionListPS.get(2), 10)
        );
        cartJPARepository.saveAll(cartListPS);
        Order order = orderJPARepository.save(newOrder(user));
        itemJPARepository.saveAll(cartListPS.stream()
                .map(x->newItem(x,order))
                .collect(Collectors.toList()));

        em.clear();
        System.out.println("-----before 완료-----");
    }

    @AfterEach
    public void close() {
        System.out.println("-----after 시작-----");
        em.clear();
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        orderJPARepository.deleteAll();
        itemJPARepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear();
        System.out.println("-----after 완료-----");
    }

    /*
    1. 결재
    given
    유저 정보, 기존 카트 정보
    then
    결재 등록
    */
    @Test
    public void order_test() {
        // given
        // 사용자 정보와 카트 정보가 주어졌을 때
        User user = userJPARepository.save(newUser("hello"));
        List<Option> optionListPS = optionJPARepository.findAll();
        List<Cart> cartListPS = new ArrayList<>();
        if (optionListPS.size() > 1) {
            for(int i=0;i<2;i++) {
                cartListPS.add(newCart(user, optionListPS.get(i), 3));
            }
        }
        cartJPARepository.saveAll(cartListPS);
        em.clear();

        // when
        // 주문을 저장하고 기존의 장바구니를 비우게 되면
        int cartCountBeforeDelete = cartJPARepository.countByUserId(user.getId());
        int orderCountBeforeDelete = orderJPARepository.countByUserId(user.getId());
        int itemCountBeforeDelete = itemJPARepository.countByUserId(user.getId());
        Order order = orderJPARepository.save(newOrder(user));
        itemJPARepository.saveAll(cartListPS.stream()
                .map(x->newItem(x,order))
                .collect(Collectors.toList()));
        cartJPARepository.deleteByUserId(user.getId());

        // then
        // 비워진 장바구니와 늘어난 주문과, 아이템을 볼 수 있다.
        int cartCountNumAfterDelete = cartJPARepository.countByUserId(user.getId());
        int orderCountAfterDelete = orderJPARepository.countByUserId(user.getId());
        int itemCountAfterDelete = itemJPARepository.countByUserId(user.getId());
        System.out.println("cart : " + cartCountBeforeDelete + "->" + cartCountNumAfterDelete);
        System.out.println("order : " + orderCountBeforeDelete + "->" + orderCountAfterDelete);
        System.out.println("item : " + itemCountBeforeDelete + "->" + itemCountAfterDelete);
    }

    /*
    2. 주문 결과 확인
    given
    유저 정보, 주문 인덱스
    then
    find by userId and id
     */
    @Test
    public void findOrderItemByUser_test() throws Exception {
        // given
        // 유저 정보와 주문 인덱스를 통해서
        int id = 1;
        int orderIndex = 2;

        // 사용자의 주문을 하나 더 추가
        User prevUser = userJPARepository.findById(id).orElse(null);
        Assertions.assertNotNull(prevUser);
        List<Option> optionListPS2 = optionJPARepository.findAll();
        List<Cart> cartListPS2 = new ArrayList<>();
        if (optionListPS2.size() > 8) {
            for(int i=4;i<7;i++) {
                cartListPS2.add(newCart(prevUser, optionListPS2.get(i), 40));
            }
        }
        cartJPARepository.saveAll(cartListPS2);
        orderJPARepository.save(newOrder(prevUser));
        em.clear();

        // when
        // 주문 아이템을 최근 순서로 검색하면
        User user = userJPARepository.findById(id).orElse(null);
        Assertions.assertNotNull(user);
        List<Order> orders = orderJPARepository.findByUserId(user.getId());
        System.out.println("orders size : " + orders.size());
        List<Item> item = null;
        if (orderIndex > 0 && orders.size() > orderIndex-1) {
            int orderId = orders.indexOf(orders.size() - orderIndex);
             item = itemJPARepository.findByOrderId(orderId);
        } else {
            throw new Exception("찾을 없는 인덱스 입니다.");
        }


        // then
        // 해당 주문들을 가져온 것을 알 수 있다.
        Assertions.assertNotNull(item);
        item.forEach(x->{
            System.out.println("order id : " + x.getOrder().getId() + ", option id : " + x.getOption().getId());
        });
    }
    @Test
    public void findOrderByUser_test() throws JsonProcessingException {
        // given
        // 유저 정보가 주어지고, 주문 정보에는 다른 유저의 주문 정보가 섞여있더라도
        int userId = 1;

        // 다른 사용자의 더미데이터
        User user1 = userJPARepository.save(newUser("hello"));
        List<Option> optionListPS1 = optionJPARepository.findAll();
        List<Cart> cartListPS1 = new ArrayList<>();
        if (optionListPS1.size() > 1) {
            for(int i=0;i<2;i++) {
                cartListPS1.add(newCart(user1, optionListPS1.get(i), 3));
            }
        }
        cartJPARepository.saveAll(cartListPS1);
        orderJPARepository.save(newOrder(user1));

        // 찾는 사용자의 추가 더미데이터
        User prevUser = userJPARepository.findById(userId).orElse(null);
        Assertions.assertNotNull(prevUser);
        List<Option> optionListPS2 = optionJPARepository.findAll();
        List<Cart> cartListPS2 = new ArrayList<>();
        if (optionListPS2.size() > 8) {
            for(int i=4;i<7;i++) {
                cartListPS2.add(newCart(prevUser, optionListPS2.get(i), 40));
            }
        }
        cartJPARepository.saveAll(cartListPS2);
        orderJPARepository.save(newOrder(prevUser));
        em.clear();

        // when
        // 유저를 확인한 후 주문정보를 가져오면
        User user = userJPARepository.findById(userId).orElse(null);
        Assertions.assertNotNull(user);
        List<Order> orders = orderJPARepository.findByUserId(user.getId());
        Assertions.assertFalse(orders.isEmpty());


        // then
        // 중간에 들어온 다른 유저의 주문을 무시하고 해당 유저의 주문만을 가져올 수 있다.
        List<Order> allOrders = orderJPARepository.findAll();
        System.out.println("find result : " + orders.size());
        System.out.println("total size : " + allOrders.size());
        System.out.println("total count : " + orderJPARepository.countByUserId(user.getId()));
        System.out.println("find data : " + om.writeValueAsString(orders));
        allOrders.forEach(x->
        {
            try {
                System.out.println("actual user : " + om.writeValueAsString(x));
            } catch (JsonProcessingException e) {
                System.out.println("could not serializable user : " + x.getUser().getUsername());
                System.out.println(e);
            }
        });
    }
}