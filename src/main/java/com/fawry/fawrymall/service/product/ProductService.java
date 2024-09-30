package com.fawry.fawrymall.service.product;

import com.fawry.fawrymall.dto.ProductDto;
import com.fawry.fawrymall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);
    Page<Product> filterProducts(String name, String sku, Pageable pageable);
    Map<String, List<Product>> getProductsByMerchantId(Long merchantId, Pageable pageable);
    Product getProductById(Long id);
    Product createProduct(ProductDto product);
    Product updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
}
