package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    // @Query("SELECT CASE WHEN COUNT(c.product) > 0 THEN true ELSE false END FROM
    // CartDetail c WHERE c.cart = :cart AND c.product = :product")
    // boolean existsProductInCartDetail(Cart cart, Product product);

    @Query("SELECT c FROM CartDetail c WHERE c.cart = :cart AND c.product = :product")
    CartDetail existsProductInCartDetail(Cart cart, Product product);

    @Query("SELECT c FROM CartDetail c WHERE c.cart = :cart")
    List<CartDetail> getCartDetailUser(Cart cart);

    List<CartDetail> findByCart(Cart cart);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartDetail c WHERE c.cart = :cart")
    void deleteCartDetailByCart(@Param("cart") Cart cart);
}
