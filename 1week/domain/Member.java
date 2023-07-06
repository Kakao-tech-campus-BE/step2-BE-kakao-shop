package pnu.kakao.ShoppingMall.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@Setter
public class Member{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="member_id")
    private Long id;

    @Column(name="member_name", length = 20)
    private String name;

    private String email;

    @Column(length = 20)
    private String password;
    private Timestamp createdAt;

    @OneToMany(mappedBy="member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy="member")
    private List<Cart> carts = new ArrayList<>();

    @PrePersist
    void createdAt(){
        this.createdAt = Timestamp.from(Instant.now());
    }

    // TODO : 추후에 주소까지 넣게된다면 추가할 예정
//    @Embedded
//    private Address address;
}


