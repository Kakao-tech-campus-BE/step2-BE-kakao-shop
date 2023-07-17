package com.example.kakao.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter @Setter
    public static class FindById{
        private int id;
        private String username;
        private String email;

        public FindById(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
