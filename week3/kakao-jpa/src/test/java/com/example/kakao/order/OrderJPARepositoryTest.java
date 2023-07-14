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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

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
    CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User userPS = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));
        List<Order> orderListPS = orderJPARepository.saveAll(orderDummyList(userPS));
        itemJPARepository.saveAll(itemDummyList(cartListPS, orderListPS));
        em.clear(); //SAVE한 후에 영속성에서 제거
    }

    //후행작업
    @AfterEach
    public void cleanUp(){
        itemJPARepository.deleteAll();
        orderJPARepository.deleteAll();
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
    }

    @Test
    public void order_findByUserId_test() throws JsonProcessingException {
        // given
        //email을 통해 user를 찾는다.
        String email = "ssar@nate.com";
        User user = userJPARepository.findByEmail(email).orElseThrow();

        // when
        //user객체에서 id를 가져와 userId를 기준으로 데이터를 호출함.
        List<Order> orderPS = orderJPARepository.findByUserId(user.getId());
        String responseBody = om.writeValueAsString(orderPS);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(orderPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderPS.get(0).getUser().getId()).isEqualTo(1);

        Assertions.assertThat(orderPS.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(orderPS.get(1).getUser().getId()).isEqualTo(1);
    }

    //시나리오: 주문목록에 해당하는 아이템을 찾기 위해 유저의 첫번째 주문번호를 통해 아이템 목록을 불러온다.
    @Test
    public void item_findByOrderId_test() throws JsonProcessingException {
        // given
        //email을 통해 user를 찾는다.
        String email = "ssar@nate.com";
        User user = userJPARepository.findByEmail(email).orElseThrow();
        List<Order> orderPS = orderJPARepository.findByUserId(user.getId());
        int id = orderPS.get(0).getId();
        // when
        List<Item> items = itemJPARepository.findByOrderId(id);

        // then - 주문의 아이템이 2개이기 때문에 검증을 두번 실행
        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getOrder().getId()).isEqualTo(4);
        Assertions.assertThat(items.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getPrice()).isEqualTo(169000);
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(items.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(items.get(0).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
        Assertions.assertThat(items.get(0).getOption().getPrice()).isEqualTo(16900);
        Assertions.assertThat(items.get(0).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        Assertions.assertThat(items.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(items.get(1).getOrder().getId()).isEqualTo(2);
        Assertions.assertThat(items.get(1).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(1).getPrice()).isEqualTo(44500);
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(items.get(1).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(1).getOption().getOptionName()).isEqualTo("2겹 식빵수세미 6매");
        Assertions.assertThat(items.get(1).getOption().getPrice()).isEqualTo(8900);
        Assertions.assertThat(items.get(1).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(1).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    }
}


