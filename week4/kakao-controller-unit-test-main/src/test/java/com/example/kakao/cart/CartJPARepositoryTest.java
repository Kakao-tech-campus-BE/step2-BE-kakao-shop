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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp() {
        System.out.println("-----before 시작-----");
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = newCarts(user,optionListPS);
        cartJPARepository.saveAll(cartListPS);
        em.flush();
        em.clear();
        System.out.println("-----before 완료-----");
    }

    @AfterEach
    public void resetIndex() {
        System.out.println("-----after 시작-----");
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.flush();
        em.clear();
        System.out.println("-----after 완료-----");
    }

    @Transactional
    @Test
    public void delete_by_userId_test() {
        // given
        // 유저 id 가 주어지고
        int id = 1;

        // 다른 장바구니에 간섭여부 확인을 위한 더미데이터
        int otherUserId = 2;
        User other = userJPARepository.save(newUser("hello"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = newCarts(other,optionListPS);
        int otherCartCount = cartListPS.size();
        cartJPARepository.saveAll(cartListPS);
        em.flush();
//        em.clear();

        // when
        // 유저가 가진 모든 장바구니를 삭제하면
        System.out.println("장바구니 삭제");
        User user = userJPARepository.findById(id).orElse(null);
        Assertions.assertNotNull(user);
        cartJPARepository.deleteByIds(
                cartJPARepository.findCartByUserId(user.getId())
                        .stream()
                        .map(x -> x.getId())
                        .collect(Collectors.toList())
        );

        // then
        // 장바구니에 남은 옵션이 없어야 한다.
        Assertions.assertEquals(cartJPARepository.findCartByUserId(user.getId()).size(), 0);
        other = userJPARepository.findById(otherUserId).orElse(null);
        Assertions.assertNotNull(other);
        Assertions.assertEquals(cartJPARepository.findCartByUserId(other.getId()).size(), otherCartCount);
    }

    @Transactional
    @Test
    public void cart_included_duplication_add_test() throws Exception  {
        // given
        // 유저 정보, saveDTO 가 주어지고 saveDTO는 일붜 중복된 데이터이다.
        int id = 1;
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(3);
        saveDTO1.setQuantity(5);

        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(4);
        saveDTO2.setQuantity(5);

        List<CartRequest.SaveDTO> saveDTOs = new ArrayList<>(Arrays.asList(saveDTO1, saveDTO2));

        // when
        // 유저 정보를 조회하고 장바구니에 담게되면
        User user = userJPARepository.findById(id).orElse(null);
        Assertions.assertNotNull(user);
        List<Integer> ids =  saveDTOs.stream()
                .map(x-> new Integer(x.getOptionId()))
                .collect(Collectors.toList());
        Assertions.assertFalse(ids.isEmpty());

        // 중복된 장바구니 조회 및 담기
        System.out.println("중복된 장바구니 조회 및 담기");
        List<Cart> carts =  new ArrayList<>();
        cartJPARepository.findDuplicatedCartsByOptionIds(user.getId(), ids).forEach(x->{
            System.out.println("option id : "+x.getId());
            CartRequest.SaveDTO saveDTO = saveDTOs.stream()
                    .filter(y-> Objects.equals(y.getOptionId(), x.getOption().getId()))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(saveDTO);
            saveDTOs.remove(saveDTO);

            Assertions.assertNotNull(saveDTOs);
            Option findOption = optionJPARepository.findById(saveDTO.getOptionId()).orElse(null);
            Assertions.assertNotNull(findOption);
            x.update(x.getQuantity()+saveDTO.getQuantity(), findOption.getPrice());
            carts.add(x);
        });

        // 나머지 장바구니 담기
        System.out.println("나머지 장바구니 담기");
        saveDTOs.forEach(System.out::println);
        saveDTOs.forEach(x->{
            Option findOption = optionJPARepository.findById(x.getOptionId()).orElse(null);
            Assertions.assertNotNull(findOption);
            carts.add(newCart(user, findOption, x.getQuantity()));
        });
        System.out.println("장바구니 추가");
        cartJPARepository.saveAll(carts);

        // then
        // 정상적으로 담겨야 한다.
        System.out.println("담은 장바구니 검사");
        List<Cart> findCarts = cartJPARepository.findCartByUserId(user.getId());
        findCarts.forEach(x-> {
            Cart first = x;
            Option findOption = first.getOption();
            Product findProduct = findOption.getProduct();
            findProduct = Product.builder()
                    .productName(findProduct.getProductName())
                    .image(findProduct.getImage())
                    .id(findProduct.getId())
                    .price(findProduct.getPrice())
                    .description(findProduct.getDescription())
                    .build();
            findOption = Option.builder()
                    .optionName(findOption.getOptionName())
                    .product(findProduct)
                    .price(findOption.getPrice())
                    .id(findOption.getId())
                    .build();
            try {
                System.out.println(x.getQuantity() + " : " + om.writeValueAsString(findOption));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Transactional
    @Test
    public void new_user_add_test() throws Exception {
        User user = userJPARepository.save(newUser("meta"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = Arrays.asList(
                newCart(user, optionListPS.get(0), 5),
                newCart(user, optionListPS.get(1), 5),
                newCart(user, optionListPS.get(2), 10)
        );
        cartJPARepository.saveAll(cartListPS);
    }

    @Transactional
    @Test
    public void cart_update_test() throws Exception  {
        // given
        // 유저 정보, saveDTO 가 주어지고
        int id = 1;
        CartRequest.UpdateDTO saveDTO1 = new CartRequest.UpdateDTO();
        saveDTO1.setCartId(1);
        saveDTO1.setQuantity(30);

        CartRequest.UpdateDTO saveDTO2 = new CartRequest.UpdateDTO();
        saveDTO2.setCartId(2);
        saveDTO2.setQuantity(20);

        List<CartRequest.UpdateDTO> saveDTOs = new ArrayList<>(Arrays.asList(saveDTO1, saveDTO2));

        // when
        // 유저 정보를 조회하고 장바구니에 담게되면
        User user = userJPARepository.findById(id).orElse(null);
        Assertions.assertNotNull(user);

        List<Integer> ids =  saveDTOs.stream()
                .map(x-> new Integer(x.getCartId()))
                .collect(Collectors.toList());
        Assertions.assertFalse(ids.isEmpty());

        // 중복된 장바구니 조회 및 담기
        System.out.println(ids);
        List<Cart>carts = new ArrayList<>();
        cartJPARepository.findDuplicatedCartsByOptionIds(user.getId(), ids).forEach(x->{
            System.out.println("option id : "+x.getId());
            CartRequest.UpdateDTO saveDTO = saveDTOs.stream()
                    .filter(y-> Objects.equals(y.getCartId(), x.getOption().getId()))
                    .findFirst()
                    .orElse(null);
            Assertions.assertNotNull(saveDTO);

            saveDTOs.remove(saveDTO);


            Option findOption = optionJPARepository.findById(saveDTO.getCartId()).orElse(null);
            Assertions.assertNotNull(findOption);
            x.update(saveDTO.getQuantity(), findOption.getPrice()*saveDTO.getQuantity());
            carts.add(x);
        });

        System.out.println("장바구니 추가");
        if (saveDTOs.isEmpty()) {
             List<Cart> tmp = cartJPARepository.saveAll(carts);
        } else {
            System.out.println(saveDTOs);
            throw new Exception("기존에 없던 옵션을 추가하려고 했습니다.");
        }
        em.flush();
        em.clear();

        // then
        // 정상적으로 담겨야 한다.
        List<Cart> findCarts = cartJPARepository.findCartByUserId(user.getId());
        System.out.println(om.writeValueAsString(findCarts.get(0)));
    }

    @Transactional
    @Test
    public void cart_without_user_findByUserId() throws JsonProcessingException {
        // given
        // userId 가 다음과 같고
        int id = 1;

        // when
        // 해당 유저가 가진 장바구니를 찾는다면
        List<Cart> cartListPs = cartJPARepository.findCartByUserId(id);

        // then
        // 해당 유저의 장바구니 목록을 출력한다.(유저의 정보가 빠진채로)
        System.out.println(om.writeValueAsString(cartListPs));
    }
}