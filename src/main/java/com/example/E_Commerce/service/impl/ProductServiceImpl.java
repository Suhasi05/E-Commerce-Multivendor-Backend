package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.Exception.ProductException;
import com.example.E_Commerce.Repository.CategoryRepository;
import com.example.E_Commerce.Repository.ProductRepository;
import com.example.E_Commerce.modal.Category;
import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.Seller;
import com.example.E_Commerce.request.CreateProductRequest;
import com.example.E_Commerce.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        int discountPrecentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());


        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellerPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMRPPrice(req.getMrpPrice());
        product.setSizes(req.getSize());
        product.setDiscountPercent(discountPrecentage);
        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice <= 0) {
            System.out.println("MRP -----------------------> "+mrpPrice);
            throw new IllegalArgumentException("Actual price must be greater than 0");
        }
        if(sellingPrice <= 0) {
            System.out.println("-----------------------> "+sellingPrice);
            throw new IllegalArgumentException("Actual price must be greater than 0");
        }
        double discountPercentage = ((double)(mrpPrice - sellingPrice) / mrpPrice) * 100;
        return (int) Math.round(discountPercentage);
    }

    @Override
    public void deleteProduct(Long id) throws ProductException {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductException {
        findProductById(id);
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        return productRepository.findById(id).orElseThrow(() -> new ProductException("Product not found with id "+id));
    }

    @Override
    public List<Product> findAllProducts(String query) {
        return productRepository.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, Integer maxDiscount, String sort, String stock, Integer pageNumber) {
        Specification<Product> spec = (root, query, ceriteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(category!=null) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(ceriteriaBuilder.equal(categoryJoin.get("categoryId"), category));
            }
            if(colors != null && !colors.isEmpty()) {
                System.out.println("color" + colors);
                predicates.add(ceriteriaBuilder.equal(root.get("color"), colors));
            }
//            filter by size
            if(sizes != null && !sizes.isEmpty()) {
                predicates.add(ceriteriaBuilder.equal(root.get("size"), sizes));
            }
            if(minPrice != null) {
                predicates.add(ceriteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
            }
            if(maxPrice != null) {
                predicates.add(ceriteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
            }
            if(minDiscount != null) {
                predicates.add(ceriteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"), minDiscount));
            }
            if(stock != null && !stock.isEmpty()) {
                predicates.add(ceriteriaBuilder.equal(root.get("stock"), stock));
            }
            return ceriteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if(sort != null && !sort.isEmpty()) {
            pageable = switch (sort) {
                case "price_low" ->
                        PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").ascending());
                case "price_high" ->
                        PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").descending());
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
            };
        } else {
            pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
        }
        return productRepository.findAll(spec, pageable);
    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }
}
