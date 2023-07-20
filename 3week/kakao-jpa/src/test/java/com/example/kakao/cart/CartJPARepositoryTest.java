package com.example.kakao.cart;
import com.example.kakao._core.util.DummyEntity;
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
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private  UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp(){

        //시나리오를 생각해보자 => 장바구니테스트를위해선 옵션이 필요하다
        // => 옵션은 상품으로부터 나온다.
        // => 한명의 유저의 장바구니를 조회 해야한다.
        // 따라서, 옵션들과 상품들, 한명의 유저는 필요하다
        // user를 통해 cart를 FindAll하는 테스트를 위해 장바구니들이 필요하다.
        // 그걸위한 셋팅 + 매 테스트를위한 각 pk초기화 및 영속성 초기화만 해주면된다.

        //초기화된 프록시객체만 노출되게 해서 json 직렬화 성공시키기 위해 ObjectMapper에다가 Hibernate5Module을 등록했습니다.
        //이거 등록해 놓으면 lazy한 애들을 안가져와도 오류은 안나는데 json 출력해보면 option, user같은 애들이 null로 들어가있는걸 확인할수 있었습니다.
        om.registerModule(new Hibernate5Module());

        //사용되는 테이블들의 pk 값이 1부터 증가되도록 초기화, 이미 db에 저장된 엔티티들의 pk를 1부터 시작시켜주진 않음
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        //유저 셋팅
        User user=userJPARepository.save(newUser("bill"));

        //상품들 셋팅
        List<Product> productList = productJPARepository.saveAll(productDummyList());

        //옵션들 셋팅
        optionJPARepository.saveAll(optionDummyList(productList));

        //장바구니들 셋팅
        Cart cart1 = cartJPARepository.save(newCart(user, optionJPARepository.findById(1).get(), 7));
        Cart cart2 = cartJPARepository.save(newCart(user, optionJPARepository.findById(2).get(), 8));
        Cart cart3 = cartJPARepository.save(newCart(user, optionJPARepository.findById(3).get(), 2));
  
        //쿼리 확인을 위한 영속성 초기화
        em.clear();

    }//test메서드안에서는 rollback으로 작용된다 => 누가 해주는데?? => @DataJpaTest가! => 다른테스트에 영향을끼치면안되니까


    @Test
