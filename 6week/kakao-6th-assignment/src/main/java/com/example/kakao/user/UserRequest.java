package com.example.kakao.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserRequest {
    @Getter
    @Setter
    public static class JoinDTO {

        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "NOT_EMAIL_FORM")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "CHARACTER_LENGTH_CONSTRAINT_8_TO_20")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "INCLUDE_ENGLISH_LETTERS_NUMBERS_SPECIAL_CHARACTERS_NO_SPACE")
        private String password;

        @Size(min = 8, max = 45, message = "CHARACTER_LENGTH_CONSTRAINT_8_TO_45")
        @NotEmpty
        private String username;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .roles("ROLE_USER")
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "NOT_EMAIL_FORM")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "CHARACTER_LENGTH_CONSTRAINT_8_TO_20")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "INCLUDE_ENGLISH_LETTERS_NUMBERS_SPECIAL_CHARACTERS_NO_SPACE")
        private String password;
    }

    @Getter
    @Setter
    public static class EmailCheckDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "NOT_EMAIL_FORM")
        private String email;
    }

    @Getter
    @Setter
    public static class UpdatePasswordDTO {
        @NotEmpty
        @Size(min = 8, max = 20, message = "CHARACTER_LENGTH_CONSTRAINT_8_TO_20")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "INCLUDE_ENGLISH_LETTERS_NUMBERS_SPECIAL_CHARACTERS_NO_SPACE")
        private String password;
    }
}
