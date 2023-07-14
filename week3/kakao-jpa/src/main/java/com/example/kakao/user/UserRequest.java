package com.example.kakao.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class UserRequest {
    @Getter @Setter @ToString
    public static class JoinDTO {
        private String email;
        private String password;
        private String username;
    }

    @Getter @Setter @ToString
    public static class LoginDTO {
        private String email;
        private String password;
    }

    @Getter @Setter @ToString
    public static class EmailCheckDTO {
        private String email;
    }
}
