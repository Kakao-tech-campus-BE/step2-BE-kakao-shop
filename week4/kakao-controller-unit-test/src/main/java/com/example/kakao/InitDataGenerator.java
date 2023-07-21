package com.example.kakao;

import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDataGenerator implements ApplicationRunner {

    private final FakeStore fakeStore;
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        makeUserData();
        makeProductData();
        makeOptionData();
    }

    private void makeUserData() {
        User user = User.builder()
                .email("meta@nate.com")
                .username("meta")
                .password(passwordEncoder.encode("meta1234!"))
                .roles("ROLE_USER")
                .build();
        userJPARepository.save(user);
    }
    private void makeProductData() {
        List<Product> fakeProducts = fakeStore.getProductList();
        productJPARepository.saveAll(fakeProducts);
    }

    private void makeOptionData() {
        List<Option> fakeOptions = fakeStore.getOptionList();
        optionJPARepository.saveAll(fakeOptions);
    }
}
