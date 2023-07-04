package com.example.kakaoshop.user;

import lombok.*;

// TODO: email 형식 검사
public class UserRequest {

    private UserRequest() {}

    @Getter
    @Setter
    public static class SignUpDTO {
        private String email;
        private String password;
        private String username;
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }

    @Getter @Setter // TODO: 생성자를 사용하지 않는 이유 ?
    public static class EmailDuplicateCheckDTO {
        private String email;
    }
}
