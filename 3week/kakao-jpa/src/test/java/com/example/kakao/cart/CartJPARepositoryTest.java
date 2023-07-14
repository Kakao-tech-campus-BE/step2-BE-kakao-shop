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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.assertj.core.api.Assertions;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// r -> pc -> datasource 를 띄움
@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {

    @Autowired //의존성 주입
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){ //테스트 전 이 메소드 실행

        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();


        //유저 정보 가져오기
        User user = userJPARepository.save(newUser("ssar"));
        //1번 물품 가져오기
        Product product1 = productJPARepository.save(newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000));
        //1번 물품에 맞는 옵션들 가져오기
        Option option1 = optionJPARepository.save(newOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
        Option option2 = optionJPARepository.save(newOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 5종", 10900));
        Option option3 = optionJPARepository.save(newOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 6종", 10900));

        //카트 더미데이터 가져오기
        cartJPARepository.save(newCart(user, option1, 5));
        cartJPARepository.save(newCart(user, option2, 5));
        cartJPARepository.save(newCart(user, option3, 5));


        System.out.println("-----------------------------------------------------");

        //영속화되어있으면 Persistent Context에 있는 것을 가져오기 때문에 clear을 해준다.
        em.clear(); //PC를 비움

    }

    //장바구니 조회 테스트
    //조인 쿼리를 직접 만들어서 사용
    @Test
    public void cart_findAll_test() throws JsonProcessingException{
        //given
        //user의 id
        int productId=1;
        int userId =1;
        //when

        //먼저, insert 시켜준 상품(Product) 1번을 select 하여 데이터를 가져온다. (Lazy Loading)
        Product productPS = productJPARepository.findById(productId).orElseThrow(
                ()-> new RuntimeException("product 없음")
        );

        //상품(Product) 는 영속화 되었기 때문에 Option 엔티티를 조회(select)할땐 join을 안해줘도 된다!?
        List<Option> optionList = optionJPARepository.findByProductId(productId);

        //유저는(User) 아직 영속화가 되지 않았기 때문에
        // 장바구니(Cart) 엔티티와 유저(User) 엔티티를 fetch join 하여 cartList에 담는다. N:1관계
        List<Cart> cartList = cartJPARepository.findAllByJoinFetch(userId);

        //직렬화
        String responseBody2 = om.writeValueAsString(cartList);
        System.out.println("테스트 : "+responseBody2);


    }



    //장바구니에서 옵션의 수량을 변경
    //cart select, option select, update
    @Test
    public void cart_update_quantity_test(){

        //given
        int cartId = 1;
        int quantity = 10;

        //when
        Optional<Cart> cartOP = cartJPARepository.findCartById(cartId);
        Optional<Option> optionOP = optionJPARepository.findById(cartId);

        Cart CartPS = new Cart();

        //변경
        if(cartOP.isPresent()&&optionOP.isPresent()){
            CartPS = cartOP.get();
            CartPS.update(quantity, optionOP.get().getPrice());
        }

        em.flush();

        //then
        //옵션 수량과 그 가격 일치하는지 확인
        Assertions.assertThat(CartPS.getQuantity()).isEqualTo(10);
        Assertions.assertThat(CartPS.getOption().getPrice()).isEqualTo(10000);
        
    }

    //장바구니 삭제 테스트
    @Test
    public void cart_deleteById_test(){
        //given
        int cartId = 1; //1번 카트 아이템 삭제
        //when
        Optional<Cart> CartOP = cartJPARepository.findCartById(cartId);

        if(CartOP.isPresent()){
            cartJPARepository.deleteById(cartId);
            em.flush(); //단위테스트할때 자동으로 rollback되므로 강제로 flush
        }

        //then
        Assertions.assertThat(CartOP.isPresent()).isEqualTo(true);

    }

    //장바구니 모두 삭제
    //select가 두번 일어남
    @Test
    public void cart_deleteAll_test(){
        //given

        //when
        List<Cart> cartList = cartJPARepository.findAll();
        cartJPARepository.deleteAll();
        em.flush();

        List<Cart> updatedCartList = cartJPARepository.findAll();
        //then
        Assertions.assertThat(updatedCartList.isEmpty()).isEqualTo(true);
    }

}