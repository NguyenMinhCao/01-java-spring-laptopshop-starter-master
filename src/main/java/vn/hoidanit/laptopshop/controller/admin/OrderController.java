package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model) {
        List<Order> lstOrder = this.orderService.getListOrder();
        model.addAttribute("lstOrder", lstOrder);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/detail/{id}")
    public String getDetailOrder(@PathVariable long id, Model model) {
        List<OrderDetail> lstOderDetail = this.orderService.getLstOrderDetailByOrder(id);
        model.addAttribute("lstOderDetail", lstOderDetail);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/view-update/{id}")
    public String getViewUpdate(@PathVariable long id, Model model) {
        Order order = this.orderService.findOrderById(id);
        model.addAttribute("order", order);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update-status")
    public String postUpdateStatusOrder(@ModelAttribute("order") Order order) {
        Order findOrder = this.orderService.findOrderById(order.getId());
        if (findOrder != null) {
            findOrder.setStatus(order.getStatus());
        }
        this.orderService.handleSaveOrder(findOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/view-detele/{id}")
    public String getViewDeteleOrder(@PathVariable long id, Model model) {
        model.addAttribute("idOrder", id);
        return "admin/order/delete";
    }

    @GetMapping("/admin/order/detele/{id}")
    public String getDeleteOrder(@PathVariable long id) {
        Order order = this.orderService.findOrderById(id);
        List<OrderDetail> lstOrderDetails = this.orderService.getLstOrderDetailByOrder(order.getId());
        if (lstOrderDetails != null) {
            for (OrderDetail orderDetail : lstOrderDetails) {
                this.orderService.deleteOrderDetail(orderDetail);
            }
            this.orderService.deleteOrder(order);
        }
        return "redirect:/admin/order";
    }
}
