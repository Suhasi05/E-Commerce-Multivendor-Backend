package com.example.E_Commerce.service;

import com.example.E_Commerce.modal.Product;
import com.example.E_Commerce.modal.Review;
import com.example.E_Commerce.modal.User;
import com.example.E_Commerce.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req, User user, Product product);
    List<Review> gwtReviewByProductId(Long productId);
    Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;

}
