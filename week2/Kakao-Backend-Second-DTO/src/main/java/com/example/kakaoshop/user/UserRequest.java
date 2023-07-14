package com.example.kakaoshop.user;

import lombok.*;

import javax.validation.constraints.*;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {

//        @NotEmpty
//        @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
//                message = "이메일 형식을 맞춰주세요.")
        private String email;

//        @NotEmpty
//        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
//                message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상 20자 이하이어야 합니다.")
        private String password;

//        @NotEmpty
//        @Size(min = 2, max = 8, message = "유저네임은 2자 이상 8자 이하입니다.")
        private String username;
    }

    @Getter
    @Setter
    public static class LoginDTO {

//        @NotEmpty
//        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
//                message = "이메일 형식을 맞춰주세요.")
        private String email;

//        @NotEmpty
//        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
//                message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상 20자 이하이어야 합니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class CheckDTO {

//        @NotEmpty
//        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
//                message = "이메일 형식을 맞춰주세요.")
        private String email;
    }
}
