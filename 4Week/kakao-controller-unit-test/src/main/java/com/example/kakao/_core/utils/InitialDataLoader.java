package com.example.kakao._core.utils;

import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InitialDataLoader implements CommandLineRunner {
    private final FakeStore fakeStore;
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;

    @Override
    public void run(String... args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        List<Product> productList = fakeStore.getProductList();
        productJPARepository.saveAll(productList);

        List<Option> optionList = fakeStore.getOptionList();
        optionJPARepository.saveAll(optionList);

        User user = User.builder()
                .id(1)
                .email("metassar@nate.com")
                .password(passwordEncoder.encode("meta1234!"))
                .username("metassar")
                .roles("ROLE_USER")
                .build();

        userJPARepository.save(user);
    }
}
