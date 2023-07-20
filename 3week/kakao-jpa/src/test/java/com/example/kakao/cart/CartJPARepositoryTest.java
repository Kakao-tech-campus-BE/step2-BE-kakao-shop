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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User user = newUser("ssar");
        userJPARepository.save(user);

        List<Product> productList = productDummyList();
        productJPARepository.saveAll(productList);

        List<Option> optionList = optionDummyList(productList);
        optionJPARepository.saveAll(optionList);

        // /carts/add에서 해야하는 작업이기 때문에 setUp에 넣지 않는다.

        /*List<Cart> cartList = new ArrayList<>();
        cartList.add(newCart(user, optionList.get(0), 5));
        cartList.add(newCart(user, optionList.get(1), 5));
        cartJPARepository.saveAll(cartList);*/

        em.clear();
    }

    @Test
    public void add_test() throws JsonProcessingException {

        //given

        List<Integer> optionIdList = Arrays.asList(1,2);
        List<Integer> quantityList = Arrays.asList(5,5);
        int id = 1;

        //when

        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다")
        );

        List<Option> optionList = optionJPARepository.mFindAllByOptionIds(optionIdList);
        List<Cart> cartList = new ArrayList<>();

        for(int i = 0; i < 2; i++)
        {
            cartList.add(newCart(user, optionList.get(i), quantityList.get(i)));
        }

        cartJPARepository.saveAll(cartList);
        // 같은 상품을 add 할 경우에 대한 처리는 controller 선에서 해야하므로 여기서는 하지 않는다.

        //then

        //응답할 데이터가 없다.
    }

    private void init() throws JsonProcessingException{

        List<Integer> optionIdList = Arrays.asList(1,2);
        List<Integer> quantityList = Arrays.asList(5,5);
        int id = 1;


        User user = userJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 유저를 찾을 수 없습니다")
        );

        List<Option> optionList = optionJPARepository.mFindAllByOptionIds(optionIdList);
        List<Cart> cartList = new ArrayList<>();

        for(int i = 0; i < 2; i++)
        {
            cartList.add(newCart(user, optionList.get(i), quantityList.get(i)));
        }

        cartJPARepository.saveAll(cartList);
        em.clear();
    }

    @Test
    public void findAllByUserId_test() throws JsonProcessingException{

        //given

        int id = 1;// user의 id
        init();

        //when

        List<Cart> cartList = cartJPARepository.mFindByUserIdJoinFetchOption(id);

        Set<Integer> productIdList = cartList.stream().map( cart -> cart.getOption().getProduct().getId()).collect(Collectors.toSet());
        productJPARepository.findAllById(productIdList); // 한번의 쿼리로 미리 조회하여 영속성 컨텍스트를 만들기 위함.

        CartResponse.FindAllDTO findAllDTO = new CartResponse.FindAllDTO(cartList);

        //then

        String responseBody = om.writeValueAsString(findAllDTO);
        System.out.println("테스트 : " + responseBody);
        Assertions.assertThat(responseBody).isEqualTo("{\"products\":" +
                "[{\"id\":1,\"productName\":\"기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전\"," +
                "\"carts\":[{\"id\":1,\"option\":{\"id\":1,\"optionName\":\"01. 슬라이딩 지퍼백 크리스마스에디션 4종\",\"price\":10000}," +
                "\"quantity\":5,\"price\":50000}," +
                "{\"id\":2,\"option\":{\"id\":2,\"optionName\":\"02. 슬라이딩 지퍼백 플라워에디션 5종\",\"price\":10900}," +
                "\"quantity\":5,\"price\":54500}]}],\"totalPrice\":104500}");
    }

    @Test
    public void update_test() throws JsonProcessingException{

        //given

        List<Integer> cartIdList = Arrays.asList(1,2);
        List<Integer> quantityList = Arrays.asList(10,10);
        // int id = 1; // user의 id -> 이미 인증된 사용자가 cart조회 화면에서 수량을 조작하기 때문에 사실상 필요없는 정보
        init();

        //when

        List<Cart> cartList = cartJPARepository.mFindAllByIdJoinFetch(cartIdList);
        // 그냥 직접입력해서 넣어도 되지만 데이터의 정합성을 위해 option의 가격이 필요하다. 그래서 joinFetch를 활용했다.
        for(int i = 0; i < 2; i++){
            Integer optionPrice = cartList.get(i).getOption().getPrice();
            cartList.get(i).update(quantityList.get(i), quantityList.get(i)*optionPrice);
        }

        em.flush();
        // 여기서 고민이 많았다. bulk update를 하고 싶은데
        // 일괄 변경이 아닌 각 cart 값마다 다른 값을 세팅해주는 것이기 때문에
        // bulk update는 적절치 않다고 판단하여 그냥 dirty checking을 통하여 쿼리를 하나씩 보내도록 하였다.
        // 근데 만약 수십, 수백만의 사용자가 장바구니에 업데이트를 한다고 치면 이것도 상당한 부담일텐데....

        CartResponse.UpdateDTO updateDTO = new CartResponse.UpdateDTO(cartList);

        //then

        String responseBody = om.writeValueAsString(updateDTO);
        System.out.println("테스트 : " + responseBody);
        Assertions.assertThat(responseBody).isEqualTo("{\"carts\":" +
                "[{\"cartId\":1,\"optionId\":1,\"optionName\":\"01. 슬라이딩 지퍼백 크리스마스에디션 4종\",\"quantity\":10,\"price\":100000}," +
                "{\"cartId\":2,\"optionId\":2,\"optionName\":\"02. 슬라이딩 지퍼백 플라워에디션 5종\",\"quantity\":10,\"price\":109000}]," +
                "\"totalPrice\":209000}");
    }
}
