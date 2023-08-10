package com.example.kakao.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String email;
        private String password;
        private String username;


        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .roles("ROLE_USER")
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class EmailCheckDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
    }
}
