package com.example.kakao.unkown;

import com.example.kakao.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanUtilsTest {
    
    @Test
    public void copyProperties_test(){ // 깊은 복사
        // given
        User user = User.builder()
                .id(1)
                .email("ssar@nate.com")
                .password("1234")
                .username("ssar")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        // when
        CopyUser copyUser = new CopyUser();
        BeanUtils.copyProperties(user, copyUser);

        // then
        System.out.println("테스트 : "+copyUser);
    }

    public static class CopyUser {
        private int id;
        private String email;
        private String password;
        private String username;
        private List<String> roles = new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        @Override
        public String toString() {
            return "CopyUser{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    ", roles=" + roles +
                    '}';
        }
    }
}
