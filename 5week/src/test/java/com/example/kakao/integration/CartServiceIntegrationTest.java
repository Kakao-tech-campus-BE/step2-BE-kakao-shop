package com.example.kakao.integration;

import com.example.kakao.domain.cart.*;
import com.example.kakao.domain.product.Product;
import com.example.kakao.domain.product.option.OptionJPARepository;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CartServiceIntegrationTest {
  @Autowired
  private CartService cartService;

  @Autowired
  private CartJPARepository cartRepository;

  @Autowired
  private OptionJPARepository optionRepository;

  @Autowired
  private UserJPARepository userRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private ObjectMapper om;

  @BeforeEach
  void setUp(){
    em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
  }


  @Test
  @DisplayName("장바구니에 상품 담기")
  void addCartList() {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
    List<CartRequest.SaveDTO> requestDTOs = List.of(
        CartRequest.SaveDTO.builder().optionId(1).quantity(2).build(),
        CartRequest.SaveDTO.builder().optionId(2).quantity(2).build(),
        CartRequest.SaveDTO.builder().optionId(3).quantity(2).build()
    );

    // when
    cartService.addCartList(requestDTOs, user);
    List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

    // then
    assertThat(cartList).hasSize(3);
    assertThat(cartList.get(0).getQuantity()).isEqualTo(2);
    assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    assertThat(cartList.get(1).getQuantity()).isEqualTo(2);
    assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
    assertThat(cartList.get(2).getQuantity()).isEqualTo(2);
    assertThat(cartList.get(2).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
  }

  @Test
  @DisplayName("장바구니에 상품 담기 - 이미 존재하는 상품은 수량, 가격만 업데이트")
  void addCartListWithUpdate() {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
    List<CartRequest.SaveDTO> requestDTOs = List.of(
      CartRequest.SaveDTO.builder().optionId(1).quantity(3).build(),
      CartRequest.SaveDTO.builder().optionId(2).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(3).quantity(2).build()
    );
    List<CartRequest.SaveDTO> requestDTOs2 = List.of(
      CartRequest.SaveDTO.builder().optionId(2).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(3).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(4).quantity(4).build()
    );

    // when
    cartService.addCartList(requestDTOs, user);
    cartService.addCartList(requestDTOs2, user);
    List<Cart> cartList = cartRepository.findAllByUserId(user.getId());


    // then
    assertThat(cartList).hasSize(4);
    assertThat(cartList.get(0).getQuantity()).isEqualTo(3);
    assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    assertThat(cartList.get(0).getPrice()).isEqualTo(30000);
    assertThat(cartList.get(1).getQuantity()).isEqualTo(4);
    assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
    assertThat(cartList.get(1).getPrice()).isEqualTo(43600);
    assertThat(cartList.get(2).getQuantity()).isEqualTo(4);
    assertThat(cartList.get(2).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
    assertThat(cartList.get(3).getQuantity()).isEqualTo(4);
    assertThat(cartList.get(3).getOption().getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
  }

  @Test
  @DisplayName("장바구니에 담긴 상품 확인")
  void findCartItems() throws JsonProcessingException {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
    List<CartRequest.SaveDTO> requestDTOs = List.of(
      CartRequest.SaveDTO.builder().optionId(1).quantity(3).build(),
      CartRequest.SaveDTO.builder().optionId(2).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(6).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(7).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(9).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(10).quantity(2).build()
    );


    // when
    cartService.addCartList(requestDTOs, user);
    List<Cart> cartList = cartRepository.findAllByUserId(user.getId());
    CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);


    // then
    System.out.println("테스트 : " + om.writeValueAsString(responseDTO)); // 이부분 작성비용이 너무 커서 눈으로 테스트 ? -> 콘솔에서 json pretty print 가 가능해야함.
    assertThat(responseDTO.getProducts()).hasSize(3);
  }

  @Test
  @DisplayName("장바구니 수정")
  void updateCart() {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
    List<CartRequest.SaveDTO> saveDTOs = List.of(
      CartRequest.SaveDTO.builder().optionId(1).quantity(3).build(),
      CartRequest.SaveDTO.builder().optionId(2).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(6).quantity(2).build(), // 삭제될 예정
      CartRequest.SaveDTO.builder().optionId(7).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(9).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(10).quantity(2).build()
    );
    List<CartRequest.UpdateDTO> updateDTOs = List.of(
      CartRequest.UpdateDTO.builder().cartId(1).quantity(4).build(),
      CartRequest.UpdateDTO.builder().cartId(2).quantity(4).build(),
      CartRequest.UpdateDTO.builder().cartId(3).quantity(0).build() // 삭제
    );

    // when
    cartService.addCartList(saveDTOs, user);
    cartService.update(updateDTOs, user);
    List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

    // then
    assertThat(cartList).hasSize(5);
    assertThat(cartList.get(0).getQuantity()).isEqualTo(4); // -- 올바르게 수량이 변경되었는가
    assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    assertThat(cartList.get(1).getQuantity()).isEqualTo(4); // -- 올바르게 수량이 변경되었는가
    assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
    assertThat(cartList.stream().anyMatch(cart -> cart.getOption().getId() == 6)).isFalse(); // optionId 6 이 존재하지 않는가?
    assertThat(cartList.get(4).getOption().getId()).isEqualTo(10); // 명시하지 않은 요소는 그대로 유지되었는가 ?

  }
}
