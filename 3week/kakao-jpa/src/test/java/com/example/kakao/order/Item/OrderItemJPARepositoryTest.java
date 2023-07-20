package com.example.kakao.order.Item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class OrderItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em; //테스트 쿼리 작성하기 위해서 추가하였음


    //어벤저스 어셈블,,, 실제로는
    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        //구현이 어렵길래 User는 단일객체로,,,, user은 2개의 order이 존재한다. cart=option은 4개 존재한다.
        User user = userJPARepository.save(newUser("test"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyObject(user,optionListPS));

        List<Order> orderListPS = orderJPARepository.saveAll(orderDummyObject(user));
        // orderId 1에는 3개의 cart가 orderId 2에는 1개의 cart가 존재한다.
        itemJPARepository.saveAll(orderItemDummyList(cartListPS,orderListPS));


        em.clear();

        System.out.println("----------추가 완료---------");
    }


    // 주문은 변하면 안된다고 생각하여 Read만 작성하였다.
    @DisplayName("주문 확인 테스트")
    @Test
    public void loadCompletedOrder_checkPersistence() throws JsonProcessingException {

        //주문 상세란에 들어가기 위해서 로그인 후 특정 주문을 클릭하는 과정 = orderId 알아냄
        // given
        int userId = 1;
        List<Order> userOrders = orderJPARepository.findByUserIdWithJoinFetch(userId);
        int orderId = userOrders.get(0).getId();

        //when
        List<Item> items = itemJPARepository.findByOrderId(orderId);
        String responseBody = om.writeValueAsString(items);
        System.out.println("테스트 : " +responseBody);

        //then
        Assertions.assertEquals(items.get(0).getOption().getOptionName(),"01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertEquals(items.get(0).getOption().getPrice(),10000);
        Assertions.assertEquals(items.get(0).getQuantity(),5);
        Assertions.assertEquals(items.get(0).getPrice(),50000);

    }

    @DisplayName("카트에서 주문으로 변경")
    @Test
    public void none(){
        //단순히 get해서 주문으로 set하는 방식인지 잘 모르겠다.
    }





}
