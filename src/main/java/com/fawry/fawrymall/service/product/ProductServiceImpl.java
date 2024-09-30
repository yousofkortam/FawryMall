package com.fawry.fawrymall.service.product;

import com.fawry.fawrymall.dto.ProductDto;
import com.fawry.fawrymall.entity.Product;
import com.fawry.fawrymall.repository.ProductRepository;
import com.fawry.fawrymall.service.category.CategoryService;
import com.fawry.fawrymall.service.mechant.MerchantService;
import com.fawry.fawrymall.util.MyLogger;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final MerchantService merchantService;

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> filterProducts(String name, String sku, Pageable pageable) {
        return productRepository.filterProducts(name, sku, pageable);
    }

    @Override
    public Map<String, List<Product>> getProductsByMerchantId(Long merchantId, Pageable pageable) {
        boolean isMerchantExists = merchantService.isMerchantExist(merchantId);
        if (!isMerchantExists) {
            throw new IllegalArgumentException("Merchant with id " + merchantId + " does not exist");
        }
        Page<Product> products = productRepository.getProductsByMerchantId(merchantId, pageable);
        return products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.getCategory().getName()
                ));
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id " + id + " not found")
        );
    }

    @Override
    public Product createProduct(ProductDto product) {
        Product newProduct = Product.builder()
                .name(product.name())
                .price(product.price())
                .sku(UUID.randomUUID().toString())
                .category(categoryService.getCategoryById(product.categoryId()))
                .merchant(merchantService.getMerchantById(product.merchantId()))
                .build();
        newProduct = productRepository.save(newProduct);
        MyLogger.getInstance().info("Product " + newProduct.getName() + " with id " + newProduct.getId() + " created");
        return newProduct;
    }

    @Override
    public Product updateProduct(Long id, ProductDto product) {
        Product oldProduct = getProductById(id);
        oldProduct.setName(product.name());
        oldProduct.setPrice(product.price());
        oldProduct.setCategory(categoryService.getCategoryById(product.categoryId()));
        oldProduct.setMerchant(merchantService.getMerchantById(product.merchantId()));
        oldProduct = productRepository.save(oldProduct);
        MyLogger.getInstance().info("Product " + oldProduct.getName() + " with id " + oldProduct.getId() + " updated");
        return oldProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        MyLogger.getInstance().info("Product " + product.getName() + " with id " + product.getId() + " deleted");
    }
}
