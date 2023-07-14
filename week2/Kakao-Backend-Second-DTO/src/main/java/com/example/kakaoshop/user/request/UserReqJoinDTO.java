package com.example.kakaoshop.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqJoinDTO {
    private String email;
    private String password;
    private String username;

    @Builder
    public UserReqJoinDTO(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
