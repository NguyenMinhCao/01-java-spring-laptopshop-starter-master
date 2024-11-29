package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.Specification.ProductSpecs;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, UserService userService, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    public Page<Product> getAllProductPage(Pageable page) {
        return this.productRepository.findAll(page);
    }

    public Page<Product> getAllProductPageSpec(Pageable page, ProductCriteriaDTO productCriteriaDTO) {
        Specification<Product> combinedSpec = Specification.where(null);

        if (productCriteriaDTO.getFactory() == null && productCriteriaDTO.getTarget() == null
                && productCriteriaDTO.getPrice() == null) {
            return this.productRepository.findAll(page);
        }

        if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
            Specification<Product> currentsSpecs = ProductSpecs.matchListFactory(productCriteriaDTO.getFactory().get());
            combinedSpec = combinedSpec.and(currentsSpecs);
        }

        if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
            Specification<Product> currentsSpecs = ProductSpecs.matchListTarget(productCriteriaDTO.getTarget().get());
            combinedSpec = combinedSpec.and(currentsSpecs);
        }

        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            Specification<Product> currentsSpecs = this.buildSpecification(productCriteriaDTO.getPrice().get());
            combinedSpec = combinedSpec.and(currentsSpecs);
        }
        return this.productRepository.findAll(combinedSpec, page);
    }

    public Specification<Product> buildSpecification(List<String> price) {
        Specification<Product> combinedSpec = Specification.where(null);
        for (String p : price) {
            double min = 0;
            double max = 0;
            switch (p) {
                case "duoi-10-trieu":
                    min = 1;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 300000000;
                    break;
            }
            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecs.matchMultiplacePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }
        return combinedSpec;
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Optional<Product> findByIdProduct(Long id) {
        return this.productRepository.findById(id);
    }

    public void deteleProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    public void deteleCartDetailById(Long id, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(id);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart cart = cartDetail.getCart();
            int sumInCart = cart.getSum();
            if (sumInCart >= 1) {
                this.cartDetailRepository.deleteById(id);
                cart.setSum(sumInCart - 1);
                this.cartRepository.save(cart);
                sumInCart = cart.getSum();
                session.setAttribute("sum", sumInCart);
                if (sumInCart == 0) {
                    this.cartRepository.delete(cart);
                }
            }
        }
    }

    public void handleAddProductToCart(String emailUser, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(emailUser);

        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);
                cart = this.cartRepository.save(newCart);
            }

            Optional<Product> productOptional = this.findByIdProduct(productId);
            Product product = productOptional.get();
            // Tìm xem có product nào mà muốn thêm vào giỏ hàng đã tồn tại trong trong giỏ
            // hàng chưa
            // boolean existsProductInCart =
            // this.cartDetailRepository.existsProductInCartDetail(cart, product);
            // Tìm ra cartDetail mà có trong giỏ hàng đã có sản phẩm muốn thêm rồi
            // để lấy ra id cartDetail update số lượng cartDetail
            CartDetail oldDetail = this.cartDetailRepository.existsProductInCartDetail(cart, product);

            if (oldDetail == null) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setQuantity(1);
                cartDetail.setPrice(product.getPrice());
                this.cartDetailRepository.save(cartDetail);
                int s = cart.getSum() + 1;
                cart.setSum(s);
                this.cartRepository.save(cart);
                session.setAttribute("sum", s);
            } else {
                oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                oldDetail.setPrice(oldDetail.getQuantity() * oldDetail.getProduct().getPrice());
                this.cartDetailRepository.save(oldDetail);
            }
        }

    }

    public List<CartDetail> arrGetCartDetail(String emailUser) {
        User user = this.userService.getUserByEmail(emailUser);
        List<CartDetail> listProductCart = null;
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            listProductCart = this.cartDetailRepository.findByCart(cart);
        }
        return listProductCart;
    }

    public CartDetail getCartDetailById(long id) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(id);
        CartDetail cartDetail = cartDetailOptional.get();
        return cartDetail;
    }

    public CartDetail handleSaveCartDetail(CartDetail cartDetail) {
        return this.cartDetailRepository.save(cartDetail);
    }

    public User getUserByEmail(String email) {
        return this.userService.getUserByEmail(email);
    }

    public void handleSaveOrder(String email, String receiverAddress, String receiverName, String receiverPhone,
            HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        Cart cart = this.cartRepository.findByUser(user);
        List<CartDetail> lstCartDetails = this.cartDetailRepository.findByCart(cart);
        double totalPrice = 0;
        for (CartDetail cartDetail : lstCartDetails) {
            totalPrice += cartDetail.getPrice();
        }
        Order order = new Order();
        order.setReceiverAddress(receiverAddress);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setStatus("PENDING");
        this.orderRepository.save(order);
        this.handleSaveOrderDetail(email, order);
        session.setAttribute("sum", 0);
    }

    public void handleSaveOrderDetail(String email, Order order) {
        User user = this.userService.getUserByEmail(email);
        Cart cart = this.cartRepository.findByUser(user);
        List<CartDetail> lstCartDetails = this.cartDetailRepository.findByCart(cart);
        for (CartDetail cartDetail : lstCartDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setQuantity(cartDetail.getQuantity());
            this.orderDetailRepository.save(orderDetail);
        }
        this.deteleCart(email);
    }

    public void deteleCart(String email) {
        User user = this.userService.getUserByEmail(email);
        Cart cart = this.cartRepository.findByUser(user);
        this.cartDetailRepository.deleteCartDetailByCart(cart);
        this.cartRepository.delete(cart);
    }
}