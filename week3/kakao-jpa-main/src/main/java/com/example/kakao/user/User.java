package com.example.kakao.user;

import com.example.kakao.cart.Cart;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_tb")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false, unique = true)
    private String email; // 인증시 필요한 필드
    @Column(length = 256, nullable = false)
    private String password;
    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 30)
    private String roles;

    @Builder
    public User(int id, String email, String password, String username, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public boolean equals(Object object){
        User myUser = (User) object;
        return this.id == myUser.id && this.email.equals(myUser.email)
                && BCrypt.checkpw(myUser.password, this.password) && this.username.equals(myUser.username)
                && this.roles.equals(myUser.roles);
    }
}
