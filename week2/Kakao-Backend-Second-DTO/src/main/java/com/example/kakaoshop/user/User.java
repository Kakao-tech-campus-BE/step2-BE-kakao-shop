package com.example.kakaoshop.user;

import com.example.kakaoshop._core.utils.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_tb")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true)
    private String email; // 인증시 필요한 필드
    @Column(length = 256, nullable = false)
    private String password;
    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 30)
    private String roles; // role은 한 개 이상

    @Builder
    public User(Long id, String email, String password, String username, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    // 요즘은 객체 불변성이 중요하다고 알고있어 setter같이 상태를 변화시키기 보다 새 객체를 내어주었습니다.
    // 하지만 password의 경우는 변경을 노출 시키는 것이 맞는지 모르겠습니다.
    public User changePassword(String password) {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .username(username)
                .roles(roles)
                .build();
    }

    public User changeUsername(String username) {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .username(username)
                .roles(roles)
                .build();
    }

    public User changeRoles(String roles) {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .username(username)
                .roles(roles)
                .build();
    }

    // password는 객체 동등성 검사에서 제외했습니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getUsername(), getRoles());
    }
}
