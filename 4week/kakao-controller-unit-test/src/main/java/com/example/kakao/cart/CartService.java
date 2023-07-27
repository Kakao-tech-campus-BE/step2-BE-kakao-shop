package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.Order;
import com.example.kakao.product.ProductService;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.product.option.ProductOptionService;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CartService {

    @Autowired
    private final FakeStore fakeStore;

    @Autowired
    private final CartJPARepository cartRepository;

    @Autowired
    private final ProductOptionService productOptionService;

    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public Cart findById(int id) {
        // 유효성 검사 등 필요한 로직 구현
        // 주문 조회 로직 추가

        return cartRepository.findById(id)
                .orElseThrow(() -> new Exception404("장바구니를 찾을 수 없습니다. ID: " + id));
    }

    @Transactional
    public Cart saveCart(User user, CartRequest.SaveDTO saveDTO) {

        // Validation for Quantity

        ProductOption productOption = productOptionService.findOptionsByProductId(saveDTO.getOptionId()).get(0);

        if (productOption == null) {
            throw new Exception404("없는 옵션입니다." + saveDTO.getOptionId());
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProductOption(productOption);
        cart.setQuantity(saveDTO.getQuantity());
        cart.setPrice(productOption.getPrice() * saveDTO.getQuantity());

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCartQuantity(int cartId, int quantity) {
        if (quantity < 0 || quantity >= Math.pow(2,32)) {
            throw new Exception400("잘못된 수량입니다."+quantity);
        }

        try{
            Cart cart = findById(cartId);
            cart.setQuantity(quantity);
            cart.setPrice(cart.getProductOption().getPrice() * quantity);
            return cartRepository.save(cart);

        } catch (Exception e) {
            throw  new Exception404("해당 장바구니가 없습니다."+ cartId);
        }
    }

    @Transactional
    public void deleteCart(int cartId) {
        try {
            cartRepository.deleteById(cartId);
        } catch (Exception e) {
            throw new Exception404("해당 장바구니가 없습니다."+ cartId);
        }
    }
}
