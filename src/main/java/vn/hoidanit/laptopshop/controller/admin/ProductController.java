package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> listProduct = productService.getAllProduct();
        model.addAttribute("listProduct", listProduct);
        return "/admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductCreate(Model model) {
        model.addAttribute("newProduct", new Product());
        return "/admin/product/create";
    }

    @PostMapping("/admin/product/create-product")
    public String postMethodName(@ModelAttribute("newProduct") Product product,
            @RequestParam("getImgFile") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(avatar);
        this.productService.handleSaveProduct(product);
        return "/admin/product/show";
    }

}
