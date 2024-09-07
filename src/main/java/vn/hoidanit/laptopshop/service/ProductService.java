package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findByIdProduct(Long id) {
        return productRepository.findById(id);
    }
}
