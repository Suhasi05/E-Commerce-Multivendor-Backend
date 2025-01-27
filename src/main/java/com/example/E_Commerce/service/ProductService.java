package com.example.E_Commerce.service;

import com.example.E_Commerce.Exception.ProductException;
import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest req, Seller seller);
    void deleteProduct(Long id) throws ProductException;
    Product updateProduct(Long id, Product product) throws ProductException;
    Product findProductById(Long id) throws ProductException;
    List<Product> findAllProducts(String query);
    Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, Integer maxDiscount, String sort, String stock, Integer pageNumber);
    List<Product> getProductBySellerId(Long sellerId);
}
