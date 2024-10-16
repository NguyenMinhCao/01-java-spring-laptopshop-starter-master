package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.dto.CartDetailUpdateRequestDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService, UserService userService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getDetailProduct(@PathVariable("id") Long id, Model model) {
        Product product = this.productService.findByIdProduct(id).get();
        model.addAttribute("product", product);
        return "client/product/detail";
    }

    @PostMapping("add-product-to-cart/{id}")
    public String addProtoCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");
        long productId = id;
        this.productService.handleAddProductToCart(emailUser, productId, session);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartDetail(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");
        List<CartDetail> lstCartDetail = this.productService.arrGetCartDetail(emailUser);
        double totalPrice = 0;
        for (CartDetail cd : lstCartDetail) {
            totalPrice = totalPrice + (cd.getProduct().getPrice() * cd.getQuantity());
        }
        model.addAttribute("cartDetails", lstCartDetail);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @GetMapping("/cart/delete/{id}")
    public String cartDetailDetele(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        this.productService.deteleCartDetailById(id, session);
        return "redirect:/cart";
    }

    // update quantity
    @PostMapping("/cart/update")
    public ResponseEntity<?> updateCartDetailQuantity(@RequestBody CartDetailUpdateRequestDTO request) {
        long cartDetailId = request.getCartDetailId();
        int quantity = request.getQuantity();
        try {
            CartDetail cartDetail = this.productService.getCartDetailById(cartDetailId);
            cartDetail.setQuantity(quantity);
            cartDetail.setPrice(cartDetail.getProduct().getPrice() * quantity);
            this.productService.handleSaveCartDetail(cartDetail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật thất bại");
        }
    }

    @GetMapping("/confirm-checkout")
    public String getCheckOutPage(HttpServletRequest request, Model model) {
        model.addAttribute("cart", new Cart());
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");
        List<CartDetail> lstCartDetail = this.productService.arrGetCartDetail(emailUser);
        double totalPrice = 0;
        for (CartDetail cd : lstCartDetail) {
            totalPrice = totalPrice + (cd.getProduct().getPrice() * cd.getQuantity());
        }
        model.addAttribute("cartDetails", lstCartDetail);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/place-order")
    public String placeOrderUser(HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        this.productService.handleSaveOrder(email, receiverName, receiverAddress, receiverPhone, session);
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getPageThanks() {
        return "client/cart/thanks";
    }

}
