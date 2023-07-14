package com.example.kakao.order;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.Cart;
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
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private  ProductJPARepository productJPARepository;


    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private  UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp(){

        //시나리오를 생각해보자 => 주문 테스트를 위해선 주문 아이템이필요하다.
        // => 주문아이템은 장바구니와 주문으로 부터 온다.
        // => 장바구니에는 옵션이있다.
        // => 옵션은 상품으로 부터 온다.
        // => 한명의 유저의 주문을 조회 해야한다.
        // 따라서, 옵션들과 상품들, 장바구니와 주만아이템들과 한명의 유저와 주문이 필요하다
        // 그걸위한 셋팅 + 매 테스트를위한 각 pk초기화 및 영속성 초기화만 해주면된다.

        //초기화된 프록시객체만 노출되게 해서 json 직렬화 성공시키기 위해 ObjectMapper에다가 Hibernate5Module을 등록했습니다.
        //이거 등록해 놓으면 lazy한 애들을 안가져와도 오류는안납니다.
        om.registerModule(new Hibernate5Module());

        //사용되는 테이블들의 pk 값이 1부터 증가되도록 초기화, 이미 db에 저장된 엔티티들의 pk를 1부터 시작시켜주진 않음
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        //유저 셋팅
        User user=userJPARepository.save(newUser("bill"));

        //상품들 셋팅
        List<Product> productList = productJPARepository.saveAll(productDummyList());

        //옵션들 셋팅
        optionJPARepository.saveAll(optionDummyList(productList));

        //장바구니 셋팅
        Cart cart1 = cartJPARepository.save(newCart(user, optionJPARepository.findById(1).get(), 7));
        Cart cart2 = cartJPARepository.save(newCart(user, optionJPARepository.findById(2).get(), 8));
        Cart cart3 = cartJPARepository.save(newCart(user, optionJPARepository.findById(8).get(), 2));

        //주문 셋팅
        Order order=orderJPARepository.save(newOrder(user));

        //주문아이템들 셋팅
        Item item1=itemJPARepository.save(newItem(cart1,order));
        Item item2=itemJPARepository.save(newItem(cart2,order));
        Item item3=itemJPARepository.save(newItem(cart3,order));

        //쿼리 확인을 위한 영속성 초기화
        em.clear();

    }//test메서드안에서는 rollback으로 작용된다 => 누가 해주는데?? => @DataJpaTest가! => 다른테스트에 영향을끼치면안되니까




    @Test
    public void order_save_test() throws JsonProcessingException {
        //given
        //유저 id와 만들어진 카트가 주어진다.
        int userId = 1;
        Cart cartPS = cartJPARepository.save(newCart(userJPARepository.getReferenceById(userId),
                optionJPARepository.getReferenceById(3), 14));

        // when
        //해당 유저 id로 주문이 하나만들어지고,
        //거기에 cart를 넣어준다.
        Order order=orderJPARepository.save(newOrder(userJPARepository.getReferenceById(userId)));
        int orderId=order.getId();
        Item itemPS = itemJPARepository.save(newItem(cartPS,
                orderJPARepository.getReferenceById(orderId)));

        String responseBody = om.writeValueAsString(itemPS);
        System.out.println("test : " + responseBody);

    }






    @Test
    public void order_findById_and_user_findById_lazy_test() throws JsonProcessingException {

        //given
        //주문 id 주어졌을떄
        int orderId = 1;

        //when,
        //test 기대값 변수 선언
        String expect = null;

        //성공시 json화 해서 보여줄 itemList 선언
        List<Item> itemList = null;

        // 우선 주어진 id로 해당 주문 있는지 확인
        Optional<Order> orderOptional = orderJPARepository.findById(orderId);

        //해당 id의 주문이 존재한다면
        if (orderOptional.isPresent()) {

            //itemList에 해당 주문 id로 조회해서 넣어줌
            itemList = itemJPARepository.findByOrderId(orderId);

            //이부분이 이게 최선인지 모르겠는데, option과 option안에 product가 필요해서, 각각
            //   item.getOption().getOptionName();
            //   item.getOption().getProduct().getProductName();
            //을 통해 각 필드에 접근함으로서 그떄마다 select구문이 돌아가는걸확인햇고 결과도 잘나왔다.
            // 다른 방법이 뭐가 있을지 알고 싶습니다!
            //그리고 위에   om.registerModule(new Hibernate5Module()); 이걸 주석처리 하면 테스트 오류가나는데,
            // 각 필드에 접근해서 쿼리문날라갔는데도 왜 오류나는지 궁금합니다!
            //findById로 가져오면 프록시객체가아니고, 필드로 접근하면 프록시객체를 가져와서 오류나는 걸까요?
            for (Item item:itemList) {
                item.getOption().getOptionName();
                item.getOption().getProduct().getProductName();
            }
            expect = om.writeValueAsString(itemList);
        } else {
            expect = "해당 주문을 찾을 수 없습니다 : " + orderId;
        }
        System.out.println("om.writeValueAsString(itemList) = " + om.writeValueAsString(itemList));
        //then
        Assertions.assertThat(expect).isEqualTo(om.writeValueAsString(itemList));

    }


}
