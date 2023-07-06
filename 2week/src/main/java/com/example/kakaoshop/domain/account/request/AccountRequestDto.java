package com.example.kakaoshop.domain.account.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AccountRequestDto {

    private AccountRequestDto() {}


    @Getter
    public static class SignUpDto {
        @Email(message = "email 형식이 올바르지 않습니다.")
        @NotBlank(message = "email 은 공백일 수 없습니다.")
        private final String email;

        @NotBlank(message = "password 는 공백일 수 없습니다.")
        @Length(min = 8, max = 20, message = "password 는 8자 이상 20자 이하여야 합니다.")
        private final String password;

        @NotBlank(message = "username 은 공백일 수 없습니다.")
        private final String username;

        @Builder
        public SignUpDto(String email, String password, String username) {
            this.email = email;
            this.password = password;
            this.username = username;
        }
    }


    @Getter
    public static class LoginDto {
        private final String email;
        private final String password;

        @Builder
        public LoginDto(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

}
