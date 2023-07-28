package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query(value = "insert into cart_tb(user_id, option_id, quantity, price) values(:userId, :optionId, :quantity, :price)", nativeQuery = true)
    void mSave(@Param("userId") int userId, @Param("optionId") int optionId, @Param("quantity") int quantity, @Param("price") int price);
    // m은 my의 약자

    List<Cart> findAllByUserId(int userId);
    // 쿼리수정 이게 숙제다 다음 주에 하는데 미리 해보자

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);
    // cart로 userid 조회
    // 순수하게 cart만 들고옴 id, quantity, price // 나머지는 lazy니까 x
    // 쿼리 수정 우리가 해야함 일부만 해주겠다

    void deleteByUserId(int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