//    lazy한 fetchType일떄 Response JSON 변환 과정 에러 해결 방법
//    1.Hibernate5Module를 사용하여 초기화된 프록시객체만 노출되게 한다.
//    2.DTO를 사용한다.
//    3 join fetch 하는 JPQL 선언
//    저는 2, 3을 해도 도저히 안되서 그냥 1.의 om.registerModule(new Hibernate5Module()); 사용해서 테스트 성공시켰습니다...
    public void cart_find_all_by_userId_lazy_test() throws JsonProcessingException {

        //given
        //유저 id 주어졌을떄
        int userId=1;

        //when, then
        //유저 id로 해당 유저의 cart리스트 조회

        //이부분은 모르겠습니다. 도와주세요 ㅠㅠ....
        // setUp메서드에서 em.clear()안하면야 당연히 setUp()단계에서 셋팅할떄 종속성에컨텍스트에 다들어가니까 테스트성공하는건 알겠습니다.
        //그리고 em.clear해서 종속성 초기화했어도 findByUserId하면 cart만가져오지, option과 product, user는 lazy하니까 안가져오는거 sql쿼리로 확인했습니다.
        // 그러면 방법은
        //1. 일일히 option,product,user를 각각 객체에 접근해서 가져오거나(findById 하거나 getter로 객체 필드 접근하거나)
        //2. DTO쓰거나
        //3. JPQL 쿼리를 쓰거나인데
        //1. 의 경우로 테스트를 완료했고
        //2.의 경우는 DTO를 어떻게 설정 해야 할지 감이 안잡히고
        //3.의 경우 또한 쿼리를 어떻게 해야할지 모르겠습니다... 한번 만들어본다고
        // @Query("select c from Cart c join fetch c.user where c.user.id = :userId")
        //     List<Cart> mfindByUserId(@Param("userId") int userId);
        // 이렇게 짜봤는데, 그러면 cart + user 까지만 알고있는 상태고 여전히 option, cart는 못가져옵니다.
        // 근데 쿼리를 어떻게짜야 user,option,product정보를 가져올지 모르겠습니다...
        //그리고    om.registerModule(new Hibernate5Module()); 이거를 주석처리하면 오류가납니다.. 이것도 확실한 이유를 알고 싶습니다! ㅠㅠ
        //피드백 부탁드립니다 ㅠㅠ

        List<Cart> cartList=cartJPARepository.findByUserId(userId);
        for (Cart cart:cartList) {
            cart.getOption().getOptionName();
            cart.getOption().getProduct().getProductName();
        }

        String responseBody = om.writeValueAsString(cartList);
        System.out.println("테스트 : "+responseBody);


    }


    @Test
    public void cart_add_success_test(){
        //given
        //user의 id와, 추가할 옵션 키, 해당옵션의 개수가 주어졌을떄
        int userId=1;
        int optionId=8;
        int quantity=35;

        //when
        //주어진 옵션과 개수를 토대로 DB에 insert한다 => 영속성 주입
        User user=userJPARepository.findById(userId).get();
        Option option=optionJPARepository.findById(optionId).get();
        cartJPARepository.save(newCart(user, option, quantity));


        //then
        Cart findedCart= cartJPARepository.findById(4).get();
        Assertions.assertThat(findedCart.getId()).isEqualTo(4);
        Assertions.assertThat(findedCart.getOption().getId()).isEqualTo(8);
        Assertions.assertThat(findedCart.getQuantity()).isEqualTo(35);
        Assertions.assertThat(findedCart.getPrice()).isEqualTo(192500);
    }


    @Test
    public void cart_add_not_exist_optionId_fail_test(){
        //given
        //user의 id와, db에 존재하지않는 옵션 키, 해당옵션의 개수가 주어졌을떄
        int userId=1;
        int optionId=9999999;
        int quantity=35;

        //when, then
        //주어진 옵션과 개수를 토대로 cart추가 => db에 존재하지않는 option key값인데 그 key를 가지고있는 엔티티에 접근하려하므로
        //EntityNotFoundException 오류를 기대
        assertThrows(EntityNotFoundException.class,() -> {
            //userJPARepository.getReferenceById(userId) 이거할떄 쿼리문 실행안되는 이유 => user엔티티의 필드에 접근하지는 않아서, 만약
            //  userJPARepository.getReferenceById(userId).getUsername();을 한다면 user 테이블에 select쿼리를 날린다.
            Cart cart = cartJPARepository.save(newCart(userJPARepository.getReferenceById(userId),
                    //그럼  optionJPARepository.getReferenceById(optionId)이거는왜 쿼리나감?
                    //new Cart할떄    .price(option.getPrice() * quantity)에서 option에 price필드에 접근하니까!
                    //그럼 new cart에  .price(quantity) 이렇게 해버리면? => option, user모두 select 쿼리 안날라간다.
                    optionJPARepository.getReferenceById(optionId), quantity));

        });





    }


    @Test
    public void cart_add_already_exist_optionId_fail_test(){
        //given
        //user의 id와, db에 존재하지않는 옵션 키, 해당옵션의 개수가 주어졌고,
        //이미 해당 given요소들로 장바구니에 추가되어 있을떄
        int userId=1;
        int optionId=3;
        int quantity=35;

        System.out.println("\"sss\" = " + "sss");
        //when, then
        //이미 존재하는 옵션을 추가한다. => db에 이미 존재하는 option이므로 
        //데이터 무결성 위반 예외인 DataIntegrityViolationException 을 기대
        assertThrows(DataIntegrityViolationException.class,() -> {
            Cart cart = cartJPARepository.save(newCart(userJPARepository.getReferenceById(userId),
                    optionJPARepository.getReferenceById(optionId), quantity));
        });

    }

    @Test
    public void cart_update_success_test() {

// given
        int cartId = 3;
        int quantity = 80;
        int price = 10000;
// when
        Optional<Cart> cartOptional = cartJPARepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.update(quantity, price);
        }
        //jpa는 트랜잭션 종료시에 엔티티변화를 감지해 auto flush하지만, em.flush를 통해 명시적으로 flush함으로서 sql쿼리날라가는걸 확인 할 수 있다.
        em.flush();
// then
        Optional<Cart> findedCartOptional = cartJPARepository.findById(cartId);
        Cart findedCart = null;
        if (findedCartOptional.isPresent()) {
            findedCart = cartOptional.get();
        }
        Assertions.assertThat(findedCart.getId()).isEqualTo(3);
        Assertions.assertThat(findedCart.getPrice()).isEqualTo(10000);
        Assertions.assertThat(findedCart.getQuantity()).isEqualTo(80);

    }

    @Test
    public void cart_update_not_exist_optionId_fail_test() {

// given
        int cartId = 99999999;
        int quantity = 80;
        int price = 10000;
// when
        String expect=null;

        System.out.println("\"Sss\" = " + "Sss");
        Optional<Cart> cartOptional = cartJPARepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.update(quantity, price);
        }
        else {
            expect="장바구니에 없는 상품은 주문할 수 없습니다 : "+cartId;
        }
        //jpa는 트랜잭션 종료시에 엔티티변화를 감지해 auto flush하지만, em.flush를 통해 명시적으로 flush함으로서 sql쿼리날라가는걸 확인 할 수 있다.
        em.flush();

// then
        Assertions.assertThat(expect).isEqualTo("장바구니에 없는 상품은 주문할 수 없습니다 : 99999999");
    }
}
