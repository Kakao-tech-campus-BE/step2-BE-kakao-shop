package com.example.kakaoshop.user;

import lombok.*;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
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
}
