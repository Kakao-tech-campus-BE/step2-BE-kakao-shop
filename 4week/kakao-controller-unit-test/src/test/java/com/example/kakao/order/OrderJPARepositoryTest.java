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

@Import(ObjectMapper.class)
@DisplayName("주문 관련 JPA 테스트")
@DataJpaTest
public class   OrderJPARepositoryTest extends DummyEntity {


    private EntityManager em;
    private CartJPARepository cartJPARepository;
    private UserJPARepository userJPARepository;
    private ProductJPARepository productJPARepository;
    private OptionJPARepository optionJPARepository;
    private OrderJPARepository orderJPARepository;
    private ItemJPARepository itemJPARepository;
    private ObjectMapper om;

    public OrderJPARepositoryTest(@Autowired EntityManager em,
                                  @Autowired CartJPARepository cartJPARepository,
                                  @Autowired UserJPARepository userJPARepository,
                                  @Autowired ProductJPARepository productJPARepository,
                                  @Autowired OptionJPARepository optionJPARepository,
                                  @Autowired OrderJPARepository orderJPARepository,
                                  @Autowired ItemJPARepository itemJPARepository,
                                  @Autowired ObjectMapper om) {
        this.em = em;
        this.cartJPARepository = cartJPARepository;
        this.userJPARepository = userJPARepository;
        this.productJPARepository = productJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.itemJPARepository = itemJPARepository;
        this.om = om;
    }

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        // 데이터를 날아 가지만 저장되는 id sequence는 초기화되지 않는다.
        User user = newUser("user");
        userJPARepository.save(user);
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));
        List<Cart> cartList = cartJPARepository.saveAll(cartDummyList(user,optionList,2));
        Order order = newOrder(user);
        orderJPARepository.save(order);
        itemJPARepository.saveAll(itemDummyList(cartList,order));
        em.clear();
    }

    @Test
    @DisplayName("결제(주문) 테스트")
    void orderAndItemSave_test() {
        // given
        User testuser = newUser("testuser");
        userJPARepository.save(testuser);
        Integer userid = testuser.getId();
        Integer optionid=1;
        Option option =optionJPARepository.findById(optionid).orElseThrow(
                () -> new RuntimeException("해당 옵션을 찾을 수 없습니다.")
        );
        Cart cart = newCart(testuser,option,3);
        cartJPARepository.save(cart);
        long previousOrderCount = orderJPARepository.count();
        long previousItemCount = itemJPARepository.count();
        // when
        System.out.println("====================start===================");
        Order testorder = newOrder(testuser);
        orderJPARepository.save(testorder);
        int orderid= testorder.getId();
        Item item = newItem(cart,testorder);
        itemJPARepository.save(item);
        System.out.println("========================end=====================");
        // then
        Assertions.assertThat(orderJPARepository.count()).isEqualTo(previousOrderCount+1);
        Assertions.assertThat(itemJPARepository.count()).isEqualTo(previousItemCount+1);
        Item savedItem = itemJPARepository.findByOrderId(orderid).orElseThrow(
                ()-> new RuntimeException("해당 주문을 찾을 수 없습니다.")
        );
        Assertions.assertThat(savedItem.getId()).isEqualTo(item.getId());
        Assertions.assertThat(savedItem.getOrder().getId()).isEqualTo(orderid);
        Assertions.assertThat(savedItem.getOption().getId()).isEqualTo(optionid);
        Assertions.assertThat(savedItem.getOption().getProduct().getId()).isEqualTo(option.getProduct().getId());
        Assertions.assertThat(savedItem.getOrder().getUser().getId()).isEqualTo(userid);

    }


    @Test
    @DisplayName("주문 결과 확인 테스트 lazy- 에러")
    void itemProductOption_findByOrderId_lazy_error_test()  {
        // given
        Integer orderid=1;
        // when
        System.out.println("====================start===================");
        List<Item> itemList = itemJPARepository.findAllByOrderId(orderid);
        System.out.println("========================end=====================");
        // then (상태 검사)
        Assertions.assertThatThrownBy(()-> om.writeValueAsString(itemList)).isInstanceOf(JsonProcessingException.class);
    }

    @Test
    @DisplayName("주문 결과 확인 테스트 lazy- 커스텀 쿼리(product, user, option, user fetch join)")
    void itemProductOption_findByOrderId_lazy_test1() throws JsonProcessingException {
        // given
        Integer orderid=1;
        // when
        System.out.println("====================start===================");
        List<Item> itemList = itemJPARepository.mFindAllByOrderId(orderid);
        String responseBody = om.writeValueAsString(itemList);
        System.out.println("테스트 : "+responseBody);
        System.out.println("========================end=====================");
        // then (상태 검사)
        Assertions.assertThat(itemList.get(0).getPrice()).isEqualTo(20000);
        Assertions.assertThat(itemList.get(0).getQuantity()).isEqualTo(2);
        Assertions.assertThat(itemList.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(itemList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemList.get(0).getOrder().getUser().getUsername()).isEqualTo("user");
        Assertions.assertThat(itemList.get(0).getOrder().getId()).isEqualTo(orderid);
    }
}
