package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public void handleSaveOrder(Order order) {
        this.orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        this.orderRepository.delete(order);
    }

    public void deleteOrderDetail(OrderDetail orderDetail) {
        this.orderDetailRepository.delete(orderDetail);
    }

    public List<OrderDetail> getAllOrder() {
        return this.orderDetailRepository.findAll();
    }

    public Order findOrderById(long id) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        return orderOptional.get();
    }

    public List<Order> getListOrder() {
        return this.orderRepository.findAll();
    }

    public List<Order> getListOrderByUser(User user) {
        return this.orderRepository.getAllOrderByUser(user);
    }

    public List<OrderDetail> getLstOrderDetailByOrder(long id) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        Order order = orderOptional.get();
        return this.orderDetailRepository.getAllOrderDetail(order);
    }
}
