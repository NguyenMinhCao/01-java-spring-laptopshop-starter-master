package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String getHomePage(@RequestParam("page") Optional<String> pageOptional, Model model) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
        }
        Pageable pageable = PageRequest.of(page - 1, 20);
        Page<Product> listProductPage = productService.getAllProductPage(pageable);
        List<Product> listProducts = listProductPage.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProductPage.getTotalPages());
        model.addAttribute("listProducts", listProducts);
        return "client/homepage/show";
    }

    @GetMapping("/products")
    public String getProductFilter(Model model, @ModelAttribute ProductCriteriaDTO productCriteriaDTO,
            HttpServletRequest request) {
        int page = 1;

        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            }
        } catch (Exception e) {
        }

        Pageable pageable = PageRequest.of(page - 1, 9);
        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            String sort = productCriteriaDTO.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 9, Sort.by(Product_.PRICE).ascending());
            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 9, Sort.by(Product_.PRICE).descending());
            }
        }

        Page<Product> listProductPage = productService.getAllProductPageSpec(pageable, productCriteriaDTO);
        List<Product> listProducts = listProductPage.getContent().size() > 0 ? listProductPage.getContent()
                : new ArrayList<Product>();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            qs = qs.replace("page=" + page, "");
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listProductPage.getTotalPages());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("queryString", qs);
        return "client/product/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashCode = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashCode);
        user.setRole(this.userService.getRoleByName("USER"));
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {
        return "client/auth/deny";
    }

    @GetMapping("/history-order")
    public String getAllOrder(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = this.userService.getUserByEmail(email);
        List<Order> lstOrder = this.orderService.getListOrderByUser(user);
        model.addAttribute("lstOrder", lstOrder);
        return "client/homepage/history";
    }
}
