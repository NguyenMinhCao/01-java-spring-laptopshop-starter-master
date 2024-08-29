package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import vn.hoidanit.laptopshop.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();

    Product save(Product product);
}
