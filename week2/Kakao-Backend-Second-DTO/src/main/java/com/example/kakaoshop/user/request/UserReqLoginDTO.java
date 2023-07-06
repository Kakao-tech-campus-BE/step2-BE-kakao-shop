package com.example.kakaoshop.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqLoginDTO {
    private String email;
    private String password;

    @Builder
    public UserReqLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
