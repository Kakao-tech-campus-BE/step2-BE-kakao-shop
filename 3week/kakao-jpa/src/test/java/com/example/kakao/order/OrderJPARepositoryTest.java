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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@Import(ObjectMapper.class)
public class OrderJPARepositoryTest extends DummyEntity {

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

    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;


    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(user, optionListPS));
        Order order = orderJPARepository.save(orderDummy(user));
        itemJPARepository.saveAll(itemDummyList(cartListPS, order));

        em.clear();
    }


    @DisplayName("사용자의 주문 결과 불러오기 테스트")
    @Test
    public void findByUserId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Item> itemListPS = itemJPARepository.findByOrderId(id);// Lazy


        System.out.println("json 직렬화 직전========================");
        //String responseBody = om.writeValueAsString(itemListPS); //직렬화에서 예외 뜨는데 이거 주석처리한 상태 검사는 통과????
        //System.out.println("테스트 : "+responseBody);

        // then (상태 검사)
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getProduct().getProductName())
                .isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemListPS.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(100000);

        Assertions.assertThat(itemListPS.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(itemListPS.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(itemListPS.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(itemListPS.get(1).getOption().getPrice()).isEqualTo(10900);
        Assertions.assertThat(itemListPS.get(1).getQuantity()).isEqualTo(10);
        Assertions.assertThat(itemListPS.get(1).getPrice()).isEqualTo(109000);

    }

    @Test
    public void save(){}


}
