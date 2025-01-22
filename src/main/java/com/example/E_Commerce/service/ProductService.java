package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest req, Seller seller);
    void deleteProduct(Long id);
    Product updateProduct(Long id, Product product);
    Product findProductById(Long id);
    List<Product> findAllProducts();
    Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, Integer maxDiscount, String sort, String stock, Integer pageNumber);
    List<Product> getProductBySellerId(Long sellerId);
}
