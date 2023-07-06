package com.example.kakaoshop.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReqCheckDTO {
    private String email;

    @Builder
    public UserReqCheckDTO(String email) {
        this.email = email;
    }
}
