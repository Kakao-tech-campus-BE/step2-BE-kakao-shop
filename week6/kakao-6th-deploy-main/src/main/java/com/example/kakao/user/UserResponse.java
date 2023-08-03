package com.example.kakao.user;

import lombok.Getter;

public class UserResponse {

    @Getter
    public static class FindById{
        private final int id;
        private final String username;
        private final String email;

        public FindById(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}
