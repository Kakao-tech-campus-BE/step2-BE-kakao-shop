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
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em; //테스트 쿼리 작성하기 위해서 추가하였음

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<User> userListPS = userJPARepository.saveAll(userDummyList());


        cartJPARepository.saveAll(cartDummyList(userListPS, optionListPS));


        em.clear();

        System.out.println("----------추가 완료---------");
    }

    @DisplayName("저장된 장바구니 데이터 불러오기 테스트")
    @Test
    public void loadSavedCart_checkPersistence() throws JsonProcessingException {
        //given
        int userId = 1; // 임의로 설정한 id 값

        //when
        List<Cart> savedCart = cartJPARepository.findAllCartByUserId(userId);

        //then
        Assertions.assertNotNull(savedCart);

        // Cart 객체를 JSON 형태로 변환하여 출력
        String json = om.writeValueAsString(savedCart);
        System.out.println(json);
    }


    @DisplayName("장바구니에 물건 담기 테스트")
    @Test
    public void newProduct_addedToCart_checkPersistence(){
        //given

        //user1 이 가지고 있는 옵션을 user2에 담아보았다.
        int userId = 2;
        int productId = 2;
        String optionName = "22년산 햇단밤 700g(한정판매)";
        int quantity = 2;


        User user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        Product product = productJPARepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        //테스트 용이성으로 옵션 이름을 사용하였지만 나중에는 id를 사용해도 좋을것 같다.
        Option option = optionJPARepository.findByProductAndOptionName(product,optionName);

        int price = option.getPrice() * quantity;

        //when
        Cart myCart = newCart(user,option,quantity);
        cartJPARepository.save(myCart);

        em.flush();
        em.clear();

        //then
        Cart savedCart = cartJPARepository.findByUserAndOption(userId, option.getId());


        Assertions.assertEquals(optionName, savedCart.getOption().getOptionName());
        Assertions.assertEquals(quantity, savedCart.getQuantity());
        Assertions.assertEquals(price, savedCart.getPrice());


    }

    //업데이트
    @DisplayName("장바구니 내 물건수량 수정 테스트")
    @Test
    public void quantity_updatedToCart_checkPersistence(){
        //given
        //카트 전체보기를 해야만 수정,삭제가 가능하기에 쉽게 Id 를 얻을거라 생각하였다.
        int userId = 1;
        int optionId = 6;
        //더미 데이터 만들때 최대 6개 선택 가능하게 했기 때문에 10개가 나올수 없다.
        int updatedQuantity = 10;


        Cart myCart = cartJPARepository.findByUserAndOption(userId,optionId);
        //업데이트 확인용
        Option updatedOption = optionJPARepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("옵션이 존재하지 않습니다."));
        int price = updatedOption.getPrice() * updatedQuantity;

        //when
        myCart.update(updatedQuantity,price);
        cartJPARepository.save(myCart);

        em.flush();
        em.clear();

        //then
        Cart updatedCart = cartJPARepository.findByUserAndOption(userId, optionId);

        Assertions.assertEquals(updatedQuantity, updatedCart.getQuantity());
        Assertions.assertEquals(price, updatedCart.getPrice());
    }
    //삭제

    @DisplayName("장바구니 삭제 테스트")
    @Test
    public void deletedToCart_checkPersistence() {
        //given
        //카트 전체보기를 해야만 수정,삭제가 가능하기에 쉽게 Id 를 얻을거라 생각하였다.
        int userId = 1;
        int optionId = 6;


        Cart myCart = cartJPARepository.findByUserAndOption(userId,optionId);
        //업데이트 확인용

        //when
        cartJPARepository.delete(myCart);

        em.flush();
        em.clear();

        //then
        Cart deletedCart = cartJPARepository.findByUserAndOption(userId, optionId);
        Assertions.assertEquals(deletedCart,null);

    }


}
