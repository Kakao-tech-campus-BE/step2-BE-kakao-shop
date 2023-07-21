package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

public class CartRequest {

    @Getter @Setter @ToString
    public static class SaveDTO {
        private int optionId;


        @Min(value = 1,message = "최소 1개 이상의 옵션을 선택해야 합니다.")
        @Max(value = 100,message = "최대 100개 까지 옵션을 선택가능 합니다.")
        private int quantity;

//
//        @Column(nullable = false)
//        private int quantity;
//
//        @Column(nullable = false)
//        private int price;
        public Cart toEntity(Option option) {
            return    Cart.builder()
                    .id(1)
                    .user(User.builder()

                            .email("test@.com")
                            .password("1234!ss23")
                            .password("1234!ss23")
                            .username("나문희의첫사랑")
                            .roles("Developer")
                            .build())
                    .option(option)
                    .quantity(quantity)
                    .price(option.getPrice() * quantity)
                    .build();


        }
    }



    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        @Min(value = 1,message = "최소 1개 이상의 옵션을 선택해야 합니다.")
        @Max(value = 100,message = "최대 100개 까지 옵션을 선택가능 합니다.")
        private int quantity;
    }



}
