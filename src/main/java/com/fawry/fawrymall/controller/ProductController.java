package com.fawry.fawrymall.controller;

import com.fawry.fawrymall.dto.ProductDto;
import com.fawry.fawrymall.entity.Product;
import com.fawry.fawrymall.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(params = {"pageNo", "size"})
    public Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int size
    ) {
        return productService.getAllProducts(PageRequest.of(pageNo, size));
    }

    @GetMapping(params = {"pageNo", "size", "name", "sku"})
    public Page<Product> filterProducts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String sku) {
        return productService.filterProducts(name, sku, PageRequest.of(pageNo, size));
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/merchant/{id}")
    public Map<String, List<Product>> getProductsByMerchantId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int size) {
        return productService.getProductsByMerchantId(id, PageRequest.of(pageNo, size));
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody ProductDto product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
