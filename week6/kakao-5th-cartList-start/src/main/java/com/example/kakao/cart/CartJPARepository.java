package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

//    @Query(value = "insert into cart_tb(user_id, option_id, quantity, price) values(:userId, :optionId, :quantity, :price)", nativeQuery = true)
//    void mSave(@Param("userId") int userId, @Param("optionId") int optionId, @Param("quantity") int quantity, @Param("price") int price);

    // select이 여러번 이루어지는 문제를 join fetch로 해결하고자 함
    @Query("select c from Cart c join fetch c.option o join fetch o.product")
    List<Cart> findAllByUserId(int userId);

    @Modifying
    @Transactional
    @Query("update Cart c set c.quantity = c.quantity + :quantity where c.id = :cartId ")
    void mUpdate(@Param("quantity")int quantity, @Param("cartId")int cartId);


    // select 문을 줄이기위해 쿼리문 작성해야함 현재 수정본임
    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("delete from Cart c where c.user.id = :userId ")
    void deleteByUserId(@Param("userId")int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
