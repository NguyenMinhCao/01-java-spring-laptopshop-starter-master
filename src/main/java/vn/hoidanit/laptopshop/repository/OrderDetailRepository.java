package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT o FROM OrderDetail o where o.order = :order")
    List<OrderDetail> getAllOrderDetail(Order order);

    @Query("SELECT o FROM OrderDetail o where o.order = :order")
    OrderDetail getOrderDetail(Order order);
}
