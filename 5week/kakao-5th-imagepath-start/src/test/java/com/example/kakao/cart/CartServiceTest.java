package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.util.MockJPAResultEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when; //BDDMockito Mockito 뭐가 다름?


@ExtendWith(MockitoExtension.class)
public class CartServiceTest extends MockJPAResultEntity {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartJPARepository cartJPARepository;

    @Mock
    private OptionJPARepository optionJPARepository;

    @DisplayName("장바구니에 이미 상품이 존재하는 경우 수량 추가 테스트")
    @Test
    public void addCartList_whenExists_test() {
        //given
        List<Option> givenOptions = optionDummyList(productDummyList());

        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CartRequest.SaveDTO temp = new CartRequest.SaveDTO();
            temp.setOptionId(i);
            temp.setQuantity(2);
            requestDTOs.add(temp);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());
        List<Option> options = new ArrayList<>();

        //mock
        when(optionJPARepository.findById(anyInt()))
                .thenAnswer(invocation -> {
                    int param = invocation.getArgument(0);
                    if (param >= 1 && param <= 48) return Optional.of(givenOptions.get(param - 1));
                    throw new Exception404("해당 옵션을 찾을 수 없습니다 : " + param);
                });

        //optionId 1은 이미 수량 2개로 저장되어 있음
        when(cartJPARepository.findByOptionIdAndUserId(anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    int param = invocation.getArgument(0);
                    if (param == 1) return Optional.of(newCart(sessionUser, givenOptions.get(param - 1), 2));
                    else return Optional.empty();
                });

        when(cartJPARepository.save(any(Cart.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        //when
        List<CartRequest.SaveDTO> saveInfos = cartService.addCartList(requestDTOs, sessionUser);
        for (CartRequest.SaveDTO saveInfo : saveInfos) {
            System.out.println(saveInfo.getOptionId());
            System.out.println(saveInfo.getQuantity());
        }

        //Then
        assertThat(saveInfos.size()).isEqualTo(3);

        //이미 존재하는 장바구니에 수량 추가
        assertThat(saveInfos.get(0).getOptionId()).isEqualTo(1);
        assertThat(saveInfos.get(0).getQuantity()).isEqualTo(4);

        assertThat(saveInfos.get(1).getOptionId()).isEqualTo(2);
        assertThat(saveInfos.get(1).getQuantity()).isEqualTo(2);

        assertThat(saveInfos.get(2).getOptionId()).isEqualTo(3);
        assertThat(saveInfos.get(2).getQuantity()).isEqualTo(2);
    }

    @DisplayName("장바구니 추가에 동일한 옵션id이 두가지 이상 들어왔을 때 예외 발생 테스트")
    @Test
    public void sameOption_fail_test() {
        //given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CartRequest.SaveDTO temp = new CartRequest.SaveDTO();
            temp.setOptionId(1);
            temp.setQuantity(2 + i);
            requestDTOs.add(temp);
        }

        User sessionUser = newUser("ssarMango", new BCryptPasswordEncoder());

        //when
        try {
            cartService.addCartList(requestDTOs, sessionUser);
        } catch (Exception400 e) {
            //then
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("동일한 옵션을 처리할 수 없습니다");
        }
    }
}
