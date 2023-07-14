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
import com.example.kakao._core.util.DummyEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity{

    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;


    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;



    @BeforeEach
    public  void setUp(){
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List <Cart> carts = Arrays.asList(
                newCart(user,optionListPS.get(0),5),
                newCart(user,optionListPS.get(2),3),
                newCart(user,optionListPS.get(7),1)
        );
        cartJPARepository.saveAll(carts);
        Cart cart = carts.get(0);
        Order order = newOrder(user);
        orderJPARepository.save(order);
        Item item = newItem(cart,order);
        itemJPARepository.save(item);

        em.clear();


    }
    // 1. order find all
    // 2. order find by order_id
    // 3. item find by order_id
    // 4. find item and order

    @Test
    public void order_FindAll() throws JsonProcessingException {
        //given

        //when
        List<Order> orderListPS = orderJPARepository.orderFindAll();

        //then
        String responseBody = om.writeValueAsString(orderListPS);
        System.out.println("테스트 : "+responseBody);

    }

    @Test
    public void order_FindById_test() throws JsonProcessingException{
        //given
        int id = 1;

        //when
        Optional<Order> orderPS = orderJPARepository.findById(id);

        //then
        String responseBody = om.writeValueAsString(orderPS);
        System.out.println("테스트 : "+responseBody);
        Assertions.assertThat(orderPS.get().getId()).isEqualTo(1);
        Assertions.assertThat(orderPS.get().getUser().getUsername()).isEqualTo("ssar");

    }
    @Test
    public void item_mFindByOrderId_lazy_test()  throws JsonProcessingException {

        //given
        int id = 1;

        // when
        Optional<Order> order = orderJPARepository.findById(id);
        List<Item> itemListPS = itemJPARepository.mFindByOrderId(id);


        //then
        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : "+responseBody);
        System.out.println("assertion");
        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(50000);



    }

    @Test
    public void order_findById_and_item_findByOrderId_lazy_test() throws JsonProcessingException  {
        // given
        int id = 1;

        // when
        Order orderPS = orderJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        );
        List<Item> itemListPS = itemJPARepository.mFindByOrderId(id); // Lazy


        // then
        String responseBody1 = om.writeValueAsString(orderPS);
        String responseBody2 = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : "+responseBody1);
        System.out.println("테스트 : "+responseBody2);

        System.out.println("assertion");
        Assertions.assertThat(orderPS.getId()).isEqualTo(1);
        Assertions.assertThat(orderPS.getUser().getUsername()).isEqualTo("ssar");


        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(50000);

    }



}
