package com.fawry.fawrymall.repository;

import com.fawry.fawrymall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> getProductsByMerchantId(Long merchantId, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.name IS NULL OR p.name = '' OR p.name LIKE :name " +
            "AND p.sku IS NULL OR p.sku = '' OR p.sku LIKE :sku")
    Page<Product> filterProducts(String name, String sku, Pageable pageable);
}
