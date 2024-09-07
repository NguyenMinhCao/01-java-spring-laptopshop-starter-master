package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

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
    public String postMethodName(@ModelAttribute("newProduct") @Valid Product product,
            BindingResult newBindingResult,
            @RequestParam("getImgFile") MultipartFile file) {
        if (newBindingResult.hasErrors()) {
            return "/admin/product/create";
        }
        String avatar = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(avatar);
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/product-detail/{id}")
    public String getDetailProduct(@PathVariable("id") Long id, Model model) {
        Optional<Product> getProduct = productService.findByIdProduct(id);
        Product productUnwrap = getProduct.get();
        model.addAttribute("imagePro", productUnwrap.getImage());
        model.addAttribute("idPro", productUnwrap.getId());
        model.addAttribute("namePro", productUnwrap.getName());
        model.addAttribute("pricePro", productUnwrap.getPrice());
        return "/admin/product/detail";
    }

}
