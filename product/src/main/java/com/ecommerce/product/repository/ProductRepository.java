package com.ecommerce.product.repository;

import com.ecommerce.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT p FROM products p WHERE p.active = true and p.stockQuantity > 0 and lower(p.name) like lower(CONCAT('%', :keyword, '%')) ")
    List<Product> searchProducts(String keyword);
}
