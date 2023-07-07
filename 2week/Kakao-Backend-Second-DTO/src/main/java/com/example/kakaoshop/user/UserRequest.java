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


    //이메일 중복체크를 위해 새로만듬
    @Getter
    @Setter
    public static class checkDTO {
        private String email;
    }
}
