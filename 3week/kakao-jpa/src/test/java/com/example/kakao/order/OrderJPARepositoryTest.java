package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Order JPA 연결 테스트")
@Import(ObjectMapper.class)
@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {

    private final EntityManager em;
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    private final ObjectMapper om;


    public OrderJPARepositoryTest(
            @Autowired EntityManager em,
            @Autowired ProductJPARepository productJPARepository,
            @Autowired OptionJPARepository optionJPARepository,
            @Autowired UserJPARepository userJPARepository,
            @Autowired CartJPARepository cartJPARepository,
            @Autowired OrderJPARepository orderJPARepository,
            @Autowired ItemJPARepository itemJPARepository,
            @Autowired ObjectMapper om
    ){
        this.em = em;
        this.productJPARepository = productJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.userJPARepository = userJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.orderJPARepository =orderJPARepository;
        this.itemJPARepository = itemJPARepository;
        this.om = om;
    }


    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        userJPARepository.save(newUser("donu"));

        em.clear();

    }


    @DisplayName("주문 결과 확인 쿼리 테스트!!")
    @Test
    public void saveOrder(){
        // given
        String userEmail = "donu@nate.com";
        User userPS = userJPARepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        List<Cart> carts = Arrays.asList(newCart(userPS, optionJPARepository.findById(1).get(), 5),
                newCart(userPS,optionJPARepository.findById(2).get() , 5));

        // when
        Order order = Order.builder().user(userPS).build();
        orderJPARepository.save(order);

        List<Item> items = new ArrayList<>();
        for(Cart c : carts){ // 카트에서 주문아이템으로 옮기는 과정
            Item item = Item.builder().option(c.getOption()).order(order).quantity(c.getQuantity()).price(c.getPrice()).build();
            items.add(item);
        }
        itemJPARepository.saveAll(items);
        em.flush();
        em.clear();

        // then
        List<Order> orderListByUserId = orderJPARepository.findByEmail(userEmail);
        assertEquals(orderListByUserId.size(), 1);
        for(Order o : orderListByUserId) {
            assertNotNull(o.getId());
        }

        //주문 결과 확인시 여러 테이블의 데이터를 가져와야하는데 fetch join으로 한번에 가져옴
        List<Item> itemList = itemJPARepository.mfindByOrderId(1);
        // 여기 밑에서 추가쿼리 x
        System.out.println("=========");
        assertNotNull(itemList.get(0).getOrder().getUser().getEmail());
        System.out.println("=========");
        assertNotNull(itemList.get(0).getOrder().getId());
        System.out.println("=========");
        assertNotNull(itemList.get(0).getOption().getOptionName());
        System.out.println("=========");
        assertNotNull(itemList.get(0).getOption().getProduct().getProductName());
    }

}