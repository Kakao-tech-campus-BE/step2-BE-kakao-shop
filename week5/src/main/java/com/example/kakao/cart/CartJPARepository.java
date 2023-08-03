package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    // RequestDTO에서 Client에게 price 값까지 받아오는 경우
//    @Query(value = "insert into cart_tb(user_id, option_id, quantity, price) values (:userId, :opitonId, :quantity, :price)", nativeQuery = true)
//    void mSave(@Param("userId") int userId, @Param("optionId") int optionId, @Param("quantity") int quantity, @Param("price") int price)

    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> findAllByUserId(int userId);

    // Lazy Loading 때문에 SELECT가 3번이나 발생함
//    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    // option_tb와 product_tb를 미리 조인한 쿼리를 사용하면 SELECT가 한번만 일어날 것
    // 의문:
    // 강의에서 이 정도 조인은 상관없다고 하셨는데,
    // JOIN과 반복 SELECT의 Trade-Off 관계에서
    // 성능 측면으로 볼 때 서로가 어느 정도의 현실적인 비중을 갖는지 궁금합니다.
    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    @Modifying
    @Query("delete from Cart c where c.user.id=:userId")
    void deleteByUserId(@Param("userId") int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);

    // 폐기
//    @Query("select c from Cart c where c.user.id = :userID and c.id = :cartId")
//    Optional<Cart> findByUserIdCartId(@Param("userId") int userId, @Param("cartId") int cartId);
}
