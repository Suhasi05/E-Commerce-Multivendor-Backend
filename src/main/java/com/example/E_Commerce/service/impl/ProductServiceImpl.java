package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Repository.CategoryRepository;
import com.example.E_Commerce.Repository.ProductRepository;
import com.example.E_Commerce.modal.Category;
import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.request.CreateProductRequest;
import com.example.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {
        Category category1 = categoryRepository.findByCategoryId(req.getCategory());

        if(category1 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1 = categoryRepository.save(category);
        }

        Category category2 = categoryRepository.findByCategoryId(req.getCategory2());
        if(category2 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepository.save(category);
        }

        Category category3 = categoryRepository.findByCategoryId(req.getCategory3());
        if(category3 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepository.save(category);
        }

        int discountPrecentage = calculateDiscountPercentage(req.getMRPprice(), req.getSellingPrice());


        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellerPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMRPPrice(req.getMRPprice());
        product.setSizes(req.getSize());
        product.setDiscountPercent(discountPrecentage);
        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int mrPprice, int sellingPrice) {
        if(mrPprice <= 0) {
            throw new IllegalArgumentException("Actusal price must be greater than 0");
        }
        double discount = (sellingPrice - mrPprice) / 100.0;
        double discountPercentage = (discount / mrPprice)*100;
        return (int)Math.round(discountPercentage);
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product findProductById(Long id) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return List.of();
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, Integer maxDiscount, String sort, String stock, Integer pageNumber) {
        return null;
    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {
        return List.of();
    }
}
