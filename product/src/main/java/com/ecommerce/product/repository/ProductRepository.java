package com.ecommerce.product.repository;

import com.ecommerce.product.models.Product;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT p FROM products p WHERE p.active = true and p.stockQuantity > 0 and lower(p.name) like lower(CONCAT('%', :keyword, '%')) ")
    List<Product> searchProducts(String keyword);

    Optional<Product> findByIdAndActiveTrue(Long id);
}
