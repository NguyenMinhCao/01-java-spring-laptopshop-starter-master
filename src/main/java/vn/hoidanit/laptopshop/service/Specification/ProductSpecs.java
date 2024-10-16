package vn.hoidanit.laptopshop.service.Specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    public static Specification<Product> matchListFactory(List<String> factorys) {
        return (root, query, criteriaBuilder) -> root.get(Product_.FACTORY).in(factorys);
    }

    public static Specification<Product> matchListTarget(List<String> targets) {
        return (root, query, criteriaBuilder) -> root.get(Product_.TARGET).in(targets);
    }

    public static Specification<Product> matchMultiplacePrice(double priceMin, double priceMax) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), priceMin, priceMax);
    }
}
