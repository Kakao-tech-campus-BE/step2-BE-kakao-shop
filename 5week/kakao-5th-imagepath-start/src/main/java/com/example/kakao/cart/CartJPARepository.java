package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

//    @Query(value = "insert into cart_tb(user_id, option_id, quantity, price) values(:userId, :optionId, :quantity, :price)", nativeQuery = true)
//    void mSave(@Param("userId") int userId, @Param("optionId") int optionId, @Param("quantity") int quantity, @Param("price") int price);

    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> findAllByUserId(int userId);
    // todo  쿼리 수정해야함!!

    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

//    @Query("select c from Cart c join fetch c.option.o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
//    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    @Query("SELECT DISTINCT C FROM Cart C JOIN FETCH C.option WHERE C.user.id = :userId")
    public List<Cart> findAllWithOptionsUsingFetchJoinByUserId(@Param("userId") int userId);

    @Query("SELECT DISTINCT C FROM Cart C JOIN fETCH C.option WHERE C.id = :cartId")
    public Optional<Cart> findCartWithOptionUsingFetchJoinById(@Param("cartId") Integer cartId);

    void deleteByUserId(int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
